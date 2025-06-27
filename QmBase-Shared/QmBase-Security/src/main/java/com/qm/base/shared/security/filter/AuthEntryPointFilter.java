package com.qm.base.shared.security.filter;

import com.qm.base.core.common.constants.FilterOrder;
import com.qm.base.shared.security.util.AntPathMatcherUtil;
import com.qm.base.shared.web.filter.QmFilter;
import com.qm.base.shared.web.filter.QmFilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

/**
 * AuthEntryPointFilter 用于处理认证入口点的过滤器。
 * 该过滤器通常用于匹配需要进行认证的请求路径，并在请求处理过程中跳过认证逻辑。
 * 主要用于在认证流程中提供一个入口点，便于后续的认证处理。
 */
@Component
public class AuthEntryPointFilter implements QmFilter {

    /**
     * 默认的认证 URL 列表，通常用于匹配需要进行认证的请求路径。
     * 可以根据实际需求进行扩展或修改。
     */
    public static final List<String> DEFAULT_AUTH_URLS = List.of("/auth/**");

    @Override
    public boolean match(HttpServletRequest request) {
        return AntPathMatcherUtil.match(request.getRequestURI(), DEFAULT_AUTH_URLS);
    }

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, QmFilterChain chain) throws ServletException, IOException {
        // 认证入口点过滤器跳过
        chain.setByPass(true);
        chain.doFilter(request, response);
    }

    @Override
    public int getOrder() {
        return FilterOrder.AUTH_ENTRY_POINT.getOrder();
    }
}
