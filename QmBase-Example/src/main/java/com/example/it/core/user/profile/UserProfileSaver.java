package com.example.it.core.user.profile;

import com.example.it.core.user.model.BasicUserInfo;

/**
 * UserSaver 提供用于创建用户基本信息的接口。
 * 该接口用于在注册时插入一条用户数据记录，仅包含必要字段。
 */
public interface UserProfileSaver {

    /**
     * 创建用户资料记录。
     * 仅保存 userId 字段，其余字段可为空或后续完善。
     *
     * @param userInfo 用户资料实体，至少包含 userId。
     * @return userId
     */
    Long create(BasicUserInfo userInfo);
}
