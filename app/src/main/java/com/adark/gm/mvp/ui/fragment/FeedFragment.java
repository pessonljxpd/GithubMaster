package com.adark.gm.mvp.ui.fragment;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;

import com.adark.gm.R;
import com.adark.gm.common.AppComponent;
import com.adark.gm.common.GMFragment;
import com.adark.gm.di.component.DaggerFeedComponent;
import com.adark.gm.di.module.FeedModule;
import com.adark.gm.mvp.contract.FeedContract;
import com.adark.gm.mvp.presenter.FeedPresenter;
import com.adark.lib.common.utils.UiUtils;

import static com.adark.lib.common.utils.Preconditions.checkNotNull;

/**
 * Created by Shelly on 2017-3-31.
 */

public class FeedFragment extends GMFragment<FeedPresenter> implements FeedContract.View {


    public static FeedFragment newInstance() {
        FeedFragment fragment = new FeedFragment();
        return fragment;
    }

    @Override
    protected void setupFragmentComponent(AppComponent pAppComponent) {
        DaggerFeedComponent
                .builder()
                .appComponent(pAppComponent)
                .feedModule(new FeedModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected View initView() {
        return LayoutInflater.from(getActivity()).inflate(R.layout.fragment_feed, null, false);
    }

    @Override
    protected void initData() {

    }

    /**
     * 此方法是让外部调用使fragment做一些操作的,比如说外部的activity想让fragment对象执行一些方法,
     * 建议在有多个需要让外界调用的方法时,统一传bundle,里面存一个what字段,来区分不同的方法,在setData
     * 方法中就可以switch做不同的操作,这样就可以用统一的入口方法做不同的事,和message同理
     * <p>
     * 使用此方法时请注意调用时fragment的生命周期,如果调用此setData方法时onActivityCreated
     * 还没执行,setData里调用presenter的方法时,是会报空的,因为dagger注入是在onActivityCreated
     * 方法中执行的,如果要做一些初始化操作,可以不必让外部调setData,在内部onActivityCreated中
     * 初始化就可以了
     *
     * @param pData
     */

    @Override
    public void setData(Object pData) {

    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String pMessage) {
        checkNotNull(pMessage);
        UiUtils.SnackbarText(pMessage);
    }

    @Override
    public void launchActivity(@NonNull Intent pIntent) {
        checkNotNull(pIntent);
        UiUtils.startActivity(pIntent);
    }

    @Override
    public void killMyself() {

    }

}