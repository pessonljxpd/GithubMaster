package com.adark.gm.di.component;

import com.adark.gm.common.AppComponent;
import com.adark.gm.di.module.MainModule;
import com.adark.gm.mvp.ui.activity.MainActivity;
import com.adark.lib.common.di.scope.ActivityScope;

import dagger.Component;


/**
 * Created by Shelly on 2017-3-22.
 */

@ActivityScope
@Component(modules = MainModule.class, dependencies = AppComponent.class)
public interface MainComponent {
    void inject(MainActivity activity);
}