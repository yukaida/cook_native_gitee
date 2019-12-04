package com.ebanswers.kitchendiary.eventbus;

/**
 * Created by Administrator on 2018/4/20.
 */

public class Event {

    public static final int EVENT_APPSCT_INVALID= 1;    //被别人登录了
    public static final int EVENT_UPDATE_FOUBND= 2; // 刷新待处理
    public static final int EVENT_UPDATE_MINE= 4; // 刷新我的界面
    public static final int EVENT_NET= 3; // 刷新待处理

    private int mType = 0;

    private String mParam;

    private int mIntParam;

    public Event(int type, String param) {
        mType = type;
        mParam = param;
    }


    public Event(int type, String param, int intParam) {
        mType = type;
        mParam = param;
        mIntParam = intParam;
    }

    public int getType() {
        return mType;
    }

    public String getParam() {
        return mParam;
    }

    public int getIntParam() {
        return mIntParam;
    }

}
