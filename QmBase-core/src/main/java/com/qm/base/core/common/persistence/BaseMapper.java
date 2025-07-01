package com.qm.base.core.common.persistence;

import java.util.List;

/**
 * 通用基础 Mapper 接口，提供常见的增删改查方法定义。
 * 各业务 Mapper 接口可继承本接口，避免重复定义通用操作。
 *
 * @param <T>  实体类型
 * @param <ID> 主键类型
 */
public interface BaseMapper<T, ID> {

    /**
     * 根据主键查询实体
     *
     * @param id 主键
     * @return 实体对象，若不存在则返回 null
     */
    T findById(ID id);

    /**
     * 查询所有实体记录
     *
     * @return 实体列表
     */
    List<T> findAll();

    /**
     * 插入一条记录
     *
     * @param entity 实体对象
     * @return 插入影响的行数
     */
    int insert(T entity);

    /**
     * 根据主键更新记录
     *
     * @param entity 实体对象（必须包含 ID）
     * @return 更新影响的行数
     */
    int updateById(T entity);

    /**
     * 根据主键删除记录
     *
     * @param id 主键
     * @return 删除影响的行数
     */
    int deleteById(ID id);
}
