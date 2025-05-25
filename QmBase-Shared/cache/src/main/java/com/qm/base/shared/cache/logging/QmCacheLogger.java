package com.qm.base.shared.cache.logging;

import com.qm.base.shared.cache.core.support.QmCacheContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * QmCacheLogger - 缓存日志打印器。
 * 可集成到切面或异步处理模块，记录缓存命中与写入行为。
 */
public class QmCacheLogger {

    private static final Logger log = LoggerFactory.getLogger(QmCacheLogger.class);

    /**
     * 输出缓存操作日志
     */
    public static void log(QmCacheContext.Context ctx) {
        if (ctx == null) {
            return;
        }
        log.info("[QmCache] {} -> {} | hit={} | cost={}ms",
                ctx.getCacheName(),
                ctx.getCacheKey(),
                ctx.isHit(),
                System.currentTimeMillis() - ctx.getStartTime());
    }
}