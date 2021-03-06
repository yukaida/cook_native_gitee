package com.ebanswers.kitchendiary.mvp.view.helper;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ebanswers.baselibrary.widget.ClearEditText;
import com.ebanswers.kitchendiary.R;
import com.ebanswers.kitchendiary.adapter.MoreWonderfulAdapter;
import com.ebanswers.kitchendiary.bean.SquareInfo;
import com.ebanswers.kitchendiary.common.CommonLazyFragment;
import com.ebanswers.kitchendiary.constant.AppConstant;
import com.ebanswers.kitchendiary.eventbus.Event;
import com.ebanswers.kitchendiary.eventbus.EventBusUtil;
import com.ebanswers.kitchendiary.mvp.contract.BaseView;
import com.ebanswers.kitchendiary.mvp.presenter.HelperPresenter;
import com.ebanswers.kitchendiary.mvp.view.base.HomeActivity;
import com.ebanswers.kitchendiary.mvp.view.base.WebActivity;
import com.ebanswers.kitchendiary.mvp.view.base.WelActivity;
import com.ebanswers.kitchendiary.network.response.BaseResponse;
import com.ebanswers.kitchendiary.utils.GlideApp;
import com.ebanswers.kitchendiary.utils.NetworkUtils;
import com.ebanswers.kitchendiary.utils.SPUtils;
import com.hjq.bar.TitleBar;
import com.hjq.toast.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * desc   :
 */
public class HelperFragment extends CommonLazyFragment implements BaseView.HelperView {


    @BindView(R.id.tb_test_b_title)
    TitleBar tbTestBTitle;
    @BindView(R.id.order_rv)
    RecyclerView orderRv;
    @BindView(R.id.order_swrl)
    SmartRefreshLayout orderSwrl;
    @BindView(R.id.steaming_roast_recipe_ll)
    LinearLayout steamingRoastRecipeLl;
    @BindView(R.id.brand_zone_ll)
    LinearLayout brandZoneLl;
    @BindView(R.id.steam_oven_use_ll)
    LinearLayout steamOvenUseLl;
    @BindView(R.id.helper_search_et)
    ClearEditText helperSearchEt;
    @BindView(R.id.helper_search_tv)
    TextView helperSearchTv;
    @BindView(R.id.helper_search_ll)
    LinearLayout helperSearchLl;
    @BindView(R.id.helper_search_bgiv)
    ImageView helperSearchBgiv;
    @BindView(R.id.helper_srv)
    NestedScrollView helperSrv;
    private Unbinder unbinder;

    private View noDataView;
    private MoreWonderfulAdapter moreWonderfulAdapter;
    private HelperPresenter helperPresenter;

    private boolean isSimpleClick = false;
    private int currentPosition = 0;
    private boolean isRefresh  = false;

    public static HelperFragment newInstance() {
        return new HelperFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_test_b_title;
    }

    @Override
    protected void initView() {

        helperPresenter = new HelperPresenter(this, this);

        List<SquareInfo.DataBean> cookingActivityInfos = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            cookingActivityInfos.add(new SquareInfo.DataBean());
        }
        moreWonderfulAdapter = new MoreWonderfulAdapter();
        moreWonderfulAdapter.notifyDataSetChanged();
        orderRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    GlideApp.with(getContext()).resumeRequests();//恢复Glide加载图片
                }else {
                    GlideApp.with(getContext()).pauseRequests();//禁止Glide加载图片
                }
            }
        });
        orderRv.setLayoutManager(new LinearLayoutManager(getContext()));
        orderRv.setHasFixedSize(true);
        orderRv.setNestedScrollingEnabled(false);
        orderRv.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        orderRv.setAdapter(moreWonderfulAdapter);
        moreWonderfulAdapter.notifyDataSetChanged();
        moreWonderfulAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                SquareInfo.DataBean item = (SquareInfo.DataBean) adapter.getItem(position);
                if (view.getId() == R.id.focus_status_iv) {

                    if (!HomeActivity.isLoginMethod()){
                        if (SPUtils.getIsLogin()){
                            String create_user = item.getCreate_user();
                            isSimpleClick = true;
                            currentPosition = position;
                            String userId =  (String)SPUtils.get(AppConstant.USER_ID, "");
                            if (item.isIs_subscribe()) {
                                helperPresenter.follower("cancel", userId, create_user);
                            } else {
                                helperPresenter.follower("", userId, create_user);
                            }

                        }else {
//                    LoginActivity.openActivity(getContext(),LoginActivity.TYPE_PHONE_CODE);
                            startActivity(new Intent(getContext(), WelActivity.class));
                        }
                    }else {
//                    LoginActivity.openActivity(getContext(),LoginActivity.TYPE_PHONE_CODE);
                        startActivity(new Intent(getContext(), WelActivity.class));
                    }

                } else if (view.getId() == R.id.goods_builder_iv || view.getId() == R.id.goods_builder_tv) {
                    Intent intent = new Intent(getContext(), WebActivity.class);
                    intent.putExtra("url", "https://wechat.53iq.com/tmp/kitchen/food/diary?openid=" + item.getCreate_user() + "&my_openid=" + HomeActivity.getOpenId());
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(getContext(), WebActivity.class);
                    String openid = (String) SPUtils.get(AppConstant.USER_ID, "");
                    String url = "https://wechat.53iq.com/tmp/kitchen/diary/" + item.getDiary_id() + "/detail?code=123&openid=" + openid;
                    intent.putExtra("url",url);
                    startActivity(intent);
                }
            }
        });


        noDataView = getLayoutInflater().inflate(R.layout.empty_view, (ViewGroup) orderRv.getParent(), false);
        TextView emptyTv = noDataView.findViewById(R.id.empty_tv);
        ImageView emptyIv = noDataView.findViewById(R.id.empty_iv);
        emptyTv.setText(getResources().getString(R.string.push_data_empty));
        orderSwrl.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                isRefresh = false;
                String userId =  (String) SPUtils.get(AppConstant.USER_ID, "");
                if (moreWonderfulAdapter != null) {
                    List<SquareInfo.DataBean> data = moreWonderfulAdapter.getData();
                    if (data.size() > 0) {
                        helperPresenter.loadSquareInfo(userId, String.valueOf(data.size()),true,data.get(data.size()-1).getDiary_id());
                    }
                }

            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                helperSearchEt.setText("");
                helperSearchLl.setVisibility(View.GONE);
                helperSearchBgiv.setVisibility(View.VISIBLE);

                isRefresh  = true;
                String userId =  (String) SPUtils.get(AppConstant.USER_ID, "");
                helperPresenter.loadSquareInfo(userId, "0",true,"");
            }
        });

    }


    @Override
    protected void initData() {
        isRefresh  = true;
        String userId =  (String)SPUtils.get(AppConstant.USER_ID, "");
        helperPresenter.loadSquareInfo(userId, "0",true,"");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public boolean isStatusBarEnabled() {
        // 使用沉浸式状态栏
        return !super.isStatusBarEnabled();
    }

    @Override
    protected boolean statusBarDarkFont() {
        return super.statusBarDarkFont();
    }

    @OnClick({R.id.steaming_roast_recipe_ll, R.id.brand_zone_ll, R.id.steam_oven_use_ll, R.id.helper_search_tv, R.id.helper_search_bgiv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.steaming_roast_recipe_ll:
                Intent intent1 = new Intent(getContext(), WebActivity.class);
                intent1.putExtra("url", "http://wechat.53iq.com/tmp/static/html/cookbook.html");
                startActivity(intent1);
                break;
            case R.id.brand_zone_ll:
                Intent intent2 = new Intent(getContext(), WebActivity.class);
                intent2.putExtra("url", "http://wechat.53iq.com/tmp/static/html/areaChoice/areaChoice.html");
                startActivity(intent2);
                break;
            case R.id.steam_oven_use_ll:
                Intent intent3 = new Intent(getContext(), WebActivity.class);
                intent3.putExtra("url", "http://wechat.53iq.com/tmp/static/html/Instruction_box/Instruction.html");
                startActivity(intent3);
                break;
            case R.id.helper_search_tv:
                String search = helperSearchEt.getText().toString().trim();
                if (!TextUtils.isEmpty(search)) {
                    Intent intent6 = new Intent(getContext(), WebActivity.class);
                    intent6.putExtra("url", "http://wechat.53iq.com/tmp/kitchen/cookbook/" + search);
//                    intent6.putExtra("url", "https://wechat.53iq.com/tmp/search?action=search&content=" + search);
                    startActivity(intent6);
                }

                break;
            case R.id.helper_search_bgiv:
                helperSearchLl.setVisibility(View.VISIBLE);
                helperSearchBgiv.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void setFollowerData(BaseResponse data) {
        if (data != null) {
            if (data.getCode() == 0) {
//                ToastUtils.show("关注成功");
//                helperPresenter.loadSquareInfo("tmp_user");
                EventBusUtil.sendEvent(new Event(Event.EVENT_UPDATE_MINE,"刷新我的界面"));
                SquareInfo.DataBean item =  moreWonderfulAdapter.getItem(currentPosition);
                if (item.isIs_subscribe()) {
                    item.setIs_subscribe(false);
//                    item.setMaster_rank(item.getMaster_rank() - 1);
                } else {
                    item.setIs_subscribe(true);
//                    item.setMaster_rank(item.getMaster_rank() + 1);
                }
                moreWonderfulAdapter.setData(currentPosition, item);
                moreWonderfulAdapter.notifyItemChanged(currentPosition);
            }
        }
    }

    @Override
    public void setMoreData(SquareInfo data) {
        orderSwrl.finishRefresh();
        orderSwrl.finishLoadMore();

        if (data != null) {
            if (data.getData() != null) {
                if (data.getData().size() > 0) {

//                    if (isSimpleClick) {
//                        moreWonderfulAdapter.setData(currentPosition, data.getData().get(currentPosition));
//                        isSimpleClick = false;
//                    } else {
                        if (isRefresh){
                            if (data.getData().size() < 5) {
                                moreWonderfulAdapter.loadMoreEnd();
                            }
                            moreWonderfulAdapter.setNewData(data.getData());
                            moreWonderfulAdapter.notifyDataSetChanged();
                        }else {
                            moreWonderfulAdapter.addData(data.getData());
//                            moreWonderfulAdapter.notifyItemRangeInserted(moreWonderfulAdapter.getData().size() - data.getData().size(),data.getData().size());
                            moreWonderfulAdapter.notifyDataSetChanged();
//                        }
                    }
                }
            }

        }
    }

    @Override
    public void netWorkError(String result) {
        if (NetworkUtils.isNetworkAvailable(getContext())){
            ToastUtils.show(result);
        }else {
            ToastUtils.show("无可用网络！");
        }
        orderSwrl.finishRefresh();
    }

    public void scroolTopRefresh() {
        if (helperSrv.getScaleY() != 0) {
            helperSrv.fullScroll(ScrollView.FOCUS_UP);
        }
        initData();

    }
}