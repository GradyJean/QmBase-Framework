package com.qm.base.shared.logger.core;

import java.time.Duration;
import java.time.Instant;

/**
 * 标准日志记录对象，用于统一封装日志结构信息。
 */
public class LogRecord {

    /**
     * 执行类名
     */
    private String className;

    /**
     * 执行方法名
     */
    private String methodName;

    /**
     * 方法参数
     */
    private String args;

    /**
     * 返回结果
     */
    private String result;

    /**
     * 异常信息（如有）
     */
    private String exception;

    /**
     * 执行开始时间
     */
    private Instant startTime;

    /**
     * 执行结束时间
     */
    private Instant endTime;

    /**
     * 耗时（毫秒）
     */
    private long durationMillis;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getArgs() {
        return args;
    }

    public void setArgs(String args) {
        this.args = args;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getEndTime() {
        return endTime;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
        if (this.startTime != null && endTime != null) {
            this.durationMillis = Duration.between(this.startTime, endTime).toMillis();
        }
    }

    public long getDurationMillis() {
        return durationMillis;
    }
}
