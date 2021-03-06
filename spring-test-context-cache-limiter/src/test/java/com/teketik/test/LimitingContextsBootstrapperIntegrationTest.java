package com.teketik.test;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.BootstrapWith;
import org.springframework.test.util.ReflectionTestUtils;

import javax.annotation.Resource;

@BootstrapWith(LimitingContextsBootstrapper.class)
@SpringBootTest
public class LimitingContextsBootstrapperIntegrationTest {

    @Resource
    private ApplicationContext applicationContext;

    @Test
    public void testContextCorrectlyCreated() {
        Assertions.assertThat(applicationContext.getId()).startsWith("application");
        Assertions.assertThat(ReflectionTestUtils.getField(LimitingContextsBootstrapper.class, "LIMITING_CACHE_AWARE_CONTEXT_LOADER_DELEGATE")).isNotNull();
    }

}
