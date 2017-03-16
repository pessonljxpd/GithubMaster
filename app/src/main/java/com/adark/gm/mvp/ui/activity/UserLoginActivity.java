package com.adark.gm.mvp.ui.activity;

import android.content.Intent;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import butterknife.BindView;
import com.adark.gm.R;
import com.adark.gm.common.AppComponent;
import com.adark.gm.common.WEActivity;
import com.adark.gm.di.component.DaggerUserLoginComponent;
import com.adark.gm.di.module.UserLoginModule;
import com.adark.gm.mvp.contract.UserLoginContract;
import com.adark.gm.mvp.presenter.UserLoginPresenter;
import com.jess.arms.utils.UiUtils;
import com.jess.arms.widget.customfonts.MyEditText;
import com.jess.arms.widget.customfonts.MyTextView;
import com.tbruyelle.rxpermissions.RxPermissions;
import okhttp3.Credentials;

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
 * Created by Shelly on 2017-3-15.
 */

public class UserLoginActivity extends WEActivity<UserLoginPresenter>
    implements UserLoginContract.View, View.OnClickListener {
    //implements UserLoginContract.View, View.OnClickListener, ResizeRelativeLayout.KeybordStateListener {
    // UI references.
    @Nullable @BindView(R.id.login_rrl) RelativeLayout mRelativeLayout;
    @Nullable @BindView(R.id.login_iv_avatar_big) ImageView mAvatarBigView;
    @Nullable @BindView(R.id.login_iv_avatar_small) ImageView mAvatarSmallView;

    @Nullable @BindView(R.id.login_tv_username) MyEditText mUsernameView;
    @Nullable @BindView(R.id.login_tv_password) MyEditText mPasswordView;
    @Nullable @BindView(R.id.login_btn_sign_in) MyTextView mSignInView;
    @Nullable @BindView(R.id.login_pb) View mProgressView;
    @Nullable @BindView(R.id.login_form) View mLoginFormView;

    private static final String TAG = UserLoginActivity.class.getSimpleName();
    private RxPermissions mRxPermissions;
    private AppComponent mAppComponent;
    private int mRootBottom = Integer.MIN_VALUE;
    private ViewTreeObserver.OnGlobalLayoutListener mOnGlobalLayoutListener =
        new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override public void onGlobalLayout() {
                Rect rect = new Rect();
                mRelativeLayout.getGlobalVisibleRect(rect);
                // 进入Activity时会布局，第一次调用onGlobalLayout，先记录开始软键盘没有弹出时底部的位置
                if (mRootBottom == Integer.MIN_VALUE) {
                    mRootBottom = rect.bottom;
                    return;
                }
                // adjustResize，软键盘弹出后高度会变小
                if (rect.bottom < mRootBottom) {
                    setAvatarBigViewVisibility(false);
                } else {
                    setAvatarBigViewVisibility(true);
                }
            }
        };

    @Override protected void setupActivityComponent(AppComponent pAppComponent) {
        mAppComponent = pAppComponent;
        mRxPermissions = new RxPermissions(this);
        DaggerUserLoginComponent.builder()
            .appComponent(pAppComponent)
            .userLoginModule(new UserLoginModule(this)) //请将UserLoginModule()第一个首字母改为小写
            .build()
            .inject(this);
    }

    @Override protected View initView() {
        return LayoutInflater.from(this).inflate(R.layout.activity_user_login, null, false);
    }

    @Override protected void initData() {
        mRelativeLayout.getViewTreeObserver().addOnGlobalLayoutListener(mOnGlobalLayoutListener);
        mSignInView.setOnClickListener(this);
    }

    @Override public void showLoading() {
        if (!mProgressView.isShown()) {
            mProgressView.setVisibility(View.VISIBLE);
            mLoginFormView.setVisibility(View.GONE);
        }
    }

    @Override public void hideLoading() {
        if (mProgressView.isShown()) {
            mProgressView.setVisibility(View.GONE);
            mLoginFormView.setVisibility(View.VISIBLE);
        }
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

    @Override public RxPermissions getRxPermissions() {
        return null;
    }

    @Override public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.login_btn_sign_in:
                String authorization = createAuthorization();
                mPresenter.signIn(authorization);
                break;
            default:
                break;
        }
    }

    private String createAuthorization() {
        final String username = mUsernameView.getText().toString().trim();
        final String password = mPasswordView.getText().toString().trim();

        return Credentials.basic(username, password);
    }

    @Override public void setAvatarBigViewVisibility(boolean pVisible) {
        if (pVisible) {
            showAvatarBigView();
        } else {
            hideAvatarBigView();
        }
    }

    private void showAvatarBigView() {
        mAvatarBigView.postDelayed(new Runnable() {
            @Override public void run() {
                mAvatarBigView.setVisibility(View.VISIBLE);
            }
        }, 100);
        mAvatarSmallView.setVisibility(View.INVISIBLE);
    }

    private void hideAvatarBigView() {
        mAvatarBigView.setVisibility(View.GONE);
        mAvatarSmallView.setVisibility(View.VISIBLE);
    }
}