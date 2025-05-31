package com.qm.base.auth.model.token;

import com.qm.base.auth.config.JwtProperties;
import com.qm.base.core.model.auth.constants.AuthConstants;
import com.qm.base.core.model.auth.dto.JwtPayload;
import com.qm.base.core.model.auth.enums.TokenType;
import io.jsonwebtoken.ClaimsBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * JWT Token 生成器。
 * <p>
 * 该类负责根据用户信息生成签名的JWT令牌，支持生成访问令牌（Access Token）和刷新令牌（Refresh Token）。
 * 通过配置的密钥和参数对Token进行HS256算法签名，确保Token的安全性和有效期管理。
 * 设计初衷是为认证系统提供统一的Token生成逻辑，简化Token管理。
 */
@Component
public class JwtTokenGenerator {

    /**
     * JWT相关配置属性，包含密钥、签发者、过期时间等信息。
     */
    private final JwtProperties jwtProperties;

    /**
     * 构造函数，注入JWT配置属性。
     *
     * @param jwtProperties JWT相关配置
     */
    public JwtTokenGenerator(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    /**
     * 生成访问令牌（Access Token）。
     * <p>
     * 该方法基于用户负载信息生成一个带有访问权限的JWT令牌，包含用户ID和Token类型，
     * 并设置过期时间为配置中的访问令牌有效时长。
     *
     * @param payload 包含用户信息的JWT负载对象
     * @return 签名后的访问令牌字符串
     */
    public String generateAccessToken(JwtPayload payload) {
        return generateToken(payload.getUserId(), TokenType.ACCESS, jwtProperties.getExpirationSeconds());
    }

    /**
     * 生成刷新令牌（Refresh Token）。
     * <p>
     * 该方法基于用户负载信息生成一个用于刷新访问令牌的JWT令牌，
     * 包含用户ID和Token类型，过期时间为配置中的刷新令牌有效时长。
     *
     * @param payload 包含用户信息的JWT负载对象
     * @return 签名后的刷新令牌字符串
     */
    public String generateRefreshToken(JwtPayload payload) {
        return generateToken(payload.getUserId(), TokenType.REFRESH, jwtProperties.getRefreshIntervalSeconds());
    }

    /**
     * 生成JWT令牌的通用方法。
     * <p>
     * 根据传入的用户ID、Token类型和过期时间，构造JWT的Claims，
     * 设置标准字段如签发者（issuer）、签发时间（issuedAt）、过期时间（expiration），
     * 并使用配置的密钥进行HS256算法签名，最终生成紧凑的JWT字符串。
     *
     * @param userId           用户唯一标识，用于标识该Token所属用户
     * @param type             Token类型，区分访问令牌或刷新令牌
     * @param expiresInSeconds Token有效期，单位为秒
     * @return 签名后的JWT令牌字符串
     */
    private String generateToken(Long userId, TokenType type, long expiresInSeconds) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + TimeUnit.SECONDS.toMillis(expiresInSeconds));

        ClaimsBuilder claimsBuilder = Jwts.claims()
                // 用户ID，标识该 Token 属于哪个用户
                .add(AuthConstants.AUTH_USER_ID, userId)
                // Token 类型（如 ACCESS、REFRESH），用于区分用途
                .add(AuthConstants.AUTH_TOKEN_TYPE, type.name());

        return Jwts.builder()
                .claims(claimsBuilder.build())
                .issuer(jwtProperties.getIssuer()) // 设置Token签发者
                .issuedAt(now)                     // 设置Token签发时间
                .expiration(expiryDate)            // 设置Token过期时间
                .signWith(getSigningKey(), Jwts.SIG.HS256) // 使用HS256算法和密钥签名Token
                .compact();
    }

    /**
     * 获取用于JWT签名的密钥。
     * <p>
     * 从配置中读取密钥字符串，将其转换为字节数组，
     * 并使用JJWT库的Keys工具生成SecretKey对象，
     * 该密钥用于对JWT进行HS256算法的签名，保证Token的完整性和安全性。
     *
     * @return 用于签名的SecretKey对象
     */
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes());
    }
}
