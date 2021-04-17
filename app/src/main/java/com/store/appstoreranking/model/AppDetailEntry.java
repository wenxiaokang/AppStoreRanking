package com.store.appstoreranking.model;

import java.util.List;

/**
 * @author wenxiaokang
 * @className AppDetailEntry
 * @description app详情
 * @date 4/15/21 3:11 PM
 */
public class AppDetailEntry {

    private int resultCount;
    private List<ResultsModel> results;

    public int getResultCount() {
        return resultCount;
    }

    public void setResultCount(int resultCount) {
        this.resultCount = resultCount;
    }

    public List<ResultsModel> getResults() {
        return results;
    }

    public void setResults(List<ResultsModel> results) {
        this.results = results;
    }

    public static class ResultsModel {

        private String trackContentRating;
        private int userRatingCount;

        public float getTrackContentRating() {
            //4+
            if (trackContentRating.length()>1) {
                return Float.parseFloat(trackContentRating.substring(0,1))+0.5f;
            }else {
                return Float.parseFloat(trackContentRating.substring(0,1));
            }
        }

        public void setTrackContentRating(String trackContentRating) {

            this.trackContentRating = trackContentRating;
        }

        public int getUserRatingCount() {
            return userRatingCount;
        }

        public void setUserRatingCount(int userRatingCount) {
            this.userRatingCount = userRatingCount;
        }
    }
}
