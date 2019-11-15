package com.ebanswers.kitchendiary.bean;

/**
 * Create by dongli
 * Create date 2019-11-14
 * desc：
 */
public class FoundTopInfo {
    /**
     * topic_hot : 58138
     * topic_content : 早餐
     * is_abandon : false
     * topic_id : 1000000000
     */

    private int topic_hot;
    private String topic_content;
    private boolean is_abandon;
    private String topic_id;

    public int getTopic_hot() {
        return topic_hot;
    }

    public void setTopic_hot(int topic_hot) {
        this.topic_hot = topic_hot;
    }

    public String getTopic_content() {
        return topic_content;
    }

    public void setTopic_content(String topic_content) {
        this.topic_content = topic_content;
    }

    public boolean isIs_abandon() {
        return is_abandon;
    }

    public void setIs_abandon(boolean is_abandon) {
        this.is_abandon = is_abandon;
    }

    public String getTopic_id() {
        return topic_id;
    }

    public void setTopic_id(String topic_id) {
        this.topic_id = topic_id;
    }
}
