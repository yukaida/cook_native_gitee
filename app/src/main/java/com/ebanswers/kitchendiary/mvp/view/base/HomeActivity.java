package com.ebanswers.kitchendiary.mvp.view.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.viewpager.widget.ViewPager;

import com.ebanswers.baselibrary.utils.OnClickUtils;
import com.ebanswers.baselibrary.utils.ScreenUtils;
import com.ebanswers.kitchendiary.R;
import com.ebanswers.kitchendiary.adapter.HomeViewPagerAdapter;
import com.ebanswers.kitchendiary.common.CommonActivity;
import com.ebanswers.kitchendiary.constant.AppConstant;
import com.ebanswers.kitchendiary.eventbus.Event;
import com.ebanswers.kitchendiary.eventbus.ShowSearchEvent;
import com.ebanswers.kitchendiary.mvp.view.found.FoundFragment;
import com.ebanswers.kitchendiary.mvp.view.helper.HelperFragment;
import com.ebanswers.kitchendiary.mvp.view.home.HomeFragment;
import com.ebanswers.kitchendiary.mvp.view.mine.MineFragment;
import com.ebanswers.kitchendiary.network.api.ApiMethods;
import com.ebanswers.kitchendiary.network.observer.MyObserver;
import com.ebanswers.kitchendiary.network.observer.ObserverOnNextListener;
import com.ebanswers.kitchendiary.network.response.MessageResponse;
import com.ebanswers.kitchendiary.service.CreateRepiceDraftService;
import com.ebanswers.kitchendiary.service.CreateRepiceService;
import com.ebanswers.kitchendiary.service.UpdateService;
import com.ebanswers.kitchendiary.utils.AppUtils;
import com.ebanswers.kitchendiary.utils.ButtonUtils;
import com.ebanswers.kitchendiary.utils.LogUtils;
import com.ebanswers.kitchendiary.utils.SPUtils;
import com.ebanswers.kitchendiary.widget.dialog.DialogBackTip;
import com.ebanswers.kitchendiary.widget.popupwindow.CustomPopWindow;
import com.ebanswers.kitchendiary.widget.viewpager_animator.ZoomOutPageTransformer;
import com.gyf.barlibrary.ImmersionBar;
import com.hjq.toast.ToastUtils;
import com.umeng.socialize.UMShareAPI;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * desc   : 主页界面
 */
public class HomeActivity extends CommonActivity implements ViewPager.OnPageChangeListener, ScreenUtils {

    @BindView(R.id.vp_home_pager)
    ViewPager mViewPager;

    public static boolean isForeground = false;
    @BindView(R.id.home_image)
    ImageView homeImage;
    @BindView(R.id.home_tv)
    TextView homeTv;
    @BindView(R.id.home_ll)
    LinearLayout homeLl;
    @BindView(R.id.helper_image)
    ImageView helperImage;
    @BindView(R.id.helper_tv)
    TextView helperTv;
    @BindView(R.id.helper_ll)
    LinearLayout helperLl;
    @BindView(R.id.tab_center_image)
    ImageView tabCenterImage;
    @BindView(R.id.tab_center_ll)
    LinearLayout tabCenterLl;
    @BindView(R.id.found_image)
    ImageView foundImage;
    @BindView(R.id.found_tv)
    TextView foundTv;
    @BindView(R.id.found_ll)
    LinearLayout foundLl;
    @BindView(R.id.me_image)
    ImageView meImage;
    @BindView(R.id.me_tv)
    TextView meTv;
    @BindView(R.id.me_ll)
    LinearLayout meLl;
    @BindView(R.id.current_date_tv)
    TextView currentDateTv;
    @BindView(R.id.message_num_tv)
    TextView messageNumTv;
    @BindView(R.id.bottom_ll)
    LinearLayout bottomLl;

    private OrientationEventListener mOrientationListener;
    // screen orientation listener
    private boolean mScreenProtrait = true;
    private boolean mCurrentOrient = false;

    private HomeViewPagerAdapter mAdapter;
    private String TAG = "HomeActivity";
    private int msg_num;
    private CustomPopWindow.PopupWindowBuilder popupWindowBuilder;
    private CustomPopWindow customPopWindow;

    private Timer timer;
    private TimerTask task;

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                loadMessageInfo();
            }
        }
    };
    private DialogBackTip.Builder builder;
    private DialogBackTip.Builder builder2;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected int getTitleBarId() {
        return 0;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);

//        popupSendRepiceWindow(tabCenterLl);

        if (TextUtils.isEmpty((String) SPUtils.get(AppConstant.USER_NAME, ""))) {
            SPUtils.put(AppConstant.USER_NAME, "厨房客人");
        }

        String open_id = getIntent().getStringExtra("open_id");
        if (!TextUtils.isEmpty(open_id)) {
            SPUtils.put(AppConstant.USER_ID, open_id);
        } else {
            if (TextUtils.isEmpty((String) SPUtils.get(AppConstant.USER_ID, ""))) {
                SPUtils.put(AppConstant.USER_ID, "tmp_user");
            }
        }

        if (!TextUtils.isEmpty((String) SPUtils.get("type", "")) &&
                ((String) SPUtils.get("type", "")).equals("draft")) {
            Intent i = new Intent(this, CreateRepiceDraftService.class);
            startService(i);
        } else {
            Intent i = new Intent(this, CreateRepiceService.class);
            startService(i);
        }

        mViewPager.addOnPageChangeListener(this);
        currentDateTv.setText(Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + "");
        // 修复在 ViewPager 中点击 EditText 弹出软键盘导致 BottomNavigationView 还显示在 ViewPager 下面的问题
//        postDelayed(this, 1000);

        mAdapter = new HomeViewPagerAdapter(this);
        mViewPager.setAdapter(mAdapter);
//        startOrientationChangeListener();
        mViewPager.setPageTransformer(false, new ZoomOutPageTransformer());
        // 限制页面数量
        mViewPager.setOffscreenPageLimit(mAdapter.getCount());
//        mViewPager.setOffscreenPageLimit(2);

        if (!TextUtils.isEmpty(getIntent().getStringExtra("position")) && getIntent().getStringExtra("position").equals("3")) {
            mViewPager.setCurrentItem(3);
            clearStatus();
            selectIndex(3);
        }
        /*
         * Generate a new EC key pair entry in the Android Keystore by
         * using the KeyPairGenerator API. The private key can only be
         * used for signing or verification and only with SHA-256 or
         * SHA-512 as the message digest.
         */
        startTask();


    }

    @Override
    protected void initData() {
        loadMessageInfo();
    }

    private void loadMessageInfo() {
        ObserverOnNextListener<MessageResponse, Throwable> listener = new ObserverOnNextListener<MessageResponse, Throwable>() {
            @Override
            public void onNext(MessageResponse messageResponse) {
                msg_num = messageResponse.getMsg_num();
                SPUtils.put("msg_num", msg_num);
                int currentItem = 0;
                if (mViewPager != null) {
                    currentItem = mViewPager.getCurrentItem();
                }


                if (msg_num > 0) {
                    if (currentItem != 2) {
                        messageNumTv.setVisibility(View.VISIBLE);
                        if (msg_num < 100) {
                            messageNumTv.setText(msg_num + "");
                        } else {
                            messageNumTv.setText("···");
                        }
                    } else {
                        messageNumTv.setVisibility(View.GONE);
                    }
                    FoundFragment item = (FoundFragment) mAdapter.getItem(2);
                    if (item != null) {
                        item.onStart();
                    }


                } else {
                    messageNumTv.setVisibility(View.GONE);
                }


            }

            @Override
            public void onError(Throwable throwable) {

            }
        };
        String userId = (String) SPUtils.get(AppConstant.USER_ID, "");
        ApiMethods.messageInfo(new MyObserver<>(this, listener), userId, "False");
    }

    /*  *//**
     * {@link Runnable}
     *//*
    @Override
    public void run() {
        *//*
         父布局为LinearLayout，因为 ViewPager 使用了权重的问题
         软键盘在弹出的时候会把布局进行收缩，ViewPager 的高度缩小了
         所以 BottomNavigationView 会显示在ViewPager 下面
         解决方法是在 ViewPager 初始化高度后手动进行设置 ViewPager 高度并将权重设置为0
         *//*
        if (mViewPager != null) {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mViewPager.getLayoutParams();
            layoutParams.height = mViewPager.getHeight();
            layoutParams.width = mViewPager.getWidth();
            mViewPager.setLayoutParams(layoutParams);
        }
    }*/

    /**
     * {@link ViewPager.OnPageChangeListener}
     */

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
//                mBottomNavigationView.setSelectedItemId(R.id.menu_home);
                break;
            case 1:
//                mBottomNavigationView.setSelectedItemId(R.id.home_credit);
                break;
            /*case 2:
                mBottomNavigationView.setSelectedItemId(R.id.home_message);
                break;*/
            case 2:
//                mBottomNavigationView.setSelectedItemId(R.id.home_me);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @OnClick({R.id.home_ll, R.id.helper_ll, R.id.tab_center_ll, R.id.found_ll, R.id.me_ll})
    public void onViewClicked(View view) {
        EventBus.getDefault().post(new ShowSearchEvent());
        switch (view.getId()) {
            case R.id.home_ll:
                currentDateTv.setVisibility(View.VISIBLE);
                ImmersionBar.with(this).statusBarDarkFont(true).transparentStatusBar().init();
                mViewPager.setCurrentItem(0);
                clearStatus();
                selectIndex(0);
                if (ButtonUtils.isFastDoubleClick(R.id.home_ll)) {
                    HomeFragment item = (HomeFragment) mAdapter.getItem(0);
                    if (item != null) {
                        item.scroolTopRefresh();
                    }
                }
                break;
            case R.id.helper_ll:
                currentDateTv.setVisibility(View.GONE);
                ImmersionBar.with(this).statusBarDarkFont(true).transparentStatusBar().init();
                mViewPager.setCurrentItem(1);
                clearStatus();
                selectIndex(1);
                if (ButtonUtils.isFastDoubleClick(R.id.helper_ll)) {
                    HelperFragment item = (HelperFragment) mAdapter.getItem(1);
                    if (item != null) {
                        item.scroolTopRefresh();
                    }
                }
                break;
            case R.id.tab_center_ll:
                if (SPUtils.getIsLogin()) {
//                    String type = (String) SPUtils.get("type", "draft");
//                    if (!TextUtils.isEmpty(type)){
//                        if (type.equals("draft")){
//                           boolean draftsuccess = (boolean) SPUtils.get("draftsuccess", false);
//                           if (draftsuccess){
//                               popupSendRepiceWindow(tabCenterLl);
//                           }else {
//                               ToastUtils.show("有待完成菜谱存在，请稍后重试");
//                           }
//                        }else {
//                            boolean success = (boolean) SPUtils.get("success", false);
//                            if (success){
//                                popupSendRepiceWindow(tabCenterLl);
//                            }else {
//                                ToastUtils.show("有待完成菜谱存在，请稍后重试");
//                            }
//                        }
//                    }else {
//                        popupSendRepiceWindow(tabCenterLl);
//                    }

//                    customPopWindow.showAtLocation(tabCenterLl, Gravity.CENTER_HORIZONTAL, 0, 400);

                    showBottomDialog();
                } else {
//                    LoginActivity.openActivity(this);
                    startActivity(new Intent(this, WelActivity.class));
                }
                break;
            case R.id.found_ll:
                if (ButtonUtils.isFastDoubleClick(R.id.found_ll)) {
                    FoundFragment item = (FoundFragment) mAdapter.getItem(2);
                    if (item != null) {
                        item.scroolTopRefresh();
                    }
                }
                currentDateTv.setVisibility(View.GONE);
                ImmersionBar.with(this).statusBarDarkFont(true).transparentStatusBar().init();
                mViewPager.setCurrentItem(2);
                clearStatus();
                selectIndex(2);
                break;
            case R.id.me_ll:
                if (ButtonUtils.isFastDoubleClick(R.id.me_ll)) {
                    MineFragment item = (MineFragment) mAdapter.getItem(3);
                    if (item != null) {
                        item.scroolTopRefresh();
                    }
                }
                currentDateTv.setVisibility(View.GONE);
                ImmersionBar.with(this).statusBarDarkFont(true).transparentStatusBar().init();
                mViewPager.setCurrentItem(3);
                clearStatus();
                selectIndex(3);
                break;
        }
    }


    /**
     * 选择图片
     */
    private Dialog bottomDialog;

    private void showBottomDialog() {

        bottomDialog = new Dialog(this, R.style.CustomDialog);
        bottomDialog.show();
        if (bottomDialog.getWindow() != null)
            bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.setContentView(R.layout.popup_send_repice_diary);
        LinearLayout diaryLl = bottomDialog.findViewById(R.id.diary_ll);
        TextView closeTv = bottomDialog.findViewById(R.id.close_tv);
        LinearLayout repiceLl = bottomDialog.findViewById(R.id.repice_ll);
        repiceLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (HomeActivity.isLoginMethod()) {
                    ToastUtils.show("请先登录");
                } else {
                    startActivity(new Intent(HomeActivity.this, SendRepiceActivity.class));
                    bottomDialog.dismiss();
                }
            }
        });

        diaryLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (HomeActivity.isLoginMethod()) {
                    ToastUtils.show("请先登录");
                } else {
                    startActivity(new Intent(HomeActivity.this, SendDiaryActivity.class));
                    bottomDialog.dismiss();
                }
            }
        });

        closeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDialog.dismiss();
            }
        });
    }


    @SuppressLint("NewApi")
    private void popupSendRepiceWindow(LinearLayout tabCenterLl) {


        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View inflate = LayoutInflater.from(this).inflate(R.layout.popup_send_repice_diary, null);
        TextView closeTv = inflate.findViewById(R.id.close_tv);
        LinearLayout diaryLl = inflate.findViewById(R.id.diary_ll);
        LinearLayout repiceLl = inflate.findViewById(R.id.repice_ll);
        inflate.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);

        if (popupWindowBuilder == null) {
            popupWindowBuilder = new CustomPopWindow.PopupWindowBuilder(this);
        }
        popupWindowBuilder.setView(inflate).size(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .setBgDarkAlpha(0.5f).enableBackgroundDark(true).setOutsideTouchable(true).setAnimationStyle(R.style.RtcPopupAnimation);
        customPopWindow = popupWindowBuilder.create();

        repiceLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (HomeActivity.isLoginMethod()) {
                    ToastUtils.show("请先登录");
                } else {
                    startActivity(new Intent(HomeActivity.this, SendRepiceActivity.class));
                    customPopWindow.dissmiss();
                }
            }
        });

        diaryLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (HomeActivity.isLoginMethod()) {
                    ToastUtils.show("请先登录");
                } else {
                    startActivity(new Intent(HomeActivity.this, SendDiaryActivity.class));
                    customPopWindow.dissmiss();
                }
            }
        });

        closeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customPopWindow.dissmiss();
            }
        });
    }

    public void clearStatus() {

        homeImage.setBackgroundResource(R.mipmap.tab_ico_home_off);
        helperImage.setBackgroundResource(R.mipmap.tab_ico_message_off);
        foundImage.setBackgroundResource(R.mipmap.tab_ico_found_off);
        meImage.setBackgroundResource(R.mipmap.tab_ico_me_off);

        homeTv.setTextColor(getResources().getColor(R.color.text_gray_normal));
        helperTv.setTextColor(getResources().getColor(R.color.text_gray_normal));
        foundTv.setTextColor(getResources().getColor(R.color.text_gray_normal));
        meTv.setTextColor(getResources().getColor(R.color.text_gray_normal));

    }

    public void selectIndex(int position) {
        switch (position) {
            case 0:
                homeImage.setBackgroundResource(R.mipmap.tab_ico_home);
                homeTv.setTextColor(getResources().getColor(R.color.app_theme));

                break;
            case 1:
                helperImage.setBackgroundResource(R.mipmap.tab_ico_message);
                helperTv.setTextColor(getResources().getColor(R.color.app_theme));

                break;
            case 2:
                foundImage.setBackgroundResource(R.mipmap.tab_ico_found);
                foundTv.setTextColor(getResources().getColor(R.color.app_theme));
                messageNumTv.setVisibility(View.GONE);
                break;
            case 3:
                meImage.setBackgroundResource(R.mipmap.tab_ico_me);
                meTv.setTextColor(getResources().getColor(R.color.app_theme));

                break;
            default:
                homeImage.setBackgroundResource(R.mipmap.tab_ico_home);
                homeTv.setTextColor(getResources().getColor(R.color.app_theme));
                break;
        }

    }

    @Override
    public void onBackPressed() {
        if (OnClickUtils.isOnDoubleClick()) {
            //移动到上一个任务栈，避免侧滑引起的不良反应
//            moveTaskToBack(false);
            getWindow().getDecorView().postDelayed(new Runnable() {
                @Override
                public void run() {
                    //销毁掉当前界面
                    finish();
                }
            }, 300);
        } else {
            ToastUtils.show(getResources().getString(R.string.home_exit_hint));
        }
    }


    @Override
    public boolean isStatusBarEnabled() {
        return super.isStatusBarEnabled();
    }

    @Override
    public boolean statusBarDarkFont() {
        return super.statusBarDarkFont();
    }

    @Override
    protected void onResume() {
        isForeground = true;
        super.onResume();
    }

    @Override
    protected void onStart() {
        checkDeviceHasNavigationBar(this);
        super.onStart();
    }

    @Override
    protected void onPause() {
        isForeground = false;
        super.onPause();
    }


    @Override
    protected void onDestroy() {
        mViewPager.removeOnPageChangeListener(this);
        mViewPager.setAdapter(null);
        stopTask();
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public boolean isSupportSwipeBack() {
        // 不使用侧滑功能
        return !super.isSupportSwipeBack();
    }


    private final void startOrientationChangeListener() {
        mOrientationListener = new OrientationEventListener(this) {
            @Override
            public void onOrientationChanged(int rotation) {
                if (((rotation >= 0) && (rotation <= 45)) || (rotation >= 315) || ((rotation >= 135) && (rotation <= 225))) {//portrait
                    mCurrentOrient = true;
                    if (mCurrentOrient != mScreenProtrait) {
                        mScreenProtrait = mCurrentOrient;
                        OrientationChanged(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                        Log.d(TAG, "Screen orientation changed from Landscape to Portrait!");
                    }
                } else if (((rotation > 45) && (rotation < 135)) || ((rotation > 225) && (rotation < 315))) {//landscape
                    mCurrentOrient = false;
                    if (mCurrentOrient != mScreenProtrait) {
                        mScreenProtrait = mCurrentOrient;
                        OrientationChanged(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                        Log.d(TAG, "Screen orientation changed from Portrait to Landscape!");
                    }
                }
            }
        };
        mOrientationListener.enable();
    }


    @Override
    public void OrientationChanged(int orientation) {
        if (orientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            //横屏设置
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            //竖屏设置
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }


    /**
     * 请求版本号
     */
    private void checkVersion() {
        //新版本的apk的地址
        int newVersionCode = 101;
        //获取当前app版本
        int currVersionCode = AppUtils.getPackageVersionCode(HomeActivity.this);

        //如果当前版本小于新版本，则提示更新
        if (currVersionCode < newVersionCode) {
            Log.i("tag", "有新版本需要更新");
            showHintDialog();
        }
    }

    //显示询问用户是否更新APP的dialog
    private void showHintDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.mipmap.ic_launcher).setMessage("检测到当前有新版本，是否更新?").setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //取消更新，则跳转到旧版本的APP的页面
                Toast.makeText(HomeActivity.this, "暂时不更新app", Toast.LENGTH_SHORT).show();
            }
        }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //6.0以下系统，不需要请求权限,直接下载新版本的app
                      /*  requestReadAndWriteSDPermission(new PermissionHandler() {
                            @Override
                            public void onGranted() {
                                downloadApk();
                            }
                        });*/
            }
        }).create().show();
    }

    //下载最新版的app
    private void downloadApk() {
        boolean isWifi = AppUtils.isWifi(this); //是否处于WiFi状态
        if (isWifi) {
            startService(new Intent(HomeActivity.this, UpdateService.class));
            Toast.makeText(HomeActivity.this, "开始下载。", Toast.LENGTH_LONG).show();
        } else {
            //弹出对话框，提示是否用流量下载
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("提示");
            builder.setMessage("是否要用流量进行下载更新");
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    Toast.makeText(HomeActivity.this, "取消更新。", Toast.LENGTH_LONG).show();
                }
            });

            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    startService(new Intent(HomeActivity.this, UpdateService.class));
                    Toast.makeText(HomeActivity.this, "开始下载。", Toast.LENGTH_LONG).show();
                }
            });
            builder.setCancelable(false);

            AlertDialog dialog = builder.create();
            //设置不可取消对话框
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    /*在fragment的管理类中，我们要实现这部操作，而他的主要作用是，当D这个activity回传数据到
    这里碎片管理器下面的fragnment中时，往往会经过这个管理器中的onActivityResult的方法。*/
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        /*在这里，我们通过碎片管理器中的Tag，就是每个碎片的名称，来获取对应的fragment*/
        MineFragment f = (MineFragment) mAdapter.getItem(3);
        /*然后在碎片中调用重写的onActivityResult方法*/
        f.onActivityResult(requestCode, resultCode, data);
    }


    public int getMsg_num() {
        return msg_num;
    }

    public void setMessageNumTv() {
        SPUtils.put("msg_num", 0);
        messageNumTv.setVisibility(View.GONE);
       /* if (msg_num>0){
            messageNumTv.setVisibility(View.VISIBLE);
            if (msg_num < 100){
                messageNumTv.setText(msg_num + "");
            }else {
                messageNumTv.setText("···");
            }

        }else {
            messageNumTv.setVisibility(View.GONE);
        }*/
    }

    public void startTask() {

        if (timer == null) {
            timer = new Timer(true);
        }

        if (task == null) {
            //任务
            task = new TimerTask() {
                public void run() {
                    Message msg = new Message();
                    msg.what = 1;
                    handler.sendMessage(msg);
                }
            };
        }

        //启动定时器
        timer.schedule(task, 0, 10 * 1000);
    }

    public void stopTask() {
        task.cancel();
        task = null;
        timer.cancel();
        timer.purge();
        timer = null;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetMessage(Event message) {
        if (message.getType() == Event.EVENT_UPDATE_FOUBND) {
            mViewPager.setCurrentItem(2);
            clearStatus();
            selectIndex(2);
            FoundFragment item = (FoundFragment) mAdapter.getItem(2);
            if (item != null) {
                item.addData();
            }
        } else if (message.getType() == Event.EVENT_UPDATE_TOMINE) {
            mViewPager.setCurrentItem(3);
            clearStatus();
            selectIndex(3);
        } else if (message.getType() == Event.EVENT_NET) {
            LogUtils.d("网络状态===" + message.getParam());
            if (message.getParam().equals("false")) {
                if (!TextUtils.isEmpty((String) SPUtils.get("type", "")) &&
                        ((String) SPUtils.get("type", "")).equals("draft")) {
                    popupBackTip();
                } else {
                    popupOpenRepice();
                }
            } else {
                startService(new Intent(HomeActivity.this, CreateRepiceService.class));
            }

        } else if (message.getType() == Event.EVENT_SEND_FAIL) {
            popupOpenRepice();
        } else if (message.getType() == Event.EVENT_SAVE_FAIL) {
            popupBackTip();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void finishThis(String finishThis) {
        if ("finishThis".equals(finishThis)) finish();
    }

    /**
     * @return 判断是否是是自己账号，非空或非默认
     */
    public static boolean isLoginMethod() {
        String userId = (String) SPUtils.get(AppConstant.USER_ID, "");
        return TextUtils.isEmpty(userId) || "tmp_user".equals(userId);
    }

    public static String getOpenId() {
        String userId = (String) SPUtils.get(AppConstant.USER_ID, "");
        if (TextUtils.isEmpty(userId)) {
            return "tmp_user";
        }
        return userId;
    }

    /**
     * 判断是否存在NavigationBar
     *
     * @param context：上下文环境
     * @return：返回是否存在(true/false)
     */
    public boolean checkDeviceHasNavigationBar(Context context) {
        boolean hasNavigationBar = false;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                //不存在虚拟按键
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                //存在虚拟按键
                hasNavigationBar = true;
                //手动设置控件的margin
                //linebutton是一个linearlayout,里面包含了两个Button
                LinearLayout.LayoutParams layout = (LinearLayout.LayoutParams) bottomLl.getLayoutParams();
                //setMargins：顺序是左、上、右、下
                layout.setMargins(0, 0, 0, getNavigationBarHeight(this) + 10);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hasNavigationBar;
    }


    /**
     * 测量底部导航栏的高度
     *
     * @param mActivity:上下文环境
     * @return：返回测量出的底部导航栏高度
     */
    private int getNavigationBarHeight(Activity mActivity) {
        Resources resources = mActivity.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }


    public void popupOpenRepice() {
        if (builder == null) {
            builder = new DialogBackTip.Builder(this);
        }

        if (!builder.create().isShowing()) {

            builder.setTitle("发布失败，是否重新发布？")
                    .setLeftText("暂存至草稿箱")
                    .setRightText("重新发布")
                    .setRightClickListener(new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            SPUtils.put("type", "release");
                            startService(new Intent(HomeActivity.this, CreateRepiceService.class));
                        }
                    }).setLeftClickListener(new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    SPUtils.put("type", "draft");
                    startService(new Intent(HomeActivity.this, CreateRepiceDraftService.class));
                }
            }).create().show();
        }


    }


    public void popupBackTip() {
        if (builder2 == null) {
            builder2 = new DialogBackTip.Builder(this);
        }

        builder2.setTitle("是否将菜谱保存为草稿？")
                .setLeftText("不保存草稿")
                .setRightText("保存草稿")
                .setRightClickListener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startService(new Intent(HomeActivity.this, CreateRepiceDraftService.class));
                    }
                }).setLeftClickListener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SPUtils.put("draft", "");
                SPUtils.put("draftsuccess", true);
                SPUtils.put(AppConstant.repice, "");
                SPUtils.put(AppConstant.pic, "");
                SPUtils.put(AppConstant.USER_IMAGE, "");
            }
        }).create().show();


    }

}