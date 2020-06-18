package com.mao.library.abs;

/**
 * Created by maozonghong
 * on 2019/11/21
 */

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;
import com.mao.library.http.OkHttpInstanceManager;

import java.io.InputStream;

/**
 * Description: Glide图片加载 替换原来的httpUrlConnection
 */

@GlideModule
public final class MyAppGlideModule extends AppGlideModule {

    @Override
    public void registerComponents(Context context, Glide glide, Registry registry) {
        //使用定义的OkhttpClient替换原有的请求
        OkHttpUrlLoader.Factory factory=new OkHttpUrlLoader.Factory(OkHttpInstanceManager.INSTANCE.getHttpClient());
        registry.replace(GlideUrl.class, InputStream.class,factory);
    }
}