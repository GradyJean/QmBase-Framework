package com.example.it.user.infrastructure.repository.mapper;

import com.example.it.user.infrastructure.repository.po.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 角色管理表RoleMapper
 */
@Mapper
public interface RoleMapper {
    /**
     * 使用主键查询实体类
     */
    Role findById(Long id);

    /**
     * 查询全部实体类
     */
    List<Role> findAll();

    /**
     * 插入数据
     */
    int insert(Role entity);

    /**
     * 根据主键更新
     */
    int updateById(Role entity);

    /**
     * 根据主键删除
     */
    int deleteById(Long id);
}