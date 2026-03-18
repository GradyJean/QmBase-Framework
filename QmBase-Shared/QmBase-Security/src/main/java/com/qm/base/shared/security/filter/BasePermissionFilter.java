package com.qm.base.shared.security.filter;

import com.qm.base.core.security.model.PermissionState;
import com.qm.base.shared.security.context.SecurityContext;
import com.qm.base.shared.security.context.SecurityContextHolder;
import com.qm.base.shared.security.exception.SecurityError;
import com.qm.base.shared.security.exception.SecurityException;
import com.qm.base.shared.web.filter.QmFilter;
import com.qm.base.shared.web.filter.QmFilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.qm.base.core.common.constants.FilterOrder;

import java.io.IOException;

/**
 * 权限过滤器抽象层。
 * 用于在 CUSTOM_PERMISSION 阶段扩展角色、部门等权限拦截能力。
 */
public abstract class BasePermissionFilter implements QmFilter {

    @Override
    public boolean match(HttpServletRequest request) {
        SecurityContext context = SecurityContextHolder.getContext();
        return PermissionState.PENDING.equals(context.getPermissionState());
    }

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, QmFilterChain chain) throws IOException, ServletException {
        SecurityContext context = SecurityContextHolder.getContext();
        if (!supports(request, context)) {
            chain.doFilter(request, response);
            return;
        }
        PermissionState state = handle(request, context);
        if (PermissionState.DENIED.equals(state)) {
            throw new SecurityException(SecurityError.SECURITY_NO_PERMISSION);
        }
        if (!PermissionState.PENDING.equals(state)) {
            context.setPermissionState(state);
        }
        chain.doFilter(request, response);
    }

    @Override
    public int getOrder() {
        return FilterOrder.CUSTOM_PERMISSION.getOrder() + getOrderOffset();
    }

    /**
     * 判断当前过滤器是否适用于当前请求。
     *
     * @param request 当前请求对象
     * @param context 当前线程上下文中的安全信息
     * @return true 表示执行当前过滤器
     */
    protected abstract boolean supports(HttpServletRequest request, SecurityContext context);

    /**
     * 执行当前过滤器的权限处理逻辑。
     *
     * @param request 当前请求对象
     * @param context 当前线程上下文中的安全信息
     * @return 当前过滤器的处理状态
     */
    protected abstract PermissionState handle(HttpServletRequest request, SecurityContext context);

    /**
     * 获取权限检查的偏移量，用于调整过滤器执行顺序。
     *
     * @return 偏移量
     */
    protected abstract int getOrderOffset();
}
