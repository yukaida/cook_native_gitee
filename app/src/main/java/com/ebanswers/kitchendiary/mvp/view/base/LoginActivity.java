package com.ebanswers.kitchendiary.mvp.view.base;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.CookieManager;

import androidx.annotation.Nullable;

import com.ebanswers.kitchendiary.R;
import com.ebanswers.kitchendiary.mvp.login.CheckCodeFragment;
import com.ebanswers.kitchendiary.mvp.login.GetCodeFragment;
import com.ebanswers.kitchendiary.mvp.login.PasswordLoginFragment;
import com.ebanswers.kitchendiary.mvp.login.PasswordSetFragment;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import org.reactivestreams.Subscription;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {
    /**
     * 手机验证码登录
     */
    public static final int TYPE_PHONE_CODE = 791;
    /**
     * 邮箱登录
     */
    public static final int TYPE_EMAIL_LOGIN = 553;
    /**
     * 邮箱注册
     */
    public static final int TYPE_EMAIL_REGISTER = 117;
    /**
     * 找回手机密码
     */
    public static final int TYPE_FIND_PHONE_PWD = 161;
    /**
     * 找回邮箱密码
     */
    public static final int TYPE_FIND_EMAIL_PWD = 664;
    /**
     * 新密码
     */
    public static final int TYPE_SET_PWD = 43;
    /**
     * 重置密码
     */
    public static final int TYPE_RESET_PWD = 584;

    /**
     * 绑定邮箱
     */
    public static final int TYPE_BAND_Email = 182;

    private int loginType;
    private String account;
    private QMUITipDialog dialog;
    private Subscription delayClose;

    public static void openActivity(Context context, int type) {
        Log.d("LoginActivity", "openActivity: " + type);
        Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }

    //打开一个activity 在fragment中
    public static void openActivity(Context context, int type, String account) {
        Log.d("LoginActivity", "openActivity: " + type + "," + account);
        Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("account", account);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreateNext(@Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this);
        initFields(getIntent());
        initFragment();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d("LoginActivity", "onNewIntent: ");
        initFields(intent);
        initFragment();
    }
    //通过判断字段中loginType的值 确定要换上哪个Fragment
    private void initFragment() {
        switch (loginType) {
            case TYPE_PHONE_CODE:
                replaceFragment(CheckCodeFragment.newInstance(account, CheckCodeFragment.TYPE_PHONE_LOGIN));
                break;
            case TYPE_EMAIL_LOGIN:
                replaceFragment(PasswordLoginFragment.newInstance(PasswordLoginFragment.LOGIN_EMAIL, ""));
                break;
            case TYPE_EMAIL_REGISTER:
                replaceFragment(GetCodeFragment.newInstance(TYPE_EMAIL_REGISTER, account));
                break;
            case TYPE_FIND_PHONE_PWD:
                replaceFragment(GetCodeFragment.newInstance(TYPE_FIND_PHONE_PWD, account));
                break;
            case TYPE_FIND_EMAIL_PWD:
                replaceFragment(GetCodeFragment.newInstance(TYPE_FIND_EMAIL_PWD, account));
                break;
            case TYPE_SET_PWD:
                replaceFragment(PasswordSetFragment.newInstance(account, TYPE_SET_PWD));
                break;
            case TYPE_RESET_PWD:
                replaceFragment(PasswordSetFragment.newInstance(account, TYPE_RESET_PWD));
                break;

            case TYPE_BAND_Email:
                replaceFragment(PasswordSetFragment.newInstance(account, TYPE_RESET_PWD));
            default:
        }
    }
    //获取传过来的intent中的字段
    private void initFields(Intent intent) {
        if (intent != null) {
            loginType = intent.getIntExtra("type", TYPE_PHONE_CODE);
            account = intent.getStringExtra("account");
            Log.d("LoginActivity", "initFields: " + loginType + "," + account);
        }
    }

    public void replaceFragment(Fragment fragment) {
        getFragmentManager().beginTransaction().replace(R.id.layout_login_container, fragment).addToBackStack(fragment.getClass().getSimpleName()).commitAllowingStateLoss();
    }

    @OnClick({R.id.iv_login_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_login_back:
//                back();
                finish();
                break;
        }
    }

    public void clearCookie() {
        CookieManager manager = CookieManager.getInstance();
        manager.removeAllCookie();
    }

    private void back() {
        if(getFragmentManager().getBackStackEntryCount()>1){
            getFragmentManager().popBackStack();
        }else {
            finish();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.d("LoginActivity", "onKeyDown: back");
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return false;
    }



    @Override
    protected View getWifiLostView() {
        return null;
    }
}
