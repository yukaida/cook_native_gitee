package com.ebanswers.kitchendiary.mvp.view.base;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.ebanswers.kitchendiary.R;
import com.ebanswers.kitchendiary.config.Constans;
import com.ebanswers.kitchendiary.widget.CommunityWebView;
import com.ebanswers.kitchendiary.widget.ScrollSwipeRefreshLayout;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import butterknife.BindView;
import butterknife.OnClick;

public class BindPhoneActivity extends BaseActivity implements TextWatcher {

    @BindView(R.id.id_img_title_back)
    ImageView backImg;
    @BindView(R.id.id_tv_title_name)
    TextView titleTv;
    @BindView(R.id.id_img_title_setting)
    ImageView setImg;
    @BindView(R.id.id_pb_bind_phone)
    ProgressBar bindPhoneProgressBar;
    @BindView(R.id.id_ssrl_bind_phone)
    ScrollSwipeRefreshLayout refreshLayout;
    @BindView(R.id.id_cw_bind_phone_activity)
    CommunityWebView webView;

    private String open_id;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_bind_phone;
    }

    @Override
    protected void onCreateNext(@Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        if (getIntent() != null){
            open_id = getIntent().getStringExtra("open_id");
        }
        initView();
        if (open_id != null){
            loadBindPhonePage(open_id);
        }
    }

    public void initView(){
        titleTv.setText(R.string.bind_phone);
        setImg.setVisibility(View.INVISIBLE);
        webView.setSwipeRefreshLayout(refreshLayout);
        refreshLayout.setViewGroup(webView);
        refreshLayout.setColorSchemeResources(R.color.main_color);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(true);
                webView.reload();
            }
        });
//        setSwipeBackEnable(false);
        bindPhoneProgressBar.setMax(100);
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView webView, int i) {
                super.onProgressChanged(webView, i);
                if (100 == i){
                    bindPhoneProgressBar.setVisibility(View.GONE);
                    refreshLayout.setRefreshing(false);
                }else {
                    bindPhoneProgressBar.setProgress(i);
                }
            }
        });
    }

    public void loadBindPhonePage(String openId){
        String Url = Constans.Bind_phone + openId;
        Log.d("Snail", "loadBindPhonePage: " + Url);
        webView.loadUrl(Url);
    }

    @OnClick({R.id.id_img_title_back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.id_img_title_back:
                finish();
                break;
        }
    }

    @Subscriber(tag = "BindPhoneSuccecss")
    public void bindPhoneSuccess(boolean b){
        Intent intent = new Intent(this,HomeActivity.class);
        intent.putExtra("open_id",open_id);
        startActivity(intent);
        finish();
    }
    @Override
    protected View getWifiLostView() {
        return null;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
