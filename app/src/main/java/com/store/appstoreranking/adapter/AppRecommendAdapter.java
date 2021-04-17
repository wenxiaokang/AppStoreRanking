package com.store.appstoreranking.adapter;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.store.appstoreranking.R;
import com.store.appstoreranking.glide.GlideLoader;
import com.store.appstoreranking.model.AppEntry;
import com.store.appstoreranking.model.AppsModel;

/**
 * @author wenxiaokang
 * @className AppRankingAdapter
 * @description App推荐
 * @date 4/14/21 5:16 PM
 */
public class AppRecommendAdapter extends BaseQuickAdapter<AppsModel, BaseViewHolder> {
    public AppRecommendAdapter() {
        super(R.layout.item_apps_recommend, null);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, AppsModel appsModel) {
        ImageView ivAppIcon = baseViewHolder.getView(R.id.iv_app_icon);
        GlideLoader.loadNetWorkResource(getContext(),appsModel.getImage(),ivAppIcon);
        baseViewHolder.setText(R.id.iv_app_name,appsModel.getName())
        .setText(R.id.tv_app_category,appsModel.getCategory());
    }
}
