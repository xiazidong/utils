package com.zd.utils.tools.util.web;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

/**
 * @author Zidong
 * @date 2019/5/23 4:21 PM
 */
public class ResponseUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResponseUtil.class);

    private ResponseUtil() {
    }

    /**
     * 输出客户端JSON内容
     */
    public static void responseJSON(HttpServletResponse response, Object object) {
        writeResponse(JSON.toJSONString(object), "application/json");
    }

    /**
     * 输出客户端html内容
     */
    public static void responseHtml(String text) {
        HttpServletResponse response = WebUtil.getHttpResponse();
        if (response != null) {
            writeResponse(text, "text/html");
        }
    }

    public static void writeSupportCrossDomain(HttpServletResponse response) {
        HttpServletRequest request = WebUtil.getHttpRequest();
        if (request != null) {
            String origin = request.getHeader("Origin");
            if (StringUtils.isNotBlank(origin) && origin.endsWith("doodl6.com")) {
                response.setHeader("Access-Control-Allow-Origin", origin);
            }
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"));
        }
    }

    private static void writeResponse(String content, String contentType) {
        HttpServletResponse response = WebUtil.getHttpResponse();
        if (response != null) {
            response.setContentType(contentType);
            response.setCharacterEncoding("utf-8");
            try (Writer writer = response.getWriter()) {
                writer.write(content);
                writer.flush();
            } catch (IOException e) {
                LOGGER.error("输出客户端内容出现异常", e);
            }
        }
    }
}
