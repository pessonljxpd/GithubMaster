package com.adark.gm.mvp.ui.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.adark.gm.R;
import com.adark.gm.common.AppComponent;
import com.adark.gm.common.WEActivity;
import com.adark.gm.di.component.DaggerMainComponent;
import com.adark.gm.di.module.MainModule;
import com.adark.gm.mvp.contract.MainContract;
import com.adark.gm.mvp.presenter.MainPresenter;
import com.jess.arms.utils.UiUtils;


import butterknife.BindView;

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
 * Created by Shelly on 2017-3-22.
 */

public class MainActivity extends WEActivity<MainPresenter> implements MainContract.View {

    @Nullable
    @BindView(R.id.main_tb)
    Toolbar mToolbar;
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void setupActivityComponent(AppComponent pAppComponent) {
        DaggerMainComponent
                .builder()
                .appComponent(pAppComponent)
                .mainModule(new MainModule(this)) //请将MainModule()第一个首字母改为小写
                .build()
                .inject(this);
    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        setSupportActionBar(mToolbar);
    }

    @Override
    protected void initData() {

    }


    @Override
    public void showLoading() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.base_toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.action_search:
                UiUtils.toastImage(R.drawable.ic_search_white_48dp, "Search");
                break;
            case R.id.action_notification:
                UiUtils.toastImage(R.drawable.ic_notifications_white_48dp, "Notifications");
                break;
            case R.id.action_sort:
                UiUtils.toastImage(R.drawable.ic_sort_white_48dp, "Sort");
                break;
            case R.id.action_refresh:
                UiUtils.toastText("please sign in");
                launchActivity(new Intent(mContext, UserLoginActivity.class));
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
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
        finish();
    }


}