package com.ebanswers.kitchendiary.bean;

import java.util.List;

/**
 * Create by dongli
 * Create date 2019-11-13
 * desc：
 */
class AdBean {

    /**
     * data_id : 5dca553916ec9c1b101bed80
     * ad_position : 7
     * ad_content : null
     * ad_link : https://wechat.53iq.com/tmp/kitchen/activity/5dbbf4c516ec9ccb1cc277b0?code=123
     * ad_title : 快来分享你的拿手蒸烤菜谱吧~
     * ad_img : ["https://storage.53iq.com/group1/M00/16/DC/CgoKTl3KVTmACLrVAALfLDd3SsQ988.png"]
     */

    private String data_id;
    private int ad_position;
    private Object ad_content;
    private String ad_link;
    private String ad_title;
    private List<String> ad_img;

    public String getData_id() {
        return data_id;
    }

    public void setData_id(String data_id) {
        this.data_id = data_id;
    }

    public int getAd_position() {
        return ad_position;
    }

    public void setAd_position(int ad_position) {
        this.ad_position = ad_position;
    }

    public Object getAd_content() {
        return ad_content;
    }

    public void setAd_content(Object ad_content) {
        this.ad_content = ad_content;
    }

    public String getAd_link() {
        return ad_link;
    }

    public void setAd_link(String ad_link) {
        this.ad_link = ad_link;
    }

    public String getAd_title() {
        return ad_title;
    }

    public void setAd_title(String ad_title) {
        this.ad_title = ad_title;
    }

    public List<String> getAd_img() {
        return ad_img;
    }

    public void setAd_img(List<String> ad_img) {
        this.ad_img = ad_img;
    }
}
