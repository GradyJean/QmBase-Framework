package com.qm.base.shared.logger.aspect;

import com.qm.base.shared.logger.annotation.Log;
import com.qm.base.shared.logger.core.LogFormatter;
import com.qm.base.shared.logger.enums.LogLevel;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.util.StopWatch;

import java.lang.reflect.Method;

/**
 * 通用日志切面，自动打印 @Log 标注方法的入参/出参/耗时信息
 */
@Slf4j
@Aspect
public class LogAspect {

    @Setter
    private boolean logParams = true;

    @Setter
    private boolean logResult = true;

    @Setter
    private LogLevel level = LogLevel.INFO;

    @Around("@annotation(com.qm.base.shared.logger.annotation.Log)")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        Log logAnno = method.getAnnotation(Log.class);

        String methodName = method.getDeclaringClass().getSimpleName() + "." + method.getName();
        String module = logAnno.module();
        String action = logAnno.action();

        boolean localLogParams = logAnno.logParams();
        boolean localLogResult = logAnno.logResult();
        LogLevel localLevel = logAnno.level();

        StopWatch watch = new StopWatch();
        watch.start();

        // 构造方法名展示
        String methodDisplay = (!module.isBlank() || !action.isBlank())
                ? String.format("[%s-%s]", module, action)
                : "[" + methodName + "]";

        String traceId = java.util.UUID.randomUUID().toString().replace("-", "").substring(0, 16);

        Object result = null;
        Throwable throwable = null;

        try {
            result = joinPoint.proceed();
            return result;
        } catch (Throwable ex) {
            throwable = ex;
            throw ex;
        } finally {
            watch.stop();

            logAt(
                    localLevel,
                    "{} | traceId={} | 参数: {} | 返回: {} | 耗时: {} ms | 异常: {}",
                    methodDisplay,
                    traceId,
                    localLogParams ? LogFormatter.formatArgs(joinPoint.getArgs()) : "-",
                    localLogResult ? LogFormatter.formatResult(result) : "-",
                    watch.getTotalTimeMillis(),
                    throwable != null ? LogFormatter.formatThrowable(throwable) : "-"
            );
        }
    }

    private void logAt(LogLevel level, String format, Object... args) {
        switch (level) {
            case TRACE -> log.trace(format, args);
            case DEBUG -> log.debug(format, args);
            case INFO -> log.info(format, args);
            case WARN -> log.warn(format, args);
            case ERROR -> log.error(format, args);
        }
    }
}