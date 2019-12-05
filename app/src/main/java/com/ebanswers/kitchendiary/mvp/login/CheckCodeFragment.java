package com.ebanswers.kitchendiary.mvp.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.ebanswers.kitchendiary.R;
import com.ebanswers.kitchendiary.bean.LoginResultInfo;
import com.ebanswers.kitchendiary.mvp.view.base.HomeActivity;
import com.ebanswers.kitchendiary.mvp.view.base.LoginActivity;
import com.ebanswers.kitchendiary.mvp.view.base.RegisterActivity;
import com.ebanswers.kitchendiary.retrofit.RetrofitTask;
import com.ebanswers.kitchendiary.utils.LanguageUtil;
import com.ebanswers.kitchendiary.utils.NetworkUtils;
import com.ebanswers.kitchendiary.utils.SPUtils;
import com.ebanswers.kitchendiary.utils.ToastCustom;
import com.ebanswers.kitchendiary.widget.VerificationCodeInput;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;

/**
 * @author caixd
 * @date 2019/9/18
 * PS: 验证码填入fragment
 */
public class CheckCodeFragment extends BaseLoginFragment {
    private static final String TAG = CheckCodeFragment.class.getSimpleName();
    @BindView(R.id.et_input_code)
    VerificationCodeInput etInputCode;
    @BindView(R.id.tv_resend_code)
    TextView tvResendCode;
    private String account;
    private CountDownTimer timer;
    private int type;
    //手机验证码直接登录
    public static final int TYPE_PHONE_LOGIN = 319;
    //找回密码
    public static final int TYPE_FIND_PWD = 303;
    //设置密码
    public static final int TYPE_SET_PWD = 922;

    //通过邮箱获取 修改手机号密码 验证码
    public static CheckCodeFragment newInstance(String account, int type) {
        CheckCodeFragment fragment = new CheckCodeFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        bundle.putString("account", account);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_check_code;
    }

    @Override//启动fragment时判断传入的参数 发送
    protected void onCreateViewNext(@Nullable Bundle savedInstanceState) {
        account = getArguments().getString("account");
        type = getArguments().getInt("type");
        Log.d("CheckCodeFragment", "onViewCreated: " + account + "," + type);
        initListener();
        if (!NetworkUtils.checkNetwork(getActivity())) {
            ToastCustom.makeText("请检查网络连接").show();
        } else {
            startCountDownTimer();
            sendCheckCode(account);
        }
    }

    private void initListener() {
        tvResendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCountDownTimer();
                sendCheckCode(account);
            }
        });
        etInputCode.setOnCompleteListener(new VerificationCodeInput.Listener() {
            @Override
            public void onComplete(String code) {
                Log.d("CheckCodeFragment", "onComplete: " + code);
                showWaitDialog(R.string.login_code_checking);
                checkCode(account, code);
            }
        });
    }

    private void checkCode(final String account, String code) {
        RetrofitTask.checkCodeRight(account, code, new RetrofitTask.CallBack<String>() {
            @Override
            public void result(String s) {
                if (!TextUtils.isEmpty(s)) {
                    try {
                        JSONObject json = new JSONObject(s);
                        String code = json.getString("code");
                        Log.d("RegisterActivity", "checkCode result: " + code);
                        closeWaitLoading();
                        etInputCode.clear();
                        if ("0".equals(code)) {
                            switch (type) {
                                case TYPE_PHONE_LOGIN:
                                    getPhoneUserInfo(account);
                                    break;
                                case TYPE_FIND_PWD:
                                    LoginActivity.openActivity(getActivity(), LoginActivity.TYPE_RESET_PWD, account);
                                    break;
                                case TYPE_SET_PWD:
                                    LoginActivity.openActivity(getActivity(), LoginActivity.TYPE_SET_PWD, account);
                                    break;
                                default:
                            }
                        } else {
                            ToastCustom.makeText(R.string.check_fail).show();
                            etInputCode.clear();
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

    private void getPhoneUserInfo(final String account) {
        RetrofitTask.postPhoneUserInfo(account, new RetrofitTask.CallBack<LoginResultInfo>() {
            @Override
            public void result(LoginResultInfo loginResultInfo) {
                Log.d(TAG, "result: " + loginResultInfo.getMsg());
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
                    Log.d("CheckCodeFragment", "user info: token:" + loginResultInfo.getData().getToken() + ",openid:" + loginResultInfo.getMsg());
                    EventBus.getDefault().post("finishThis");
                    Intent intent = new Intent(getActivity(), HomeActivity.class);
                    intent.putExtra("open_id", loginResultInfo.getMsg());
                    startActivity(intent);
                    closeWaitLoading();
                    etInputCode.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (getActivity() != null)
                                getActivity().finish();
                        }
                    }, 100);
                }
            }

            @Override
            public void onError() {
                Log.d(TAG, "onError: ");
                closeWaitLoading();
                ToastCustom.makeText(type == LoginActivity.TYPE_SET_PWD ? R.string.register_failed : R.string.change_failed).show();
            }
        });
    }

    private void startCountDownTimer() {
        tvResendCode.setEnabled(false);
        timer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(final long millisUntilFinished) {
                Log.d("CheckCodeFragment", "onTick: " + millisUntilFinished);
                tvResendCode.setText(String.format(LanguageUtil.getInstance().getStringById(R.string.login_retry), millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                cancelCountDownTimer();
            }
        };
        timer.start();
    }

    private void cancelCountDownTimer() {
        Log.d("RegisterActivity", "cancelCountDownTimer: ");
        tvResendCode.setEnabled(true);
        tvResendCode.setText(R.string.login_resend_code);
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    public void sendCheckCode(String phoneNumber) {
        if (!RetrofitTask.isCanSendCode()) {
            cancelCountDownTimer();
            ToastCustom.makeText(R.string.check_code_error).show();
        } else {
            RetrofitTask.getPhoneCheckCode(RegisterActivity.TYPE_PHOE, phoneNumber, new RetrofitTask.CallBack<String>() {
                @Override
                public void result(String s) {
                    if (!TextUtils.isEmpty(s)) {
                        try {
                            JSONObject json = new JSONObject(s);
                            String code = json.getString("code");
                            Log.d("RegisterActivity", "code result: " + code + " ,Json info: " + json.toString());
                            if (!"0".equals(code)) {
                                tvResendCode.setEnabled(true);
                                tvResendCode.setText(R.string.login_resend_code);
                                cancelCountDownTimer();
                                if (json.has("errmsg")) {
                                    String errmsg = json.getString("errmsg");
                                    if (errmsg != null && errmsg.contains("格式错误")) {
                                        ToastCustom.makeText(R.string.phone_number_format_error).show();
                                    }
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
                    /**
                     * 当验证码发送失败时，取消倒计时，允许重复发送
                     */
                    cancelCountDownTimer();
                }
            });
        }
    }


    @Override
    public void onDestroyView() {
        cancelCountDownTimer();
        super.onDestroyView();
    }
}
