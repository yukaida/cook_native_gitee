package com.ebanswers.kitchendiary.bean;

/**
 * Create by dongli
 * Create date 2019-11-15
 * desc：
 */
public class Stepinfo {

    private boolean isEdit = false;
    private String img;
    private String thumbnail;
    private String desc;

    public boolean isEdit() {
        return isEdit;
    }

    public void setEdit(boolean edit) {
        isEdit = edit;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

}
