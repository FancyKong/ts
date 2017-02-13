/**
 * Bestpay.com.cn Inc.
 * Copyright (c) 2011-2016 All Rights Reserved.
 */
package com.jdkcc.ts.web.aop;

import com.google.common.base.Throwables;
import com.jdkcc.ts.common.enums.ErrorCode;
import com.jdkcc.ts.common.exceptions.ServiceException;
import com.jdkcc.ts.common.utils.TraceLogIdConverter;
import com.jdkcc.ts.service.dto.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.ConstraintViolation;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

/**
 * @author Jiangjiaze
 * @version Id: Aspert.java, v 0.1 2016/9/27 0027 下午 4:22 FancyKong Exp $$
 * 对Controllers 做统一日志打印，请求参数验证， 统一事务外异常处理
 */
@Slf4j
@Aspect
@Component
public class TsAspect {

    @Autowired
    private LocalValidatorFactoryBean localValidatorFactoryBean;

    /**
     * 日志跟踪id
     */
    private static final String TRACE_LOG_ID = TraceLogIdConverter.TRACE_LOG_ID;

    @Around("execution(public com.jdkcc.ts.service.dto.response.Response" +
            " com.jdkcc.ts.web.controller.*.*(..))")
    public Response serviceAccess(ProceedingJoinPoint joinPoint) {
        //计时
        StopWatch stopwatch = new StopWatch();
        stopwatch.start();
        Response response = null;
        //获得接口名
        String interfaceName = joinPoint.getSignature().getDeclaringTypeName();
        //获得方法名
        String methodName = joinPoint.getSignature().getName();
        //服务名
        String controllerName = interfaceName + "." + methodName;
        //获得参数列表
        Object[] args = joinPoint.getArgs();
        if (args != null) {
            //初始化日志ID，在logback 的pattern 统一打印
            initMDC();
            Object argObject = args[0];
            log.info("Start to handle {}, PARAMETER: {}", controllerName, argObject);
            try {
                //参数校验
                validate(argObject);
                //业务执行
                response = (Response) joinPoint.proceed();
                stopwatch.stop();
                log.info("Finish handling {}, RESULT: {}, ELAPSED: {}", controllerName, response, stopwatch);
            } catch (Throwable throwable) {
                if (throwable instanceof ServiceException) {
                    //逻辑错误
                    ServiceException e = (ServiceException) throwable;
                    response = new Response();
                    response.setErrorCode(e.getCode());
                    response.setErrorMsg(e.getMessage());
                    stopwatch.stop();
                    log.info("Failed to call {}, RESULT: {}, ELAPSED: {}", controllerName, response, stopwatch);
                } else if (throwable instanceof DataAccessException) {
                    //数据库有问题
                    response = new Response();
                    response.setErrorCode(ErrorCode.ERROR_CODE_500_002.getCode());
                    response.setErrorMsg(ErrorCode.ERROR_CODE_500_002.getDesc());
                    stopwatch.stop();
                    log.info("Failed to call {}, RESULT: {}, ELAPSED: {}", controllerName, response, stopwatch);
                    log.error("Failed to call {}, RESULT: {}, CAUSE: {}", controllerName, response, Throwables.getStackTraceAsString(throwable));
                } else {
                    //内部错误
                    response = new Response();
                    response.setErrorCode(ErrorCode.ERROR_CODE_500_001.getCode());
                    response.setErrorMsg(ErrorCode.ERROR_CODE_500_001.getDesc());
                    stopwatch.stop();
                    log.info("Failed to call {}, RESULT: {}, ELAPSED: {}", controllerName, response, stopwatch);
                    log.error("Failed to call {}, RESULT: {}, CAUSE: {}", controllerName, response, Throwables.getStackTraceAsString(throwable));
                }
            }
        }
        return response;
    }

    /**
     * 参数校验
     * @param object 校验对象
     * @throws ServiceException
     */
    private <T> void validate(T object, Class<?>... groups) throws ServiceException {
        Set<ConstraintViolation<T>> constraintViolations = localValidatorFactoryBean.validate(object, groups);
        if (constraintViolations != null && constraintViolations.size() > 0) {
            ConstraintViolation c = constraintViolations.iterator().next();
            throw new ServiceException(ErrorCode.ERROR_CODE_400.getCode(), c.getMessage());
        }
    }

    /**
     * 初始化日志ID
     */
    private void initMDC() {
        String traceLogId = (int) (Math.random() * 100) +new SimpleDateFormat("yyyy-MM-dd-hh:mm:ss*SSS").format(new Date());
        MDC.put(TRACE_LOG_ID, traceLogId);
    }

}
