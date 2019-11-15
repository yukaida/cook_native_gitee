package com.ebanswers.kitchendiary.network.response;

/**
 * Create by dongli
 * Create date 2019-11-07
 * desc：
 */
public class MessageResponse extends BaseResponse{

    /**
     * is_remind : false
     * msg_num : 0
     */

    private boolean is_remind;
    private int msg_num;

    public boolean isIs_remind() {
        return is_remind;
    }

    public void setIs_remind(boolean is_remind) {
        this.is_remind = is_remind;
    }

    public int getMsg_num() {
        return msg_num;
    }

    public void setMsg_num(int msg_num) {
        this.msg_num = msg_num;
    }
}
