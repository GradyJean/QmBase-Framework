package com.qm.base.auth.service.token;

import com.qm.base.core.auth.model.AuthToken;


/**
 * TokenService 接口定义了与用户访问令牌（AuthToken）相关的核心操作。
 * <p>
 * 支持根据 userId 和 deviceId 获取、保存、撤销访问令牌，适用于多端登录场景。
 * 实现类可通过 Redis 或数据库等方式持久化令牌数据。
 * </p>
 */
public interface TokenService {
    /**
     * 根据用户 ID 和设备 ID 查找访问令牌。
     *
     * @param userId   用户唯一标识
     * @param deviceId 设备标识符（如浏览器、APP设备等）
     * @return AuthToken 如果存在则返回对应令牌，否则返回 null
     */
    AuthToken findAuthTokenByUserId(Long userId, String deviceId);

    /**
     * 保存访问令牌。
     *
     * @param userId    用户唯一标识
     * @param deviceId  设备标识符
     * @param authToken 访问令牌对象
     */
    void saveAuthToken(Long userId, String deviceId, AuthToken authToken);

    /**
     * 撤销指定用户在某个设备上的访问令牌。
     *
     * @param userId   用户唯一标识
     * @param deviceId 设备标识符
     */
    void revokeToken(Long userId, String deviceId);
}
