package com.adark.gm.mvp.ui.activity;

import android.content.Intent;
import android.icu.text.NumberFormat;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.adark.gm.R;
import com.adark.gm.common.AppComponent;
import com.adark.gm.common.GMActivity;
import com.adark.gm.di.component.DaggerSplashComponent;
import com.adark.gm.di.module.SplashModule;
import com.adark.gm.mvp.contract.SplashContract;
import com.adark.gm.mvp.presenter.SplashPresenter;
import com.adark.lib.common.utils.UiUtils;


import butterknife.BindView;
import butterknife.OnClick;

import static com.adark.lib.common.utils.Preconditions.checkNotNull;

/**
 * 通过Template生成对应页面的MVP和Dagger代码,请注意输入框中输入的名字必须相同
 * 由于每个项目包结构都不一定相同,所以每生成一个文件需要自己导入import包名,可以在设置中设置自动导入包名
 * 请在对应包下按以下顺序生成对应代码,Contract->Model->Presenter->Activity->Module->Component
 * 因为生成Activity时,Module和Component还没生成,但是Activity中有它们的引用,所以会报错,但是不用理会
 * 继续将Module和Component生成完后,编译一下项目再回到Activity,按提示修改一个方法名即可
 * 如果想生成Fragment的相关文件,则将上面构建顺序中的Activity换为Fragment,并将Component中inject方法的参数改为此Fragment
 */

/**
 * Created by Shelly on 2017-3-22.
 */

public class SplashActivity extends GMActivity<SplashPresenter> implements SplashContract.View {
    @Nullable
    @BindView(R.id.splash_btn_jump)
    AppCompatButton mBtnCountDownJump;
    @Nullable
    @BindView(R.id.splash_ll_bottom)
    LinearLayout mLinearLayout;
    private static final String TAG = SplashActivity.class.getSimpleName();
    private SplashCountDownTimer mCountDownTimer;

    @Override
    protected void setupActivityComponent(AppComponent pAppComponent) {
        DaggerSplashComponent
                .builder()
                .appComponent(pAppComponent)
                .splashModule(new SplashModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {
        mCountDownTimer = new SplashCountDownTimer(3000, 1000);
        mCountDownTimer.start();
    }

    @Override
    protected void initData() {
    }

    @OnClick(R.id.splash_btn_jump)
    public void loadMainActivity(View pView) {
        if (!noDoubleClick()) return;
        if (pView.getId() == R.id.splash_btn_jump) {
            onCountDownTimerCancel();
            launchActivity(new Intent(mContext, MainActivity.class));
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    private void onCountDownTimerCancel() {
        mCountDownTimer.cancel();
    }

    @Override
    public void killMyself() {
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

    class SplashCountDownTimer extends CountDownTimer {
        public SplashCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            mBtnCountDownJump.setText((millisUntilFinished / 1000) + "s");
        }

        @Override
        public void onFinish() {
            launchActivity(new Intent(mContext, MainActivity.class));
        }
    }
}