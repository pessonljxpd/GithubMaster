package com.adark.gm.mvp.model.api.cache;

import com.adark.gm.mvp.model.entity.User;
import io.rx_cache.DynamicKey;
import io.rx_cache.EvictProvider;
import io.rx_cache.LifeCache;
import io.rx_cache.Reply;
import java.util.List;
import java.util.concurrent.TimeUnit;
import rx.Observable;

/**
 * Created by Shelly on 2017-3-13.
 */

public interface CommonCache {

    @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES) Observable<Reply<List<User>>> getUsers(
        Observable<List<User>> pObservableUsers, DynamicKey pIdLastUserQueried, EvictProvider pEvictProvider);
}
