package com.qm.base.shared.web.proxy;

import java.util.Comparator;

import com.qm.base.shared.web.filter.QmFilter;
import com.qm.base.shared.web.filter.QmFilterChain;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 * QmFilterChainProxy 是一个自定义的过滤器链代理类，
 * 负责顺序执行注册的 Filter，实现类似 Spring Security 的拦截器链机制。
 */
public class QmFilterChainProxy implements Filter {

    private final List<QmFilter> filters;

    public QmFilterChainProxy(List<QmFilter> filters) {
        this.filters = filters.stream()
                .sorted(Comparator.comparingInt(QmFilter::getOrder))
                .toList();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain originalChain)
            throws IOException, ServletException {
        QmFilterChain qmChain = new QmFilterChain(filters, originalChain);
        qmChain.doFilter((HttpServletRequest) request, (HttpServletResponse) response);
    }
}
