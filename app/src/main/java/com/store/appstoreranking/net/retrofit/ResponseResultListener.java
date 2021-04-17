package com.store.appstoreranking.net.retrofit;

/**
 * Author by ${wenxiaokang}, Date on 2018/11/2.
 * @author wenxiaokang
 */
public interface ResponseResultListener<T> {
    /**
     * 结果的回调
     * @param t
     */
    void success(T t);
    /**
     * 异常的回调
     * @param e
     */
    void failure(ApiException e);
}

