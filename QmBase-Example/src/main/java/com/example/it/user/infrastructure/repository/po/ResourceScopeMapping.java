package com.example.it.user.infrastructure.repository.po;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 资源与权限域映射表
 */
@Data
public class ResourceScopeMapping {
    /**
     * 主键ID
     */
    private Long id;
    /**
     * 资源路径，支持Ant风格，例如 /doc/**、/user/${id}
     */
    private String resourcePattern;
    /**
     * 权限域编码，必须指定
     */
    private String scopeCode;
    /**
     * 描述信息，可选
     */
    private String description;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}