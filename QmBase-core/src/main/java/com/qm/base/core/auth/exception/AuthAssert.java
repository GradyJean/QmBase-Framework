package com.qm.base.core.auth.exception;


import com.qm.base.core.exception.AbstractAssert;

/**
 * 提供认证相关的断言方法。
 * 断言失败时会抛出自定义 AuthException 异常，统一错误码结构。
 * 成功时返回原值，便于链式调用和简化业务逻辑。
 */
public class AuthAssert extends AbstractAssert<AuthException, AuthError> {
    public static final AuthAssert INSTANCE = new AuthAssert();

    private AuthAssert() {

    }

    @Override
    public AuthException createException(AuthError authError) {
        return new AuthException(authError);
    }
}
