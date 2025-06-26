package com.qm.base.shared.web.filter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * QmFilter 是自定义请求过滤器接口，模仿 Spring Security 中的 Filter 链机制。
 * 实现类可通过 match 方法控制路径匹配，
 * 并通过 doFilter 执行自定义拦截逻辑。
 */
public interface QmFilter {

    /**
     * 判断当前过滤器是否匹配该请求
     *
     * @param request 当前请求
     * @return 是否匹配
     */
    boolean match(HttpServletRequest request);

    /**
     * 执行过滤逻辑
     *
     * @param request  请求对象
     * @param response 响应对象
     * @param chain    自定义的过滤器链调用器
     * @throws IOException      I/O 错误
     * @throws ServletException Servlet 错误
     */
    void doFilter(HttpServletRequest request, HttpServletResponse response, QmFilterChain chain) throws IOException, ServletException;

    /**
     * 获取过滤器执行顺序，值越小优先级越高。
     *
     * @return 执行顺序值
     */
    int getOrder();
}
