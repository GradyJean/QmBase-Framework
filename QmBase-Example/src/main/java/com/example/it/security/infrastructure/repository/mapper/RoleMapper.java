package com.example.it.security.infrastructure.repository.mapper;

import com.example.it.security.infrastructure.repository.po.RolePO;
import com.qm.base.core.common.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * RoleMapper 接口用于操作角色相关的数据库表。
 * 提供了查询、插入、更新和删除角色信息的方法。
 * 该接口使用 MyBatis 框架进行数据持久化操作。
 */
@Mapper
public interface RoleMapper extends BaseMapper<RolePO, Long> {
    /**
     * 根据角色编码查询角色信息
     *
     * @param roleCode 角色编码
     * @return 角色持久化对象
     */
    RolePO findByCode(String roleCode);

}
