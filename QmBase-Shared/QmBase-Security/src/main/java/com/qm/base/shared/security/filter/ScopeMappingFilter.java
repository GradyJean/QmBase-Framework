package com.qm.base.shared.security.filter;

import com.qm.base.core.common.constants.FilterOrder;
import com.qm.base.shared.security.context.SecurityContext;
import com.qm.base.shared.security.context.SecurityContextHolder;
import com.qm.base.shared.security.mapping.ScopeMappingLoader;
import com.qm.base.shared.security.model.ScopeEntry;
import com.qm.base.shared.security.model.ScopeMappingEntry;
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
public class ScopeMappingFilter implements QmFilter {
    private final ScopeMappingLoader scopeMappingLoader;

    private List<ScopeMappingEntry> mappings;
    private final AntPathMatcher matcher = new AntPathMatcher();

    public ScopeMappingFilter(ScopeMappingLoader scopeMappingLoader) {
        this.scopeMappingLoader = scopeMappingLoader;
        loadScopeMappings();
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
        String requestUri = request.getRequestURI();
        // 请求方法
        String requestMethod = request.getMethod();
        // 权限域实体类
        ScopeEntry entry = new ScopeEntry(requestUri, requestMethod);
        for (ScopeMappingEntry mappingEntry : mappings) {
            if (matcher.match(mappingEntry.getResourcePattern(), requestUri)
                    && entry.getHttpMethod().equalsIgnoreCase(requestMethod)) {
                // 匹配成功，可以从 mappingEntry.getDomain() 拿到 domain 做进一步逻辑
                entry.setScope(mappingEntry.getScope());
                entry.setAction(mappingEntry.getAction());
                break;
            }
        }
        SecurityContextHolder.getContext().setScopeEntry(entry);
        chain.doFilter(request, response);
    }

    @Override
    public int getOrder() {
        return FilterOrder.DOMAIN_MAPPING.getOrder();
    }

    private void loadScopeMappings() {
        mappings = scopeMappingLoader.loadScopeMappings();
    }
}
