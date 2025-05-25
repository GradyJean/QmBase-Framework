package com.qm.base.shared.base.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 通用实体基类，提供所有表通用字段。
 * 建议所有聚合根与实体类继承此类。
 */
@Data
public abstract class BaseEntity implements Serializable {

    /**
     * 主键 ID（可由雪花算法或数据库自增生成）
     */
    private Long id;

    /**
     * 数据创建时间（自动填充）
     */
    private LocalDateTime createdAt;

    /**
     * 数据更新时间（自动填充）
     */
    private LocalDateTime updatedAt;

    /**
     * 创建人（可通过审计自动注入）
     */
    private String createdBy;

    /**
     * 更新人（可通过审计自动注入）
     */
    private String updatedBy;

    /**
     * 逻辑删除标识：0 = 正常，1 = 已删除
     */
    private Integer deleted = 0;
}