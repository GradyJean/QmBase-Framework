package com.qm.base.shared.cache.core.support;

/**
 * QmCacheContext - 缓存调用上下文，用于日志埋点与行为跟踪。
 * 基于 ThreadLocal 保存每次缓存操作的信息。
 */
public class QmCacheContext {

    private static final ThreadLocal<Context> HOLDER = new ThreadLocal<>();

    public static void set(Context ctx) {
        HOLDER.set(ctx);
    }

    public static Context get() {
        return HOLDER.get();
    }

    public static void clear() {
        HOLDER.remove();
    }

    /**
     * 内部上下文数据结构。
     */
    public static class Context {
        private final String cacheName;
        private final String cacheKey;
        private final boolean hit;
        private final Object result;
        private final long startTime;

        public Context(String cacheName, String cacheKey, boolean hit, Object result, long startTime) {
            this.cacheName = cacheName;
            this.cacheKey = cacheKey;
            this.hit = hit;
            this.result = result;
            this.startTime = startTime;
        }

        public String getCacheName() {
            return cacheName;
        }

        public String getCacheKey() {
            return cacheKey;
        }

        public boolean isHit() {
            return hit;
        }

        public Object getResult() {
            return result;
        }

        public long getStartTime() {
            return startTime;
        }
    }
}