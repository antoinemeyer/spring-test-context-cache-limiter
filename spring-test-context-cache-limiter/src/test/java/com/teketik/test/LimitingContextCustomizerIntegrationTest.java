package com.teketik.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Resource;

@SpringBootTest
public class LimitingContextCustomizerIntegrationTest {

    @Resource
    private ApplicationContext applicationContext;

    @Test
    public void testContextCorrectlyCreated() {
        Assertions.assertTrue(applicationContext.getId().startsWith("application"));
        final AtomicInteger contextCounter = (AtomicInteger) ReflectionTestUtils.getField(LimitingContextCustomizer.class, "CONTEXT_COUNTER");
        Assertions.assertEquals(Integer.valueOf(1), contextCounter.get());
    }

}
