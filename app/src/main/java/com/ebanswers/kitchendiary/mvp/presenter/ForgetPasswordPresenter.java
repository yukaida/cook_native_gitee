package com.ebanswers.kitchendiary.mvp.presenter;

import com.ebanswers.kitchendiary.mvp.contract.BaseView;
import com.ebanswers.kitchendiary.mvp.view.base.ForgetPassActivity;
import com.ebanswers.kitchendiary.network.observer.ObserverOnNextListener;
import com.ebanswers.kitchendiary.network.response.BaseResponse;

/**
 * Create by dongli
 * Create date 2019-09-24
 * desc：
 */
public class ForgetPasswordPresenter extends BasePresenter<BaseView.ForgetPassView, ForgetPassActivity> {

    public ForgetPasswordPresenter(BaseView.ForgetPassView view, ForgetPassActivity activity) {
        super(view, activity);
    }

    public void modify(String userName, String code, String password, String confirmpassword){

        ObserverOnNextListener<BaseResponse,Throwable> listener = new ObserverOnNextListener<BaseResponse, Throwable>() {
            @Override
            public void onNext(BaseResponse baseResponse) {


            }

            @Override
            public void onError(Throwable throwable) {
                getView().netWorkError("后台网络异常");
            }
        };

//        ApiMethods.moddifyPwd(new ProgressObserver<BaseResponse>(getActivity(),listener),userName,code,password,confirmpassword);


    }

    public void sendCode(String userName){

        ObserverOnNextListener<BaseResponse,Throwable> listener = new ObserverOnNextListener<BaseResponse, Throwable>() {
            @Override
            public void onNext(BaseResponse baseResponse) {

            }

            @Override
            public void onError(Throwable throwable) {
                getView().netWorkError("后台网络异常");
            }
        };

//        ApiMethods.sendCode(new ProgressObserver<BaseResponse>(getActivity(),listener),userName);


    }



}
