package com.qm.base.shared.security.exception;

import com.qm.base.core.code.ICode;
import com.qm.base.core.http.HttpStatus;

public enum SecurityError implements ICode {
    // 账号相关
    SECURITY_ERROR("SECURITY_ERROR", "鉴权模块异常", HttpStatus.INTERNAL_SERVER_ERROR),
    // Token 相关
    SECURITY_TOKEN_EMPTY("AUTH_TOKEN_EMPTY", "令牌为空", HttpStatus.BAD_REQUEST),
    SECURITY_TOKEN_INVALID("AUTH_TOKEN_INVALID", "令牌无效", HttpStatus.BAD_REQUEST),
    SECURITY_TOKEN_EXPIRED("AUTH_TOKEN_EXPIRED", "令牌过期", HttpStatus.BAD_REQUEST),
    SECURITY_TOKEN_MISSING("AUTH_TOKEN_MISSING", "缺少认证令牌", HttpStatus.BAD_REQUEST),
    SECURITY_UNAUTHORIZED("AUTH_UNAUTHORIZED", "未授权访问", HttpStatus.UNAUTHORIZED),
    ;
    // 错误码
    private final String code;
    // 错误信息
    private final String message;
    // 状态码
    private final HttpStatus status;

    SecurityError(String code, String message, HttpStatus status) {
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
