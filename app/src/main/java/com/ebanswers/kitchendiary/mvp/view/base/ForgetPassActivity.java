package com.ebanswers.kitchendiary.mvp.view.base;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.hjq.bar.TitleBar;
import com.hjq.toast.ToastUtils;
import com.ebanswers.baselibrary.widget.CountdownView;
import com.ebanswers.kitchendiary.R;
import com.ebanswers.kitchendiary.common.CommonActivity;
import com.ebanswers.kitchendiary.mvp.presenter.ForgetPasswordPresenter;
import com.ebanswers.kitchendiary.mvp.contract.BaseView;
import com.ebanswers.kitchendiary.network.response.BaseResponse;
import com.ebanswers.kitchendiary.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ForgetPassActivity extends CommonActivity implements BaseView.ForgetPassView {

    @BindView(R.id.forget_password_title)
    TitleBar forgetPasswordTitle;
    @BindView(R.id.code_et)
    EditText codeEt;
    @BindView(R.id.send_code_cdv)
    CountdownView sendCodeCdv;
    @BindView(R.id.btn_modify_commit)
    Button btnModifyCommit;
    @BindView(R.id.clear_iv)
    ImageView clearIv;
    @BindView(R.id.mobile_num_et)
    EditText mobileNumEt;
    @BindView(R.id.new_pass_et)
    EditText newPassEt;
    @BindView(R.id.confirm_pass_et)
    EditText confirmPassEt;
    private ForgetPasswordPresenter presenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_forget_pass;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.forget_password_title;
    }

    @Override
    protected void initView() {
        presenter = new ForgetPasswordPresenter(this, this);
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
    public void setData(BaseResponse data) {

    }

    @Override
    public void netWorkError(String msg) {
        ToastUtils.show(msg);
    }

    @OnClick({R.id.clear_iv, R.id.send_code_cdv, R.id.btn_modify_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.clear_iv:
                mobileNumEt.setText("");
                break;
            case R.id.send_code_cdv:
                String mobileNum = mobileNumEt.getText().toString();
                if (Utils.isChinaMobileNO(mobileNum)) {
                    presenter.sendCode(mobileNum);
                } else {
                    ToastUtils.show(getString(R.string.phone_input_error));
                    sendCodeCdv.resetState();
                }

                break;
            case R.id.btn_modify_commit:
                String mobileNum1 = mobileNumEt.getText().toString();
                String code = codeEt.getText().toString();
                String newPassword = newPassEt.getText().toString().trim();
                String confirmPassword = confirmPassEt.getText().toString().trim();
                if (Utils.isChinaMobileNO(mobileNum1)) {
                    if (!TextUtils.isEmpty(newPassword)) {
                        if (newPassword.length() >= 6) {
                            if (!TextUtils.isEmpty(confirmPassword)) {
                                if (confirmPassword.equals(newPassword)) {
                                    if (confirmPassword.equals(newPassword)) {
                                        if (!TextUtils.isEmpty(code)) {
                                            presenter.modify(mobileNum1, code, newPassword, confirmPassword);
                                        } else {
                                            ToastUtils.show("验证码不能为空");
                                        }

                                    } else {
                                        ToastUtils.show("两次密码不一致！");
                                    }
                                } else {
                                    ToastUtils.show("密码最少6位");
                                }
                            } else {
                                ToastUtils.show("请再次输入新密码！");
                            }
                        } else {
                            ToastUtils.show("密码最少6位");
                        }

                    }else {
                        ToastUtils.show("新密码不能为空！");
                    }

                } else {
                    ToastUtils.show(getString(R.string.phone_input_error));
                }
                break;
        }
    }
}
