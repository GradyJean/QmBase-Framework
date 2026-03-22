package com.qm.base.shared.security.filter;

import com.qm.base.core.common.constants.FilterOrder;
import com.qm.base.core.security.model.PermissionState;
import com.qm.base.shared.security.config.SecurityProperties;
import com.qm.base.shared.security.context.SecurityContextHolder;
import com.qm.base.shared.security.util.AntPathMatcherUtil;
import com.qm.base.shared.security.web.PermissionRegistry;
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
 * 匹配配置中的 exclude-permission-urls 后，标记权限流程已完成，跳过后续权限处理。
 * 注意：上下文仍会被构建，因此可透传用户信息。
 */
@Component
public class PermissionIgnoreFilter implements QmFilter {

    private final PermissionRegistry permissionRegistry;

    @Resource
    private SecurityProperties securityProperties;

    public PermissionIgnoreFilter(PermissionRegistry permissionRegistry) {
        this.permissionRegistry = permissionRegistry;
    }

    @Override
    public boolean match(HttpServletRequest request) {
        String path = request.getServletPath();
        String method = request.getMethod();
        return AntPathMatcherUtil.match(path, securityProperties.getExcludePermissionUrls()) || permissionRegistry.isIgnored(path, method);
    }

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, QmFilterChain chain) throws ServletException, IOException {
        SecurityContextHolder.getContext().setPermissionState(PermissionState.IGNORED);
        chain.doFilter(request, response);
    }

    @Override
    public int getOrder() {
        return FilterOrder.IGNORE_PERMISSION.getOrder();
    }
}
