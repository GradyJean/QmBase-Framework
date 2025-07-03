package com.example.it.security.infrastructure.web.filter;

import com.qm.base.shared.security.context.SecurityContext;
import com.qm.base.shared.security.filter.AbstractPermissionFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public class ExampleSystemRoleFilter extends AbstractPermissionFilter {

    private static final String SCOPE = "System";
    private static final String MODEL_PATH = "SystemRole.conf";

    public ExampleSystemRoleFilter() {
        super(SCOPE, MODEL_PATH);
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
