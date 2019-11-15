package com.ebanswers.kitchendiary.network.response;

/**
 * Create by dongli
 * Create date 2019-09-23
 * desc：
 */
public class KefuResponse  extends BaseResponse{

    /**
     * tel : 400000000
     * email : 400000000
     * zstel : 13800013800
     * jishu : 10000@qq.com
     * kefu800 : http://c112.pop800.com/web800/c.do?n=888888&type=1&url=238594&l=cn&at=0
     */

    private String tel;
    private String email;
    private String zstel;
    private String jishu;
    private String kefu800;

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getZstel() {
        return zstel;
    }

    public void setZstel(String zstel) {
        this.zstel = zstel;
    }

    public String getJishu() {
        return jishu;
    }

    public void setJishu(String jishu) {
        this.jishu = jishu;
    }

    public String getKefu800() {
        return kefu800;
    }

    public void setKefu800(String kefu800) {
        this.kefu800 = kefu800;
    }
}
