package com.qm.base.shared.web.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qm.base.core.common.model.Result;
import com.qm.base.core.exception.QmException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 * QmFilterChain 是自定义过滤器链的执行器，模仿 Spring Security 的 FilterChainProxy 设计。
 * 它顺序执行注册的 QmFilter，实现链式调用。
 * <p>
 * 该类支持嵌套链式调用，能够安全地用于 Spring Web 的 Servlet Filter 中，
 * 保证在多线程环境下每次请求的过滤器执行顺序正确且互不干扰。
 */
public class QmFilterChain {
    /**
     * JSON 序列化工具，用于将异常信息转换为 JSON 格式响应。
     */
    private final ObjectMapper objectMapper = new ObjectMapper();
    /**
     * 过滤器列表，按照注册顺序存储。
     */
    private final List<QmFilter> filters;
    /**
     * 原始的 Servlet FilterChain，用于在所有 QmFilter 执行完毕后继续执行。
     */
    private final FilterChain originalChain;
    /**
     * 当前过滤器执行位置，初始为 0，表示从第一个过滤器开始执行。
     */
    private int currentPosition = 0;
    /**
     * 是否跳过后续过滤器执行的标志，默认为 false。
     * 如果设置为 true，则后续的过滤器将不会被执行。
     */
    private boolean byPass = false;

    public QmFilterChain(List<QmFilter> filters, FilterChain originalChain) {
        this.filters = filters;
        this.originalChain = originalChain;
    }

    /**
     * 执行过滤器链。每个过滤器负责调用 chain.doFilter() 来继续执行后续链条。
     * 该方法递归调用自身以跳过不匹配的过滤器，确保只有匹配的过滤器才被执行。
     *
     * @param request  请求对象
     * @param response 响应对象
     * @throws IOException      异常
     * @throws ServletException servlet 异常
     */
    public void doFilter(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        System.out.println(currentPosition);
        // 如果设置了 byPass，直接跳过所有 QmFilter，执行原始 FilterChain
        if (byPass) {
            originalChain.doFilter(request, response);
            return;
        }

        // 如果当前过滤器位置超出范围，说明所有 QmFilter 执行完毕
        if (currentPosition >= filters.size()) {
            originalChain.doFilter(request, response);
            return;
        }

        QmFilter nextFilter = filters.get(currentPosition);
        currentPosition++;
        if (nextFilter.match(request)) {
            try {
                nextFilter.doFilter(request, response, this);
            } catch (QmException e) {
                response.setStatus(e.getStatus());
                response.setContentType("application/json;charset=UTF-8");
                Result<String> result = Result.FAIL(e.getCode(), e.getMessage());
                response.getWriter().write(objectMapper.writeValueAsString(result));
            }
        } else {
            // 继续下一个
            doFilter(request, response);
        }
    }

    public void setByPass(boolean byPass) {
        this.byPass = byPass;
    }
}
