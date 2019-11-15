package com.ebanswers.kitchendiary.network.response;

import com.ebanswers.kitchendiary.bean.AllMsgFound;

import java.util.List;

/**
 * Create by dongli
 * Create date 2019-11-11
 * desc：
 */
public class FocusResponse extends BaseResponse {


    /**
     * first_login : false
     * current_user_img : https://storage.53iq.com/group1/M00/00/0B/CgoKRFcgbrWAaxrrAAA1rVOF8Gk753.jpg
     * t_id :
     * nickname : 厨房客人
     * latest_yueFanPa : 5da7cdc316ec9c8235aec4bd
     * timestamp : 1573451039
     * all_msg : []
     * activity_text :
     * app :
     * master : [{"is_subscribe":false,"signature":["早餐"],"nickname":"了了","openid":"oYazTskYlnZowyXAx6pkjgKjpULs","headimgurl":"https://thirdwx.qlogo.cn/mmopen/XX3Aw19RxhJ5zA0GxBQM3ZCbViaONVe5sP9p5gZk5ZukxH7fHy3n4HOHk06v13afGLsCp6djUQiaIoNH2QukMImZhEZ5s4hsBg/132"},{"is_subscribe":false,"signature":["凉菜沙拉","早餐","羹粥","面食","家常菜","烘焙"],"nickname":"董小姐的营养厨房","openid":"oYazTsvhKLIvs6Vx6suuyjLVwOyk","headimgurl":"https://thirdwx.qlogo.cn/mmopen/j8cooK2zCqrKXj7Zgc7daqyNwxxHmrJHgUz3I8nt1O78M6TqOsicuX9NoL6z0fQXXU3vmWMIyz7wVjXE9w4orib4Oib59IUSNy8/132"},{"is_subscribe":false,"signature":["面食","西餐","家常菜","凉菜沙拉","早餐","创意菜","羹粥","特色小吃","煲汤","烘焙","快手菜"],"nickname":"颜女子","openid":"oYazTsrI5FfH3DIziObXtK0v2dXM","headimgurl":"https://thirdwx.qlogo.cn/mmopen/ZTYKOnkIIKUZgbJR2beyayibEIxcu3ib4zsAdLXhQzCf5yQzP0nCibL9GVoXpPQXYbQe9KTHiahicMadwgclr7YNyarNyiaBEAyOUg/132"},{"is_subscribe":false,"signature":[],"nickname":"燕子的洋🌸","openid":"oYazTsvW3lwEIqBiQwdERAd0jFS8","headimgurl":"https://thirdwx.qlogo.cn/mmopen/j8cooK2zCqrKXj7Zgc7dakVVrwU5Muf3uFw1qtDYW9fl7XLsmenyJduictiaLdNPxEYys8PNHjbIpso5jQTXqfh6siag9ibfoJM3/132"}]
     * recommend_diary_by_tag : [{"thumbnail_url":["https://storage.53iq.com/group1/M00/09/47/CgoKTV3I6NSAJfSeAAApKGiDGgQ833.jpg","https://storage.53iq.com/group1/M00/09/47/CgoKTV3I6NSAFI_5AAAj0Q5CfIk207.jpg"]},{"thumbnail_url":["https://storage.53iq.com/group1/M00/16/D0/CgoKTl3I1TuAC_fJAACFT_NqUCc168.jpg"]},{"thumbnail_url":["https://storage.53iq.com/group1/M00/16/CF/CgoKTl3IxxOAMZiCAABQ4NkObRw157.jpg"]}]
     * lang : zh
     * noncestr : 53iqdiary
     * reminder_gen_poster : 0
     * appid : wx565fdf8a7db23984
     * types : related
     * signature : e41351d66e47fe7d0524c0e52764da62e013795c
     * is_target_user : false
     * scope :
     * x_source :
     * ctg :
     * openid : tmp_user
     * from_ :
     * show_circle_finished : 0
     */

    private boolean first_login;
    private String current_user_img;
    private String t_id;
    private String nickname;
    private String latest_yueFanPa;
    private int timestamp;
    private String activity_text;
    private String app;
    private String lang;
    private String noncestr;
    private int reminder_gen_poster;
    private String appid;
    private String types;
    private String signature;
    private boolean is_target_user;
    private String scope;
    private String x_source;
    private String ctg;
    private String openid;
    private String from_;
    private int show_circle_finished;
    private List<AllMsgFound> all_msg;
    private List<MasterBean> master;
    private List<RecommendDiaryByTagBean> recommend_diary_by_tag;

    public boolean isFirst_login() {
        return first_login;
    }

    public void setFirst_login(boolean first_login) {
        this.first_login = first_login;
    }

    public String getCurrent_user_img() {
        return current_user_img;
    }

    public void setCurrent_user_img(String current_user_img) {
        this.current_user_img = current_user_img;
    }

    public String getT_id() {
        return t_id;
    }

    public void setT_id(String t_id) {
        this.t_id = t_id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getLatest_yueFanPa() {
        return latest_yueFanPa;
    }

    public void setLatest_yueFanPa(String latest_yueFanPa) {
        this.latest_yueFanPa = latest_yueFanPa;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public String getActivity_text() {
        return activity_text;
    }

    public void setActivity_text(String activity_text) {
        this.activity_text = activity_text;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public int getReminder_gen_poster() {
        return reminder_gen_poster;
    }

    public void setReminder_gen_poster(int reminder_gen_poster) {
        this.reminder_gen_poster = reminder_gen_poster;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public boolean isIs_target_user() {
        return is_target_user;
    }

    public void setIs_target_user(boolean is_target_user) {
        this.is_target_user = is_target_user;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getX_source() {
        return x_source;
    }

    public void setX_source(String x_source) {
        this.x_source = x_source;
    }

    public String getCtg() {
        return ctg;
    }

    public void setCtg(String ctg) {
        this.ctg = ctg;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getFrom_() {
        return from_;
    }

    public void setFrom_(String from_) {
        this.from_ = from_;
    }

    public int getShow_circle_finished() {
        return show_circle_finished;
    }

    public void setShow_circle_finished(int show_circle_finished) {
        this.show_circle_finished = show_circle_finished;
    }

    public List<AllMsgFound> getAll_msg() {
        return all_msg;
    }

    public void setAll_msg(List<AllMsgFound> all_msg) {
        this.all_msg = all_msg;
    }

    public List<MasterBean> getMaster() {
        return master;
    }

    public void setMaster(List<MasterBean> master) {
        this.master = master;
    }

    public List<RecommendDiaryByTagBean> getRecommend_diary_by_tag() {
        return recommend_diary_by_tag;
    }

    public void setRecommend_diary_by_tag(List<RecommendDiaryByTagBean> recommend_diary_by_tag) {
        this.recommend_diary_by_tag = recommend_diary_by_tag;
    }

    public static class MasterBean {
        /**
         * is_subscribe : false
         * signature : ["早餐"]
         * nickname : 了了
         * openid : oYazTskYlnZowyXAx6pkjgKjpULs
         * headimgurl : https://thirdwx.qlogo.cn/mmopen/XX3Aw19RxhJ5zA0GxBQM3ZCbViaONVe5sP9p5gZk5ZukxH7fHy3n4HOHk06v13afGLsCp6djUQiaIoNH2QukMImZhEZ5s4hsBg/132
         */

        private boolean is_subscribe;
        private String nickname;
        private String openid;
        private String headimgurl;
        private List<String> signature;

        public boolean isIs_subscribe() {
            return is_subscribe;
        }

        public void setIs_subscribe(boolean is_subscribe) {
            this.is_subscribe = is_subscribe;
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

        public String getHeadimgurl() {
            return headimgurl;
        }

        public void setHeadimgurl(String headimgurl) {
            this.headimgurl = headimgurl;
        }

        public List<String> getSignature() {
            return signature;
        }

        public void setSignature(List<String> signature) {
            this.signature = signature;
        }
    }

    public static class RecommendDiaryByTagBean {
        private List<String> thumbnail_url;

        public List<String> getThumbnail_url() {
            return thumbnail_url;
        }

        public void setThumbnail_url(List<String> thumbnail_url) {
            this.thumbnail_url = thumbnail_url;
        }
    }
}
