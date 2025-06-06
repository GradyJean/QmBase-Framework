package com.qm.base.core.code;

import com.qm.base.core.http.HttpStatus;

/**
 * 状态码接口（脱离任何框架）
 */
public interface ICode {

    /**
     * 业务错误码（用于前端识别，如 AUTH_TOKEN_EXPIRED）
     */
    String getCode();

    /**
     * 错误消息（用于展示给用户）
     */
    String getMessage();

    /**
     * 建议的 HTTP 状态码（int 类型，避免依赖 Spring）
     */
    HttpStatus getStatus();
}
