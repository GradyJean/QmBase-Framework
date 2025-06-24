package com.qm.base.shared.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qm.base.core.common.model.Result;
import com.qm.base.shared.web.filter.QmFilter;
import com.qm.base.shared.web.filter.QmFilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 安全异常处理过滤器，用于捕获过滤链中抛出的 SecurityException，
 * 并以统一的 JSON 格式返回客户端，避免异常传播为 500 错误。
 */
@Component
public class SecurityExceptionFilter implements QmFilter {
    // 用于将结果对象序列化为 JSON 字符串
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 始终匹配所有请求，确保此过滤器作为兜底异常处理器生效。
     */
    @Override
    public boolean match(HttpServletRequest request) {
        return true;
    }

    /**
     * 执行过滤链，捕获 SecurityException 异常并返回统一错误响应。
     */
    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, QmFilterChain chain) throws IOException, ServletException {
        try {
            chain.doFilter(request, response);
        } catch (com.qm.base.shared.security.exception.SecurityException e) {
            response.setStatus(e.getStatus());
            response.setContentType("application/json;charset=UTF-8");
            Result<String> result = Result.FAIL(e.getCode(), e.getMessage());
            response.getWriter().write(objectMapper.writeValueAsString(result));
        }
    }

    /**
     * 返回过滤器顺序，使用最大值确保此过滤器最后执行。
     */
    @Override
    public int getOrder() {
        return Integer.MAX_VALUE;
    }
}
