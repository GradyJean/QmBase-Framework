package com.qm.base.auth.model.constant;

/**
 * 认证模块错误码枚举
 * 所有认证相关的业务错误码都在此集中定义
 * <p>
 * 提供根据错误码获取对应枚举实例的方法。
 */
public enum AuthErrorCodeEnum {
    //注册相关
    AUTH_IDENTIFIER_TYPE_EMPTY("REGISTER_IDENTIFIER_TYPE_EMPTY", "类型为空"),
    AUTH_USERNAME_INVALID("AUTH_USERNAME_INVALID", "用户名格式不正确"),
    AUTH_EMAIL_INVALID("AUTH_EMAIL_INVALID", "邮箱格式不正确"),
    AUTH_PHONE_INVALID("AUTH_PHONE_INVALID", "手机号格式不正确"),
    AUTH_ACCOUNT_EXIST("AUTH_ACCOUNT_EXIST", "账号已存在"),
    AUTH_VERIFICATION_CODE_EMPTY("AUTH_VERIFICATION_CODE_EMPTY", "验证码不能为空"),
    AUTH_VERIFICATION_CODE_ERROR("AUTH_VERIFICATION_CODE_ERROR", "验证码错误"),
    AUTH_IDENTIFIER_TYPE_INVALID("AUTH_IDENTIFIER_TYPE_INVALID", "类型异常"),
    // 登录相关
    AUTH_LOGIN_FAILED("AUTH_LOGIN_FAILED", "用户名或密码错误"),
    AUTH_LOGIN_TYPE_ERROR("AUTH_LOGIN_TYPE_ERROR", "登录类型错误"),
    AUTH_PASSWORD_INVALID("AUTH_PASSWORD_INVALID", "密码格式不正确"),
    AUTH_PASSWORD_NOT_SET("AUTH_PASSWORD_NOT_SET", "密码未设置"),
    AUTH_CREDENTIAL_EMPTY("AUTH_CREDENTIAL_EMPTY", "用户名或密码不能为空"),
    AUTH_ACCOUNT_DISABLED("AUTH_ACCOUNT_DISABLED", "账号已被禁用"),
    AUTH_ACCOUNT_LOCKED("AUTH_ACCOUNT_LOCKED", "账号已被锁定"),
    AUTH_ACCOUNT_NOT_EXIST("AUTH_ACCOUNT_NOT_EXIST", "账号不存在"),
    // 密码重置相关
    AUTH_EMAIL_OR_PHONE_EMPTY("AUTH_EMAIL_OR_PHONE_EMPTY", "邮箱或手机号码不能为空"),
    AUTH_EMAIL_OR_PHONE_NOT_EXIST("AUTH_EMAIL_OR_PHONE_NOT_EXIST", "邮箱或手机号码未注册"),
    AUTH_EMAIL_OR_PHONE_INVALID("AUTH_EMAIL_OR_PHONE_INVALID", "邮箱或手机号码格式不正确"),
    AUTH_PASSWORD_EMPTY("AUTH_PASSWORD_EMPTY", "邮箱或手机号码格式不正确"),
    // 第三方登录
    AUTH_OAUTH_UNSUPPORTED("AUTH_OAUTH_UNSUPPORTED", "不支持的第三方登录类型"),
    AUTH_OAUTH_BINDING_REQUIRED("AUTH_OAUTH_BINDING_REQUIRED", "需要绑定账号"),
    AUTH_OAUTH_BINDING_FAILED("AUTH_OAUTH_BINDING_FAILED", "账号绑定失败"),

    // Token 相关
    AUTH_REFRESH_TOKEN_EMPTY("AUTH_REFRESH_TOKEN_EMPTY", "刷新令牌不能为空"),
    AUTH_REFRESH_TOKEN_INVALID("AUTH_REFRESH_TOKEN_INVALID", "刷新令牌无效"),
    AUTH_TOKEN_INVALID("AUTH_TOKEN_INVALID", "Token 无效"),
    AUTH_TOKEN_EXPIRED("AUTH_TOKEN_EXPIRED", "Token 已过期"),
    AUTH_TOKEN_MISSING("AUTH_TOKEN_MISSING", "缺少认证 Token"),

    // 权限相关
    AUTH_ACCESS_DENIED("AUTH_ACCESS_DENIED", "没有权限访问该资源"),
    AUTH_ROLE_REQUIRED("AUTH_ROLE_REQUIRED", "需要指定角色才能访问"),

    // 通用错误
    AUTH_ERROR("AUTH_ERROR", "认证模块异常");

    // 错误码
    private final String code;
    // 错误信息
    private final String message;

    AuthErrorCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 获取错误码
     */
    public String getCode() {
        return code;
    }

    /**
     * 获取错误信息
     */
    public String getMessage() {
        return message;
    }

    /**
     * 根据错误码获取对应枚举实例
     *
     * @param code 错误码
     * @return 对应的 AuthErrorCodeEnum 枚举值
     * @throws IllegalArgumentException 如果没有匹配的枚举值
     */
    public static AuthErrorCodeEnum fromCode(String code) {
        for (AuthErrorCodeEnum value : values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        throw new IllegalArgumentException("无效的认证错误码: " + code);
    }

    @Override
    public String toString() {
        return code + ": " + message;
    }
}