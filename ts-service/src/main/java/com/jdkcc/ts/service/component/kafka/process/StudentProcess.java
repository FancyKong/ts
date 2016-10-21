/**
 * Bestpay.com.cn Inc.
 * Copyright (c) 2011-2016 All Rights Reserved.
 */
package com.jdkcc.ts.service.component.kafka.process;

import com.jdkcc.ts.service.component.kafka.common.BussinessProcess;
import com.jdkcc.ts.service.dto.Student;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author Jiangjiaze
 * @version Id: StudentProcess.java, v 0.1 2016/8/23 0023 上午 9:38 FancyKong Exp $$
 * 对Student topic的处理
 */
@Slf4j
@Component
public class StudentProcess extends BussinessProcess<Student> {
    @Override
    public void doBussiness(Student student) {
        log.info("doing bussiness: student = {}", student);
        //you can do something like insertInfoDB ect...
    }
}
