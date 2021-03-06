package com.ebanswers.kitchendiary.mvp.view.base;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ebanswers.kitchendiary.R;
import com.ebanswers.kitchendiary.common.CommonActivity;
import com.ebanswers.kitchendiary.config.Constans;
import com.ebanswers.kitchendiary.database.bean.AD;
import com.ebanswers.kitchendiary.utils.ADManager;
import com.ebanswers.kitchendiary.utils.DownloadSpeedUtil;
import com.ebanswers.kitchendiary.utils.LanguageUtil;
import com.ebanswers.kitchendiary.utils.SPUtils;
import com.ebanswers.kitchendiary.utils.ScreenSizeUtils;
import com.ebanswers.kitchendiary.wxapi.WXEntryActivity;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import static java.lang.Thread.sleep;

//启动页 广告页 隐私弹窗页

public class SplashActivity extends CommonActivity {

    @BindView(R.id.tv_wel_skip)
    TextView tvWelSkip;
    @BindView(R.id.iv_wel_ad)
    ImageView ivWelAd;
    private Subscription countDown;
    private AD localAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        localAd = ADManager.getInstance().getLocalValidAd();
        File adFile = ADManager.getInstance().getLocalAdImage(localAd);

        if (localAd != null) {
            Log.d("localad", "onCreate: "+localAd.toString());
        }

        DownloadSpeedUtil.getNetSpeed(getApplicationInfo().uid);

        if (localAd != null && adFile != null) {
            Picasso.with(this).load(adFile).resize(ScreenSizeUtils.getScreenWidth(this), ScreenSizeUtils.getScreenHeight(this)).centerInside().into(ivWelAd);
            startCountDown(localAd.getShow_time());
        } else {
            pri_Pop();
        }


        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.ebanswers.kitchendiary",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
        } catch (NoSuchAlgorithmException e) {
        }
    }


    private void stopCountDown() {
        if (countDown != null) {
            countDown.unsubscribe();
        }
    }

    private void pri_Pop(){
        new Thread( new Runnable( ) {
            @Override
            public void run() {
                //耗时任务，比如加载网络数据
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // 这里可以睡几秒钟，如果要放广告的话
                        try {
                            sleep(800);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }


                        if (!(Boolean) (SPUtils.get(SplashActivity.this, Constans.AGREEN_PRIVACY, false))) {
                            createPrivacyDialog();
                        } else {
                            Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
//                                HomeActivity.newInstance(SplashActivity.this);
                            startActivity(intent);
                            SplashActivity.this.finish();
                        }

//                        Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
////                                HomeActivity.newInstance(SplashActivity.this);
//                        startActivity(intent);


                    }
                });
            }
        } ).start();
    }//隐私弹窗

    @Override
    protected int getLayoutId() {
        return R.layout.activity_wel;
    }

    @Override
    protected int getTitleBarId() {
        return 0;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }


    public void createPrivacyDialog() {
        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_privacy, null);
        dialog.setView(view);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(ScreenSizeUtils.getScreenWidth(this) * 4 / 5, WindowManager.LayoutParams.WRAP_CONTENT);
        TextView clickAll = (TextView) view.findViewById(R.id.tv_dialog_click);
        Button confirmBtn = (Button) view.findViewById(R.id.btn_dialog_confirm);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SPUtils.put(SplashActivity.this, Constans.AGREEN_PRIVACY, true);
                dialog.dismiss();
                //跳转到登录界面
                Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
//                                HomeActivity.newInstance(SplashActivity.this);
                startActivity(intent);
                SplashActivity.this.finish();
            }
        });

        Button rejectBtn = view.findViewById(R.id.btn_dialog_reject);
        rejectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        SpannableString spannableString = new SpannableString(clickAll.getText());



        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
//                Intent intent1 = new Intent(SplashActivity.this, FullActivity.class);
//                intent1.putExtra("type", FullActivity.TYPE_LOGIN_POLICY);
//                intent1.putExtra("url", Constans.URL_PROTOCOL);
//                startActivity(intent1);

                Intent intent1 = new Intent(SplashActivity.this, WebActivity.class);
                intent1.putExtra("url", Constans.URL_PROTOCOL);
                startActivity(intent1);


            }
            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                ds.setUnderlineText(false);
            }
        }, 9, 15, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//
        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Intent intent1 = new Intent(SplashActivity.this, WebActivity.class);
                intent1.putExtra("url", Constans.URL_PRIVACY);
                startActivity(intent1);
            }


            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                ds.setUnderlineText(false);
            }
        }, 16, clickAll.getText().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.main_color)), 9, 15, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.main_color)), 16, clickAll.getText().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

//        clickAll.setText(spannableString);
//        clickAll.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent1 = new Intent(SplashActivity.this, WebActivity.class);
//                intent1.putExtra("url", Constans.URL_PRIVACY);
//                startActivity(intent1);
//            }
//        });

        clickAll.setMovementMethod(LinkMovementMethod.getInstance());
        clickAll.setText(spannableString);
        clickAll.setHighlightColor(getResources().getColor(R.color.nocolor));
    }

    private void startCountDown(final int seconds) {
        countDown = Observable.interval(1, 1, TimeUnit.SECONDS).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        tvWelSkip.setVisibility(View.VISIBLE);
                        tvWelSkip.setText(LanguageUtil.getInstance().getStringById(R.string.wel_skip) + (seconds - aLong));
                        if (aLong >= seconds) {
                            pri_Pop();
                            countDown.unsubscribe();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
    }


    @OnClick({R.id.iv_wel_ad, R.id.tv_wel_skip})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_wel_ad:
                if (localAd != null) {
                    Intent intent1 = new Intent(SplashActivity.this, FullActivity.class);
                    intent1.putExtra("type", FullActivity.TYPE_LOGIN_POLICY);
                    intent1.putExtra("url", localAd.getUrl());
                    startActivityForResult(intent1, 0);
                    tvWelSkip.setVisibility(View.GONE);
                    stopCountDown();
                }
                break;
            case R.id.tv_wel_skip:
               pri_Pop();
                break;
            default:
                break;
        }
    }

}
