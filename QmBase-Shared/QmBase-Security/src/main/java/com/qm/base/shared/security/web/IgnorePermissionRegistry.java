
package com.qm.base.shared.security.web;

import com.qm.base.shared.security.annotation.IgnorePermission;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * IgnorePermissionRegistry 用于注册标记了 @IgnorePermission 的接口方法，
 * 在权限过滤中跳过这些方法。
 */
@Component
public class IgnorePermissionRegistry implements ApplicationContextAware {

    private static final Set<String> IGNORE_URIS = new HashSet<>();

    /**
     * Spring MVC 的请求映射处理器，用于获取所有的 HandlerMethod 映射
     */

    private final RequestMappingHandlerMapping handlerMapping;

    public IgnorePermissionRegistry(@Qualifier("requestMappingHandlerMapping") RequestMappingHandlerMapping handlerMapping) {
        this.handlerMapping = handlerMapping;
    }

    /**
     * 设置 ApplicationContext，并从中获取所有标记了 @IgnorePermission 的 HandlerMethod，
     * 将其对应的 URI 添加到 IGNORE_URIS 集合中。
     *
     * @param applicationContext Spring 应用上下文
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        Map<RequestMappingInfo, HandlerMethod> map = handlerMapping.getHandlerMethods();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : map.entrySet()) {
            if (entry.getValue().hasMethodAnnotation(IgnorePermission.class)) {
                // 保存不需权限校验的 URI
                if (entry.getKey().getPathPatternsCondition() != null) {
                    IGNORE_URIS.addAll(entry.getKey().getPathPatternsCondition().getDirectPaths());
                }
            }
        }
    }

    /**
     * 检查给定的请求 URI 是否被标记为忽略权限校验
     *
     * @param requestUri 请求的 URI
     * @return 如果该 URI 被标记为忽略权限校验，则返回 true，否则返回 false
     */
    public static boolean isIgnored(String requestUri) {
        return IGNORE_URIS.contains(requestUri);
    }
}
