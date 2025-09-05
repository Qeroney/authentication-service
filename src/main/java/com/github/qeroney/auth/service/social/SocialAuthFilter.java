package com.github.qeroney.auth.service.social;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

public class SocialAuthFilter implements Filter {

    public static final String PARAM_MOBILE = "mobile";

    private class SocialAuthHttpRequestWrapper extends HttpServletRequestWrapper {

        private SocialAuthHttpRequestWrapper(HttpServletRequest request) {
            super(request);
        }

        @Override
        public Map<String, String[]> getParameterMap() {
            return super.getParameterMap()
                        .entrySet()
                        .stream()
                        .filter(entry -> !PARAM_MOBILE.equals(entry.getKey()))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        }

        @Override
        public StringBuffer getRequestURL() {
            String host = getHeader("x-forwarded-host");
            String proto = getHeader("x-forwarded-proto");
            String prefix = getHeader("x-forwarded-prefix");

            if (host != null && proto != null && prefix != null) {
                return new StringBuffer(proto)
                        .append("://")
                        .append(host)
                        .append(prefix)
                        .append(getRequestURI());
            }
            return super.getRequestURL();
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        String paramValue = request.getParameter(PARAM_MOBILE);
        if (paramValue != null) {
            request.getSession().setAttribute(PARAM_MOBILE, paramValue.equalsIgnoreCase("true"));
            filterChain.doFilter(new SocialAuthHttpRequestWrapper(request), servletResponse);
        } else {
            Boolean isMobile = (Boolean) request.getSession().getAttribute(PARAM_MOBILE);
            if (isMobile != null) {
                request.setAttribute(PARAM_MOBILE, isMobile);
                request.getSession().removeAttribute(PARAM_MOBILE);
            }
            filterChain.doFilter(new SocialAuthHttpRequestWrapper(request), servletResponse);
        }
    }
}
