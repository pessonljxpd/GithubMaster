package com.adark.gm.common;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import com.adark.gm.R;
import com.adark.lib.common.base.BaseActivity;
import com.adark.lib.common.mvp.BasePresenter;

import butterknife.BindView;

public abstract class GMActivity<P extends BasePresenter> extends BaseActivity<P> {
    @Nullable
    @BindView(R.id.common_toolbar)
    public Toolbar mToolbar;
    protected GMApplication mWeApplication;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    protected void initToolbar() {
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
        }
    }

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
