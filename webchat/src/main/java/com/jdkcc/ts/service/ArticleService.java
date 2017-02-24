package com.jdkcc.ts.service;

import com.jdkcc.ts.dal.dto.ArticleDTO;
import com.jdkcc.ts.dal.entity.Article;
import com.jdkcc.ts.dal.vo.ArticleVO;
import com.jdkcc.ts.dal.vo.BasicSearchVO;
import com.jdkcc.ts.repository.ArticleDAO;
import com.jdkcc.ts.repository.IBaseDAO;
import com.jdkcc.ts.util.ObjectConvertUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Slf4j
@Service
@Transactional(readOnly = true)
@CacheConfig(cacheNames = "articles")
public class ArticleService extends ABaseService<Article, Long> {

    @Autowired
    private ArticleDAO newsDAO;

    @Override
    protected IBaseDAO<Article, Long> getEntityDAO() {
        return newsDAO;
    }

    @Cacheable(key = "'articleId' + #articleId")
    public ArticleDTO findOne(Long articleId) {
        Article article = newsDAO.findOne(articleId);
        return ObjectConvertUtil.objectCopy(new ArticleDTO(), article);
    }

    @CacheEvict(allEntries = true)
    @Transactional
    public void delete(Long articleId) {
        super.delete(articleId);
    }

    public Page<ArticleDTO> findAll(BasicSearchVO basicSearchVO) {

        int pageNumber = basicSearchVO.getStartIndex() / basicSearchVO.getPageSize() + 1;
        Page<Article> newsPage = this.findAll(pageNumber, basicSearchVO.getPageSize());

        return newsPage.map(source -> {
            ArticleDTO newsDTO = new ArticleDTO();
            ObjectConvertUtil.objectCopy(newsDTO, source);
            return newsDTO;
        });
    }

    @CacheEvict(allEntries = true)
    @Transactional
    public void updateByVO(ArticleVO newsVO) {
        Article news = this.findById(newsVO.getId());
        ObjectConvertUtil.objectCopy(news, newsVO);
        news.setModifiedTime(new Date());
        if (news.getReadSum() == null){
            news.setReadSum(0);
        }
        this.update(news);
    }

    @Transactional
    public void saveByVO(ArticleVO newsVO) {
        Article news = new Article();
        ObjectConvertUtil.objectCopy(news, newsVO);
        news.setCreatedTime(new Date());
        news.setModifiedTime(new Date());
        if (news.getReadSum() == null){
            news.setReadSum(0);
        }
        this.save(news);
    }


}
