package com.example.it.user.infrastructure.repository.mapper;

import com.example.it.user.infrastructure.repository.po.ResourcePolicy;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 资源权限策略表ResourcePolicyMapper
 */

@Mapper
public interface ResourcePolicyMapper {
    /**
     * 使用主键查询实体类
     */
    ResourcePolicy findById(Long id);

    /**
     * 查询全部实体类
     */
    List<ResourcePolicy> findAll();

    /**
     * 插入数据
     */
    int insert(ResourcePolicy entity);

    /**
     * 根据主键更新
     */
    int updateById(ResourcePolicy entity);

    /**
     * 根据主键删除
     */
    int deleteById(Long id);
}