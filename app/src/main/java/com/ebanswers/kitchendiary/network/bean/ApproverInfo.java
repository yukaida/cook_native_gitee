package com.ebanswers.kitchendiary.network.bean;

import java.util.List;

public class ApproverInfo {

    /**
     * appDetails : []
     * dictName : []
     * handleType : 0
     * hiState : 0
     * myLeave : []
     * nextPerson : []
     * sPId : 526
     * spName : 张成俊
     * state : 0
     * userId : 0
     */

    private int handleType;
    private int hiState;
    private String sPId;
    private String spName;
    private int state;
    private int userId;
    private List<?> appDetails;
    private List<?> dictName;
    private List<?> myLeave;
    private List<?> nextPerson;

    public int getHandleType() {
        return handleType;
    }

    public void setHandleType(int handleType) {
        this.handleType = handleType;
    }

    public int getHiState() {
        return hiState;
    }

    public void setHiState(int hiState) {
        this.hiState = hiState;
    }

    public String getSPId() {
        return sPId;
    }

    public void setSPId(String sPId) {
        this.sPId = sPId;
    }

    public String getSpName() {
        return spName;
    }

    public void setSpName(String spName) {
        this.spName = spName;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<?> getAppDetails() {
        return appDetails;
    }

    public void setAppDetails(List<?> appDetails) {
        this.appDetails = appDetails;
    }

    public List<?> getDictName() {
        return dictName;
    }

    public void setDictName(List<?> dictName) {
        this.dictName = dictName;
    }

    public List<?> getMyLeave() {
        return myLeave;
    }

    public void setMyLeave(List<?> myLeave) {
        this.myLeave = myLeave;
    }

    public List<?> getNextPerson() {
        return nextPerson;
    }

    public void setNextPerson(List<?> nextPerson) {
        this.nextPerson = nextPerson;
    }

}
