package com.example.it.security.infrastructure.repository.po;

import lombok.Data;

/**
 * 资源与权限域的映射关系实体类。
 * 用于定义某个 HTTP 请求资源（如 URL 模式、请求方法）在哪个权限域（domain）下，执行何种操作（action）。
 */
@Data
public class ResourceDomainMappingPO {

  /**
   * 主键 ID，自增。
   */
  private Long id;

  /**
   * 资源路径匹配模式，支持 Ant 风格，例如 /doc/**。
   */
  private String resourcePattern;

  /**
   * HTTP 方法，例如 GET、POST、PUT、DELETE。
   */
  private String httpMethod;

  /**
   * 行为操作名称，例如 view、edit、delete。
   */
  private String action;

  /**
   * 所属权限域，例如 document、user、admin 等。
   */
  private String domain;

  /**
   * 描述信息，可用于辅助说明该映射的业务含义。
   */
  private String description;

  /**
   * 创建时间。
   */
  private java.sql.Timestamp createTime;

}
