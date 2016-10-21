/**
 * Bestpay.com.cn Inc.
 * Copyright (c) 2011-2016 All Rights Reserved.
 */
package com.jdkcc.ts.service.dto.response;


import java.io.Serializable;

/**
 * @author Jiangjiaze
 * @version Id: Response.java, v 0.1 2016/9/27 0027 下午 3:47 FancyKong Exp $$
 * Service统一返回类型
 */
public class Response<T> implements Serializable {
    private static final long serialVersionUID = -4319319797162300011L;
    /**
     * 调用是否成功
     */
    private boolean success;
    /**
     * 获取调用返回值
     */
    private T result;
    /**
     * 获取错误码
     */
    private String errorCode;
    /**
     * 获取错误信息
     */
    private String errorMsg;

    public Response(){}

    /**
     * 构造方法，根据flag返回不同结果
     *
     * @param flag   true|false
     * @param result 若flag=true，则返回result对象,若flag=false则返回errorCode
     */
    public Response(boolean flag, T result) {
        if (flag) {
            this.success = true;
            this.result = result;
        } else {
            this.success = false;
            this.errorCode = (String) result;
        }
    }

    public Response(String errorCode) {
        this.success = false;
        this.errorCode = errorCode;
    }

    public Response(String errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public boolean isSuccess() {
        return success;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        success = true;
        this.result = result;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.success = false;
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Response response = (Response) o;

        if (success != response.success) return false;
        if (!errorCode.equals(response.errorCode)) return false;
        if (!result.equals(response.result)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result1 = (success ? 1 : 0);
        result1 = 31 * result1 + result.hashCode();
        result1 = 31 * result1 + errorCode.hashCode();
        return result1;
    }

    @Override
    public String toString() {
        return "Response{" +
                "success=" + success +
                ", result=" + result +
                ", errorCode='" + errorCode + '\'' +
                ", errorMsg='" + errorMsg + '\'' +
                '}';
    }
}
