package com.ebanswers.kitchendiary.mvp.presenter;

import com.ebanswers.kitchendiary.mvp.contract.BaseView;
import com.ebanswers.kitchendiary.mvp.view.base.LoginActivity;
import com.ebanswers.kitchendiary.network.api.ApiMethods;
import com.ebanswers.kitchendiary.network.observer.ObserverOnNextListener;
import com.ebanswers.kitchendiary.network.progress.ProgressObserver;
import com.ebanswers.kitchendiary.network.response.LoginResponse;

/**
 * Create by dongli
 * Create date 2019-09-22
 * desc：
 */
public class LoginPresenter extends BasePresenter<BaseView.LoginView, LoginActivity> {

    public LoginPresenter(BaseView.LoginView view, LoginActivity activity) {
        super(view, activity);
    }

    public void login(String username, String password, boolean remenberPass){
        ObserverOnNextListener<LoginResponse,Throwable> listener = new ObserverOnNextListener<LoginResponse, Throwable>() {
            @Override
            public void onNext(LoginResponse loginResponse) {

            }

            @Override
            public void onError(Throwable throwable) {
                getView().netWorkError("后台网络异常");
            }
        };

        ApiMethods.login(new ProgressObserver<>(getActivity(),listener),username,password);
    }
}
