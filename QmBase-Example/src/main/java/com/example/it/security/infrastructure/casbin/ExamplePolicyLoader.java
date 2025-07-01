package com.example.it.security.infrastructure.casbin;

import com.example.it.security.infrastructure.repository.mapper.ResourcePolicyMapper;
import com.example.it.security.infrastructure.repository.mapper.RoleMappingMapper;
import com.example.it.security.infrastructure.repository.po.ResourcePolicyPO;
import com.example.it.security.infrastructure.repository.po.RoleMappingPO;
import com.qm.base.shared.security.casbin.storage.PolicyLoader;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ExamplePolicyLoader implements PolicyLoader {
    private final RoleMappingMapper roleMappingMapper;
    private final ResourcePolicyMapper resourcePolicyMapper;

    public ExamplePolicyLoader(RoleMappingMapper roleMappingMapper, ResourcePolicyMapper resourcePolicyMapper) {
        this.roleMappingMapper = roleMappingMapper;
        this.resourcePolicyMapper = resourcePolicyMapper;
    }

    /**
     * p, admin, *, *,*
     * g, 593718197424578560, admin,ROLE
     */
    @Override
    public List<List<String>> loadPolicies(String domain) {
        List<List<String>> policies = new ArrayList<>();
        // 查询角色映射和资源策略
        List<RoleMappingPO> roleMappingPOS = roleMappingMapper.listByDomain(domain);
        List<List<String>> gPolicies = roleMappingPOS.stream()
                .map(mapping -> List.of("g",
                        mapping.getUserId(),
                        mapping.getRoleCode(),
                        mapping.getDomain()))
                .toList();
        List<ResourcePolicyPO> resourcePolicyPOS = resourcePolicyMapper.listByDomain(domain);
        // 将资源策略转换为 Casbin 的 p 规则
        List<List<String>> pPolicies = resourcePolicyPOS.stream()
                .map(policy -> List.of("p",
                        policy.getSubject(),
                        policy.getResource(),
                        policy.getAction(),
                        policy.getDomain()))
                .toList();
        // 合并角色映射和资源策略
        policies.addAll(gPolicies);
        policies.addAll(pPolicies);
        return policies;
    }
}
