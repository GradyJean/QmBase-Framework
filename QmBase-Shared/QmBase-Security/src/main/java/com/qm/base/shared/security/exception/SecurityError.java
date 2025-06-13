package com.qm.base.shared.security.exception;

import com.qm.base.core.code.ICode;
import com.qm.base.core.http.HttpStatus;

public enum SecurityError implements ICode {
    // token 相关
    SECURITY_TOKEN_ERROR("SECURITY_TOKEN_ERROR", "令牌验证失败", HttpStatus.BAD_REQUEST),
    SECURITY_TOKEN_INVALID("SECURITY_TOKEN_INVALID", " 无效令牌", HttpStatus.BAD_REQUEST),
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
