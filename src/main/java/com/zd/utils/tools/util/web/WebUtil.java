package com.zd.utils.tools.util.web;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Zidong
 * @date 2019/5/8 7:24 PM
 */
public class WebUtil {

    public static HttpServletRequest getHttpRequest() {
        try {
            return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        } catch (Exception var1) {
            return null;
        }
    }

    public static HttpServletResponse getHttpResponse() {
        try {
            return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        } catch (Exception var1) {
            return null;
        }
    }
}
