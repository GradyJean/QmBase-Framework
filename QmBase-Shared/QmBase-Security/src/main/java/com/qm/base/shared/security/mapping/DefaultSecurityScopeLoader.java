package com.qm.base.shared.security.mapping;

import com.qm.base.core.security.model.SecurityScope;

import java.util.List;

public class DefaultSecurityScopeLoader implements SecurityScopeLoader {
    @Override
    public List<SecurityScope> loadScopes() {
        return List.of(new SecurityScope("*", "*", "*"));
    }
}
