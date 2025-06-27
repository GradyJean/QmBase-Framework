package com.example.it.common.exception;

import com.qm.base.core.code.ICode;
import com.qm.base.core.http.HttpStatus;
import lombok.Getter;

@Getter
public enum BusinessError implements ICode {
    USER_INSERT_ERROR("USER_INSERT_ERROR", "用户存储失败", HttpStatus.INTERNAL_SERVER_ERROR),
    ;

    private final String code;
    private final String message;
    private final HttpStatus status;

    BusinessError(String code, String message, HttpStatus status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }

    public String toString() {
        return this.code + ": " + this.message;
    }
}
