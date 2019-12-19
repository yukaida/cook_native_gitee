package com.ebanswers.kitchendiary.mvp.openjs;

import android.util.Log;
import android.view.View;

import com.ebanswers.kitchendiary.R;
import com.ebanswers.kitchendiary.utils.ToastCustom;
import com.google.android.material.snackbar.Snackbar;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.lang.ref.WeakReference;

/**
 * @author
 * Created by lishihui on 2017/1/23.
 */

public class ShareListener implements UMShareListener {
    private WeakReference<View> view;
    private OnShareListener mOnShareListener;
    public ShareListener(WeakReference<View> view) {
        this.view = view;
    }

    @Override
    public void onStart(SHARE_MEDIA share_media) {

    }

    @Override
    public void onResult(SHARE_MEDIA platform) {
        Log.d("ShareListener", "onResult: 分享成功");
        if (view!=null&&view.get()!= null){
            ToastCustom.makeText(view.get().getContext(), R.string.send_success, Snackbar.LENGTH_SHORT).show();
        }

        if (mOnShareListener!=null){
            mOnShareListener.onResult(platform);
        }
    }

    @Override
    public void onError(SHARE_MEDIA platform, Throwable t) {
        Log.d("ShareListener", "onError: 分享失败");
        t.printStackTrace();
        if (view!=null&&view.get()!= null){
            ToastCustom.makeText(R.string.send_fail).show();
        }
        if (mOnShareListener!=null){
            mOnShareListener.onError(platform,t);
        }
    }

    @Override
    public void onCancel(SHARE_MEDIA platform) {
        Log.d("ShareListener", "onCancel: ");
        if (mOnShareListener!=null){
            mOnShareListener.onCancel(platform);
        }
    }

    public void setOnShareListener(OnShareListener mOnShareListener) {
        this.mOnShareListener = mOnShareListener;
    }

    public interface OnShareListener{
        void onResult(SHARE_MEDIA platform);
        void onError(SHARE_MEDIA platform, Throwable t);
        void onCancel(SHARE_MEDIA platform);
    }
}
