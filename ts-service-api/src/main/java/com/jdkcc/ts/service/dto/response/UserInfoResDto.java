/**
 * Bestpay.com.cn Inc.
 * Copyright (c) 2011-2016 All Rights Reserved.
 */
package com.jdkcc.ts.service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Jiangjiaze
 * @version Id: UserInfoReqDto.java, v 0.1 2016/9/27 0027 下午 4:00 FancyKong Exp $$
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoResDto {
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
