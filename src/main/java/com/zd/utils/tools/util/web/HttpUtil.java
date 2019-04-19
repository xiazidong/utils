package com.zd.utils.tools.util.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sf.pg.entity.R;
import com.zd.utils.tools.util.io.IOUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.MimeHeaders;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.zd.utils.constant.HTTPMethod.GET_REQUEST;

/**
 * @author Zidong
 * @date 2019/4/4 11:13 AM
 */
@Slf4j
public class HttpUtil {
    /**
     * 调用接口，获取图片资源
     * @param response httpRes
     */
    public static void getImage(URL url, HttpServletResponse response){
        try {
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod(GET_REQUEST);
            conn.setConnectTimeout(5 * 1000);
            // 通过输入流获取图片数据
            InputStream inStream = conn.getInputStream();
            byte[] data = IOUtil.readInputStream(inStream);
            //设置返回的文件类型
            response.setContentType("image/jpg");
            OutputStream out = response.getOutputStream();
            out.write(data);
            out.flush();
        } catch (Exception e) {
            log.error("获取图片资源发生异常，url:{}，异常原因:{}",url,e.getMessage());
        }
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
     * 修改 header信息，key-value键值对加入到 header中
     * @param request req
     * @param key key
     * @param value value
     */
    public static void reflectSetparam(HttpServletRequest request, String key, String value) {
        Class<? extends HttpServletRequest> requestClass = request.getClass();
        try {
            Field request1 = requestClass.getDeclaredField("request");
            request1.setAccessible(true);
            Object o = request1.get(request);
            Field coyoteRequest = o.getClass().getDeclaredField("coyoteRequest");
            coyoteRequest.setAccessible(true);
            Object o1 = coyoteRequest.get(o);
            Field headers = o1.getClass().getDeclaredField("headers");
            headers.setAccessible(true);
            MimeHeaders o2 = (MimeHeaders) headers.get(o1);
            o2.addValue(key).setString(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.debug("重写 request中的 header完毕！从 request.header中获取到的:{}={}", key, request.getHeader(key));
    }

    /**
     * 返回错误信息
     * 可以用在 filter、intercepter中
     * @param response httpRes
     * @param msg 描述信息
     * @param code 错误编码
     * @throws IOException io
     */
    public void returnFailMsg(ServletResponse response, String msg, String code) throws IOException {
        response.setContentType("application/json; charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        R r = R.error(msg, code);
        ObjectMapper mapper = new ObjectMapper();
        String userJson = mapper.writeValueAsString(r);
        OutputStream out = response.getOutputStream();
        out.write(userJson.getBytes(StandardCharsets.UTF_8));
        out.flush();
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
