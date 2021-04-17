package com.store.appstoreranking.glide;


import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import androidx.annotation.Nullable;


/**
 * @author wenxiaokang
 * @className CustomAppGlideModule
 * @description 生成Glide加载类
 * @date 4/13/21 5:06 PM
 */
public class GlideLoader {

    /**
     * 加载网络图片
     *
     * @param context   上下文
     * @param imageUrl  图片地址
     * @param imageView ImageView
     */
    public static void loadNetWorkResource(Context context, @Nullable String imageUrl, ImageView imageView) {
        GlideRequest<Bitmap> glideRequest = GlideApp.with(context).asBitmap();
        glideRequest.load(imageUrl)
                // 是否跳过内存缓存
                .skipMemoryCache(false)
                .into(imageView);
    }
}
