package com.teketik.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.SpringProperties;
import org.springframework.lang.Nullable;
import org.springframework.test.context.ContextCustomizer;
import org.springframework.test.context.MergedContextConfiguration;
import org.springframework.test.context.cache.ContextCache;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * {@link ContextCustomizer} limiting the number of contexts that can be created.
 * @author Antoine Meyer
 */
public class LimitingContextCustomizer implements ContextCustomizer {

    private static final AtomicInteger CONTEXT_COUNTER = new AtomicInteger();

    private static final Log logger = LogFactory.getLog(LimitingContextCustomizer.class);

    /**
     * System property used to configure the limit size of the number of contexts authorized as a positive integer.
     * May alternatively be configured via the
     * {@link org.springframework.core.SpringProperties} mechanism.
     * <p>No limit will be enforced if an invalid or missing value is provided
     * <p>This is not to be confused with {@link ContextCache#MAX_CONTEXT_CACHE_SIZE_PROPERTY_NAME} which
     * drives the size of the cache before eviction and is not an actual limit
     */
    public static final String LIMIT_CONTEXT_SIZE_PROPERTY_NAME = "spring.test.context.limitSize";

    @Nullable
    private final Integer maxContexts;

    public LimitingContextCustomizer() {
        this.maxContexts = retrieveMaxCacheSize().orElse(null);
        if (logger.isDebugEnabled()) {
            if (maxContexts != null) {
                logger.debug("Limiting number of cached contexts to " + maxContexts);
            } else {
                logger.debug("No limit provided. Not limiting the number of cached contexts");
            }
        }
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
    public void customizeContext(ConfigurableApplicationContext context, MergedContextConfiguration mergedConfig) {
        final int numberOfContexts = CONTEXT_COUNTER.incrementAndGet();
        final boolean hasLimits = maxContexts != null;
        if (logger.isDebugEnabled()) {
            logger.debug(formatMessage(numberOfContexts, hasLimits));
        }
        if (hasLimits) {
            Assert.state(
                    numberOfContexts <= maxContexts.intValue(),
                    "Number of test contexts exceeds configured maximum: " + maxContexts
            );
        }
    }

    private String formatMessage(final int numberOfContexts, boolean hasLimits) {
        return "Number of contexts: " + numberOfContexts
                + (hasLimits ? "/" + maxContexts : " (no limit enforced)");
    }


}
