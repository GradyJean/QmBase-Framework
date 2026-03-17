package com.qm.base.shared.security.mapping;

import com.qm.base.core.security.constants.SecurityConstant;
import com.qm.base.core.security.model.SecurityScope;

import java.util.List;

public class DefaultSecurityScopeLoader implements SecurityScopeLoader {
    @Override
    public List<SecurityScope> loadScopes() {
        return List.of(new DefaultSecurityScope());
    }

    static class DefaultSecurityScope implements SecurityScope {
        private final String resourcePattern;
        private final String httpMethod;
        private final String scope;

        public DefaultSecurityScope() {
            this.resourcePattern = "/**";
            this.httpMethod = SecurityConstant.SECURITY_METHOD_DEFAULT;
            this.scope = SecurityConstant.SECURITY_SCOPE_DEFAULT;
        }

        @Override
        public String getResourcePattern() {
            return this.resourcePattern;
        }

        @Override
        public String getHttpMethod() {
            return this.httpMethod;
        }

        @Override
        public String getScope() {
            return this.scope;
        }
    }
}
