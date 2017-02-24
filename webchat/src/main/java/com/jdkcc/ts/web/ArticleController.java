package com.jdkcc.ts.web;

import com.jdkcc.ts.dal.MResponse;
import com.jdkcc.ts.dal.dto.ArticleDTO;
import com.jdkcc.ts.dal.entity.Article;
import com.jdkcc.ts.dal.vo.ArticleVO;
import com.jdkcc.ts.dal.vo.BasicSearchVO;
import com.jdkcc.ts.service.ArticleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;


/**
 * 文章控制器
 * Created by Cherish on 2017/1/6.
 */
@Controller
@RequestMapping("article")
public class ArticleController extends ABaseController {

    @Autowired
    private ArticleService articleService;

    /**
     * 对外开放的查看文章详情
     * @param articleId 文章ID
     * @return JSON
     */
    @GetMapping("/{articleId}")
    @ResponseBody
    public MResponse findOne(@PathVariable Long articleId){

        try {
            ArticleDTO article = articleService.findOne(articleId);

            return buildResponse(Boolean.TRUE, "查看文章详情", article);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("获取列表失败:", e.getMessage());
            return buildResponse(Boolean.FALSE, BUSY_MSG, null);
        }
    }

    @GetMapping
    @RequiresPermissions("info:show")
    public ModelAndView index(){
        ModelAndView mv = new ModelAndView("admin/article/list");
        return mv;
    }

    /**
     * 返回新增页面
     */
    @GetMapping("/add")
    @RequiresPermissions("info:add")
    public ModelAndView addForm(){
        ModelAndView mv = new ModelAndView("admin/article/add");
        return mv;
    }

    /**
     * 返回修改信息页面
     */
    @GetMapping("/{articleId}/update")
    @RequiresPermissions("info:update")
    public ModelAndView updateForm(@PathVariable("articleId") Long articleId){
        ModelAndView mv = new ModelAndView("admin/article/edit");
        Article article = articleService.findById(articleId);
        mv.addObject(article);
        return mv;
    }

    /**
     * 分页查询
     * @param basicSearchVO 基本搜索条件
     * @return JSON
     * @date 2016年8月30日 下午5:30:18
     */
    @GetMapping("/page")
    @ResponseBody
    public MResponse toPage(BasicSearchVO basicSearchVO){

        try {
            Page<ArticleDTO> page = articleService.findAll(basicSearchVO);

            return buildResponse(Boolean.TRUE, basicSearchVO.getDraw(), page);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("获取列表失败: {}", e.getMessage());
            return buildResponse(Boolean.FALSE, BUSY_MSG, null);
        }
    }

    /**
     * 删除
     * @param articleId ID
     * @return JSON
     */
    @DeleteMapping("/{articleId}/delete")
    @RequiresPermissions("info:delete")
    @ResponseBody
    public MResponse delete(@PathVariable("articleId") Long articleId){

        try {
            articleService.delete(articleId);
            return buildResponse(Boolean.TRUE, "删除成功", null);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("删除失败:{}", e.getMessage());
            return buildResponse(Boolean.FALSE, "删除失败", null);
        }
    }

    /**
     * 更改信息
     * @param articleVO 更新信息
     * @return ModelAndView
     */
    @PostMapping("/update")
    @RequiresPermissions("info:update")
    public ModelAndView update(@Validated ArticleVO articleVO, BindingResult bindingResult){

        ModelAndView mv = new ModelAndView("admin/article/edit");
        Map<String, Object> errorMap = new HashMap<>();
        mv.addObject("errorMap", errorMap);

        if(articleVO == null || articleVO.getId() == null){
            errorMap.put("msg", "数据错误");
            return mv;
        }

        if (bindingResult.hasErrors()) {
            errorMap.putAll(getErrors(bindingResult));
            mv.addObject("article", articleVO);

        }else {
            try {
                articleService.updateByVO(articleVO);

                mv.addObject("article", articleService.findById(articleVO.getId()));
                errorMap.put("msg", "修改成功");
            } catch (Exception e) {
                e.printStackTrace();
                errorMap.put("msg", "系统繁忙");
                LOGGER.error("修改错误:{}", e.getMessage());
            }
        }

        return mv;
    }

    /**
     * 保存新用户
     * @param articleVO 保存的信息
     * @return ModelAndView
     */
    @PostMapping("/save")
    @RequiresPermissions("info:add")
    public ModelAndView save(@Validated ArticleVO articleVO, BindingResult bindingResult){

        ModelAndView mv = new ModelAndView("admin/article/add");
        Map<String, Object> errorMap = new HashMap<>();
        mv.addObject("errorMap", errorMap);

        if (bindingResult.hasErrors()) {
            errorMap.putAll(getErrors(bindingResult));
            mv.addObject("article", articleVO);

        }else {
            try {
                articleService.saveByVO(articleVO);
                errorMap.put("msg", "添加成功");

            } catch (Exception e) {
                e.printStackTrace();
                errorMap.put("msg", "系统繁忙");
                LOGGER.error("添加失败:{}", e.getMessage());
            }
        }

        return mv;
    }



}
