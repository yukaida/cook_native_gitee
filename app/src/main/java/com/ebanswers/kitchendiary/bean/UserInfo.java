package com.ebanswers.kitchendiary.bean;

public class UserInfo {

    /**
     * rank : -1
     * is_master : false
     * a_content : 点击查看
     * appid : wx565fdf8a7db23984
     * signature : 71faf56ec827e24ddd222f0f9cabec40c7f29b6e
     * nums : {"classes":5,"cookbook":4,"diary":3}
     * days : 0
     * timestamp : 1572939596
     * following_number : 0
     * noncestr : 53iqdiary
     * x_source :
     * a_link : https://wechat.53iq.com/tmp/static/html/act1101/index.html?openid=oYazTsjEeSdEt1_q3XuX3_uYRvEw&acv_id=5d8322a116ec9c4afded3cd1&device_name=jiaweilu_acp&expired=1
     * bgurl : https://storage.53iq.com/group1/M00/0E/61/CgoKQ1jQ7n2AYs8vAAGuc3BdU9k732.jpg
     * openid : tmp_user
     * visit_from :
     * points : 268
     * my_name : 厨房客人
     * is_apple : false
     * follower_number : 0
     * head_url : https://storage.53iq.com/group1/M00/00/0B/CgoKRFcgbrWAaxrrAAA1rVOF8Gk753.jpg
     */

    private int rank;
    private boolean is_master;
    private String a_content;
    private String appid;
    private String signature;
    private NumsBean nums;
    private int days;
    private int timestamp;
    private int following_number;
    private String noncestr;
    private String x_source;
    private String a_link;
    private String bgurl;
    private String openid;
    private String visit_from;
    private int points;
    private String my_name;
    private boolean is_apple;
    private int follower_number;
    private String head_url;

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public boolean isIs_master() {
        return is_master;
    }

    public void setIs_master(boolean is_master) {
        this.is_master = is_master;
    }

    public String getA_content() {
        return a_content;
    }

    public void setA_content(String a_content) {
        this.a_content = a_content;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public NumsBean getNums() {
        return nums;
    }

    public void setNums(NumsBean nums) {
        this.nums = nums;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public int getFollowing_number() {
        return following_number;
    }

    public void setFollowing_number(int following_number) {
        this.following_number = following_number;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getX_source() {
        return x_source;
    }

    public void setX_source(String x_source) {
        this.x_source = x_source;
    }

    public String getA_link() {
        return a_link;
    }

    public void setA_link(String a_link) {
        this.a_link = a_link;
    }

    public String getBgurl() {
        return bgurl;
    }

    public void setBgurl(String bgurl) {
        this.bgurl = bgurl;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getVisit_from() {
        return visit_from;
    }

    public void setVisit_from(String visit_from) {
        this.visit_from = visit_from;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getMy_name() {
        return my_name;
    }

    public void setMy_name(String my_name) {
        this.my_name = my_name;
    }

    public boolean isIs_apple() {
        return is_apple;
    }

    public void setIs_apple(boolean is_apple) {
        this.is_apple = is_apple;
    }

    public int getFollower_number() {
        return follower_number;
    }

    public void setFollower_number(int follower_number) {
        this.follower_number = follower_number;
    }

    public String getHead_url() {
        return head_url;
    }

    public void setHead_url(String head_url) {
        this.head_url = head_url;
    }

    public static class NumsBean {
        /**
         * classes : 5
         * cookbook : 4
         * diary : 3
         */

        private int classes;
        private int cookbook;
        private int diary;

        public int getClasses() {
            return classes;
        }

        public void setClasses(int classes) {
            this.classes = classes;
        }

        public int getCookbook() {
            return cookbook;
        }

        public void setCookbook(int cookbook) {
            this.cookbook = cookbook;
        }

        public int getDiary() {
            return diary;
        }

        public void setDiary(int diary) {
            this.diary = diary;
        }
    }
}
