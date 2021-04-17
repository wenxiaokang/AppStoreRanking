package com.store.appstoreranking.mvp.base;


import android.content.Intent;
import android.os.Bundle;

import com.store.appstoreranking.mvp.base.presenter.BasePresenter;
import com.store.appstoreranking.mvp.base.presenter.PresenterDispatch;
import com.store.appstoreranking.mvp.base.presenter.PresenterProviders;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;

/**
 * @author wenxiaokang
 * @className BaseMvpActivity
 * @description Activity基类
 * @date 4/12/21 10:28 PM
 */
public abstract class BaseMvpActivity<P extends BasePresenter> extends RxAppCompatActivity implements BaseView {

    private PresenterProviders mPresenterProviders;
    private PresenterDispatch mPresenterDispatch;
    public BaseMvpActivity mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        ButterKnife.bind(this);
        mContext = this;
        mPresenterProviders = PresenterProviders.inject(this);
        mPresenterDispatch = new PresenterDispatch(mPresenterProviders);
        mPresenterDispatch.attachView(this, this);
        mPresenterDispatch.onCreatePresenter(savedInstanceState);
        init();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mPresenterDispatch.onSaveInstanceState(outState);
    }

    protected abstract int getContentView();

    public abstract void init();

    protected P getPresenter() {
        return mPresenterProviders.getPresenter(0);
    }

    public PresenterProviders getPresenterProviders() {
        return mPresenterProviders;
    }

    @Override
    public void complete() {

    }

    @Override
    public void showError(String msg, String code) {
        //  处理错误信息
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenterDispatch.detachView();
    }


    /**
     * 绑定生命周期
     */
    @Override
    public <T> LifecycleTransformer<T> bindToLife() {
        return bindUntilEvent(ActivityEvent.DESTROY);
    }

}

