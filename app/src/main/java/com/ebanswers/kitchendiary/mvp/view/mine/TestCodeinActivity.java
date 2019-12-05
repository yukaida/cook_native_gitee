package com.ebanswers.kitchendiary.mvp.view.mine;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ebanswers.kitchendiary.R;
import com.ebanswers.kitchendiary.bean.LoginResultInfo;
import com.ebanswers.kitchendiary.mvp.view.base.HomeActivity;
import com.ebanswers.kitchendiary.mvp.view.base.LoginActivity;
import com.ebanswers.kitchendiary.retrofit.RetrofitTask;
import com.ebanswers.kitchendiary.utils.SPUtils;
import com.ebanswers.kitchendiary.utils.ToastCustom;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TestCodeinActivity extends AppCompatActivity implements TextWatcher {

    private static final String TAG = "TestCodeinActivity";
    @BindView(R.id.test_checkcode)
    EditText testCheckcode;//填入的验证码
    @BindView(R.id.test_check)
    Button testCheck;//校验验证码按钮
    String email = null;
    String phone = null;
    @BindView(R.id.imageView2)
    ImageView imageView2;
    @BindView(R.id.textView3)
    TextView textView3;
    @BindView(R.id.textView4)
    TextView textView4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_codein);
        setStatusBarFullTransparent();
        ButterKnife.bind(this);
        initListener();
        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        phone = intent.getStringExtra("phone");
        textView4.setText(email);
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @OnClick(R.id.test_check)
    public void onViewClicked() {
        //todo 判断验证码是否正确
        checkCode(phone,email,testCheckcode.getText().toString());
        Log.d(TAG, "onViewClicked: mark"+email+phone+testCheckcode.getText().toString());

    }

    private void checkCode(final String phone, String email, String code) {
        RetrofitTask.isCodeRight(phone, email,code, new RetrofitTask.CallBack<String>() {
            @Override
            public void result(String s) {
                Log.d(TAG, "result: mark"+s);
                if (!TextUtils.isEmpty(s)) {
                    try {
                        JSONObject json = new JSONObject(s);
                        int code = json.getInt("code");
                        Log.d("Test_codein", "checkCode result: " + code);
                        if (code==0) {//绑定成功跳转到修改密码界面
                            Toast.makeText(TestCodeinActivity.this, "绑定成功", Toast.LENGTH_SHORT).show();
                            //todo 正确的话 跳转到修改密码界面 （附带手机号）
                            LoginActivity.openActivity(TestCodeinActivity.this, LoginActivity.TYPE_SET_PWD, phone);

                        } else {
                            ToastCustom.makeText(R.string.check_fail).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onError() {
                ToastCustom.makeText(R.string.check_fail).show();
            }
        });
    }



    private void initListener() {
        testCheckcode.addTextChangedListener(this);
    }

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
                    Log.d("CheckCodeFragment", "user info: token:" + loginResultInfo.getData().getToken() + ",openid:" + loginResultInfo.getMsg());
                    Intent intent = new Intent(TestCodeinActivity.this, HomeActivity.class);
                    intent.putExtra("open_id", loginResultInfo.getMsg());
                    startActivity(intent);

                }
            }

            @Override
            public void onError() {

            }
        });
    }

    /**
     * 全透状态栏
     */
    protected void setStatusBarFullTransparent() {
        if (Build.VERSION.SDK_INT >= 21) {//21表示5.0
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= 19) {//19表示4.4
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //虚拟键盘也透明
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        testCheckcode.setSelected(!TextUtils.isEmpty(testCheckcode.getText()));
        testCheck.setEnabled(!TextUtils.isEmpty(testCheckcode.getText()));
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
