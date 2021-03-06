package com.teketik.test;

import org.springframework.boot.test.context.SpringBootTestContextBootstrapper;
import org.springframework.core.SpringProperties;
import org.springframework.test.context.CacheAwareContextLoaderDelegate;
import org.springframework.test.context.TestContextBootstrapper;
import org.springframework.test.context.cache.ContextCache;
import org.springframework.util.StringUtils;

import java.util.Optional;

/**
 * Similar to {@link SpringBootTestContextBootstrapper} with a {@link LimitingCacheAwareContextLoaderDelegate}.
 * <p>Use this {@link TestContextBootstrapper} to ensure the number of contexts created by the bootstrapped tests
 * never exceed the number configured by {@value #LIMIT_CONTEXT_SIZE_PROPERTY_NAME}.
 * @author antoine
 */
public class LimitingContextsBootstrapper extends SpringBootTestContextBootstrapper {

    /**
     * System property used to configure the limit size of the {@link ContextCache}
     * as a positive integer. May alternatively be configured via the
     * {@link org.springframework.core.SpringProperties} mechanism.
     * <p>Note that implementations of {@code ContextCache} are not required to
     * actually support a maximum cache size. Consult the documentation of the
     * corresponding implementation for details.
     * <p>No limit will be enforced if an invalid or missing value is provided
     * <p>This is not to be confused with {@link ContextCache#MAX_CONTEXT_CACHE_SIZE_PROPERTY_NAME} which
     * drives the size of the cache before eviction and is not an actual limit
     */
    public static final String LIMIT_CONTEXT_SIZE_PROPERTY_NAME = "spring.test.context.limitSize";

    private static final LimitingCacheAwareContextLoaderDelegate LIMITING_CACHE_AWARE_CONTEXT_LOADER_DELEGATE
            = buildDelegate();

    static LimitingCacheAwareContextLoaderDelegate buildDelegate() {
        return new LimitingCacheAwareContextLoaderDelegate(retrieveMaxCacheSize().orElse(null));
    }

    private static Optional<Integer> retrieveMaxCacheSize() {
        try {
            String maxSize = SpringProperties.getProperty(LIMIT_CONTEXT_SIZE_PROPERTY_NAME);
            if (StringUtils.hasText(maxSize)) {
                return Optional.of(Integer.valueOf(maxSize.trim()));
            }
        }
        catch (Exception ex) {
            // ignore
        }
        return Optional.empty();
    }

    @Override
    protected CacheAwareContextLoaderDelegate getCacheAwareContextLoaderDelegate() {
        return LIMITING_CACHE_AWARE_CONTEXT_LOADER_DELEGATE;
    }

}
