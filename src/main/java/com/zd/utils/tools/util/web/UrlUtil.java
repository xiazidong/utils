package com.zd.utils.tools.util.web;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Zidong
 * @date 2019/5/8 7:16 PM
 */
public class UrlUtil {


    public static String getBasePath(HttpServletRequest request) {
        String path = request.getContextPath();
        return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    }

    /**
     * 获取当前服务的 URL，不包含参数
     * ex: http://127.0.0.1:10889
     * @return url
     */
    public static String getSelfUrl() {
        UriComponentsBuilder base = ServletUriComponentsBuilder.fromCurrentContextPath();
        URI uri = base.build().toUri();
        return uri.toString();
    }

    public static String urlCodeToUtf8(String str) {
        if (null == str || "".equals(str)) {
            return "";
        }
        try {
            return URLDecoder.decode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    /**
     * 获取完整 url，带参数
     */
    public static String getFullUrl(HttpServletRequest request) {
        String url = request.getRequestURL().toString();
        String queryurl = request.getQueryString();
        if (null != queryurl) {
            url += "?" + queryurl;
        }
        return url;
    }

    /**
     * 获取 url中的参数
     * @param url
     * @return Map集合
     */
    public static Map<String, List<String>> getQueryParams(String url) {
        try {
            Map<String, List<String>> params = new HashMap<>();
            String[] urlParts = url.split("\\?");
            if (urlParts.length > 1) {
                String query = urlParts[1];
                for (String param : query.split("&")) {
                    String[] pair = param.split("=");
                    String key = URLDecoder.decode(pair[0], "UTF-8");
                    String value = "";
                    if (pair.length > 1) {
                        value = URLDecoder.decode(pair[1], "UTF-8");
                    }
                    List<String> values = params.computeIfAbsent(key, k -> new ArrayList<>());
                    values.add(value);
                }
            }
            return params;
        } catch (UnsupportedEncodingException ex) {
            throw new AssertionError(ex);
        }
    }
}
