package com.ebanswers.kitchendiary.bean;

public class DraftItem {

    private String img;
    private String draft_id;
    private String time;
    private String name;
    public void setImg(String img) {
        this.img = img;
    }
    public String getImg() {
        return img;
    }

    public void setDraft_id(String draft_id) {
        this.draft_id = draft_id;
    }
    public String getDraft_id() {
        return draft_id;
    }

    public void setTime(String time) {
        this.time = time;
    }
    public String getTime() {
        return time;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

}