package com.qm.base.core.model.auth.constants;

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
     * 用户角色字段
     */
    public static final String AUTH_ROLE = "role";
    /**
     * 认证令牌字段
     */
    public static final String AUTH_TOKEN = "token";
    /**
     * 登录操作标识
     */
    public static final String AUTH_LOGIN = "login";
    /**
     * 登出操作标识
     */
    public static final String AUTH_LOGOUT = "logout";
    /**
     * 记住我功能标识
     */
    public static final String AUTH_REMEMBER_ME = "rememberMe";
    /**
     * 多个记住我令牌标识
     */
    public static final String AUTH_REMEMBER_MES = "rememberMes";
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
}
