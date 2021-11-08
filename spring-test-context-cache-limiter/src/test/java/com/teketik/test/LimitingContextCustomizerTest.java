package com.teketik.test;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.core.SpringProperties;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.concurrent.atomic.AtomicInteger;

public class LimitingContextCustomizerTest {

    @AfterEach
    public void reset() {
        ((AtomicInteger) ReflectionTestUtils.getField(new LimitingContextCustomizer(), "CONTEXT_COUNTER")).set(0);
        SpringProperties.setProperty(LimitingContextCustomizer.LIMIT_CONTEXT_SIZE_PROPERTY_NAME, null);
    }

    @Test
    public void testLimitingContextCustomizerMaxContextsWithNoValue() {
        final LimitingContextCustomizer limitingContextCustomizer = new LimitingContextCustomizer();
        Assertions.assertNull(ReflectionTestUtils.getField(limitingContextCustomizer, "maxContexts"));
        limitingContextCustomizer.customizeContext(null, null);
    }

    @Test
    public void testLimitingContextCustomizerWithInvalidValue() {
        SpringProperties.setProperty(LimitingContextCustomizer.LIMIT_CONTEXT_SIZE_PROPERTY_NAME, "invalid");
        final LimitingContextCustomizer limitingContextCustomizer = new LimitingContextCustomizer();
        Assertions.assertNull(ReflectionTestUtils.getField(limitingContextCustomizer, "maxContexts"));
        limitingContextCustomizer.customizeContext(null, null);
    }

    @Test
    public void testLimitingContextCustomizer() {
        SpringProperties.setProperty(LimitingContextCustomizer.LIMIT_CONTEXT_SIZE_PROPERTY_NAME, "1");
        final LimitingContextCustomizer limitingContextCustomizer = new LimitingContextCustomizer();
        Assertions.assertEquals(Integer.valueOf(1), ReflectionTestUtils.getField(limitingContextCustomizer, "maxContexts"));
        ((AtomicInteger) ReflectionTestUtils.getField(limitingContextCustomizer, "CONTEXT_COUNTER")).set(0);
        limitingContextCustomizer.customizeContext(null, null);
        try {
            limitingContextCustomizer.customizeContext(null, null);
            Assert.fail();
        } catch (Exception e) {
            Assertions.assertEquals("Number of test contexts exceeds configured maximum: 1", e.getMessage());
        }
    }

}
