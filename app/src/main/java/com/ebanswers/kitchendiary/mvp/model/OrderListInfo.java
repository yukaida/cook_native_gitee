package com.ebanswers.kitchendiary.mvp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Create by dongli
 * Create date 2019-09-23
 * desc：
 */
public class OrderListInfo implements Parcelable {
    /**
     * pay_amount : 100.0000
     * pay_actualamount : 98.0000
     * pay_successdate : 0
     * pay_bankname : 支付宝
     * pay_orderid : 20190512020421539999
     */

    private String pay_amount;
    private String pay_actualamount;
    private String pay_successdate;
    private String pay_bankname;
    private String pay_orderid;

    protected OrderListInfo(Parcel in) {
        pay_amount = in.readString();
        pay_actualamount = in.readString();
        pay_successdate = in.readString();
        pay_bankname = in.readString();
        pay_orderid = in.readString();
    }

    public static final Creator<OrderListInfo> CREATOR = new Creator<OrderListInfo>() {
        @Override
        public OrderListInfo createFromParcel(Parcel in) {
            return new OrderListInfo(in);
        }

        @Override
        public OrderListInfo[] newArray(int size) {
            return new OrderListInfo[size];
        }
    };

    public String getPay_amount() {
        return pay_amount;
    }

    public void setPay_amount(String pay_amount) {
        this.pay_amount = pay_amount;
    }

    public String getPay_actualamount() {
        return pay_actualamount;
    }

    public void setPay_actualamount(String pay_actualamount) {
        this.pay_actualamount = pay_actualamount;
    }

    public String getPay_successdate() {
        return pay_successdate;
    }

    public void setPay_successdate(String pay_successdate) {
        this.pay_successdate = pay_successdate;
    }

    public String getPay_bankname() {
        return pay_bankname;
    }

    public void setPay_bankname(String pay_bankname) {
        this.pay_bankname = pay_bankname;
    }

    public String getPay_orderid() {
        return pay_orderid;
    }

    public void setPay_orderid(String pay_orderid) {
        this.pay_orderid = pay_orderid;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(pay_amount);
        dest.writeString(pay_actualamount);
        dest.writeString(pay_successdate);
        dest.writeString(pay_bankname);
        dest.writeString(pay_orderid);
    }
}
