package com.example.it.user.infrastructure.repository.mapper;

import com.example.it.user.infrastructure.repository.po.RoleMapping;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 用户角色映射表RoleMappingMapper
 */
@Mapper
public interface RoleMappingMapper {
    /**
     * 使用主键查询实体类
     */
    RoleMapping findById(Long id);

    /**
     * 查询全部实体类
     */
    List<RoleMapping> findAll();

    /**
     * 插入数据
     */
    int insert(RoleMapping entity);

    /**
     * 根据主键更新
     */
    int updateById(RoleMapping entity);

    /**
     * 根据主键删除
     */
    int deleteById(Long id);
}