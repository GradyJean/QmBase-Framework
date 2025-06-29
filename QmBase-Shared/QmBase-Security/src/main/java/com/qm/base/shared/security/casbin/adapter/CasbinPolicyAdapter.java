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

    private final PolicyLoader policyLoader;

    public CasbinPolicyAdapter(PolicyLoader policyLoader) {
        this.policyLoader = policyLoader;
    }

    @Override
    public void loadPolicy(Model model) {
        List<List<String>> policies = policyLoader.loadPolicies();
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
