package com.adark.gm.di.module;

import com.adark.gm.mvp.model.api.service.CommonService;
import com.adark.gm.mvp.model.api.service.UserService;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import retrofit2.Retrofit;

/**
 * Created by Shelly on 2017-3-13.
 */
@Module public class ServiceModule {

    @Singleton @Provides CommonService provideCommonService(Retrofit pRetrofit) {
        return pRetrofit.create(CommonService.class);
    }

    @Singleton @Provides UserService provideUserService(Retrofit pRetrofit) {
        return pRetrofit.create(UserService.class);
    }
}
