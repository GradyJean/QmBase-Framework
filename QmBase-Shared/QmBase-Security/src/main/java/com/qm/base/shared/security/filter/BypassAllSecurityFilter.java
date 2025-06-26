package com.qm.base.shared.security.filter;

import com.qm.base.core.common.constants.FilterOrder;
import com.qm.base.shared.logger.core.QmLog;
import com.qm.base.shared.security.config.SecurityProperties;
import com.qm.base.shared.security.util.AntPathMatcherUtil;
import com.qm.base.shared.web.filter.QmFilter;
import com.qm.base.shared.web.filter.QmFilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

/**
 * BypassAllSecurityFilter 用于跳过所有安全相关拦截器，
 * 通常应用于静态资源、公共接口等无需上下文和权限校验的场景。
 * 一旦匹配成功，将短路整个过滤器链中后续的安全处理逻辑。
 */
@Component
public class BypassAllSecurityFilter implements QmFilter {


    private final SecurityProperties securityProperties;

    public BypassAllSecurityFilter(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

    @Override
    public boolean match(HttpServletRequest request) {
        String path = request.getRequestURI();
        return AntPathMatcherUtil.match(path, securityProperties.getExcludeAllUrls());
    }

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, QmFilterChain chain) {
        String path = request.getRequestURI();
        QmLog.debug("BypassAllSecurityFilter: 跳过安全拦截器，匹配路径: {}", path);
        // 匹配成功即跳过所有拦截器，因此不调用 chain.doFilter()
    }

    @Override
    public int getOrder() {
        return FilterOrder.BYPASS_ALL_SECURITY.getOrder();
    }
}
