package com.qm.base.auth.model.token;

import com.qm.base.auth.config.JwtProperties;
import com.qm.base.core.model.auth.constants.AuthConstants;
import com.qm.base.core.model.auth.dto.AuthToken;
import com.qm.base.core.model.auth.dto.JwtPayload;
import com.qm.base.core.model.auth.enums.TokenType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ClaimsBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class JwtTokenManager {
    private final JwtProperties jwtProperties;
    private final SecretKey secretKey;

    public JwtTokenManager(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
        this.secretKey = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8));
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
    private String generateToken(Long userId, TokenType tokenType, Date issuedAt, Date expiration) {

        ClaimsBuilder claimsBuilder = Jwts.claims()
                // 用户ID，标识该 Token 属于哪个用户
                .add(AuthConstants.AUTH_USER_ID, userId)
                // Token 类型（如 ACCESS、REFRESH），用于区分用途
                .add(AuthConstants.AUTH_TOKEN_TYPE, tokenType.name())
                .add(AuthConstants.AUTH_TOKEN_ISSUER, jwtProperties.getIssuer())
                .add(AuthConstants.AUTH_TOKEN_EXPIRATION, expiration);

        return Jwts.builder()
                .claims(claimsBuilder.build())
                .issuer(jwtProperties.getIssuer()) // 设置Token签发者
                .issuedAt(issuedAt)                     // 设置Token签发时间
                .expiration(expiration)            // 设置Token过期时间
                .signWith(this.secretKey, Jwts.SIG.HS256) // 使用HS256算法和密钥签名Token
                .compact();
    }

    public AuthToken generateAuthToken(JwtPayload payload) {
        Long userId = payload.getUserId();
        Date now = new Date();
        Date accessExpiration = new Date(now.getTime() + TimeUnit.SECONDS.toMillis(jwtProperties.getExpirationSeconds()));
        Date refreshExpiration = new Date(now.getTime() + TimeUnit.SECONDS.toMillis(jwtProperties.getRefreshIntervalSeconds()));
        String accessToken = generateToken(userId, TokenType.ACCESS, now, accessExpiration);
        String refreshToken = generateToken(userId, TokenType.REFRESH, now, refreshExpiration);
        return AuthToken.of(accessToken, refreshToken, accessExpiration.getTime());
    }

    /**
     * 从 token 字符串中解析出 JwtPayload。
     *
     * @param token JWT 字符串
     * @return JwtPayload 对象，包含用户 ID 信息
     * @throws JwtException 若 token 无效或验证失败
     */
    public JwtPayload parse(String token) throws JwtException {
        Claims claims = Jwts.parser()
                .verifyWith(this.secretKey)
                .requireIssuer(jwtProperties.getIssuer())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        JwtPayload payload = new JwtPayload();
        payload.setUserId(claims.get(AuthConstants.AUTH_USER_ID, Long.class));
        payload.setType(TokenType.valueOf(claims.get(AuthConstants.AUTH_TOKEN_TYPE, String.class)));
        payload.setIssuer(claims.get(AuthConstants.AUTH_TOKEN_ISSUER, String.class));
        payload.setExpiresAt(claims.get(AuthConstants.AUTH_TOKEN_EXPIRATION, Date.class));
        return payload;
    }
}
