package com.ebanswers.kitchendiary.mvp.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.ebanswers.kitchendiary.R;
import com.ebanswers.kitchendiary.bean.LoginResultInfo;
import com.ebanswers.kitchendiary.config.QRCodeMap;
import com.ebanswers.kitchendiary.mvp.view.base.HomeActivity;
import com.ebanswers.kitchendiary.mvp.view.base.LoginActivity;
import com.ebanswers.kitchendiary.retrofit.RetrofitTask;
import com.ebanswers.kitchendiary.utils.PhoneLoginUtils;
import com.ebanswers.kitchendiary.utils.SPUtils;
import com.ebanswers.kitchendiary.utils.ToastCustom;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author caixd
 * @date 2019/9/18
 * PS: 设置密码和重置密码
 */
public class PasswordSetFragment extends BaseLoginFragment implements TextWatcher {
    @BindView(R.id.tv_pwd_title)
    TextView tvPwdTitle;
    @BindView(R.id.et_pwd_first)
    EditText etPwdFirst;
    @BindView(R.id.et_pwd_second)
    EditText etPwdSecond;
    @BindView(R.id.tv_pwd_confirm)
    TextView tvPwdConfirm;
    private int type;
    private String account;
//    //新密码
//    public static final int TYPE_SET_PWD = 233;
//    //重置密码
//    public static final int TYPE_RESET_PWD = 255;

    public static PasswordSetFragment newInstance(String account, int type) {
        PasswordSetFragment fragment = new PasswordSetFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        bundle.putString("account", account);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_set_pwd;
    }

    @Override
    protected void onCreateViewNext(@Nullable Bundle savedInstanceState) {
        initFields();
        initListener();
    }

    private void initFields() {
        type = getArguments().getInt("type");
        account = getArguments().getString("account");
        Log.d("PasswordSetFragment", "initFields: " + type + "," + account);
        tvPwdTitle.setText(type == LoginActivity.TYPE_SET_PWD ? R.string.login_set_pwd : R.string.login_set_new_pwd);
        etPwdFirst.setHint(type == LoginActivity.TYPE_SET_PWD ? R.string.login_set_pwd : R.string.login_input_new_pwd);
        etPwdSecond.setHint(type == LoginActivity.TYPE_SET_PWD ? R.string.login_confirm_pwd : R.string.login_input_confirm_pwd);
    }

    private void initListener() {
        etPwdFirst.addTextChangedListener(this);
        etPwdSecond.addTextChangedListener(this);
    }

    @OnClick({R.id.tv_pwd_confirm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_pwd_confirm:
                if (!TextUtils.equals(etPwdFirst.getText().toString(), etPwdSecond.getText().toString())) {
                    ToastCustom.makeText(R.string.login_pwd_error).show();
                } else if (!PhoneLoginUtils.checkPassword(etPwdFirst.getText().toString())) {
                    ToastCustom.makeText(R.string.login_password_hint).show();
                } else if (type == LoginActivity.TYPE_SET_PWD) {
                    showWaitDialog(R.string.register);
                    registerAccount(account, etPwdFirst.getText().toString());
                } else {
                    showWaitDialog(R.string.password_changing);
                    changePassword(account, etPwdFirst.getText().toString());
                }
                break;
            default:
        }
    }

    private void registerAccount(final String account, String pwd) {
        RetrofitTask.registerAccount(account, pwd, new RetrofitTask.CallBack<String>() {
            @Override
            public void result(String s) {
                if (!TextUtils.isEmpty(s)) {
                    try {
                        JSONObject json = new JSONObject(s);
                        int code = json.getInt("code");
                        Log.d("RegisterActivity", "register result: " + code);
                        if (code == 0) {
                            getPhoneUserInfo(account);
                        } else {
                            closeWaitLoading();
                            ToastCustom.makeText(QRCodeMap.getMsg(code)).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onError() {
                closeWaitLoading();
                ToastCustom.makeText(R.string.register_failed).show();
            }
        });
    }

    private void changePassword(final String account, String pwd) {
        RetrofitTask.changePassword(account, pwd, new RetrofitTask.CallBack<String>() {
            @Override
            public void result(String s) {
                if (!TextUtils.isEmpty(s)) {
                    try {
                        JSONObject json = new JSONObject(s);
                        int code = json.getInt("code");
                        Log.d("RegisterActivity", "changePassword: " + code);
                        if (code == 0) {
                            getPhoneUserInfo(account);
                        } else {
                            closeWaitLoading();
                            ToastCustom.makeText(QRCodeMap.getMsg(code)).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onError() {
                closeWaitLoading();
                ToastCustom.makeText(R.string.change_failed).show();
            }
        });
    }

    private void getPhoneUserInfo(final String account) {
        RetrofitTask.postPhoneUserInfo(account, new RetrofitTask.CallBack<LoginResultInfo>() {
            @Override
            public void result(LoginResultInfo loginResultInfo) {
                if (loginResultInfo != null) {
                    closeWaitLoading();
                    if (loginResultInfo.getCode() == 0){
                        SPUtils.setLogin(true);
                                         /*   if (!TextUtils.isEmpty(data.get())) {
                                                SPUtils.put(AppConstant.USER_NAME, data.getMy_name());
                                            }
                                            if (!TextUtils.isEmpty(data.getOpenid())) {
                                                SPUtils.put(AppConstant.USER_ID, data.getOpenid());
                                            }*/
                    }
//                    WechatUserConfig.clear(mContext);
//                    ((LoginActivity) getActivity()).setAliasAndTag(loginResultInfo.getMsg());
//                    ((LoginActivity) getActivity()).clearCookie();//切换用户后需要清空cookie, 否则我的界面用户名不更新
//                    PhoneUserConfig.setPhoneNumber(account);
//                    SPUtils.put(mContext, Constans.DEVICE_TOKEN, loginResultInfo.getData().getToken());
//                    WechatUserConfig.setWechatPublicOpenId(KitchenDiaryApplication.getInstance(), loginResultInfo.getMsg());
                    Log.d("RegisterActivity", "user info: token:" + loginResultInfo.getData().getToken() + ",openid:" + loginResultInfo.getMsg());
                    Intent intent = new Intent(getActivity(), HomeActivity.class);
                    intent.putExtra("open_id", loginResultInfo.getMsg());
                    startActivity(intent);
                    getActivity().finish();
                }
            }

            @Override
            public void onError() {
                closeWaitLoading();
                ToastCustom.makeText(type == LoginActivity.TYPE_SET_PWD ? R.string.register_failed : R.string.change_failed).show();
            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
//        etPwdFirst.setSelected(!TextUtils.isEmpty(etPwdFirst.getText()));
//        etPwdSecond.setSelected(!TextUtils.isEmpty(etPwdSecond.getText()));
        tvPwdConfirm.setEnabled(!TextUtils.isEmpty(etPwdFirst.getText()) && !TextUtils.isEmpty(etPwdSecond.getText()));
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
