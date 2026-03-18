package com.example.it.security.infrastructure.casbin.manager;

import com.example.it.common.constants.ScopeEnum;
import com.example.it.security.infrastructure.repository.mapper.ResourcePolicyMapper;
import com.example.it.security.infrastructure.repository.mapper.RoleMappingMapper;
import com.example.it.security.infrastructure.repository.po.ResourcePolicyPO;
import com.example.it.security.infrastructure.repository.po.RoleMappingPO;
import com.qm.base.shared.security.casbin.manager.BasePermissionManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

abstract class ExamplePermissionManagerSupport extends BasePermissionManager {

    private final RoleMappingMapper roleMappingMapper;
    private final ResourcePolicyMapper resourcePolicyMapper;

    protected ExamplePermissionManagerSupport(String scope, String modelPath,
                                              RoleMappingMapper roleMappingMapper,
                                              ResourcePolicyMapper resourcePolicyMapper) {
        super(scope, modelPath);
        this.roleMappingMapper = roleMappingMapper;
        this.resourcePolicyMapper = resourcePolicyMapper;
    }

    @Override
    protected List<List<String>> loadPolicies() {
        String scope = getScope();
        List<List<String>> policies = new ArrayList<>();
        List<RoleMappingPO> roleMappings = roleMappingMapper.listByScopeCode(scope);
        List<ResourcePolicyPO> resourcePolicies = resourcePolicyMapper.listByScopeCode(scope);
        policies.addAll(toGroupingPolicies(scope, roleMappings));
        policies.addAll(toPermissionPolicies(scope, resourcePolicies));
        return policies;
    }

    @Override
    protected boolean savePolicyRule(List<String> rule) {
        return insertResourcePolicy(getScope(), rule) > 0;
    }

    @Override
    protected boolean deletePolicyRule(List<String> rule) {
        return removeResourcePolicy(getScope(), rule);
    }

    @Override
    protected boolean saveGroupingPolicyRule(List<String> rule) {
        return insertRoleMapping(getScope(), rule) > 0;
    }

    @Override
    protected boolean deleteGroupingPolicyRule(List<String> rule) {
        return removeRoleMapping(getScope(), rule);
    }

    private int insertRoleMapping(String scope, List<String> rule) {
        if (rule.size() < 2) {
            throw new IllegalArgumentException("Invalid g rule: " + rule);
        }
        RoleMappingPO po = new RoleMappingPO();
        po.setUserId(rule.get(0));
        po.setRoleCode(rule.get(1));
        po.setScopeCode(scope);
        po.setCreatedAt(new Date());
        if (isDomainScoped(scope) && rule.size() > 2) {
            po.setDomain(rule.get(2));
        }
        return roleMappingMapper.insert(po);
    }

    private int insertResourcePolicy(String scope, List<String> rule) {
        if (rule.size() < 3) {
            throw new IllegalArgumentException("Invalid p rule: " + rule);
        }
        ResourcePolicyPO po = new ResourcePolicyPO();
        po.setSubject(rule.get(0));
        po.setResource(rule.get(1));
        po.setAction(rule.get(2));
        po.setScopeCode(scope);
        po.setCreatedAt(new Date());
        if (isDomainScoped(scope) && rule.size() > 3) {
            po.setDomain(rule.get(3));
        }
        return resourcePolicyMapper.insert(po);
    }

    private boolean removeRoleMapping(String scope, List<String> rule) {
        if (rule.size() < 2) {
            return false;
        }
        List<RoleMappingPO> all = roleMappingMapper.listByScopeCode(scope);
        for (RoleMappingPO item : all) {
            if (matchesRoleMapping(scope, item, rule)) {
                return roleMappingMapper.deleteById(item.getId()) > 0;
            }
        }
        return false;
    }

    private boolean removeResourcePolicy(String scope, List<String> rule) {
        if (rule.size() < 3) {
            return false;
        }
        List<ResourcePolicyPO> all = resourcePolicyMapper.listByScopeCode(scope);
        for (ResourcePolicyPO item : all) {
            if (matchesResourcePolicy(scope, item, rule)) {
                return resourcePolicyMapper.deleteById(item.getId()) > 0;
            }
        }
        return false;
    }

    private static List<List<String>> toPermissionPolicies(String scope, List<ResourcePolicyPO> source) {
        List<List<String>> result = new ArrayList<>();
        if (source == null || source.isEmpty()) {
            return result;
        }
        for (ResourcePolicyPO po : source) {
            List<String> line = new ArrayList<>();
            line.add("p");
            line.add(po.getSubject());
            line.add(po.getResource());
            line.add(po.getAction());
            if (isDomainScoped(scope)) {
                line.add(po.getDomain());
            }
            result.add(line);
        }
        return result;
    }

    private static List<List<String>> toGroupingPolicies(String scope, List<RoleMappingPO> source) {
        List<List<String>> result = new ArrayList<>();
        if (source == null || source.isEmpty()) {
            return result;
        }
        for (RoleMappingPO po : source) {
            List<String> line = new ArrayList<>();
            line.add("g");
            line.add(po.getUserId());
            line.add(po.getRoleCode());
            if (isDomainScoped(scope)) {
                line.add(po.getDomain());
            }
            result.add(line);
        }
        return result;
    }

    private static boolean matchesRoleMapping(String scope, RoleMappingPO po, List<String> rule) {
        boolean baseMatch = rule.get(0).equals(po.getUserId()) && rule.get(1).equals(po.getRoleCode());
        if (!baseMatch) {
            return false;
        }
        if (!isDomainScoped(scope)) {
            return true;
        }
        return rule.size() > 2 && rule.get(2).equals(po.getDomain());
    }

    private static boolean matchesResourcePolicy(String scope, ResourcePolicyPO po, List<String> rule) {
        boolean baseMatch = rule.get(0).equals(po.getSubject())
                && rule.get(1).equals(po.getResource())
                && rule.get(2).equals(po.getAction());
        if (!baseMatch) {
            return false;
        }
        if (!isDomainScoped(scope)) {
            return true;
        }
        return rule.size() > 3 && rule.get(3).equals(po.getDomain());
    }

    private static boolean isDomainScoped(String scope) {
        return !ScopeEnum.SYSTEM.equals(scope);
    }
}
