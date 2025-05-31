package com.qm.base.shared.cache.metrics;

import com.qm.base.shared.cache.core.support.QmCacheContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * QmCacheMetricsRecorder - 缓存指标记录器。
 * 记录缓存调用命中率、耗时等指标，可扩展对接 Prometheus、Micrometer 等监控系统。
 */
public class QmCacheMetricsRecorder {

    private static final Logger log = LoggerFactory.getLogger(QmCacheMetricsRecorder.class);

    /**
     * 记录缓存调用指标信息
     */
    public static void record(QmCacheContext.Context ctx) {
        if (ctx == null) {
            return;
        }
        log.debug("[QmCache-Metrics] cache={} | key={} | hit={} | cost={}ms",
                ctx.getCacheName(),
                ctx.getCacheKey(),
                ctx.isHit(),
                System.currentTimeMillis() - ctx.getStartTime());
    }
}