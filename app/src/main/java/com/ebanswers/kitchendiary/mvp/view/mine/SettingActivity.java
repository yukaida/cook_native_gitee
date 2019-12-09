package com.ebanswers.kitchendiary.mvp.view.mine;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ebanswers.kitchendiary.R;
import com.ebanswers.kitchendiary.common.CommonApplication;
import com.ebanswers.kitchendiary.config.Constans;
import com.ebanswers.kitchendiary.config.WechatUserConfig;
import com.ebanswers.kitchendiary.constant.AppConstant;
import com.ebanswers.kitchendiary.constant.FileUtils;
import com.ebanswers.kitchendiary.eventbus.Event;
import com.ebanswers.kitchendiary.eventbus.EventBusUtil;
import com.ebanswers.kitchendiary.mvp.view.base.BaseActivity;
import com.ebanswers.kitchendiary.mvp.view.base.HomeActivity;
import com.ebanswers.kitchendiary.mvp.view.base.WebActivity;
import com.ebanswers.kitchendiary.mvp.view.base.WelActivity;
import com.ebanswers.kitchendiary.utils.CheckUpdateTask;
import com.ebanswers.kitchendiary.utils.DialogUtils;
import com.ebanswers.kitchendiary.utils.LanguageUtil;
import com.ebanswers.kitchendiary.utils.LongClickUtils;
import com.ebanswers.kitchendiary.utils.NetworkUtils;
import com.ebanswers.kitchendiary.utils.SPUtils;
import com.ebanswers.kitchendiary.utils.SavePictureUtils;
import com.ebanswers.kitchendiary.utils.ThreadUtils;
import com.ebanswers.kitchendiary.utils.ToastCustom;
import com.ebanswers.kitchendiary.wxapi.WXEntryActivity;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.trycatch.mysnackbar.Prompt;
import com.trycatch.mysnackbar.TSnackbar;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.simple.eventbus.EventBus;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import butterknife.BindView;
import butterknife.OnClick;

import static com.qmuiteam.qmui.widget.dialog.QMUITipDialog.Builder.ICON_TYPE_LOADING;

/**
 * @author Created by lishihui on 2017/1/16.
 */

public class SettingActivity extends BaseActivity {
    @BindView(R.id.id_img_title_setting)
    ImageView idImgTitleSetting;
    @BindView(R.id.id_tv_title_name)
    TextView idTvTitleName;
    @BindView(R.id.id_img_title_back)
    ImageView idImgTitleBack;
    @BindView(R.id.id_ll_setting_activity_clear)
    LinearLayout idLlSettingActivityClear;
    @BindView(R.id.id_tv_setting_activity_size)
    TextView idTvCacheSize;
    @BindView(R.id.id_tv_setting_activity_exit)
    TextView idTvSettingActivityExit;
    @BindView(R.id.id_tv_setting_activity_version)
    TextView version;
    @BindView(R.id.id_ll_setting_activity_checkupdate)
    LinearLayout idLlSettingActivityCheckupdate;
    @BindView(R.id.id_tv_setting_activity_mytag)
    TextView idTvSettingActivityMytag;
    @BindView(R.id.id_tv_setting_activity_notify)
    TextView idTvSettingActivityNotify;
    @BindView(R.id.id_tv_setting_activity_about)
    TextView idTvSettingActivityAbout;
    @BindView(R.id.id_tv_setting_activity_reply)
    TextView idTvSettingActivityReply;
    private float cacheFileSize = 0.0f;
    private LinearLayout idLlNetwork;
    private ScheduledExecutorService scheduledExecutorService;
    private int speed_totle = 0;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void onCreateNext(@Nullable Bundle savedInstanceState) {
        org.greenrobot.eventbus.EventBus.getDefault().register(this);
        initViews();
        initListener();

        if (SPUtils.getIsLogin()) {
            idTvSettingActivityExit.setText(getString(R.string.exit_login));
        } else {
            idTvSettingActivityExit.setText(getString(R.string.login_text));
        }

        scheduledExecutorService = new ScheduledExecutorService() {
            @NonNull
            @Override
            public ScheduledFuture<?> schedule(@NonNull Runnable runnable, long l, @NonNull TimeUnit timeUnit) {
                return null;
            }

            @NonNull
            @Override
            public <V> ScheduledFuture<V> schedule(@NonNull Callable<V> callable, long l, @NonNull TimeUnit timeUnit) {
                return null;
            }

            @NonNull
            @Override
            public ScheduledFuture<?> scheduleAtFixedRate(@NonNull Runnable runnable, long l, long l1, @NonNull TimeUnit timeUnit) {
                return null;
            }

            @NonNull
            @Override
            public ScheduledFuture<?> scheduleWithFixedDelay(@NonNull Runnable runnable, long l, long l1, @NonNull TimeUnit timeUnit) {
                return null;
            }

            @Override
            public void shutdown() {

            }

            @NonNull
            @Override
            public List<Runnable> shutdownNow() {
                return null;
            }

            @Override
            public boolean isShutdown() {
                return false;
            }

            @Override
            public boolean isTerminated() {
                return false;
            }

            @Override
            public boolean awaitTermination(long l, @NonNull TimeUnit timeUnit) throws InterruptedException {
                return false;
            }

            @NonNull
            @Override
            public <T> Future<T> submit(@NonNull Callable<T> callable) {
                return null;
            }

            @NonNull
            @Override
            public <T> Future<T> submit(@NonNull Runnable runnable, T t) {
                return null;
            }

            @NonNull
            @Override
            public Future<?> submit(@NonNull Runnable runnable) {
                return null;
            }

            @NonNull
            @Override
            public <T> List<Future<T>> invokeAll(@NonNull Collection<? extends Callable<T>> collection) throws InterruptedException {
                return null;
            }

            @NonNull
            @Override
            public <T> List<Future<T>> invokeAll(@NonNull Collection<? extends Callable<T>> collection, long l, @NonNull TimeUnit timeUnit) throws InterruptedException {
                return null;
            }

            @NonNull
            @Override
            public <T> T invokeAny(@NonNull Collection<? extends Callable<T>> collection) throws InterruptedException, ExecutionException {
                return null;
            }

            @Override
            public <T> T invokeAny(@NonNull Collection<? extends Callable<T>> collection, long l, @NonNull TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
                return null;
            }

            @Override
            public void execute(@NonNull Runnable runnable) {

            }
        };

    }

    private void initViews() {
        idTvTitleName.setText(R.string.setting);
        idImgTitleSetting.setVisibility(View.INVISIBLE);
        idTvSettingActivityMytag.setVisibility(LanguageUtil.getInstance().isChinease() ? View.VISIBLE : View.GONE);
        idTvSettingActivityAbout.setVisibility(LanguageUtil.getInstance().isChinease() ? View.VISIBLE : View.GONE);
        idTvSettingActivityReply.setVisibility(LanguageUtil.getInstance().isChinease() ? View.VISIBLE : View.GONE);

        try {
            String version_name = getPackageManager().getPackageInfo(getApplication().getPackageName(), 0).versionName;
            version.setText(version_name);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            version.setText("1.0");
        }
        ThreadUtils.exeMore(new Runnable() {
            @Override
            public void run() {
                getFileSize(getCacheDir());
                getFileSize(getExternalCacheDir());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        DecimalFormat df = new DecimalFormat("######0.00");
                        if (cacheFileSize == 0) {
                            idTvCacheSize.setText(0 + "M");
                        } else {
                            idTvCacheSize.setText(df.format(cacheFileSize) + "M");
                        }
                    }
                });
            }
        });
    }

    private void initListener() {
        idTvSettingActivityAbout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ToastCustom.makeText(SettingActivity.this, R.string.set_core, Toast.LENGTH_SHORT).show();
                return true;
            }
        });
//        idLlSettingActivityCheckupdate.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                Log.d("SettingActivity", "onLongClick: ");
//                update(Constans.SETTING_UPDATE_DEBUG);
//                return true;
//            }
//        });
        LongClickUtils.setLongClick(new Handler(), idLlSettingActivityCheckupdate, 5000, new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.d("SettingActivity", "onLongClick: ");
                update(Constans.SETTING_UPDATE_DEBUG);
                return true;
            }
        });
    }

    public void onPause() {
        super.onPause();
        if (myHandler != null) {
            myHandler.removeCallbacksAndMessages(null);
        }
    }


    @OnClick({R.id.id_img_title_back, R.id.id_ll_setting_activity_clear, R.id.id_tv_setting_activity_exit, R.id.id_ll_setting_activity_checkupdate, R.id.id_tv_setting_activity_mytag, R.id.id_tv_setting_activity_notify, R.id.id_tv_setting_activity_about, R.id.id_tv_setting_activity_reply})
    public void onClickView(final View view) {
        switch (view.getId()) {
            case R.id.id_img_title_back:
                finish();
                break;
            case R.id.id_ll_setting_activity_clear:
                QMUITipDialog.Builder builder = new QMUITipDialog.Builder(this);
                builder.setIconType(ICON_TYPE_LOADING);
                builder.setTipWord(getResources().getString(R.string.set_clear));
                final QMUITipDialog qmuiTipDialog = builder.create();
                qmuiTipDialog.show();
                ThreadUtils.exeMore(new Runnable() {
                    @Override
                    public void run() {
                        clearCache();
                        if (qmuiTipDialog.isShowing()) {
                            myHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    if (isFinishing()) {
                                        return;
                                    }
                                    idTvCacheSize.setText("0M");
                                    qmuiTipDialog.dismiss();
                                    TSnackbar.make(rootView, getResources().getString(R.string.set_clear_success), TSnackbar.LENGTH_SHORT, TSnackbar.APPEAR_FROM_TOP_TO_DOWN)
                                            .setPromptThemBackground(Prompt.SUCCESS)
                                            .setBackgroundColor(Color.parseColor("#f35019"))
                                            .show();
                                }
                            }, 1000);
                        }
                    }
                });
                break;
            case R.id.id_tv_setting_activity_exit:

                if (SPUtils.getIsLogin()) {
                    ThreadUtils.exeMore(new Runnable() {
                        @Override
                        public void run() {
                            WechatUserConfig.clear(SettingActivity.this);
//                        SPUtils.setLogin(false);
                            SPUtils.clearUserInfo();
                            clearCache();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    SPUtils.put(AppConstant.USER_ID, "tmp_user");
                                    org.greenrobot.eventbus.EventBus.getDefault().post("finishThis");
                                    CommonApplication.getInstance().removeAllActivity(SettingActivity.this);
                                    Intent intent = new Intent(SettingActivity.this, HomeActivity.class);
                                    intent.putExtra("position","3");
                                    startActivity(intent);
                                    EventBusUtil.sendEvent(new Event(Event.EVENT_UPDATE_TOMINE,"我的"));
                                    finish();
                                }
                            });
                        }
                    });
                } else {
                    SPUtils.put(AppConstant.USER_ID, "tmp_user");
                    SPUtils.put(AppConstant.USER_NAME, "厨房客人");
//                    org.greenrobot.eventbus.EventBus.getDefault().post("finishThis");
                    EventBusUtil.sendEvent(new Event(Event.EVENT_UPDATE_TOMINE,"我的"));
                    Intent intent = new Intent(SettingActivity.this, WXEntryActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }

                break;
            case R.id.id_ll_setting_activity_checkupdate:
                update(Constans.SETTING_UPDATE);
                break;
            case R.id.id_tv_setting_activity_mytag:
                if (SPUtils.getIsLogin()) {
                    gowWeb("https://wechat.53iq.com/tmp/kitchen/" + SPUtils.get(AppConstant.USER_ID, "") + "/tag");
                } else {
//                    LoginActivity.openActivity(getContext(),LoginActivity.TYPE_PHONE_CODE);
                    startActivity(new Intent(this, WelActivity.class));
                }

                break;
            case R.id.id_tv_setting_activity_notify:
//                gowWeb(String.format(Constans.Notify, WechatUserConfig.getWechatPublicOpenId(CommonApplication.getInstance())));
                if (SPUtils.getIsLogin()) {
                    gowWeb("https://wechat.53iq.com/tmp/kitchen/setting?types=notify_setting&code=123&openid=" + SPUtils.get(AppConstant.USER_ID, ""));
                } else {
//                    LoginActivity.openActivity(getContext(),LoginActivity.TYPE_PHONE_CODE);
                    startActivity(new Intent(this, WelActivity.class));
                }

                break;
            case R.id.id_tv_setting_activity_about:
                gowWeb("https://mp.weixin.qq.com/s?__biz=MzAwMDIxNjY3Mw==&mid=502137091&idx=1&sn=7e202f0fb2f4b89ef5ae3d02bd6d283f#wechat_redirect");
                break;
            case R.id.id_tv_setting_activity_reply:
                View mview = View.inflate(this, R.layout.setting_reply_layout, null);
                final Dialog dialog1 = DialogUtils.createInteraction(this, mview);
                final ImageView imageView = (ImageView) mview.findViewById(R.id.id_img_assist_qr);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (dialog1 != null && dialog1.isShowing()) {
                            dialog1.dismiss();
                        }
                    }
                });
                imageView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        BitmapDrawable bitmapDrawable = (BitmapDrawable) imageView.getDrawable();
                        if (bitmapDrawable != null) {
                            Bitmap bitmap = bitmapDrawable.getBitmap();
                            SavePictureUtils.savePicture(context, bitmap);
                            ToastCustom.makeText(context, R.string.set_save_success, Toast.LENGTH_SHORT).show();
                            if (dialog1 != null && dialog1.isShowing()) {
                                dialog1.dismiss();
                            }
                        }
                        return true;
                    }
                });
                break;
            default:
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void finishThis(String finishThis) {
        if ("finishThis".equals(finishThis))
            finish();
    }

    private void update(final String updateUrl) {//检查更新方法
        Log.d("SettingActivity", "update: " + updateUrl);
        if (NetworkUtils.checkNetwork(this)) {

            QMUITipDialog.Builder builder1 = new QMUITipDialog.Builder(this);

            builder1.setIconType(ICON_TYPE_LOADING);
            builder1.setTipWord(getResources().getString(R.string.set_check_update));
            final QMUITipDialog ck_dialog = builder1.create();
            ck_dialog.show();
            myHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    new CheckUpdateTask(context, updateUrl).run(true, new CheckUpdateTask.CallBack() {
                        @Override
                        public void success() {
                            if (isFinishing()) {
                                return;
                            }
                            ck_dialog.dismiss();
                        }

                        @Override
                        public void error() {
                            if (isFinishing()) {
                                return;
                            }
                            ck_dialog.dismiss();
                        }
                    });
                }
            }, 1500);

        } else {
            checkNet();
        }
    }

    private void gowWeb(String url) {
        if (NetworkUtils.checkNetwork(this)) {
            Intent intent = new Intent(this, WebActivity.class);
            intent.putExtra("url", url);
            intent.putExtra("flag", 4);
            startActivity(intent);
        } else {
            checkNet();
        }

    }

    @Override
    protected View getWifiLostView() {
        idLlNetwork = (LinearLayout) findViewById(R.id.id_ll_network);
        return idLlNetwork;
    }

    private void getFileSize(File file) {
        if (file.exists() && file.isDirectory()) {
            for (File file1 : file.listFiles()) {
                if (file1.exists() && file1.isFile()) {
                    cacheFileSize += FileUtils.getFileSize(file1.getAbsolutePath()) * 1.0f / (1024 * 1024);
                } else {
                    getFileSize(file1);
                }
            }
        } else if (file.exists() && file.isFile()) {
            cacheFileSize = FileUtils.getFileSize(file.getAbsolutePath()) * 1.0f / (1024 * 1024);
        }
    }

    private void clearCache() {
        FileUtils.deleteFile(getCacheDir().getAbsolutePath());
        FileUtils.deleteFile(getExternalCacheDir().getAbsolutePath());
        FileUtils.deleteFile(getFilesDir().getAbsolutePath());
        FileUtils.deleteFile(getExternalFilesDir(null).getAbsolutePath());
        String path = getFilesDir().getAbsolutePath();
        path = path.substring(0, path.lastIndexOf("/") + 1);
        FileUtils.deleteFile(path + "app_webview");
        FileUtils.deleteFile(path + "databases");
        //清除webview缓存
        EventBus.getDefault().post("all", "clearWebCache");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        org.greenrobot.eventbus.EventBus.getDefault().unregister(this);
    }
}
