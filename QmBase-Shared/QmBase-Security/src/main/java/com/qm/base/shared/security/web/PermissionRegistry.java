
package com.qm.base.shared.security.web;

import com.qm.base.core.security.constants.SecurityConstant;
import com.qm.base.core.security.model.PermissionRecord;
import com.qm.base.core.utils.StringUtils;
import com.qm.base.shared.security.annotation.Permission;
import com.qm.base.shared.security.annotation.PermissionIgnore;
import com.qm.base.shared.security.util.AntPathMatcherUtil;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.annotation.Annotation;
import java.util.LinkedHashSet;
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

    private static final String ANY_METHOD = SecurityConstant.SECURITY_METHOD_DEFAULT;

    private final Set<RoutePattern> ignorePatterns = ConcurrentHashMap.newKeySet();

    private final Map<RoutePattern, PermissionRecord> permissionPatterns = new ConcurrentHashMap<>();
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
        ignorePatterns.clear();
        permissionPatterns.clear();
        Map<RequestMappingInfo, HandlerMethod> map = handlerMapping.getHandlerMethods();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : map.entrySet()) {
            Set<String> patterns = extractPatterns(entry.getKey());
            Set<String> methods = extractMethods(entry.getKey());
            if (patterns.isEmpty()) {
                continue;
            }
            HandlerMethod handlerMethod = entry.getValue();
            PermissionIgnore permissionIgnore = resolveAnnotation(handlerMethod, PermissionIgnore.class);
            if (permissionIgnore != null) {
                for (String pattern : patterns) {
                    for (String method : methods) {
                        ignorePatterns.add(new RoutePattern(pattern, method));
                    }
                }
            }
            Permission permission = resolveAnnotation(handlerMethod, Permission.class);
            if (permission != null) {
                for (String pattern : patterns) {
                    for (String method : methods) {
                        RoutePattern routePattern = new RoutePattern(pattern, method);
                        PermissionRecord record = new PermissionRecord(
                                pattern,
                                method,
                                permission.name(),
                                permission.action(),
                                StringUtils.isBlank(permission.vResourceType()) ? permission.name() : permission.vResourceType());
                        permissionPatterns.put(routePattern, record);
                    }
                }
            }
        }
    }

    /**
     * 检查给定的请求 URI 是否被标记为忽略权限校验。
     *
     * @param path          请求的 URI
     * @param requestMethod 请求方法
     * @return 如果该 URI 被标记为忽略权限校验，则返回 true，否则返回 false
     */
    public boolean isIgnored(String path, String requestMethod) {
        return matchPattern(path, requestMethod, ignorePatterns);
    }

    /**
     * 获取指定请求 URI 对应的权限信息。
     *
     * @param requestUri    请求的 URI
     * @param requestMethod 请求方法
     * @return 对应的权限信息，如果不存在则返回 null
     */
    public PermissionRecord getPermission(String requestUri, String requestMethod) {
        PermissionRecord exact = permissionPatterns.get(new RoutePattern(requestUri, requestMethod));
        if (exact != null) {
            return exact;
        }
        PermissionRecord anyMethod = permissionPatterns.get(new RoutePattern(requestUri, ANY_METHOD));
        if (anyMethod != null) {
            return anyMethod;
        }
        for (Map.Entry<RoutePattern, PermissionRecord> entry : permissionPatterns.entrySet()) {
            if (entry.getKey().matches(requestUri, requestMethod)) {
                return entry.getValue();
            }
        }
        return null;
    }

    private <A extends Annotation> A resolveAnnotation(HandlerMethod handlerMethod, Class<A> annotationClass) {
        A annotation = handlerMethod.getMethodAnnotation(annotationClass);
        if (annotation != null) {
            return annotation;
        }
        return handlerMethod.getBeanType().getAnnotation(annotationClass);
    }

    private Set<String> extractPatterns(RequestMappingInfo mappingInfo) {
        Set<String> patterns = new LinkedHashSet<>();
        if (mappingInfo.getPathPatternsCondition() != null) {
            patterns.addAll(mappingInfo.getPathPatternsCondition().getPatternValues());
        }
        if (mappingInfo.getPatternsCondition() != null) {
            patterns.addAll(mappingInfo.getPatternsCondition().getPatterns());
        }
        return patterns;
    }

    private Set<String> extractMethods(RequestMappingInfo mappingInfo) {
        Set<String> methods = mappingInfo.getMethodsCondition().getMethods().stream()
                .map(RequestMethod::name)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        if (methods.isEmpty()) {
            methods.add(ANY_METHOD);
        }
        return methods;
    }

    private boolean matchPattern(String path, String requestMethod, Set<RoutePattern> patterns) {
        if (patterns.contains(new RoutePattern(path, requestMethod))) {
            return true;
        }
        if (patterns.contains(new RoutePattern(path, ANY_METHOD))) {
            return true;
        }
        for (RoutePattern pattern : patterns) {
            if (pattern.isDynamic() && pattern.matches(path, requestMethod)) {
                return true;
            }
        }
        return false;
    }

    private record RoutePattern(String path, String method) {

        private boolean isDynamic() {
            return path != null && (path.contains("{") || path.contains("*"));
        }

        private boolean methodMatches(String requestMethod) {
            return ANY_METHOD.equals(method) || method.equalsIgnoreCase(requestMethod);
        }

        private boolean matches(String requestUri, String requestMethod) {
            return methodMatches(requestMethod) && AntPathMatcherUtil.match(requestUri, path);
        }
    }
}
