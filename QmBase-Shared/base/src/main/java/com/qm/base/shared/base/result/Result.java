package com.qm.base.shared.base.result;

import com.qm.base.shared.base.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 通用 API 返回包装类。
 *
 * @param <T> 响应数据类型
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {
    /**
     * 请求是否成功标识，true 表示成功，false 表示失败
     */
    private boolean success;

    /**
     * 自定义业务状态码（如：10000 表示成功，14001 表示未登录）
     */
    private String code;

    /**
     * 错误或成功提示信息，通常用于前端提示或日志记录
     */
    private String message;

    /**
     * 响应的实际数据内容，支持任意类型
     */
    private T data;

//    public Result() {
//    }
//
//    public Result(boolean success, String code, String message, T data) {
//        this.success = success;
//        this.code = code;
//        this.message = message;
//        this.data = data;
//    }

    public static <T> Result<T> SUCCESS() {
        return new Result<>(true, ResultCode.SUCCESS, ResultCode.SUCCESS_MSG, null);
    }

    public static <T> Result<T> SUCCESS(T data) {
        return new Result<>(true, ResultCode.SUCCESS, ResultCode.SUCCESS_MSG, data);
    }

    public static <T> Result<T> SUCCESS(T data, String code, String message) {
        return new Result<>(true, code, message, data);
    }

    public static <T> Result<T> FAIL() {
        return new Result<>(false, ResultCode.FAIL, ResultCode.FAIL_MSG, null);
    }

    public static <T> Result<T> FAIL(String code, String message) {
        return new Result<>(false, code, message, null);
    }

    public static <T> Result<T> FAIL(T data) {
        return new Result<>(false, ResultCode.FAIL, ResultCode.FAIL_MSG, data);
    }

    public static <T> Result<T> FAIL(ErrorCode errorCode) {
        return new Result<>(false, errorCode.name(), errorCode.getMessage(), null);
    }

    public static <T> Result<T> from(boolean success, String code, String message, T data) {
        return new Result<>(success, code, message, data);
    }

}