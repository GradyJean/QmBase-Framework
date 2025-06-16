package com.qm.base.shared.web.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 * QmFilterChain 是自定义过滤器链的执行器，模仿 Spring Security 的 FilterChainProxy 设计。
 * 它顺序执行注册的 QmFilter，实现链式调用。
 */
public class QmFilterChain {

    private final List<QmFilter> filters;
    private final FilterChain originalChain;
    private int currentPosition = 0;

    public QmFilterChain(List<QmFilter> filters, FilterChain originalChain) {
        this.filters = filters;
        this.originalChain = originalChain;
    }

    /**
     * 执行过滤器链。每个过滤器负责调用 chain.doFilter() 来继续执行后续链条。
     *
     * @param request  请求对象
     * @param response 响应对象
     * @throws IOException      异常
     * @throws ServletException servlet 异常
     */
    public void doFilter(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // 跳过不匹配当前请求的过滤器，确保只有匹配的过滤器才被执行
        while (currentPosition < filters.size()) {
            QmFilter nextFilter = filters.get(currentPosition++);
            if (nextFilter.match(request)) {
                nextFilter.doFilter(request, response, this);
                return;
            }
        }
        originalChain.doFilter(request, response);
    }
}
