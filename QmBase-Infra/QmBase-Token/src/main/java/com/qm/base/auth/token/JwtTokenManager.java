package com.qm.base.auth.token;

import com.qm.base.core.auth.config.TokenProperties;
import com.qm.base.core.auth.constants.AuthConstants;
import com.qm.base.core.auth.enums.TokenType;
import com.qm.base.core.auth.model.Payload;
import com.qm.base.core.auth.token.TokenManager;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ClaimsBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.lang.Assert;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class JwtTokenManager implements TokenManager {

    /**
     * 秘钥
     */
    private final SecretKey secretKey;

    private final String issuer;

    public JwtTokenManager(TokenProperties properties) {
        Assert.notNull(properties, "TokenProperties must not be null");
        Assert.isTrue(properties.getIssuer() != null && !properties.getIssuer().isEmpty(), "TokenProperties issuer must not be null");
        Assert.isTrue(properties.getSecret() != null && !properties.getSecret().isEmpty(), "TokenProperties secret must not be null");
        this.secretKey = Keys.hmacShaKeyFor(properties.getSecret().getBytes(StandardCharsets.UTF_8));
        this.issuer = properties.getIssuer();
    }

    @Override
    public Payload parse(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(this.secretKey)
                .requireIssuer(issuer)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        Payload payload = new Payload();
        payload.setUserId(claims.get(AuthConstants.AUTH_USER_ID, Long.class));
        payload.setType(TokenType.valueOf(claims.get(AuthConstants.AUTH_TOKEN_TYPE, String.class)));
        payload.setIssuer(claims.get(AuthConstants.AUTH_TOKEN_ISSUER, String.class));
        payload.setExpiresAt(claims.get(AuthConstants.AUTH_TOKEN_EXPIRATION, Date.class));
        return payload;
    }

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
    public String generateToken(Long userId, TokenType tokenType, Date issuedAt, Date expiration, String deviceId) {
        ClaimsBuilder claimsBuilder = Jwts.claims()
                // 用户ID，标识该 Token 属于哪个用户
                .add(AuthConstants.AUTH_USER_ID, userId)
                // Token 类型（如 ACCESS、REFRESH），用于区分用途
                .add(AuthConstants.AUTH_TOKEN_TYPE, tokenType.name())
                .add(AuthConstants.AUTH_TOKEN_ISSUER, issuer)
                .add(AuthConstants.AUTH_TOKEN_EXPIRATION, expiration)
                .add(AuthConstants.AUTH_TOKEN_DEVICE_ID, deviceId);

        return Jwts.builder()
                .claims(claimsBuilder.build())
                .issuer(issuer) // 设置Token签发者
                .issuedAt(issuedAt)                     // 设置Token签发时间
                .expiration(expiration)            // 设置Token过期时间
                .signWith(this.secretKey, Jwts.SIG.HS256) // 使用HS256算法和密钥签名Token
                .compact();
    }
}
