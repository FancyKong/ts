/**
 * Bestpay.com.cn Inc.
 * Copyright (c) 2011-2016 All Rights Reserved.
 */
package com.jdkcc.ts.service.component.kafka.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Jiangjiaze
 * @version Id: Topic.java, v 0.1 2016/8/18 0018 上午 9:11 FancyKong Exp $$
 * 主题,话题.
 */
@Getter
@AllArgsConstructor
public enum Topic {

    USER("USER","用户主题"),
    USER_CREATE("USER_CREATE","用户创建");

    private String code;
    private String info;


}
