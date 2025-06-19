package com.qm.base.core.auth.model;

import com.qm.base.core.auth.enums.TokenType;

import java.io.Serializable;
import java.util.Date;

/**
 * Payload 标准结构。
 * 用于承载 Token 中的用户相关信息。
 * <p>
 * 包含以下字段：
 * - userId：用户唯一标识
 * - issuedAt：签发时间
 * - expiresAt：过期时间
 * - type：Token 类型（如 ACCESS、REFRESH），用于区分用途，类型为 TokenType 枚举
 * - deviceId 设备 ID 用于区分登录环境
 */
public class Payload implements Serializable {
    /**
     * 用户 ID
     */
    private Long userId;
    /**
     * 过期时间
     */
    private Date expiresAt;
    /**
     *
     */
    private TokenType type;
    /**
     * 设备 ID 用于区分登录环境
     */
    private String deviceId;

    public Payload() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
