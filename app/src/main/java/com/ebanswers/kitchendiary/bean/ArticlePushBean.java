package com.ebanswers.kitchendiary.bean;

/**
 * Created by air on 2017/9/12.
 */

public class ArticlePushBean {

    /**
     * msg_type : article
     * msg_data : {"title":"title","desc":"desc","url":"url"}
     * receiver : receiver
     * sender : sys
     */

    private String msg_type;
    private MsgDataBean msg_data;
    private String receiver;
    private String sender;

    public String getMsg_type() {
        return msg_type;
    }

    public void setMsg_type(String msg_type) {
        this.msg_type = msg_type;
    }

    public MsgDataBean getMsg_data() {
        return msg_data;
    }

    public void setMsg_data(MsgDataBean msg_data) {
        this.msg_data = msg_data;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public static class MsgDataBean {
        /**
         * title : title
         * desc : desc
         * url : url
         */

        private String title;
        private String desc;
        private String url;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
