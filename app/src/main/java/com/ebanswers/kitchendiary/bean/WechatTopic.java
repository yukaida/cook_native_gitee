package com.ebanswers.kitchendiary.bean;

import java.util.List;

/**
 * @author
 * Created by lishihui on 2017/1/11.
 */

public class WechatTopic {


    /**
     * code : 0
     * last : [{"topic_content":"你好，2017","topic_id":"300000530"}]
     * data : [{"topic_content":"跨年打卡大作战","topic_id":"300000471"},{"topic_content":"元旦快乐","topic_id":"300000532"},{"topic_content":"你好，2017","topic_id":"300000530"},{"topic_content":"再见，2016","topic_id":"300000531"},{"topic_content":"新年快乐","topic_id":"300000529"},{"topic_content":"早餐","topic_id":"1000000000"},{"topic_content":"花样打卡","topic_id":"300000472"},{"topic_content":"火锅季","topic_id":"300000398"},{"topic_content":"365天早餐不重样","topic_id":"2000000009"},{"topic_content":"和厨房谈恋爱","topic_id":"300000265"}]
     */

    private int code;
    private List<LastBean> last;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<LastBean> getLast() {
        return last;
    }

    public void setLast(List<LastBean> last) {
        this.last = last;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class LastBean {
        /**
         * topic_content : 你好，2017
         * topic_id : 300000530
         */

        private String topic_content;
        private String topic_id;

        public LastBean() {
        }

        public LastBean(String topic_content, String topic_id) {
            this.topic_content = topic_content;
            this.topic_id = topic_id;
        }

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
    }

    public static class DataBean {
        /**
         * topic_content : 跨年打卡大作战
         * topic_id : 300000471
         */

        private String topic_content;
        private String topic_id;

        public DataBean() {
        }

        public DataBean(String topic_content, String topic_id) {
            this.topic_content = topic_content;
            this.topic_id = topic_id;
        }

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
    }
}
