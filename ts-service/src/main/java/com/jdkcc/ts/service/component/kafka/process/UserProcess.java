/**
 * Bestpay.com.cn Inc.
 * Copyright (c) 2011-2016 All Rights Reserved.
 */
package com.jdkcc.ts.service.component.kafka.process;

import com.jdkcc.ts.dal.entities.JUser;
import com.jdkcc.ts.service.component.kafka.common.BussinessProcess;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author Jiangjiaze
 * @version Id: UserProcess.java, v 0.1 2016/10/15 1:28 FancyKong Exp $$
 */
@Slf4j
@Component
public class UserProcess extends BussinessProcess<JUser>{

    @Override
    public void doBussiness(JUser jUser) {
        log.info("doing jUser process:{}",jUser);
    }
}
