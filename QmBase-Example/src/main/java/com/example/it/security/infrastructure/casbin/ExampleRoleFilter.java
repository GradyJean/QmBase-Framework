package com.example.it.security.infrastructure.casbin;

import com.qm.base.shared.security.casbin.adapter.CasbinPolicyAdapter;
import com.qm.base.shared.security.context.SecurityContext;
import com.qm.base.shared.security.filter.AbstractPermissionFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public class ExampleRoleFilter extends AbstractPermissionFilter {

    public ExampleRoleFilter(CasbinPolicyAdapter casbinPolicyAdapter) {
        super(casbinPolicyAdapter);
    }

    @Override
    protected String getModelPath() {
        return formPathResource("Role.conf");
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
