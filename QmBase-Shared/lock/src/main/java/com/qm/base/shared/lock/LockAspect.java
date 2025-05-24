package com.qm.base.shared.lock;

import com.qm.base.shared.lock.annotation.Lock;
import com.qm.base.shared.lock.distribute.DistributedLockService;
import com.qm.base.shared.lock.enums.LockType;
import com.qm.base.shared.lock.local.LocalLockService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * 锁注解切面类，支持根据注解配置自动选择本地锁或分布式锁执行目标方法
 */
@Slf4j
@Aspect
@Component
public class LockAspect {

    @Autowired
    private LocalLockService localLockService;

    @Autowired
    private DistributedLockService distributedLockService;

    /**
     * 环绕通知：拦截带有 @Lock 注解的方法
     *
     * @param pjp     切点信息（方法调用上下文）
     * @param lockAnn 注解对象
     * @return 方法执行结果
     * @throws Throwable 方法内部可能抛出的任何异常
     */
    @Around("@annotation(lockAnn)")
    public Object around(ProceedingJoinPoint pjp, Lock lockAnn) throws Throwable {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        String rawKey = lockAnn.key();
        ExpressionParser parser = new SpelExpressionParser();
        StandardEvaluationContext context = new StandardEvaluationContext();
        String[] paramNames = signature.getParameterNames();
        Object[] args = pjp.getArgs();
        for (int i = 0; i < paramNames.length; i++) {
            context.setVariable(paramNames[i], args[i]);
        }
        String key = parser.parseExpression(rawKey).getValue(context, String.class);

        long expireMillis = lockAnn.expire();
        long waitMillis = lockAnn.waitMillis();
        LockType type = lockAnn.type();
        String requestId = UUID.randomUUID().toString();

        // 根据锁类型选择实现类
        LockService lockService = (type == LockType.DISTRIBUTED)
                ? distributedLockService
                : localLockService;

        // 执行目标方法并自动释放锁
        return lockService.execute(key, requestId, () -> {
            try {
                return pjp.proceed();
            } catch (Throwable e) {
                throw new RuntimeException("Locked method execution failed", e);
            }
        }, expireMillis, waitMillis);
    }
}