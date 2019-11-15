package com.ebanswers.kitchendiary.mvp.view.mine;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hjq.bar.TitleBar;
import com.ebanswers.kitchendiary.R;
import com.ebanswers.kitchendiary.common.CommonActivity;
import com.ebanswers.kitchendiary.common.CommonApplication;
import com.ebanswers.kitchendiary.constant.AppConstant;
import com.ebanswers.kitchendiary.mvp.view.base.LoginActivity;
import com.ebanswers.kitchendiary.utils.SPUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 设置
 */
public class SettingActivity extends CommonActivity {


    @BindView(R.id.settiing_title)
    TitleBar settiingTitle;
    @BindView(R.id.personal_data_ll)
    LinearLayout personalDataLl;

    @BindView(R.id.logout_bt)
    Button logoutBt;
    @BindView(R.id.message_notice_ll)
    LinearLayout messageNoticeLl;
    @BindView(R.id.modify_pass_ll)
    LinearLayout modifyPassLl;
    @BindView(R.id.username_tv)
    TextView usernameTv;
    @BindView(R.id.store_name_tv)
    TextView storeNameTv;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.settiing_title;
    }

    @Override
    protected void initView() {

        String username = (String) SPUtils.get(AppConstant.userNameTitle, "");
        if (!TextUtils.isEmpty(username)) {
            usernameTv.setText(username);
        }

        String storename = (String) SPUtils.get(AppConstant.STORE, "");
        if (!TextUtils.isEmpty(storename)) {
            storeNameTv.setText(storename);
        }

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


    @OnClick({R.id.personal_data_ll, R.id.message_notice_ll, R.id.modify_pass_ll, R.id.logout_bt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.personal_data_ll:
                startActivity(PersonalDataActivity.class);
                break;
            case R.id.message_notice_ll:
                break;
            case R.id.modify_pass_ll:
                startActivity(ModifyPasswordActivity.class);
                break;
            case R.id.logout_bt:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("退出登录").setMessage("是否确定要退出登录账户？").setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(LoginActivity.class);
                        CommonApplication.getInstance().removeAllActivity(SettingActivity.this);
                        SPUtils.setLogin(false);
                        finish();

                    }
                }).create().show();
                break;
        }
    }
}
