package com.qm.base.shared.security.casbin.adapter;

import com.qm.base.shared.security.casbin.storage.PolicyLoader;
import org.casbin.jcasbin.model.Assertion;
import org.casbin.jcasbin.model.Model;
import org.casbin.jcasbin.persist.Adapter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * CasbinPolicyAdapter 是一个 Casbin 适配器，用于将策略加载和保存操作与自定义的 PolicyLoader 接口对接。
 * <p>
 * 它实现了 Casbin 的 Adapter 接口，并使用 PolicyLoader 加载策略数据。
 * 可以指定权限域标识，默认为 "default"。
 */
public class CasbinPolicyAdapter implements Adapter {

    /**
     * CasbinPolicyAdapter 是一个适配器类，
     * 用于将 Casbin 的策略加载和保存操作与自定义的 PolicyLoader 接口对接。
     * <p>
     * - 实现了 Adapter 接口，提供了 loadPolicy、savePolicy 等方法。
     * - 使用 PolicyLoader 加载策略数据。
     */
    private final PolicyLoader policyLoader;

    /**
     * 权限域标识，默认为 "default"。
     * 可用于区分不同的权限域，如租户、项目等。
     */
    private final String domain;

    public CasbinPolicyAdapter(PolicyLoader policyLoader, String domain) {
        this.policyLoader = policyLoader;
        this.domain = domain;
    }

    /**
     * 默认构造函数，使用 "default" 作为权限域标识。
     * 适用于不需要特定权限域的场景。
     *
     * @param policyLoader 用于加载策略的 PolicyLoader 实例
     */
    public CasbinPolicyAdapter(PolicyLoader policyLoader) {
        this(policyLoader, "default");
    }

    @Override
    public void loadPolicy(Model model) {
        List<List<String>> policies = policyLoader.loadPolicies(domain);
        if (policies == null || policies.isEmpty()) {
            return;
        }
        for (List<String> policy : policies) {
            if (policy.isEmpty()) {
                continue;
            }
            String key = policy.getFirst();
            String sec = key.substring(0, 1);
            Map<String, Assertion> astMap = model.model.get(sec);
            if (astMap == null) {
                continue;
            }
            Assertion ast = astMap.get(key);
            if (ast == null) {
                continue;
            }
            ast.policy.add(policy.subList(1, policy.size()));
            ast.policyIndex.put(policy.toString(), ast.policy.size() - 1);
        }
    }


    @Override
    public void savePolicy(Model model) {

    }

    @Override
    public void addPolicy(String s, String s1, List<String> list) {

    }

    @Override
    public void removePolicy(String s, String s1, List<String> list) {

    }

    @Override
    public void removeFilteredPolicy(String s, String s1, int i, String... strings) {

    }
}
