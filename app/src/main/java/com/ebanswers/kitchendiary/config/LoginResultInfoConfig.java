package com.ebanswers.kitchendiary.config;

import android.content.Context;
import android.text.TextUtils;

import com.ebanswers.kitchendiary.bean.LoginResultInfo;
import com.ebanswers.kitchendiary.common.CommonApplication;
import com.ebanswers.kitchendiary.utils.SPUtils;
import com.google.gson.Gson;

/**
 * @author
 * Created by lishihui on 2017/4/29.
 */

public class LoginResultInfoConfig {

    public static void setLoginResultInfo(LoginResultInfo loginResultInfo) {
        if (loginResultInfo != null) {
            Gson gson = new Gson();
            SPUtils.put(CommonApplication.getInstance(), Constans.Login_Result_Info_JSON, gson.toJson(loginResultInfo));
        }
    }

    public static LoginResultInfo getLoginResultInfo() {
        return getLoginResultInfo(CommonApplication.getInstance());
    }
    public static LoginResultInfo getLoginResultInfo(Context context) {
        String loginResultInfo = (String) SPUtils.get(context, Constans.Login_Result_Info_JSON, "");
        if (!TextUtils.isEmpty(loginResultInfo)) {
            Gson gson = new Gson();
            return gson.fromJson(loginResultInfo, LoginResultInfo.class);
        }
        return null;
    }
}
