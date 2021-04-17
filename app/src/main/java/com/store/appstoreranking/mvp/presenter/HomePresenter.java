package com.store.appstoreranking.mvp.presenter;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.store.appstoreranking.manger.DBHelper;
import com.store.appstoreranking.model.AppDetailEntry;
import com.store.appstoreranking.model.AppEntry;
import com.store.appstoreranking.model.AppsModel;
import com.store.appstoreranking.model.AppsRankingEntry;
import com.store.appstoreranking.mvp.base.presenter.BasePresenter;
import com.store.appstoreranking.mvp.view.HomeView;
import com.store.appstoreranking.net.NetUtils;
import com.store.appstoreranking.net.retrofit.ApiException;
import com.store.appstoreranking.net.retrofit.ResponseResultListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author wenxiaokang
 * @className HomePresenter
 * @description 首页presenter层
 * @date 4/13/21 9:59 PM
 */
public class HomePresenter extends BasePresenter<HomeView> {
    // 横向列表数据
    private static int GROSSING_LIMIT = 10;
    // 竖向列表数据
    private static int FREE_LIMIT = 100;
    // 每页数据
    private int PAGE_LIMIT = 10;
    // 数据页码
    private int page = 1;


    // 分页数据
    private List<List<AppsModel>> appArrayList = new ArrayList<>();
    // 为我推荐
    private List<AppsModel> appsRecommendList = new ArrayList<>();
    // 数据库处理
    private DBHelper dbHelper;

    @Override
    public void onCreatePresenter(Bundle savedState) {
        super.onCreatePresenter(savedState);
        dbHelper = new DBHelper(mContext);
        initData();

    }

    /**
     * 打开页面时 获取数据库数据
     */
    public void getDBData() {
        List<AppsModel> appsRecommendModels = dbHelper.queryApps(DBHelper.TABLE_RECOMMEND);
        List<AppsModel> appsRakingModels = dbHelper.queryApps(DBHelper.TABLE_RANKING);
        if (appsRakingModels != null && appsRakingModels.size() > 0) {
            initPager(appsRakingModels);
            mView.setDBData(appsRecommendModels, appArrayList.get(0));
        } else {
            mView.setDBData(appsRecommendModels, appsRakingModels);
        }


    }

    /**
     * 分页数据
     *
     * @param appsModels
     */
    private void initPager(List<AppsModel> appsModels) {
        appArrayList.clear();
        // 将数据分成每页10条
        int pageNum = (appsModels.size() - 1) / PAGE_LIMIT + 1;
        for (int i = 0; i < pageNum; i++) {
            ArrayList<AppsModel> appEntries = new ArrayList<>();
            for (int i1 = 0; i1 < PAGE_LIMIT; i1++) {
                appEntries.add(appsModels.get(i1 + PAGE_LIMIT * i));
            }
            appArrayList.add(appEntries);
        }
    }

    /**
     * 获取数据
     */
    public void initData() {
        getRankingAppList();
        getRecommendAppList();
    }

    /**
     * 获取推荐列表数据
     */
    public void getRecommendAppList() {
        NetUtils.subscribe(NetUtils.getApiService().getTopGrossingApplications(String.valueOf(GROSSING_LIMIT)),
                mView.bindToLife(),
                new ResponseResultListener<AppsRankingEntry>() {
                    @Override
                    public void success(AppsRankingEntry appRankingInfoModel) {
                        appsRecommendList.clear();
                        ArrayList<AppsModel> appsModels = new ArrayList<>();
                        for (AppEntry appEntry : appRankingInfoModel.getFeed().getEntry()) {
                            appsModels.add(new AppsModel(appEntry));
                        }
                        appsRecommendList.addAll(appsModels);
                        mView.setRecommendAppList(appsModels);
                        dbHelper.addApps(appsModels, DBHelper.TABLE_RECOMMEND);
                    }

                    @Override
                    public void failure(ApiException e) {

                    }

                });
    }

    /**
     * 获取排行列表数据
     */
    public void getRankingAppList() {
        page = 1;
        NetUtils.subscribe(NetUtils.getApiService().getTopFreeApplications(String.valueOf(FREE_LIMIT)),
                mView.bindToLife(),
                new ResponseResultListener<AppsRankingEntry>() {
                    @Override
                    public void success(AppsRankingEntry appRankingInfoModel) {
                        getAppDetail(appRankingInfoModel.getFeed().getEntry());
                    }

                    @Override
                    public void failure(ApiException e) {

                    }
                });
    }

    /**
     * 加载更多
     */
    public void getMoreRankingAppList() {
        page++;
        // 分页数据
        if (page > appArrayList.size()) {
            //没有更多数据
            mView.setMoreRankingAppList(new ArrayList<AppsModel>(), false);
        } else {
            mView.setMoreRankingAppList(appArrayList.get(page - 1), page < appArrayList.size());
        }

    }

    /**
     * 获取App详情数据
     *
     * @param appsModel
     */
    public void getAppDetail(List<AppEntry> appsModel) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < appsModel.size(); i++) {
            if (i < appsModel.size() - 1) {
                stringBuilder.append(appsModel.get(i).getId().getAttributes().getId() + ",");
            } else {
                stringBuilder.append(appsModel.get(i).getId().getAttributes().getId());
            }
        }
        HashMap<String, String> hashMap = new HashMap<>(1);
        hashMap.put("id", String.valueOf(stringBuilder));
        NetUtils.subscribe(NetUtils.getApiService().getLookup(hashMap),
                mView.bindToLife(),
                new ResponseResultListener<AppDetailEntry>() {
                    @Override
                    public void success(AppDetailEntry appDetailEntry) {
                        appArrayList.clear();
                        // 将数据分成每页10条
                        int pageNum = (appsModel.size() - 1) / PAGE_LIMIT + 1;
                        for (int i = 0; i < pageNum; i++) {
                            ArrayList<AppsModel> appEntries = new ArrayList<>();
                            for (int i1 = 0; i1 < PAGE_LIMIT; i1++) {
                                appEntries.add(new AppsModel(appsModel.get(i1 + PAGE_LIMIT * i), appDetailEntry.getResults().get(i1 + PAGE_LIMIT * i)));

                            }
                            appArrayList.add(appEntries);
                        }
                        mView.setRankingAppList(appArrayList.get(0), true);
                        dbHelper.addApps(appArrayList.get(0), DBHelper.TABLE_RANKING);
                    }

                    @Override
                    public void failure(ApiException e) {

                    }
                });
    }

    /**
     * 搜索
     *
     * @param newText 搜索条件
     */
    public void searchList(String newText) {
        // 排行搜索
        if (TextUtils.isEmpty(newText)) {
            mView.setRankingAppList(appArrayList.get(0), true);
            mView.setRecommendAppList(appsRecommendList);
            return;
        }
        ArrayList<AppsModel> appsModels1 = new ArrayList<>();
        for (List<AppsModel> appsModels : appArrayList) {
            for (AppsModel appsModel : appsModels) {
                if (appsModel.isFitSearch(newText)) {
                    appsModels1.add(appsModel);
                }
            }
        }
        mView.setRankingAppList(appsModels1, false);
        // 为我推荐搜索
        ArrayList<AppsModel> appsModels = new ArrayList<>();
        for (AppsModel appsModel : appsRecommendList) {
            if (appsModel.isFitSearch(newText)) {
                appsModels.add(appsModel);
            }
        }
        mView.setRecommendAppList(appsModels);
    }
}
