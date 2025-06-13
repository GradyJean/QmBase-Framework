package com.qm.base.shared.security.filter;

import com.qm.base.core.auth.model.Payload;
import com.qm.base.core.auth.token.TokenManager;
import com.qm.base.core.auth.token.TokenService;
import com.qm.base.shared.security.config.SecurityProperties;
import com.qm.base.shared.security.exception.SecurityAssert;
import com.qm.base.shared.security.exception.SecurityError;
import com.qm.base.shared.security.exception.SecurityException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * TokenAuthenticationFilter is a custom security filter that extracts the token
 * from the Authorization header and sets the authentication context if valid.
 */
@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    Logger logger = LoggerFactory.getLogger(TokenAuthenticationFilter.class);
    private final TokenService tokenService;
    private final SecurityProperties securityProperties;
    private final TokenManager tokenManager;

    public TokenAuthenticationFilter(TokenService tokenService, SecurityProperties securityProperties, TokenManager tokenManager) {
        this.tokenService = tokenService;
        this.securityProperties = securityProperties;
        this.tokenManager = tokenManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // 从请求头中获取 token
        String token = request.getHeader(securityProperties.getTokenHeader());

        // 检查 token 是否存在且以预设前缀开头
        if (token != null && token.startsWith(securityProperties.getTokenPrefix())) {
            token = token.substring(securityProperties.getTokenPrefix().length());
            Payload payload = null;
            try {
                // 验证并解析 token
                payload = tokenManager.parse(token);
            } catch (JwtException e) {
                // token 无效，不设置认证
                logger.warn("无效的 token：{}", e.getMessage());
                throw new SecurityException(SecurityError.SECURITY_TOKEN_INVALID);
            }
            SecurityAssert.INSTANCE.notNull(payload, SecurityError.SECURITY_TOKEN_ERROR);
//            Authentication authentication = new UsernamePasswordAuthenticationToken()
//            if (userId != null && tokenService.loadAuthentication(userId) != null) {
//                // 设置认证上下文
//                var authentication = tokenService.loadAuthentication(userId);
//                if (authentication != null) {
//                    SecurityContextHolder.getContext().setAuthentication(authentication);
//                }
//            }

        }

        // 继续执行过滤器链
        filterChain.doFilter(request, response);
    }
}
