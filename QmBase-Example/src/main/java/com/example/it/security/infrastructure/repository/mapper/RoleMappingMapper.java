package com.example.it.security.infrastructure.repository.mapper;

import com.example.it.security.infrastructure.repository.po.RoleMappingPO;
import com.qm.base.core.common.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoleMappingMapper extends BaseMapper<RoleMappingPO, Long> {
    /**
     * 根据权限域查询角色映射持久化对象列表
     *
     * @param scopeCode 权限域
     * @return list 角色映射持久化对象列表
     */
    List<RoleMappingPO> listByScopeCode(String scopeCode);
}
