package com.ebanswers.kitchendiary.bean;

/**
 * @author
 * Created by lishihui on 2017/2/16.
 */

public class ShareBean {
    private int pictureId;
    private String name;

    public ShareBean(int pictureId,String name) {
        this.name = name;
        this.pictureId = pictureId;
    }

    public int getPictureId() {

        return pictureId;
    }

    public void setPictureId(int pictureId) {
        this.pictureId = pictureId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
