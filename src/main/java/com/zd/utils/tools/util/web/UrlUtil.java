package com.zd.utils.tools.util.web;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.zd.utils.constant.HttpConstant.HTTPS_SCHEME;
import static com.zd.utils.constant.HttpConstant.HTTP_SCHEME;

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

    public static String getURI(HttpServletRequest request) {
        UrlPathHelper helper = new UrlPathHelper();
        String uri = helper.getOriginatingRequestUri(request);
        String ctx = helper.getOriginatingContextPath(request);
        if (!StringUtils.isBlank(ctx)) {
            return uri.substring(ctx.length());
        } else {
            return uri;
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
     * @param url url
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

    /**
     * http协议转https协议
     * @param uri 链接地址
     * @return https协议地址
     */
    public static String httpToHttps(String uri) {
        if (StringUtils.isBlank(uri)) {
            return uri;
        }
        uri = uri.trim();
        if (!uri.startsWith(HTTP_SCHEME) && !uri.startsWith(HTTPS_SCHEME)) {
            throw new IllegalArgumentException("URI must start width 'http://' or 'https://'");
        }
        if (uri.startsWith(HTTP_SCHEME)) {
            return uri.replace(HTTP_SCHEME, HTTPS_SCHEME);
        }
        return uri;
    }
}
