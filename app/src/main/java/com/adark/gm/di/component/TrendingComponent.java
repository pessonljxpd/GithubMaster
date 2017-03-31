package com.adark.gm.di.component;

import com.adark.gm.common.AppComponent;
import com.adark.gm.di.module.TrendingModule;
import com.adark.gm.mvp.ui.fragment.TrendingFragment;
import com.adark.lib.common.di.scope.ActivityScope;

import dagger.Component;

/**
 * Created by Shelly on 2017-3-22.
 */

@ActivityScope
@Component(modules = TrendingModule.class, dependencies = AppComponent.class)
public interface TrendingComponent {
    void inject(TrendingFragment pFragment);
}