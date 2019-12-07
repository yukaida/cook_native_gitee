package com.ebanswers.kitchendiary.network.api;


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
import com.ebanswers.kitchendiary.network.NetworkManager;
import com.ebanswers.kitchendiary.network.function.HttpResultFunction;
import com.ebanswers.kitchendiary.network.function.ServerResultFunction;
import com.ebanswers.kitchendiary.network.response.BaseResponse;
import com.ebanswers.kitchendiary.network.response.CookbookResponse;
import com.ebanswers.kitchendiary.network.response.DiaryResponse;
import com.ebanswers.kitchendiary.network.response.FocusResponse;
import com.ebanswers.kitchendiary.network.response.FoundTopResponse;
import com.ebanswers.kitchendiary.network.response.ImageResponse;
import com.ebanswers.kitchendiary.network.response.LoginResponse;
import com.ebanswers.kitchendiary.network.response.MessageResponse;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * 开始网络请求的步骤
 * Created by DeMon on 2017/9/6.
 */

public class ApiMethods {

    /**
     * 封装线程管理和订阅的过程
     */
    public static void ApiSubscribe(Observable observable, Observer observer, LifecycleProvider lifecycle) {
        observable.subscribeOn(Schedulers.io())
                .map(new ServerResultFunction())
                .onErrorResumeNext(new HttpResultFunction<>())
                .compose(lifecycle.bindToLifecycle())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public static void ApiSubscribe(Observable observable, Observer observer) {
        observable.subscribeOn(Schedulers.io())
//                .map(new ServerResultFunction())
//                .onErrorResumeNext(new HttpResultFunction<>())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    //登录
    public static void login(Observer<LoginResponse> observer, String userName, String userPassword) {
        ApiSubscribe(NetworkManager.getLoginApi().login(userName, userPassword), observer);
    }

    //首页信息
    public static void userinfo(Observer<HomePageInfo> observer, String code, String result, String openid) {
        ApiSubscribe(NetworkManager.getLoginApi().userinfo(code, result,openid), observer);
    }

    //发现信息
    public static void foundinfo(Observer<FoundHomeInfo> observer, String code, String result, String openid) {
        ApiSubscribe(NetworkManager.getLoginApi().foundinfo(code, result,openid), observer);
    }

    //关注信息
    public static void focuinfo(Observer<FocusResponse> observer, String code, String types, String result, String openid) {
        ApiSubscribe(NetworkManager.getLoginApi().focuinfo(code,types, result,openid), observer);
    }

    //更多精彩
    public static void square(Observer<SquareInfo> observer, String action, String total, String openid, String types) {
        ApiSubscribe(NetworkManager.getLoginApi().square(action, total,openid,types), observer);
    }


    //发布日记------------
    public static void postDiary(Observer<PostDiaryBack> observer, HashMap<String, String> map) {
        ApiSubscribe(NetworkManager.getLoginApi().postDiary(map), observer);
    }

    // 草稿箱 加载更多--------------------------------------------------------------------------------
    public static void draftsloadmore(Observer<Drafts> observer, String result, String openid, String code) {
        ApiSubscribe(NetworkManager.getLoginApi().draft(result, openid,code), observer);
    }

    //草稿箱 删除子项--------------------------------------------------
    public static void draftsdelete(Observer<DraftsDeleteBack> observer, String action, String draft_id, String openid) {
        ApiSubscribe(NetworkManager.getLoginApi().draftsdelete(action, draft_id,openid), observer);
    }

    //草稿箱 点击子项加载 详细数据------------------------------------------
    public static void draftsGetDetail(Observer<DraftsDetail> observer, String action, String draft_id, String openid) {
        ApiSubscribe(NetworkManager.getLoginApi().draftsDetail(action, draft_id,openid), observer);
    }

    //删除动态------------------------------------------------------------
    public static void FoundtDelete(Observer<BaseResponse> observer, String action, String diary_id, String from_openid) {
        ApiSubscribe(NetworkManager.getLoginApi().FoundDelete(action, diary_id,from_openid), observer);
    }

    //获取话题列表---------------------------------------------------------
    public static void getTopic(Observer<Topics> observer, String openid) {
        ApiSubscribe(NetworkManager.getLoginApi().getTopic(openid), observer);
    }



    //更多发现
    public static void foundinfoLoadMore(Observer<FoundLoadMoreInfo> observer, String action, String total, String openid, String types,
                                         String diary_id, String ctg, String scope, String lang) {
        ApiSubscribe(NetworkManager.getLoginApi().foundinfoLoadMore(action, total,openid,types,diary_id,ctg,scope,lang), observer);
    }


    //关注
    public static void follower(Observer<BaseResponse> observer, String action, String follower_id, String openid) {
        ApiSubscribe(NetworkManager.getLoginApi().follower(action, follower_id,openid), observer);
    }


    //点赞
    public static void islike(Observer<BaseResponse> observer, String action, String diary_id, String nickname) {
        ApiSubscribe(NetworkManager.getLoginApi().islike(action, diary_id,nickname), observer);
    }

    //收藏
    public static void collect(Observer<BaseResponse> observer, String action, String diary_id, String diary_openid) {
        ApiSubscribe(NetworkManager.getLoginApi().collect(action, diary_id,diary_openid), observer);
    }

    //收藏取消
    public static void uncollect(Observer<BaseResponse> observer, String action, String diary_id) {
        ApiSubscribe(NetworkManager.getLoginApi().uncollect(action, diary_id), observer);
    }

    //评论
    public static void comment(Observer<BaseResponse> observer, String action, String diary_id, String comment,
                              String to_openid, String to_user) {
        ApiSubscribe(NetworkManager.getLoginApi().comment(action, diary_id,comment,to_openid, to_user), observer);
    }

    //评论
    public static void commentReply(Observer<BaseResponse> observer, String action, String diary_id, String comment,
                              String to_openid, String to_user,String from_openid,String from_user) {
        ApiSubscribe(NetworkManager.getLoginApi().commentReply(action, diary_id,comment,to_openid, to_user,from_openid,from_user), observer);
    }




    //删除评论
    public static void commentDelete(Observer<BaseResponse> observer, String action, String diary_id,String comment,
                                     String from_openid, String to_user) {
        ApiSubscribe(NetworkManager.getLoginApi().commentDelete(action, diary_id,comment,from_openid,to_user), observer);
    }

    //我的日记
    public static void mineinfo(Observer<UserInfo> observer, String v, String openid) {
        ApiSubscribe(NetworkManager.getLoginApi().mineinfo(v, openid), observer);
    }

    //我的日记
    public static void diary(Observer<DiaryResponse> observer, String action, String total,
                             String openid, String types, String is_first) {
        ApiSubscribe(NetworkManager.getLoginApi().diary(action, total,openid,types,is_first), observer);
    }


    //我的菜谱
    public static void cookbook(Observer<CookbookResponse> observer, String action, String total,
                                String openid, String types, String is_first) {
        ApiSubscribe(NetworkManager.getLoginApi().cookbook(action, total,openid,types,is_first), observer);
    }

    //消息数
    public static void messageInfo(Observer<MessageResponse> observer, String openid, String first_login) {
        ApiSubscribe(NetworkManager.getLoginApi().messageInfo(openid,first_login), observer);
    }

    //草稿数量
    public static void draftnum(Observer<DrufNumResponse> observer, String openid) {
        ApiSubscribe(NetworkManager.getLoginApi().draftnum(openid), observer);
    }

    //修改用户信息
    public static void changeHeadUrl(Observer<BaseResponse> observer, String openid, String head_url, String types) {
        ApiSubscribe(NetworkManager.getLoginApi().changeHeadUrl(openid,head_url,types), observer);
    }

    //修改用户信息
    public static void changeName(Observer<BaseResponse> observer, String openid,String name, String types) {
        ApiSubscribe(NetworkManager.getLoginApi().changeName(openid,name,types), observer);
    }


    //保存发布菜谱
    public static void sendcookbook(Observer<BaseResponse> observer, String allMsgFound, String action) {
        ApiSubscribe(NetworkManager.getLoginApi().sendcookbook(allMsgFound,action), observer);
    }


   //上传图片
    public static void uploadImg(Observer<ImageResponse> observer, MultipartBody.Part image, RequestBody watermark) {
        ApiSubscribe(NetworkManager.getLoginApi().uploadImg(image,watermark), observer);
    }

    //发布日记上传图片

    public static void uploadImg_diary(Observer<ResponseBody> observer, RequestBody requestBody) {
        ApiSubscribe(NetworkManager.getLoginApi().upLoad_diary(requestBody), observer);
    }

    public static void topic(Observer<FoundTopResponse> observer) {
        ApiSubscribe(NetworkManager.getLoginApi().topic(), observer);
    }






}


