package com.store.appstoreranking.mvp.base.presenter;

import android.os.Bundle;
import android.util.Log;

import com.store.appstoreranking.mvp.base.BaseMvpActivity;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;

/**
 * @param <V> BaseView
 * @author wenxiaokang
 * @className BasePresenter
 * @description Presenter基类
 * @date 4/12/21 10:22 PM
 */
public class BasePresenter<V> {
    public static final String TAG = BasePresenter.class.getSimpleName();
    protected BaseMvpActivity mContext;
    protected V mView;



    private LifecycleProvider<ActivityEvent> provider;

    protected void onCleared() {
        Log.i(TAG, "onCleared");
    }

    public void attachView(BaseMvpActivity context, V view) {
        this.mContext = context;
        this.mView = view;
    }

    public void detachView() {
        this.mView = null;
    }

    public boolean isAttachView() {
        return this.mView != null;
    }

    public void onCreatePresenter(Bundle savedState) {

    }

    public void onDestroyPresenter() {
        this.mContext = null;
        detachView();
    }

    public void onSaveInstanceState(Bundle outState) {
        Log.i(TAG, "onSaveInstanceState");
    }

    public LifecycleProvider<ActivityEvent> getProvider() {
        return provider;
    }

    public void setProvider(LifecycleProvider<ActivityEvent> provider) {
        this.provider = provider;
    }
}
