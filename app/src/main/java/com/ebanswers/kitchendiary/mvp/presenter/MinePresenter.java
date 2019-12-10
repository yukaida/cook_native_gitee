package com.ebanswers.kitchendiary.mvp.presenter;

import com.ebanswers.kitchendiary.bean.UserInfo;
import com.ebanswers.kitchendiary.mvp.contract.BaseView;
import com.ebanswers.kitchendiary.mvp.view.mine.MineFragment;
import com.ebanswers.kitchendiary.network.api.ApiMethods;
import com.ebanswers.kitchendiary.network.observer.MyObserver;
import com.ebanswers.kitchendiary.network.observer.ObserverOnNextListener;
import com.ebanswers.kitchendiary.network.progress.ProgressObserver;
import com.ebanswers.kitchendiary.network.response.BaseResponse;
import com.ebanswers.kitchendiary.network.response.CookbookResponse;
import com.ebanswers.kitchendiary.network.response.DiaryResponse;

/**
 * Create by dongli
 * Create date 2019-09-23
 * desc：
 */
public class MinePresenter extends BasePresenter<BaseView.MineView, MineFragment>{



    public MinePresenter(BaseView.MineView view, MineFragment activity) {
        super(view, activity);
    }


    public void loadUserInfo(String v, String openid){
        ObserverOnNextListener<UserInfo,Throwable> listener = new ObserverOnNextListener<UserInfo, Throwable>() {
            @Override
            public void onNext(UserInfo baseResponse) {
                getView().setData(baseResponse);
            }

            @Override
            public void onError(Throwable throwable) {
                getView().netWorkError("后台网络异常");
            }
        };

        ApiMethods.mineinfo(new ProgressObserver<UserInfo>(getActivity().getSupportActivity(),listener),v,openid,"json");
    }

    public void loadDiaryInfo( String action, String total,String openid, String types, String is_first,boolean isRefresh){
        ObserverOnNextListener<DiaryResponse,Throwable> listener = new ObserverOnNextListener<DiaryResponse, Throwable>() {
            @Override
            public void onNext(DiaryResponse baseResponse) {
                getView().setDiaryData(baseResponse);
            }

            @Override
            public void onError(Throwable throwable) {
                getView().netWorkError("后台网络异常");
            }
        };
        if (isRefresh){
            ApiMethods.diary(new MyObserver<DiaryResponse>(getActivity().getSupportActivity(),listener),action, total,openid,types,is_first);
        }else {
            ApiMethods.diary(new ProgressObserver<DiaryResponse>(getActivity().getSupportActivity(),listener),action, total,openid,types,is_first);
        }
    }

    public void loadCookbookInfo(String action, String total,String openid, String types, String is_first,boolean isRefresh){
        ObserverOnNextListener<CookbookResponse,Throwable> listener = new ObserverOnNextListener<CookbookResponse, Throwable>() {
            @Override
            public void onNext(CookbookResponse baseResponse) {
                getView().setCookBookData(baseResponse);
            }

            @Override
            public void onError(Throwable throwable) {
                getView().netWorkError("后台网络异常");
            }
        };

        if (isRefresh){
            ApiMethods.cookbook(new MyObserver<CookbookResponse>(getActivity().getSupportActivity(),listener),action, total,openid,types,is_first);
        }else {
            ApiMethods.cookbook(new ProgressObserver<CookbookResponse>(getActivity().getSupportActivity(),listener),action, total,openid,types,is_first);
        }
    }

    //点赞
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


    //
    public void saveName(String name, String openid){

        ObserverOnNextListener<BaseResponse,Throwable> listener = new ObserverOnNextListener<BaseResponse, Throwable>() {
            @Override
            public void onNext(BaseResponse baseResponse) {
                getView().changeName(baseResponse);
            }

            @Override
            public void onError(Throwable throwable) {
                getView().netWorkError("后台网络异常");
            }
        };

        ApiMethods.changeName(new ProgressObserver<BaseResponse>(getActivity().getSupportActivity(),listener),openid, name,"name");
    }

    //
    public void saveHeadUrl(String headUrl, String openid){

        ObserverOnNextListener<BaseResponse,Throwable> listener = new ObserverOnNextListener<BaseResponse, Throwable>() {
            @Override
            public void onNext(BaseResponse baseResponse) {
                getView().changeHeadUrl(baseResponse);
            }

            @Override
            public void onError(Throwable throwable) {
                getView().netWorkError("后台网络异常");
            }
        };

        ApiMethods.changeName(new MyObserver<BaseResponse>(getActivity().getSupportActivity(),listener),openid, headUrl,"head_url");
    }

}
