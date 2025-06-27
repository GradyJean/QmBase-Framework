package com.example.it.core.user.model;

import lombok.Data;

/**
 * 用户基础信息 DTO。
 * 用于对外传输用户基础信息（如昵称、头像、手机号、邮箱等），
 * 通常服务于展示层或接口返回对象。
 */
@Data
public class BasicUserInfo {
    /**
     * 用户唯一标识。
     */
    private Long userId;
    /**
     * 用户昵称。
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
     * 邮箱
     */
    private String email;
    /**
     * 手机号码
     */
    private String phone;
    /**
     * 删除标识
     */
    private Boolean enabled;
}
