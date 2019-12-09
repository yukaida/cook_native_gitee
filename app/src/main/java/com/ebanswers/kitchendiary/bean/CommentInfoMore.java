package com.ebanswers.kitchendiary.bean;


import java.util.List;

public class CommentInfoMore {

    private int code;
    private List<CommentInfo> data;

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setData(List<CommentInfo> data) {
        this.data = data;
    }

    public List<CommentInfo> getData() {
        return data;
    }

}