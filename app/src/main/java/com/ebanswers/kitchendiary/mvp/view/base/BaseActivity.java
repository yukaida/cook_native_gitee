package com.ebanswers.kitchendiary.mvp.view.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.ebanswers.kitchendiary.R;
import com.ebanswers.kitchendiary.utils.NetworkUtils;
import com.ebanswers.kitchendiary.utils.ToastCustom;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;

import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;

/**
 * @author Created by lishihui on 2017/1/9.
 */

public abstract class BaseActivity extends AppCompatActivity {
    protected FragmentManager mFragmentManager;
    public Handler myHandler;
    protected CloseWifiRunnable mRunnable;
    protected BaseActivity context;
    protected static Boolean isExit = false;
    public ViewGroup rootView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStackManager.add(this);
        context = this;
        rootView = (ViewGroup) findViewById(android.R.id.content).getRootView();
        setContentView(getLayoutId());
        QMUIStatusBarHelper.translucent(this);
        QMUIStatusBarHelper.setStatusBarLightMode(this);
        initField();
        ButterKnife.bind(this);
        onCreateNext(savedInstanceState);
        initSwipeBack();
    }

    private void initSwipeBack() {
        QMUIStatusBarHelper.translucent(this);
        QMUIStatusBarHelper.setStatusBarLightMode(this);
    }

    protected void initField() {
        mFragmentManager = getSupportFragmentManager();
        myHandler = new MyHandler(this);
        if (getWifiLostView() != null) {
            mRunnable = new CloseWifiRunnable();
        }
        if (mRunnable != null) {
            checkNet();
        }
    }

    public void checkNet() {
        if (!NetworkUtils.checkNetwork(this)) {
            if (getWifiLostView() != null) {
                getWifiLostView().setVisibility(View.VISIBLE);
                myHandler.removeCallbacksAndMessages(null);
                myHandler.postDelayed(mRunnable, 2000);
            }
        } else {
            if (getWifiLostView().getVisibility() == View.VISIBLE) {
                getWifiLostView().setVisibility(View.GONE);
            }
        }
    }

    protected void windowFeature() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    protected abstract int getLayoutId();

    protected abstract void onCreateNext(@Nullable Bundle savedInstanceState);

    protected abstract View getWifiLostView();


    protected class MyHandler extends Handler {
        private WeakReference<Activity> mActivity;

        public MyHandler(Activity activity) {
            mActivity = new WeakReference<Activity>(activity);
        }

        @Override
        public void handleMessage(Message message) {
            Activity activity = mActivity.get();
            if (activity != null && !activity.isFinishing()) {
                dealHandler(message);
            }
        }
    }

    protected void dealHandler(Message message) {

    }

    protected class CloseWifiRunnable implements Runnable {
        CloseWifiRunnable() {
        }

        @Override
        public void run() {
            if (getWifiLostView() != null && getWifiLostView().getVisibility() == View.VISIBLE) {
                getWifiLostView().setVisibility(View.GONE);
            }
        }
    }

    protected boolean checkInputMethodIsOpen() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        return imm.isActive();
    }

    public void closeInputMethod() {
        if (checkInputMethodIsOpen()) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (getCurrentFocus() != null && getCurrentFocus().getWindowToken() != null) {
                inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    public void showInputMethod(EditText v) {
        Log.d("BaseActivity", "showInputMethod: " + checkInputMethodIsOpen());
        if (!checkInputMethodIsOpen()) {
            if (this.getCurrentFocus() != null && this.getCurrentFocus().getWindowToken() != null) {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.showSoftInput(v, 0);
            }
        }
    }

    public void toggleInputMethod() {
        Log.d("BaseActivity", "toggleInputMethod: " + checkInputMethodIsOpen());
//        if (this.getCurrentFocus() != null && this.getCurrentFocus().getWindowToken() != null) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
//        }
    }


    protected void exitBy2Click() {
        Timer tExit;
        if (!isExit) {
            isExit = true; // 准备退出
            ToastCustom.makeText(this, R.string.tip_exit, Toast.LENGTH_SHORT).show();
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
        } else {
            finish();
            System.exit(0);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("BaseActivity", "onTouchEvent: " + (this.getCurrentFocus() != null));
        if (this.getCurrentFocus() != null) {
            closeInputMethod();
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityStackManager.remove(this);
        closeInputMethod();
    }
}
