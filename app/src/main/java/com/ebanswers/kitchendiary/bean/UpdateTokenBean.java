package com.ebanswers.kitchendiary.bean;

/**
 * Created by lishihui on 2017/4/29.
 */

public class UpdateTokenBean {

    /**
     * code : 0
     * data : {"token":"","expire_in":""}
     * errormsg :
     */

    private String code;
    private DataBean data;
    private String errormsg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getErrormsg() {
        return errormsg;
    }

    public void setErrormsg(String errormsg) {
        this.errormsg = errormsg;
    }

    public static class DataBean {
        /**
         * token :
         * expire_in :
         */

        private String token;
        private String expire_in;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getExpire_in() {
            return expire_in;
        }

        public void setExpire_in(String expire_in) {
            this.expire_in = expire_in;
        }
    }
}
