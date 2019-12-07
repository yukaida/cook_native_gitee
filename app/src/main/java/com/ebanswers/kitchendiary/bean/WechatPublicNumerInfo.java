package com.ebanswers.kitchendiary.bean;

/**
 * @author
 * Created by lishihui on 2017/1/10.
 */

public class WechatPublicNumerInfo {


    /**
     * msg : oYazTsjMtQvvvFiTqApthwbQ0Lt0
     * data : {"mosquitto_batch_topic":"xingxing/user/batch","mosquitto_client_id":"eb-978d7d78-9755-11e7-a449-00a0d1edbe70","mosquitto_topic":"xingxing/user/oYazTsjMtQvvvFiTqApthwbQ0Lt0","mosquitto_sub_user":"eb_sub_xingxing","mosquitto_port":1883,"expire_in":36000,"is_need_update":0,"mosquitto_sub_pwd":"53iq.com","mosquitto_host":"122.144.167.74","token":"api_token_978d7d78-9755-11e7-a449-00a0d1edbe70","type":"0"}
     * code : 0
     */

    private String msg;
    private DataBean data;
    private int code;

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

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public static class DataBean {
        /**
         * mosquitto_batch_topic : xingxing/user/batch
         * mosquitto_client_id : eb-978d7d78-9755-11e7-a449-00a0d1edbe70
         * mosquitto_topic : xingxing/user/oYazTsjMtQvvvFiTqApthwbQ0Lt0
         * mosquitto_sub_user : eb_sub_xingxing
         * mosquitto_port : 1883
         * expire_in : 36000
         * is_need_update : 0
         * mosquitto_sub_pwd : 53iq.com
         * mosquitto_host : 122.144.167.74
         * token : api_token_978d7d78-9755-11e7-a449-00a0d1edbe70
         * type : 0
         */

        private String mosquitto_batch_topic;
        private String mosquitto_client_id;
        private String mosquitto_topic;
        private String mosquitto_sub_user;
        private int mosquitto_port;
        private int expire_in;
        private int is_need_update;
        private String mosquitto_sub_pwd;
        private String mosquitto_host;
        private String token;
        private String type;

        public String getMosquitto_batch_topic() {
            return mosquitto_batch_topic;
        }

        public void setMosquitto_batch_topic(String mosquitto_batch_topic) {
            this.mosquitto_batch_topic = mosquitto_batch_topic;
        }

        public String getMosquitto_client_id() {
            return mosquitto_client_id;
        }

        public void setMosquitto_client_id(String mosquitto_client_id) {
            this.mosquitto_client_id = mosquitto_client_id;
        }

        public String getMosquitto_topic() {
            return mosquitto_topic;
        }

        public void setMosquitto_topic(String mosquitto_topic) {
            this.mosquitto_topic = mosquitto_topic;
        }

        public String getMosquitto_sub_user() {
            return mosquitto_sub_user;
        }

        public void setMosquitto_sub_user(String mosquitto_sub_user) {
            this.mosquitto_sub_user = mosquitto_sub_user;
        }

        public int getMosquitto_port() {
            return mosquitto_port;
        }

        public void setMosquitto_port(int mosquitto_port) {
            this.mosquitto_port = mosquitto_port;
        }

        public int getExpire_in() {
            return expire_in;
        }

        public void setExpire_in(int expire_in) {
            this.expire_in = expire_in;
        }

        public int getIs_need_update() {
            return is_need_update;
        }

        public void setIs_need_update(int is_need_update) {
            this.is_need_update = is_need_update;
        }

        public String getMosquitto_sub_pwd() {
            return mosquitto_sub_pwd;
        }

        public void setMosquitto_sub_pwd(String mosquitto_sub_pwd) {
            this.mosquitto_sub_pwd = mosquitto_sub_pwd;
        }

        public String getMosquitto_host() {
            return mosquitto_host;
        }

        public void setMosquitto_host(String mosquitto_host) {
            this.mosquitto_host = mosquitto_host;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "mosquitto_batch_topic='" + mosquitto_batch_topic + '\'' +
                    ", mosquitto_client_id='" + mosquitto_client_id + '\'' +
                    ", mosquitto_topic='" + mosquitto_topic + '\'' +
                    ", mosquitto_sub_user='" + mosquitto_sub_user + '\'' +
                    ", mosquitto_port=" + mosquitto_port +
                    ", expire_in=" + expire_in +
                    ", is_need_update=" + is_need_update +
                    ", mosquitto_sub_pwd='" + mosquitto_sub_pwd + '\'' +
                    ", mosquitto_host='" + mosquitto_host + '\'' +
                    ", token='" + token + '\'' +
                    ", type='" + type + '\'' +
                    '}';
        }
    }
}
