package com.ebanswers.kitchendiary.utils;

import android.text.TextUtils;

import com.ebanswers.kitchendiary.R;
import com.ebanswers.kitchendiary.config.PhoneUserConfig;


/**
 * Created by lishihui on 2017/4/28.
 */

public class PhoneLoginUtils {

    /**
     * 当前有效账号, 用于在页面之间传递
     */
    public static String currentAccount = "";
    /**
     * 验证国内手机号码, 国外手机号暂不校验
     * <p>
     * 移动号码段:139、138、137、136、135、134、150、151、152、157、158、159、182、183、187、188、147
     * 联通号码段:130、131、132、136、185、186、145
     * 电信号码段:133、153、180、189
     *
     * @param cellphone
     * @return
     */
    public static boolean checkCellphone(String cellphone) {
        if (PhoneUserConfig.isChinaNationCode()) {
            String regex = "^1[3|4|5|7|8][0-9]{9}$";
            return cellphone.matches(regex);
        } else {
            return true;
        }
    }

    public static boolean checkPhoneValidity(String account){
        if (TextUtils.isEmpty(account)) {
            ToastCustom.makeText(R.string.phone_number_is_null).show();
            return false;
        } else if (!checkCellphone(account)) {
            ToastCustom.makeText(R.string.phone_number_is_error).show();
            return false;
        }
        return true;
    }

    public static boolean checkPassword(String password) {
        String regex = "^[a-zA-Z0-9]{6,18}$";
        return password.matches(regex);
    }
    public static boolean checkPasswordValidity(String password){
        if (TextUtils.isEmpty(password)) {
            ToastCustom.makeText(R.string.password_is_null).show();
            return false;
        } else if (!checkPassword(password)) {
            ToastCustom.makeText(R.string.login_password_hint).show();
            return false;
        }
        return true;
    }

    public static boolean checkEmailValidity(String email){
        if (TextUtils.isEmpty(email)) {
            ToastCustom.makeText(R.string.email_is_null).show();
            return false;
        } else if (!checkEmail(email)) {
            ToastCustom.makeText(R.string.email_is_error).show();
            return false;
        }
        return true;
    }

    public static boolean checkEmail(String email) {
//        String reg = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        String reg = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        return email.matches(reg);
    }


}
