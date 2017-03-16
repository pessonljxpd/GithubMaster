package com.adark.gm.mvp.model;

import android.app.Application;
import com.adark.gm.mvp.contract.UserContract;
import com.adark.gm.mvp.model.api.cache.CacheManager;
import com.adark.gm.mvp.model.api.service.ServiceManager;
import com.adark.gm.mvp.model.entity.User;
import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BaseModel;
import io.rx_cache.DynamicKey;
import io.rx_cache.EvictDynamicKey;
import io.rx_cache.Reply;
import java.util.List;
import javax.inject.Inject;
import rx.Observable;
import rx.functions.Func1;

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

@ActivityScope public class UserModel extends BaseModel<ServiceManager, CacheManager> implements UserContract.Model {
    private static final int USER_PER_PAGE = 10;
    private Gson mGson;
    private Application mApplication;

    @Inject
    public UserModel(ServiceManager pServiceManager, CacheManager pCacheManager, Gson pGson, Application pApplication) {
        super(pServiceManager, pCacheManager);
        this.mGson = pGson;
        this.mApplication = pApplication;
    }

    @Override public void onDestory() {
        super.onDestory();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override public Observable<List<User>> getUsers(int pLastIdQueried, boolean pUpdate) {
        Observable<List<User>> listObservable =
            mServiceManager.getUserService().getUsers(pLastIdQueried, USER_PER_PAGE);
        return mCacheManager.getCommonCache()
            .getUsers(listObservable, new DynamicKey(pLastIdQueried), new EvictDynamicKey(pUpdate))
            .flatMap(new Func1<Reply<List<User>>, Observable<List<User>>>() {
                @Override public Observable<List<User>> call(Reply<List<User>> pListReply) {
                    return Observable.just(pListReply.getData());
                }
            });
    }
}