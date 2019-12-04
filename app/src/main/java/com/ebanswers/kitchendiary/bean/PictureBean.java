package com.ebanswers.kitchendiary.bean;

/**
 * @author
 * Created by lishihui on 2017/2/7.
 */

public class PictureBean {
    private int index;
    private String url;

    public PictureBean(int index, String url) {
        this.index = index;
        this.url = url;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
