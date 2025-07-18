package com.example.it.security.infrastructure.repository.po;

import lombok.Data;

import java.util.Date;

/**
 * RoleMappingPO 表示用户与角色在特定权限域（domain）下的关联关系。
 * 用于存储权限系统中的用户角色映射信息。
 */
@Data
public class RoleMappingPO {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 角色编码，例如：admin、editor 等
     */
    private String roleCode;

    /**
     * 权限域，用于区分系统中的不同资源域
     */
    private String domain;
    /**
     * 权限作用域编码，用于唯一标识权限域
     */
    private String scopeCode;
    /**
     * 创建时间
     */
    private Date createdAt;

}
