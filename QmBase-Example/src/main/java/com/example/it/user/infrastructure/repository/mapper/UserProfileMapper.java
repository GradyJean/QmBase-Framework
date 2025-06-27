package com.example.it.user.infrastructure.repository.mapper;

import com.example.it.user.infrastructure.repository.po.UserProfilePO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserProfileMapper {
    /**
     * 插入新的用户档案记录。
     *
     * @param userProfilePO 用户档案持久化对象
     * @return 影响的行数
     */
    int insert(UserProfilePO userProfilePO);

    /**
     * 根据用户主键 ID 查询用户档案。
     *
     * @param id 用户主键 ID
     * @return 用户档案持久化对象，若未找到返回 null
     */
    UserProfilePO selectById(Long id);
}
