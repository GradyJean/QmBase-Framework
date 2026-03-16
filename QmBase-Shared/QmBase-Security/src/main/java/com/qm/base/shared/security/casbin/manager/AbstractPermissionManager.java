package com.qm.base.shared.security.casbin.manager;

import com.qm.base.shared.security.casbin.watcher.LocalPolicyWatcher;
import com.qm.base.shared.security.constants.SecurityConstant;
import jakarta.annotation.Resource;
import org.casbin.jcasbin.main.Enforcer;
import org.casbin.jcasbin.model.Model;
import org.casbin.jcasbin.persist.Adapter;
import org.casbin.jcasbin.persist.Watcher;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class AbstractPermissionManager {
    private Enforcer enforcer;
    private final String scope;
    private final String modelPath;
    @Resource
    private EnforcerManager enforcerManager;

    public AbstractPermissionManager(String scope, String modelPath) {
        this.scope = scope;
        this.modelPath = modelPath;
    }

    public AbstractPermissionManager(String modelPath) {
        this.scope = SecurityConstant.SECURITY_SCOPE_DEFAULT;
        this.modelPath = modelPath;
    }

    /**
     * 获取当前使用的 Watcher 实现，默认返回 LocalPolicyWatcher。
     * 业务可通过子类重写该方法来自定义使用 Redis、Zookeeper 等分布式 Watcher 实现。
     *
     * @return Watcher 实例
     */
    protected Watcher getWatcher() {
        // 返回一个新的 LocalPolicyWatcher 实例，用于监听权限变更事件
        return new LocalPolicyWatcher();
    }
}
