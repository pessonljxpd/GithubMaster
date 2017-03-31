package com.adark.gm.mvp.contract;

import com.adark.gm.mvp.model.entity.User;
import com.adark.lib.common.base.DefaultAdapter;
import com.adark.lib.common.mvp.BaseView;
import com.adark.lib.common.mvp.IModel;
import com.tbruyelle.rxpermissions.RxPermissions;
import java.util.List;
import rx.Observable;

/**
 * Created by Shelly on 2017-3-13.
 */

public interface UserContract {
    //对于经常使用的关于UI的方法可以定义到BaseView中,如显示隐藏进度条,和显示文字消息
    interface View extends BaseView {
        void setAdapter(DefaultAdapter pAdapter);

        void startLoadMore();

        void endLoadMore();

        RxPermissions getRxPermissions();
    }

    //Model层定义接口,外部只需关心model返回的数据,无需关心内部细节,及是否使用缓存
    interface Model extends IModel {
        Observable<List<User>> getUsers(int pLastIdQueried, boolean pUpdate);
    }
}