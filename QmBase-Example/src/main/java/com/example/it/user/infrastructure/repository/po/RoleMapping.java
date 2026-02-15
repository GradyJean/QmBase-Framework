package com.example.it.user.infrastructure.repository.po;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户角色映射表
 */
@Data
public class RoleMapping {
    /**
     * 主键ID
     */
    private Long id;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 映射的角色编码
     */
    private String roleCode;
    /**
     * 所属领域（如租户ID），默认为全局
     */
    private String domain;
    /**
     * 作用域（如应用ID），默认为全局
     */
    private String scopeCode;
    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
}