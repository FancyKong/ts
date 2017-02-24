package com.jdkcc.ts.service;

import com.jdkcc.ts.dal.entity.WeixinUser;
import com.jdkcc.ts.repository.IBaseDAO;
import com.jdkcc.ts.repository.WeixinUserDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
public class WeixinUserService extends ABaseService<WeixinUser, Long> {

    @Autowired
    private WeixinUserDAO weixinUserDAO;

    @Override
    protected IBaseDAO<WeixinUser, Long> getEntityDAO() {
        return weixinUserDAO;
    }

    public WeixinUser findByOpenid(String openid) {
        return weixinUserDAO.findByOpenid(openid);
    }

    public boolean exist(String openid) {
        return weixinUserDAO.findByOpenid(openid) != null;
    }


}
