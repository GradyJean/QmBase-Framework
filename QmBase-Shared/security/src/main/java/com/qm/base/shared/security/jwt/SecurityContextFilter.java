package com.qm.base.shared.security.jwt;

import com.qm.base.shared.security.context.LoginUser;
import com.qm.base.shared.security.context.SecurityContextHolder;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * JWT token 解析过滤器，将解析出的 LoginUser 注入上下文
 */
public class SecurityContextFilter extends OncePerRequestFilter {

    private final String jwtSecret;

    public SecurityContextFilter(String jwtSecret) {
        this.jwtSecret = jwtSecret;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String token = resolveToken(request);
        if (token != null) {
            try {
                SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
                Claims claims = Jwts.parser()
                        .verifyWith(key)
                        .build()
                        .parseSignedClaims(token)
                        .getPayload();

                LoginUser user = new LoginUser();
                user.setUserId(Long.valueOf(claims.get("userId").toString()));
                user.setUsername(claims.get("username", String.class));
                user.setRoles((List<String>) claims.get("roles"));
                user.setPermissions((List<String>) claims.get("permissions"));

                SecurityContextHolder.set(user);
            } catch (Exception e) {
                // token 非法或过期，忽略处理，走匿名流程
            }
        }

        try {
            filterChain.doFilter(request, response);
        } finally {
            SecurityContextHolder.clear();
        }
    }

    private String resolveToken(HttpServletRequest request) {
        String bearer = request.getHeader("Authorization");
        if (bearer != null && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
    }
}