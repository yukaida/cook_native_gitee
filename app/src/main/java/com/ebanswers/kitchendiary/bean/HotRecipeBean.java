package com.ebanswers.kitchendiary.bean;

/**
 * @author
 * Created by lishihui on 2017/3/2.
 */

public class HotRecipeBean {

    /**
     * rec : {"meal":"豆皮青菜汤","period":"supper"}
     * url : https://wechat.53iq.com/tmp/kitchen/collect
     * total_count : 0
     */

    private RecBean rec;
    private String url;
    private int total_count;

    public RecBean getRec() {
        return rec;
    }

    public void setRec(RecBean rec) {
        this.rec = rec;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getTotal_count() {
        return total_count;
    }

    public void setTotal_count(int total_count) {
        this.total_count = total_count;
    }

    public static class RecBean {
        /**
         * meal : 豆皮青菜汤
         * period : supper
         */

        private String meal;
        private String period;

        public String getMeal() {
            return meal;
        }

        public void setMeal(String meal) {
            this.meal = meal;
        }

        public String getPeriod() {
            return period;
        }

        public void setPeriod(String period) {
            this.period = period;
        }

        @Override
        public String toString() {
            return "RecBean{" +
                    "meal='" + meal + '\'' +
                    ", period='" + period + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "HotRecipeBean{" +
                "rec=" + rec +
                ", url='" + url + '\'' +
                ", total_count=" + total_count +
                '}';
    }
}
