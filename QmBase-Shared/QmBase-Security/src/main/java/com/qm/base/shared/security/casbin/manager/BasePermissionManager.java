package com.qm.base.shared.security.casbin.manager;

import com.qm.base.shared.security.casbin.watcher.LocalPolicyWatcher;
import org.casbin.jcasbin.main.Enforcer;
import org.casbin.jcasbin.model.Assertion;
import org.casbin.jcasbin.model.Model;
import org.casbin.jcasbin.persist.Adapter;
import org.casbin.jcasbin.persist.Watcher;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public abstract class BasePermissionManager implements SmartInitializingSingleton {

    private final String scope;
    private final String modelPath;
    private volatile Enforcer enforcer;
    private Watcher watcher;

    public BasePermissionManager(String scope, String modelPath) {
        Assert.hasText(scope, "scope must not be empty");
        Assert.hasText(modelPath, "modelPath must not be empty");
        this.scope = scope;
        this.modelPath = modelPath;
    }

    public BasePermissionManager(String modelPath) {
        Assert.hasText(modelPath, "modelPath must not be empty");
        this.scope = "*";
        this.modelPath = modelPath;
    }

    public final String getScope() {
        return scope;
    }

    public final void setWatcher(Watcher watcher) {
        this.watcher = watcher;
        Enforcer current = enforcer;
        if (current != null) {
            Watcher effectiveWatcher = resolveWatcher();
            effectiveWatcher.setUpdateCallback(current::loadPolicy);
            current.setWatcher(effectiveWatcher);
        }
    }

    public final boolean enforce(Object... requestParams) {
        return getEnforcer().enforce(requestParams);
    }

    public final boolean addPolicy(String... rule) {
        return getEnforcer().addPolicy(rule);
    }

    public final boolean removePolicy(String... rule) {
        return getEnforcer().removePolicy(rule);
    }

    public final boolean addGroupingPolicy(String... rule) {
        return getEnforcer().addGroupingPolicy(rule);
    }

    public final boolean removeGroupingPolicy(String... rule) {
        return getEnforcer().removeGroupingPolicy(rule);
    }

    public final void reloadPolicy() {
        getEnforcer().loadPolicy();
    }

    public final void notifyPolicyUpdate() {
        resolveWatcher().update();
    }

    public final Enforcer getEnforcer() {
        Enforcer current = enforcer;
        if (current != null) {
            return current;
        }
        synchronized (this) {
            if (enforcer == null) {
                String absoluteModelPath = formPathResource(modelPath);
                Enforcer created = new Enforcer(absoluteModelPath, new ManagerPolicyAdapter());
                created.enableAutoSave(true);
                Watcher effectiveWatcher = resolveWatcher();
                effectiveWatcher.setUpdateCallback(created::loadPolicy);
                created.setWatcher(effectiveWatcher);
                enforcer = created;
            }
            return enforcer;
        }
    }

    @Override
    public void afterSingletonsInstantiated() {
        getEnforcer();
    }

    protected abstract List<List<String>> loadPolicies();

    protected abstract boolean savePolicyRule(List<String> rule);

    protected abstract boolean deletePolicyRule(List<String> rule);

    protected abstract boolean saveGroupingPolicyRule(List<String> rule);

    protected abstract boolean deleteGroupingPolicyRule(List<String> rule);

    protected boolean deleteFilteredPolicy(String ptype, int fieldIndex, String... fieldValues) {
        return false;
    }

    private Watcher resolveWatcher() {
        if (watcher == null) {
            watcher = new LocalPolicyWatcher();
        }
        return watcher;
    }

    private String formPathResource(String path) {
        try {
            ClassPathResource resource = new ClassPathResource(path);
            File tempFile = File.createTempFile("casbin_", ".conf");
            tempFile.deleteOnExit();
            Files.copy(resource.getInputStream(), tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            return tempFile.getAbsolutePath();
        } catch (IOException e) {
            throw new RuntimeException("无法加载 Casbin 配置文件: " + path, e);
        }
    }

    private final class ManagerPolicyAdapter implements Adapter {
        @Override
        public void loadPolicy(Model model) {
            List<List<String>> policies = BasePermissionManager.this.loadPolicies();
            if (policies == null || policies.isEmpty()) {
                return;
            }
            for (List<String> policy : policies) {
                if (policy == null || policy.isEmpty()) {
                    continue;
                }
                String ptype = policy.getFirst();
                if (!StringUtils.hasText(ptype)) {
                    continue;
                }
                String sec = ptype.substring(0, 1);
                Map<String, Assertion> astMap = model.model.get(sec);
                if (astMap == null) {
                    continue;
                }
                Assertion assertion = astMap.get(ptype);
                if (assertion == null) {
                    continue;
                }
                List<String> rule = policy.subList(1, policy.size());
                assertion.policy.add(rule);
                assertion.policyIndex.put(policy.toString(), assertion.policy.size() - 1);
            }
        }

        @Override
        public void savePolicy(Model model) {
            throw new UnsupportedOperationException("savePolicy is not supported, use incremental CRUD methods.");
        }

        @Override
        public void addPolicy(String sec, String ptype, List<String> rule) {
            boolean success = switch (ptype) {
                case "p" -> BasePermissionManager.this.savePolicyRule(rule);
                case "g" -> BasePermissionManager.this.saveGroupingPolicyRule(rule);
                default -> throw new IllegalArgumentException("Unsupported ptype: " + ptype);
            };
            if (!success) {
                throw new IllegalStateException("Persist policy failed: " + ptype + " " + Arrays.toString(rule.toArray()));
            }
        }

        @Override
        public void removePolicy(String sec, String ptype, List<String> rule) {
            boolean success = switch (ptype) {
                case "p" -> BasePermissionManager.this.deletePolicyRule(rule);
                case "g" -> BasePermissionManager.this.deleteGroupingPolicyRule(rule);
                default -> throw new IllegalArgumentException("Unsupported ptype: " + ptype);
            };
            if (!success) {
                throw new IllegalStateException("Delete policy failed: " + ptype + " " + Arrays.toString(rule.toArray()));
            }
        }

        @Override
        public void removeFilteredPolicy(String sec, String ptype, int fieldIndex, String... fieldValues) {
            boolean success = BasePermissionManager.this.deleteFilteredPolicy(ptype, fieldIndex, fieldValues);
            if (!success) {
                throw new IllegalStateException("Delete filtered policy failed: " + ptype);
            }
        }
    }
}
