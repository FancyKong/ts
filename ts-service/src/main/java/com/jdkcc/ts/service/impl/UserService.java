/**
 * Bestpay.com.cn Inc.
 * Copyright (c) 2011-2016 All Rights Reserved.
 */
package com.jdkcc.ts.service.impl;

import com.jdkcc.ts.dal.entities.JUser;
import com.jdkcc.ts.dal.mapper.JUserMapper;
import com.jdkcc.ts.service.api.UserFacade;
import com.jdkcc.ts.service.dto.request.UserCreateReqDto;
import com.jdkcc.ts.service.dto.response.UserInfoResDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author Jiangjiaze
 * @version Id: UserService.java, v 0.1 2016/9/28 0028 上午 9:02 FancyKong Exp $$
 */
@Service
public class UserService implements UserFacade {

    @Autowired
    private JUserMapper userMapper;

    @Override
    @Transactional
    public boolean register(UserCreateReqDto reqDto) {
        JUser user = new JUser();
        user.setCity(reqDto.getCity());
        user.setGender(reqDto.getGender());
        user.setHeadimgurl(reqDto.getHeadimgurl());
        user.setNickname(reqDto.getNickname());
        user.setOpenid(reqDto.getOpenid());
        user.setSubscribeTime(new Date());
        return userMapper.insert(user) > 0;
    }

    @Override
    public UserInfoResDto getUserInfo(long id) {
        JUser user = userMapper.selectById(id);
        return new UserInfoResDto(user.getId(),
                user.getOpenid(),
                user.getNickname(),
                user.getCity(),
                user.getSubscribeTime(),
                user.getHeadimgurl(),
                user.getGender());
    }
}
