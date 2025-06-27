package com.example.it.user.infrastructure.repository.po;

import com.qm.base.core.entity.QmEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserProfilePO extends QmEntity {

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
    private Boolean enabled;

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
