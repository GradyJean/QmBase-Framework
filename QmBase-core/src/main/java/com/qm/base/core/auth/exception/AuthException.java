package com.qm.base.core.auth.exception;

import com.qm.base.core.auth.enums.AuthError;
import com.qm.base.core.exception.QmException;

/**
 * AuthException 是认证模块的业务异常类，用于封装登录、注册等相关的错误信息。
 */
public class AuthException extends QmException {

    public AuthException(AuthError authError) {
        super(authError);
    }
}