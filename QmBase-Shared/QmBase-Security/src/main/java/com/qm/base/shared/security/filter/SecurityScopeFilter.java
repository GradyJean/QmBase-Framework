package com.qm.base.shared.security.filter;

import com.qm.base.core.common.constants.FilterOrder;
import com.qm.base.core.security.constants.SecurityConstant;
import com.qm.base.core.security.model.PermissionState;
import com.qm.base.core.security.model.SecurityScope;
import com.qm.base.shared.security.context.SecurityContext;
import com.qm.base.shared.security.context.SecurityContextHolder;
import com.qm.base.shared.security.mapping.SecurityScopeLoader;
import com.qm.base.shared.web.filter.QmFilter;
import com.qm.base.shared.web.filter.QmFilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.io.IOException;
import java.util.List;

/**
 * 权限域路由过滤器。
 * 根据请求路径匹配权限域，并在权限检查前写入安全上下文。
 */
@Component
public class SecurityScopeFilter implements QmFilter {
    private final SecurityScopeLoader scopeLoader;

    private List<SecurityScope> securityScopes;
    private final AntPathMatcher matcher = new AntPathMatcher();

    public SecurityScopeFilter(SecurityScopeLoader scopeLoader) {
        this.scopeLoader = scopeLoader;
        loadScopes();
    }

    @Override
    public boolean match(HttpServletRequest request) {
        SecurityContext context = SecurityContextHolder.getContext();
        return PermissionState.PENDING.equals(context.getPermissionState());
    }

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, QmFilterChain chain) throws IOException, ServletException {
        String requestUri = request.getServletPath();
        String contextScope = SecurityConstant.SECURITY_SCOPE_DEFAULT;
        for (SecurityScope securityScope : securityScopes) {
            if (matcher.match(securityScope.getPathPattern(), requestUri)) {
                contextScope = securityScope.getScope();
                break;
            }
        }
        SecurityContextHolder.getContext().setSecurityScope(contextScope);
        chain.doFilter(request, response);
    }

    @Override
    public int getOrder() {
        return FilterOrder.DOMAIN_MAPPING.getOrder();
    }

    private void loadScopes() {
        securityScopes = scopeLoader.loadScopes();
    }
}
