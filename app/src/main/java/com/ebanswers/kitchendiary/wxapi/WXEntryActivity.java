package com.ebanswers.kitchendiary.wxapi;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.CookieManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.ebanswers.kitchendiary.R;
import com.ebanswers.kitchendiary.bean.LoginResultInfo;
import com.ebanswers.kitchendiary.bean.WechatUserInfo;
import com.ebanswers.kitchendiary.common.CommonApplication;
import com.ebanswers.kitchendiary.config.Constans;
import com.ebanswers.kitchendiary.config.PhoneUserConfig;
import com.ebanswers.kitchendiary.config.QRCodeMap;
import com.ebanswers.kitchendiary.config.WechatUserConfig;
import com.ebanswers.kitchendiary.constant.AppConstant;
import com.ebanswers.kitchendiary.database.bean.WechatConfig;
import com.ebanswers.kitchendiary.database.bean.WechatRefreshConfig;
import com.ebanswers.kitchendiary.eventbus.Event;
import com.ebanswers.kitchendiary.eventbus.EventBusUtil;
import com.ebanswers.kitchendiary.mvp.login.GetCodeFragment;
import com.ebanswers.kitchendiary.mvp.login.PasswordLoginFragment;
import com.ebanswers.kitchendiary.mvp.view.base.HomeActivity;
import com.ebanswers.kitchendiary.mvp.view.base.LoginActivity;
import com.ebanswers.kitchendiary.mvp.view.base.WebActivity;
import com.ebanswers.kitchendiary.retrofit.RetrofitTask;
import com.ebanswers.kitchendiary.utils.LanguageUtil;
import com.ebanswers.kitchendiary.utils.NetworkUtils;
import com.ebanswers.kitchendiary.utils.PhoneLoginUtils;
import com.ebanswers.kitchendiary.utils.SPUtils;
import com.ebanswers.kitchendiary.utils.ToastCustom;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.socialize.weixin.view.WXCallbackActivity;
import com.zhy.autolayout.AutoFrameLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;
import org.simple.eventbus.Subscriber;

import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

import static com.ebanswers.kitchendiary.config.Constans.GetCodeRequest;
import static com.ebanswers.kitchendiary.config.Constans.GetUserInfo;
import static com.qmuiteam.qmui.widget.dialog.QMUITipDialog.Builder.ICON_TYPE_LOADING;

public class WXEntryActivity extends WXCallbackActivity implements IWXAPIEventHandler {
    @BindView(R.id.tv_login_type)
    TextView tvLoginType;
    @BindView(R.id.layout_login_container)
    AutoFrameLayout layoutLoginContainer;
    @BindView(R.id.iv_login_phone_wechat)
    ImageView ivLoginPhoneWechat;
    @BindView(R.id.iv_login_phone_email)
    ImageView ivLoginPhoneEmail;
    @BindView(R.id.tv_login_phone_read)
    TextView tvLoginPhoneRead;
    @BindView(R.id.iv_login_phone_facebook)//facebook第三方登录按钮图标
            ImageView ivLoginPhoneFacebook;
    private WeakReference<WXEntryActivity> context;
    private QMUITipDialog dialog;
    private int count;
    private IWXAPI mSApi;
    private Subscription delayClose;
    private static LoginResultInfo mLoginResultInfo;
    private int loginType;//手机登录或密码登录
    private View pwdLoginView;//密码登录界面
    private View phoneLoginView;//验证码登录界面
    private static final int LOGIN_TYPE_PHONE = 636;
    private static final int LOGIN_TYPE_PWD = 75;
    private String validAccount;
    private GetCodeFragment phoneCodeFragment;
    private PasswordLoginFragment phonePwdFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        QMUIStatusBarHelper.translucent(this);
        QMUIStatusBarHelper.setStatusBarLightMode(this);
        setContentView(R.layout.activity_main_login);
        ButterKnife.bind(this);
        context = new WeakReference<>(this);
        mSApi = WXAPIFactory.createWXAPI(CommonApplication.getInstance(), Constans.WX_APP_ID, false);
        mSApi.registerApp(Constans.WX_APP_ID);
        mSApi.handleIntent(getIntent(), context.get());
        showPolicy();
        loginType = LOGIN_TYPE_PHONE;
        updateView();
    }

    private void updateView() {
        Log.d("WXEntryActivity", "updateView: " + loginType + "," + validAccount);
        tvLoginType.setText(loginType == LOGIN_TYPE_PHONE ? R.string.login_type_password : R.string.login_type_phone);
        if (loginType == LOGIN_TYPE_PHONE) {
            phoneCodeFragment = GetCodeFragment.newInstance(LoginActivity.TYPE_PHONE_CODE, validAccount);
        } else {
            phonePwdFragment = PasswordLoginFragment.newInstance(PasswordLoginFragment.LOGIN_PHONE, validAccount);
        }
        getFragmentManager().beginTransaction().replace(R.id.layout_login_container, loginType == LOGIN_TYPE_PHONE ? phoneCodeFragment : phonePwdFragment).commitAllowingStateLoss();
    }

    //用户协议 隐私政策 富文本
    private void showPolicy() {
        SpannableString spannableString = new SpannableString(tvLoginPhoneRead.getText());
//        int agreementStart = LanguageUtil.getInstance().isChinease() ? 8 : 9;
//        int agreementEnd = LanguageUtil.getInstance().isChinease() ? 25 : 13;
//        int privacyStart = LanguageUtil.getInstance().isChinease() ? 30 : 14;
        int agreementStart = LanguageUtil.getInstance().isChinease() ? 9 : 8;
        int agreementEnd = LanguageUtil.getInstance().isChinease() ? 13 : 25;
        int privacyStart = LanguageUtil.getInstance().isChinease() ? 14 : 30;
        int privacyEnd = tvLoginPhoneRead.getText().length();
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.primary_dark)), agreementStart, agreementEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.primary_dark)), privacyStart, privacyEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
              /*  Intent intent1 = new Intent(WXEntryActivity.this, FullActivity.class);
                intent1.putExtra("type", FullActivity.TYPE_LOGIN_POLICY);
                intent1.putExtra("url", Constans.URL_PROTOCOL);
                startActivity(intent1);*/
                Intent intent = new Intent(WXEntryActivity.this, WebActivity.class);
                intent.putExtra("url", Constans.URL_PROTOCOL);
                startActivity(intent);

            }
        }, agreementStart, agreementEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                /*Intent intent1 = new Intent(WXEntryActivity.this, FullActivity.class);
                intent1.putExtra("type", FullActivity.TYPE_LOGIN_POLICY);
                intent1.putExtra("url", Constans.URL_PRIVACY);
                startActivity(intent1);*/

                Intent intent = new Intent(WXEntryActivity.this, WebActivity.class);
                intent.putExtra("url", Constans.URL_PRIVACY);
                startActivity(intent);

            }
        }, privacyStart, privacyEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvLoginPhoneRead.setMovementMethod(LinkMovementMethod.getInstance());
        tvLoginPhoneRead.setText(spannableString);
        tvLoginPhoneRead.setHighlightColor(Color.TRANSPARENT);
    }

    public void clearCookie() {
        CookieManager manager = CookieManager.getInstance();
        manager.removeAllCookie();
    }

    @OnClick({R.id.iv_login_phone_wechat, R.id.iv_login_phone_email, R.id.iv_login_phone_facebook, R.id.tv_login_type})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_login_phone_wechat:
                WXLogin();
                break;
            case R.id.iv_login_phone_email:
                LoginActivity.openActivity(context.get(), LoginActivity.TYPE_EMAIL_LOGIN, "");
                break;
            case R.id.tv_login_type:
//                loginType = loginType == LOGIN_TYPE_PHONE ? LOGIN_TYPE_PWD : LOGIN_TYPE_PHONE;
                if (loginType == LOGIN_TYPE_PHONE) {
                    loginType = LOGIN_TYPE_PWD;
                    validAccount = PhoneLoginUtils.checkCellphone(phoneCodeFragment.getAccount()) ? phoneCodeFragment.getAccount() : "";
                } else {
                    loginType = LOGIN_TYPE_PHONE;
                    validAccount = PhoneLoginUtils.checkCellphone(phonePwdFragment.getAccount()) ? phonePwdFragment.getAccount() : "";
                }
                updateView();
                break;
            default:
        }
    }


    /**
     * 手机登录
     *
     * @param account
     * @param password
     */
    private void phoneLogin(String account, String password) {
        if (!NetworkUtils.checkNetwork(this)) {
            ToastCustom.makeText(R.string.check_network).show();
        } else if (TextUtils.isEmpty(account)) {
            ToastCustom.makeText(R.string.phone_number_is_null).show();
        } else if (!PhoneLoginUtils.checkCellphone(account)) {
            ToastCustom.makeText(R.string.phone_number_is_error).show();
        } else if (TextUtils.isEmpty(password)) {
            ToastCustom.makeText(R.string.password_is_null).show();
        } else if (!PhoneLoginUtils.checkPassword(password)) {
            ToastCustom.makeText(R.string.login_password_hint).show();
        } else {
            closeWaitLoading();
            QMUITipDialog.Builder builder = new QMUITipDialog.Builder(this);
            builder.setIconType(ICON_TYPE_LOADING);
            builder.setTipWord(LanguageUtil.getInstance().getStringById(R.string.login));
            dialog = builder.create();
            dialog.show();
            delayCloseLoading();
//            test();
//            newApi(account, password);
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
                        Log.d("WXEntryActivity", "login: " + s);
                        if (code == 0) {
                            RetrofitTask.postPhoneUserInfo(phoneNumberStr, new RetrofitTask.CallBack<LoginResultInfo>() {
                                @Override
                                public void result(LoginResultInfo loginResultInfo) {
                                    if (loginResultInfo != null) {
                                        closeWaitLoading();
                                        if (loginResultInfo.getCode() == 0){
                                            SPUtils.setLogin(true);
                                            if (!TextUtils.isEmpty(loginResultInfo.getMsg())) {
                                                SPUtils.put(AppConstant.USER_ID, loginResultInfo.getMsg());
                                            }
                                        }
//                                        WechatUserConfig.clear(context.get());
//                                        setAliasAndTag(loginResultInfo.getMsg());
//                                        clearCookie();//切换用户后需要清空cookie, 否则我的界面用户名不更新
//                                        PhoneUserConfig.setPhoneNumber(phoneNumberStr);
//                                        SPUtils.put(context.get(), Constans.DEVICE_TOKEN, loginResultInfo.getData().getToken());
//                                        WechatUserConfig.setWechatPublicOpenId(KitchenDiaryApplication.getInstance(), loginResultInfo.getMsg());
                                        Log.d("WXEntryActivity", "login result: token:" + loginResultInfo.getData().getToken() + ",openid:" + loginResultInfo.getMsg());
                                        Intent intent = new Intent(context.get(), HomeActivity.class);
                                        EventBus.getDefault().post("finishThis");
                                        EventBusUtil.sendEvent(new Event(Event.EVENT_UPDATE_TOMINE,"我的"));
                                        if (context != null && context.get() != null) {
                                            intent.putExtra("open_id", loginResultInfo.getMsg());
                                            startActivity(intent);
                                            finish();
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
                        closeWaitLoading();
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

    private void setAliasAndTag(String openId) {
//        Set<String> tag = new HashSet<>();
//        tag.add("device");
//        JPushInterface.setTags(getApplicationContext(), 0, tag);
//        JPushInterface.setAlias(getApplicationContext(), 0, openId);
//        JPushInterface.resumePush(getApplicationContext());
    }

    /**
     * 登录微信
     */
    private void WXLogin() {
        if (mSApi.isWXAppInstalled()) {
            if (NetworkUtils.checkNetwork(context.get())) {
                QMUITipDialog.Builder builder = new QMUITipDialog.Builder(this);
                builder.setIconType(ICON_TYPE_LOADING);
                builder.setTipWord(LanguageUtil.getInstance().getStringById(R.string.login_requesting));
                dialog = builder.create();
                dialog.show();
                SendAuth.Req req = new SendAuth.Req();
                req.scope = "snsapi_userinfo";
                req.state = "wechat_sdk_demo";
                mSApi.sendReq(req);
            } else {
                ToastCustom.makeText(R.string.network_error).show();
            }
        } else {
            ToastCustom.makeText(R.string.login_error).show();
        }
    }

    @Override
    public void onResp(BaseResp baseResp) {
        if (count % 2 == 0) {
            if (baseResp != null && baseResp instanceof SendAuth.Resp) {
                switch (baseResp.errCode) {
                    case BaseResp.ErrCode.ERR_OK:
                        if (baseResp instanceof SendAuth.Resp) {
                            String code = ((SendAuth.Resp) baseResp).code;
                            closeWaitLoading();
                            QMUITipDialog.Builder builder = new QMUITipDialog.Builder(this);
                            builder.setIconType(ICON_TYPE_LOADING);
                            builder.setTipWord(LanguageUtil.getInstance().getStringById(R.string.login));
                            dialog = builder.create();
                            dialog.show();
                            delayCloseLoading();
                            String accessToken = WechatUserConfig.getAccessToken(getApplicationContext());
                            String openid = WechatUserConfig.getWechatOpenId(getApplicationContext());
                            if (NetworkUtils.checkNetwork(context.get())) {
                                if (!TextUtils.isEmpty(accessToken) && !TextUtils.isEmpty(openid)) {
                                    // 有access_token，判断是否过期有效
                                    isExpireAccessToken(accessToken, openid);
                                } else {
                                    // 没有access_token
                                    getAccessToken(String.format(GetCodeRequest, Constans.WX_APP_ID, Constans.WX_APP_SECRET, code));
                                }
                            }
                        } else {
                            finish();
                            return;
                        }
                        break;
                    case BaseResp.ErrCode.ERR_USER_CANCEL:
                        closeWaitLoading();
                        ToastCustom.makeText(R.string.login_cancel).show();
                        break;
                    case BaseResp.ErrCode.ERR_AUTH_DENIED:
                        closeWaitLoading();
                        ToastCustom.makeText(R.string.login_refuse).show();
                        break;
                    default:
                        closeWaitLoading();
                        ToastCustom.makeText(R.string.login_back).show();
                        break;
                }

            } else {
                super.onResp(baseResp);
            }
        }
        count++;
    }

    /**
     * 获取用户个人信息的URL（微信）
     *
     * @param access_token 获取access_token时给的
     * @param openid       获取access_token时给的
     * @return URL
     */
    private String getUserInfoUrl(String access_token, String openid) {
        return String.format(GetUserInfo, access_token, openid);
    }

    /**
     * 获取access_token
     *
     * @param get_access_token
     */
    private void getAccessToken(String get_access_token) {
        RetrofitTask.getAccessToken(get_access_token, new RetrofitTask.CallBack<WechatConfig>() {
            @Override
            public void result(WechatConfig wechatConfig) {
                if (wechatConfig != null) {
                    String access_token = wechatConfig.getAccess_token();
                    String refresh_access_token = wechatConfig.getRefresh_token();
                    String openid = wechatConfig.getOpenid();
                    String unionid = wechatConfig.getUnionid();
                    if (!TextUtils.isEmpty(access_token))
                        WechatUserConfig.setAccessToken(CommonApplication.getInstance(), access_token);
                    if (!TextUtils.isEmpty(refresh_access_token))
                        WechatUserConfig.setRefreshAccessToken(CommonApplication.getInstance(), refresh_access_token);
                    if (!TextUtils.isEmpty(openid))
                        WechatUserConfig.setWechatOpenId(CommonApplication.getInstance(), openid);
                    if (!TextUtils.isEmpty(unionid))
                        WechatUserConfig.setUnionId(CommonApplication.getInstance(), unionid);
                    if (!TextUtils.isEmpty(access_token) && !TextUtils.isEmpty(openid))
                        getUserInfo(getUserInfoUrl(access_token, openid));
                }
            }

            @Override
            public void onError() {
                ToastCustom.makeText(R.string.login_failed).show();
            }
        });
    }

    /**
     * 刷新的accesstoken
     *
     * @param get_refresh_access_token
     */
    private void getRefreshAccessToken(String get_refresh_access_token) {
        RetrofitTask.getRefreshAccessToken(get_refresh_access_token, new RetrofitTask.CallBack<WechatRefreshConfig>() {
            @Override
            public void result(WechatRefreshConfig wechatRefreshConfig) {
                if (wechatRefreshConfig != null) {
                    String access_token = wechatRefreshConfig.getAccess_token();
                    String refresh_access_token = wechatRefreshConfig.getRefresh_token();
                    String openid = wechatRefreshConfig.getOpenid();
                    if (!TextUtils.isEmpty(access_token))
                        WechatUserConfig.setAccessToken(CommonApplication.getInstance(), access_token);
                    if (!TextUtils.isEmpty(refresh_access_token))
                        WechatUserConfig.setRefreshAccessToken(CommonApplication.getInstance(), refresh_access_token);
                    if (!TextUtils.isEmpty(openid))
                        WechatUserConfig.setWechatOpenId(CommonApplication.getInstance(), openid);
                    if (!TextUtils.isEmpty(access_token) && !TextUtils.isEmpty(openid))
                        getUserInfo(getUserInfoUrl(access_token, openid));
                }
            }

            @Override
            public void onError() {

            }
        });
    }

    /**
     * 得到微信用户信息
     *
     * @param get_user_info_url
     */
    private void getUserInfo(String get_user_info_url) {
        RetrofitTask.getUserInfo(get_user_info_url, new RetrofitTask.CallBack<WechatUserInfo>() {
            @Override
            public void result(WechatUserInfo wechatUserInfo) {
                if (wechatUserInfo != null) {
                    String nickname = wechatUserInfo.getNickname();
                    String head_url = wechatUserInfo.getHeadimgurl();
                    WechatUserConfig.setWechatUserInfo(wechatUserInfo);
                    WechatUserConfig.setWechatNickName(CommonApplication.getInstance(), nickname);
                    WechatUserConfig.setWechatHeadUrl(CommonApplication.getInstance(), head_url);
                }
                RetrofitTask.postUserInfo(wechatUserInfo, new RetrofitTask.CallBack<LoginResultInfo>() {
                    @Override
                    public void result(LoginResultInfo loginResultInfo) {

                        if (loginResultInfo != null) {
                            if (!TextUtils.isEmpty(loginResultInfo.getMsg())) {
                                mLoginResultInfo = loginResultInfo;
                                    SPUtils.put(AppConstant.USER_ID, loginResultInfo.getMsg());

                                clearCookie();
                                Log.d("Snail", "result: " + loginResultInfo.getData().getNeed_bind_phone());
                                if (1 == loginResultInfo.getData().getNeed_bind_phone()) {
                                    EventBus.getDefault().post("finishThis");
                                    bindPhoneSuccess(true);
                                    PhoneUserConfig.removePhoneNumber();
                                    SPUtils.setLogin(true);
                                    SPUtils.put(AppConstant.USER_ID, loginResultInfo.getMsg());
                                    Intent intent = new Intent(context.get(), HomeActivity.class);
                                    intent.putExtra("open_id", loginResultInfo.getMsg());
                                    startActivity(intent);
                                    closeWaitLoading();
                                    finish();

                                } else {
                                    if (context != null && context.get() != null) {
                                        EventBus.getDefault().post("finishThis");
                                        bindPhoneSuccess(true);
                                        PhoneUserConfig.removePhoneNumber();
                                        SPUtils.setLogin(true);

                                        SPUtils.put(AppConstant.USER_ID, loginResultInfo.getMsg());
                                        Intent intent = new Intent(context.get(), HomeActivity.class);
                                        intent.putExtra("open_id", loginResultInfo.getMsg());
                                        startActivity(intent);
                                        closeWaitLoading();
                                        finish();
                                    }
                                }
                            } else {
                                Log.d("login_error", "getUserInfo");
                                ToastCustom.makeText(R.string.login_failed).show();
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
            public void onError() {
                closeWaitLoading();
                ToastCustom.makeText(R.string.login_failed).show();
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void finishThis(String finishThis) {
        if ("finishThis".equals(finishThis))
            finish();
    }

    /**
     * 关闭等待窗
     */
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
                        Log.d("login_error", "delayClose");
                        ToastCustom.makeText(R.string.login_failed).show();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        mSApi.handleIntent(intent, context.get());
    }


    /**
     * 判断accesstoken是过期
     *
     * @param accessToken token
     * @param openid      授权用户唯一标识
     */
    private void isExpireAccessToken(final String accessToken, final String openid) {
        String url = String.format(Constans.CheckTokenIsUseful, accessToken, openid);
        RetrofitTask.isExpireAccessToken(url, new RetrofitTask.CallBack<String>() {
            @Override
            public void result(String result) {
                if (!TextUtils.isEmpty(result)) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        boolean flag = jsonObject.getString("errcode").equals("0");
                        if (flag) {//没有过期
                            if (!TextUtils.isEmpty(accessToken) && !TextUtils.isEmpty(openid))
                                getUserInfo(getUserInfoUrl(accessToken, openid));
                        } else {
                            String refresh_token = WechatUserConfig.getRefreshAccessToken(CommonApplication.getInstance());
                            if (!TextUtils.isEmpty(refresh_token)) {
                                getRefreshAccessToken(String.format(Constans.WX_REFRESH_Token, Constans.WX_APP_ID, refresh_token));
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onError() {
                ToastCustom.makeText(R.string.login_failed).show();
            }
        });
    }


    protected boolean checkInputMethodIsOpen() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        return imm.isActive();
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

    @Subscriber(tag = "BindPhoneSuccecss")
    public void bindPhoneSuccess(boolean b) {
        if (mLoginResultInfo != null) {
            WechatUserConfig.setWechatPublicOpenId(CommonApplication.getInstance(), mLoginResultInfo.getMsg());
            setAliasAndTag(mLoginResultInfo.getMsg());
            SPUtils.put(context.get(), Constans.DEVICE_TOKEN, mLoginResultInfo.getData().getToken());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
