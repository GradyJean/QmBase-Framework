/**
 * 本地策略更新 Watcher，用于在单体部署或本地环境中监听策略更新。
 * 可由 PermissionManager 持有并触发 update。
 * 如需切换为分布式 Watcher（如 RedisWatcher），可在业务中通过 setWatcher 替换。
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
        this.updateCallback = () -> func.accept("");
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
