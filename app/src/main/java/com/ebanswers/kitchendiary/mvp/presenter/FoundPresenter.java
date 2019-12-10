package com.ebanswers.kitchendiary.mvp.presenter;

import android.util.Log;

import com.ebanswers.kitchendiary.bean.FoundHomeInfo;
import com.ebanswers.kitchendiary.bean.FoundLoadMoreInfo;
import com.ebanswers.kitchendiary.mvp.contract.BaseView;
import com.ebanswers.kitchendiary.mvp.view.found.FoundFragmentSub;
import com.ebanswers.kitchendiary.network.api.ApiMethods;
import com.ebanswers.kitchendiary.network.observer.MyObserver;
import com.ebanswers.kitchendiary.network.observer.ObserverOnNextListener;
import com.ebanswers.kitchendiary.network.progress.ProgressObserver;
import com.ebanswers.kitchendiary.network.response.BaseResponse;
import com.ebanswers.kitchendiary.network.response.FoundTopResponse;

/**
 * Create by dongli
 * Create date 2019-09-23
 * desc：
 */
public class FoundPresenter extends BasePresenter<BaseView.FoundView, FoundFragmentSub>{



    public FoundPresenter(BaseView.FoundView view, FoundFragmentSub activity) {
        super(view, activity);
    }


    public void loadInfo(String openid,boolean isRefresh){
        ObserverOnNextListener<FoundHomeInfo,Throwable> listener = new ObserverOnNextListener<FoundHomeInfo, Throwable>() {
            @Override
            public void onNext(FoundHomeInfo foundHomeInfo) {
                getView().setData(foundHomeInfo);
            }

            @Override
            public void onError(Throwable throwable) {
                getView().netWorkError("后台网络异常");
            }
        };
        if (isRefresh){
            ApiMethods.foundinfo(new MyObserver<FoundHomeInfo>(getActivity().getSupportActivity(),listener),"123","json",openid );
        }else {
        ApiMethods.foundinfo(new ProgressObserver<FoundHomeInfo>(getActivity().getSupportActivity(),listener),"123","json",openid );
        }
    }


    //关注
    public void follower(String action,String openid,String followerId){
        ObserverOnNextListener<BaseResponse,Throwable> listener = new ObserverOnNextListener<BaseResponse, Throwable>() {
            @Override
            public void onNext(BaseResponse baseResponse) {
                getView().setFollowerData(baseResponse);
            }

            @Override
            public void onError(Throwable throwable) {
                getView().netWorkError("后台网络异常");
            }
        };

        ApiMethods.follower(new MyObserver<BaseResponse>(getActivity().getSupportActivity(),listener),action,followerId,openid);
    }


     public void foundinfoLoadMore(String action, String total, String openid, String types,
                                   String diary_id,String ctg, String scope, String lang,boolean isRefresh){
        ObserverOnNextListener<FoundLoadMoreInfo,Throwable> listener = new ObserverOnNextListener<FoundLoadMoreInfo, Throwable>() {
            @Override
            public void onNext(FoundLoadMoreInfo foundLoadMoreInfo) {
                getView().setMoreData(foundLoadMoreInfo);
            }

            @Override
            public void onError(Throwable throwable) {
                getView().netWorkError("后台网络异常");
            }
        };

        if (isRefresh) {
            ApiMethods.foundinfoLoadMore(new MyObserver<FoundLoadMoreInfo>(getActivity().getSupportActivity(), listener), action, total, openid, types, diary_id, ctg, scope, lang);
        }else {
            ApiMethods.foundinfoLoadMore(new ProgressObserver<FoundLoadMoreInfo>(getActivity().getSupportActivity(), listener), action, total, openid, types, diary_id, ctg, scope, lang);
        }
    }




    //收藏
    public void islike(String action, String diary_id, String nickname){

        ObserverOnNextListener<BaseResponse,Throwable> listener = new ObserverOnNextListener<BaseResponse, Throwable>() {
            @Override
            public void onNext(BaseResponse baseResponse) {
                getView().setFollowerData(baseResponse);
            }

            @Override
            public void onError(Throwable throwable) {
                getView().netWorkError("后台网络异常");
            }
        };

        ApiMethods.islike(new MyObserver<BaseResponse>(getActivity().getSupportActivity(),listener),action, diary_id,nickname);
    }

    //收藏
    public void collected(String action, String diary_id, String diary_openid){
        ObserverOnNextListener<BaseResponse,Throwable> listener = new ObserverOnNextListener<BaseResponse, Throwable>() {
            @Override
            public void onNext(BaseResponse baseResponse) {
                getView().setFollowerData(baseResponse);
            }

            @Override
            public void onError(Throwable throwable) {
                getView().netWorkError("后台网络异常");
            }
        };

        ApiMethods.collect(new MyObserver<BaseResponse>(getActivity().getSupportActivity(),listener),action, diary_id,diary_openid);
    }


    //取消收藏
    public void uncollected(String action, String diary_id){

        ObserverOnNextListener<BaseResponse,Throwable> listener = new ObserverOnNextListener<BaseResponse, Throwable>() {
            @Override
            public void onNext(BaseResponse baseResponse) {
                getView().setFollowerData(baseResponse);
            }

            @Override
            public void onError(Throwable throwable) {
                getView().netWorkError("后台网络异常");
            }
        };

        ApiMethods.uncollect(new MyObserver<BaseResponse>(getActivity().getSupportActivity(),listener),action, diary_id);
    }


    //评论
    public void comment(String action, String diary_id, String comment,
                        String to_openid, String to_user){

        ObserverOnNextListener<BaseResponse,Throwable> listener = new ObserverOnNextListener<BaseResponse, Throwable>() {
            @Override
            public void onNext(BaseResponse baseResponse) {
                getView().setFollowerData(baseResponse);
            }

            @Override
            public void onError(Throwable throwable) {
                getView().netWorkError("后台网络异常");
            }
        };

        ApiMethods.comment(new MyObserver<BaseResponse>(getActivity().getSupportActivity(),listener),action, diary_id,comment,to_openid, to_user);
    }


    //评论
    public void commentReply(String action, String diary_id, String comment,
                        String to_openid, String to_user,String from_openid,String from_user){

        ObserverOnNextListener<BaseResponse,Throwable> listener = new ObserverOnNextListener<BaseResponse, Throwable>() {
            @Override
            public void onNext(BaseResponse baseResponse) {
                getView().setFollowerData(baseResponse);
            }

            @Override
            public void onError(Throwable throwable) {
                getView().netWorkError("后台网络异常");
            }
        };

        ApiMethods.commentReply(new MyObserver<BaseResponse>(getActivity().getSupportActivity(),listener),action, diary_id,comment,to_openid, to_user,from_openid,from_user);
    }




    //评论删除
    public void commentDelete(String action, String diary_id, String comment,
                        String from_openid, String to_user){

        ObserverOnNextListener<BaseResponse,Throwable> listener = new ObserverOnNextListener<BaseResponse, Throwable>() {
            @Override
            public void onNext(BaseResponse baseResponse) {
                getView().setFollowerData(baseResponse);
            }

            @Override
            public void onError(Throwable throwable) {
                getView().netWorkError("后台网络异常");
            }
        };

        ApiMethods.commentDelete(new MyObserver<BaseResponse>(getActivity().getSupportActivity(),listener),action, diary_id,comment,from_openid, to_user);
    }

    public void topic(){
        ObserverOnNextListener<FoundTopResponse,Throwable> listener = new ObserverOnNextListener<FoundTopResponse, Throwable>() {
            @Override
            public void onNext(FoundTopResponse baseResponse) {
                getView().topData(baseResponse);
            }

            @Override
            public void onError(Throwable throwable) {
                getView().netWorkError("后台网络异常");
            }
        };

        ApiMethods.topic(new MyObserver<FoundTopResponse>(getActivity().getSupportActivity(),listener));
    }
}
