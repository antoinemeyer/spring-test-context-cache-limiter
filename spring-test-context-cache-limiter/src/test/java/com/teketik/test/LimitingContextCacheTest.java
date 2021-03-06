package com.teketik.test;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.MergedContextConfiguration;

public class LimitingContextCacheTest {

    private final ApplicationContext applicationContext = Mockito.mock(ApplicationContext.class);
    private final MergedContextConfiguration context1 = Mockito.mock(MergedContextConfiguration.class);
    private final MergedContextConfiguration context2 = Mockito.mock(MergedContextConfiguration.class);

    @Test
    public void testNoLimit() throws Exception {
        final LimitingContextCache limitingContextCache = new LimitingContextCache(null);
        limitingContextCache.put(context1, applicationContext);
        Assertions.assertThat(limitingContextCache.get(context1)).isEqualTo(applicationContext);
        limitingContextCache.put(context2, applicationContext);
        Assertions.assertThat(limitingContextCache.get(context2)).isEqualTo(applicationContext);
    }

    @Test
    public void testLimit() {
        final LimitingContextCache limitingContextCache = new LimitingContextCache(1);
        limitingContextCache.put(context1, applicationContext);
        Assertions.assertThat(limitingContextCache.get(context1)).isEqualTo(applicationContext);
        try {
            limitingContextCache.put(context2, applicationContext);
            Assertions.fail("should have thrown exception");
        } catch (Exception e) {
            Assertions.assertThat(e.getClass()).isEqualTo(IllegalStateException.class);
            Assertions.assertThat(e.getMessage()).isEqualTo("Number of test contexts exceeds configured maximum: 1");
        }
    }
}
