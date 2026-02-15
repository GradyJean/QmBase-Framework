package com.example.it.user.infrastructure.repository.mapper;

import com.example.it.user.infrastructure.repository.po.Scope;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 权限域管理表ScopeMapper
 */
@Mapper
public interface ScopeMapper {
    /**
     * 使用主键查询实体类
     */
    Scope findById(Long id);

    /**
     * 查询全部实体类
     */
    List<Scope> findAll();

    /**
     * 插入数据
     */
    int insert(Scope entity);

    /**
     * 根据主键更新
     */
    int updateById(Scope entity);

    /**
     * 根据主键删除
     */
    int deleteById(Long id);
}