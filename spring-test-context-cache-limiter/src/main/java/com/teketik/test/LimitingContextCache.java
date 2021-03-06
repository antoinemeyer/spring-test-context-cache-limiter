package com.teketik.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.lang.Nullable;
import org.springframework.test.context.MergedContextConfiguration;
import org.springframework.test.context.cache.DefaultContextCache;
import org.springframework.util.Assert;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Similar to {@link DefaultContextCache} with the ability to limit the number of contexts created.
 * <p>Ensures that the total number of contexts does not exceed {@link #maxContexts}.
 * @author antoine
 * @see LimitingContextsBootstrapper
 */
class LimitingContextCache extends DefaultContextCache {

    private static final Log logger = LogFactory.getLog(LimitingContextCache.class);

    private final AtomicInteger contextCounter = new AtomicInteger();

    @Nullable
    private final Integer maxContexts;

    public LimitingContextCache(
            @Nullable Integer maxContexts
    ) {
        if (logger.isDebugEnabled()) {
            if (maxContexts != null) {
                logger.debug("Limiting number of cached contexts to " + maxContexts);
            } else {
                logger.debug("No limit provided. Not limiting the number of cached contexts");
            }
        }
        this.maxContexts = maxContexts;
    }

    @Override
    public void put(MergedContextConfiguration key, ApplicationContext context) {
        if (maxContexts != null) {
            final int numberOfContexts = contextCounter.incrementAndGet();
            if (logger.isDebugEnabled()) {
                logger.debug("Number of contexts: " + numberOfContexts + "/" + maxContexts);
            }
            Assert.state(
                    numberOfContexts <= maxContexts.intValue(),
                    "Number of test contexts exceeds configured maximum: " + maxContexts
            );
        }
        super.put(key, context);
    };

}
