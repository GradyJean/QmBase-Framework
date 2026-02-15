package com.example.it.user.infrastructure.repository.po;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 资源权限策略表
 */
@Data
public class ResourcePolicy {
    /**
     * 主键
     */
    private Long id;
    /**
     * 主体（如用户ID或角色）
     */
    private String subject;
    /**
     * 资源对象
     */
    private String resource;
    /**
     * 资源类型（如文档、页面等）
     */
    private String resourceType;
    /**
     * 操作行为（如read、write）
     */
    private String action;
    /**
     * 所属领域（如租户ID）
     */
    private String domain;
    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
}