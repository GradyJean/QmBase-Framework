package com.example.it.security.infrastructure.casbin;

import com.qm.base.shared.security.casbin.storage.PolicyLoader;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ExamplePolicyLoader implements PolicyLoader {

    /**
     * p, admin, *, *
     * g, alice, admin
     */
    @Override
    public List<List<String>> loadPolicies(String domain) {
        List<List<String>> policies = new ArrayList<>();
        List<String> policyNames1 = new ArrayList<>();
        policyNames1.add("p");
        policyNames1.add("admin");
        policyNames1.add("*");
        policyNames1.add("*");
        List<String> policyNames2 = new ArrayList<>();
        policyNames2.add("g");
        policyNames2.add("593718197424578560");
        policyNames2.add("admin");
        policies.add(policyNames1);
        policies.add(policyNames2);
        return policies;
    }
}
