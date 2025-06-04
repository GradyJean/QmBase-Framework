package com.qm.base.auth.model.dto;

import com.qm.base.core.model.auth.enums.IdentifierType;

/**
 * 验证码请求参数对象。
 * 用于封装用户请求验证码时的必要信息，
 * 包括标识符（如邮箱、手机号、用户名）和对应的标识类型（IdentifierType）。
 * <p>
 * 适用于验证码发送前的身份验证场景。
 */
public class VerifyCodeRequest {
    private String identifier;
    private IdentifierType identifierType;

    /**
     * 获取用户标识符（如邮箱、手机号、用户名等）
     *
     * @return 用户标识符
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * 设置用户标识符（如邮箱、手机号、用户名等）
     *
     * @param identifier 用户标识符
     */
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    /**
     * 获取标识符类型（如 EMAIL、PHONE、USERNAME）
     *
     * @return 标识符类型
     */
    public IdentifierType getIdentifierType() {
        return identifierType;
    }

    /**
     * 设置标识符类型（如 EMAIL、PHONE、USERNAME）
     *
     * @param identifierType 标识符类型
     */
    public void setIdentifierType(IdentifierType identifierType) {
        this.identifierType = identifierType;
    }
}
