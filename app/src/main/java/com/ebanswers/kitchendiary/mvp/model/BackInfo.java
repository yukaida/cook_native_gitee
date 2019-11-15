package com.ebanswers.kitchendiary.mvp.model;

/**
 * Create by dongli
 * Create date 2019-09-23
 * desc：
 */
public class BackInfo {
    /**
     * bankname : 中国邮政储蓄银行
     * subbranch : 春熙路支行
     * accountname : 刘关芝
     * cardnumber : 6230520750012177877
     * province : 四川省
     */

    private String bankname;
    private String subbranch;
    private String accountname;
    private String cardnumber;
    private String province;

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public String getSubbranch() {
        return subbranch;
    }

    public void setSubbranch(String subbranch) {
        this.subbranch = subbranch;
    }

    public String getAccountname() {
        return accountname;
    }

    public void setAccountname(String accountname) {
        this.accountname = accountname;
    }

    public String getCardnumber() {
        return cardnumber;
    }

    public void setCardnumber(String cardnumber) {
        this.cardnumber = cardnumber;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }
}
