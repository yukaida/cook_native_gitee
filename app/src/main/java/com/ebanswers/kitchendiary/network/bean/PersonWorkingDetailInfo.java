package com.ebanswers.kitchendiary.network.bean;

import java.util.List;

public class PersonWorkingDetailInfo {
    /**
     * projectNumber : 1
     * rows : [{"createTime":"2019-01-15","createUser":"yaozhirong","planVersionNum":"","projectName":"","type":"其他","workContent":"dddd","workDate":"2019-01-15","workHour":"8"}]
     * total : 1
     * totalWorkingHours : 8
     */

    private int projectNumber;
    private int total;
    private int totalWorkingHours;
    private List<MessageListInfo> rows;

    public int getProjectNumber() {
        return projectNumber;
    }

    public void setProjectNumber(int projectNumber) {
        this.projectNumber = projectNumber;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotalWorkingHours() {
        return totalWorkingHours;
    }

    public void setTotalWorkingHours(int totalWorkingHours) {
        this.totalWorkingHours = totalWorkingHours;
    }

    public List<MessageListInfo> getRows() {
        return rows;
    }

    public void setRows(List<MessageListInfo> rows) {
        this.rows = rows;
    }

}
