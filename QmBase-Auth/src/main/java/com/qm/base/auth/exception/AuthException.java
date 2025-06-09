package com.qm.base.auth.exception;

import com.qm.base.core.exception.QmException;

/**
 * AuthException 是认证模块的业务异常类，用于封装登录、登出、权限校验、Token 校验等相关错误。
 * 通常与 {@link AuthError} 枚举配合使用，由断言类 {@code AuthAssert} 抛出。
 */
public class AuthException extends QmException {

    public AuthException(AuthError authError) {
        super(authError);
    }
}