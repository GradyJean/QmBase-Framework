package com.example.it.user.infrastructure.repository.mapper;

import com.example.it.user.infrastructure.repository.po.ResourceScopeMapping;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 资源与权限域映射表ResourceScopeMappingMapper
 */
@Mapper
public interface ResourceScopeMappingMapper {
    /**
     * 使用主键查询实体类
     */
    ResourceScopeMapping findById(Long id);

    /**
     * 查询全部实体类
     */
    List<ResourceScopeMapping> findAll();

    /**
     * 插入数据
     */
    int insert(ResourceScopeMapping entity);

    /**
     * 根据主键更新
     */
    int updateById(ResourceScopeMapping entity);

    /**
     * 根据主键删除
     */
    int deleteById(Long id);
}