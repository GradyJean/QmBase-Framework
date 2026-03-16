package com.example.it.security.infrastructure.web.filter;

import com.example.it.security.infrastructure.casbin.manager.ExampleApplicationPermissionManager;
import com.qm.base.shared.security.context.SecurityContext;
import com.qm.base.shared.security.filter.AbstractPermissionFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public class ExampleApplicationPermissionFilter extends AbstractPermissionFilter {

    public ExampleApplicationPermissionFilter(ExampleApplicationPermissionManager permissionManager) {
        super(permissionManager);
    }

    @Override
    protected int getOrderOffset() {
        return 2;
    }

    @Override
    protected String[] getRequestParameters(HttpServletRequest request, SecurityContext context) {
        return new String[]{String.valueOf(context.getUserId()), request.getRequestURI(), request.getMethod()};
    }
}
