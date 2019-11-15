package com.ebanswers.kitchendiary.mvp.model;

/**
 * Create by dongli
 * Create date 2019-09-27
 * desc：
 */
public class RecordsLiquidationInfo {

    /**
     * bankname : 中国邮政储蓄银行
     * banknumber : 6230520750012177877
     * bankfullname : 刘关芝
     * money : 100.0000
     * sqdatetime : 2019-09-27 00:45:20
     * status : 1
     */

    private String bankname;
    private String banknumber;
    private String bankfullname;
    private String money;
    private String sqdatetime;
    private String status;

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public String getBanknumber() {
        return banknumber;
    }

    public void setBanknumber(String banknumber) {
        this.banknumber = banknumber;
    }

    public String getBankfullname() {
        return bankfullname;
    }

    public void setBankfullname(String bankfullname) {
        this.bankfullname = bankfullname;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getSqdatetime() {
        return sqdatetime;
    }

    public void setSqdatetime(String sqdatetime) {
        this.sqdatetime = sqdatetime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
