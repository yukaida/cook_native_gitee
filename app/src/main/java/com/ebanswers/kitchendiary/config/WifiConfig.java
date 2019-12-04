package com.ebanswers.kitchendiary.config;

import android.content.Context;

import com.ebanswers.kitchendiary.utils.SPUtils;


/**
 * Created by caixd on 2017/12/14.
 */

public class WifiConfig {

    public static void setPassword(Context context, String ssid, String pwd) {
        SPUtils.put(context, ssid, pwd);
    }

    public static String getPassword(Context context, String ssid) {
        return (String) SPUtils.get(context, ssid, "");
    }
}
