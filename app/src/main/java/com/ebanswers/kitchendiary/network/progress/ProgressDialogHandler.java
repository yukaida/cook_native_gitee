package com.ebanswers.kitchendiary.network.progress;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

/**
 * Created by DeMon on 2017/9/6.
 */

public class ProgressDialogHandler extends Handler {
    public static final int SHOW_PROGRESS_DIALOG = 1;   //显示进度条
    public static final int DISMISS_PROGRESS_DIALOG = 2;  //隐藏进度条

    private DialogCircleProgress.Builder pd;
    private String text;
    private Context context;
    private boolean cancelable;
    private ProgressCancelListener mProgressCancelListener;

    public ProgressDialogHandler(Context context, ProgressCancelListener mProgressCancelListener, boolean cancelable) {
        this.context = context;
        this.mProgressCancelListener = mProgressCancelListener;
        this.cancelable = cancelable;
    }

    public ProgressDialogHandler(Context context, ProgressCancelListener mProgressCancelListener, boolean cancelable, String text) {
        this.context = context;
        this.mProgressCancelListener = mProgressCancelListener;
        this.cancelable = cancelable;
        this.text = text;
    }

    private void initProgressDialog(Context context) {
        if (pd == null) {
            pd = new DialogCircleProgress.Builder(context);
//            pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);// 设置进度条的形式为圆形转动的进度条
            pd.setCancelable(cancelable);

            pd.setDialogSize(120,context);
            if (cancelable) {
                pd.setListener(new DialogCircleProgress.Builder.CircleProgressListener() {
                    @Override
                    public void onCancel() {
                        mProgressCancelListener.onCancelProgress();
                    }
                });
            }
            pd.create().show();
            if (!pd.isShowing()) {
                pd.dismiss();
            }
        }
    }

    private void dismissProgressDialog() {
        if (pd != null) {
            pd.dismiss();
            pd = null;
        }
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case SHOW_PROGRESS_DIALOG:
                initProgressDialog(context);
                break;
            case DISMISS_PROGRESS_DIALOG:
                dismissProgressDialog();
                break;
        }
    }
}
