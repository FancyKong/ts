/**
 * Bestpay.com.cn Inc.
 * Copyright (c) 2011-2016 All Rights Reserved.
 */
package com.jdkcc.ts.service.component.kafka.common;

/**
 * @author Jiangjiaze
 * @version Id: BussinessProcess.java, v 0.1 2016/8/17 0017 下午 3:13 FancyKong Exp $$
 * 这个主要为了规范就是定义一个业务逻辑方法给Process类继承
 *
 */

public abstract class BussinessProcess <DTO>{

    /**
     * 执行消费消息的业务逻辑
     * @param dto 业务逻辑所需的dto
     */
    public abstract void doBussiness(DTO dto);
}
