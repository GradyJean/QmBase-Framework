package com.qm.base.shared.security.filter;

import com.qm.base.core.security.model.PermissionState;
import com.qm.base.shared.security.casbin.manager.BasePermissionManager;
import com.qm.base.shared.security.context.SecurityContext;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.util.Assert;

/**
 * Casbin 权限过滤器。
 * 子类需指定权限域、选择对应的 PermissionManager，并实现请求参数映射逻辑。
 */
public abstract class CasbinPermissionFilter extends BasePermissionFilter {
    private final BasePermissionManager permissionManager;

    public CasbinPermissionFilter(BasePermissionManager permissionManager) {
        Assert.notNull(permissionManager, "permissionManager must not be null");
        this.permissionManager = permissionManager;
    }

    @Override
    protected boolean supports(HttpServletRequest request, SecurityContext context) {
        String scope = permissionManager.getScope();
        return scope.equals(context.getSecurityScope());
    }

    @Override
    protected PermissionState handle(HttpServletRequest request, SecurityContext context) {
        Object[] params = getRequestParameters(request, context);
        boolean hasPermission = permissionManager.enforce(params);
        return onEnforce(hasPermission);
    }

    protected PermissionState onEnforce(boolean granted) {
        return granted ? PermissionState.ALLOWED : PermissionState.DENIED;
    }

    /**
     * 获取权限模型中定义的请求参数。
     * 子类根据 Casbin 模型的 request_definition 部分提供相应顺序的参数。
     *
     * @param request 当前请求对象
     * @param context 当前线程上下文中的安全信息
     * @return 参数数组，用于传入 Casbin 的 enforcer.enforce(...) 方法
     */
    protected abstract String[] getRequestParameters(HttpServletRequest request, SecurityContext context);
}
