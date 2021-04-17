package com.store.appstoreranking.net.retrofit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Function;

/**
 * Author by ${wenxiaokang}, Date on 2018/9/19.
 * <p>
 * 处理网络，解析异常，服务器返回数据异常等情况
 */
public class ResultTransformer {
    /**
     * 服务器API异常，解析异常，服务器返回数据异常的处理操作
     *
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T, T> handleResult() {
        return upstream -> upstream
                .onErrorResumeNext(new ErrorFunction<>())
                .flatMap(new ResponseFunction<>());
    }

    /**
     * 处理网络异常或者解析错误等
     *
     * @param <T>
     */
    private static class ErrorFunction<T> implements Function<Throwable, ObservableSource<? extends T>> {
        @Override
        public ObservableSource<? extends T> apply(Throwable throwable) throws Exception {
            return Observable.error(ApiException.ExceptionHandler.handlerException(throwable));
        }
    }

    /**
     * 处理常见的业务逻辑错误,非正常code，返回异常
     * 这里适配20000
     */
    private static class ResponseFunction<T> implements Function<T, ObservableSource<T>> {
        @Override
        public ObservableSource<T> apply(T tResult) throws Exception {
            return Observable.just(tResult);
        }
    }
}
