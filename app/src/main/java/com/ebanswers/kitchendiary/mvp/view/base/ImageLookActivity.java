package com.ebanswers.kitchendiary.mvp.view.base;

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
    protected void onStart() {
        super.onStart();
        ImmersionBar.with(this)
                .statusBarDarkFont(true)    //默认状态栏字体颜色为黑色
                .statusBarColor(R.color.white)     //状态栏颜色，不写默认透明色
//                .navigationBarColor(R.color.colorPrimaryDark) //导航栏颜色，不写默认黑色
//                .barColor(R.color.colorAccent)  //同时自定义状态栏和导航栏颜色，不写默认状态栏为透明色，导航栏为黑色
                .keyboardEnable(false, WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN
                        | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }
}
