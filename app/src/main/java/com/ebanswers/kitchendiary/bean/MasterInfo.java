package com.ebanswers.kitchendiary.bean;

import java.util.List;

/**
 * Create by dongli
 * Create date 2019-11-14
 * desc：
 */
public class MasterInfo {

    /**
     * openid : oYazTsqXhbIX7Us4s_arCcXdF6MM
     * is_subscribe : false
     * headimgurl : https://thirdwx.qlogo.cn/mmopen/ZTYKOnkIIKUZgbJR2beyax8CiazQlGFibWbhBQXbib74s1VPv05v0OT2xibst67HIicVeibFFPW88vxTbpgfQAINl8icYbZrB5xwLAf/132
     * signature : ["早餐","面食","家常菜","烘焙"]
     * nickname : 小C美食记
     */

    private String openid;
    private boolean is_subscribe;
    private String headimgurl;
    private String nickname;
    private List<String> signature;

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public boolean isIs_subscribe() {
        return is_subscribe;
    }

    public void setIs_subscribe(boolean is_subscribe) {
        this.is_subscribe = is_subscribe;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public List<String> getSignature() {
        return signature;
    }

    public void setSignature(List<String> signature) {
        this.signature = signature;
    }
}
