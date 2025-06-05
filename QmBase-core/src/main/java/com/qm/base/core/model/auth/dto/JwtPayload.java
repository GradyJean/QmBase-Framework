package com.qm.base.core.model.auth.dto;

import com.qm.base.core.model.auth.enums.TokenType;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;

/**
 * JWT Payload 标准结构。
 * 用于承载 Token 中的用户相关信息。
 * <p>
 * 包含以下字段：
 * - userId：用户唯一标识
 * - issuedAt：签发时间
 * - expiresAt：过期时间
 * - type：Token 类型（如 ACCESS、REFRESH），用于区分用途，类型为 TokenType 枚举
 */
public class JwtPayload implements Serializable {

    private Long userId;
    private String issuer;
    private Date expiresAt;
    private TokenType type; // Token 类型字段，区分 ACCESS、REFRESH 等用途，类型为 TokenType 枚举

    public JwtPayload() {
    }

    /**
     * 创建一个仅包含用户 ID 的简化 JwtPayload 实例。
     * 常用于无需设置 issuedAt、expiresAt 和 type 的场景。
     *
     * @param userId 用户唯一标识
     * @return JwtPayload 实例
     */
    public static JwtPayload ofUser(Long userId) {
        JwtPayload payload = new JwtPayload();
        payload.setUserId(userId);
        return payload;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public Date getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Date expiresAt) {
        this.expiresAt = expiresAt;
    }

    public TokenType getType() {
        return type;
    }

    public void setType(TokenType type) {
        this.type = type;
    }
}
