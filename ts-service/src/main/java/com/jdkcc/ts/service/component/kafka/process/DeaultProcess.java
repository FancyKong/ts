/**
 * Bestpay.com.cn Inc.
 * Copyright (c) 2011-2016 All Rights Reserved.
 */
package com.jdkcc.ts.service.component.kafka.process;


import com.jdkcc.ts.service.component.kafka.common.BussinessProcess;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author Jiangjiaze
 * @version Id: DeaultProcess.java, v 0.1 2016/8/22 0022 上午 10:28 FancyKong Exp $$
 * 对于默认的topic的处理.
 */
@Slf4j
@Component
public class DeaultProcess extends BussinessProcess<String> {

    static int i = 0;

    @Override
    public void doBussiness(String s) {
        //do some thing
        i++;
        log.info("duBussiness ing = {} ,i= {}",s,i);
    }
}
