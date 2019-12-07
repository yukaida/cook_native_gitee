package com.ebanswers.kitchendiary.mvp.view.base;

import android.app.ActionBar;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;


import com.ebanswers.kitchendiary.R;
import com.gyf.barlibrary.ImmersionBar;
import com.previewlibrary.GPreviewActivity;

/**
 * Create by dongli
 * Create date 2019-11-08
 * desc：
 */
public class ImageLookActivity extends GPreviewActivity {
    /***
     * 重写该方法
     * 使用你的自定义布局
     **/
    @Override
    public int setContentLayout() {
        return R.layout.activity_image_look;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        ImmersionBar.with(this)
                .statusBarDarkFont(true)    //默认状态栏字体颜色为黑色
                .statusBarColor(R.color.black)     //状态栏颜色，不写默认透明色
//                .navigationBarColor(R.color.colorPrimaryDark) //导航栏颜色，不写默认黑色
//                .barColor(R.color.colorAccent)  //同时自定义状态栏和导航栏颜色，不写默认状态栏为透明色，导航栏为黑色
                .keyboardEnable(false, WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN
                        | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

//    @Override
//    public void onWindowFocusChanged(boolean hasFocus) {
//        super.onWindowFocusChanged(hasFocus);
//        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
//            View decorView = getWindow().getDecorView();
//            decorView.setSystemUiVisibility(
//                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
////                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
////                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                            | View.SYSTEM_UI_FLAG_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
//        }
//    }
}
