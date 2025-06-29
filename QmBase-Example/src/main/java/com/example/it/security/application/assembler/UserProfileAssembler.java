package com.example.it.security.application.assembler;


import com.example.it.core.user.model.BasicUserInfo;
import com.example.it.user.domain.model.UserProfile;
import com.example.it.user.infrastructure.repository.po.UserProfilePO;

/**
 * 领域模型与持久化对象之间的转换工具类。
 * 提供将 BasicUserInfo、UserProfilePO 和 UserProfile 之间的转换方法。
 */
public class UserProfileAssembler {

    /**
     * 将 BasicUserInfo 对象转换为 UserProfilePO 持久化对象。
     *
     * @param info 基础用户信息对象
     * @return 对应的 UserProfilePO 对象，若 info 为 null 则返回 null
     */
    public static UserProfilePO assemblePOFromBasic(BasicUserInfo info) {
        if (info == null) return null;
        UserProfilePO profile = new UserProfilePO();
        profile.setId(info.getUserId());
        profile.setNickname(info.getNickname());
        profile.setAvatarUrl(info.getAvatarUrl());
        profile.setPhone(info.getPhone());
        profile.setEmail(info.getEmail());
        return profile;
    }

    /**
     * 将 UserProfilePO 持久化对象转换为 BasicUserInfo。
     *
     * @param po 用户持久化对象
     * @return 对应的 BasicUserInfo 对象，若 po 为 null 则返回 null
     */
    public static BasicUserInfo assembleBasicFromPO(UserProfilePO po) {
        if (po == null) return null;
        BasicUserInfo info = new BasicUserInfo();
        info.setUserId(po.getId());
        info.setNickname(po.getNickname());
        info.setAvatarUrl(po.getAvatarUrl());
        info.setPhone(po.getPhone());
        info.setEmail(po.getEmail());
        info.setEnabled(po.getEnabled());
        return info;
    }

    /**
     * 将 UserProfilePO 对象转换为领域模型 UserProfile。
     *
     * @param po 持久化对象
     * @return 对应的 UserProfile 领域对象，若 po 为 null 则返回 null
     */
    public static UserProfile assembleUserProfile(UserProfilePO po) {
        if (po == null) return null;
        UserProfile profile = new UserProfile();
        profile.setId(po.getId());
        profile.setNickname(po.getNickname());
        profile.setAvatarUrl(po.getAvatarUrl());
        profile.setPhone(po.getPhone());
        profile.setEmail(po.getEmail());
        profile.setEnable(po.getEnabled());
        profile.setDeleted(po.getDeleted());
        return profile;
    }
}
