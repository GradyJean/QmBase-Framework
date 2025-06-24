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
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final List<QmFilter> filters;
    private final FilterChain originalChain;
    private int currentPosition = 0;

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
        if (currentPosition < filters.size()) {
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
                    return; // 终止当前 filter 链执行，避免继续调用
                }
            } else {
                doFilter(request, response); // 跳过当前不匹配的，继续执行下一个
            }
        } else {
            // 所有 QmFilter 执行完毕，调用原始 Servlet FilterChain
            originalChain.doFilter(request, response);
        }
    }
}
