package com.store.appstoreranking.net.okhttp;


import java.util.Map;
import java.util.concurrent.TimeUnit;

import me.jessyan.autosize.utils.LogUtils;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Author by ${wenxiaokang}, Date on 2018/9/19.
 */
public class OkHttpProvider {
    public static OkHttpClient provideOkHttpClient() {
        return provideOkHttpClient(new HeaderInterceptor() {
            @Override
            public Map<String, String> createCommonHeader() {
                return null;
            }
        });
    }

    public static OkHttpClient provideOkHttpClient(Interceptor interceptor) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(30, TimeUnit.SECONDS)
                //设置出现错误进行重新连接。
                .retryOnConnectionFailure(false)
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(60 * 1000, TimeUnit.MILLISECONDS)
                //拦截器
                .addInterceptor(interceptor)
                .addNetworkInterceptor(interceptor)//自定义网络拦截器
                // .addInterceptor(interceptor)
                // .sslSocketFactory(HttpsUtils.getSslSocketFactory(null, null, null))//创建一个证书工厂
                .hostnameVerifier((hostname, session) -> true);

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        // 打印一次请求的全部信息
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(loggingInterceptor);

        OkHttpClient okhttpClient = builder.build();
        return okhttpClient;
    }
}
