package com.example.it.user.infrastructure.repository.po;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 角色管理表
 */
@Data
public class Role {
    /**
     * 主键 ID
     */
    private Long id;
    /**
     * 角色编码（唯一）
     */
    private String roleCode;
    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 角色类型，例如 SYSTEM / TENANT / CUSTOM
     */
    private String roleType;
    /**
     * 角色描述信息
     */
    private String description;
    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;
}