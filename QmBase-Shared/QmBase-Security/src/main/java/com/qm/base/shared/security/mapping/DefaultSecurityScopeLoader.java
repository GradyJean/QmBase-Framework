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
        private final String pathPattern;
        private final String scope;

        public DefaultSecurityScope() {
            this.pathPattern = "/**";
            this.scope = SecurityConstant.SECURITY_SCOPE_DEFAULT;
        }

        @Override
        public String getPathPattern() {
            return this.pathPattern;
        }

        @Override
        public String getScope() {
            return this.scope;
        }
    }
}
