package com.ebanswers.kitchendiary.bean;

/**
 * @author
 * Created by lishihui on 2017/1/10.
 */

public class LoginResultInfo {


    /**
     * code : 0
     * msg : oYazTso5Aqjj9PzkoPggHjTeJJTI
     * data : {"is_need_update":0,"type":"0","token":"token","expire_in":36000,"mosquitto_host":"192.168.0.62","mosquitto_port":1183,"mosquitto_client_id":"eb-client11","mosquitto_sub_user":"eb_pub_xingxing","mosquitto_sub_pwd":"pwd1","mosquitto_topic":"xingxing/user/qwe"}
     */

    private int code;
    private String msg;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * is_need_update : 0
         * type : 0
         * token : token
         * expire_in : 36000
         * mosquitto_host : 192.168.0.62
         * mosquitto_port : 1183
         * mosquitto_client_id : eb-client11
         * mosquitto_sub_user : eb_pub_xingxing
         * mosquitto_sub_pwd : pwd1
         * mosquitto_topic : xingxing/user/qwe
         */

        private int is_need_update;
        private String type;
        private String token;
        private int expire_in;
        private String mosquitto_host;
        private int mosquitto_port;
        private String mosquitto_client_id;
        private String mosquitto_sub_user;
        private String mosquitto_sub_pwd;
        private String mosquitto_topic;
        private int need_bind_phone;

        public int getNeed_bind_phone() {
            return need_bind_phone;
        }

        public void setNeed_bind_phone(int need_bind_phone) {
            this.need_bind_phone = need_bind_phone;
        }

        public int getIs_need_update() {
            return is_need_update;
        }

        public void setIs_need_update(int is_need_update) {
            this.is_need_update = is_need_update;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public int getExpire_in() {
            return expire_in;
        }

        public void setExpire_in(int expire_in) {
            this.expire_in = expire_in;
        }

        public String getMosquitto_host() {
            return mosquitto_host;
        }

        public void setMosquitto_host(String mosquitto_host) {
            this.mosquitto_host = mosquitto_host;
        }

        public int getMosquitto_port() {
            return mosquitto_port;
        }

        public void setMosquitto_port(int mosquitto_port) {
            this.mosquitto_port = mosquitto_port;
        }

        public String getMosquitto_client_id() {
            return mosquitto_client_id;
        }

        public void setMosquitto_client_id(String mosquitto_client_id) {
            this.mosquitto_client_id = mosquitto_client_id;
        }

        public String getMosquitto_sub_user() {
            return mosquitto_sub_user;
        }

        public void setMosquitto_sub_user(String mosquitto_sub_user) {
            this.mosquitto_sub_user = mosquitto_sub_user;
        }

        public String getMosquitto_sub_pwd() {
            return mosquitto_sub_pwd;
        }

        public void setMosquitto_sub_pwd(String mosquitto_sub_pwd) {
            this.mosquitto_sub_pwd = mosquitto_sub_pwd;
        }

        public String getMosquitto_topic() {
            return mosquitto_topic;
        }

        public void setMosquitto_topic(String mosquitto_topic) {
            this.mosquitto_topic = mosquitto_topic;
        }
    }
}
