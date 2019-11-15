package com.ebanswers.kitchendiary.bean;

import java.util.List;

/**
 * Create by dongli
 * Create date 2019-10-29
 * desc：
 */
public class Diary {
    /**
     * article_link :
     * article_title :
     * diary_id : 003552e6-f920-11e9-8ddd-848f69fd90b9
     * article_img :
     * thumbnail_url : ["https://storage.53iq.com/group1/M00/15/C3/CgoKTl22QHiAbqQ0AAFpZc7cQ14707.jpg","https://storage.53iq.com/group1/M00/15/C3/CgoKTl22QHiAS2gaAAGRniOS6jI448.jpg","https://storage.53iq.com/group1/M00/15/C3/CgoKTl22QHiAGdDfAAFKVGN-Ah0219.jpg","https://storage.53iq.com/group1/M00/15/C3/CgoKTl22QHiAPNjQAAFlyPBwBR8392.jpg"]
     * img_url : ["https://storage.53iq.com/group1/M00/15/C3/CgoKTl22QHiAbqQ0AAFpZc7cQ14707.jpg","https://storage.53iq.com/group1/M00/15/C3/CgoKTl22QHiAS2gaAAGRniOS6jI448.jpg","https://storage.53iq.com/group1/M00/15/C3/CgoKTl22QHiAGdDfAAFKVGN-Ah0219.jpg","https://storage.53iq.com/group1/M00/15/C3/CgoKTl22QHiAPNjQAAFlyPBwBR8392.jpg"]
     * topic_list : []
     * msg_content : #婷婷365天早餐不重样##我的烘焙日记##早餐#
     */

    private String article_link;
    private String article_title;
    private String diary_id;
    private String article_img;
    private String msg_content;
    private List<String> thumbnail_url;
    private List<String> img_url;
    private List<?> topic_list;

    public String getArticle_link() {
        return article_link;
    }

    public void setArticle_link(String article_link) {
        this.article_link = article_link;
    }

    public String getArticle_title() {
        return article_title;
    }

    public void setArticle_title(String article_title) {
        this.article_title = article_title;
    }

    public String getDiary_id() {
        return diary_id;
    }

    public void setDiary_id(String diary_id) {
        this.diary_id = diary_id;
    }

    public String getArticle_img() {
        return article_img;
    }

    public void setArticle_img(String article_img) {
        this.article_img = article_img;
    }

    public String getMsg_content() {
        return msg_content;
    }

    public void setMsg_content(String msg_content) {
        this.msg_content = msg_content;
    }

    public List<String> getThumbnail_url() {
        return thumbnail_url;
    }

    public void setThumbnail_url(List<String> thumbnail_url) {
        this.thumbnail_url = thumbnail_url;
    }

    public List<String> getImg_url() {
        return img_url;
    }

    public void setImg_url(List<String> img_url) {
        this.img_url = img_url;
    }

    public List<?> getTopic_list() {
        return topic_list;
    }

    public void setTopic_list(List<?> topic_list) {
        this.topic_list = topic_list;
    }
}
