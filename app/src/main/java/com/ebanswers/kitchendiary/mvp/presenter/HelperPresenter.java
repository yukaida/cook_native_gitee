package com.ebanswers.kitchendiary.mvp.presenter;

import com.ebanswers.kitchendiary.bean.SquareInfo;
import com.ebanswers.kitchendiary.mvp.contract.BaseView;
import com.ebanswers.kitchendiary.mvp.view.helper.HelperFragment;
import com.ebanswers.kitchendiary.network.api.ApiMethods;
import com.ebanswers.kitchendiary.network.observer.MyObserver;
import com.ebanswers.kitchendiary.network.observer.ObserverOnNextListener;
import com.ebanswers.kitchendiary.network.progress.ProgressObserver;
import com.ebanswers.kitchendiary.network.response.BaseResponse;

/**
 * Create by dongli
 * Create date 2019-09-23
 * desc：
 */
public class HelperPresenter extends BasePresenter<BaseView.HelperView, HelperFragment>{



    public HelperPresenter(BaseView.HelperView view, HelperFragment activity) {
        super(view, activity);
    }


    public void loadSquareInfo(String openid, String totle,boolean isRefresh,String diary_id){
        ObserverOnNextListener<SquareInfo,Throwable> listener = new ObserverOnNextListener<SquareInfo, Throwable>() {
            @Override
            public void onNext(SquareInfo squareInfo) {
                getView().setMoreData(squareInfo);
            }

            @Override
            public void onError(Throwable throwable) {
                getView().netWorkError("后台网络异常");
            }
        };

        if (isRefresh){
            ApiMethods.help(new MyObserver<SquareInfo>(getActivity().getSupportActivity(),listener),"more",totle,openid,"square","");
        }else {
            ApiMethods.help(new ProgressObserver<SquareInfo>(getActivity().getSupportActivity(),listener),"more",totle,openid,"square",diary_id);
        }
    }


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



}
