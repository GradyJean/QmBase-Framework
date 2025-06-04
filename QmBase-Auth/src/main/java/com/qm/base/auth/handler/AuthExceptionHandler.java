package com.qm.base.auth.handler;

import com.qm.base.auth.exception.AuthException;
import com.qm.base.shared.base.model.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器，用于捕获并统一格式化异常响应
 */
@RestControllerAdvice
public class AuthExceptionHandler {

    /**
     * 捕获认证模块异常
     */
    @ExceptionHandler(AuthException.class)
    public Result<String> handleAuthException(AuthException ex) {
        return Result.FAIL(ex.getCode(), ex.getMessage());
    }
}