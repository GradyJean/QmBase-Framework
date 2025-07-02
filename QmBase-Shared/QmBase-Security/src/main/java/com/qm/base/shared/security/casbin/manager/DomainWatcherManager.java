package com.qm.base.shared.security.casbin.manager;

import org.casbin.jcasbin.persist.Watcher;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 管理不同 domain 对应的 Watcher。
 * 默认使用本地 Watcher 实现，可替换为 Redis/Zookeeper 等分布式 Watcher。
 */
@Component
public class DomainWatcherManager {

    private final Map<String, Watcher> watcherMap = new ConcurrentHashMap<>();

    /**
     * 注册某个 domain 对应的 Watcher 实例。
     *
     * @param domain  域标识
     * @param watcher Watcher 实例
     */
    void register(String domain, Watcher watcher) {
        watcherMap.put(domain, watcher);
    }

    /**
     * 触发某个 domain 的 Watcher，通知权限变更。
     *
     * @param domain 域标识
     */
    void notifyUpdate(String domain) {
        Watcher watcher = watcherMap.get(domain);
        if (watcher != null) {
            watcher.update();
        }
    }
}
