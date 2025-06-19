package com.qm.base.core.auth.exception;

import com.qm.base.core.code.ICode;
import com.qm.base.core.http.HttpStatus;

/**
 * 认证模块错误码枚举
 * 所有认证相关的业务错误码都在此集中定义
 * <p>
 * 提供根据错误码获取对应枚举实例的方法。
 */
public enum AuthError implements ICode {
    // 账号相关
    AUTH_ACCOUNT_EXIST("AUTH_ACCOUNT_EXIST", "账号已存在", HttpStatus.BAD_REQUEST),
    AUTH_ACCOUNT_NOT_EXIST("AUTH_ACCOUNT_NOT_EXIST", "账号不存在", HttpStatus.BAD_REQUEST),
    AUTH_ACCOUNT_EMPTY("AUTH_ACCOUNT_EMPTY", "账号为空", HttpStatus.BAD_REQUEST),
    AUTH_ACCOUNT_INVALID("AUTH_ACCOUNT_INVALID", "账号格式错误", HttpStatus.BAD_REQUEST),
    AUTH_ACCOUNT_DISABLED("AUTH_ACCOUNT_DISABLED", "账号被禁用", HttpStatus.FORBIDDEN),
    AUTH_ACCOUNT_LOCKED("AUTH_ACCOUNT_LOCKED", "账号被锁定", HttpStatus.FORBIDDEN),
    // 密码相关
    AUTH_CREDENTIAL_EMPTY("AUTH_CREDENTIAL_EMPTY", "密码为空", HttpStatus.BAD_REQUEST),
    AUTH_CREDENTIAL_INVALID("AUTH_CREDENTIAL_INVALID", "密码格式错误", HttpStatus.BAD_REQUEST),
    AUTH_CREDENTIAL_NOT_SET("AUTH_CREDENTIAL_NOT_SET", "密码未设置", HttpStatus.BAD_REQUEST),
    // 手机号码相关
    AUTH_PHONE_NUMBER_EMPTY("AUTH_PHONE_NUMBER_EMPTY", "手机号码为空", HttpStatus.BAD_REQUEST),
    AUTH_PHONE_NUMBER_INVALID("AUTH_PHONE_NUMBER_INVALID", "手机号码格式错误", HttpStatus.BAD_REQUEST),
    // 邮箱相关
    AUTH_EMAIL_EMPTY("AUTH_EMAIL_EMPTY", "邮箱为空", HttpStatus.BAD_REQUEST),
    AUTH_EMAIL_INVALID("AUTH_EMAIL_INVALID", "邮箱格式错误", HttpStatus.BAD_REQUEST),
    // 验证码相关
    AUTH_VERIFY_CODE_EMPTY("AUTH_VERIFY_CODE_EMPTY", "验证码为空", HttpStatus.BAD_REQUEST),
    AUTH_VERIFY_CODE_ERROR("AUTH_VERIFY_CODE_ERROR", "验证码错误", HttpStatus.BAD_REQUEST),
    //注册相关
    AUTH_ACCOUNT_TYPE_INVALID("AUTH_ACCOUNT_TYPE_INVALID", " 账号类型错误", HttpStatus.BAD_REQUEST),
    AUTH_ONLY_PHONE_NUMBER_OR_EMAIL("AUTH_ONLY_PHONE_NUMBER_OR_EMAIL", "仅支持手机号码或邮箱", HttpStatus.BAD_REQUEST),
    // 登录相关
    AUTH_DEVICE_ID_EMPTY("AUTH_DEVICE_ID_EMPTY", "设备ID为空", HttpStatus.BAD_REQUEST),
    // 系统相关
    AUTH_ERROR("AUTH_ERROR", "认证模块异常", HttpStatus.INTERNAL_SERVER_ERROR),
    AUTH_REQUEST_ERROR("AUTH_REQUEST_ERROR", "请求参数错误", HttpStatus.BAD_REQUEST),
    // Token 相关
    AUTH_TOKEN_EMPTY("AUTH_TOKEN_EMPTY", "令牌为空", HttpStatus.BAD_REQUEST),
    AUTH_TOKEN_INVALID("AUTH_TOKEN_INVALID", "令牌无效", HttpStatus.BAD_REQUEST),
    AUTH_TOKEN_EXPIRED("AUTH_TOKEN_EXPIRED", "令牌过期", HttpStatus.BAD_REQUEST),
    AUTH_TOKEN_MISSING("AUTH_TOKEN_MISSING", "缺少认证令牌", HttpStatus.BAD_REQUEST),
    AUTH_LOGIN_FAILED("AUTH_LOGIN_FAILED", "用户名或密码错误", HttpStatus.BAD_REQUEST),
    AUTH_UNAUTHORIZED("AUTH_UNAUTHORIZED", "未授权访问", HttpStatus.UNAUTHORIZED),
    AUTH_LOGIN_TYPE_ERROR("AUTH_LOGIN_TYPE_ERROR", "登录类型错误", HttpStatus.BAD_REQUEST),
    // 密码重置相关
    AUTH_EMAIL_OR_PHONE_EMPTY("AUTH_EMAIL_OR_PHONE_EMPTY", "邮箱或手机号码为空", HttpStatus.BAD_REQUEST),
    AUTH_EMAIL_OR_PHONE_NOT_EXIST("AUTH_EMAIL_OR_PHONE_NOT_EXIST", "邮箱或手机号码未绑定", HttpStatus.BAD_REQUEST),
    AUTH_EMAIL_OR_PHONE_INVALID("AUTH_EMAIL_OR_PHONE_INVALID", "邮箱或手机号码格式错误", HttpStatus.BAD_REQUEST),
    // 第三方登录相关
    AUTH_THIRD_PLATFORM_NOT_EXIST("AUTH_THIRD_PLATFORM_NOT_EXIST", "第三方登录类型不存在", HttpStatus.BAD_REQUEST),
    AUTH_THIRD_LOGIN_INVALID("AUTH_THIRD_LOGIN_INVALID", "第三方登录失败", HttpStatus.UNAUTHORIZED),
    AUTH_THIRD_LOGIN_CALLBACK_ERROR("AUTH_THIRD_LOGIN_CALLBACK_ERROR", "第三方登录回调失败", HttpStatus.INTERNAL_SERVER_ERROR),
    AUTH_THIRD_LOGIN_STATE_EMPTY("AUTH_THIRD_LOGIN_STATE_EMPTY", "第三方登录state字段缺失", HttpStatus.BAD_REQUEST),
    AUTH_THIRD_ERROR("AUTH_THIRD_ERROR", "第三方登录异常", HttpStatus.INTERNAL_SERVER_ERROR),
    // 第三方登录-微信相关
    AUTH_THIRD_WECHAT_APPID_EMPTY("AUTH_THIRD_WECHAT_APPID_EMPTY", "微信 appId 为空", HttpStatus.INTERNAL_SERVER_ERROR),
    AUTH_THIRD_WECHAT_APP_SECRET_EMPTY("AUTH_THIRD_WECHAT_APP_SECRET_EMPTY", "微信 appSecret 为空", HttpStatus.INTERNAL_SERVER_ERROR),
    AUTH_THIRD_WECHAT_REDIRECT_URI_EMPTY("AUTH_THIRD_WECHAT_REDIRECT_URI_EMPTY", "微信 redirect_uri 为空", HttpStatus.INTERNAL_SERVER_ERROR),
    AUTH_THIRD_WECHAT_CODE_EMPTY("AUTH_THIRD_WECHAT_CODE_EMPTY", "微信 code 缺失", HttpStatus.INTERNAL_SERVER_ERROR),
    AUTH_THIRD_URI_INVALID("AUTH_THIRD_URI_INVALID", "uri格式错误", HttpStatus.INTERNAL_SERVER_ERROR),
    ;
    // 错误码
    private final String code;
    // 错误信息
    private final String message;
    // 状态码
    private final HttpStatus status;

    AuthError(String code, String message, HttpStatus status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }

    /**
     * 获取错误码
     */
    @Override
    public String getCode() {
        return code;
    }

    /**
     * 获取错误信息
     */
    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return code + ": " + message;
    }
}