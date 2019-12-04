package com.ebanswers.kitchendiary.mvp.view.base;

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
import com.ebanswers.kitchendiary.mvp.view.mine.TestCodeinActivity;
import com.ebanswers.kitchendiary.retrofit.RetrofitTask;
import com.ebanswers.kitchendiary.utils.ToastCustom;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TestBindActivity extends AppCompatActivity implements TextWatcher {
    private static final String TAG = "TestBindActivity";
    @BindView(R.id.test_email)
    EditText testEmail;//填入的邮箱
    @BindView(R.id.test_bind)
    Button testBind;//绑定邮箱按钮
    String email = null;
    String phone = null;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.test_bindemial_textview)
    TextView testBindemialTextview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test__bind);
        setStatusBarFullTransparent();

        ButterKnife.bind(this);
        initListener();
        Intent intent = getIntent();
        phone = intent.getStringExtra("phone");


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    @OnClick(R.id.test_bind)
    public void onViewClicked() {
        //todo 调用接口绑定邮箱和手机 同时发送验证码到邮箱
        Toast.makeText(this, "点击", Toast.LENGTH_SHORT).show();
        email = testEmail.getText().toString();
        getBindCode(phone,email);
    }


    //获取邮箱验证码
    public void sendCheckCode(final String email) {
        if (!RetrofitTask.isCanSendCode()) {
            ToastCustom.makeText(R.string.check_code_error).show();
        } else {
            RetrofitTask.getPhoneCheckCode(657, email, new RetrofitTask.CallBack<String>() {
                @Override
                public void result(String s) {
                    if (!TextUtils.isEmpty(s)) {
                        try {
                            JSONObject json = new JSONObject(s);
                            String code = json.getString("code");
                            if ("0".equals(code)) {
                                Toast.makeText(TestBindActivity.this, "发送成功", Toast.LENGTH_SHORT).show();
                                //如果发送成功  跳到填入验证码界面
                                Intent intent = new Intent(TestBindActivity.this, TestCodeinActivity.class);
                                intent.putExtra("email", email);
                                intent.putExtra("phone", phone);
                                startActivity(intent);
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

    private void initListener() {
        testEmail.addTextChangedListener(this);
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
        testEmail.setSelected(!TextUtils.isEmpty(testEmail.getText()));
        testBind.setEnabled(!TextUtils.isEmpty(testEmail.getText()));
    }
    @Override
    public void afterTextChanged(Editable editable) {

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
                            Toast.makeText(TestBindActivity.this, "发送成功", Toast.LENGTH_SHORT).show();
                            //todo 跳转到填入验证码界面
                            Intent intent = new Intent(TestBindActivity.this, TestCodeinActivity.class);
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
}
