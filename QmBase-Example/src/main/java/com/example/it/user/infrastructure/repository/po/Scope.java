package com.example.it.user.infrastructure.repository.po;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 权限域管理表
 */
@Data
public class Scope {
    /**
     * 主键
     */
    private Long id;
    /**
     * 权限域标识（如：BLOG、COURSE）
     */
    private String scopeCode;
    /**
     * 权限域名称（如：博客系统、课程系统,博客后台管理系统）
     */
    private String scopeName;
    /**
     * 可选字段，用于说明该权限域的用途
     */
    private String description;
    /**
     * 创建时间，默认为当前时间
     */
    private LocalDateTime createdAt;
}