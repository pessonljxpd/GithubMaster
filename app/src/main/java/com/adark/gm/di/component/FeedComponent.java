package com.adark.gm.di.component;

import com.adark.gm.common.AppComponent;
import com.adark.gm.di.module.FeedModule;
import com.adark.gm.mvp.ui.fragment.FeedFragment;
import com.adark.lib.common.di.scope.ActivityScope;

import dagger.Component;

/**
 * Created by Shelly on 2017-3-31.
 */

@ActivityScope
@Component(modules = FeedModule.class, dependencies = AppComponent.class)
public interface FeedComponent {
    void inject(FeedFragment pFragment);
}