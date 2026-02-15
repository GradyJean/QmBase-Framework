package com.example.it.core.user.profile;


import com.example.it.core.user.model.BasicUserInfo;

/**
 * 端口接口：用于查询用户基础信息。
 * 提供对 UserProfile 聚合根的只读访问能力。
 */
public interface UserProfileFetcher {

    /**
     * 根据用户ID查询用户的基础信息。
     *
     * @param id 用户主键ID
     * @return 用户基础信息（若不存在则可能返回 null，视具体实现而定）
     */
    BasicUserInfo selectById(String id);
}
