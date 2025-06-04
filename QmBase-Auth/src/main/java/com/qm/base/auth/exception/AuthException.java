package com.qm.base.auth.exception;

import com.qm.base.auth.model.constant.AuthErrorCodeEnum;
import com.qm.base.core.exception.QmException;

/**
 * AuthException 是认证模块的业务异常类，用于封装登录、注册等相关的错误信息。
 */
public class AuthException extends QmException {

    // 错误码
    private final String code;

    /**
     * 构造函数
     *
     * @param code    错误码
     * @param message 错误信息
     */
    public AuthException(String code, String message) {
        super(message);
        this.code = code;
    }

    public AuthException(AuthErrorCodeEnum authErrorCodeEnum) {
        super(authErrorCodeEnum.getMessage());
        this.code = authErrorCodeEnum.getCode();
    }

    /**
     * 获取错误码
     *
     * @return 错误码
     */
    public String getCode() {
        return code;
    }

    /**
     * 快捷构造方法
     *
     * @param code    错误码
     * @param message 错误信息
     * @return AuthException 实例
     */
    public static AuthException of(String code, String message) {
        return new AuthException(code, message);
    }
}