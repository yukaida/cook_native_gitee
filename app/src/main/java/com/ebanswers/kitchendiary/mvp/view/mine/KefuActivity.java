package com.ebanswers.kitchendiary.mvp.view.mine;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hjq.bar.TitleBar;
import com.hjq.permissions.OnPermission;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.hjq.toast.ToastUtils;
import com.ebanswers.kitchendiary.R;
import com.ebanswers.kitchendiary.common.CommonActivity;
import com.ebanswers.kitchendiary.constant.AppConstant;
import com.ebanswers.kitchendiary.mvp.presenter.KefuPresenter;
import com.ebanswers.kitchendiary.mvp.contract.BaseView;
import com.ebanswers.kitchendiary.network.response.KefuResponse;
import com.ebanswers.kitchendiary.utils.SPUtils;
import com.ebanswers.kitchendiary.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 客服
 */
public class KefuActivity extends CommonActivity implements BaseView.KefuView, OnPermission {

    @BindView(R.id.kefu_title)
    TitleBar kefuTitle;
    @BindView(R.id.kefu_line_tv)
    TextView kefuLineTv;
    @BindView(R.id.kefu_line_ll)
    LinearLayout kefuLineLl;
    @BindView(R.id.data_email_tv)
    TextView dataEmailTv;
    @BindView(R.id.data_email_ll)
    LinearLayout dataEmailLl;
    @BindView(R.id.join_line_tv)
    TextView joinLineTv;
    @BindView(R.id.join_line_ll)
    LinearLayout joinLineLl;
    @BindView(R.id.technical_support_email_tv)
    TextView technicalSupportEmailTv;
    @BindView(R.id.technical_support_ll)
    LinearLayout technicalSupportLl;
    private KefuPresenter presenter;
    private String phone;




    @Override
    protected int getLayoutId() {
        return R.layout.activity_kefu;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.kefu_title;
    }

    @Override
    protected void initView() {
        getStatusBarConfig().statusBarDarkFont(statusBarDarkFont()).statusBarColor(R.color.app_theme).init();

        presenter = new KefuPresenter(this,this);
    }

    @Override
    protected void initData() {
        String username = (String) SPUtils.get(AppConstant.USER_NAME, "");
        if (!TextUtils.isEmpty(username)){
            presenter.getInfo(username,SPUtils.getToken());
        }else {
            Toast.makeText(this,"用户为空",Toast.LENGTH_LONG).show();
        }

    }


    @Override
    public void setData(KefuResponse data) {
        if (data != null){
            if (!TextUtils.isEmpty(data.getTel())){
                kefuLineTv.setText(data.getTel());
            }

            if (!TextUtils.isEmpty(data.getEmail())){
                dataEmailTv.setText(data.getEmail());
            }

            if (!TextUtils.isEmpty(data.getZstel())){
                joinLineTv.setText(data.getZstel());
            }

            if (!TextUtils.isEmpty(data.getJishu())){
                technicalSupportEmailTv.setText(data.getJishu());
            }

        }
    }

    @Override
    public void netWorkError(String msg) {
        ToastUtils.show(msg);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.kefu_line_ll, R.id.data_email_ll, R.id.join_line_ll, R.id.technical_support_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.kefu_line_ll:
                phone = "tele";
                if (!Utils.isPad(this)){
                    requestFilePermission();
                }else {
                    ToastUtils.show("无电话功能");
                }
                break;
            case R.id.data_email_ll:
                String dataEmail = dataEmailTv.getText().toString().trim();
                //获取剪贴板管理器：
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                // 创建普通字符型ClipData
                ClipData mClipData = ClipData.newPlainText("Label", dataEmail);
                // 将ClipData内容放到系统剪贴板里。
                cm.setPrimaryClip(mClipData);
                ToastUtils.show("邮箱地址已经复制");
                break;
            case R.id.join_line_ll:
                phone = "mobile";
                if (!Utils.isPad(this)){
                    requestFilePermission();
                }else {
                    ToastUtils.show("无电话功能");
                }
                break;
            case R.id.technical_support_ll:
                String technicalSupportEmail = technicalSupportEmailTv.getText().toString().trim();
                //获取剪贴板管理器：
                ClipboardManager cm1 = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                // 创建普通字符型ClipData
                ClipData mClipData1 = ClipData.newPlainText("Label", technicalSupportEmail);
                // 将ClipData内容放到系统剪贴板里。
                cm1.setPrimaryClip(mClipData1);
                ToastUtils.show("邮箱地址已经复制");
                break;
        }
    }

    private void requestFilePermission() {
        XXPermissions.with(this)
                .permission(Permission.CALL_PHONE)
                .request(this);

    }

    @Override
    public void hasPermission(List<String> granted, boolean isAll) {
        if (!TextUtils.isEmpty(phone)){
            if (phone.equals("tele")){
            String tel = kefuLineTv.getText().toString().trim();
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + tel));
            startActivity(intent);
            }else if (phone.equals("mobile")){
                String mobile = joinLineTv.getText().toString().trim();
                Intent intent1 = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + mobile));
                startActivity(intent1);
            }
        }
    }

    @Override
    public void noPermission(List<String> denied, boolean quick) {
        if (quick) {
            ToastUtils.show("没有权限访问文件，请手动授予权限");
            XXPermissions.gotoPermissionSettings(this, true);
        }else {
            ToastUtils.show("请先授予打电话权限");
            getWindow().getDecorView().postDelayed(new Runnable() {
                @Override
                public void run() {
                    requestFilePermission();
                }
            }, 2000);
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    @Override
    public boolean isStatusBarEnabled() {
        return super.isStatusBarEnabled();
    }

    @Override
    public boolean statusBarDarkFont() {
        return false;
    }
}
