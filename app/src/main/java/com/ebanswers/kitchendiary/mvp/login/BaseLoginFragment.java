package com.ebanswers.kitchendiary.mvp.login;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.ebanswers.kitchendiary.mvp.view.base.BaseActivity;
import com.ebanswers.kitchendiary.utils.LanguageUtil;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * @author
 * Created by lishihui on 2017/1/9.
 */

public abstract class BaseLoginFragment extends Fragment {

    protected View contentView;
    protected Context mContext;
    private QMUITipDialog dialog;
    private Subscription delayClose;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        contentView = inflater.inflate(getLayoutId(),container,false);
        ButterKnife.bind(this,contentView);
        onCreateViewNext(savedInstanceState);
        return contentView;
    }

    protected abstract int getLayoutId();

    protected abstract void onCreateViewNext(@Nullable Bundle savedInstanceState);

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext =  context;
    }

    public void showWaitDialog(int resId) {
        closeWaitLoading();
        QMUITipDialog.Builder builder = new QMUITipDialog.Builder(getActivity());
        builder.setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING);
        builder.setTipWord(LanguageUtil.getInstance().getStringById(resId));
        dialog = builder.create();
        dialog.show();
        delayCloseLoading();
    }

    public void closeWaitLoading() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }
        if (delayClose != null && !delayClose.isUnsubscribed()) {
            delayClose.unsubscribe();
        }
    }

    //请求中突然断网, 延时关闭
    public void delayCloseLoading() {
        if (delayClose != null && !delayClose.isUnsubscribed()) {
            delayClose.unsubscribe();
        }
        delayClose = Observable.timer(15, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        closeWaitLoading();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
    }

    protected void closeInputMethod(){
        if (mContext != null) {
            if (mContext instanceof BaseActivity) {
                BaseActivity baseActivity = (BaseActivity) mContext;
                baseActivity.closeInputMethod();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        closeWaitLoading();

    }

}
