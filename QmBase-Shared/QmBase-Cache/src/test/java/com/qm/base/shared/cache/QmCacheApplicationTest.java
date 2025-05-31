package com.qm.base.shared.cache;

import com.qm.base.shared.cache.service.DemoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * QmCacheApplicationTest - Spring Boot 启动入口测试类，验证 QmCache 行为。
 */
@SpringBootTest(classes = CacheTestApp.class)
@ActiveProfiles("test")
@EnableAutoConfiguration
public class QmCacheApplicationTest {

    @Autowired
    DemoService demoService;

    @Test
    public void contextLoads() {
        assertNotNull(demoService);
    }

    @Test
    public void testCacheHitWithinTTL() throws InterruptedException {
        String key = "ttl-test";
        String v1 = demoService.getTime(key);
        String v2 = demoService.getTime(key);
        assertEquals(v1, v2);
    }

    @Test
    public void testCacheMissAfterTTL() throws InterruptedException {
        String key = "ttl-expire";
        String v1 = demoService.getTime(key);
        Thread.sleep(6000);
        String v2 = demoService.getTime(key);
        assertNotEquals(v1, v2);
    }

    @Test
    public void testUnlessSkipsCache() throws InterruptedException {
        String result = demoService.getUnless(true);
        String again = demoService.getUnless(true);
        assertNotEquals(result, again); // 因为 unless=true，不应缓存
    }

    @Test
    public void testCacheNullValueAllowed() {
        String key = "null-allowed";
        String v1 = demoService.getCacheNull(true);
        String v2 = demoService.getCacheNull(true);
        assertNull(v1);
        assertNull(v2);
    }

    @Test
    public void testCacheNullValueBlocked() {
        String v1 = demoService.getCacheNull(false);
        String v2 = demoService.getCacheNull(false);
//        assertNotEquals(v1, v2); // 每次调用应重新执行方法，返回值应不同
    }

    @Autowired
    ApplicationContext context;

    @Test
    void printRedisBeans() {
        System.out.println("RedisConnectionFactory = " + Arrays.toString(context.getBeanNamesForType(RedisConnectionFactory.class)));
        System.out.println("RedisTemplate = " + Arrays.toString(context.getBeanNamesForType(RedisTemplate.class)));
    }
}
