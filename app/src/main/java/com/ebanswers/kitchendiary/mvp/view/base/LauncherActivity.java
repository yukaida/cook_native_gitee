package com.ebanswers.kitchendiary.mvp.view.base;

import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.gyf.barlibrary.BarHide;
import com.hjq.permissions.OnPermission;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.hjq.toast.ToastUtils;
import com.ebanswers.kitchendiary.R;
import com.ebanswers.kitchendiary.common.CommonActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * desc   : 启动界面
 */
public class LauncherActivity extends CommonActivity implements OnPermission, Animation.AnimationListener {

    @BindView(R.id.iv_launcher_bg)
    ImageView ivLauncherBg;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_launcher;
    }

    @Override
    protected int getTitleBarId() {
        return 0;
    }

    @Override
    protected void initView() {
        //初始化动画
        initStartAnim();
        //设置状态栏和导航栏参数
        setCheckNetWork(false);
        getStatusBarConfig().fullScreen(true)//有导航栏的情况下，activity全屏显示，也就是activity最下面被导航栏覆盖，不写默认非全屏
                .hideBar(BarHide.FLAG_HIDE_STATUS_BAR)//隐藏状态栏
                .transparentNavigationBar()//透明导航栏，不写默认黑色(设置此方法，fullScreen()方法自动为true)
                .init();

    }

    @Override
    protected void initData() {
    }

    private static final int ANIM_TIME = 1000;

    /**
     * 启动动画
     */
    private void initStartAnim() {
        // 渐变展示启动屏
        AlphaAnimation aa = new AlphaAnimation(1.0f, 1.0f);
        aa.setDuration(ANIM_TIME * 3);
        aa.setAnimationListener(this);
        ivLauncherBg.startAnimation(aa);

    }

    private void requestFilePermission() {
        XXPermissions.with(this).permission(Permission.Group.STORAGE).request(this);
    }

    /**
     * 获取权限后的界面操作
     * {@link OnPermission}
     */

    @Override
    public void hasPermission(List<String> granted, boolean isAll) {
//        if (SPUtils.getIsLogin()){
        startActivity(HomeActivity.class);
//        }else {
//            startActivity(LoginActivity.class);
//        }
        finish();
    }

    @Override
    public void onBackPressed() {
        //禁用返回键
        //super.onBackPressed();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (XXPermissions.isHasPermission(LauncherActivity.this, Permission.Group.STORAGE)) {
            hasPermission(null, true);
        } else {
            requestFilePermission();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public boolean isSupportSwipeBack() {
        //不使用侧滑功能
        return !super.isSupportSwipeBack();
    }

    @Override
    public void noPermission(List<String> denied, boolean quick) {
        if (quick) {
            ToastUtils.show("没有权限访问文件，请手动授予权限");
            XXPermissions.gotoPermissionSettings(LauncherActivity.this, true);
        } else {
            ToastUtils.show("请先授予文件读写权限");
            getWindow().getDecorView().postDelayed(new Runnable() {
                @Override
                public void run() {
                    requestFilePermission();
                }
            }, 2000);
        }
    }

    /**
     * {@link Animation.AnimationListener}
     */

    @Override
    public void onAnimationStart(Animation animation) {
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        requestFilePermission();
    }

    @Override
    public void onAnimationRepeat(Animation animation) {
    }


    @Override
    protected void initOrientation() {
        //Android 8.0踩坑记录：Only fullscreen opaque activities can request orientation
        // https://www.jianshu.com/p/d0d907754603
        //super.initOrientation();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
