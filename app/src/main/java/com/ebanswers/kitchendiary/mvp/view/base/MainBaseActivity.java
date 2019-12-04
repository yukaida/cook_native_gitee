package com.ebanswers.kitchendiary.mvp.view.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import com.ebanswers.kitchendiary.R;
import com.ebanswers.kitchendiary.utils.NetworkUtils;
import com.ebanswers.kitchendiary.utils.ToastCustom;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;

import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by air
 *
 * @link https://github.com/LiShiHui24740
 * On 2018/4/10 上午9:44.
 * @description:
 */

public abstract  class MainBaseActivity extends BaseActivity {
    protected FragmentManager mFragmentManager;
    public Handler myHandler;
    protected CloseWifiRunnable mRunnable;
    protected MainBaseActivity context;
    protected static Boolean isExit = false;
    public ViewGroup rootView;
    protected Unbinder mUnbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        rootView = (ViewGroup) findViewById(android.R.id.content).getRootView();
        QMUIStatusBarHelper.translucent(this);
        QMUIStatusBarHelper.setStatusBarLightMode(this);
        if (getLayoutId() > 0) {
            setContentView(getLayoutId());
        }
        initField();
        mUnbinder = ButterKnife.bind(this);
        onCreateNext(savedInstanceState);
        ActivityStackManager.add(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    protected void initField() {
        if (mFragmentManager == null)
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
                myHandler.removeCallbacks(mRunnable);
                myHandler.postDelayed(mRunnable, 2000);
            }
        } else {
            if (getWifiLostView() != null && getWifiLostView().getVisibility() == View.VISIBLE) {
                getWifiLostView().setVisibility(View.GONE);
            }
        }
    }

    protected abstract int getLayoutId();

    protected abstract void onCreateNext(@Nullable Bundle savedInstanceState);

    protected abstract View getWifiLostView();

    protected class MyHandler extends Handler {
        private WeakReference<Activity> mActivity;

        public MyHandler(Activity activity) {
            mActivity = new WeakReference<>(activity);
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
            if (this.getCurrentFocus() != null && this.getCurrentFocus().getWindowToken() != null) {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
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
    protected void onDestroy() {
        ActivityStackManager.remove(this);
        if (myHandler != null) {
            myHandler.removeCallbacksAndMessages(null);
            myHandler = null;
        }
        if (mUnbinder != null)
            mUnbinder.unbind();
        super.onDestroy();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);

    }
}
