package com.ebanswers.kitchendiary.network.bean;

public class TimeInfo {
    /**
     * attendNum : 0
     * attendState : 0
     * clockTime : 08:51
     */

    private int attendNum;
    private int attendState;
    private String clockTime;

    public int getAttendNum() {
        return attendNum;
    }

    public void setAttendNum(int attendNum) {
        this.attendNum = attendNum;
    }

    public int getAttendState() {
        return attendState;
    }

    public void setAttendState(int attendState) {
        this.attendState = attendState;
    }

    public String getClockTime() {
        return clockTime;
    }

    public void setClockTime(String clockTime) {
        this.clockTime = clockTime;
    }
}
