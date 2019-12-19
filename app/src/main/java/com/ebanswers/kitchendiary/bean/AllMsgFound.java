package com.ebanswers.kitchendiary.bean;

import com.ebanswers.kitchendiary.bean.Topics.NormalTopics;

import java.util.List;

/**
 * Create by dongli
 * Create date 2019-11-01
 * desc：
 */
public class AllMsgFound  {
    /**
     * article_img :
     * is_top : 0
     * is_subscribe : false
     * topic_list : []
     * diary_type : diary
     * liked : []
     * desc :
     * master_rank : 15
     * msg_content : 与儿子约个饭，明天一早，小朋友又要飞走啦。
     * thumbnail_url : ["https://storage.53iq.com/group1/M00/16/44/CgoKTl27-weAChhKAAD7Nx84jbU144.jpg"]
     * comment_count : 0
     * head_url : https://thirdwx.qlogo.cn/mmopen/MaMWodKY4buowzAcZTsW8XC1BJiajoxNzgnd789WoiartgAcAABcwkCch2SPdKl91r8AJG2mUy6EsHOI6AS9QOmsYDotTk0pwl/132
     * like_count : 0
     * is_collected : false
     * title :
     * is_master : false
     * cookbook_type :
     * article_title :
     * comment : []
     * cover_img :
     * folded : false
     * diary_id : 238200f6-fc8a-11e9-9253-848f69fd90b9
     * create_date : 2分钟前
     * create_user : oYazTsiVl9i5HdCnMQ-mYnSxUqZo
     * nickname : Yvonne徐
     * is_recommend :
     * is_liked : false
     * img_url : ["https://storage.53iq.com/group1/M00/16/44/CgoKTl27-weAChhKAAD7Nx84jbU144.jpg"]
     * article_link :
     * is_class : false
     */

    private String article_img;
    private int is_top;
    private boolean is_subscribe;
    private String diary_type;
    private String desc;
    private int master_rank;
    private String msg_content;
    private int comment_count;
    private String head_url;
    private String is_show;
    private int like_count;
    private boolean is_collected;
    private String title;
    private boolean is_master;
    private String cookbook_type;
    private String article_title;
    private String cover_img;
    private boolean folded;
    private String diary_id;
    private String create_date;
    private String create_user;
    private String nickname;
    private String is_recommend;
    private boolean is_liked;
    private String article_link;
    private boolean is_class;
    private List<NormalTopics> topic_list;
    private List<LikedInfo> liked;
    private List<CommentInfo> comment;
    private List<String> thumbnail_url;
    private List<String> img_url;
    private List<String> media_id;
    private List<String> steps_media_id;
    private List<FoodMaterialinfo> material;
    private List<FoodStepinfo> steps;

    public String getIs_show() {
        return is_show;
    }

    public void setIs_show(String is_show) {
        this.is_show = is_show;
    }

    public List<String> getMedia_id() {
        return media_id;
    }

    public void setMedia_id(List<String> media_id) {
        this.media_id = media_id;
    }

    public List<String> getSteps_media_id() {
        return steps_media_id;
    }

    public void setSteps_media_id(List<String> steps_media_id) {
        this.steps_media_id = steps_media_id;
    }

    public List<FoodMaterialinfo> getMaterial() {
        return material;
    }

    public void setMaterial(List<FoodMaterialinfo> material) {
        this.material = material;
    }

    public AllMsgFound() {
    }

    public List<FoodStepinfo> getSteps() {
        return steps;
    }

    public void setSteps(List<FoodStepinfo> steps) {
        this.steps = steps;
    }

    public String getArticle_img() {
        return article_img;
    }

    public void setArticle_img(String article_img) {
        this.article_img = article_img;
    }

    public int getIs_top() {
        return is_top;
    }

    public void setIs_top(int is_top) {
        this.is_top = is_top;
    }

    public boolean isIs_subscribe() {
        return is_subscribe;
    }

    public void setIs_subscribe(boolean is_subscribe) {
        this.is_subscribe = is_subscribe;
    }

    public String getDiary_type() {
        return diary_type;
    }

    public void setDiary_type(String diary_type) {
        this.diary_type = diary_type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getMaster_rank() {
        return master_rank;
    }

    public void setMaster_rank(int master_rank) {
        this.master_rank = master_rank;
    }

    public String getMsg_content() {
        return msg_content;
    }

    public void setMsg_content(String msg_content) {
        this.msg_content = msg_content;
    }

    public int getComment_count() {
        return comment_count;
    }

    public void setComment_count(int comment_count) {
        this.comment_count = comment_count;
    }

    public String getHead_url() {
        return head_url;
    }

    public void setHead_url(String head_url) {
        this.head_url = head_url;
    }

    public int getLike_count() {
        return like_count;
    }

    public void setLike_count(int like_count) {
        this.like_count = like_count;
    }

    public boolean isIs_collected() {
        return is_collected;
    }

    public void setIs_collected(boolean is_collected) {
        this.is_collected = is_collected;
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

    public String getCookbook_type() {
        return cookbook_type;
    }

    public void setCookbook_type(String cookbook_type) {
        this.cookbook_type = cookbook_type;
    }

    public String getArticle_title() {
        return article_title;
    }

    public void setArticle_title(String article_title) {
        this.article_title = article_title;
    }

    public String getCover_img() {
        return cover_img;
    }

    public void setCover_img(String cover_img) {
        this.cover_img = cover_img;
    }

    public boolean isFolded() {
        return folded;
    }

    public void setFolded(boolean folded) {
        this.folded = folded;
    }

    public String getDiary_id() {
        return diary_id;
    }

    public void setDiary_id(String diary_id) {
        this.diary_id = diary_id;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public String getCreate_user() {
        return create_user;
    }

    public void setCreate_user(String create_user) {
        this.create_user = create_user;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getIs_recommend() {
        return is_recommend;
    }

    public void setIs_recommend(String is_recommend) {
        this.is_recommend = is_recommend;
    }

    public boolean isIs_liked() {
        return is_liked;
    }

    public void setIs_liked(boolean is_liked) {
        this.is_liked = is_liked;
    }

    public String getArticle_link() {
        return article_link;
    }

    public void setArticle_link(String article_link) {
        this.article_link = article_link;
    }

    public boolean isIs_class() {
        return is_class;
    }

    public void setIs_class(boolean is_class) {
        this.is_class = is_class;
    }

    public List<NormalTopics> getTopic_list() {
        return topic_list;
    }

    public void setTopic_list(List<NormalTopics> topic_list) {
        this.topic_list = topic_list;
    }

    public List<LikedInfo> getLiked() {
        return liked;
    }

    public void setLiked(List<LikedInfo> liked) {
        this.liked = liked;
    }

    public List<String> getThumbnail_url() {
        return thumbnail_url;
    }

    public void setThumbnail_url(List<String> thumbnail_url) {
        this.thumbnail_url = thumbnail_url;
    }

    public List<CommentInfo> getComment() {
        return comment;
    }

    public void setComment(List<CommentInfo> comment) {
        this.comment = comment;
    }

    public List<String> getImg_url() {
        return img_url;
    }

    public void setImg_url(List<String> img_url) {
        this.img_url = img_url;
    }

}
