package com.ebanswers.kitchendiary.network.response;


/**
 * Created by Administrator on 2016/4/25.
 */
public class BaseResponse {


    /**
     * data : success
     * code : 0
     */

    private int code;
    /**
     * msg : system error
     * data :
     */

    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
