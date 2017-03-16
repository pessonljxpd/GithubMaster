package com.adark.gm.di.module;

import com.adark.gm.mvp.model.api.cache.CommonCache;
import dagger.Module;
import dagger.Provides;
import io.rx_cache.internal.RxCache;
import javax.inject.Singleton;

/**
 * Created by Shelly on 2017-3-13.
 */
@Module public class CacheModule {

    @Singleton @Provides CommonCache provideCommonCache(RxCache pRxCache) {
        return pRxCache.using(CommonCache.class);
    }
}
