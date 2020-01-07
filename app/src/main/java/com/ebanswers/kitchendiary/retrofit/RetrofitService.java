package com.ebanswers.kitchendiary.retrofit;

import com.ebanswers.kitchendiary.bean.LoginResultInfo;
import com.ebanswers.kitchendiary.bean.WechatUserInfo;
import com.ebanswers.kitchendiary.database.bean.AdBean;
import com.ebanswers.kitchendiary.database.bean.WechatConfig;
import com.ebanswers.kitchendiary.database.bean.WechatRefreshConfig;

import java.util.Map;

import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Observable;

/**
 * @author Created by lishihui on 2017/1/9.
 */

public interface RetrofitService {

    @FormUrlEncoded
    @POST()
    Observable<String> checkCodeRight(@Url() String url, @Field("access_token") String access_token, @Field("phone") String phone, @Field("code") String code);


    @GET()
    Observable<String> isExpireAccessToken(@Url() String url);
    @GET()
    Observable<WechatConfig> getAccessToken(@Url() String url);
    @GET()
    Observable<WechatRefreshConfig> getRefreshAccessToken(@Url() String url);
    @FormUrlEncoded
    @POST()
    Observable<String> checkInternationalCodeRight(@Url() String url, @Field("access_token") String access_token, @Field("phone") String phone, @Field("code") String code, @Query("nationcode") String nationCode);

    @GET()
    Observable<String> getPhoneCheckCode(@Url() String url, @Query("access_token") String access_token, @Query("phone") String phone);
    @GET()
    Observable<WechatUserInfo> getUserInfo(@Url() String url);


    @FormUrlEncoded
    @POST()
    Observable<String> accountManager(@Url() String url, @Field("action") String action, @Field("phone") String phone, @Field("password") String password);

    @GET()
    Observable<String> getInternationalPhoneCheckCode(@Url() String url, @Query("access_token") String access_token, @Query("phone") String phone, @Query("nationcode") String nationCode);

    @FormUrlEncoded
    @POST()
    Observable<LoginResultInfo> postUserInfo(@Url() String url, @FieldMap Map<String, String> map);


    @FormUrlEncoded
    @POST()
    Observable<String> isCodeRight(@Url() String url, @Field("action") String action, @Field("email") String email, @Field("phone") String phone,@Field("code") String code);


    //判断用户是否绑定过邮箱
    @GET()
    Observable<String> isOrNotBindEmail(@Url() String url, @Query("phone") String phone);
    @GET()
    Observable<String> getBindInfo(@Url() String url, @Query("mac") String mac);

    @FormUrlEncoded
    @POST()
    Observable<String> getBindcode(@Url() String url, @Field("action") String action, @Field("email") String email, @Field("phone") String phone,@Field("lang") String lang,@Field("subject") String subject);



    @GET()
    Observable<String> getClockDay(@Url() String url, @Query("openid") String openid,@Query("return_type") String json,@Query("v") String wer);


    /**
     * 获取广告
     * @param url
     * @param appType 0全部 1智能厨房  2厨房日记
     * @return
     */
    @GET()
    Observable<AdBean> getAds(@Url() String url, @Query("app") String appType);


}
