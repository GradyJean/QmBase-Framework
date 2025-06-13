package com.qm.base.shared.security.exception;


import com.qm.base.core.exception.AbstractAssert;

/**
 * 提供认证相关的断言方法。
 * 断言失败时会抛出自定义 AuthException 异常，统一错误码结构。
 * 成功时返回原值，便于链式调用和简化业务逻辑。
 */
public class SecurityAssert extends AbstractAssert<SecurityException, SecurityError> {
    public static final SecurityAssert INSTANCE = new SecurityAssert();

    private SecurityAssert() {

    }

    @Override
    public SecurityException createException(SecurityError securityError) {
        return new SecurityException(securityError);
    }
}
