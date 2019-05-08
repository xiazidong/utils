package com.zd.utils.filter;

import com.zd.utils.wapper.ParameterRequestWrapper;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 参数过滤,对所有请求app/接口的请求进行拦截，解密，并将参数放入request.parameter中
 * @author Zidong
 * @date 2019/4/12 5:21 PM
 */

@Component
public class RequestParameterFilter extends OncePerRequestFilter {
    /**
     * 过滤路径
     */
    static final String AUTH_PATH = "/app/";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        /*如果请求路径是为app,进行过滤对参数parameter内容解密，放入 request.parameter中*/
        if (request.getRequestURI().contains(AUTH_PATH)) {
            /*1.获取加密串,进行解密 or 加密*/

            /*2.解密出加密串，我和前台约定的是JSON,获取到JSON我将其转换为map，这里我直接用手动封装map代替*/
            Map<String, Object> paramter = new HashMap<>(16);
            paramter.put("username", "admin");
            paramter.put("password", "password");
            ParameterRequestWrapper wrapper = new ParameterRequestWrapper(request, paramter);
            filterChain.doFilter(wrapper, response);
            return;
        }
        filterChain.doFilter(request, response);
    }
}