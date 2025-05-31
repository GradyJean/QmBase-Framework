package com.qm.base.auth.model.token;

import com.qm.base.core.model.auth.enums.TokenType;
import com.qm.base.auth.config.JwtProperties;
import com.qm.base.core.model.auth.constants.AuthConstants;
import com.qm.base.core.model.auth.dto.JwtPayload;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

/**
 * JwtTokenParser 用于从 JWT 字符串中解析出 Payload 数据。
 * 它使用配置中的密钥进行验证，并提取出用户标识信息。
 */
@Component
public class JwtTokenParser {

    /**
     * JWT 配置信息
     */
    private final JwtProperties jwtProperties;

    /**
     * 用于验证 JWT 的密钥
     */
    private final SecretKey key;

    /**
     * 构造方法，初始化配置与密钥
     *
     * @param jwtProperties 注入的 JWT 配置
     */
    public JwtTokenParser(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
        this.key = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8));
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
                .verifyWith(this.key)
                .requireIssuer(jwtProperties.getIssuer())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        JwtPayload payload = new JwtPayload();
        payload.setUserId(claims.get(AuthConstants.AUTH_USER_ID, Long.class));
        payload.setType(TokenType.valueOf(claims.get(AuthConstants.AUTH_TOKEN_TYPE, String.class)));
        return payload;
    }
}
