package com.qm.base.core.auth.model;

import com.qm.base.core.auth.enums.IdentifierType;

import java.io.Serializable;

/**
 * 抽象认证请求类。
 * <p>
 * 用于登录、注册等认证场景的通用请求模型，封装了标识符（如手机号、邮箱、微信ID）、凭证（如密码、验证码、Token）、
 * 以及标识符类型（如 EMAIL、PHONE、WECHAT 等），由子类继承扩展具体的认证行为。
 * <p>
 * 适用于支持多种登录方式的统一认证接口设计，有助于身份认证流程的抽象与复用。
 */
public class AuthCredential implements Serializable {
    /**
     * 用户登录标识，例如手机号、邮箱、用户名或第三方平台唯一ID（如 openid）。
     */
    protected String identifier;

    /**
     * 用户凭证，用于验证身份。可为密码、验证码或第三方令牌。
     */
    protected String credential;

    /**
     * 登录标识类型，用于区分 identifier 的来源类型，如 USERNAME、EMAIL、PHONE、WECHAT 等。
     */
    protected IdentifierType identifierType;

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getCredential() {
        return credential;
    }

    public void setCredential(String credential) {
        this.credential = credential;
    }

    public IdentifierType getIdentifierType() {
        return identifierType;
    }

    public void setIdentifierType(IdentifierType identifierType) {
        this.identifierType = identifierType;
    }
}
