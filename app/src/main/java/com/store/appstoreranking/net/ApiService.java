package com.store.appstoreranking.net;


import com.store.appstoreranking.model.AppDetailEntry;
import com.store.appstoreranking.model.AppRankingInfoModel;
import com.store.appstoreranking.model.AppsRankingEntry;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * Author by ${wenxiaokang}, Date on 2018/9/19.
 * api 网络请求
 */

public interface ApiService {

    @GET("rss/topgrossingapplications/limit={limit}/json")
    Observable<AppsRankingEntry> getTopGrossingApplications(@Path("limit") String limit);

    @GET("rss/topfreeapplications/limit={limit}/json")
    Observable<AppsRankingEntry> getTopFreeApplications(@Path("limit") String limit);

    @GET("lookup")
    Observable<AppDetailEntry> getLookup(@QueryMap Map<String, String> map);

}
