package com.adark.gm.di.module;

import com.adark.gm.mvp.contract.UserContract;
import com.adark.gm.mvp.model.UserModel;
import com.adark.gm.mvp.presenter.UserPresenter;
import com.google.gson.Gson;
import android.app.Application;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * 通过Template生成对应页面的MVP和Dagger代码,请注意输入框中输入的名字必须相同
 * 由于每个项目包结构都不一定相同,所以每生成一个文件需要自己导入import包名,可以在设置中设置自动导入包名
 * 请在对应包下按以下顺序生成对应代码,Contract->Model->Presenter->Activity->Module->Component
 * 因为生成Activity时,Module和Component还没生成,但是Activity中有它们的引用,所以会报错,但是不用理会
 * 继续将Module和Component生成完后,编译一下项目再回到Activity,按提示修改一个方法名即可
 * 如果想生成Fragment的相关文件,则将上面构建顺序中的Activity换为Fragment,并将Component中inject方法的参数改为此Fragment
 */

/**
 * Created by Shelly on 2017-3-14.
 */

@Module public class UserModule {
    private UserContract.View mView;

    /**
     * 构建UserModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     */
    public UserModule(UserContract.View pView) {
        this.mView = pView;
    }

    @ActivityScope @Provides UserContract.View provideUserView() {
        return this.mView;
    }

    @ActivityScope @Provides UserContract.Model provideUserModel(UserModel pModel) {
        return pModel;
    }
}