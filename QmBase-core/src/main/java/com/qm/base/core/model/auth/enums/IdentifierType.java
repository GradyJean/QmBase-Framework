package com.qm.base.core.model.auth.enums;

/**
 * 登录类型枚举，标识凭证的类型。
 */
public enum IdentifierType {

    /**
     * 用户名密码登录
     */
    PASSWORD,

    /**
     * 短信验证码登录
     */
    SMS,

    /**
     * 微信登录
     */
    WECHAT,

    /**
     * 邮箱验证码登录
     */
    EMAIL_CODE,

    /**
     * GitHub 登录
     */
    GITHUB,

    /**
     * Apple 登录
     */
    APPLE
}
