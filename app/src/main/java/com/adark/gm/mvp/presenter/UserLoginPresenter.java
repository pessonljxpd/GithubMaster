package com.adark.gm.mvp.presenter;

import android.app.Application;
import android.widget.Toast;
import com.adark.gm.common.DaggerAppComponent;
import com.adark.gm.mvp.contract.UserLoginContract;
import com.adark.gm.mvp.model.entity.User;
import com.google.gson.Gson;
import com.jess.arms.base.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.PermissionUtil;
import com.jess.arms.utils.RxUtils;
import com.jess.arms.widget.imageloader.ImageLoader;
import java.util.concurrent.TimeUnit;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import javax.inject.Inject;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
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
 * Created by Shelly on 2017-3-15.
 */

@ActivityScope public class UserLoginPresenter extends BasePresenter<UserLoginContract.Model, UserLoginContract.View> {
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private ImageLoader mImageLoader;
    private AppManager mAppManager;

    @Inject
    public UserLoginPresenter(UserLoginContract.Model pModel, UserLoginContract.View pRootView, RxErrorHandler pHandler,
        Application pApplication, ImageLoader pImageLoader, AppManager pAppManager) {
        super(pModel, pRootView);
        this.mErrorHandler = pHandler;
        this.mApplication = pApplication;
        this.mImageLoader = pImageLoader;
        this.mAppManager = pAppManager;
    }

    public void signIn(String pCredentials) {
        mModel.getLoginUser(pCredentials)
            .subscribeOn(Schedulers.io())
            .retryWhen(new RetryWithDelay(3, 2))
            .doOnSubscribe(new Action0() {
                @Override public void call() {
                    mRootView.showLoading();
                }
            })
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(AndroidSchedulers.mainThread())
            .doAfterTerminate(new Action0() {
                @Override public void call() {
                    mRootView.hideLoading();
                }
            })
            .compose(RxUtils.<User>bindToLifecycle(mRootView))
            .delaySubscription(2, TimeUnit.MILLISECONDS )
            .subscribe(new ErrorHandleSubscriber<User>(mErrorHandler) {
                private String mUserInfo;

                @Override public void onNext(User pUser) {
                    Gson gson = new Gson();
                    mUserInfo = gson.toJson(pUser, User.class);
                    mRootView.showMessage(mUserInfo);
                }
            });
    }

    @Override public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }
}
