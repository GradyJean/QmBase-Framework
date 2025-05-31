package com.qm.base.core.model.auth.dto;

import com.qm.base.core.model.auth.enums.TokenType;

import java.io.Serializable;
import java.time.Instant;

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
    private Instant issuedAt;
    private Instant expiresAt;
    private TokenType type; // Token 类型字段，区分 ACCESS、REFRESH 等用途，类型为 TokenType 枚举

    public JwtPayload() {
    }

    public JwtPayload(Long userId, Instant issuedAt, Instant expiresAt, TokenType type) {
        this.userId = userId;
        this.issuedAt = issuedAt;
        this.expiresAt = expiresAt;
        this.type = type;
    }

    /**
     * 获取用户 ID。
     */
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取签发时间。
     */
    public Instant getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(Instant issuedAt) {
        this.issuedAt = issuedAt;
    }

    /**
     * 获取过期时间。
     */
    public Instant getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Instant expiresAt) {
        this.expiresAt = expiresAt;
    }

    /**
     * 获取 Token 类型。
     * 如 ACCESS 或 REFRESH。
     */
    public TokenType getType() {
        return type;
    }

    public void setType(TokenType type) {
        this.type = type;
    }
}
