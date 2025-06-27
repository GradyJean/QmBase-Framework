package com.qm.base.shared.security.propagation.feign;

import com.qm.base.core.common.constants.HeaderConstant;
import com.qm.base.core.utils.StringUtils;
import com.qm.base.shared.security.util.SecurityContextTransmitter;
import com.qm.base.shared.security.util.TrustedServiceChecker;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Component;

/**
 * Feign 拦截器：用于在请求头中自动注入当前线程中的安全上下文信息（如用户ID、角色等）。
 * <p>
 * 仅当目标服务为注册中心中我方微服务时，才注入 X-Security-Context 头部字段。
 * 依赖 TrustedServiceChecker 进行服务可信判断。
 */
@Component
@ConditionalOnClass({RequestInterceptor.class})
@ConditionalOnBean({TrustedServiceChecker.class})
public class SecurityContextFeignInterceptor implements RequestInterceptor {
    private final static Logger LOGGER = LoggerFactory.getLogger(SecurityContextFeignInterceptor.class);
    private final TrustedServiceChecker trustedServiceChecker;

    /**
     * 构造函数注入注册中心服务判断器
     */
    public SecurityContextFeignInterceptor(TrustedServiceChecker trustedServiceChecker) {
        this.trustedServiceChecker = trustedServiceChecker;
    }

    /**
     * Feign 拦截逻辑：
     * 1. 获取当前线程上下文并编码为 Base64 字符串。
     * 2. 判断目标服务是否为我方微服务。
     * 3. 若是，则添加 X-Security-Context 请求头。
     * 4. 否则打印调试日志。
     */
    @Override
    public void apply(RequestTemplate template) {
        String encoded = SecurityContextTransmitter.encodeContext();
        if (StringUtils.isNotBlank(encoded)) {
            String serviceId = template.feignTarget().name();  // 获取 @FeignClient(name="xxx") 的 name
            if (trustedServiceChecker.isInternal(serviceId)) {
                // 是我方微服务，设置 X-Security-Context
                template.header(HeaderConstant.SECURITY_CONTEXT, encoded);
            } else {
                LOGGER.debug("Skip injecting security context, not trusted service: {}", serviceId);
            }
        }
    }
}
