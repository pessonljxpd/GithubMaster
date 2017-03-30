package com.adark.lib.common.di.component;

import com.adark.lib.common.base.BaseApplication;
import com.adark.lib.common.di.module.AppModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by jess on 14/12/2016 13:58
 * Contact with jess.yan.effort@gmail.com
 */
@Singleton
@Component(modules={AppModule.class})
public interface BaseComponent {
    void inject(BaseApplication application);
}
