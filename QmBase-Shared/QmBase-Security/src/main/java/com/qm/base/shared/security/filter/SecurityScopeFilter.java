package com.qm.base.shared.security.filter;

import com.qm.base.core.common.constants.FilterOrder;
import com.qm.base.core.security.constants.SecurityConstant;
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
 * DomainMappingFilter 用于处理域名映射的过滤器。
 * 它会在请求中设置当前域名，并确保在权限检查之前执行。
 * 该过滤器依赖于 DomainMappingLoader 加载域名映射配置。
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
        // 先排除路径，再判断是否已授权，确保短路优化与语义清晰
        return !context.isAuthorized();
    }

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, QmFilterChain chain) throws IOException, ServletException {
        // 请求路径
        String requestUri = request.getServletPath();
        // 请求方法
        String requestMethod = request.getMethod();
        String contextScope = SecurityConstant.SECURITY_SCOPE_DEFAULT;
        // 权限域实体类
        for (SecurityScope securityScope : securityScopes) {
            // 获取权限域的请求方法
            String scopeHttpMethod = securityScope.getHttpMethod();
            // 获取权限域的资源路径
            String scopeResourcePattern = securityScope.getResourcePattern();
            // 匹配请求方法 * 表示匹配任意方法
            boolean methodMatched = SecurityConstant.SECURITY_METHOD_DEFAULT.equals(scopeHttpMethod)
                    || scopeHttpMethod.equalsIgnoreCase(requestMethod);
            // 匹配资源路径
            if (matcher.match(scopeResourcePattern, requestUri) && methodMatched) {
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
