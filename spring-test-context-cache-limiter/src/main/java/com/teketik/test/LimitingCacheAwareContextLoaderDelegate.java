package com.teketik.test;

import org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate;

/**
 * Similar to {@link DefaultCacheAwareContextLoaderDelegate} with a {@link LimitingContextCache}.
 * @author antoine
 * @see LimitingContextsBootstrapper
 */
class LimitingCacheAwareContextLoaderDelegate extends DefaultCacheAwareContextLoaderDelegate {

    public LimitingCacheAwareContextLoaderDelegate(Integer maxContexts) {
        super(new LimitingContextCache(maxContexts));
    }

}
