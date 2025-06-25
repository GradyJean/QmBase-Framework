package com.qm.base.shared.security.util;

import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.util.List;

/**
 * AntPathMatcherUtil 是一个工具类，用于处理 Ant 风格的路径匹配。
 * 该类目前为空，可能在未来添加具体的路径匹配方法。
 *
 * @since 1.0
 */
public class AntPathMatcherUtil {
    /**
     * Ant 风格的路径匹配器实例。
     * 使用静态常量以提高性能，避免每次调用时创建新的实例。
     */
    private final static PathMatcher MATCHER = new AntPathMatcher();

    /**
     * 使用 Ant 风格的路径匹配器判断给定的路径是否匹配指定的模式。
     *
     * @param pattern 要匹配的 Ant 风格模式，例如 "/api/**"
     * @param paths   要检查的路径列表
     * @return 如果任意一个路径匹配模式，则返回 true；否则返回 false
     */
    public static boolean match(String pattern, List<String> paths) {
        if (pattern == null || paths == null || paths.isEmpty()) {
            return false;
        }
        for (String path : paths) {
            if (MATCHER.match(pattern, path)) {
                return true;
            }
        }
        return false;
    }
}
