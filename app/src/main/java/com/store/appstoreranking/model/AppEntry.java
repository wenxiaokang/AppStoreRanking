package com.store.appstoreranking.model;

import java.util.List;

/**
 * @author wenxiaokang
 * @className AppEntry
 * @description app信息gson实体类
 * @date 4/13/21 7:26 PM
 */
public class AppEntry {

    @com.google.gson.annotations.SerializedName("im:name")
    private IMBean imName;
    //icon
    @com.google.gson.annotations.SerializedName("im:image")
    private List<IMBean> imImage;
    //品类
    private IMBean category;
    //id
    private IMBean id;
    //评级
    private float trackContentRating;
    //人数
    private int userRatingCount;
    //公司
    private IMBean title;


    public IMBean getTitle() {
        return title;
    }

    public void setTitle(IMBean title) {
        this.title = title;
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

    public IMBean getId() {
        return id;
    }

    public void setId(IMBean id) {
        this.id = id;
    }

    public IMBean getImName() {
        return imName;
    }

    public void setImName(IMBean imName) {
        this.imName = imName;
    }

    public void setImImage(List<IMBean> imImage) {
        this.imImage = imImage;
    }


    public List<IMBean> getImImage() {
        return imImage;
    }

    public IMBean getCategory() {
        return category;
    }

    public void setCategory(IMBean category) {
        this.category = category;
    }

    public static class IMBean {
        private String label;
        private IMBean attributes;

        @com.google.gson.annotations.SerializedName("im:id")
        private String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public IMBean getAttributes() {
            return attributes;
        }

        public void setAttributes(IMBean attributes) {
            this.attributes = attributes;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

    }

}
