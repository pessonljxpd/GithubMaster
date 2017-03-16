package com.adark.gm.mvp.ui.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import butterknife.BindView;
import com.adark.gm.R;
import com.adark.gm.common.AppComponent;
import com.adark.gm.common.WEActivity;
import com.adark.gm.di.component.DaggerUserLoginComponent;
import com.adark.gm.di.module.UserLoginModule;
import com.adark.gm.mvp.contract.UserLoginContract;
import com.adark.gm.mvp.presenter.UserLoginPresenter;
import com.jess.arms.utils.UiUtils;
import com.tbruyelle.rxpermissions.RxPermissions;
import okhttp3.Credentials;
import okhttp3.Request;

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
    // UI references.
    @Nullable @BindView(R.id.login_tv_username) EditText mUsernameView;
    @Nullable @BindView(R.id.login_tv_password) EditText mPasswordView;
    @Nullable @BindView(R.id.login_btn_sign_in) Button mSignInView;
    @Nullable @BindView(R.id.login_pb) View mProgressView;
    @Nullable @BindView(R.id.login_form) View mLoginFormView;

    private static final String TAG = UserLoginActivity.class.getSimpleName();
    private RxPermissions mRxPermissions;
    private AppComponent mAppComponent;

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
        mUsernameView.setText("pessonljxpd");
        mPasswordView.setText("xing19910716");
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

        //mWeApplication.getAppComponent().okHttpClient().newBuilder().addNetworkInterceptor(new Interceptor() {
        //    @Override public Response intercept(Chain chain) throws IOException {
        //        String credentials = Credentials.basic(username, password);
        //
        //        return chain.proceed(chain.request().newBuilder().addHeader("Authorization", credentials).build());
        //    }
        //}).build();

        //mWeApplication.getAppComponent().okHttpClient().newBuilder().authenticator(new Authenticator() {
        //    @Override public Request authenticate(Route route, Response response) throws IOException {
        //        String credentials = Credentials.basic(username, password);
        //        return response.request().newBuilder().addHeader("Authorization", credentials).build();
        //    }
        //}).build();

        //mAppComponent.okHttpClient().newBuilder().addInterceptor(new Interceptor() {
        //    @Override public Response intercept(Chain chain) throws IOException {
        //        String credentials = Credentials.basic(username, password);
        //        return chain.proceed(chain.request().newBuilder().addHeader("Authorization", credentials).build());
        //    }
        //});
    }
}