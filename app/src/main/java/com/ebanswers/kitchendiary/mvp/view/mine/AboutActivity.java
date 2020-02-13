package com.ebanswers.kitchendiary.mvp.view.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.ebanswers.kitchendiary.mvp.view.base.BaseActivity;
import com.ebanswers.kitchendiary.mvp.view.base.WebActivity;
import com.ebanswers.kitchendiary.utils.NetworkUtils;
import com.hjq.bar.TitleBar;
import com.ebanswers.kitchendiary.R;
import com.ebanswers.kitchendiary.common.CommonActivity;
import com.ebanswers.kitchendiary.utils.AppUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author : HJQ
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2018/10/18
 * desc   : 关于界面
 */
public class AboutActivity extends BaseActivity {

    @BindView(R.id.tb_about_title)
    TitleBar tbAboutTitle;
    @BindView(R.id.app_version_tv)
    TextView appVersionTv;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    protected void onCreateNext(@Nullable Bundle savedInstanceState) {
        appVersionTv.setText("V" + AppUtils.getVersionName(getApplicationContext()));
    }

    @Override
    protected View getWifiLostView() {
        return null;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    private void gowWeb(String url) {
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
