package com.adark.gm.di.component;

import com.adark.gm.common.AppComponent;
import com.adark.gm.di.module.UserLoginModule;
import com.adark.gm.mvp.ui.activity.UserLoginActivity;
import com.adark.lib.common.di.scope.ActivityScope;

import dagger.Component;

/**
 * Created by Shelly on 2017-3-15.
 */

@ActivityScope @Component(modules = UserLoginModule.class, dependencies = AppComponent.class)
public interface UserLoginComponent {
    void inject(UserLoginActivity activity);
}