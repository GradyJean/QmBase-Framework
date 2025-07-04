package com.example.it.security.infrastructure.repository.po;

import lombok.Data;

import java.util.Date;

@Data
public class ScopePO {

    /**
     * 主键 ID
     */
    private Long id;
    /**
     * 权限域编码，用于唯一标识权限域
     */
    private String scopeCode;
    /**
     * 权限域名称，用于展示
     */
    private String scopeName;
    /**
     * 描述
     */
    private String description;
    /**
     * 创建时间
     */
    private Date createdAt;

}
