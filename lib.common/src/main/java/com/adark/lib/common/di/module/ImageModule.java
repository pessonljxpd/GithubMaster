package com.adark.lib.common.di.module;

import com.adark.lib.common.widget.imageloader.BaseImageLoaderStrategy;
import com.adark.lib.common.widget.imageloader.glide.GlideImageLoaderStrategy;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by jess on 8/5/16 16:10
 * contact with jess.yan.effort@gmail.com
 */
@Module
public class ImageModule {

    @Singleton
    @Provides
    public BaseImageLoaderStrategy provideImageLoaderStrategy(GlideImageLoaderStrategy glideImageLoaderStrategy) {
        return glideImageLoaderStrategy;
    }

}
