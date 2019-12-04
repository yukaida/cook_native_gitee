package com.ebanswers.kitchendiary.mvp.view.base;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.ebanswers.kitchendiary.R;
import com.ebanswers.kitchendiary.config.Constans;
import com.ebanswers.kitchendiary.config.WechatUserConfig;
import com.ebanswers.kitchendiary.constant.FileUtils;
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


/**
 * @author Created by lishihui on 2017/1/9.
 */

public class WelActivity extends MainBaseActivity {

    @BindView(R.id.tv_wel_skip)
    TextView tvWelSkip;
    @BindView(R.id.iv_wel_ad)
    ImageView ivWelAd;
    private Subscription countDown;
    private AD localAd;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_wel;
    }

    @Override
    protected void onCreateNext(@Nullable Bundle savedInstanceState) {
        localAd = ADManager.getInstance().getLocalValidAd();
        File adFile = ADManager.getInstance().getLocalAdImage(localAd);


        DownloadSpeedUtil.getNetSpeed(getApplicationInfo().uid);
        if (localAd != null && adFile != null) {
            Picasso.with(this).load(adFile).resize(ScreenSizeUtils.getScreenWidth(this), ScreenSizeUtils.getScreenHeight(this)).centerInside().into(ivWelAd);
            startCountDown(localAd.getShow_time());
        } else {
            login();
        }


        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.ebanswers.smartkitchen",
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

    private void login() {
        final String open_id = WechatUserConfig.getWechatPublicOpenId(this);
        String token = (String) SPUtils.get(this, Constans.DEVICE_TOKEN, "");
        if (!TextUtils.isEmpty(open_id) && !TextUtils.isEmpty(token)) {
            setAliasAndTag(open_id);
            clearWebViewCache();

            Intent intent = new Intent(WelActivity.this, HomeActivity.class);
            intent.putExtra("open_id", open_id);
            startActivity(intent);
        } else {
            startActivity(new Intent(WelActivity.this, WXEntryActivity.class));
        }
        finish();
    }

    @OnClick({R.id.iv_wel_ad, R.id.tv_wel_skip})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_wel_ad:
                if (localAd != null) {
                    Intent intent1 = new Intent(context, FullActivity.class);
                    intent1.putExtra("type", FullActivity.TYPE_LOGIN_POLICY);
                    intent1.putExtra("url", localAd.getUrl());
                    startActivityForResult(intent1, 0);
                    tvWelSkip.setVisibility(View.GONE);
                    stopCountDown();
                }
                break;
            case R.id.tv_wel_skip:
                login();
                break;
            default:
                break;
        }
    }

    private void stopCountDown() {
        if (countDown != null) {
            countDown.unsubscribe();
        }
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
                            login();
                            countDown.unsubscribe();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
    }

    @Override
    protected View getWifiLostView() {
        return null;
    }

    private void clearWebViewCache() {
        //刚启动时清除webview缓存
        FileUtils.deleteFile(getFilesDir().getParent() + "/app_webview");
        CookieManager manager = CookieManager.getInstance();
        manager.removeAllCookie();
    }

    private void setAliasAndTag(final String openId) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
//                JPushInterface.setAlias(getApplicationContext(), 0, openId.replace("-", ""));
//                JPushInterface.resumePush(getApplicationContext());
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopCountDown();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
            login();
        }
    }
}
