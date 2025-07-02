package com.qm.base.shared.security.casbin.manager;

import org.casbin.jcasbin.persist.Watcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 管理不同 domain 对应的 Watcher。
 * 默认使用本地 Watcher 实现，可替换为 Redis/Zookeeper 等分布式 Watcher。
 */
@Component
public class ScopeWatcherManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(ScopeWatcherManager.class);
    private final Map<String, Watcher> watcherMap = new ConcurrentHashMap<>();

    /**
     * 注册某个 domain 对应的 Watcher 实例。
     *
     * @param scope  域标识
     * @param watcher Watcher 实例
     */
    void register(String scope, Watcher watcher) {
        watcherMap.put(scope, watcher);
    }

    /**
     * 触发某个 domain 的 Watcher，通知权限变更。
     *
     * @param scopes 域标识
     */
    public void notifyUpdate(String... scopes) {
        if (scopes == null) {
            return;
        }
        for (String scope : scopes) {
            Watcher watcher = watcherMap.get(scope);
            if (watcher != null) {
                watcher.update();
                LOGGER.info("Watcher updated for domain: {}", scope);
            }
        }
    }
}
