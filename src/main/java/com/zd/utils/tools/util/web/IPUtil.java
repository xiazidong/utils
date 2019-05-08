package com.zd.utils.tools.util.web;

import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Zidong
 * @date 2019/5/8 7:17 PM
 */
public class IPUtil {

    private static final String[] IP_HEADERS = new String[]{"X-Forwarded-For", "X-Real-IP", "Proxy-Client-IP", "WL-Proxy-Client-IP"};

    /**
     * 获取当前请求的 IP地址
     * @param request
     * @return
     */
    public static String getIP(HttpServletRequest request) {
        for (String ipHeader : IP_HEADERS) {
            String ip = request.getHeader(ipHeader);
            if (!StringUtils.isEmpty(ip) && !ip.contains("unknown")) {
                return ip;
            }
        }
        return request.getRemoteAddr();
    }

}
