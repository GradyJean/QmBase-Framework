package com.qm.base.shared.web.filter;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

public abstract class AbstractAntMatcherQmFilter implements QmFilter {
    PathMatcher pathMatcher = new AntPathMatcher();

    @Override
    public boolean match(HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        String method = request.getMethod();
        String pattern = getPattern();
        String httpMethod = getHttpMethod();

        boolean uriMatches = pathMatcher.match(pattern, requestUri);
        boolean methodMatches = (httpMethod == null || httpMethod.equalsIgnoreCase(method));
        return uriMatches && methodMatches;
    }

    /**
     * 获取路径匹配模式，如 /api/**。
     */
    protected abstract String getPattern();

    /**
     * 获取匹配的 HTTP 方法，如 GET、POST，返回 null 表示全部方法。
     */
    protected String getHttpMethod() {
        return null;
    }
}
