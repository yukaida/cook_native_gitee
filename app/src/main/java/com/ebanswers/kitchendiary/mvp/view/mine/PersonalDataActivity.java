package com.ebanswers.kitchendiary.mvp.view.mine;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hjq.bar.TitleBar;
import com.ebanswers.kitchendiary.R;
import com.ebanswers.kitchendiary.common.CommonActivity;
import com.ebanswers.kitchendiary.constant.AppConstant;
import com.ebanswers.kitchendiary.utils.SPUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 个人信息
 */
public class PersonalDataActivity extends CommonActivity {

    @BindView(R.id.personal_data_title)
    TitleBar personalDataTitle;
    @BindView(R.id.personal_data_ll)
    LinearLayout personalDataLl;
    @BindView(R.id.store_name_tv)
    TextView storeNameTv;
    @BindView(R.id.account_tv)
    TextView accountTv;
    @BindView(R.id.contact_tv)
    TextView contactTv;
    @BindView(R.id.mobile_num_tv)
    TextView mobileNumTv;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_personal_data;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.personal_data_title;
    }

    @Override
    protected void initView() {

        String username = (String) SPUtils.get(AppConstant.userNameTitle, "");
        if (!TextUtils.isEmpty(username)) {
            contactTv.setText(username);
        }

        String account = (String) SPUtils.get(AppConstant.USER_NAME, "");
        if (!TextUtils.isEmpty(account)) {
            accountTv.setText(account);
        }

        String storename = (String) SPUtils.get(AppConstant.STORE, "");
        if (!TextUtils.isEmpty(storename)) {
            storeNameTv.setText(storename);
        }


        String mobile = (String) SPUtils.get(AppConstant.MOBILE, "");
        if (!TextUtils.isEmpty(mobile)) {
            mobileNumTv.setText(mobile);
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


    @Override
    public boolean isStatusBarEnabled() {
        return super.isStatusBarEnabled();
    }

    @Override
    public boolean statusBarDarkFont() {
        return super.statusBarDarkFont();
    }
}
