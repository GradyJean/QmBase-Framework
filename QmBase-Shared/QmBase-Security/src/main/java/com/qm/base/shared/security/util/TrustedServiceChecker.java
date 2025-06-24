package com.qm.base.shared.security.util;

import com.qm.base.core.utils.StringUtils;
import org.springframework.cloud.client.discovery.DiscoveryClient;

import java.util.List;

/**
 * 内部服务判断工具类：用于判断某服务 ID 或 host 是否为我方注册中心内的服务
 */
public record TrustedServiceChecker(DiscoveryClient discoveryClient) {

    /**
     * 判断给定服务名是否是注册中心中已注册的服务（用于 Feign 判断）
     *
     * @param serviceId 服务名
     * @return 是否为内部服务
     */
    public boolean isInternal(String serviceId) {
        return discoveryClient.getServices().contains(serviceId);
    }

    /**
     * 判断给定 host 是否是注册中心中已注册的服务（用于 RestTemplate 判断）
     *
     * @param host 请求中的 host 值
     * @return 是否为内部服务
     */
    public boolean isInternalHost(String host) {
        if (StringUtils.isBlank(host)) return false;
        List<String> services = discoveryClient.getServices(); // 如来自 Nacos/Eureka
        return services.contains(host);
    }
}