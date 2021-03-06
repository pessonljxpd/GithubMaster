package com.adark.lib.common.base;

import android.app.Application;
import android.content.Context;

import com.adark.lib.common.di.component.DaggerBaseComponent;
import com.adark.lib.common.di.module.AppModule;
import com.adark.lib.common.di.module.ClientModule;
import com.adark.lib.common.di.module.GlobeConfigModule;
import com.adark.lib.common.di.module.ImageModule;

import javax.inject.Inject;

import static com.adark.lib.common.utils.Preconditions.checkNotNull;

/**
 * 本项目由
 * mvp
 * +dagger2
 * +retrofit
 * +rxjava
 * +androideventbus
 * +butterknife组成
 */
public abstract class BaseApplication extends Application {
    protected final String TAG = this.getClass().getSimpleName();
    @Inject protected AppManager mAppManager;
    private static BaseApplication mApplication;
    private ClientModule mClientModule;
    private AppModule mAppModule;
    private ImageModule mImagerModule;
    private GlobeConfigModule mGlobeConfigModule;

    @Override public void onCreate() {
        super.onCreate();
        mApplication = this;
        this.mAppModule = new AppModule(this);//提供application
        DaggerBaseComponent.builder().appModule(mAppModule).build().inject(this);
        this.mImagerModule = new ImageModule();//图片加载框架默认使用glide
        this.mClientModule = new ClientModule(mAppManager);//用于提供okhttp和retrofit的单例
        this.mGlobeConfigModule = checkNotNull(getGlobeConfigModule(), "lobeConfigModule is required");
    }

    /**
     * 程序终止的时候执行
     */
    @Override public void onTerminate() {
        super.onTerminate();
        if (mClientModule != null) this.mClientModule = null;
        if (mAppModule != null) this.mAppModule = null;
        if (mImagerModule != null) this.mImagerModule = null;
        if (mAppManager != null) {//释放资源
            this.mAppManager.release();
            this.mAppManager = null;
        }
        if (mApplication != null) this.mApplication = null;
    }

    /**
     * 将app的全局配置信息封装进module(使用Dagger注入到需要配置信息的地方)
     */
    protected abstract GlobeConfigModule getGlobeConfigModule();

    public ClientModule getClientModule() {
        return mClientModule;
    }

    public AppModule getAppModule() {
        return mAppModule;
    }

    public ImageModule getImageModule() {
        return mImagerModule;
    }

    public AppManager getAppManager() {
        return mAppManager;
    }

    /**
     * 返回上下文
     */
    public static Context getContext() {
        return mApplication;
    }
}
