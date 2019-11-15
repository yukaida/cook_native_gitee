package com.ebanswers.kitchendiary.mvp.view.mine;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hjq.bar.TitleBar;
import com.hjq.toast.ToastUtils;
import com.ebanswers.kitchendiary.R;
import com.ebanswers.kitchendiary.common.CommonActivity;
import com.ebanswers.kitchendiary.constant.AppConstant;
import com.ebanswers.kitchendiary.mvp.presenter.ModifyPasswordPresenter;
import com.ebanswers.kitchendiary.mvp.contract.BaseView;
import com.ebanswers.kitchendiary.network.response.BaseResponse;
import com.ebanswers.kitchendiary.utils.SPUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ModifyPasswordActivity extends CommonActivity implements BaseView.ModifyPasswordView {

    @BindView(R.id.modify_password_title)
    TitleBar modifyPasswordTitle;
    @BindView(R.id.login_acount_tv)
    TextView loginAcountTv;
    @BindView(R.id.login_name_tv)
    TextView loginNameTv;
    @BindView(R.id.new_pass_tv)
    EditText newPassTv;
    @BindView(R.id.confirm_pass_tv)
    EditText confirmPassTv;
    @BindView(R.id.btn_modify_commit)
    Button btnModifyCommit;
    private ModifyPasswordPresenter modifyPasswordPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_modify_password;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.modify_password_title;
    }

    @Override
    protected void initView() {

        String username = (String) SPUtils.get(AppConstant.USER_NAME, "");
        if (!TextUtils.isEmpty(username)) {
            loginAcountTv.setText(username);
        }

        String usernamereal = (String) SPUtils.get(AppConstant.userNameTitle, "");
        if (!TextUtils.isEmpty(usernamereal)) {
            loginNameTv.setText(usernamereal);
        }

        modifyPasswordPresenter = new ModifyPasswordPresenter(this, this);


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

    @OnClick(R.id.btn_modify_commit)
    public void onViewClicked() {
        String newPassword = newPassTv.getText().toString().trim();
        String confirmPassword = confirmPassTv.getText().toString().trim();

        if (!TextUtils.isEmpty(newPassword)){
            if (newPassword.length() >= 6){
                if (!TextUtils.isEmpty(confirmPassword)){
                    if (confirmPassword.length() >= 6) {
                        if (confirmPassword.equals(newPassword)) {
                            String username = (String) SPUtils.get(AppConstant.USER_NAME, "");
                            if (!TextUtils.isEmpty(username)) {
                                modifyPasswordPresenter.modify(username, SPUtils.getToken(), newPassword, confirmPassword);
                            } else {
                                Toast.makeText(this, "用户为空", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            ToastUtils.show("两次密码不一致！");
                        }
                    }else {
                        ToastUtils.show("密码最少6位");
                    }
                }else {
                    ToastUtils.show("请再次输入新密码！");
                }
            }else {
                ToastUtils.show("密码最少6位");
            }

        }else {
            ToastUtils.show("新密码不能为空！");
        }

    }

    @Override
    public boolean isStatusBarEnabled() {
        return super.isStatusBarEnabled();
    }

    @Override
    public boolean statusBarDarkFont() {
        return super.statusBarDarkFont();
    }

    @Override
    public void setData(BaseResponse data) {

    }

    @Override
    public void netWorkError(String msg) {
        ToastUtils.show(msg);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        modifyPasswordPresenter = null;
    }
}
