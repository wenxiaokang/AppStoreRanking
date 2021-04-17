package com.store.appstoreranking.net.retrofit;

/**
 * Author by ${wenxiaokang}, Date on 2018/9/19.
 */
public class Result<T> {

    // 具体实体
    private T feed;

    public T getData() {
        return feed;
    }

    public void setData(T data) {
        this.feed = data;
    }

}
