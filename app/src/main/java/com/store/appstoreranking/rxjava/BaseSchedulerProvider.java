package com.store.appstoreranking.rxjava;

import io.reactivex.ObservableTransformer;
import io.reactivex.Scheduler;

/**
 * Author by ${wenxiaokang}, Date on 2018/9/19.
 */
public interface BaseSchedulerProvider {

    Scheduler createIOThread();

    Scheduler createUIThread();

    Scheduler createComputationThread();

    /**
     * 线程切换
     *
     * @param <T>
     * @return
     */
    <T> ObservableTransformer<T, T> applySchedulers();

}
