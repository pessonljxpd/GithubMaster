package com.adark.gm.mvp.model;

import android.app.Application;

import com.adark.gm.mvp.contract.FeedContract;
import com.adark.gm.mvp.model.api.cache.CacheManager;
import com.adark.gm.mvp.model.api.service.ServiceManager;
import com.adark.lib.common.di.scope.ActivityScope;
import com.google.gson.Gson;
import com.adark.lib.common.mvp.BaseModel;

import javax.inject.Inject;

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
 * Created by Shelly on 2017-3-31.
 */

@ActivityScope
public class FeedModel extends BaseModel<ServiceManager, CacheManager> implements FeedContract.Model {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public FeedModel(ServiceManager pServiceManager, CacheManager pCacheManager, Gson pGson, Application pApplication) {
        super(pServiceManager, pCacheManager);
        this.mGson = pGson;
        this.mApplication = pApplication;
    }

    @Override
    public void onDestory() {
        super.onDestory();
        this.mGson = null;
        this.mApplication = null;
    }

}