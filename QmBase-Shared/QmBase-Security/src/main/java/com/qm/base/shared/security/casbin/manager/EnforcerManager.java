package com.qm.base.shared.security.casbin.manager;

import com.qm.base.shared.security.casbin.adapter.CasbinPolicyAdapter;
import com.qm.base.shared.security.casbin.storage.PolicyLoader;
import org.casbin.jcasbin.main.Enforcer;
import org.casbin.jcasbin.persist.Watcher;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 多 domain 的 Enforcer 管理器，支持缓存、Watcher绑定。
 */
@Component
public class EnforcerManager {

    private final Map<String, Enforcer> enforcerCache = new ConcurrentHashMap<>();
    private final DomainWatcherManager watcherManager;
    private final PolicyLoader policyLoader;

    public EnforcerManager(DomainWatcherManager watcherManager,
                           PolicyLoader policyLoader) {
        this.watcherManager = watcherManager;
        this.policyLoader = policyLoader;
    }

    /**
     * 获取指定 domain 的 Enforcer（带缓存）。
     *
     * @param domain    权限域
     * @param modelPath 模型路径
     * @param watcher   Watcher 实例
     */
    public Enforcer getEnforcer(String domain, String modelPath, Watcher watcher) {
        Assert.hasText(domain, "domain must not be empty");
        Assert.hasText(modelPath, "modelPath must not be empty");
        return enforcerCache.computeIfAbsent(domain, dom -> {
            Enforcer enforcer = new Enforcer(modelPath, new CasbinPolicyAdapter(policyLoader, dom));
            enforcer.setWatcher(watcher);
            if (watcher != null) {
                watcher.setUpdateCallback(enforcer::loadPolicy);
                watcherManager.register(dom, watcher);
            }
            return enforcer;
        });
    }

    /**
     * 获取指定 domain 的 Enforcer（带缓存）。
     *
     * @param domain    权限域
     * @param modelPath 模型路径
     */
    public Enforcer getEnforcer(String domain, String modelPath) {
        return getEnforcer(domain, modelPath, null);
    }

    /**
     * 触发某个 domain 的策略更新（并通知 Watcher）。
     */
    public void notifyPolicyChanged(String domain) {
        watcherManager.notifyUpdate(domain);
    }
}