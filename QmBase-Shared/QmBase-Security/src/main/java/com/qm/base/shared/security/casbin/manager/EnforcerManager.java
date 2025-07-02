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
    private final ScopeWatcherManager watcherManager;
    private final PolicyLoader policyLoader;

    public EnforcerManager(ScopeWatcherManager watcherManager,
                           PolicyLoader policyLoader) {
        this.watcherManager = watcherManager;
        this.policyLoader = policyLoader;
    }

    /**
     * 获取指定 domain 的 Enforcer（带缓存）。
     *
     * @param scope    权限域
     * @param modelPath 模型路径
     * @param watcher   Watcher 实例
     */
    public Enforcer getEnforcer(String scope, String modelPath, Watcher watcher) {
        Assert.hasText(scope, "domain must not be empty");
        Assert.hasText(modelPath, "modelPath must not be empty");
        return enforcerCache.computeIfAbsent(scope, sco -> {
            Enforcer enforcer = new Enforcer(modelPath, new CasbinPolicyAdapter(policyLoader, sco));
            enforcer.setWatcher(watcher);
            if (watcher != null) {
                watcher.setUpdateCallback(enforcer::loadPolicy);
                watcherManager.register(sco, watcher);
            }
            return enforcer;
        });
    }
}