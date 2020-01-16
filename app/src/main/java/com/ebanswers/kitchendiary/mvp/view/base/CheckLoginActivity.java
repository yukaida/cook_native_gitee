package com.ebanswers.kitchendiary.mvp.view.base;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.ebanswers.kitchendiary.R;
import com.ebanswers.kitchendiary.common.CommonActivity;
import com.ebanswers.kitchendiary.constant.AppConstant;
import com.ebanswers.kitchendiary.utils.SPUtils;

public class CheckLoginActivity extends CommonActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_login);

        if ("tmp_user".equals(SPUtils.get(AppConstant.USER_ID, ""))) {//没登录
            startActivity(new Intent(this, WelActivity.class));
            finish();
        } else {
            Intent intent = new Intent(this, SendDiaryActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected int getLayoutId() {
        return 0;
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
}
