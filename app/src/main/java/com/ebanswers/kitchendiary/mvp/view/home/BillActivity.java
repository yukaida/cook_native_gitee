package com.ebanswers.kitchendiary.mvp.view.home;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hjq.bar.TitleBar;
import com.hjq.toast.ToastUtils;
import com.ebanswers.kitchendiary.R;
import com.ebanswers.kitchendiary.common.CommonActivity;
import com.ebanswers.kitchendiary.constant.AppConstant;
import com.ebanswers.kitchendiary.mvp.contract.BillContract;
import com.ebanswers.kitchendiary.mvp.model.OrderListInfo;
import com.ebanswers.kitchendiary.mvp.presenter.BillPresenter;
import com.ebanswers.kitchendiary.utils.LogUtils;
import com.ebanswers.kitchendiary.utils.SPUtils;
import com.ebanswers.kitchendiary.widget.datepicker.CustomDatePicker;
import com.ebanswers.kitchendiary.widget.datepicker.DateFormatUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class BillActivity extends CommonActivity implements SwipeRefreshLayout.OnRefreshListener, BillContract.View, BaseQuickAdapter.RequestLoadMoreListener {


    @BindView(R.id.title_my_bill)
    TitleBar titleMyBill;
    @BindView(R.id.day_before)
    TextView dayBefore;
    @BindView(R.id.current_calendar_tv)
    TextView currentCalendarTv;
    @BindView(R.id.time_ll)
    LinearLayout timeLl;
    @BindView(R.id.day_after)
    TextView dayAfter;
    @BindView(R.id.amount_real_tv)
    TextView amountRealTv;
    @BindView(R.id.amount_refoud_tv)
    TextView amountRefoudTv;
    @BindView(R.id.bill_rv)
    RecyclerView billRv;
    @BindView(R.id.bill_swrl)
    SwipeRefreshLayout billSwrl;

    private CustomDatePicker mDatePicker;
    private String currentDay;
    private String jtime;
    private String ktime;
    private String username;
    private BillPresenter presenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bill;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.title_my_bill;
    }

    @Override
    protected void initView() {
        presenter = new BillPresenter(this,this);
        billSwrl.setColorSchemeColors(Color.RED,Color.GREEN,Color.BLUE);
        billSwrl.setOnRefreshListener(this);
        initDatePicker();

        currentDay = DateFormatUtils.long2Str(System.currentTimeMillis(), DateFormatUtils.YMD);
        currentCalendarTv.setText(currentDay + "(今天)");

        ktime = String.valueOf(DateFormatUtils.str2Long(currentDay,DateFormatUtils.YMD) /1000);
        jtime = String.valueOf(DateFormatUtils.str2Long(currentDay,DateFormatUtils.YMD)/1000 + 24*60*60);


        billRv.setLayoutManager(new LinearLayoutManager(this));


    }

    @Override
    protected void initData() {
        username = (String) SPUtils.get(AppConstant.USER_NAME, "");
        if (!TextUtils.isEmpty(username)){
            presenter.loadOrderList(username,SPUtils.getToken(),ktime,jtime);
            presenter.loadOrder(username,SPUtils.getToken(),ktime,jtime);
        }else {
            Toast.makeText(this,"用户为空",Toast.LENGTH_LONG).show();
        }
    }

    @OnClick({R.id.day_before, R.id.time_ll, R.id.day_after})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.day_before:
                String currentCalendar = currentCalendarTv.getText().toString();
                ktime = String.valueOf(DateFormatUtils.str2Long(currentCalendar.substring(0, 10), DateFormatUtils.YMD)/1000 - 24 * 60 * 60);
                jtime = String.valueOf(DateFormatUtils.str2Long(currentCalendar.substring(0, 10), DateFormatUtils.YMD)/1000);
                if (currentDay.equals(DateFormatUtils.long2Str(Long.parseLong(ktime)*1000, DateFormatUtils.YMD))) {
                    currentCalendarTv.setText(currentDay + "(今天)");
                } else {
                    currentCalendarTv.setText(DateFormatUtils.long2Str(Long.parseLong(ktime)*1000, DateFormatUtils.YMD));
                }
                if (!TextUtils.isEmpty(username)) {
                    presenter.loadOrderList(username,SPUtils.getToken(),ktime,jtime);
                    presenter.loadOrder(username,SPUtils.getToken(),ktime,jtime);
                } else {
                    Toast.makeText(this, "用户为空", Toast.LENGTH_LONG).show();
                }

                break;
            case R.id.time_ll:
                String currentCalendar1 = currentCalendarTv.getText().toString();
                if (!TextUtils.isEmpty(currentCalendar1)){
                    mDatePicker.show(currentCalendar1.substring(0, 10));
                }

                break;
            case R.id.day_after:
                String currentCalendar2 = currentCalendarTv.getText().toString();
                ktime = String.valueOf(DateFormatUtils.str2Long(currentCalendar2.substring(0, 10), DateFormatUtils.YMD)/1000 + 24 * 60 * 60 );
                jtime = String.valueOf(DateFormatUtils.str2Long(currentCalendar2.substring(0, 10), DateFormatUtils.YMD)/1000 + 48 * 60 * 60 );
                if (currentDay.equals(DateFormatUtils.long2Str(Long.parseLong(ktime)*1000, DateFormatUtils.YMD))) {
                    currentCalendarTv.setText(currentDay + "(今天)");
                } else {
                    currentCalendarTv.setText(DateFormatUtils.long2Str(Long.parseLong(ktime)*1000, DateFormatUtils.YMD));
                }
                if (!TextUtils.isEmpty(username)) {
                    presenter.loadOrderList(username,SPUtils.getToken(),ktime,jtime);
                    presenter.loadOrder(username,SPUtils.getToken(),ktime,jtime);
                } else {
                    Toast.makeText(this, "用户为空", Toast.LENGTH_LONG).show();
                }


                break;
        }
    }

    private void initDatePicker() {
        long beginTimestamp = DateFormatUtils.str2Long("2000-01-01", DateFormatUtils.YMD);
//        long endTimestamp = System.currentTimeMillis() + Long.valueOf(100*365*24*60*60*1000);
//        LogUtils.d("--------"+ System.currentTimeMillis());
        long endTimestamp = DateFormatUtils.str2Long("2100-12-31", DateFormatUtils.YMD);
//        LogUtils.d("========"+ endTimestamp);

        // 通过时间戳初始化日期，毫秒级别
        mDatePicker = new CustomDatePicker(this, new CustomDatePicker.Callback() {
            @Override
            public void onTimeSelected(long timestamp) {
                String date = DateFormatUtils.long2Str(timestamp, DateFormatUtils.YMD);
                if (currentDay.equals(date)){
                    currentCalendarTv.setText(currentDay+ "(今天)");
                }else {
                    currentCalendarTv.setText(date);
                }

                ktime = String.valueOf(DateFormatUtils.str2Long(date,DateFormatUtils.YMD)/1000);
                jtime = String.valueOf(DateFormatUtils.str2Long(date,DateFormatUtils.YMD)/1000 + 24*60*60);

                if (!TextUtils.isEmpty(username)){
                    presenter.loadOrderList(username,SPUtils.getToken(),ktime,jtime);
                    presenter.loadOrder(username,SPUtils.getToken(),ktime,jtime);
                }else {
                    Toast.makeText(BillActivity.this,"用户为空",Toast.LENGTH_LONG).show();
                }

            }
        }, beginTimestamp, endTimestamp);
        // 不允许点击屏幕或物理返回键关闭
        mDatePicker.setCancelable(false);
        // 不显示时和分
        mDatePicker.setCanShowPreciseTime(DateFormatUtils.YMD);
        // 不允许循环滚动
        mDatePicker.setScrollLoop(false);
        // 不允许滚动动画
        mDatePicker.setCanShowAnim(false);
    }

    @Override
    public boolean isStatusBarEnabled() {
        return super.isStatusBarEnabled();
    }

    @Override
    public boolean statusBarDarkFont() {
        return !super.statusBarDarkFont();
    }

    @Override
    public void onRefresh() {

        if (!TextUtils.isEmpty(username)){
            presenter.loadOrderList(username,SPUtils.getToken(),ktime,jtime);
        }else {
            Toast.makeText(BillActivity.this,"用户为空",Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onLoadMoreRequested() {
//        orderInfoAdapter.setEnableLoadMore();
        if (!TextUtils.isEmpty(username)){
            presenter.loadOrderList(username,SPUtils.getToken(),ktime,jtime);
        }else {
            Toast.makeText(BillActivity.this,"用户为空",Toast.LENGTH_LONG).show();
        }


    }

    @Override
    public void setData(List<OrderListInfo> data) {

        LogUtils.d("========" + data.size());
        if (data != null){
//            orderInfoAdapter.setNewData(data);
//            orderInfoAdapter.notifyDataSetChanged();
        }

        if (billSwrl.isRefreshing()){
            billSwrl.setEnabled(true);
            billSwrl.setRefreshing(false);
        }

//        if (orderInfoAdapter.isLoadMoreEnable()){
//            orderInfoAdapter.loadMoreEnd();
//            orderInfoAdapter.setEnableLoadMore(true);
//        }
    }

    @Override
    public void netWorkError(String msg) {
        ToastUtils.show(msg);

        if (billSwrl.isRefreshing()){
            billSwrl.setEnabled(true);
            billSwrl.setRefreshing(false);
        }

//        if (orderInfoAdapter.isLoadMoreEnable()){
//            orderInfoAdapter.loadMoreEnd();
//            orderInfoAdapter.setEnableLoadMore(true);
//        }
    }

}
