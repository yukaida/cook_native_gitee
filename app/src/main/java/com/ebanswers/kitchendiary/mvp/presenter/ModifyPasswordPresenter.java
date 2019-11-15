package com.ebanswers.kitchendiary.mvp.presenter;

import com.ebanswers.kitchendiary.mvp.contract.BaseView;
import com.ebanswers.kitchendiary.mvp.view.mine.ModifyPasswordActivity;
import com.ebanswers.kitchendiary.network.observer.ObserverOnNextListener;
import com.ebanswers.kitchendiary.network.response.BaseResponse;

/**
 * Create by dongli
 * Create date 2019-09-24
 * desc：
 */
public class ModifyPasswordPresenter extends BasePresenter<BaseView.ModifyPasswordView, ModifyPasswordActivity> {


    public ModifyPasswordPresenter(BaseView.ModifyPasswordView view, ModifyPasswordActivity activity) {
        super(view, activity);
    }

    public void modify(String userName, String session, String password, String confirmpassword){

        ObserverOnNextListener<BaseResponse,Throwable> listener = new ObserverOnNextListener<BaseResponse, Throwable>() {
            @Override
            public void onNext(BaseResponse baseResponse) {


            }

            @Override
            public void onError(Throwable throwable) {
                getView().netWorkError("后台网络异常");
            }
        };



    }

}
