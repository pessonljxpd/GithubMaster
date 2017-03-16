package com.adark.gm.mvp.presenter;

import android.app.Application;
import android.widget.Toast;
import com.adark.gm.mvp.contract.UserContract;
import com.adark.gm.mvp.model.entity.User;
import com.adark.gm.mvp.ui.adapter.UserAdapter;
import com.jess.arms.base.AppManager;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.PermissionUtil;
import com.jess.arms.utils.RxUtils;
import com.jess.arms.widget.imageloader.ImageLoader;
import dagger.Module;
import java.util.ArrayList;
import java.util.List;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import javax.inject.Inject;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * 通过Template生成对应页面的MVP和Dagger代码,请注意输入框中输入的名字必须相同
 * 由于每个项目包结构都不一定相同,所以每生成一个文件需要自己导入import包名,可以在设置中设置自动导入包名
 * 请在对应包下按以下顺序生成对应代码,Contract->Model->Presenter->Activity->Module->Component
 * 因为生成Activity时,Module和Component还没生成,但是Activity中有它们的引用,所以会报错,但是不用理会
 * 继续将Module和Component生成完后,编译一下项目再回到Activity,按提示修改一个方法名即可
 * 如果想生成Fragment的相关文件,则将上面构建顺序中的Activity换为Fragment,并将Component中inject方法的参数改为此Fragment
 */

/**
 * Created by Shelly on 2017-3-13.
 */

@ActivityScope
public class UserPresenter extends BasePresenter<UserContract.Model, UserContract.View> {
    private static final String TAG = UserPresenter.class.getSimpleName();
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private ImageLoader mImageLoader;
    private AppManager mAppManager;

    private List<User> mUsers = new ArrayList<>();
    private DefaultAdapter mAdapter;
    private int mLastUserId = 1;

    @Inject public UserPresenter(UserContract.Model pModel, UserContract.View pRootView, RxErrorHandler pHandler,
        Application pApplication, ImageLoader pImageLoader, AppManager pAppManager) {
        super(pModel, pRootView);
        this.mErrorHandler = pHandler;
        this.mApplication = pApplication;
        this.mImageLoader = pImageLoader;
        this.mAppManager = pAppManager;

        mAdapter = new UserAdapter(mUsers);
        mRootView.setAdapter(mAdapter);
    }

    public void requestUsers(final boolean pPullToRefresh) {
        PermissionUtil.externalStorage(new PermissionUtil.RequestPermission() {
            @Override public void onRequestPermissionSuccess() {
                //request permission success, do something.
                Timber.d(TAG, "request permission");
            }
        }, mRootView.getRxPermissions(), mRootView, mErrorHandler);

        if (pPullToRefresh) {
            mLastUserId = 1;
        }

        mModel.getUsers(mLastUserId, pPullToRefresh)
            .subscribeOn(Schedulers.io())
            .retryWhen(new RetryWithDelay(3, 2))
            .doOnSubscribe(new Action0() {
                @Override public void call() {
                    if (pPullToRefresh) {
                        mRootView.showLoading();
                    } else {
                        mRootView.startLoadMore();
                    }
                }
            })
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(AndroidSchedulers.mainThread())
            .doAfterTerminate(new Action0() {
                @Override public void call() {
                    if (!pPullToRefresh) {
                        mRootView.hideLoading();
                    } else {
                        mRootView.endLoadMore();
                    }
                }
            })
            .compose(RxUtils.<List<User>>bindToLifecycle(mRootView))
            .subscribe(new ErrorHandleSubscriber<List<User>>(mErrorHandler) {
                @Override public void onNext(List<User> pUsers) {
                    mLastUserId = pUsers.get(pUsers.size() - 1).getId();
                    if (pPullToRefresh) mUsers.clear();
                    for (User user : pUsers) {
                        mUsers.add(user);
                    }
                    mAdapter.notifyDataSetChanged();
                }
            });
    }

    @Override public void onDestroy() {
        super.onDestroy();
        this.mAdapter = null;
        this.mUsers = null;
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }
}
