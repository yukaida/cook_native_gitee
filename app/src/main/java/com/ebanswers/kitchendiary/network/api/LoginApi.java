package com.ebanswers.kitchendiary.network.api;


import com.ebanswers.kitchendiary.bean.CommentInfoMore;
import com.ebanswers.kitchendiary.bean.DeleteDRBack;
import com.ebanswers.kitchendiary.bean.Drafts;
import com.ebanswers.kitchendiary.bean.DraftsDeleteBack;
import com.ebanswers.kitchendiary.bean.FoundHomeInfo;
import com.ebanswers.kitchendiary.bean.FoundLoadMoreInfo;
import com.ebanswers.kitchendiary.bean.HomePageInfo;
import com.ebanswers.kitchendiary.bean.PostDiaryBack;
import com.ebanswers.kitchendiary.bean.SquareInfo;
import com.ebanswers.kitchendiary.bean.Topics.Topics;
import com.ebanswers.kitchendiary.bean.UserInfo;
import com.ebanswers.kitchendiary.bean.draftsDetail.DraftsDetail;
import com.ebanswers.kitchendiary.network.response.BaseResponse;
import com.ebanswers.kitchendiary.network.response.CookbookResponse;
import com.ebanswers.kitchendiary.network.response.DiaryResponse;
import com.ebanswers.kitchendiary.network.response.FocusResponse;
import com.ebanswers.kitchendiary.network.response.FoundTopResponse;
import com.ebanswers.kitchendiary.network.response.ImageResponse;
import com.ebanswers.kitchendiary.network.response.LoginResponse;
import com.ebanswers.kitchendiary.network.response.MessageResponse;

import java.util.HashMap;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2018/1/26.
 */

public interface LoginApi {

    @FormUrlEncoded
    @POST(Api.LOGIN)
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    Observable<LoginResponse> login(@Field("username") String username,
                                    @Field("password") String password);

    @FormUrlEncoded
    @POST(Api.MODIFY_PASS)
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    Observable<BaseResponse> modifyPass(@Field("username") String username,
                                         @Field("password") String password,
                                         @Field("confirmpassword") String confirmpassword,
                                         @Field("session") String session);
//    @FormUrlEncoded
    @GET(Api.HOME_INFO)
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    Observable<HomePageInfo> userinfo(@Query("code") String code,
                                      @Query("result") String result,
                                      @Query("openid") String openid);



    @FormUrlEncoded
    @POST(Api.Square)
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    Observable<SquareInfo> square(@Field("action") String action,
                                  @Field("total") String total,
                                  @Field("openid") String openid,
                                  @Field("types") String types);


    @FormUrlEncoded
    @POST(Api.follower)
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    Observable<BaseResponse> follower(@Field("action") String action,
                                  @Field("follower_id") String follower_id,
                                  @Field("openid") String openid);


    //    @FormUrlEncoded
    @GET(Api.Square)
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    Observable<FoundHomeInfo> foundinfo(@Query("code") String code,
                                        @Query("result") String result,
                                        @Query("openid") String openid);

    //    @FormUrlEncoded
    @GET(Api.Square)
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    Observable<FocusResponse> focuinfo(@Query("code") String code,
                                       @Query("types") String types,
                                       @Query("result") String result,
                                       @Query("openid") String openid);


    //草稿箱------------------------------------
    @GET(Api.draftdata)
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    Observable<Drafts> draft(@Query("result") String result,
                             @Query("openid") String openid,
                             @Query("code") String code);

    //删除草稿箱子项----------------------------------
    @FormUrlEncoded
    @POST(Api.draftdelete)
    @Headers("Content-Type:application/x-www-form-urlencoded;arset=utf-8")
    Observable<DraftsDeleteBack> draftsdelete(@Field("action") String action,
                                              @Field("draft_id") String draft_id,
                                              @Field("openid") String openid);


    //获取草稿箱子项细节
    @FormUrlEncoded
    @POST(Api.draftdetail)
    @Headers("Content-Type:application/x-www-form-urlencoded;arset=utf-8")
    Observable<DraftsDetail> draftsDetail(@Field("action") String action,
                                          @Field("draft_id") String draft_id,
                                          @Field("openid") String openid);


    //获取草稿箱子项细节
    @FormUrlEncoded
    @POST(Api.getMoreComment)
    @Headers("Content-Type:application/x-www-form-urlencoded;arset=utf-8")
    Observable<CommentInfoMore> getMoreComment(@Field("action") String action,
                                             @Field("diary_id") String diary_id,
                                             @Field("total") String total);

    // 删除动态--------------------------------------------------------
    @FormUrlEncoded
    @POST(Api.Square)
    @Headers("Content-Type:application/x-www-form-urlencoded;arset=utf-8")
    Observable<DeleteDRBack> FoundDelete(@Field("action") String action,
                                         @Field("diary_id") String diary_id,
                                         @Field("'openid'") String from_openid);


    // 获取话题--------------------------------------------------------
    @FormUrlEncoded
    @POST(Api.topics)
    @Headers("Content-Type:application/x-www-form-urlencoded;arset=utf-8")
    Observable<Topics> getTopic(@Field("openid") String openid);


    @FormUrlEncoded
    @POST(Api.Square)
    @Headers("Content-Type:application/x-www-form-urlencoded;arset=utf-8")
    Observable<FoundLoadMoreInfo> foundinfoLoadMore(@Field("action") String action,
                                                    @Field("total") String total,
                                                    @Field("openid") String openid,
                                                    @Field("types") String types,
                                                    @Field("diary_id") String diary_id,
                                                    @Field("ctg") String ctg,
                                                    @Field("scope") String scope,
                                                    @Field("lang") String lang);



    //2.点赞/取消点赞
    @FormUrlEncoded
    @POST(Api.Square)
    @Headers("Content-Type:application/x-www-form-urlencoded;arset=utf-8")
    Observable<BaseResponse> islike(@Field("action") String action,
                                                    @Field("diary_id") String diary_id,
                                                    @Field("nickname") String nickname);

    //收藏
    @FormUrlEncoded
    @POST(Api.collect)
    @Headers("Content-Type:application/x-www-form-urlencoded;arset=utf-8")
    Observable<BaseResponse> collect(@Field("action") String action,
                                                    @Field("diary_id") String diary_id,
                                                    @Field("diary_openid") String diary_openid);
    //取消收藏
    @FormUrlEncoded
    @POST(Api.collect)
    @Headers("Content-Type:application/x-www-form-urlencoded;arset=utf-8")
    Observable<BaseResponse> uncollect(@Field("action") String action,
                                                    @Field("diary_id") String diary_id);


    // 评论
    @FormUrlEncoded
    @POST(Api.Square)
    @Headers("Content-Type:application/x-www-form-urlencoded;arset=utf-8")
    Observable<BaseResponse> comment(@Field("action") String action,
                                    @Field("diary_id") String diary_id,
                                    @Field("comment") String comment,
                                    @Field("to_openid") String to_openid,
                                    @Field("to_user") String to_user);


    // 评论
    @FormUrlEncoded
    @POST(Api.Square)
    @Headers("Content-Type:application/x-www-form-urlencoded;arset=utf-8")
    Observable<BaseResponse> commentReply(@Field("action") String action,
                                     @Field("diary_id") String diary_id,
                                     @Field("comment") String comment,
                                     @Field("to_openid") String to_openid,
                                     @Field("to_user") String to_user ,
                                     @Field("from_openid") String from_openid,
                                     @Field("from_user") String from_user);




    // 删除评论
    @FormUrlEncoded
    @POST(Api.Square)
    @Headers("Content-Type:application/x-www-form-urlencoded;arset=utf-8")
    Observable<BaseResponse> commentDelete(@Field("action") String action,
                                    @Field("diary_id") String diary_id,
                                    @Field("comment") String comment,
                                    @Field("from_openid") String from_openid,
                                    @Field("to_user") String to_user);

//    我的信息
    //    @FormUrlEncoded
    @GET(Api.mineinfo)
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    Observable<UserInfo> mineinfo(@Query("v") String v,
                                  @Query("openid") String openid);
    //  日记
    @FormUrlEncoded
    @POST(Api.mine)
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    Observable<DiaryResponse> diary(@Field("action") String action,
                                    @Field("total") String total,
                                    @Field("openid") String openid,
                                    @Field("types") String types,
                                    @Field("is_first") String is_first);


    //  菜谱
    @FormUrlEncoded
    @POST(Api.mine)
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    Observable<CookbookResponse> cookbook(@Field("action") String action,
                                          @Field("total") String total,
                                          @Field("openid") String openid,
                                          @Field("types") String types,
                                          @Field("is_first") String is_first);
    //  消息
    @FormUrlEncoded
    @POST(Api.messageInfo)
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    Observable<MessageResponse> messageInfo(@Field("openid") String openid,
                                            @Field("first_login") String first_login);

    //  修改昵称和头像
    @FormUrlEncoded
    @POST(Api.changemsg)
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    Observable<BaseResponse> changeName(@Field("openid") String openid,
                                        @Field("name") String name,
                                        @Field("types") String types);

    //  修改头像
    @FormUrlEncoded
    @POST(Api.changemsg)
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    Observable<BaseResponse> changeHeadUrl(@Field("openid") String openid,
                                        @Field("head_url") String head_url,
                                        @Field("types") String types);
    //  消息
//    @FormUrlEncoded
    @GET(Api.draftnum)
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    Observable<DrufNumResponse> draftnum(@Query("openid") String openid);

    //  消息
    @FormUrlEncoded
    @POST(Api.cookbook)
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    Observable<BaseResponse> sendcookbook(@Field("data") String data,
                                      @Field("action") String action);


    // 图片上传
    @Multipart
    @POST(Api.upload_img)
    Observable<ImageResponse> uploadImg(@Part MultipartBody.Part image,
                                        @Part("watermark") RequestBody watermark);
    //发布日记
    @FormUrlEncoded
    @POST(Api.postDiary)
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    Observable<PostDiaryBack> postDiary(@FieldMap HashMap<String, String> map);


    //发布日记图片上传
    @POST(Api.upload_img)
    Observable<ResponseBody> upLoad_diary(@Body RequestBody Body);

    //  消息
    @GET(Api.topic)
    Observable<FoundTopResponse> topic();

}
