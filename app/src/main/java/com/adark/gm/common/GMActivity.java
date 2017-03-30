package com.adark.gm.common;

import com.adark.lib.common.base.BaseActivity;
import com.adark.lib.common.mvp.BasePresenter;

public abstract class GMActivity<P extends BasePresenter> extends BaseActivity<P> {
    protected GMApplication mWeApplication;
    @Override
    protected void ComponentInject() {
        mWeApplication = (GMApplication) getApplication();
        setupActivityComponent(mWeApplication.getAppComponent());
    }

    //提供AppComponent(提供所有的单例对象)给子类，进行Component依赖
    protected abstract void setupActivityComponent(AppComponent appComponent);

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.mWeApplication = null;
    }
}
