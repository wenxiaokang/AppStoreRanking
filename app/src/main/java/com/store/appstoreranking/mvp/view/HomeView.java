package com.store.appstoreranking.mvp.view;

import com.store.appstoreranking.model.AppsModel;
import com.store.appstoreranking.mvp.base.BaseView;

import java.util.List;

/**
 * @author wenxiaokang
 * @className HomeView
 * @description 首页view 数据回调接口
 * @date 4/13/21 10:00 PM
 */
public interface HomeView extends BaseView {
    void setRecommendAppList(List<AppsModel> entry);

    void setRankingAppList(List<AppsModel> entry, boolean hasMore);

    void setMoreRankingAppList(List<AppsModel> subList, boolean hasMore);

    void setDBData(List<AppsModel> appsRecommendModels, List<AppsModel> appsModels);
}
