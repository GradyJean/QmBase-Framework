package com.example.it.security.infrastructure.web.filter;

import com.example.it.security.infrastructure.casbin.ExamplePolicyLoader;
import com.qm.base.shared.security.context.SecurityContext;
import com.qm.base.shared.security.filter.AbstractPermissionFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ExampleRoleFilter extends AbstractPermissionFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExampleRoleFilter.class);
    private static final String DOMAIN_ROLE = "ROLE";

    public ExampleRoleFilter(ExamplePolicyLoader policyLoader) {
        super(policyLoader, DOMAIN_ROLE);
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
