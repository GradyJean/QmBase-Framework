package com.qm.base.shared.security.filter;

import com.qm.base.core.common.constants.FilterOrder;
import com.qm.base.core.security.model.PermissionState;
import com.qm.base.shared.security.context.SecurityContext;
import com.qm.base.shared.security.context.SecurityContextHolder;
import com.qm.base.shared.security.exception.SecurityError;
import com.qm.base.shared.security.exception.SecurityException;
import com.qm.base.shared.web.filter.QmFilter;
import com.qm.base.shared.web.filter.QmFilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

/**
 * 权限处理兜底过滤器。
 * 若请求进入了安全链，但没有任何权限过滤器给出最终放行结论，则统一拒绝。
 */
@Component
public class PermissionEnforcementFilter implements QmFilter {

    @Override
    public boolean match(HttpServletRequest request) {
        SecurityContext context = SecurityContextHolder.getContext();
        return PermissionState.PENDING.equals(context.getPermissionState());
    }

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, QmFilterChain chain) {
        throw new SecurityException(SecurityError.SECURITY_NO_PERMISSION);
    }

    @Override
    public int getOrder() {
        return FilterOrder.PERMISSION_ENFORCEMENT.getOrder();
    }
}
