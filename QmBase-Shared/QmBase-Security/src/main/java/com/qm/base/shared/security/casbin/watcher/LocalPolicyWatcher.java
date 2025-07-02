/**
 * 本地策略更新 Watcher，用于在单体部署或本地环境中监听策略更新。
 * 可被 EnforcerManager 管理并统一触发 notifyUpdate。
 * 如需切换为分布式 Watcher（如 RedisWatcher），可在配置中替换实现。
 */
package com.qm.base.shared.security.casbin.watcher;

import org.casbin.jcasbin.persist.Watcher;

import java.util.function.Consumer;

public class LocalPolicyWatcher implements Watcher {
    /**
     * 策略更新回调函数
     */
    private Runnable updateCallback;


    /**
     * 注册策略更新时的回调函数
     */
    @Override
    public void setUpdateCallback(Runnable runnable) {
        this.updateCallback = runnable;
    }

    @Override
    public void setUpdateCallback(Consumer<String> func) {

    }


    /**
     * 主动触发策略更新事件（本地调用）
     */
    @Override
    public void update() {
        if (updateCallback != null) {
            updateCallback.run();
        }
    }
}
