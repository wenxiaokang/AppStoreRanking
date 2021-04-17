package com.store.appstoreranking.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.LoadMoreListenerImp;
import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.store.appstoreranking.R;
import com.store.appstoreranking.glide.GlideLoader;
import com.store.appstoreranking.model.AppEntry;
import com.store.appstoreranking.model.AppsModel;

import java.util.List;

/**
 * @author wenxiaokang
 * @className AppRankingAdapter
 * @description 排行adapter
 * @date 4/14/21 5:16 PM
 */
public class AppRankingAdapter extends BaseQuickAdapter<AppsModel, BaseViewHolder> implements LoadMoreModule {

    public AppRankingAdapter() {
        super(R.layout.item_apps_ranking, null);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, AppsModel appsModel) {
        ImageView ivAppIcon = baseViewHolder.getView(R.id.iv_app_icon);
        ImageView ivAppIconSquare = baseViewHolder.getView(R.id.iv_app_icon_square);
        RatingBar ratingBar = baseViewHolder.getView(R.id.rb_star);
        //基数显示方形 偶数显示圆形
        if (baseViewHolder.getAdapterPosition() % 2 == 0) {
            GlideLoader.loadNetWorkResource(getContext(), appsModel.getImage(), ivAppIcon);
            ivAppIcon.setVisibility(View.VISIBLE);
            ivAppIconSquare.setVisibility(View.GONE);
        } else {
            GlideLoader.loadNetWorkResource(getContext(), appsModel.getImage(), ivAppIconSquare);
            ivAppIcon.setVisibility(View.GONE);
            ivAppIconSquare.setVisibility(View.VISIBLE);
        }
        baseViewHolder.setText(R.id.iv_app_name, appsModel.getName())
                .setText(R.id.tv_app_category, appsModel.getCategory())
                .setText(R.id.tv_rank_no, String.valueOf(baseViewHolder.getAdapterPosition()))
                .setText(R.id.tv_count, "(" + appsModel.getUserRatingCount() + ")");
        ratingBar.setRating(appsModel.getTrackContentRating());
    }

}
