package com.ebanswers.kitchendiary.mvp.view.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.LinkMovementMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.CookieManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ebanswers.kitchendiary.R;
import com.ebanswers.kitchendiary.bean.LoginResultInfo;
import com.ebanswers.kitchendiary.config.Constans;
import com.ebanswers.kitchendiary.config.PhoneUserConfig;
import com.ebanswers.kitchendiary.config.QRCodeMap;
import com.ebanswers.kitchendiary.retrofit.RetrofitTask;
import com.ebanswers.kitchendiary.utils.LanguageUtil;
import com.ebanswers.kitchendiary.utils.NetworkUtils;
import com.ebanswers.kitchendiary.utils.PhoneLoginUtils;
import com.ebanswers.kitchendiary.utils.ToastCustom;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.sahooz.library.Country;
import com.sahooz.library.PickActivity;
import com.zhy.autolayout.AutoLinearLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

import static com.qmuiteam.qmui.widget.dialog.QMUITipDialog.Builder.ICON_TYPE_LOADING;

public class RegisterActivity extends MainBaseActivity implements TextWatcher    {

    @BindView(R.id.id_img_title_back)
    ImageView idImgTitleBack;
    @BindView(R.id.id_tv_title_name)
    TextView idTvTitleName;
    @BindView(R.id.id_img_title_setting)
    ImageView idImgTitleSetting;
    @BindView(R.id.et_register_account)
    EditText etRegisterAccount;
    @BindView(R.id.layout_register_phone)
    AutoLinearLayout layoutRegisterPhone;
    @BindView(R.id.et_register_code)
    EditText etRegisterCode;
    @BindView(R.id.tv_register_code)
    TextView tvRegisterCode;
    @BindView(R.id.layout_register_code)
    AutoLinearLayout layoutRegisterCode;
    @BindView(R.id.et_register_password)
    EditText etRegisterPassword;
    @BindView(R.id.cb_register_password)
    CheckBox cbRegisterPassword;
    @BindView(R.id.layout_register_password)
    AutoLinearLayout layoutRegisterPassword;
    @BindView(R.id.tv_register_submit)
    TextView tvRegisterSubmit;
    @BindView(R.id.tv_register_nation)
    TextView tvRegisterNation;
    @BindView(R.id.tv_register_account)
    TextView tvRegisterAccount;
    @BindView(R.id.tv_register_read)
    TextView tvRegisterRead;
//    @BindView(R.id.et_register_tip)
//    TextView tvRegisterTip;


    private CountDownTimer timer;
    public static final int TYPE_REGISTER = 795;
    public static final int TYPE_FORGET = 668;
    public static final int TYPE_PHOE = 639;
    public static final int TYPE_EMAIL = 657;
    public static final int TYPE_PASSWORD = 658;
    private int type;
    private QMUITipDialog dialog;
    private Subscription delayClose;
    private int loginType;//手机登录或邮箱登录

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void onCreateNext(@Nullable Bundle savedInstanceState) {
        initViews();
        initListener();
    }

    private void initListener() {
        etRegisterAccount.addTextChangedListener(this);
        etRegisterPassword.addTextChangedListener(this);
        etRegisterCode.addTextChangedListener(this);
//        etRegisterAccount.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                Log.d("RegisterActivity", "onFocusChange: " + hasFocus);
//                String account = etRegisterAccount.getText().toString().trim();
//                if (!hasFocus && !TextUtils.isEmpty(account)) {
//                    if (!PhoneLoginUtils.checkCellphone(account)) {
//                        tvRegisterTip.setVisibility(View.VISIBLE);
//                        tvRegisterTip.setText(LanguageUtil.getInstance().getStringById(R.string.phone_number_is_error));
//                    } else {
//                        tvRegisterTip.setVisibility(View.GONE);
//                        checkAccount(account, String.valueOf(new Random().nextInt(999999)));
//                    }
//                }
//            }
//        });
    }

    private void initViews() {
        tvRegisterNation.setText("+" + PhoneUserConfig.getNationCode());
        type = getIntent().getIntExtra("type", TYPE_REGISTER);
        loginType = getIntent().getIntExtra("login_type", TYPE_PHOE);
        idImgTitleSetting.setVisibility(View.INVISIBLE);
        if (type == TYPE_FORGET) {
            idTvTitleName.setText(LanguageUtil.getInstance().getStringById(R.string.login_change_password));
        } else {
            idTvTitleName.setText(LanguageUtil.getInstance().getStringById(R.string.login_register_title));
        }
        tvRegisterAccount.setText(loginType == TYPE_PHOE ? R.string.login_phone : R.string.login_email);
        etRegisterAccount.setHint(loginType == TYPE_PHOE ? R.string.login_phone : R.string.login_email);
        tvRegisterNation.setVisibility(loginType == TYPE_PHOE ? View.VISIBLE : View.GONE);
        showPolicy();
    }

    private void showPolicy() {
        SpannableString spannableString = new SpannableString(tvRegisterRead.getText());
        int agreementStart = 8;
//        int agreementEnd = LanguageUtil.getInstance().isEnglish() ? 25 : 12;
//        int privacyStart = LanguageUtil.getInstance().isEnglish() ? 30 : 13;
        int agreementEnd = LanguageUtil.getInstance().isChinease() ? 12 : 25;
        int privacyStart = LanguageUtil.getInstance().isChinease() ? 13 : 30;
        int privacyEnd = tvRegisterRead.getText().length();
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.primary_dark)), agreementStart, agreementEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.primary_dark)), privacyStart, privacyEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Intent intent1 = new Intent(RegisterActivity.this, FullActivity.class);
                intent1.putExtra("type", FullActivity.TYPE_LOGIN_POLICY);
                intent1.putExtra("url", Constans.URL_PROTOCOL);
                startActivity(intent1);
            }
        }, agreementStart, agreementEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Intent intent1 = new Intent(RegisterActivity.this, FullActivity.class);
                intent1.putExtra("type", FullActivity.TYPE_LOGIN_POLICY);
                intent1.putExtra("url", Constans.URL_PRIVACY);
                startActivity(intent1);
            }
        }, privacyStart, privacyEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvRegisterRead.setMovementMethod(LinkMovementMethod.getInstance());
        tvRegisterRead.setText(spannableString);
        tvRegisterRead.setHighlightColor(Color.TRANSPARENT);
    }

    @OnClick({R.id.tv_register_submit, R.id.tv_register_code, R.id.id_img_title_back, R.id.tv_register_nation})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_img_title_back:
                finish();
                break;
            case R.id.tv_register_submit:
                submit(etRegisterAccount.getText().toString().trim(), etRegisterCode.getText().toString().trim(), etRegisterPassword.getText().toString().trim());
                break;
            case R.id.tv_register_code:
                checkCode(tvRegisterCode, etRegisterAccount.getText().toString().trim());
                break;
            case R.id.tv_register_nation:
                startActivityForResult(new Intent(context, PickActivity.class), 111);
                break;

        }
    }

    private void submit(String account, String code, String pwd) {
        if (!NetworkUtils.checkNetwork(this)) {
            ToastCustom.makeText(R.string.check_network).show();
        } else if (checkInputInfo()) {
            closeWaitLoading();
            showWaitDialog(type == TYPE_FORGET ? R.string.password_changing : R.string.register);
            checkCode(account, code, pwd);
        }
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
//                    WechatUserConfig.clear(context);
//                    setAliasAndTag(loginResultInfo.getMsg());
//                    clearCookie();//切换用户后需要清空cookie, 否则我的界面用户名不更新
//                    PhoneUserConfig.setPhoneNumber(account);
//                    SPUtils.put(context, Constans.DEVICE_TOKEN, loginResultInfo.getData().getToken());
//                    WechatUserConfig.setWechatPublicOpenId(KitchenDiaryApplication.getInstance(), loginResultInfo.getMsg());
//                    Log.d("RegisterActivity", "user info: token:" + loginResultInfo.getData().getToken() + ",openid:" + loginResultInfo.getMsg());
                    Intent intent = new Intent(context, HomeActivity.class);
                    if (context != null) {
                        intent.putExtra("open_id", loginResultInfo.getMsg());
                        startActivity(intent);
                        closeWaitLoading();
                        finish();
                    }
                }
            }

            @Override
            public void onError() {
                closeWaitLoading();
                if (type == TYPE_FORGET) {
                    ToastCustom.makeText(type == TYPE_FORGET ? R.string.change_failed : R.string.register_failed).show();
                } else {
                    ToastCustom.makeText(R.string.register_failed).show();
                }
            }
        });
    }

    private void showWaitDialog(int resId) {
        QMUITipDialog.Builder builder = new QMUITipDialog.Builder(this);
        builder.setIconType(ICON_TYPE_LOADING);
        builder.setTipWord(LanguageUtil.getInstance().getStringById(resId));
        dialog = builder.create();
        dialog.show();
        delayCloseLoading();
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

    /**
     * 检查账号是否存在, 使用现有登录接口(-4不存在, 其他存在)
     *
     * @param account
     * @param pwd
     */
    private void checkAccount(final String account, String pwd) {
        RetrofitTask.accountLogin(account, pwd, new RetrofitTask.CallBack<String>() {
            @Override
            public void result(String s) {
                if (!TextUtils.isEmpty(s)) {
                    try {
                        JSONObject json = new JSONObject(s);
                        int code = json.getInt("code");
                        Log.d("RegisterActivity", "checkAccount result: " + json);
                        if (code == -4) {
                            sendCheckCode(account);
                        } else {
                            ToastCustom.makeText(R.string.register_exist).show();
                            cancelCountDownTimer();
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

    private void checkCode(final String account, String code, final String pwd) {
        RetrofitTask.checkCodeRight(account, code, new RetrofitTask.CallBack<String>() {
            @Override
            public void result(String s) {
                if (!TextUtils.isEmpty(s)) {
                    try {
                        JSONObject json = new JSONObject(s);
                        String code = json.getString("code");
                        Log.d("RegisterActivity", "checkCode result: " + code);
                        if ("0".equals(code)) {
                            if (type == TYPE_FORGET) {
                                changePassword(account, pwd);
                            } else {
                                registerAccount(account, pwd);
                            }
                        } else {
                            closeWaitLoading();
                            ToastCustom.makeText(R.string.check_fail).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onError() {
                closeWaitLoading();
                ToastCustom.makeText(R.string.check_fail).show();
            }
        });
    }

    private boolean checkInputInfo() {
        boolean result = false;
        String phone = etRegisterAccount.getText().toString().trim();
        String password = etRegisterPassword.getText().toString().trim();
        String code = etRegisterCode.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
//            ToastCustom.makeText(tvRegisterSubmit, loginType == TYPE_PHOE ? R.string.phone_number_is_null : R.string.email_is_null, Snackbar.LENGTH_SHORT).show();
            ToastCustom.makeText(loginType == TYPE_PHOE ? R.string.phone_number_is_null : R.string.email_is_null).show();
        } else if (loginType == TYPE_PHOE && !PhoneLoginUtils.checkCellphone(phone)) {
            ToastCustom.makeText(R.string.phone_number_is_error).show();
        } else if (loginType == TYPE_EMAIL && !PhoneLoginUtils.checkEmail(phone)) {
            ToastCustom.makeText(R.string.email_is_error).show();
        } else if (TextUtils.isEmpty(code)) {
            ToastCustom.makeText(R.string.check_code_is_null).show();
        } else if (TextUtils.isEmpty(password)) {
            ToastCustom.makeText(R.string.password_is_null).show();
        } else if (!PhoneLoginUtils.checkPassword(password)) {
            ToastCustom.makeText(R.string.login_password_hint).show();
        } else {
            result = true;
        }
        return result;
    }

    @OnCheckedChanged(R.id.cb_register_password)
    public void onCheck(CompoundButton view, boolean isCheck) {
        if (isCheck) {
            etRegisterPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            etRegisterPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
        Log.d("RegisterActivity", "onCheck: " + etRegisterPassword.length());
        if (etRegisterPassword.length() > 0) {
            etRegisterPassword.setSelection(etRegisterPassword.length());
        }
    }

    /**
     * <p>发送手机验证码</p>
     *
     * @param v           点击按钮
     * @param phoneNumber 手机号码
     */
    private void checkCode(final View v, String phoneNumber) {
        if (!NetworkUtils.checkNetwork(this)) {
            ToastCustom.makeText(R.string.check_network).show();
        } else if (TextUtils.isEmpty(phoneNumber)) {
            ToastCustom.makeText(loginType == TYPE_PHOE ? R.string.phone_number_is_null : R.string.email_is_null).show();
        } else if (loginType == TYPE_PHOE && !PhoneLoginUtils.checkCellphone(phoneNumber)) {
            ToastCustom.makeText(R.string.phone_number_is_error).show();
        } else if (loginType == TYPE_EMAIL && !PhoneLoginUtils.checkEmail(phoneNumber)) {
            ToastCustom.makeText(R.string.email_is_error).show();
        } else if (type == TYPE_REGISTER) {
            startCountDownTimer();
            checkAccount(phoneNumber, String.valueOf(new Random().nextInt(999999)));
        } else {
            startCountDownTimer();
            sendCheckCode(phoneNumber);
        }
    }

    public void sendCheckCode(String phoneNumber) {
        if (!RetrofitTask.isCanSendCode()) {
            cancelCountDownTimer();
            ToastCustom.makeText(R.string.check_code_error).show();
        } else {
            RetrofitTask.getPhoneCheckCode(loginType, phoneNumber, new RetrofitTask.CallBack<String>() {
                @Override
                public void result(String s) {
                    if (!TextUtils.isEmpty(s)) {
                        try {
                            JSONObject json = new JSONObject(s);
                            String code = json.getString("code");
                            Log.d("RegisterActivity", "code result: " + code);
                            if (!"0".equals(code)) {
                                tvRegisterCode.setEnabled(true);
                                tvRegisterCode.setText(R.string.check_code);
                                cancelCountDownTimer();
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

    private void closeWaitLoading() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }
        if (delayClose != null && !delayClose.isUnsubscribed()) {
            delayClose.unsubscribe();
        }
    }

    //请求中突然断网, 延时关闭
    private void delayCloseLoading() {
        if (delayClose != null && !delayClose.isUnsubscribed()) {
            delayClose.unsubscribe();
        }
        delayClose = Observable.timer(10, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        closeWaitLoading();
                        ToastCustom.makeText(R.string.register_failed).show();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
    }

    private void setAliasAndTag(String openId) {
//        Set<String> tag = new HashSet<>();
//        tag.add("device");
//        JPushInterface.setTags(getApplicationContext(), 0, tag);
    }

    public void clearCookie() {
        CookieManager manager = CookieManager.getInstance();
        manager.removeAllCookie();
    }

    /**
     * <p>取消验证码倒计时</p>
     */
    private void cancelCountDownTimer() {
        Log.d("RegisterActivity", "cancelCountDownTimer: ");
        tvRegisterCode.setEnabled(true);
        tvRegisterCode.setText(R.string.check_code);
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    /**
     * <p>开始验证码倒计时1分钟</p>
     */
    private void startCountDownTimer() {
        tvRegisterCode.setEnabled(false);
        timer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(final long millisUntilFinished) {
                tvRegisterCode.setText(String.format(LanguageUtil.getInstance().getStringById(R.string.login_retry), millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                cancelCountDownTimer();
            }
        };
        timer.start();
    }


    @Override
    protected View getWifiLostView() {
        return null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 111 && resultCode == Activity.RESULT_OK) {
            Country country = Country.fromJson(data.getStringExtra("country"));
            Log.d("RegisterActivity", "onActivityResult: " + country);
            tvRegisterNation.setText("+" + country.getCode());
            PhoneUserConfig.setNationCode(country.getCode());
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (getCurrentFocus() != null) {
            if (checkInputMethodIsOpen()) {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
        return super.onTouchEvent(event);
    }


    @Override
    protected void onDestroy() {
        Country.destroy();
        cancelCountDownTimer();
        if (etRegisterAccount != null) {
            etRegisterAccount.removeTextChangedListener(this);
        }
        if (etRegisterPassword != null) {
            etRegisterPassword.removeTextChangedListener(this);
        }
        if (etRegisterCode != null) {
            etRegisterCode.removeTextChangedListener(this);
        }
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        tvRegisterSubmit.setEnabled(!TextUtils.isEmpty(etRegisterAccount.getText()) && !TextUtils.isEmpty(etRegisterPassword.getText()) && !TextUtils.isEmpty(etRegisterCode.getText()));
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
