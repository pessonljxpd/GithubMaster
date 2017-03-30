package com.adark.gm.common;

import com.adark.lib.common.base.BaseFragment;
import com.adark.lib.common.mvp.BasePresenter;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by jess on 8/5/16 14:11
 * contact with jess.yan.effort@gmail.com
 */
public abstract class WEFragment<P extends BasePresenter> extends BaseFragment<P> {
    protected WEApplication mWeApplication;
    @Override
    protected void ComponentInject() {
        mWeApplication = (WEApplication)mActivity.getApplication();
        setupFragmentComponent(mWeApplication.getAppComponent());
    }

    //提供AppComponent(提供所有的单例对象)给子类，进行Component依赖
    protected abstract void setupFragmentComponent(AppComponent appComponent);

    @Override
    public void onDestroy() {
        super.onDestroy();
        RefWatcher watcher = WEApplication.getRefWatcher(getActivity());//使用leakCanary检测fragment的内存泄漏
        if (watcher != null) {
            watcher.watch(this);
        }
        this.mWeApplication =null;
    }
}
