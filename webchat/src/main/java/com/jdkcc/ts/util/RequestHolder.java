package com.jdkcc.ts.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 需要配置{@link org.springframework.web.context.request.RequestContextListener}
 * @see org.springframework.web.context.request.RequestContextListener
 */
public class RequestHolder {
	
    public static HttpServletRequest getRequest(){
        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return req;
    }

    public static HttpServletResponse getResponse(){
        HttpServletResponse resp = ((ServletWebRequest)RequestContextHolder.getRequestAttributes()).getResponse();
        return resp;
    }

    public static HttpSession getSession(){
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession(true);
    }

    public static HttpSession getSession(boolean create){
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession(create);
    }
    
    public static ServletContext getAppplication(){
    	ServletContext application = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getServletContext();
        return application;
    }
}
