package com.qm.base.core.auth.constants;

/**
 * 认证模块常量定义。
 */
public final class AuthConstants {

    private AuthConstants() {
        // 工具类构造器私有化
    }

    /**
     * 用户标识字段
     */
    public static final String AUTH_USER_ID = "userId";
    /**
     * Token 类型标识
     */
    public static final String AUTH_TOKEN_TYPE = "type";
    /**
     * token 签发者标识
     */
    public static final String AUTH_TOKEN_ISSUER = "issuer";
    /**
     * 过期时间 标识
     */
    public static final String AUTH_TOKEN_EXPIRATION = "expiration";
    /**
     * 设备 ID
     */
    public static final String AUTH_TOKEN_DEVICE_ID = "deviceId";
}
