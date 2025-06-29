package com.qm.base.shared.security.casbin.adapter;

import com.qm.base.shared.security.casbin.storage.PolicyLoader;
import org.casbin.jcasbin.model.Assertion;
import org.casbin.jcasbin.model.Model;
import org.casbin.jcasbin.persist.Adapter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
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
     * 可选的权限域标识，默认为空字符串。
     * 可用于区分不同的权限域，如租户ID、项目ID等。
     */
    private String domain = "default";

    public CasbinPolicyAdapter(PolicyLoader policyLoader) {
        this.policyLoader = policyLoader;
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

    public void setDomain(String domain) {
        this.domain = domain;
    }
}
