package com.qm.base.shared.security.config;

import com.qm.base.core.auth.config.TokenProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "qm.security")
public class SecurityProperties implements TokenProperties {
    private String secret;
    private String issuer;

    @Override
    public String getSecret() {
        return "";
    }

    @Override
    public String getIssuer() {
        return "";
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }
}
