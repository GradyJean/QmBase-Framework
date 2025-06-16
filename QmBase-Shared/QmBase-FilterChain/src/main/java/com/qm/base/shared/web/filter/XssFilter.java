package com.qm.base.shared.web.filter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * XSS 过滤器，用于拦截所有请求路径，对请求参数进行包装过滤，防止跨站脚本攻击。
 */
public class XssFilter extends AbstractAntMatcherQmFilter {

    /**
     * 返回当前过滤器匹配的路径模式，默认拦截所有请求。
     */
    @Override
    protected String getPattern() {
        return "/**";
    }

    /**
     * 包装请求对象，使用自定义的 XssRequestWrapper 对请求参数进行清洗，防止 XSS 攻击。
     */
    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, QmFilterChain chain) throws IOException, ServletException {
        HttpServletRequest xssRequest = new XssRequestWrapper(request);
        chain.doFilter(xssRequest, response);
    }

    @Override
    public int getOrder() {
        return 1;
    }

    /**
     * 自定义请求包装器，重写参数获取方法，自动对参数值进行 XSS 清理处理。
     */
    private static class XssRequestWrapper extends HttpServletRequestWrapper {
        public XssRequestWrapper(HttpServletRequest request) {
            super(request);
        }

        /**
         * 重写获取单个参数值的方法，自动清洗潜在的 XSS 内容。
         */
        @Override
        public String getParameter(String name) {
            return clean(super.getParameter(name));
        }

        /**
         * 重写获取多个参数值的方法，循环清洗每个参数值中的 XSS 风险内容。
         */
        @Override
        public String[] getParameterValues(String name) {
            String[] values = super.getParameterValues(name);
            if (values == null) {
                return null;
            }
            String[] cleanedValues = new String[values.length];
            for (int i = 0; i < values.length; i++) {
                cleanedValues[i] = clean(values[i]);
            }
            return cleanedValues;
        }

        /**
         * 清理传入的字符串值，转义常见的 HTML 标签和脚本注入方式，防止 XSS 攻击。
         *
         * @param value 原始参数值
         * @return 清洗后的安全值
         */
        private String clean(String value) {
            if (value == null) return null;
            return value
                    .replaceAll("<", "&lt;")
                    .replaceAll(">", "&gt;")
                    .replaceAll("\\(", "&#40;")
                    .replaceAll("\\)", "&#41;")
                    .replaceAll("'", "&#39;")
                    .replaceAll("eval\\((.*)\\)", "")
                    .replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
        }
    }
}
