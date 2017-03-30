package com.adark.gm.mvp.model.api.cache;

import com.adark.lib.common.http.BaseCacheManager;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Shelly on 2017-3-13.
 */
@Singleton public class CacheManager implements BaseCacheManager {

    private CommonCache mCommonCache;

    @Inject public CacheManager(CommonCache pCommonCache) {
        mCommonCache = pCommonCache;
    }

    public CommonCache getCommonCache() {
        return mCommonCache;
    }

    @Override public void onDestory() {

    }
}
