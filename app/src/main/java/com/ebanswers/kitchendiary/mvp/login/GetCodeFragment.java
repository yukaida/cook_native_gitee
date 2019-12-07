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
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.ebanswers.kitchendiary.R;
import com.ebanswers.kitchendiary.bean.LoginResultInfo;
import com.ebanswers.kitchendiary.config.PhoneUserConfig;
import com.ebanswers.kitchendiary.config.QRCodeMap;
import com.ebanswers.kitchendiary.mvp.view.base.HomeActivity;
import com.ebanswers.kitchendiary.mvp.view.base.LoginActivity;
import com.ebanswers.kitchendiary.mvp.view.base.TestBindActivity;
import com.ebanswers.kitchendiary.mvp.view.mine.TestCodeinActivity;
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

import java.util.Random;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;


/**
 * @author caixd
 * @date 2019/9/18
 * PS:
 */
public class GetCodeFragment extends BaseLoginFragment implements TextWatcher {
    private static final String TAG = "GetCodeFragment";
    @BindView(R.id.tv_code_title)
    TextView tvCodeTitle;
    @BindView(R.id.tv_code_nation)
    TextView tvCodeNation;
    @BindView(R.id.et_code_account)
    EditText etCodeAccount;
    @BindView(R.id.layout_code_account)
    AutoLinearLayout layoutCodeAccount;
    @BindView(R.id.tv_get_code)
    TextView tvGetCode;
    @BindView(R.id.tv_code_tip)
    TextView tvCodeTip;
    //中国地区手机注册布局
    @BindView(R.id.all_phone_number_china)
    AutoLinearLayout layoutPhoneChina;
    //非大陆地区手机注册布局
    @BindView(R.id.all_phone_number_other)
    AutoLinearLayout layoutPhoneOther;
    //非大陆地区手机区号
    @BindView(R.id.tv_code_nation_other)
    TextView tvCodeNationOther;

    @BindView(R.id.et_code_account_other)
    EditText etCodeAccountOther;
    @BindView(R.id.et_pwd_password)
    EditText etPassword;
    @BindView(R.id.cb_pwd_password)
    CheckBox cbPassword;
    @BindView(R.id.et_pwd_password_confirm)
    EditText etPasswordConfirm;
    @BindView(R.id.cb_pwd_password_confirm)
    CheckBox cbPasswordConfirm;
    private String email_get;
    private int type;
    private boolean isPhone;
    private boolean isEmail;
    private String account;

    public static final int TYPE_LOGIN_PHONE = 1;
    //手机找回密码
    public static final int TYPE_FIND_PWD_PHONE = 2;
    //邮箱注册
    public static final int TYPE_EMAIL_REGISTER = 3;
    //邮箱找回密码
    public static final int TYPE_FIND_PWD_EMAIL = 4;

    public static GetCodeFragment newInstance(int type, String account) {
        GetCodeFragment fragment = new GetCodeFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        bundle.putString("account", account);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId()  {
        return R.layout.fragment_get_code;
    }

    @Override
    protected void onCreateViewNext(@Nullable Bundle savedInstanceState) {
        initListener();
        initFields();

    }

    private void initFields() {
        type = getArguments().getInt("type");
        account = getArguments().getString("account");
        isPhone = type == LoginActivity.TYPE_PHONE_CODE || type == LoginActivity.TYPE_FIND_PHONE_PWD;
        isEmail = type == LoginActivity.TYPE_EMAIL_REGISTER || type == LoginActivity.TYPE_FIND_EMAIL_PWD;
        tvCodeNation.setVisibility(isPhone ? View.VISIBLE : View.GONE);
        etCodeAccount.setHint(isPhone ? R.string.login_phone : R.string.login_input_email);
        etCodeAccount.setInputType(isPhone ? EditorInfo.TYPE_CLASS_NUMBER : EditorInfo.TYPE_CLASS_TEXT);
        tvCodeTip.setVisibility(type == LoginActivity.TYPE_PHONE_CODE ? View.VISIBLE : View.GONE);
        tvCodeTitle.setText(type == LoginActivity.TYPE_PHONE_CODE ? R.string.login_type_phone : (type == LoginActivity.TYPE_EMAIL_REGISTER ? R.string.login_email_register : R.string.login_find_pwd));
        if (!TextUtils.isEmpty(account)) {
            etCodeAccount.setText(account);
        }
    }

    private void initListener() {
        etCodeAccount.addTextChangedListener(this);
    }

    @OnClick({R.id.tv_get_code, R.id.tv_code_nation, R.id.tv_code_nation_other, R.id.tv_register})
    public void onClick(View view) {
        Log.d(GetCodeFragment.class.getSimpleName(), "onClick: " + view.getId());
        switch (view.getId()) {
            case R.id.tv_get_code://获取验证码按钮

                if (PhoneUserConfig.getNationCode() == 86) {
                    if ((isPhone && PhoneLoginUtils.checkPhoneValidity(etCodeAccount.getText().toString())) ||
                            (isEmail && PhoneLoginUtils.checkEmailValidity(etCodeAccount.getText().toString()))) {
                        if (!RetrofitTask.isCanSendCode()) {
                            ToastCustom.makeText(R.string.check_code_error).show();
                            return;
                        } else if (!NetworkUtils.checkNetwork(getActivity())) {
                            ToastCustom.makeText(R.string.network_error).show();
                            return;
                        } else {
                            sendCode();
                        }
                    }
                } else {

                    if ((isPhone && PhoneLoginUtils.checkPhoneValidity(etCodeAccount.getText().toString())) ||
                            (isEmail && PhoneLoginUtils.checkEmailValidity(etCodeAccount.getText().toString()))) {
                        if (!RetrofitTask.isCanSendCode()) {
                            ToastCustom.makeText(R.string.check_code_error).show();
                            return;
                        } else if (!NetworkUtils.checkNetwork(getActivity())) {
                            ToastCustom.makeText(R.string.network_error).show();
                            return;
                        } else {
                            //  非中国号码   判断是否绑定了邮箱   是（走A逻辑） 否（走B逻辑）
                            Log.d(TAG, "onClick: mark" + getAccount());
                            checkBind(getAccount());
                        }
                    }
                }
                break;
            case R.id.tv_code_nation:
            case R.id.tv_code_nation_other:
                startActivityForResult(new Intent(getActivity(), PickActivity.class), 111);
                break;
            case R.id.tv_register:
                checkSubmitInfo();
                break;
            default:
                break;
        }
    }


    private void sendCode() {
        Log.d("GetCodeFragment", "sendCode: " + type);
        switch (type) {
            case LoginActivity.TYPE_PHONE_CODE:
                LoginActivity.openActivity(getActivity(), LoginActivity.TYPE_PHONE_CODE, etCodeAccount.getText().toString());
                break;
            case LoginActivity.TYPE_FIND_PHONE_PWD:
            case LoginActivity.TYPE_FIND_EMAIL_PWD:
                ((LoginActivity) getActivity()).replaceFragment(CheckCodeFragment.newInstance(etCodeAccount.getText().toString(), CheckCodeFragment.TYPE_FIND_PWD));
                break;
            case LoginActivity.TYPE_EMAIL_REGISTER:
                checkExists(etCodeAccount.getText().toString());
                break;
        }
    }

    //获取邮箱验证码
    public void sendCheckCode(final String email) {
        if (!RetrofitTask.isCanSendCode()) {
            ToastCustom.makeText(R.string.check_code_error).show();
        } else {
            Toast.makeText(mContext, "正在请求验证码，请稍等", Toast.LENGTH_LONG).show();
            RetrofitTask.getPhoneCheckCode(657, email, new RetrofitTask.CallBack<String>() {
                @Override
                public void result(String s) {
                    if (!TextUtils.isEmpty(s)) {
                        try {
                            JSONObject json = new JSONObject(s);
                            String code = json.getString("code");
                            if ("0".equals(code)) {
                                Toast.makeText(getActivity(), "发送成功", Toast.LENGTH_SHORT).show();
                                //如果发送成功  跳到填入验证码界面
                                Intent intent = new Intent(getActivity(), TestCodeinActivity.class);
                                intent.putExtra("email", email);
                                intent.putExtra("phone", getAccount());
                                startActivity(intent);
                                Log.d(TAG, "result: mark" + getAccount());
//                                Intent intent = new Intent(BindedActivity.this, CodeInActivity.class);
//                                intent.putExtra("email", email);
//                                startActivity(intent);

                            }
                            Log.d("RegisterActivity", "code result: " + code);
                            if (!"0".equals(code)) {
                                if (json.has("errmsg")) {
                                    ToastCustom.makeText(json.getString("errmsg")).show();
                                } else {
                                    ToastCustom.makeText(R.string.check_code_send_fail).show();
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onError() {

                }
            });
        }
    }


    @OnCheckedChanged({R.id.cb_pwd_password_confirm, R.id.cb_pwd_password})
    public void onCheck(CompoundButton view, boolean isCheck) {
        if (view.getId() == R.id.cb_pwd_password) {
            if (isCheck) {
                etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        }
        if (view.getId() == R.id.cb_pwd_password_confirm) {
            if (isCheck) {
                etPasswordConfirm.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                etPasswordConfirm.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        }
    }

    /**
     * 判断账号是否存在
     * 使用现有登录接口(-4不存在, 其他存在)
     *
     * @param account
     */
    private void checkExists(String account) {
        showWaitDialog(R.string.dialog_waiting);
        RetrofitTask.accountLogin(account, String.valueOf(new Random().nextInt(999999)), new RetrofitTask.CallBack<String>() {
            @Override
            public void result(String s) {
                if (!TextUtils.isEmpty(s)) {
                    try {
                        JSONObject json = new JSONObject(s);
                        int code = json.getInt("code");
                        Log.d("GetCodeFragment", "checkAccount result: " + json);
                        closeWaitLoading();
                        if (code == -4) {
                            ((LoginActivity) getActivity()).replaceFragment(CheckCodeFragment.newInstance(etCodeAccount.getText().toString(), CheckCodeFragment.TYPE_SET_PWD));
                        } else {
                            ToastCustom.makeText(R.string.register_exist).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        closeWaitLoading();
                    }
                }
            }

            @Override
            public void onError() {
                closeWaitLoading();
            }
        });
    }


    //判断手机号是否绑定邮箱
    private void checkBind(String account) {
        showWaitDialog(R.string.dialog_waiting);
        RetrofitTask.Bind(account, new RetrofitTask.CallBack<String>() {
            @Override
            public void result(String s) {

                Log.d(TAG, "result: mark" + s);
                if (!TextUtils.isEmpty(s)) {
                    try {
                        JSONObject json = new JSONObject(s);
                        int code = json.getInt("code");
                        String email = json.getString("email");
                        Log.d("GetCodeFragment", "checkAccount result: " + json);
                        closeWaitLoading();
                        if (code == 2) {
                            getBindCode(getAccount(), email);
                        } else if (code == 1) {
                            Intent intent2 = new Intent(getActivity(), TestBindActivity.class);
                            intent2.putExtra("phone", getAccount());
                            startActivity(intent2);
                        } else {
                            ToastCustom.makeText(R.string.register_exist).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        closeWaitLoading();
                    }
                }
            }

            @Override
            public void onError() {
                closeWaitLoading();
                Log.d(TAG, "onError: mark");
            }
        });
    }

    //手机号没绑定邮箱 获取验证码
    private void getBindCode(final String phone, final String email) {
        RetrofitTask.getBindCode(phone,email, new RetrofitTask.CallBack<String>() {
            @Override
            public void result(String s) {
                Log.d(TAG, "result: mark"+s);
                if (!TextUtils.isEmpty(s)) {
                    try {
                        JSONObject json = new JSONObject(s);
                        String code = json.getString("code");
                        Log.d("TestBindActivity", "checkAccount result: mark" + json);
                        if ("true".equals(code)) {
                            Toast.makeText(getActivity(), "发送成功", Toast.LENGTH_SHORT).show();
                            // 跳转到填入验证码界面
                            Intent intent = new Intent(getActivity(), TestCodeinActivity.class);
                            intent.putExtra("phone", phone);
                            intent.putExtra("email", email);
                            startActivity(intent);

                        } else {
                            ToastCustom.makeText(R.string.register_exist).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onError() {
                Log.d(TAG, "onError:mark ");
            }
        });
    }
    public void setAccount(String account) {
        etCodeAccount.setText(account);
    }

    public String getAccount() {
        return etCodeAccount.getText().toString();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (type == LoginActivity.TYPE_EMAIL_REGISTER) {
            changeNationalCodeView(true);
            return;
        }
        if (type == LoginActivity.TYPE_FIND_EMAIL_PWD) {
            changeNationalCodeView(true);
            return;
        }
        if (PhoneUserConfig.getNationCode() != 86 && type != LoginActivity.TYPE_FIND_PHONE_PWD) {
            changeNationalCodeView(false);
            return;
        } else {
            changeNationalCodeView(true);
            return;
        }
    }

    public void changeNationalCodeView(boolean isCN) {
        Log.d(TAG, "changeNationalCodeView: ");
        if (isCN) {
            tvCodeNation.setText("+" + PhoneUserConfig.getNationCode());
        } else {
            tvCodeNationOther.setText("+" + PhoneUserConfig.getNationCode());
        }
        layoutPhoneChina.setVisibility(isCN ? View.VISIBLE : View.GONE);
        layoutPhoneOther.setVisibility(isCN ? View.GONE : View.VISIBLE);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        Log.d("GetCodeFragment", "onTextChanged: " + etCodeAccount.length() + "," + etCodeAccount.getText());
        tvGetCode.setEnabled(!TextUtils.isEmpty(etCodeAccount.getText()));
//        layoutCodeAccount.setSelected(!TextUtils.isEmpty(etCodeAccount.getText()));
//        etCodeAccount.setSelection(etCodeAccount.length());
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

    /**
     * 验证提交的信息
     */
    public void checkSubmitInfo() {
        String phoneNumber = etCodeAccountOther.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String passwordConfirm = etPasswordConfirm.getText().toString().trim();
        if (TextUtils.isEmpty(phoneNumber)) {
            ToastCustom.makeText(R.string.phone_number_is_null).show();
            return;
        }
        if (TextUtils.isEmpty(password) || TextUtils.isEmpty(passwordConfirm)) {
            ToastCustom.makeText(R.string.password_is_null).show();
            return;
        }
        if (!password.equals(passwordConfirm)) {
            ToastCustom.makeText(R.string.login_pwd_error).show();
            return;
        }
        if (password.length() < 6 || password.length() > 18) {
            ToastCustom.makeText(R.string.login_password_hint).show();
            return;
        }
        if (password.equals(passwordConfirm)) {
            //注册
            submit(phoneNumber, password);
        }
    }

    /**
     * 提交注册信息
     *
     * @param account
     * @param pwd
     */
    private void submit(String account, String pwd) {
        if (!NetworkUtils.checkNetwork(getActivity())) {
            ToastCustom.makeText(R.string.check_network).show();
        } else {
            closeWaitLoading();
            showWaitDialog(R.string.register);
            registerAccount(account, pwd);
        }
    }

    /**
     * 注册账号
     *
     * @param account
     * @param pwd
     */
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

    /**
     * 获取此号码用户的信息,跳转到主页
     *
     * @param account
     */
    private void getPhoneUserInfo(final String account) {
        RetrofitTask.postPhoneUserInfo(account, new RetrofitTask.CallBack<LoginResultInfo>() {
            @Override
            public void result(LoginResultInfo loginResultInfo) {
                if (loginResultInfo != null) {
                    if (loginResultInfo.getCode() == 0){
                        SPUtils.setLogin(true);
                                         /*   if (!TextUtils.isEmpty(data.get())) {
                                                SPUtils.put(AppConstant.USER_NAME, data.getMy_name());
                                            }
                                            if (!TextUtils.isEmpty(data.getOpenid())) {
                                                SPUtils.put(AppConstant.USER_ID, data.getOpenid());
                                            }*/
                    }
                    Intent intent = new Intent(getActivity(), HomeActivity.class);
                    intent.putExtra("open_id", loginResultInfo.getMsg());
                    startActivity(intent);
                    closeWaitLoading();
                }
            }

            @Override
            public void onError() {
                closeWaitLoading();
                ToastCustom.makeText(R.string.register_failed).show();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
