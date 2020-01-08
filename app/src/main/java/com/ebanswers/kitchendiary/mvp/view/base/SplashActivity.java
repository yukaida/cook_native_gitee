package com.ebanswers.kitchendiary.mvp.view.base;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.ebanswers.kitchendiary.R;
import com.ebanswers.kitchendiary.config.Constans;
import com.ebanswers.kitchendiary.utils.SPUtils;
import com.ebanswers.kitchendiary.utils.ScreenSizeUtils;

import static java.lang.Thread.sleep;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

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
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.main_color)), 8, clickAll.getText().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        clickAll.setText(spannableString);
        clickAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(SplashActivity.this, WebActivity.class);
                intent1.putExtra("url", "http://53iq.com/static/privacy/privacy.html");
                startActivity(intent1);
            }
        });
    }
}
