package com.example.it.security.infrastructure.repository.po;

import lombok.Data;

/**
 * ResourcePolicyPO 表示资源访问策略的持久化对象（PO）。
 * 用于映射权限策略规则，例如某个用户或角色（subject）对某类资源（resource）在某个域（domain）中拥有特定操作（action）的权限。
 */
@Data
public class ResourcePolicyPO {

  /**
   * 主键 ID
   */
  private Long id;

  /**
   * 权限主体，如用户 ID 或角色标识
   */
  private String subject;

  /**
   * 资源标识，例如 URL、文档 ID 等
   */
  private String resource;

  /**
   * 资源类型，如 doc、folder、menu 等
   */
  private String resourceType;

  /**
   * 操作类型，如 read、write、delete 等
   */
  private String action;

  /**
   * 所属权限域，如 document、user、admin 等
   */
  private String domain;

  /**
   * 创建时间，格式为 yyyy-MM-dd HH:mm:ss
   */
  private String createdAt;

}
