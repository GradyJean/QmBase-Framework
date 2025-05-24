package com.qm.base.shared.logger;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * QmBase 日志配置项
 *
 * 支持 application.yml 注入
 * 示例：
 * qmbase:
 *   logger:
 *     log-params: true
 *     log-result: false
 *     level: INFO
 */
@Data
@ConfigurationProperties(prefix = "qm.base.logger")
public class QmLoggerProperties {

    /**
     * 是否打印方法参数
     */
    private boolean logParams = true;

    /**
     * 是否打印方法返回值
     */
    private boolean logResult = true;

    /**
     * 日志级别（默认 INFO）
     */
    private LogLevel level = LogLevel.INFO;
}