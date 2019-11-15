package com.ebanswers.kitchendiary.common;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.ebanswers.baselibrary.utils.KeyboardUtils;
import com.ebanswers.kitchendiary.listener.LifeCycleListener;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

/**
 *    desc   : Activity基类
 */
public abstract class BaseActivity extends RxAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getLayoutId() > 0) {
            setContentView(getLayoutId());
        }
        if (mListener != null) {
            mListener.onCreate(savedInstanceState);
        }
        init();
    }

    public void init(){
        initView();
        initData();
    }

    //引入布局
    protected abstract int getLayoutId();

    //标题栏id
    protected abstract int getTitleBarId();

    //初始化控件
    protected abstract void initView();

    //初始化数据
    protected abstract void initData();

    @Override
    public void finish() {
        // 隐藏软键盘，避免软键盘引发的内存泄露
        KeyboardUtils.hideKeyboard(getCurrentFocus());
        super.finish();
    }

    /**
     * 跳转到其他Activity
     *
     * @param cls       目标Activity的Class
     */
    public void startActivity(Class<? extends Activity> cls) {
        startActivity(new Intent(this, cls));
    }

    /**
     * 延迟执行某个任务
     *
     * @param action          Runnable对象
     */
    public boolean post(Runnable action) {
        return getWindow().getDecorView().post(action);
    }

    /**
     * 延迟某个时间执行某个任务
     *
     * @param action        Runnable对象
     * @param delayMillis   延迟的时间
     */
    public boolean postDelayed(Runnable action, long delayMillis) {
        return getWindow().getDecorView().postDelayed(action, delayMillis);
    }

    /**
     * 删除某个延迟任务
     * @param action        Runnable对象
     */
    public boolean removeCallbacks(Runnable action) {
        if(getWindow().getDecorView() != null) {
            return getWindow().getDecorView().removeCallbacks(action);
        }else {
            return true;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mListener != null) {
            mListener.onStart();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (mListener != null) {
            mListener.onRestart();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mListener != null) {
            mListener.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mListener != null) {
            mListener.onPause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mListener != null) {
            mListener.onStop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mListener != null) {
            mListener.onDestroy();
        }
    }

    /**
     * 回调函数
     */
    public LifeCycleListener mListener;

    public void setOnLifeCycleListener(LifeCycleListener listener) {
        mListener = listener;
    }
}