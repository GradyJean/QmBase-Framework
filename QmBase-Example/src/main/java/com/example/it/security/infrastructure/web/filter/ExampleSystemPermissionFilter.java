package com.example.it.security.infrastructure.web.filter;

import com.example.it.security.infrastructure.casbin.manager.ExampleSystemPermissionManager;
import com.qm.base.core.security.model.PermissionState;
import com.qm.base.shared.security.context.SecurityContext;
import com.qm.base.shared.security.filter.CasbinPermissionFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public class ExampleSystemPermissionFilter extends CasbinPermissionFilter {

    public ExampleSystemPermissionFilter(ExampleSystemPermissionManager permissionManager) {
        super(permissionManager);
    }

    @Override
    protected int getOrderOffset() {
        return 1;
    }

    @Override
    protected PermissionState onEnforce(boolean granted) {
        return null;
    }

    @Override
    protected String[] getRequestParameters(HttpServletRequest request, SecurityContext context) {
        return new String[]{String.valueOf(context.getUserId()), request.getServletPath(), request.getMethod()};
    }
}
