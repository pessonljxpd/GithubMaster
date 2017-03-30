package com.adark.gm.mvp.ui.holder;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import com.adark.gm.R;
import com.adark.gm.common.GMApplication;
import com.adark.gm.mvp.model.entity.User;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.adark.lib.common.base.BaseHolder;
import com.adark.lib.common.widget.imageloader.ImageLoader;
import com.adark.lib.common.widget.imageloader.glide.GlideImageConfig;
import rx.Observable;

/**
 * Created by Shelly on 2017-3-13.
 */

public class UserRecyclerItemHolder extends BaseHolder<User> {

    @Nullable @BindView(R.id.user_iv_avatar) ImageView mUserIvAvatar;
    @Nullable @BindView(R.id.user_tv_name) TextView mUserTvName;

    private ImageLoader mImageLoader;
    private GMApplication mApplication;

    public UserRecyclerItemHolder(View itemView) {
        super(itemView);
        mApplication = (GMApplication) itemView.getContext().getApplicationContext();
        mImageLoader = mApplication.getAppComponent().imageLoader();
    }

    @Override public void setData(User data) {
        Observable.just(data.getLogin()).subscribe(RxTextView.text(mUserTvName));

        mImageLoader.loadImage(mApplication,
            GlideImageConfig.builder().url(data.getAvatar_url()).imagerView(mUserIvAvatar).build());
    }
}
