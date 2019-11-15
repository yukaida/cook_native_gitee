package com.ebanswers.kitchendiary.mvp.model;

/**
 * Create by dongli
 * Create date 2019-09-23
 * desc：
 */
public class UserInfo {


    /**
     * username : aa5678
     * balance : 14.3156
     * realname : 张雨绮
     * storename : 千里香混沌黄焖鸡米饭
     * mobile : 13800013800
     */

    private String username;
    private String balance;
    private String realname;
    private String storename;
    private String mobile;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getStorename() {
        return storename;
    }

    public void setStorename(String storename) {
        this.storename = storename;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
