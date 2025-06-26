package com.qm.base.auth.filter;

import com.qm.base.auth.context.AuthContextHolder;
import com.qm.base.auth.model.vo.AuthContext;
import com.qm.base.core.auth.exception.AuthAssert;
import com.qm.base.core.auth.exception.AuthError;
import com.qm.base.core.common.constants.FilterOrder;
import com.qm.base.shared.logger.core.QmLog;
import com.qm.base.shared.web.filter.QmFilter;
import com.qm.base.shared.web.filter.QmFilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.io.IOException;
import java.util.List;

/**
 * AuthHeaderFilter 用于拦截 /auth/** 路径的请求，
 * 并从请求头中提取设备 ID（X-Device-Id），注入到 AuthContext 中。
 * 请求处理完成后自动清理上下文，避免 ThreadLocal 泄漏。
 */
@Component
public class AuthHeaderFilter implements QmFilter {
    private final PathMatcher matcher = new AntPathMatcher();
    private static final List<String> INTERNAL_AUTH_EXCLUDE_PATHS = List.of(
            "/auth/login",
            "/auth/third/*/url"
    );

    /**
     * 匹配特定的 /auth/ 路径，用于决定是否执行当前过滤器。
     * 目前仅匹配如下路径：
     * - /auth/login
     * - /auth/third/{platform}/url
     *
     * @param request 当前请求对象
     * @return 如果路径匹配上述规则，返回 true；否则返回 false
     */
    @Override
    public boolean match(HttpServletRequest request) {
        String path = request.getServletPath();
        return INTERNAL_AUTH_EXCLUDE_PATHS.stream().anyMatch(p -> matcher.match(p, path));
    }

    /**
     * 执行过滤逻辑，提取设备 ID 注入到上下文中，并在请求结束后清除上下文。
     *
     * @param request  当前请求
     * @param response 当前响应
     * @param chain    过滤器链
     * @throws IOException      IO 异常
     * @throws ServletException Servlet 异常
     */
    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, QmFilterChain chain) throws IOException, ServletException {
        String deviceId = AuthAssert.INSTANCE.notBlank(request.getHeader("X-Device-Id"), AuthError.AUTH_DEVICE_ID_EMPTY);
        AuthContext context = new AuthContext();
        context.setDeviceId(deviceId);
        AuthContextHolder.setContext(context);
        QmLog.debug("deviceId:{}", deviceId);
        try {
            chain.doFilter(request, response);
        } finally {
            AuthContextHolder.clearContext();
        }
    }

    /**
     * 获取当前过滤器的执行顺序。
     *
     * @return 顺序值，越小优先级越高
     */
    @Override
    public int getOrder() {
        return FilterOrder.AUTH_HEADER.getOrder();
    }
}
