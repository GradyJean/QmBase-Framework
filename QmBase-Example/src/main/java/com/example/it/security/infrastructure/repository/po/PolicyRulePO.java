package com.example.it.security.infrastructure.repository.po;

import lombok.Data;

/**
 * 权限策略规则持久化对象，对应 resource_policy 表。
 * 表示一个权限规则，包括主体（subject）、资源（object）、
 * 动作（action）和所属域（domain）。
 */
@Data
public class PolicyRulePO {

    /**
     * 主键 ID，自增。
     */
    private Long id;

    /**
     * 权限主体，通常为用户 ID 或角色标识。
     */
    private String subject;

    /**
     * 资源标识，例如资源路径或资源名称。
     */
    private String object;

    /**
     * 操作类型，例如 read、write、delete 等。
     */
    private String action;

    /**
     * 权限所属的作用域或域，例如 document、project 等。
     */
    private String domain;

    /**
     * 规则创建时间，字符串格式。
     */
    private String createdAt;

}
