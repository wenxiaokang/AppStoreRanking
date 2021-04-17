package com.store.appstoreranking.net;

import com.store.appstoreranking.constact.Constact;
import com.store.appstoreranking.mvp.base.BaseView;
import com.store.appstoreranking.net.okhttp.HeaderInterceptor;
import com.store.appstoreranking.net.okhttp.OkHttpProvider;
import com.store.appstoreranking.net.retrofit.BaseObserver;
import com.store.appstoreranking.net.retrofit.ResponseResultListener;
import com.store.appstoreranking.net.retrofit.Result;
import com.store.appstoreranking.net.retrofit.ResultTransformer;
import com.store.appstoreranking.net.retrofit.RetrofitClient;
import com.store.appstoreranking.rxjava.SchedulerProvider;
import com.trello.rxlifecycle2.LifecycleTransformer;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;

import io.reactivex.Observable;


/**
 * @author wenxiaokang
 */
public class NetUtils {
    private static ApiService apiService;

    /**
     * 初始化
     */
    public static void init() {
        apiService = RetrofitClient.getInstance()
                .init(Constact.BASE_API, OkHttpProvider.provideOkHttpClient(new RequestHeaderInterceptor()))
                .createService(ApiService.class);
    }

    public static ApiService getApiService() {
        return apiService;
    }

    public static <T> void subscribe(Observable<T> observable, LifecycleTransformer<T> lifecycleTransformer, ResponseResultListener<T> resultListener) {
        Observable<T> observable1 = observable.compose(ResultTransformer.handleResult())
                .compose(SchedulerProvider.getInstance().applySchedulers());
        if (lifecycleTransformer != null) {
            observable1.compose(lifecycleTransformer);
        }
        observable1.subscribe(new BaseObserver<>(resultListener));
    }

    private final static class RequestHeaderInterceptor extends HeaderInterceptor {
        @Override
        public Map<String, String> createCommonHeader() {
            return HeaderUtils.createCommonHeader();
        }
    }

    private final static class HeaderUtils {
        /**
         * 通用的header
         *
         * @return header
         */
        public static Map<String, String> createCommonHeader() {
            Map<String, String> header = new HashMap<>();
            return header;
        }
    }

}
