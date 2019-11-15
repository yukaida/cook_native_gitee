package com.ebanswers.kitchendiary.bean;

/**
 * Create by dongli
 * Create date 2019-11-01
 * desc：
 */
public class CommentInfo {

    /**
     * from_openid :
     * from_user :
     * nickname : Angel
     * openid : oYazTssajsG3I5O32WykC21JTm00
     * comment : 都是我爱吃的
     */

    private String from_openid;
    private String from_user;
    private String nickname;
    private String openid;
    private String comment;

    public String getFrom_openid() {
        return from_openid;
    }

    public void setFrom_openid(String from_openid) {
        this.from_openid = from_openid;
    }

    public String getFrom_user() {
        return from_user;
    }

    public void setFrom_user(String from_user) {
        this.from_user = from_user;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
