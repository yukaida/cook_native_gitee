package com.ebanswers.kitchendiary.network.progress;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.ebanswers.kitchendiary.network.observer.ObserverOnNextListener;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 带进度条的事件订阅  监听
 * Created by DeMon on 2017/9/6.
 */
public class ProgressObserver<T> implements Observer<T>, ProgressCancelListener {

    private static final String TAG = "ProgressObserver";
    private ObserverOnNextListener listener;
    private ProgressDialogHandler mProgressDialogHandler;
    private DialogCircleProgress.Builder builder;
    private Activity context;
    private Disposable d;

    private String mTag;//请求标识

    /*public ProgressObserver(String tag, Activity context, ObserverOnNextListener listener) {
        this.mTag = tag;
        this.listener = listener;
        this.context = context;
        initProgressDialog(context);
//        mProgressDialogHandler = new ProgressDialogHandler(context, this, true);
    }
*/
    public ProgressObserver(Activity context, ObserverOnNextListener listener) {
        this.listener = listener;
        this.context = context;
        initProgressDialog(context);
//        mProgressDialogHandler = new ProgressDialogHandler(context, this, true);
    }

    private void showProgressDialog() {
//        if (mProgressDialogHandler != null) {
//            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG).sendToTarget();
//        }

        if (builder != null){

        }
    }

    private void dismissProgressDialog() {
//        if (mProgressDialogHandler != null) {
//            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG)
//                    .sendToTarget();
//            mProgressDialogHandler = null;
//        }
        if (builder != null) {
            builder.dismiss();
            builder = null;
        }
    }

    @Override    //建立订阅模式
    public void onSubscribe(Disposable d) {
        this.d = d;
        Log.d(TAG, "请求开始");
        showProgressDialog();
      /*  if (!TextUtils.isEmpty(mTag)) {
            RxActionManagerImpl.getInstance().add(mTag, d);
        }*/

    }

    @Override    //处理返回事件
    public void onNext(T t) {
        listener.onNext(t);
       /* if (!TextUtils.isEmpty(mTag)) {
            RxActionManagerImpl.getInstance().remove(mTag);
        }*/
    }

    @Override   //处理异常错误
    public void onError(Throwable e) {
        dismissProgressDialog();
        listener.onError(e);
       /* RxActionManagerImpl.getInstance().remove(mTag);
        if (e instanceof ApiException) {
            onError((ApiException) e);
        } else {
            onError(new ApiException(e, ExceptionEngine.UN_KNOWN_ERROR));
        }*/
        Log.e(TAG, "onError: ", e);
    }

    @Override       //处理完成请求
    public void onComplete() {
        dismissProgressDialog();
        Log.d(TAG, "onComplete: 完成");


    }

    @Override
    public void onCancelProgress() {
        //如果处于订阅状态，则取消订阅
        if (!d.isDisposed()) {
            d.dispose();
            dismissProgressDialog();
        }
/*
        if (!TextUtils.isEmpty(mTag)) {
            RxActionManagerImpl.getInstance().cancel(mTag);
        }*/
    }

    private void initProgressDialog(Context context) {
        if (builder == null) {
            builder = new DialogCircleProgress.Builder(context);
//            pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);// 设置进度条的形式为圆形转动的进度条
            builder.setCancelable(true);
            builder.setDialogSize(120,context);
            if (true) {
                builder.setListener(new DialogCircleProgress.Builder.CircleProgressListener() {
                    @Override
                    public void onCancel() {

                    }
                });
            }
            builder.create().show();
            if (!builder.isShowing()) {
                builder.dismiss();
            }
        }
    }

}
