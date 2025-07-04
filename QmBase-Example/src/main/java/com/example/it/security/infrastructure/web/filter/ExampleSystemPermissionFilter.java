package com.example.it.security.infrastructure.web.filter;

import com.example.it.common.constants.ScopeEnum;
import com.qm.base.shared.security.context.SecurityContext;
import com.qm.base.shared.security.filter.AbstractPermissionFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public class ExampleSystemPermissionFilter extends AbstractPermissionFilter {

    private static final String MODEL_PATH = "SystemPermission.conf";

    public ExampleSystemPermissionFilter() {
        super(ScopeEnum.SYSTEM.name(), MODEL_PATH);
    }

    @Override
    protected int getOrderOffset() {
        return 1;
    }

    @Override
    protected String[] getRequestParameters(HttpServletRequest request, SecurityContext context) {
        return new String[]{String.valueOf(context.getUserId()), request.getRequestURI(), request.getMethod()};
    }
}
