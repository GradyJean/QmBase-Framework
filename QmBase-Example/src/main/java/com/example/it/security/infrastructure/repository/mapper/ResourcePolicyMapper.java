package com.example.it.security.infrastructure.repository.mapper;

import com.example.it.security.infrastructure.repository.po.ResourcePolicyPO;
import com.qm.base.core.common.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ResourcePolicyMapper extends BaseMapper<ResourcePolicyPO, Long> {
    /**
     * 根据权限域编码查询资源策略持久化对象列表
     *
     * @param scopeCode 权限域编码
     * @return list 资源策略持久化对象列表
     */
    List<ResourcePolicyPO> listByScopeCode(String scopeCode);
}
