/**
 * Bestpay.com.cn Inc.
 * Copyright (c) 2011-2016 All Rights Reserved.
 */
package com.jdkcc.ts.dal.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * @author Jiangjiaze
 * @version Id: JUser.java, v 0.1 2016/9/27 0027 下午 3:34 FancyKong Exp $$
 */
@Getter
@Setter
@NoArgsConstructor
public class JUser {
    /**
     * 用户id
     */
    private long id;
    /**
     * 微信openid
     */
    private String openid;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 城市
     */
    private String city;
    /**
     * 关注时间
     */
    private Date subscribeTime;
    /**
     * 头像url
     */
    private String headimgurl;
    /**
     * 性别
     */
    private String gender;
}
