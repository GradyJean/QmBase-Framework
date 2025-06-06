package com.qm.base.core.entity;


import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Qm 通用实体基类模型
 *
 * <p>用于提供聚合根或实体类中常见的统一字段结构，具备可序列化能力。
 * 推荐所有基础数据对象继承该类。</p>
 *
 * 字段说明：
 * <ul>
 *     <li>{@code id} 主键 ID，可由雪花算法或数据库生成</li>
 *     <li>{@code createdAt} 数据创建时间（通常自动填充）</li>
 *     <li>{@code updatedAt} 数据更新时间（通常自动填充）</li>
 *     <li>{@code createdBy} 创建人（由审计机制注入）</li>
 *     <li>{@code updatedBy} 更新人（由审计机制注入）</li>
 *     <li>{@code deleted} 逻辑删除标识（0=正常，1=删除）</li>
 * </ul>
 */
public abstract class QmEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键 ID（可由雪花算法或数据库自增生成）
     */
    protected Long id;

    /**
     * 数据创建时间（自动填充）
     */
    protected LocalDateTime createdAt;

    /**
     * 数据更新时间（自动填充）
     */
    protected LocalDateTime updatedAt;

    /**
     * 创建人（可通过审计自动注入）
     */
    protected String createdBy;

    /**
     * 更新人（可通过审计自动注入）
     */
    protected String updatedBy;

    /**
     * 逻辑删除标识：0 = 正常，1 = 已删除
     */
    protected Integer deleted = 0;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }
}