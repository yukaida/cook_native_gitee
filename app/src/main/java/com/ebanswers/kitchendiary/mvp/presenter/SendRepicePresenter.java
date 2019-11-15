package com.ebanswers.kitchendiary.mvp.presenter;

import com.ebanswers.kitchendiary.mvp.contract.BaseView;
import com.ebanswers.kitchendiary.mvp.view.base.SendRepiceActivity;
import com.ebanswers.kitchendiary.network.api.ApiMethods;
import com.ebanswers.kitchendiary.network.api.DrufNumResponse;
import com.ebanswers.kitchendiary.network.observer.MyObserver;
import com.ebanswers.kitchendiary.network.observer.ObserverOnNextListener;
import com.ebanswers.kitchendiary.network.progress.ProgressObserver;
import com.ebanswers.kitchendiary.network.response.BaseResponse;
import com.ebanswers.kitchendiary.network.response.ImageResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Create by dongli
 * Create date 2019-09-23
 * desc：
 */
public class SendRepicePresenter extends BasePresenter<BaseView.SendRepiceView, SendRepiceActivity>{



    public SendRepicePresenter(BaseView.SendRepiceView view, SendRepiceActivity activity) {
        super(view, activity);
    }


    public void loadDraftnum(String openid){
        ObserverOnNextListener<DrufNumResponse,Throwable> listener = new ObserverOnNextListener<DrufNumResponse, Throwable>() {
            @Override
            public void onNext(DrufNumResponse baseResponse) {
                getView().setData(baseResponse);
            }

            @Override
            public void onError(Throwable throwable) {
                getView().netWorkError("后台网络异常");
            }
        };

        ApiMethods.draftnum(new ProgressObserver<DrufNumResponse>(getActivity(),listener),openid);
    }


    public void loadCookbook(String action, String allMsgFound){
        ObserverOnNextListener<BaseResponse,Throwable> listener = new ObserverOnNextListener<BaseResponse, Throwable>() {
            @Override
            public void onNext(BaseResponse baseResponse) {
                getView().saveOrSendData(baseResponse);
            }

            @Override
            public void onError(Throwable throwable) {
                getView().netWorkError("后台网络异常");
            }
        };

        ApiMethods.sendcookbook(new ProgressObserver<BaseResponse>(getActivity(),listener),allMsgFound,action);
    }

    public void uploadImg(MultipartBody.Part allMsgFound, RequestBody body){
        ObserverOnNextListener<ImageResponse,Throwable> listener = new ObserverOnNextListener<ImageResponse, Throwable>() {
            @Override
            public void onNext(ImageResponse imageResponse) {
                getView().savePic(imageResponse);
            }

            @Override
            public void onError(Throwable throwable) {
                getView().netWorkError("后台网络异常");
            }
        };

        ApiMethods.uploadImg(new MyObserver<ImageResponse>(getActivity(),listener),allMsgFound,body);
    }


}
