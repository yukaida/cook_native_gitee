package com.ebanswers.kitchendiary.mvp.view.base;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hjq.toast.ToastUtils;
import com.ebanswers.baselibrary.utils.EditTextInputHelper;
import com.ebanswers.kitchendiary.R;
import com.ebanswers.kitchendiary.common.CommonActivity;
import com.ebanswers.kitchendiary.constant.AppConstant;
import com.ebanswers.kitchendiary.mvp.presenter.LoginPresenter;
import com.ebanswers.kitchendiary.mvp.contract.BaseView;
import com.ebanswers.kitchendiary.network.response.LoginResponse;
import com.ebanswers.kitchendiary.utils.LogUtils;
import com.ebanswers.kitchendiary.utils.SPUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.com.salsoft.sqlitestudioremote.SQLiteStudioService;

/**
 * desc   : 登录界面
 */
public class LoginActivity extends CommonActivity implements View.OnClickListener, BaseView.LoginView {

    @BindView(R.id.et_login_phone)
    EditText mPhoneView;
    @BindView(R.id.et_login_password)
    EditText mPasswordView;
    @BindView(R.id.btn_login_commit)
    Button mCommitView;
    @BindView(R.id.clear_iv)
    ImageView clearIv;
    @BindView(R.id.remenber_pass_iv)
    ImageView remenberPassIv;
    @BindView(R.id.tv_login_forget)
    TextView tvLoginForget;
    @BindView(R.id.agreement_tv)
    TextView agreementTv;
    @BindView(R.id.remenber_show_iv)
    ImageView remenberShowIv;
    @BindView(R.id.agreement_iv)
    ImageView agreementIv;
    @BindView(R.id.agreement_show_iv)
    ImageView agreementShowIv;
    @BindView(R.id.remenber_pass_rl)
    RelativeLayout remenberPassRl;
    @BindView(R.id.agreement_rl)
    RelativeLayout agreementRl;

    private EditTextInputHelper mEditTextInputHelper;
    private boolean isChecked = false;
    private LoginPresenter loginPresenter;
    private boolean remenberPass;
    private boolean agreementB;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected int getTitleBarId() {
        return 0;
    }

    @Override
    protected void initView() {
        remenberPass = SPUtils.getRemenberPass();
        agreementB = SPUtils.getAgreement();
        loginPresenter = new LoginPresenter(this, this);
        mCommitView.setOnClickListener(this);
        mEditTextInputHelper = new EditTextInputHelper(mCommitView, false);
        mEditTextInputHelper.addViews(mPhoneView, mPasswordView);
        setCheckNetWork(true);

        String username = (String) SPUtils.get(AppConstant.USER_NAME, "");
        if (!TextUtils.isEmpty(username)) {
            mPhoneView.setText(username);
        }

        if (remenberPass) {
            String pass = (String) SPUtils.get(AppConstant.PASS, "");
            if (!TextUtils.isEmpty(pass)) {
                mPasswordView.setText(pass);
            }
            remenberShowIv.setVisibility(View.VISIBLE);
        } else {
            mPasswordView.setText("");
            remenberShowIv.setVisibility(View.GONE);
        }

        if (agreementB) {
            SPUtils.setAgreement(false);
            agreementShowIv.setVisibility(View.VISIBLE);
            LogUtils.d("不同意；");
        } else {
            SPUtils.setAgreement(true);
            agreementShowIv.setVisibility(View.GONE);
            LogUtils.d("同意；");
        }


        SQLiteStudioService.instance().start(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onDestroy() {
        mEditTextInputHelper.removeViews();
        super.onDestroy();
        SQLiteStudioService.instance().stop();
    }

    @Override
    public boolean isSupportSwipeBack() {
        //不使用侧滑功能
        return !super.isSupportSwipeBack();
    }

    /**
     * {@link View.OnClickListener}
     */
    @Override
    public void onClick(View v) {
        if (v == mCommitView) {
            String phone = mPhoneView.getText().toString();
            String password = mPasswordView.getText().toString();
            if (agreementB){
                if (password.length() >= 6){
                    loginPresenter.login(phone, password, remenberPass);
                }else {
                    ToastUtils.show("密码最少6位");
                }

            }else {
                ToastUtils.show("请同意用户隐私证劵条款");
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.clear_iv, R.id.remenber_pass_rl, R.id.agreement_rl, R.id.agreement_tv,R.id.login_forget_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.clear_iv:
                mPhoneView.setText("");
                break;
           /* case R.id.pass_show_iv:
                if (!isChecked) {
                    //如果选中，显示密码
                    mPasswordView.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    isChecked = true;
                    passShowIv.setBackground(getResources().getDrawable(R.mipmap.icon_eye_show));
                } else {
                    //否则隐藏密码
                    mPasswordView.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    isChecked = false;
                    passShowIv.setBackground(getResources().getDrawable(R.mipmap.icon_close_eye));
                }
                break;*/

            case R.id.remenber_pass_rl:
                if (remenberPass) {
                    SPUtils.setRemenberPass(false);
                    remenberPass = false;
                    SPUtils.put(AppConstant.PASS, "");
                    remenberShowIv.setVisibility(View.GONE);
                    LogUtils.d("不记住密码；");
                } else {
                    SPUtils.setRemenberPass(true);
                    remenberPass = true;
                    remenberShowIv.setVisibility(View.VISIBLE);
                    LogUtils.d("记住密码；");
                }
                break;
            case R.id.agreement_rl:
                if (agreementB) {
                    SPUtils.setAgreement(false);
                    agreementB = false;
                    agreementShowIv.setVisibility(View.GONE);
                    LogUtils.d("不同意；");
                } else {
                    SPUtils.setAgreement(true);
                    agreementB = true;
                    agreementShowIv.setVisibility(View.VISIBLE);
                    LogUtils.d("同意；");
                }
                break;
            case R.id.agreement_tv:

                Intent intent = new Intent(this, WebActivity.class);
                intent.putExtra("url", "file:///android_asset/协议.html");
                startActivity(intent);
//                intent.putExtra("url","file:///android_asset/平安付掌柜支付协议.html");
//                startActivity(AgreementActivity.class);
                break;
            case R.id.login_forget_tv:
                startActivity(ForgetPassActivity.class);
                break;
        }
    }

    @Override
    public void setData(LoginResponse data) {
        SPUtils.setLogin(true);
        startActivity(HomeActivity.class);
        finish();
    }

    @Override
    public void netWorkError(String result) {
        Toast.makeText(this, result, Toast.LENGTH_LONG).show();
    }

}