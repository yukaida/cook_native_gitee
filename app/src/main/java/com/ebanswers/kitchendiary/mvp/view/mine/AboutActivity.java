package com.ebanswers.kitchendiary.mvp.view.mine;

import android.os.Bundle;
import android.widget.TextView;

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
public class AboutActivity extends CommonActivity {

    @BindView(R.id.tb_about_title)
    TitleBar tbAboutTitle;
    @BindView(R.id.app_version_tv)
    TextView appVersionTv;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_about_title;
    }

    @Override
    protected void initView() {
        appVersionTv.setText("版本号：" + AppUtils.getVersionName(getApplicationContext()));
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
