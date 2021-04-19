package com.store.appstoreranking;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.store.appstoreranking.adapter.AppRankingAdapter;
import com.store.appstoreranking.adapter.AppRecommendAdapter;
import com.store.appstoreranking.model.AppsModel;
import com.store.appstoreranking.mvp.base.BaseMvpActivity;
import com.store.appstoreranking.mvp.base.presenter.CreatePresenter;
import com.store.appstoreranking.mvp.base.presenter.PresenterVariable;
import com.store.appstoreranking.mvp.presenter.HomePresenter;
import com.store.appstoreranking.mvp.view.HomeView;
import com.store.appstoreranking.util.StatusBarUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author wenxiaokang
 * @className MainActivity
 * @description 首页
 * @date 4/12/21 9:16 PM
 */
@CreatePresenter(presenter = {HomePresenter.class})
public class MainActivity extends BaseMvpActivity implements HomeView {

    @PresenterVariable
    HomePresenter homePresenter;

    @BindView(R.id.rv_apps)
    RecyclerView rvApps;
    @BindView(R.id.swip)
    SwipeRefreshLayout swip;
    @BindView(R.id.sv_apps)
    SearchView svApps;

    private AppRankingAdapter appRankingAdapter;
    private AppRecommendAdapter appRecommendAdapter;
    private View headView;


    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    public void init() {
        initView();
        initRecyclerView();
        swip.setRefreshing(true);
        homePresenter.getDBData();
        initListen();
    }

    /**
     * 初始化UI
     */
    private void initView() {
        // 设置状态栏字体黑色
        StatusBarUtil.setTransparent(this);
        StatusBarUtil.setLightMode(this);
        //去掉输入的下划线
        svApps.setBackgroundColor(Color.TRANSPARENT);
    }

    /**
     * 设置监听
     */
    private void initListen() {
        // 刷新监听
        swip.setOnRefreshListener(() -> {
            homePresenter.initData();
        });
        // 加载更多监听
        appRankingAdapter.getLoadMoreModule().setOnLoadMoreListener(() -> {
            homePresenter.getMoreRankingAppList();
        });
        // 搜索监听
        svApps.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                homePresenter.searchList(newText);
                return false;
            }
        });
        // 搜索触发监听
        svApps.setOnClickListener(v -> {
            // 弹出软键盘
            svApps.setIconified(false);
        });
    }

    /**
     * 初始化recyclerView
     */
    private void initRecyclerView() {
        rvApps.setLayoutManager(new LinearLayoutManager(mContext));
        appRankingAdapter = new AppRankingAdapter();
        appRecommendAdapter = new AppRecommendAdapter();
        rvApps.setAdapter(appRankingAdapter);

        // 加载更多模块
        // 当数据不满一页时，是否继续自动加载
        appRankingAdapter.getLoadMoreModule().setEnableLoadMoreIfNotFullPage(false);
        // 添加头部
        headView = getLayoutInflater().inflate(R.layout.layout_home_head, (ViewGroup) rvApps.getParent(), false);
        appRankingAdapter.addHeaderView(headView);
        RecyclerView rvRecommend = headView.findViewById(R.id.rv_recommend);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        // 设置横向滑动
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvRecommend.setLayoutManager(linearLayoutManager);
        rvRecommend.setAdapter(appRecommendAdapter);
    }

    /**
     * 返回recommendList
     *
     * @param appList
     */
    @Override
    public void setRecommendAppList(List<AppsModel> appList) {
        //如果为我推荐没有数据，不显示为我推荐条目
        headView.setVisibility((appList != null && appList.size()>0) ? View.VISIBLE : View.GONE);
        appRecommendAdapter.setNewInstance(appList);
    }

    /**
     * 返回rankingList
     *
     * @param appsModels
     * @param hasMore 是否有更多数据
     */
    @Override
    public void setRankingAppList(List<AppsModel> appsModels, boolean hasMore) {
        swip.setRefreshing(false);
        appRankingAdapter.setNewInstance(appsModels);
        if (!hasMore) {
            // 所有数据加载完成
            appRankingAdapter.getLoadMoreModule().loadMoreEnd();
        }
    }
    /**
     * 返回rankingList
     *
     * @param appsModels
     * @param hasMore 是否有更多数据
     */
    @Override
    public void setMoreRankingAppList(List<AppsModel> appsModels, boolean hasMore) {
        appRankingAdapter.getData().addAll(appsModels);
        appRankingAdapter.getLoadMoreModule().loadMoreComplete();
        if (!hasMore) {
            // 所有数据加载完成
            appRankingAdapter.getLoadMoreModule().loadMoreEnd();
        }
    }

    /**
     * 数据库数据
     * @param appsRecommendModels  为我推荐数据
     * @param appsModels 排行数据
     */
    @Override
    public void setDBData(List<AppsModel> appsRecommendModels, List<AppsModel> appsModels) {
        headView.setVisibility((appsRecommendModels != null && appsRecommendModels.size()>0) ? View.VISIBLE : View.GONE);
        appRecommendAdapter.setNewInstance(appsRecommendModels);
        appRankingAdapter.setNewInstance(appsModels);

    }

}
