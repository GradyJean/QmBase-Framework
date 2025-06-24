package com.qm.base.shared.security.propagation.resttemplate;

import com.qm.base.core.common.constants.HeaderConstant;
import com.qm.base.core.utils.StringUtils;
import com.qm.base.shared.security.util.SecurityContextTransmitter;
import com.qm.base.shared.security.util.TrustedServiceChecker;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

/**
 * RestTemplate 拦截器：用于在发起内部服务调用时注入安全上下文 Header
 */
public record SecurityContextRestTemplateInterceptor(
        TrustedServiceChecker trustedServiceChecker) implements ClientHttpRequestInterceptor {

    /**
     * 拦截 RestTemplate 请求，在请求头中注入安全上下文信息（X-Security-Context）
     * 仅对已注册为内部服务的 host 注入，防止敏感信息泄露到外部
     */
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        // 编码当前线程的 SecurityContext 为 Base64 字符串
        String encoded = SecurityContextTransmitter.encodeContext();
        if (StringUtils.isNotBlank(encoded)) {
            String host = request.getURI().getHost();
            // 判断为我方服务，则注入安全上下文 Header
            if (trustedServiceChecker.isInternalHost(host)) {
                request.getHeaders().add(HeaderConstant.SECURITY_CONTEXT, encoded);
            }
        }
        return execution.execute(request, body);
    }
}