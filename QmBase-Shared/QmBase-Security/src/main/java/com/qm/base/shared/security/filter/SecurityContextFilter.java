package com.qm.base.shared.security.filter;

import com.qm.base.auth.token.JwtTokenManager;
import com.qm.base.core.auth.enums.TokenType;
import com.qm.base.core.auth.model.Payload;
import com.qm.base.core.auth.token.TokenManager;
import com.qm.base.core.common.constants.FilterOrder;
import com.qm.base.core.common.constants.HeaderConstant;
import com.qm.base.core.utils.StringUtils;
import com.qm.base.shared.id.api.QmId;
import com.qm.base.shared.logger.core.QmLog;
import com.qm.base.shared.security.config.SecurityProperties;
import com.qm.base.shared.security.context.SecurityContextHolder;
import com.qm.base.shared.security.exception.SecurityAssert;
import com.qm.base.shared.security.exception.SecurityError;
import com.qm.base.shared.security.context.SecurityContext;
import com.qm.base.shared.security.util.AntPathMatcherUtil;
import com.qm.base.shared.security.util.SecurityContextTransmitter;
import com.qm.base.shared.web.filter.QmFilter;
import com.qm.base.shared.web.filter.QmFilterChain;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 安全上下文过滤器，用于解析 accessToken 或上游传递的安全上下文信息，
 * 并设置到当前线程上下文中，供后续业务访问。
 */
@Component
public class SecurityContextFilter implements QmFilter {
    /**
     * 配置文件
     */
    private final SecurityProperties securityProperties;
    /**
     * token 管理器
     */
    private final TokenManager tokenManager;

    /**
     * 构造函数，注入安全配置项并初始化 token 管理器。
     */
    public SecurityContextFilter(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
        tokenManager = new JwtTokenManager(securityProperties);
    }

    /**
     * 判断当前请求是否需要经过该过滤器。
     * 若请求路径匹配配置中排除的路径，则跳过过滤器处理。
     */
    @Override
    public boolean match(HttpServletRequest request) {
        // 使用 AntPathMatcher 判断请求路径是否匹配排除的 URL
        return !AntPathMatcherUtil.match(request.getRequestURI(), securityProperties.getExcludeUrls());
    }


    /**
     * 执行过滤逻辑：
     * - 若请求头包含 Bearer Token，则解析 Token；
     * - 若无 Token，则读取上游传递的 X-Security-Context；
     * - 设置 userId、deviceId、traceId 到 SecurityContext；
     * - 设置到线程上下文中，并继续调用过滤链；
     * - 最后清理上下文防止线程复用带来污染。
     */
    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, QmFilterChain chain) throws IOException, ServletException {
        String authorization = request.getHeader(HeaderConstant.AUTHORIZATION);
        String accessToken = null;

        if (authorization != null && authorization.startsWith(HeaderConstant.BEARER)) {
            accessToken = authorization.substring(7);
        }
        String contextStr = request.getHeader(HeaderConstant.SECURITY_CONTEXT);
        // accessToken 存在为新入口
        if (!StringUtils.isBlank(accessToken)) {
            try {
                // 获取令牌解析
                Payload payload = SecurityAssert.INSTANCE.notNull(tokenManager.parse(accessToken), SecurityError.SECURITY_TOKEN_INVALID);
                // 判断令牌类型
                SecurityAssert.INSTANCE.isTrue(TokenType.ACCESS.equals(payload.getType()), SecurityError.SECURITY_TOKEN_INVALID);
                // 设置 userId
                Long userId = payload.getUserId();
                // 设置设备 ID
                String deviceId = payload.getDeviceId();
                // 生成新traceId
                String traceId = String.format("%s:%s", "trace-", QmId.nextId());
                // 设置上下文
                SecurityContextHolder.setContext(new SecurityContext(userId, traceId, deviceId));
            } catch (ExpiredJwtException e) {
                throw new com.qm.base.shared.security.exception.SecurityException(SecurityError.SECURITY_TOKEN_EXPIRED);
            } catch (SecurityException e) {
                throw new com.qm.base.shared.security.exception.SecurityException(SecurityError.SECURITY_TOKEN_INVALID);
            } catch (JwtException e) {
                QmLog.error(e.getMessage(), e);
                throw new com.qm.base.shared.security.exception.SecurityException(SecurityError.SECURITY_ERROR);
            }
        } else if (StringUtils.isNotBlank(contextStr)) {
            // 解析并设置上下文
            SecurityContextTransmitter.decodeAndSetContext(contextStr);
        } else {
            throw new com.qm.base.shared.security.exception.SecurityException(SecurityError.SECURITY_UNAUTHORIZED);
        }
        try {
            chain.doFilter(request, response);
        } finally {
            SecurityContextHolder.clearContext();
        }
    }

    @Override
    public int getOrder() {
        return FilterOrder.SECURITY_CONTEXT.getOrder();
    }
}
