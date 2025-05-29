package com.qm.base.shared.base.exception;

import com.qm.base.shared.base.model.Result;
import com.qm.base.shared.base.model.ResultCode;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理业务异常
     */
    @ExceptionHandler(BizException.class)
    public Result<?> handleBizException(BizException ex) {
        return Result.FAIL(ex.getCode(), ex.getMessage());
    }

    /**
     * 处理未知异常
     */
    @ExceptionHandler(Exception.class)
    public Result<?> handleException(Exception ex) {
        return Result.FAIL(ResultCode.FAIL, ex.getMessage());
    }
}
