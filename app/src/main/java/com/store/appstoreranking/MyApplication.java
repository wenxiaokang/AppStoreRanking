package com.store.appstoreranking;

import android.app.Application;

import com.store.appstoreranking.net.NetUtils;

import me.jessyan.autosize.AutoSizeConfig;

/**
 * @author wenxiaokang
 * @className MyApplication
 * @description application
 * @date 4/13/21 4:35 PM
 */
public class MyApplication extends Application {
    private MyApplication myApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;
        // 适配Fragment
        AutoSizeConfig.getInstance().setCustomFragment(true);
        // 初始化网络请求
        NetUtils.init();
    }

    public MyApplication getMyApplication() {
        return myApplication;
    }

}
