package com.ebanswers.kitchendiary.config;


import com.ebanswers.kitchendiary.common.CommonApplication;
import com.ebanswers.kitchendiary.utils.LanguageUtil;
import com.ebanswers.kitchendiary.utils.SPUtils;

/**
 * Created by lishihui on 2017/4/28.
 */

public class PhoneUserConfig {

    public static void setPhoneNumber(String phoneNumber) {
        SPUtils.put(CommonApplication.getInstance(), Constans.PHONE_NUMBER, phoneNumber);
    }

    public static String getPhoneNumber() {
        return (String) SPUtils.get(CommonApplication.getInstance(), Constans.PHONE_NUMBER, "");
    }

    public static void setEmail(String email) {
        SPUtils.put(CommonApplication.getInstance(), Constans.EMAIL_ACCOUNT, email);
    }

    public static String getEmail() {
        return (String) SPUtils.get(CommonApplication.getInstance(), Constans.EMAIL_ACCOUNT, "");
    }

    public static void removePhoneNumber() {
        SPUtils.remove(CommonApplication.getInstance(), Constans.PHONE_NUMBER);
    }

    public static void setNationCode(int code) {
        SPUtils.put(CommonApplication.getInstance(), Constans.NATION_CODE, code);
    }

    public static int getNationCode() {
        return (int) SPUtils.get(CommonApplication.getInstance(), Constans.NATION_CODE, LanguageUtil.getInstance().isChinease() ? 86 : 1);
    }

    public static boolean isChinaNationCode() {
        return getNationCode() == 86;
    }


}

