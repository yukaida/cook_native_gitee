package com.ebanswers.kitchendiary.config;

import android.text.TextUtils;
import android.util.SparseArray;

import com.ebanswers.kitchendiary.R;
import com.ebanswers.kitchendiary.utils.LanguageUtil;


/**
 * Created by pp on 2018/6/4.
 */

public class QRCodeMap {
    private SparseArray<String> map;

    private QRCodeMap() {
        map = new SparseArray<>();
        map.put(-1, LanguageUtil.getInstance().getStringById(R.string.qr_error_system));
        map.put(-2, LanguageUtil.getInstance().getStringById(R.string.register_exist));
        map.put(-3, LanguageUtil.getInstance().getStringById(R.string.password_error));
        map.put(-4, LanguageUtil.getInstance().getStringById(R.string.account_error));
        map.put(0, LanguageUtil.getInstance().getStringById(R.string.qr_error_success));
        map.put(1001, LanguageUtil.getInstance().getStringById(R.string.qr_error_1001));
        map.put(1002, LanguageUtil.getInstance().getStringById(R.string.qr_error_1002));
        map.put(1003, LanguageUtil.getInstance().getStringById(R.string.qr_error_1003));
        map.put(1004, LanguageUtil.getInstance().getStringById(R.string.qr_error_1004));
        map.put(1005, LanguageUtil.getInstance().getStringById(R.string.qr_error_1005));
        map.put(1006, LanguageUtil.getInstance().getStringById(R.string.qr_error_1006));
        map.put(1007, LanguageUtil.getInstance().getStringById(R.string.qr_error_1007));
        map.put(1008, LanguageUtil.getInstance().getStringById(R.string.qr_error_1008));
        map.put(1009, LanguageUtil.getInstance().getStringById(R.string.qr_error_1009));
        map.put(1010, LanguageUtil.getInstance().getStringById(R.string.qr_error_1010));
        map.put(2001, LanguageUtil.getInstance().getStringById(R.string.qr_error_2001));
        map.put(2002, LanguageUtil.getInstance().getStringById(R.string.qr_error_2002));
        map.put(2003, LanguageUtil.getInstance().getStringById(R.string.qr_error_2003));
        map.put(2004, LanguageUtil.getInstance().getStringById(R.string.qr_error_2004));
        map.put(2005, LanguageUtil.getInstance().getStringById(R.string.qr_error_2005));
        map.put(2006, LanguageUtil.getInstance().getStringById(R.string.qr_error_2006));
        map.put(2007, LanguageUtil.getInstance().getStringById(R.string.qr_error_2007));
        map.put(2008, LanguageUtil.getInstance().getStringById(R.string.qr_error_2008));
        map.put(2009, LanguageUtil.getInstance().getStringById(R.string.qr_error_2009));
        map.put(2010, LanguageUtil.getInstance().getStringById(R.string.qr_error_2010));
        map.put(2011, LanguageUtil.getInstance().getStringById(R.string.qr_error_2011));
        map.put(2012, LanguageUtil.getInstance().getStringById(R.string.qr_error_2012));
        map.put(2013, LanguageUtil.getInstance().getStringById(R.string.qr_error_2013));
        map.put(2014, LanguageUtil.getInstance().getStringById(R.string.qr_error_2014));
        map.put(2015, LanguageUtil.getInstance().getStringById(R.string.qr_error_2015));
        map.put(2016, LanguageUtil.getInstance().getStringById(R.string.qr_error_2016));
        map.put(2017, LanguageUtil.getInstance().getStringById(R.string.qr_error_2017));
        map.put(3001, LanguageUtil.getInstance().getStringById(R.string.qr_error_3001));
        map.put(3002, LanguageUtil.getInstance().getStringById(R.string.qr_error_3002));
        map.put(3003, LanguageUtil.getInstance().getStringById(R.string.qr_error_3003));
        map.put(3004, LanguageUtil.getInstance().getStringById(R.string.qr_error_3004));
        map.put(3005, LanguageUtil.getInstance().getStringById(R.string.qr_error_3005));
        map.put(3006, LanguageUtil.getInstance().getStringById(R.string.qr_error_3006));
        map.put(4009, LanguageUtil.getInstance().getStringById(R.string.check_fail));
    }

    private String getMessage(int code) {
        String msg = map.get(code);
        if (TextUtils.isEmpty(msg)) {
            msg = LanguageUtil.getInstance().getStringById(R.string.qr_error_unknown);
        }
        return msg;
    }

    private static class Holder {
        public static QRCodeMap instance = new QRCodeMap();
    }

    public static String getMsg(int code) {
        return Holder.instance.getMessage(code);
    }

    public static boolean isQrInvalid(int code) {
        return code == 3006 ;
    }

    public static boolean isSucceed(int code) {
        return code == 0;
    }


}
