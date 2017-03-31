package com.adark.gm.di.component;

import com.adark.gm.common.AppComponent;
import com.adark.gm.di.module.UserResumeModule;
import com.adark.gm.mvp.ui.fragment.UserResumeFragment;
import com.adark.lib.common.di.scope.ActivityScope;

import dagger.Component;

/**
 * Created by Shelly on 2017-3-31.
 */

@ActivityScope
@Component(modules = UserResumeModule.class, dependencies = AppComponent.class)
public interface UserResumeComponent {
    void inject(UserResumeFragment pFragment);
}