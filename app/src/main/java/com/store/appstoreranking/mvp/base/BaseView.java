package com.store.appstoreranking.mvp.base;

import com.trello.rxlifecycle2.LifecycleTransformer;

/**
 * @author wenxiaokang
 * @className BaseView
 * @description View基类
 * @date 4/12/21 10:40 PM
 */
public interface BaseView {
    /**
     * 绑定生命周期
     *
     * @param <T> 组建
     * @return
     */
    <T> LifecycleTransformer<T> bindToLife();

    /**
     * 错误回调
     *
     * @param msg  错误信息
     * @param code 错误代码
     */
    void showError(String msg, String code);

    /**
     * 网络接口请求完成，对应[onRequestStarted],在请求完成后调用
     */
    void complete();

}
