package com.teketik.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.core.SpringProperties;
import org.springframework.test.util.ReflectionTestUtils;

public class LimitingContextsBootstrapperTest {

    @Test
    public void testLimitingContextsBootstrapperMaxContextsValue() {
        SpringProperties.setProperty(LimitingContextsBootstrapper.LIMIT_CONTEXT_SIZE_PROPERTY_NAME, "1");
        final LimitingCacheAwareContextLoaderDelegate limitingContextsBootstrapper = LimitingContextsBootstrapper.buildDelegate();
        final LimitingContextCache limitingContextCache = (LimitingContextCache) ReflectionTestUtils
                .getField(limitingContextsBootstrapper, "contextCache");
        Assertions.assertEquals(Integer.valueOf(1), ReflectionTestUtils.getField(limitingContextCache, "maxContexts"));
    }

    @Test
    public void testLimitingContextsBootstrapperMaxContextsWithNoValue() {
        final LimitingCacheAwareContextLoaderDelegate limitingContextsBootstrapper = LimitingContextsBootstrapper.buildDelegate();
        final LimitingContextCache limitingContextCache = (LimitingContextCache) ReflectionTestUtils
                .getField(limitingContextsBootstrapper, "contextCache");
        Assertions.assertNull(ReflectionTestUtils.getField(limitingContextCache, "maxContexts"));
    }

    @Test
    public void testLimitingContextsBootstrapperMaxContextsWithInvalidValue() {
        SpringProperties.setProperty(LimitingContextsBootstrapper.LIMIT_CONTEXT_SIZE_PROPERTY_NAME, "invalid");
        final LimitingCacheAwareContextLoaderDelegate limitingContextsBootstrapper = LimitingContextsBootstrapper.buildDelegate();
        final LimitingContextCache limitingContextCache = (LimitingContextCache) ReflectionTestUtils
                .getField(limitingContextsBootstrapper, "contextCache");
        Assertions.assertNull(ReflectionTestUtils.getField(limitingContextCache, "maxContexts"));
    }

    @Test
    public void testLimitingContextsBootstrapperGetCacheAwareContextLoaderDelegate() {
        final LimitingContextsBootstrapper limitingContextsBootstrapper = new LimitingContextsBootstrapper();
        Assertions.assertEquals(LimitingCacheAwareContextLoaderDelegate.class, limitingContextsBootstrapper.getCacheAwareContextLoaderDelegate().getClass());
    }

}
