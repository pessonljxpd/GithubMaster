package com.adark.lib.common.base;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.adark.lib.common.R;
import com.adark.lib.common.mvp.BasePresenter;
import com.adark.lib.common.receiver.NetworkStateBrocastReceiver;
import com.adark.lib.common.receiver.NetworkStateChangeObserver;
import com.adark.lib.common.utils.NetworkConstants;
import com.adark.lib.common.utils.UiUtils;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.zhy.autolayout.AutoFrameLayout;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import org.simple.eventbus.EventBus;

import java.util.Calendar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import timber.log.Timber;

public abstract class BaseActivity<P extends BasePresenter> extends RxAppCompatActivity {
    protected final String TAG = this.getClass().getSimpleName();
    protected BaseApplication mApplication;
    protected Context mContext;
    @Inject
    protected P mPresenter;
    private Unbinder mUnbinder;
    //    当前Activity渲染的视图View
    private View mContextView = null;
    //    监听网络状态变化
    private NetworkStateChangeObserver mNetworkStateChangeObserver;

    private static final String LAYOUT_LINEARLAYOUT = "LinearLayout";
    private static final String LAYOUT_FRAMELAYOUT = "FrameLayout";
    private static final String LAYOUT_RELATIVELAYOUT = "RelativeLayout";
    public static final String IS_NOT_ADD_ACTIVITY_LIST = "is_add_activity_list";//是否加入到activity的list，管理

    //    根据具体业务可以定制最小点击延迟的时长
    protected long mMiniClickDelayTime = 1000L;
    private long mLastClickTime = 0L;

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        View view = null;
        if (name.equals(LAYOUT_FRAMELAYOUT)) {
            view = new AutoFrameLayout(context, attrs);
        }

        if (name.equals(LAYOUT_LINEARLAYOUT)) {
            view = new AutoLinearLayout(context, attrs);
        }

        if (name.equals(LAYOUT_RELATIVELAYOUT)) {
            view = new AutoRelativeLayout(context, attrs);
        }

        if (view != null) return view;

        return super.onCreateView(name, context, attrs);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mApplication.getAppManager().setCurrentActivity(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mApplication.getAppManager().getCurrentActivity() == this) {
            mApplication.getAppManager().setCurrentActivity(null);
        }
    }

    @Nullable
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (isAllowFullScreen()) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                                 WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        super.onCreate(savedInstanceState);
        mApplication = (BaseApplication) getApplication();
        mContext = this;
        //如果intent包含了此字段,并且为true说明不加入到list
        // 默认为false,如果不需要管理(比如不需要在退出所有activity(killAll)时，退出此activity就在intent加此字段为true)
        boolean isNotAdd = false;
        if (getIntent() != null) {
            isNotAdd = getIntent().getBooleanExtra(IS_NOT_ADD_ACTIVITY_LIST, false);
        }

        if (!isNotAdd) {
            mApplication.getAppManager().addActivity(this);
        }

        if (useEventBus()) {//如果要使用eventbus请将此方法返回true
            EventBus.getDefault().register(this);//注册到事件主线
        }
        if (!isScreenRote()) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        mNetworkStateChangeObserver = new NetworkStateChangeObserver() {
            @Override
            public void onNetConnected(int pNetworkType) {
                onNetworkConnected(pNetworkType);
            }

            @Override
            public void onNetDisconnect() {
                onNetworkDisconnect();
            }
        };
        NetworkStateBrocastReceiver.registerNetworkStateBrocastReceiver(this);
        NetworkStateBrocastReceiver.registerNetworkStateChangeObserver(mNetworkStateChangeObserver);

        View mView = bindView();
        if (null == mView) {
            mContextView = LayoutInflater.from(this).inflate(bindLayout(), null);
        } else {
            mContextView = mView;
        }
        setContentView(mContextView);
        //绑定到butterknife
        mUnbinder = ButterKnife.bind(this);
        ComponentInject();//依赖注入
        initToolbar();
        initView();
        initData();
    }

    protected abstract void initToolbar();

    /**
     * 返回需要Activity渲染的view，此处给一个默认实现
     *
     * @return 默认为null
     */
    protected View bindView() {
        return null;
    }

    protected abstract int bindLayout();

    /**
     * 依赖注入的入口
     */
    protected abstract void ComponentInject();

    /**
     * 隐藏虚拟菜单
     */
    public void hideBottomUIMenu() {
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NetworkStateBrocastReceiver.unregisterNetworkStateBrocastReceiver(this);
        mApplication.getAppManager().removeActivity(this);
        if (mPresenter != null) mPresenter.onDestroy();//释放资源
        if (mUnbinder != Unbinder.EMPTY) mUnbinder.unbind();
        if (useEventBus())//如果要使用eventbus请将此方法返回true
        {
            EventBus.getDefault().unregister(this);
        }
        this.mPresenter = null;
        this.mUnbinder = null;
        this.mApplication = null;
    }

    /**
     * 是否允许全品
     *
     * @return 默认值为false，不允许全屏
     */
    public boolean isAllowFullScreen() {
        return false;
    }

    /**
     * 是否设置状态栏沉浸
     *
     * @return 默认值为true，设置状态栏沉浸
     */
    public boolean isSteepStatusBar() {
        return true;
    }

    /**
     * 是否允许屏幕旋转
     *
     * @return 默认值为false，屏幕不旋转
     */
    public boolean isScreenRote() {
        return false;
    }

    /**
     * 是否使用eventBus,默认为使用(true)，
     */
    protected boolean useEventBus() {
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    protected abstract void initView();

    protected abstract void initData();

    protected void onNetworkConnected(int pNetworkType) {
        Timber.tag(TAG).d("NetworkType=" + pNetworkType);
        switch (pNetworkType) {
            case NetworkConstants.NETWORK_CLASS_2_G:
            case NetworkConstants.NETWORK_CLASS_3_G:
            case NetworkConstants.NETWORK_CLASS_4_G:
                onMoblieNetworkConnected(pNetworkType);
                break;
            case NetworkConstants.NETWORK_WIFI:
                onWIFIConnected();
                break;
            default:
                break;
        }
        if (pNetworkType == NetworkConstants.NETWORK_WIFI) {
            onWIFIConnected();
        } else {
            UiUtils.toastImage(R.drawable.send, "当前正在使用移动网络");
        }
    }

    protected void onMoblieNetworkConnected(int pNetworkType) {
        //TODO 子类根据自己需求选择是否需要重写此方法
        Timber.tag(TAG).d("当前正在使用移动网络");
        if (pNetworkType == NetworkConstants.NETWORK_CLASS_2_G) {
            onWeakMobileNetConnect();
        }
    }


    protected void onWeakMobileNetConnect() {
        //TODO 子类根据自己需求选择是否需要重写此方法
        Timber.tag(TAG).d("当前移动网络网速较差，是否考虑开启极速模式？");
    }

    protected void onWIFIConnected() {
        //TODO 子类根据自己需求选择是否需要重写此方法
        Timber.tag(TAG).d("WIFI已连接");
    }

    protected void onNetworkDisconnect() {
        //TODO 子类根据自己需求选择是否需要重写此方法
        Timber.tag(TAG).d("onNetworkDisconnect");
    }

    /**
     * 判断是否重复点击
     *
     * @return
     */
    protected boolean noDoubleClick() {
        boolean noDoubleClick = false;
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - mLastClickTime > mMiniClickDelayTime) {
            mLastClickTime = currentTime;
            noDoubleClick = true;
        } else {
            noDoubleClick = false;
        }

        return noDoubleClick;
    }
}
