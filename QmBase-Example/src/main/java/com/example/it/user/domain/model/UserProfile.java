package com.example.it.user.domain.model;

import lombok.Data;

/**
 * 用户资料领域模型。
 * <p>
 * 表示用户的基础信息，包括昵称、头像、简介等，用于领域层的业务建模。
 * 该模型继承自 QmEntity，具备创建与更新时间、创建人等基础审计字段。
 */
@Data
public class UserProfile {
    /**
     * id
     */
    private Long id;
    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像 URL
     */
    private String avatarUrl;

    /**
     * 个人简介
     */
    private String bio;

    /**
     * 启用状态（true=启用，false=禁用）
     */
    private Boolean enable;

    /**
     * 是否逻辑删除（true=删除，false=正常）
     */
    private Boolean deleted;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 手机号码
     */
    private String phone;
}
