package com.ebanswers.kitchendiary.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Created by lishihui on 2017/1/11.
 */

public class TopicBean implements Parcelable {
    private String topic_content;
    private String topic_id;

    public TopicBean(String topic_content, String topic_id) {
        this.topic_content = topic_content;
        this.topic_id = topic_id;
    }

    protected TopicBean(Parcel in) {
        topic_content = in.readString();
        topic_id = in.readString();
    }

    public static final Creator<TopicBean> CREATOR = new Creator<TopicBean>() {
        @Override
        public TopicBean createFromParcel(Parcel in) {
            return new TopicBean(in);
        }

        @Override
        public TopicBean[] newArray(int size) {
            return new TopicBean[size];
        }
    };

    public String getTopic_content() {
        return topic_content;
    }

    public void setTopic_content(String topic_content) {
        this.topic_content = topic_content;
    }

    public String getTopic_id() {
        return topic_id;
    }

    public void setTopic_id(String topic_id) {
        this.topic_id = topic_id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(topic_content);
        dest.writeString(topic_id);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TopicBean) {
            TopicBean topicBean = (TopicBean) obj;
            return this.getTopic_content().equals(topicBean.getTopic_content());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return getTopic_content().hashCode();
    }
}
