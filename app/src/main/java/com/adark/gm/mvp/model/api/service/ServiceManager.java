package com.adark.gm.mvp.model.api.service;

import com.adark.lib.common.http.BaseServiceManager;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Shelly on 2017-3-13.
 */
@Singleton
public class ServiceManager implements BaseServiceManager{

    private CommonService mCommonService;
    private UserService mUserService;

    @Inject
    public ServiceManager(CommonService pCommonService, UserService pUserService) {
        mCommonService = pCommonService;
        mUserService = pUserService;
    }

    public CommonService getCommonService() {
        return mCommonService;
    }

    public UserService getUserService() {
        return mUserService;
    }

    /**
     * 这里可以释放一些资源(注意这里是单例，即不需要在activity的生命周期调用)
     */
    @Override public void onDestory() {

    }
}
