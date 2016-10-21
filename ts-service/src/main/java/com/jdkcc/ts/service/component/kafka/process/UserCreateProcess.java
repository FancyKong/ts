/**
 * Bestpay.com.cn Inc.
 * Copyright (c) 2011-2016 All Rights Reserved.
 */
package com.jdkcc.ts.service.component.kafka.process;

import com.jdkcc.ts.service.component.kafka.common.BussinessProcess;
import com.jdkcc.ts.service.dto.request.UserCreateReqDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author Jiangjiaze
 * @version Id: UserCreateProcess.java, v 0.1 2016/10/15 10:33 FancyKong Exp $$
 */
@Slf4j
@Component
public class UserCreateProcess extends BussinessProcess<UserCreateReqDto> {

    @Override
    public void doBussiness(UserCreateReqDto userCreateReqDto) {
        log.info("doing userCreateReqDto process:{}",userCreateReqDto);
    }
}
