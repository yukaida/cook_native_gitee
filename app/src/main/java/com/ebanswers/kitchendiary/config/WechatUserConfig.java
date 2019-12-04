package com.ebanswers.kitchendiary.config;

import android.content.Context;
import android.text.TextUtils;
import android.webkit.CookieManager;

import com.ebanswers.kitchendiary.bean.WechatUserInfo;
import com.ebanswers.kitchendiary.common.CommonApplication;
import com.ebanswers.kitchendiary.utils.SPUtils;
import com.google.gson.Gson;

import static com.ebanswers.kitchendiary.config.Constans.Access_Refresh_Token_Key;
import static com.ebanswers.kitchendiary.config.Constans.Access_Token_Key;
import static com.ebanswers.kitchendiary.config.Constans.Union_Id_Key;
import static com.ebanswers.kitchendiary.utils.SPUtils.get;

/**
 * @author Created by lishihui on 2017/1/12.
 */

public class WechatUserConfig {
    public static void setAccessToken(Context context, String accesstoken) {
        SPUtils.put(context, Access_Token_Key, accesstoken);
    }

    public static void setRefreshAccessToken(Context context, String refresh_accesstoken) {
        SPUtils.put(context, Access_Refresh_Token_Key, refresh_accesstoken);
    }

    public static void setUnionId(Context context, String unionId) {
        SPUtils.put(context, Union_Id_Key, unionId);
    }

    public static void setWechatOpenId(Context context, String openId) {
        SPUtils.put(context, Constans.Open_Id_Key, openId);
    }

    public static void setWechatHeadUrl(Context context, String head_url) {
        SPUtils.put(context, Constans.Wechat_Head_Url, head_url);
    }

    public static void setWechatUserInfo(WechatUserInfo wechatUserInfo) {
        if (wechatUserInfo != null) {
            Gson gson = new Gson();
            String wechatuserInfoJson = gson.toJson(wechatUserInfo);
            SPUtils.put(CommonApplication.getInstance(), Constans.WECHAT_USERINFO_JSON, wechatuserInfoJson);
        }

    }

    public static void setWechatNickName(Context context, String nickname) {
        SPUtils.put(context, Constans.Wechat_NickName, nickname);
    }

    public static void setWechatPublicOpenId(Context context, String publicOpenId) {
        SPUtils.put(context, Constans.Public_Number_OpenId_Key, publicOpenId);
    }

    public static void removeWechatPublicOpenId() {
        SPUtils.remove(CommonApplication.getInstance(), Constans.Public_Number_OpenId_Key);
    }

    public static String getAccessToken(Context context) {
        return (String) get(context, Access_Token_Key, "");
    }

    public static String getRefreshAccessToken(Context context) {
        return (String) get(context, Access_Refresh_Token_Key, "");
    }

    public static String getUnionId(Context context) {
        return (String) get(context, Union_Id_Key, "");
    }

    public static String getWechatOpenId(Context context) {
        return (String) get(context, Constans.Open_Id_Key, "");
    }

    public static String getWechatHeadUrl(Context context) {
        return (String) get(context, Constans.Wechat_Head_Url, "");
    }

    public static String getWechatNickName(Context context) {
        return (String) get(context, Constans.Wechat_NickName, "");
    }

    public static String getWechatPublicOpenId(Context context) {
        return (String) get(context, Constans.Public_Number_OpenId_Key, "");
    }

    public static WechatUserInfo getWechatUserinfo() {
        String wechat_json = (String) get(CommonApplication.getInstance(), Constans.WECHAT_USERINFO_JSON, "");
        if (!TextUtils.isEmpty(wechat_json)) {
            Gson gson = new Gson();
            return gson.fromJson(wechat_json, WechatUserInfo.class);
        }
        return null;
    }

    public static void removeWechatUserInfo() {
        SPUtils.remove(CommonApplication.getInstance(), Constans.WECHAT_USERINFO_JSON);
    }

    public static void clear(Context context) {
        setAccessToken(context, "");
        removeWechatPublicOpenId();
        removeWechatUserInfo();
        CookieManager manager = CookieManager.getInstance();
        manager.removeAllCookie();
    }

}
