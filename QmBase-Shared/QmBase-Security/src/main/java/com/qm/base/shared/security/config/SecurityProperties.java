package com.qm.base.shared.security.config;

import com.qm.base.core.auth.config.TokenProperties;

public class SecurityProperties implements TokenProperties {

    @Override
    public String getSecret() {
        return "";
    }

    @Override
    public String getIssuer() {
        return "";
    }
}
