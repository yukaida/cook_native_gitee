package com.ebanswers.kitchendiary.mvp.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.ebanswers.kitchendiary.R;
import com.ebanswers.kitchendiary.bean.LoginResultInfo;
import com.ebanswers.kitchendiary.config.PhoneUserConfig;
import com.ebanswers.kitchendiary.config.QRCodeMap;
import com.ebanswers.kitchendiary.constant.AppConstant;
import com.ebanswers.kitchendiary.eventbus.Event;
import com.ebanswers.kitchendiary.eventbus.EventBusUtil;
import com.ebanswers.kitchendiary.mvp.view.base.HomeActivity;
import com.ebanswers.kitchendiary.mvp.view.base.LoginActivity;
import com.ebanswers.kitchendiary.retrofit.RetrofitTask;
import com.ebanswers.kitchendiary.utils.NetworkUtils;
import com.ebanswers.kitchendiary.utils.PhoneLoginUtils;
import com.ebanswers.kitchendiary.utils.SPUtils;
import com.ebanswers.kitchendiary.utils.ToastCustom;
import com.sahooz.library.Country;
import com.sahooz.library.PickActivity;
import com.zhy.autolayout.AutoLinearLayout;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

import static com.chad.library.adapter.base.listener.SimpleClickListener.TAG;


/**
 * @author caixd
 * @date 2019/9/18
 * PS: 账号密码登录 包括手机账号密码登录和邮箱密码登录
 */
public class PasswordLoginFragment extends BaseLoginFragment implements TextWatcher {
    @BindView(R.id.tv_login_title)
    TextView tvLoginTitle;
    @BindView(R.id.tv_pwd_nation)
    TextView tvPwdNation;
    @BindView(R.id.et_pwd_account)
    EditText etPwdAccount;
    @BindView(R.id.layout_pwd_account)
    AutoLinearLayout layoutPwdAccount;
    @BindView(R.id.et_pwd_password)
    EditText etPwdPassword;
    @BindView(R.id.cb_pwd_password)
    CheckBox cbPwdPassword;
    @BindView(R.id.layout_pwd_password)
    AutoLinearLayout layoutPwdPassword;
    @BindView(R.id.tv_pwd_login)
    TextView tvPwdLogin;
    @BindView(R.id.tv_login_register)
    TextView tvLoginRegister;


    //忘记密码按钮
    @BindView(R.id.tv_pwd_forget)
    TextView tvPwdForget;
    private int loginType;
    //手机号码密码登录
    public static final int LOGIN_PHONE = 233;
    //邮箱密码登录
    public static final int LOGIN_EMAIL = 255;
    private String validAccount, account;

    public static PasswordLoginFragment newInstance(int type, String account) {
        PasswordLoginFragment fragment = new PasswordLoginFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("loginType", type);
        bundle.putString("account", account);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
            return R.layout.fragment_login_pwd;
    }

    @Override
    protected void onCreateViewNext(@Nullable Bundle savedInstanceState) {
        initListener();

        initFields();
    }

    private void initFields() {
        loginType = getArguments().getInt("loginType");
        account = getArguments().getString("account");
        Log.d("PasswordLoginFragment", "initFields: " + loginType);
        tvLoginTitle.setText(loginType == LOGIN_PHONE ? R.string.login_type_password : R.string.login_email);
        tvPwdNation.setVisibility(loginType == LOGIN_PHONE ? View.VISIBLE : View.GONE);
        etPwdAccount.setHint(loginType == LOGIN_PHONE ? R.string.login_phone : R.string.login_input_email);
        tvLoginRegister.setVisibility(loginType == LOGIN_PHONE ? View.GONE : View.VISIBLE);
        etPwdAccount.setInputType(loginType == LOGIN_PHONE ? EditorInfo.TYPE_CLASS_NUMBER : EditorInfo.TYPE_CLASS_TEXT);
        if(!TextUtils.isEmpty(account)){
            etPwdAccount.setText(account);
        }
    }

    private void initListener() {
        etPwdAccount.addTextChangedListener(this);
        etPwdPassword.addTextChangedListener(this);
    }

    public void setAccount(String account) {
        etPwdAccount.setText(account);
    }

    public String getAccount() {
        return etPwdAccount.getText().toString();
    }

    @Override
    public void onResume() {
        super.onResume();
        tvPwdNation.setText("+" + PhoneUserConfig.getNationCode());
    }

    @OnClick({R.id.tv_pwd_login, R.id.tv_login_register, R.id.tv_pwd_forget, R.id.tv_pwd_nation})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_pwd_login:
                if ((loginType == LOGIN_PHONE && PhoneLoginUtils.checkPhoneValidity(etPwdAccount.getText().toString())) ||
                        (loginType == LOGIN_EMAIL && PhoneLoginUtils.checkEmailValidity(etPwdAccount.getText().toString()))) {
                    if (PhoneLoginUtils.checkPasswordValidity(etPwdPassword.getText().toString())) {
                        phoneLogin(etPwdAccount.getText().toString(), etPwdPassword.getText().toString());
                    }
                }
                break;
            case R.id.tv_login_register:
                if (loginType == LOGIN_EMAIL) {
                    validAccount = PhoneLoginUtils.checkEmail(etPwdAccount.getText().toString()) ? etPwdAccount.getText().toString() : "";
                    LoginActivity.openActivity(getActivity(), LoginActivity.TYPE_EMAIL_REGISTER, validAccount);
                }
                break;
            case R.id.tv_pwd_forget://忘记密码按钮
//                String sta=getResources().getConfiguration().locale.getLanguage();
//                Log.d(TAG, "getLayoutId:  系统语言为 "+sta);
//                if (sta.equals("zh")) {
                    validAccount = (loginType == LOGIN_PHONE && PhoneLoginUtils.checkCellphone(etPwdAccount.getText().toString())) || (loginType == LOGIN_EMAIL && PhoneLoginUtils.checkEmail(etPwdAccount.getText().toString())) ? etPwdAccount.getText().toString() : "";
                    Log.d("PasswordLoginFragment", "forget: " + validAccount);
                    LoginActivity.openActivity(getActivity(), loginType == LOGIN_PHONE ? LoginActivity.TYPE_FIND_PHONE_PWD : LoginActivity.TYPE_FIND_EMAIL_PWD, validAccount);
//                } else {
//                    Log.d(TAG, "onClick: mark " + getActivity().toString());
//                    Intent intent = new Intent(getActivity(), PhoneInActivity.class);
//                    startActivity(intent);
//                }

                break;
            case R.id.tv_pwd_nation:
                startActivityForResult(new Intent(getActivity(), PickActivity.class), 111);
                break;
            default:
        }

    }

    @OnCheckedChanged(R.id.cb_pwd_password)
    public void onCheck(CompoundButton view, boolean isCheck) {
        if (isCheck) {
            etPwdPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            etPwdPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
        Log.d("RegisterActivity", "onCheck: " + etPwdPassword.length());
        if (etPwdPassword.length() > 0) {
            etPwdPassword.setSelection(etPwdPassword.length());
        }
    }

    /**
     * 手机登录
     *
     * @param account
     * @param password
     */
    private void phoneLogin(String account, String password) {
        if (!NetworkUtils.checkNetwork(getActivity())) {
            ToastCustom.makeText(R.string.check_network).show();
            Log.d(TAG, "phoneLogin: mark");
        } else {
            showWaitDialog(R.string.login);
            oldApi(account, password);
        }
    }

    private void oldApi(final String phoneNumberStr, String password) {
        RetrofitTask.accountLogin(phoneNumberStr, password, new RetrofitTask.CallBack<String>() {
            @Override
            public void result(String s) {
                if (!TextUtils.isEmpty(s)) {
                    try {
                        JSONObject json = new JSONObject(s);
                        int code = json.getInt("code");
                        Log.d("WXEntryActivity", "login:  mark" + s);
                        if (code == 0) {

                            RetrofitTask.postPhoneUserInfo(phoneNumberStr, new RetrofitTask.CallBack<LoginResultInfo>() {
                                @Override
                                public void result(LoginResultInfo loginResultInfo) {
                                    if (loginResultInfo != null) {
                                        LoginResultInfo.DataBean data = loginResultInfo.getData();
                                        if (loginResultInfo.getCode() == 0){
                                            SPUtils.setLogin(true);
                                            if (!TextUtils.isEmpty(loginResultInfo.getMsg())) {
                                                SPUtils.put(AppConstant.USER_ID, loginResultInfo.getMsg());
                                            }
                                         /*   if (!TextUtils.isEmpty(data.get())) {
                                                SPUtils.put(AppConstant.USER_NAME, data.getMy_name());
                                            }
                                            if (!TextUtils.isEmpty(data.getOpenid())) {
                                                SPUtils.put(AppConstant.USER_ID, data.getOpenid());
                                            }*/
                                        }


                                        closeWaitLoading();
                                        Log.d("WXEntryActivity", "login result: token:" + loginResultInfo.getData().getToken() + ",openid:" + loginResultInfo.getMsg());
                                        Intent intent = new Intent(getActivity(), HomeActivity.class);
                                        if (getActivity() != null) {
                                            intent.putExtra("open_id", loginResultInfo.getMsg());
                                            SPUtils.put(AppConstant.USER_ID,loginResultInfo.getMsg());
                                            EventBusUtil.sendEvent(new Event(Event.EVENT_UPDATE_TOMINE,"我的"));
                                            startActivity(intent);
                                            getActivity().finish();
                                        }
                                    }
                                }

                                @Override
                                public void onError() {
                                    closeWaitLoading();
                                    ToastCustom.makeText(R.string.login_failed).show();
                                }
                            });
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
                ToastCustom.makeText(R.string.login_failed).show();
            }
        });
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
       tvPwdLogin.setEnabled(!TextUtils.isEmpty(etPwdAccount.getText()) && !TextUtils.isEmpty(etPwdPassword.getText()));
        /* layoutPwdAccount.setSelected(!TextUtils.isEmpty(etPwdAccount.getText()));
        layoutPwdPassword.setSelected(!TextUtils.isEmpty(etPwdPassword.getText()));
        etPwdAccount.setSelection(etPwdAccount.length());*/
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 111 && resultCode == Activity.RESULT_OK) {
            Country country = Country.fromJson(data.getStringExtra("country"));
            PhoneUserConfig.setNationCode(country.getCode());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
