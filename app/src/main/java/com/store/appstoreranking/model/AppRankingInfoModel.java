package com.store.appstoreranking.model;

import java.util.List;

/**
 * @author wenxiaokang
 * @className AppEntry
 * @description
 * @date 4/13/21 7:23 PM
 */
public class AppRankingInfoModel {
    private List<AppEntry> entry;

    public List<AppEntry> getEntry() {
        return entry;
    }

    public void setEntry(List<AppEntry> entry) {
        this.entry = entry;
    }
}
