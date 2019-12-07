package com.ebanswers.kitchendiary.bean;

import java.util.List;

public class Drafts {

    private String nickname;
    private String head_url;
    private String openid;
    private List<DraftItem> draft;
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public String getNickname() {
        return nickname;
    }

    public void setHead_url(String head_url) {
        this.head_url = head_url;
    }
    public String getHead_url() {
        return head_url;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }
    public String getOpenid() {
        return openid;
    }

    public void setDraft(List<DraftItem> draft) {
        this.draft = draft;
    }
    public List<DraftItem> getDraft() {
        return draft;
    }

}