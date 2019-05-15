package com.zd.utils.filter.xss;

import com.zd.utils.tools.util.web.UrlUtil;
import org.apache.commons.lang.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * xss拦截器
 */
public class XssFilter implements Filter {
    private String filterChar;
    private String replaceChar;
    private String splitChar;
    private String excludeUrls;
    private FilterConfig filterConfig = null;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterChar = filterConfig.getInitParameter("FilterChar");
        this.replaceChar = filterConfig.getInitParameter("ReplaceChar");
        this.splitChar = filterConfig.getInitParameter("SplitChar");
        this.excludeUrls = filterConfig.getInitParameter("excludeUrls");
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        if (isExcludeUrl(request)) {
            chain.doFilter(request, response);
        } else {
            chain.doFilter(new XssHttpServletRequestWrapper((HttpServletRequest) request, filterChar, replaceChar, splitChar), response);
        }
    }

    private boolean isExcludeUrl(ServletRequest request) {
        boolean exclude = false;
        if (StringUtils.isNotBlank(excludeUrls)) {
            String[] excludeUrl = excludeUrls.split(splitChar);
            if (excludeUrl.length > 0) {
                for (String url : excludeUrl) {
                    if (UrlUtil.getURI((HttpServletRequest) request).startsWith(url)) {
                        exclude = true;
                    }
                }
            }
        }
        return exclude;
    }

    @Override
    public void destroy() {
        this.filterConfig = null;
    }
}
