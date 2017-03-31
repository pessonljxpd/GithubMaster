package com.adark.gm.di.component;

import com.adark.gm.common.AppComponent;
import com.adark.gm.di.module.UserModule;
import com.adark.gm.mvp.ui.activity.UserActivity;
import com.adark.lib.common.di.scope.ActivityScope;

import dagger.Component;

/**
 * Created by Shelly on 2017-3-14.
 */

@ActivityScope @Component(modules = UserModule.class, dependencies = AppComponent.class)
public interface UserComponent {
    void inject(UserActivity activity);
}