package com.example.it.security.infrastructure.repository.po;

import lombok.Data;

import java.util.Date;

@Data
public class DomainPO {

    /**
     * 主键 ID
     */
    private Long id;
    /**
     * 权限域编码，具有唯一性
     */
    private String domainCode;
    /**
     * 权限域名称，用于展示
     */
    private String domainName;
    /**
     * 描述
     */
    private String description;
    /**
     * 状态标识，例如是否启用（0/1）
     */
    private Long status;
    /**
     * 创建时间
     */
    private Date createdAt;

}
