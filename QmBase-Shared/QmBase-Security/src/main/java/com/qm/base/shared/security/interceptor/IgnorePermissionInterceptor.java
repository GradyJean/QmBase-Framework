package com.qm.base.shared.security.interceptor;

import com.qm.base.shared.security.annotation.IgnorePermission;
import com.qm.base.shared.security.context.SecurityContext;
import com.qm.base.shared.security.context.SecurityContextHolder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class IgnorePermissionInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (handler instanceof HandlerMethod method) {
            if (method.hasMethodAnnotation(IgnorePermission.class)
                    || method.getBeanType().isAnnotationPresent(IgnorePermission.class)) {
                SecurityContext context = SecurityContextHolder.getContext();
                context.setAuthorized(true); // 标记为已授权，后续 Filter 可跳过
                return true;
            }
        }
        return true;
    }
}