package com.ebanswers.kitchendiary.mvp.view.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.ebanswers.kitchendiary.R;
import com.zhy.autolayout.AutoLinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FullActivity extends BaseActivity {
    public static final int TYPE_INPUT_BARCODE = 131;
    public static final int TYPE_LOGIN_POLICY = 132;
    //搜索ACP
    public static final int TYPE_ACP_SEARCH = 108;
    //搜索acp关联菜谱
    public static final int TYPE_SEARCHE_RECIPE = 814;
    @BindView(R.id.id_img_title_back)
    ImageView idImgTitleBack;
    @BindView(R.id.tv_full_title)
    TextView tvFullTitle;
    @BindView(R.id.layout_top)
    AutoLinearLayout layoutTop;

    public static void openActivity(Context context, int type) {
        Intent intent1 = new Intent(context, FullActivity.class);
        intent1.putExtra("type", type);
        context.startActivity(intent1);
    }

    public static void openActivity(Context context, int type, String url) {
        Intent intent1 = new Intent(context, FullActivity.class);
        intent1.putExtra("type", type);
        intent1.putExtra("url", url);
        context.startActivity(intent1);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_full;
    }

    @Override
    protected void onCreateNext(@Nullable Bundle savedInstanceState) {
        initViews();
    }

    private void initViews() {

    }

    public void setFullTitle(String title) {
        Log.d("FullActivity", "setFullTitle: " + title);
        tvFullTitle.setText(title);
    }

    private void setTopVisibility(boolean isShow) {
        layoutTop.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    @OnClick({R.id.id_img_title_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_img_title_back:
                setResult(Activity.RESULT_OK);
                finish();
                break;
            default:
        }
    }

    @Override
    protected View getWifiLostView() {
        return findViewById(R.id.id_ll_network);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (null != manager) {
            manager.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
