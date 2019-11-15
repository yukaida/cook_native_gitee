package com.ebanswers.kitchendiary.bean;

import java.util.List;

/**
 * Create by dongli
 * Create date 2019-11-05
 * desc：
 */
public class DiaryInfo {

    /**
     * img_url :
     * comment_count : 0
     * folded : false
     * topic_list : [{"topic_id":"2006415321517","topic_content":"打卡第1天"}]
     * cookbook_type :
     * article_img :
     * cover_img :
     * article_link :
     * comment : []
     * is_liked : false
     * nickname : 厨房客人
     * is_collected : false
     * liked : []
     * msg_content : #打卡第1天#
     * is_recommend :
     * master_rank : -1
     * head_url : https://storage.53iq.com/group1/M00/00/0B/CgoKRFcgbrWAaxrrAAA1rVOF8Gk753.jpg
     * diary_type : diary
     * title :
     * is_master : false
     * is_subscribe : true
     * is_top : 0
     * desc :
     * is_class : false
     * like_count : 0
     * article_title :
     * create_date : 21分钟前
     * diary_id : 51f8d754-0070-11ea-82e5-848f69fd90b9
     * thumbnail_url :
     * create_user : tmp_user
     */

    private String img_url;
    private int comment_count;
    private boolean folded;
    private String cookbook_type;
    private String article_img;
    private String cover_img;
    private String article_link;
    private boolean is_liked;
    private String nickname;
    private boolean is_collected;
    private String msg_content;
    private String is_recommend;
    private int master_rank;
    private String head_url;
    private String diary_type;
    private String title;
    private boolean is_master;
    private boolean is_subscribe;
    private int is_top;
    private String desc;
    private boolean is_class;
    private int like_count;
    private String article_title;
    private String create_date;
    private String diary_id;
    private String thumbnail_url;
    private String create_user;
    private List<TopicListBean> topic_list;
    private List<CommentInfo> comment;
    private List<?> liked;

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public int getComment_count() {
        return comment_count;
    }

    public void setComment_count(int comment_count) {
        this.comment_count = comment_count;
    }

    public boolean isFolded() {
        return folded;
    }

    public void setFolded(boolean folded) {
        this.folded = folded;
    }

    public String getCookbook_type() {
        return cookbook_type;
    }

    public void setCookbook_type(String cookbook_type) {
        this.cookbook_type = cookbook_type;
    }

    public String getArticle_img() {
        return article_img;
    }

    public void setArticle_img(String article_img) {
        this.article_img = article_img;
    }

    public String getCover_img() {
        return cover_img;
    }

    public void setCover_img(String cover_img) {
        this.cover_img = cover_img;
    }

    public String getArticle_link() {
        return article_link;
    }

    public void setArticle_link(String article_link) {
        this.article_link = article_link;
    }

    public boolean isIs_liked() {
        return is_liked;
    }

    public void setIs_liked(boolean is_liked) {
        this.is_liked = is_liked;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public boolean isIs_collected() {
        return is_collected;
    }

    public void setIs_collected(boolean is_collected) {
        this.is_collected = is_collected;
    }

    public String getMsg_content() {
        return msg_content;
    }

    public void setMsg_content(String msg_content) {
        this.msg_content = msg_content;
    }

    public String getIs_recommend() {
        return is_recommend;
    }

    public void setIs_recommend(String is_recommend) {
        this.is_recommend = is_recommend;
    }

    public int getMaster_rank() {
        return master_rank;
    }

    public void setMaster_rank(int master_rank) {
        this.master_rank = master_rank;
    }

    public String getHead_url() {
        return head_url;
    }

    public void setHead_url(String head_url) {
        this.head_url = head_url;
    }

    public String getDiary_type() {
        return diary_type;
    }

    public void setDiary_type(String diary_type) {
        this.diary_type = diary_type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isIs_master() {
        return is_master;
    }

    public void setIs_master(boolean is_master) {
        this.is_master = is_master;
    }

    public boolean isIs_subscribe() {
        return is_subscribe;
    }

    public void setIs_subscribe(boolean is_subscribe) {
        this.is_subscribe = is_subscribe;
    }

    public int getIs_top() {
        return is_top;
    }

    public void setIs_top(int is_top) {
        this.is_top = is_top;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean isIs_class() {
        return is_class;
    }

    public void setIs_class(boolean is_class) {
        this.is_class = is_class;
    }

    public int getLike_count() {
        return like_count;
    }

    public void setLike_count(int like_count) {
        this.like_count = like_count;
    }

    public String getArticle_title() {
        return article_title;
    }

    public void setArticle_title(String article_title) {
        this.article_title = article_title;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public String getDiary_id() {
        return diary_id;
    }

    public void setDiary_id(String diary_id) {
        this.diary_id = diary_id;
    }

    public String getThumbnail_url() {
        return thumbnail_url;
    }

    public void setThumbnail_url(String thumbnail_url) {
        this.thumbnail_url = thumbnail_url;
    }

    public String getCreate_user() {
        return create_user;
    }

    public void setCreate_user(String create_user) {
        this.create_user = create_user;
    }

    public List<TopicListBean> getTopic_list() {
        return topic_list;
    }

    public void setTopic_list(List<TopicListBean> topic_list) {
        this.topic_list = topic_list;
    }

    public List<CommentInfo> getComment() {
        return comment;
    }

    public void setComment(List<CommentInfo> comment) {
        this.comment = comment;
    }

    public List<?> getLiked() {
        return liked;
    }

    public void setLiked(List<?> liked) {
        this.liked = liked;
    }

    public static class TopicListBean {
        /**
         * topic_id : 2006415321517
         * topic_content : 打卡第1天
         */

        private String topic_id;
        private String topic_content;

        public String getTopic_id() {
            return topic_id;
        }

        public void setTopic_id(String topic_id) {
            this.topic_id = topic_id;
        }

        public String getTopic_content() {
            return topic_content;
        }

        public void setTopic_content(String topic_content) {
            this.topic_content = topic_content;
        }
    }
}
