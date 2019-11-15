package com.ebanswers.kitchendiary.network.bean;

import java.util.List;

public class SignInfo {

    /**
     * calcDatetime : 2018-09-04
     * realName : 李东
     * timeInfo : [{"attendNum":0,"attendState":0,"clockTime":"08:51"}]
     * userId : 880
     * userName : lidong
     * workNum : L0881
     */

    private String calcDatetime;
    private String realName;
    private int userId;
    private String userName;
    private String workNum;
    private List<TimeInfo> timeInfo;

    public String getCalcDatetime() {
        return calcDatetime;
    }

    public void setCalcDatetime(String calcDatetime) {
        this.calcDatetime = calcDatetime;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getWorkNum() {
        return workNum;
    }

    public void setWorkNum(String workNum) {
        this.workNum = workNum;
    }

    public List<TimeInfo> getTimeInfo() {
        return timeInfo;
    }

    public void setTimeInfo(List<TimeInfo> timeInfo) {
        this.timeInfo = timeInfo;
    }

}
