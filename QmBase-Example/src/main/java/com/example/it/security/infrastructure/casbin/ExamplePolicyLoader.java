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
        List<List<String>> gPolicies = getGPolicies(roleMappingPOS);
        List<ResourcePolicyPO> resourcePolicyPOS = resourcePolicyMapper.listByDomain(domain);
        // 将资源策略转换为 Casbin 的 p 规则
        List<List<String>> pPolicies = getPPolicies(resourcePolicyPOS);
        // 合并角色映射和资源策略
        policies.addAll(gPolicies);
        policies.addAll(pPolicies);
        return policies;
    }

    /**
     * 获取资源策略的 Casbin p 规则列表
     *
     * @param resourcePolicyPOS 资源策略持久化对象列表
     * @return p 规则列表
     */
    private static List<List<String>> getPPolicies(List<ResourcePolicyPO> resourcePolicyPOS) {
        List<List<String>> pPolicies = new ArrayList<>();
        if (resourcePolicyPOS != null && !resourcePolicyPOS.isEmpty()) {
            for (ResourcePolicyPO policy : resourcePolicyPOS) {
                List<String> p = new ArrayList<>();
                p.add("p");
                p.add(policy.getSubject());
                p.add(policy.getResource());
                p.add(policy.getAction());
                if (policy.getDomain() != null) {
                    p.add(policy.getDomain());
                }
                pPolicies.add(p);
            }
        }
        return pPolicies;
    }

    /**
     * 获取角色映射的 Casbin g 规则列表
     *
     * @param roleMappingPOS 角色映射持久化对象列表
     * @return g 规则列表
     */
    private static List<List<String>> getGPolicies(List<RoleMappingPO> roleMappingPOS) {
        List<List<String>> gPolicies = new ArrayList<>();
        if (roleMappingPOS != null && !roleMappingPOS.isEmpty()) {
            for (RoleMappingPO mapping : roleMappingPOS) {
                List<String> g = new ArrayList<>();
                g.add("g");
                g.add(mapping.getUserId());
                g.add(mapping.getRoleCode());
                if (mapping.getDomain() != null) {
                    g.add(mapping.getDomain());
                }
                gPolicies.add(g);
            }
        }
        return gPolicies;
    }
}
