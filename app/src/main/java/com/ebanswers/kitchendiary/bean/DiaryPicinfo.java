package com.ebanswers.kitchendiary.bean;

public class DiaryPicinfo {
    String imagePath;

    public DiaryPicinfo(String path) {
        super();
        imagePath = path;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
