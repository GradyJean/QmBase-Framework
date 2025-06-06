package com.qm.base.auth.handler;

import com.qm.base.core.auth.exception.AuthException;
import com.qm.base.core.common.model.Result;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Result<String>> handleAuthException(AuthException ex) {
        return ResponseEntity
                .status(ex.getStatus())
                .body(Result.FAIL(ex.getCode(), ex.getMessage()));
    }
}