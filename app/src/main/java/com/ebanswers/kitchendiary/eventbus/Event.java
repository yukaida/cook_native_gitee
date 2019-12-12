package com.ebanswers.kitchendiary.eventbus;

/**
 * Created by Administrator on 2018/4/20.
 */

public class Event {

    public static final int EVENT_APPSCT_INVALID= 1;    //被别人登录了
    public static final int EVENT_UPDATE_FOUBND= 2; // 刷新待处理
    public static final int EVENT_UPDATE_TOMINE= 5; // 刷新待处理
    public static final int EVENT_UPDATE_MINE= 4; // 刷新我的界面
    public static final int EVENT_NET= 3; // 刷新待处理
    public static final int EVENT_SEND_FAIL= 6; // 发布失败
    public static final int EVENT_SAVE_FAIL= 7; // 保存失败
    public static final int EVENT_SAVE_SUCCESS= 8; // 保存成功
    public static final int EVENT_SEND_SUCCESS= 9; // 发布失败

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
