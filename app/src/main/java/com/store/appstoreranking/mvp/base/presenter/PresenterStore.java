package com.store.appstoreranking.mvp.base.presenter;

import java.util.HashMap;

/**
 * @author wenxiaokang
 * @className BasePresenter
 * @description Presenter管理者
 * @date 4/12/21 10:29 PM
 */
public class PresenterStore<P extends BasePresenter> {

    private static final String DEFAULT_KEY = "PresenterStore.DefaultKey";
    private HashMap<String, P> mMap = new HashMap<>();

    public final void put(String key, P presenter) {
        P oldPresenter = mMap.put(DEFAULT_KEY + ":" + key, presenter);
        if (oldPresenter != null) {
            oldPresenter.onCleared();
        }
    }

    public final P get(String key) {
        return mMap.get(DEFAULT_KEY + ":" + key);
    }

    public final void clear() {
        for (P presenter : mMap.values()) {
            presenter.onCleared();
        }
        mMap.clear();
    }

    public int getSize() {
        return mMap.size();
    }

    public HashMap<String, P> getMap() {
        return mMap;
    }
}
