package com.qm.base.shared.security.filter;

import com.qm.base.core.common.constants.FilterOrder;
import com.qm.base.shared.security.config.SecurityProperties;
import com.qm.base.shared.security.util.AntPathMatcherUtil;
import com.qm.base.shared.web.filter.QmFilter;
import com.qm.base.shared.web.filter.QmFilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * SecurityBypassFilter 用于跳过所有安全相关拦截器，
 * 通常应用于静态资源、公共接口等无需上下文和权限校验的场景。
 * 一旦匹配成功，将短路整个过滤器链中后续的安全处理逻辑。
 */
@Component
public class SecurityBypassFilter implements QmFilter {

    private final static Logger LOGGER = LoggerFactory.getLogger(SecurityBypassFilter.class);

    private final SecurityProperties securityProperties;

    public SecurityBypassFilter(SecurityProperties securityProperties) {
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
        LOGGER.debug("SecurityBypassFilter: 跳过安全拦截器，匹配路径: {}", path);
        // 设置 byPass 标志，跳过后续所有安全相关过滤器
        chain.setByPass(true);
    }

    @Override
    public int getOrder() {
        return FilterOrder.BYPASS_ALL_SECURITY.getOrder();
    }
}
