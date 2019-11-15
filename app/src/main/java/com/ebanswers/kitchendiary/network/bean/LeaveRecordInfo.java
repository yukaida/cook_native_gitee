package com.ebanswers.kitchendiary.network.bean;

import java.util.List;

public class LeaveRecordInfo {

    /**
     * appDetails : [{"dealRemarks":"","hiState":1,"taskName":"人力资源审批","userId":526,"userName":"张成俊"},{"dealRemarks":"","endDate":"2018-09-17 11:40:27","hiState":0,"taskName":"请假申请","userId":880,"userName":"李东"}]
     * days : 1
     * dictName : []
     * handleType : 0
     * hiState : 0
     * myLeave : []
     * nextPerson : []
     * reason : 家里有事
     * state : 0
     * timeAp : 2018-09-13 上午
     * typeDictName : 138
     * userId : 0
     * workNum : L0881
     */

    private int days;
    private int handleType;
    private int hiState;
    private String reason;
    private int state;
    private String timeAp;
    private String typeDictName;
    private int userId;
    private int leaveId;
    private String workNum;
    private List<AppDetailsBean> appDetails;
    private List<?> dictName;
    private List<?> myLeave;
    private List<?> nextPerson;
    private boolean isChecked;

    public int getLeaveId() {
        return leaveId;
    }

    public void setLeaveId(int leaveId) {
        this.leaveId = leaveId;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

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

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getTimeAp() {
        return timeAp;
    }

    public void setTimeAp(String timeAp) {
        this.timeAp = timeAp;
    }

    public String getTypeDictName() {
        return typeDictName;
    }

    public void setTypeDictName(String typeDictName) {
        this.typeDictName = typeDictName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getWorkNum() {
        return workNum;
    }

    public void setWorkNum(String workNum) {
        this.workNum = workNum;
    }

    public List<AppDetailsBean> getAppDetails() {
        return appDetails;
    }

    public void setAppDetails(List<AppDetailsBean> appDetails) {
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

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public static class AppDetailsBean {
        /**
         * dealRemarks :
         * hiState : 1
         * taskName : 人力资源审批
         * userId : 526
         * userName : 张成俊
         * endDate : 2018-09-17 11:40:27
         */

        private String dealRemarks;
        private int hiState;
        private String taskName;
        private int userId;
        private String userName;
        private String endDate;

        public String getDealRemarks() {
            return dealRemarks;
        }

        public void setDealRemarks(String dealRemarks) {
            this.dealRemarks = dealRemarks;
        }

        public int getHiState() {
            return hiState;
        }

        public void setHiState(int hiState) {
            this.hiState = hiState;
        }

        public String getTaskName() {
            return taskName;
        }

        public void setTaskName(String taskName) {
            this.taskName = taskName;
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

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }
    }
}
