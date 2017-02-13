/**
 * Bestpay.com.cn Inc.
 * Copyright (c) 2011-2016 All Rights Reserved.
 */
package com.jdkcc.ts.common.utils;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Jiangjiaze
 * @version Id: TraceLogIdConverter.java, v 0.1 2016/9/27 0027 下午 5:05 FancyKong Exp $$
 */
public class TraceLogIdConverter extends ClassicConverter {

    public static final String TRACE_LOG_ID = "trace_log_id";

    @Override
    public String convert(ILoggingEvent iLoggingEvent) {
        try {
            return iLoggingEvent.getMDCPropertyMap().get(TRACE_LOG_ID);
        } catch (Exception e) {
            return (int) (Math.random() * 100) + new SimpleDateFormat("yyyy-MM-dd-hh:mm:ss*SSS").format(new Date());
        }
    }
}
