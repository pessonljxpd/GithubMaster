package com.adark.gm.mvp.ui.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import butterknife.BindView;
import com.adark.gm.R;
import com.adark.gm.common.AppComponent;
import com.adark.gm.common.WEActivity;
import com.adark.gm.di.component.DaggerUserComponent;
import com.adark.gm.di.module.UserModule;
import com.adark.gm.mvp.contract.UserContract;
import com.adark.gm.mvp.presenter.UserPresenter;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.utils.UiUtils;
import com.paginate.Paginate;
import com.tbruyelle.rxpermissions.RxPermissions;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import timber.log.Timber;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 通过Template生成对应页面的MVP和Dagger代码,请注意输入框中输入的名字必须相同
 * 由于每个项目包结构都不一定相同,所以每生成一个文件需要自己导入import包名,可以在设置中设置自动导入包名
 * 请在对应包下按以下顺序生成对应代码,Contract->Model->Presenter->Activity->Module->Component
 * 因为生成Activity时,Module和Component还没生成,但是Activity中有它们的引用,所以会报错,但是不用理会
 * 继续将Module和Component生成完后,编译一下项目再回到Activity,按提示修改一个方法名即可
 * 如果想生成Fragment的相关文件,则将上面构建顺序中的Activity换为Fragment,并将Component中inject方法的参数改为此Fragment
 */

/**
 * Created by Shelly on 2017-3-14.
 */

public class UserActivity extends WEActivity<UserPresenter>
    implements UserContract.View, SwipeRefreshLayout.OnRefreshListener {

    @Nullable @BindView(R.id.user_recycler_view) RecyclerView mUserRecyclerView;

    @Nullable @BindView(R.id.user_swipe_refresh) SwipeRefreshLayout mUserSwipeRefreshView;

    private Paginate mPaginate;
    private RxPermissions mRxPermissions;
    private boolean mIsLoadingMore;

    @Override protected void setupActivityComponent(AppComponent pAppComponent) {
        mRxPermissions = new RxPermissions(this);
        DaggerUserComponent.builder()
            .appComponent(pAppComponent)
            .userModule(new UserModule(this)) //请将UserModule()第一个首字母改为小写
            .build()
            .inject(this);
    }

    @Override protected View initView() {
        return LayoutInflater.from(this).inflate(R.layout.activity_user, null, false);
    }

    @Override protected void initData() {
        mPresenter.requestUsers(true);
    }

    @Override public void onRefresh() {
        mPresenter.requestUsers(true);
    }

    @Override public void showLoading() {
        Timber.tag(TAG).d("showLoading");
        Observable.just(1).subscribeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Integer>() {
            @Override public void call(Integer pInteger) {
                mUserSwipeRefreshView.setRefreshing(true);
            }
        });
    }

    @Override public void hideLoading() {
        Timber.tag(TAG).d("hideLoading");
        mUserSwipeRefreshView.setRefreshing(false);
    }

    @Override public void showMessage(@NonNull String pMessage) {
        checkNotNull(pMessage);
        UiUtils.SnackbarText(pMessage);
    }

    @Override public void launchActivity(@NonNull Intent pIntent) {
        checkNotNull(pIntent);
        UiUtils.startActivity(pIntent);
    }

    @Override public void killMyself() {
        finish();
    }

    @Override public void setAdapter(DefaultAdapter pAdapter) {
        initRecyclerView();
        mUserRecyclerView.setAdapter(pAdapter);
        initPaginate();
    }

    private void initRecyclerView() {
        mUserSwipeRefreshView.setOnRefreshListener(this);
        configRecyclerView(mUserRecyclerView, new GridLayoutManager(mContext, 2));
    }

    private void configRecyclerView(RecyclerView pUserRecyclerView, GridLayoutManager pGridLayoutManager) {
        mUserRecyclerView.setLayoutManager(pGridLayoutManager);
        //如果可以确定每个Item的高度是固定的，那么setHasFixedSize为true可以提高性能
        mUserRecyclerView.setHasFixedSize(true);
        mUserRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void initPaginate() {
        if (mPaginate == null) {
            Paginate.Callbacks callbacks = new Paginate.Callbacks() {

                @Override public void onLoadMore() {
                    Timber.tag(TAG).d(mPresenter == null ? "mPresenter==null" : mPresenter.toString());
                    mPresenter.requestUsers(true);
                }

                @Override public boolean isLoading() {
                    return mIsLoadingMore;
                }

                @Override public boolean hasLoadedAllItems() {
                    return false;
                }
            };

            mPaginate = Paginate.with(mUserRecyclerView, callbacks).build();
            mPaginate.setHasMoreDataToLoad(false);
        }
    }

    @Override public void startLoadMore() {
        mIsLoadingMore = true;
    }

    @Override public void endLoadMore() {
        mIsLoadingMore = false;
    }

    @Override public RxPermissions getRxPermissions() {
        return mRxPermissions;
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        mRxPermissions = null;
        mPaginate = null;
    }
}