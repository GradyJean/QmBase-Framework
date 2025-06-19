package com.qm.base.shared.cache.core.aspect;

import com.qm.base.shared.cache.api.QmCache;
import com.qm.base.shared.cache.api.QmCacheManager;
import com.qm.base.shared.cache.core.annotation.QmCacheable;
import com.qm.base.shared.cache.core.key.QmKeyGenerator;
import com.qm.base.shared.cache.core.support.CacheValueUtil;
import com.qm.base.shared.cache.core.support.QmCacheContext;
import com.qm.base.shared.cache.logging.QmCacheLogger;
import com.qm.base.shared.cache.metrics.QmCacheMetricsRecorder;
import com.qm.base.shared.cache.model.NullValue;
import jakarta.annotation.Resource;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class QmCacheAspect {
    @Resource
    private QmKeyGenerator keyGenerator;
    private final ExpressionParser parser = new SpelExpressionParser();
    private final DefaultParameterNameDiscoverer nameDiscoverer = new DefaultParameterNameDiscoverer();

    @Resource
    private QmCacheManager qmCacheManager;

    @Around("@annotation(qmCacheable)")
    public Object around(ProceedingJoinPoint pjp, QmCacheable qmCacheable) throws Throwable {
        Method method = ((MethodSignature) pjp.getSignature()).getMethod();
        Object[] args = pjp.getArgs();

        // 1. 构造上下文用于 SpEL 解析，确保参数绑定完整
        StandardEvaluationContext context = new MethodBasedEvaluationContext(pjp.getTarget(), method, args, nameDiscoverer);

        // 2. 解析 key 与 condition
        String cacheKey;
        if (!qmCacheable.key().isEmpty()) {
            cacheKey = parser.parseExpression(qmCacheable.key()).getValue(context, String.class);
        } else {
            cacheKey = keyGenerator.generate(pjp.getTarget(), method, args);
        }
        // 安全解析 condition 表达式
        boolean conditionMatch = true;
        if (!qmCacheable.condition().isBlank()) {
            Boolean evaluated = parser.parseExpression(qmCacheable.condition()).getValue(context, Boolean.class);
            conditionMatch = Boolean.TRUE.equals(evaluated);
        }

        long start = System.currentTimeMillis();
        boolean hit = false;
        Object result;
        try {
            if (!conditionMatch) {
                result = pjp.proceed(); // 不满足缓存条件，直接执行原方法
                // 设置上下文
                QmCacheContext.set(new QmCacheContext.Context(
                        qmCacheable.name(), cacheKey, false, result, start
                ));
                return result;
            }

            QmCache cache = qmCacheManager.getCache(qmCacheable.name());
            Object cacheResult = cache.get(cacheKey);
            boolean isCachedNull = cacheResult instanceof NullValue;
            hit = cacheResult != null;

            if (hit) {
                result = isCachedNull ? null : cacheResult;
            } else {
                result = pjp.proceed();
                // 设置上下文变量，保证 SpEL 可用 result
                context.setVariable("result", result);
                // 安全解析 unless 表达式
                boolean unlessMatch = false;
                if (!qmCacheable.unless().isBlank()) {
                    Boolean evaluated = parser.parseExpression(qmCacheable.unless()).getValue(context, Boolean.class);
                    unlessMatch = Boolean.TRUE.equals(evaluated);
                }
                if (!unlessMatch && (result != null || qmCacheable.cacheNull())) {
                    long ttl = qmCacheable.ttl() > 0 ? qmCacheable.ttl() : 300;
                    cache.put(cacheKey, CacheValueUtil.wrap(result), ttl);
                }
            }

            // 设置上下文
            QmCacheContext.set(new QmCacheContext.Context(
                    qmCacheable.name(), cacheKey, hit, result, start
            ));

            return result;
        } finally {
            QmCacheLogger.log(QmCacheContext.get());
            QmCacheMetricsRecorder.record(QmCacheContext.get());
            QmCacheContext.clear();
        }
    }
}