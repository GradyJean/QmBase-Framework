package com.qm.base.shared.logger;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.util.StopWatch;

import java.lang.reflect.Method;
import java.util.Arrays;

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

    @Around("@annotation(com.qm.base.shared.logger.Log)")
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

        if (logParams && localLogParams) {
            logAt(localLevel, "[{}-{}] 方法参数: {}", module, action, Arrays.toString(joinPoint.getArgs()));
        }

        Object result;
        try {
            result = joinPoint.proceed();
        } finally {
            watch.stop();
        }

        if (logResult && localLogResult) {
            logAt(localLevel, "[{}-{}] 方法返回: {}", module, action, result);
        }

        logAt(localLevel, "[{}-{}] 方法耗时: {} ms", module, action, watch.getTotalTimeMillis());
        return result;
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