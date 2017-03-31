package com.adark.gm.di.component;

import com.adark.gm.common.AppComponent;
import com.adark.gm.di.module.SplashModule;
import com.adark.gm.mvp.ui.activity.SplashActivity;
import com.adark.lib.common.di.scope.ActivityScope;

import dagger.Component;

/**
 * Created by Shelly on 2017-3-22.
 */

@ActivityScope
@Component(modules = SplashModule.class, dependencies = AppComponent.class)
public interface SplashComponent {
    void inject(SplashActivity pActivity);
}