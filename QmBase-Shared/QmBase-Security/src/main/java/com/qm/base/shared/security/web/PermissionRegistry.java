
package com.qm.base.shared.security.web;

import com.qm.base.core.security.model.PermissionRecord;
import com.qm.base.shared.security.annotation.Permission;
import com.qm.base.shared.security.annotation.PermissionIgnore;
import com.qm.base.shared.security.util.AntPathMatcherUtil;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * PermissionRegistry 用于注册标记了 @PermissionIgnore 和 @Permission 的接口，
 * 供权限过滤流程在运行时快速判断。
 */
@Component
public class PermissionRegistry implements SmartInitializingSingleton {

    private static final Set<String> IGNORE_PATTERNS = ConcurrentHashMap.newKeySet();

    private static final Map<String, PermissionRecord> PERMISSION_PATTERNS = new ConcurrentHashMap<>();
    /**
     * Spring MVC 的请求映射处理器，用于获取所有的 HandlerMethod 映射
     */

    private final RequestMappingHandlerMapping handlerMapping;

    public PermissionRegistry(@Qualifier("requestMappingHandlerMapping") RequestMappingHandlerMapping handlerMapping) {
        this.handlerMapping = handlerMapping;
    }

    /**
     * 在所有单例 Bean 初始化完成后，收集所有权限相关的路由模式。
     */
    @Override
    public void afterSingletonsInstantiated() {
        IGNORE_PATTERNS.clear();
        PERMISSION_PATTERNS.clear();
        Map<RequestMappingInfo, HandlerMethod> map = handlerMapping.getHandlerMethods();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : map.entrySet()) {
            Set<String> patterns = extractPatterns(entry.getKey());
            if (patterns.isEmpty()) {
                continue;
            }
            HandlerMethod handlerMethod = entry.getValue();
            if (hasPermissionIgnore(handlerMethod)) {
                IGNORE_PATTERNS.addAll(patterns);
            }
            Permission permission = resolvePermission(handlerMethod);
            if (permission != null) {
                PermissionRecord record = new PermissionRecord(permission.name(), permission.action());
                for (String pattern : patterns) {
                    PERMISSION_PATTERNS.put(pattern, record);
                }
            }
        }
    }

    /**
     * 检查给定的请求 URI 是否被标记为忽略权限校验。
     *
     * @param requestUri 请求的 URI
     * @return 如果该 URI 被标记为忽略权限校验，则返回 true，否则返回 false
     */
    public static boolean isIgnored(String requestUri) {
        return matchPattern(requestUri, IGNORE_PATTERNS);
    }

    /**
     * 获取指定请求 URI 对应的权限信息。
     *
     * @param requestUri 请求的 URI
     * @return 对应的权限信息，如果不存在则返回 null
     */
    public static PermissionRecord getPermission(String requestUri) {
        PermissionRecord exact = PERMISSION_PATTERNS.get(requestUri);
        if (exact != null) {
            return exact;
        }
        for (Map.Entry<String, PermissionRecord> entry : PERMISSION_PATTERNS.entrySet()) {
            if (AntPathMatcherUtil.match(requestUri, entry.getKey())) {
                return entry.getValue();
            }
        }
        return null;
    }

    private static boolean hasPermissionIgnore(HandlerMethod handlerMethod) {
        return handlerMethod.hasMethodAnnotation(PermissionIgnore.class)
                || handlerMethod.getBeanType().isAnnotationPresent(PermissionIgnore.class);
    }

    private static Permission resolvePermission(HandlerMethod handlerMethod) {
        Permission methodPermission = handlerMethod.getMethodAnnotation(Permission.class);
        if (methodPermission != null) {
            return methodPermission;
        }
        return handlerMethod.getBeanType().getAnnotation(Permission.class);
    }

    private static Set<String> extractPatterns(RequestMappingInfo mappingInfo) {
        Set<String> patterns = new LinkedHashSet<>();
        if (mappingInfo.getPathPatternsCondition() != null) {
            patterns.addAll(mappingInfo.getPathPatternsCondition().getPatternValues());
        }
        if (mappingInfo.getPatternsCondition() != null) {
            patterns.addAll(mappingInfo.getPatternsCondition().getPatterns());
        }
        return patterns;
    }

    private static boolean matchPattern(String requestUri, Set<String> patterns) {
        if (patterns.contains(requestUri)) {
            return true;
        }
        List<String> dynamicPatterns = patterns.stream()
                .filter(pattern -> pattern.contains("{") || pattern.contains("*"))
                .collect(Collectors.toCollection(ArrayList::new));
        return AntPathMatcherUtil.match(requestUri, dynamicPatterns);
    }
}
