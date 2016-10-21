/**
 * Bestpay.com.cn Inc.
 * Copyright (c) 2011-2016 All Rights Reserved.
 */
package com.jdkcc.ts.service.component.kafka.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Jiangjiaze
 * @version Id: GroupName.java, v 0.1 2016/8/17 0017 下午 3:03 FancyKong Exp $$
 * 消费者组枚举
 * 每个组可以有多个消费者,每个组可以订阅多个主题,一个主题的每条消息只能由这个组的一个消费者去消费
 */
@Getter
@AllArgsConstructor
public enum GroupName {
    USER("USER","用户分组"),
    USER_CREATE("USER_CREATE","用户创建");
    private String code;
    private String info;
}
