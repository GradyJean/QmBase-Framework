package com.qm.base.shared.web.proxy;

import com.qm.base.shared.web.filter.QmFilter;
import com.qm.base.shared.web.filter.QmFilterChain;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

/**
 * QmFilterChainProxy 是一个自定义的过滤器链代理类，
 * 负责顺序执行注册的 Filter，实现类似 Spring Security 的拦截器链机制。
 */
@Component
public class QmFilterChainProxy extends GenericFilterBean {

    /**
     * 过滤器列表，按照 order 属性进行排序。
     * 该列表包含所有需要执行的 QmFilter 实例。
     */
    private final List<QmFilter> filters;

    /**
     * 构造函数，接收一组 QmFilter，并按照其 order 属性进行排序。
     *
     * @param filters 需要执行的过滤器列表
     */
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
