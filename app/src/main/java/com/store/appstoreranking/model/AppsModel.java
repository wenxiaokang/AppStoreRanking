package com.store.appstoreranking.model;

import android.content.ContentValues;

/**
 * @author wenxiaokang
 * @className AppsModel
 * @description 符合业务流程的app实体类
 * @date 4/15/21 9:36 PM
 */
public class AppsModel {
    public AppsModel() {
    }

    //AppEntry--->AppsModel
    public AppsModel(AppEntry appEntry,AppDetailEntry.ResultsModel resultsModel) {
        this.name = appEntry.getImName().getLabel();
        this.image = appEntry.getImImage().get(appEntry.getImImage().size() > 1 ? 1 : 0).getLabel();
        this.id = appEntry.getId().getAttributes().getId();
        this.category = appEntry.getCategory().getAttributes().getLabel();
        this.title = appEntry.getTitle().getLabel();
        this.trackContentRating = resultsModel.getTrackContentRating();
        this.userRatingCount = resultsModel.getUserRatingCount();
    }

    public AppsModel(AppEntry appEntry) {
        this.name = appEntry.getImName().getLabel();
        this.image = appEntry.getImImage().get(appEntry.getImImage().size() > 1 ? 1 : 0).getLabel();
        this.id = appEntry.getId().getAttributes().getId();
        this.category = appEntry.getCategory().getAttributes().getLabel();
        this.title = appEntry.getTitle().getLabel();

    }

    /**
     * 是否符合 名称、开发者、类别
     * @param key 条件
     * @return
     */
    public boolean isFitSearch(String key){
        return (name+category+title).contains(key);
    }

    //名称
    private String name;
    //图标
    private String image;
    //id
    private String id;
    //评级
    private float trackContentRating;
    //人数
    private int userRatingCount;
    //类别
    private String category;
    //公司
    private String title;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public float getTrackContentRating() {
        return trackContentRating;
    }

    public void setTrackContentRating(float trackContentRating) {
        this.trackContentRating = trackContentRating;
    }

    public int getUserRatingCount() {
        return userRatingCount;
    }

    public void setUserRatingCount(int userRatingCount) {
        this.userRatingCount = userRatingCount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ContentValues getContentValue(int type){
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("image", image);
        values.put("id", id);
        values.put("trackContentRating", trackContentRating);
        values.put("userRatingCount", userRatingCount);
        values.put("category", category);
        values.put("title", title);
        values.put("type", type);
        return values;

    }
}
