package com.qm.base.shared.security.filter;

import com.qm.base.core.common.constants.FilterOrder;
import com.qm.base.shared.security.config.SecurityProperties;
import com.qm.base.shared.security.context.SecurityContextHolder;
import com.qm.base.shared.security.util.AntPathMatcherUtil;
import com.qm.base.shared.web.filter.QmFilter;
import com.qm.base.shared.web.filter.QmFilterChain;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * IgnorePermissionFilter 用于跳过权限校验的拦截器。
 * 匹配配置中的 exclude-permission-urls 后，标记上下文为已授权，跳过后续权限处理。
 * 注意：上下文仍会被构建，因此可透传用户信息。
 */
@Component
public class IgnorePermissionFilter implements QmFilter {

    @Resource
    private SecurityProperties securityProperties;

    @Override
    public boolean match(HttpServletRequest request) {
        String path = request.getRequestURI();
        return AntPathMatcherUtil.match(path, securityProperties.getExcludePermissionUrls());
    }

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, QmFilterChain chain) throws ServletException, IOException {
        // 标记当前请求为已授权，跳过权限检查
        SecurityContextHolder.getContext().setAuthorized(true);
        chain.doFilter(request, response);
    }

    @Override
    public int getOrder() {
        return FilterOrder.IGNORE_PERMISSION.getOrder();
    }
}
