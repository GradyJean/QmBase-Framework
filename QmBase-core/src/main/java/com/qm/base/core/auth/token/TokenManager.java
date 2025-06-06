package com.qm.base.core.auth.token;

import com.qm.base.core.auth.enums.TokenType;
import com.qm.base.core.auth.model.Payload;

import java.util.Date;

/**
 * token 管理器
 * 用于生产 AuthToken、解析 token
 */
public interface TokenManager {
    /**
     * 生成JWT令牌的通用方法。
     * <p>
     * 根据传入的用户ID、Token类型和过期时间，构造JWT的Claims，
     * 设置标准字段如签发者（issuer）、签发时间（issuedAt）、过期时间（expiration），
     * 并使用配置的密钥进行HS256算法签名，最终生成紧凑的JWT字符串。
     *
     * @param userId     用户唯一标识，用于标识该Token所属用户
     * @param tokenType  Token类型，区分访问令牌或刷新令牌
     * @param issuedAt   签发时间
     * @param expiration 过期时间
     * @return 签名后的JWT令牌字符串
     */
    String generateToken(Long userId, TokenType tokenType, Date issuedAt, Date expiration, String deviceId);

    /**
     * 解析  Token
     *
     * @param token 令牌字符串
     * @return 解析后的  载荷
     */
    Payload parse(String token);
}
