/**
 * Bestpay.com.cn Inc.
 * Copyright (c) 2011-2016 All Rights Reserved.
 */
package com.jdkcc.ts.common.exceptions;

import com.jdkcc.ts.common.enums.ErrorCode;
import lombok.Getter;

/**
 * @author Jiangjiaze
 * @version Id: ServiceException.java, v 0.1 2016/9/27 0027 下午 4:47 FancyKong Exp $$
 */
public class ServiceException extends RuntimeException{

    private static final long serialVersionUID = -7243774691044598098L;

    @Getter
    private String code;

    public ServiceException(String code) {
        this.code = code;
    }

    public ServiceException(String code, Throwable throwable) {
        super(throwable);
        this.code = code;
    }

    public ServiceException(String code, String message) {
        super(message);
        this.code = code;
    }

    public ServiceException(String code, String message, Throwable throwable) {
        super(message, throwable);
        this.code = code;
    }

    public ServiceException(ErrorCode errorCode) {
        super(errorCode.getDesc());
        this.code = errorCode.getCode();
    }
}
