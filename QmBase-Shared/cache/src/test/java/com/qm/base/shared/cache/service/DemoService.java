package com.qm.base.shared.cache.service;

import com.qm.base.shared.cache.core.annotation.QmCacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * DemoService - 示例服务类，用于测试 QmCache 缓存注解。
 */
@Service
public class DemoService {

    @QmCacheable(name = "demo", key = "#id", ttl = 5)
    public String getTime(String id) {
        return "Data for " + id + " at " + System.currentTimeMillis();
    }


    @QmCacheable(name = "demo", key = "'unless:' + #skip", ttl = 10, unless = "#skip")
    public String getUnless(boolean skip) {
        return "unless result at " + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME) + " (" + System.nanoTime() + ")";
    }

    @QmCacheable(name = "demo", key = "'null:' + #allow", ttl = 10, cacheNull = true)
    public String getCacheNull(boolean allow) {
        return allow ? null : "non-null at " + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME) + " (" + System.nanoTime() + ")";
    }
}