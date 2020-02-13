package com.ebanswers.kitchendiary.mvp.view.mine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ebanswers.kitchendiary.R;
import com.ebanswers.kitchendiary.common.CommonActivity;
import com.ebanswers.kitchendiary.config.Constans;
import com.ebanswers.kitchendiary.mvp.view.base.WebActivity;
import com.ebanswers.kitchendiary.utils.AppUtils;
import com.ebanswers.kitchendiary.utils.Constants;
import com.ebanswers.kitchendiary.utils.NetworkUtils;

import butterknife.BindView;

public class NewAboutActivity extends CommonActivity {

    TextView user_pro;
    TextView user_pri;
    TextView aboutus;
    TextView version;


    ImageView topback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_about);



        user_pri = findViewById(R.id.ab_userprivacy);
        user_pro = findViewById(R.id.ab_userprotocol);
        aboutus = findViewById(R.id.ab_aboutus);
        topback = findViewById(R.id.ab_topback);

        version = findViewById(R.id.app_version_tv);
        version.setText("V" + AppUtils.getVersionName(getApplicationContext()));


        user_pro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goWeb(Constans.URL_PROTOCOL);
            }
        });

        user_pri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goWeb(Constans.URL_PRIVACY);
            }
        });

        aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goWeb("https://mp.weixin.qq.com/s?__biz=MzAwMDIxNjY3Mw==&mid=502137091&idx=1&sn=7e202f0fb2f4b89ef5ae3d02bd6d283f#wechat_redirect");
            }
        });

        topback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });







    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_new_about;
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

    private void goWeb(String url) {
        if (NetworkUtils.checkNetwork(this)) {
            Intent intent = new Intent(this, WebActivity.class);
            intent.putExtra("url", url);
            intent.putExtra("flag", 4);
            startActivity(intent);
        } else {
            Toast.makeText(this, "请检查网络", Toast.LENGTH_SHORT).show();
        }

    }
}
