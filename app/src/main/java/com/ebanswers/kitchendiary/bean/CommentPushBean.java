package com.ebanswers.kitchendiary.bean;

/**
 * Created by air on 2017/9/12.
 */

public class CommentPushBean {
    /**
     * url : https://wechat.53iq.com/tmp/kitchen/relate/me?code=123
     * content : 不错
     * msg_type : comment
     */

    private String url;
    private String content;
    private String msg_type;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMsg_type() {
        return msg_type;
    }

    public void setMsg_type(String msg_type) {
        this.msg_type = msg_type;
    }
}
