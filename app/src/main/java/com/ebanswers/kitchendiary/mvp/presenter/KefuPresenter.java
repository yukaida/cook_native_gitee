package com.ebanswers.kitchendiary.mvp.presenter;

import com.ebanswers.kitchendiary.mvp.contract.BaseView;
import com.ebanswers.kitchendiary.mvp.view.mine.KefuActivity;
import com.ebanswers.kitchendiary.network.observer.ObserverOnNextListener;
import com.ebanswers.kitchendiary.network.response.KefuResponse;

/**
 * Create by dongli
 * Create date 2019-09-23
 * desc：
 */
public class KefuPresenter extends BasePresenter<BaseView.KefuView, KefuActivity>{

    public KefuPresenter(BaseView.KefuView view, KefuActivity activity) {
        super(view, activity);
    }

    public void getInfo(String username,String session){
        ObserverOnNextListener<KefuResponse,Throwable> listener = new ObserverOnNextListener<KefuResponse, Throwable>() {
            @Override
            public void onNext(KefuResponse kefuResponse) {



            }

            @Override
            public void onError(Throwable throwable) {
                getView().netWorkError(throwable.getMessage());
            }
        };



    }

}
