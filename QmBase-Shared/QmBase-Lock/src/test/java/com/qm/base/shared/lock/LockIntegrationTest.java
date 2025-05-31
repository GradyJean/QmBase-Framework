package com.qm.base.shared.lock;

import com.qm.base.shared.lock.annotation.Lock;
import com.qm.base.shared.lock.enums.LockType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Import(LockIntegrationTest.TestService.class)
@EnableAutoConfiguration
public class LockIntegrationTest {

    @SpringBootConfiguration
    @ComponentScan("com.qm.base.shared.lock")
    static class Config {
    }

    @Autowired
    private TestService testService;

    @Test
    void testLocalLockViaAnnotation() {
        boolean result = testService.doLocalLockedWork("local-test");
        assertThat(result).isTrue();
    }

    @Test
    void testDistributedLockViaAnnotation() {
        boolean result = testService.doDistributedLockedWork("dist-test");
        assertThat(result).isTrue();
    }

    @Service
    public static class TestService {

        @Lock(key = "'lock:' + #key", type = LockType.LOCAL, expire = 3000)
        public boolean doLocalLockedWork(String key) {
            return true; // simulate local work
        }

        @Lock(key = "'lock:' + #key", type = LockType.DISTRIBUTED, expire = 3000)
        public boolean doDistributedLockedWork(String key) {
            return true; // simulate distributed work
        }
    }
}
