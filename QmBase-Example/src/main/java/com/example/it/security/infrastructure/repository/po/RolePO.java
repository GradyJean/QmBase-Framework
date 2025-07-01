package com.example.it.security.infrastructure.repository.po;

import lombok.Data;

/**
 * 角色持久化对象，用于映射数据库中的角色表。
 */
@Data
public class RolePO {

  /**
   * 主键 ID
   */
  private Long id;

  /**
   * 角色编码，具有唯一性
   */
  private String roleCode;

  /**
   * 角色名称，用于展示
   */
  private String roleName;

  /**
   * 角色类型，例如系统角色、自定义角色等
   */
  private String roleType;

  /**
   * 角色描述信息
   */
  private String description;

  /**
   * 状态标识，例如是否启用（0/1）
   */
  private Long status;

  /**
   * 是否内置角色（1 表示是，0 表示否）
   */
  private Long builtIn;

  /**
   * 创建时间，格式为字符串（建议为 ISO 日期格式）
   */
  private String createdAt;

  /**
   * 更新时间，格式为字符串
   */
  private String updatedAt;

}
