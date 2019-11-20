package com.ebanswers.kitchendiary.mvp.view.home;

import android.content.Intent;
import android.net.Uri;
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

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ebanswers.baselibrary.widget.ClearEditText;
import com.ebanswers.kitchendiary.R;
import com.ebanswers.kitchendiary.adapter.CookingActivityAdapter;
import com.ebanswers.kitchendiary.adapter.ExpertStoryAdapter;
import com.ebanswers.kitchendiary.bean.HomePageInfo;
import com.ebanswers.kitchendiary.bean.RecommendForYou;
import com.ebanswers.kitchendiary.bean.SquareInfo;
import com.ebanswers.kitchendiary.common.CommonLazyFragment;
import com.ebanswers.kitchendiary.constant.AppConstant;
import com.ebanswers.kitchendiary.eventbus.Event;
import com.ebanswers.kitchendiary.eventbus.EventBusUtil;
import com.ebanswers.kitchendiary.mvp.contract.BaseView;
import com.ebanswers.kitchendiary.mvp.presenter.HomePresenter;
import com.ebanswers.kitchendiary.mvp.view.base.HomeActivity;
import com.ebanswers.kitchendiary.mvp.view.base.WebActivity;
import com.ebanswers.kitchendiary.network.response.BaseResponse;
import com.ebanswers.kitchendiary.utils.SPUtils;
import com.hjq.bar.TitleBar;
import com.hjq.permissions.OnPermission;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.hjq.toast.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * desc   : 项目炫酷效果示例
 */
public class HomeFragment extends CommonLazyFragment implements BaseView.HomeView, OnPermission {


    @BindView(R.id.home_title)
    TitleBar homeTitle;
    @BindView(R.id.cooking_activity_rv)
    RecyclerView cookingActivityRv;
    @BindView(R.id.more_wonderful_tv)
    TextView moreWonderfulTv;
    @BindView(R.id.expert_story_tv)
    TextView expertStoryTv;
    @BindView(R.id.more_iv)
    ImageView moreIv;
    @BindView(R.id.more_wonderful_rv)
    RecyclerView moreWonderfulRv;
    @BindView(R.id.hot_recipe_ll)
    LinearLayout hotRecipeLl;
    @BindView(R.id.brand_area_ll)
    LinearLayout brandAreaLl;
    @BindView(R.id.integral_punching_ll)
    LinearLayout integralPunchingLl;
    @BindView(R.id.message_ll)
    LinearLayout messageLl;
    @BindView(R.id.activity_more_tv)
    TextView activityMoreTv;
    @BindView(R.id.home_swrl)
    SmartRefreshLayout homeSwrl;
    @BindView(R.id.search_et)
    ClearEditText searchEt;
    @BindView(R.id.search_tv)
    TextView searchTv;
    @BindView(R.id.search_ll)
    LinearLayout searchLl;
    @BindView(R.id.search_bgiv)
    ImageView searchBgiv;
    @BindView(R.id.home_srv)
    NestedScrollView homeSrv;
    private HomePresenter homePresenter;
    private CookingActivityAdapter cookingActivityAdapter;
    //    private MoreWonderfulAdapter moreWonderfulAdapter;
    private ExpertStoryAdapter expertStoryAdapter;

    private boolean isSimpleClick = false;
    private boolean isRefresh = false;
    private int currentPosition = 0;
    private int msg_num;


    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.home_title;
    }


    @Override
    protected void initView() {
        String userId =  (String)SPUtils.get(AppConstant.USER_ID, "");

        EventBusUtil.register(this);
        homePresenter = new HomePresenter(this, this);



        cookingActivityRv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        cookingActivityAdapter = new CookingActivityAdapter();
        cookingActivityRv.setAdapter(cookingActivityAdapter);

        cookingActivityAdapter.notifyDataSetChanged();
        cookingActivityAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                RecommendForYou item = (RecommendForYou) adapter.getItem(position);
                Intent intent = new Intent(getContext(), WebActivity.class);
                intent.putExtra("url", item.getUrl());
                startActivity(intent);
            }
        });

        expertStoryAdapter = new ExpertStoryAdapter();

        moreWonderfulRv.setHasFixedSize(true);
        moreWonderfulRv.setNestedScrollingEnabled(false);
        moreWonderfulRv.setLayoutManager(new LinearLayoutManager(getContext()));

        moreWonderfulRv.setAdapter(expertStoryAdapter);

        expertStoryAdapter.notifyDataSetChanged();
        expertStoryAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                SquareInfo.DataBean item = (SquareInfo.DataBean) adapter.getItem(position);
                if (view.getId() == R.id.focu_iv) {
                    isSimpleClick = true;
                    currentPosition = position;
                    String create_user = item.getCreate_user();
                    String userId =  (String)SPUtils.get(AppConstant.USER_ID, "");
                    if (item.isIs_subscribe()) {
                        homePresenter.follower("cancel", userId, create_user);
                    } else {
                        homePresenter.follower("", userId, create_user);
                    }
                } else if (view.getId() == R.id.cooking_username_tv || view.getId() == R.id.cooking_user_iv) {
                    Intent intent = new Intent(getContext(), WebActivity.class);
                    intent.putExtra("url", "https://wechat.53iq.com/tmp/kitchen/food/diary?openid=" + item.getCreate_user());
                    startActivity(intent);
                }
            }
        });

        homeSwrl.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                isRefresh = false;
                if (expertStoryAdapter != null) {
                    List<SquareInfo.DataBean> data = expertStoryAdapter.getData();
                    if (data.size() > 0) {
                        homePresenter.loadSquareInfo(userId, String.valueOf(data.size()),true);
                    }
                }
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                isRefresh = true;
                homePresenter.loadSquareInfo(userId, "0",true);
            }
        });
    }


    @Override
    protected void initData() {
        isRefresh = true;
        String userId =  (String)SPUtils.get(AppConstant.USER_ID, "");
        homePresenter.loadInfo(userId);
        homePresenter.loadSquareInfo(userId,"0", false);
    }

    @Override
    public void onStart() {
        super.onStart();
       /* msg_num = (int) SPUtils.get("msg_num",0);
        if (msg_num>0){
            messageNumTv.setVisibility(View.VISIBLE);
            if (msg_num < 100){
                messageNumTv.setText(msg_num + "");
            }else {
                messageNumTv.setText("···");
            }

        }else {
            messageNumTv.setVisibility(View.GONE);
        }*/
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public boolean isStatusBarEnabled() {
        // 使用沉浸式状态栏
        return !super.isStatusBarEnabled();
    }

    @Override
    protected boolean statusBarDarkFont() {
        return super.isStatusBarEnabled();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBusUtil.unregister(this);
    }


    @Override
    public void setData(HomePageInfo data) {
        homeSwrl.finishRefresh();
        if (data != null) {
            if (data.getRecommend_for_you() != null) {
                if (data.getRecommend_for_you().size() > 0) {
                    cookingActivityAdapter.setNewData(data.getRecommend_for_you());
                    cookingActivityAdapter.notifyDataSetChanged();
                }
            }

        }
    }

    @Override
    public void setMoreData(SquareInfo data) {
        homeSwrl.finishRefresh();
        homeSwrl.finishLoadMore();
        if (data != null) {
            if (data.getData() != null) {
                if (data.getData().size() > 0) {

                        if (isSimpleClick) {
                            expertStoryAdapter.setData(currentPosition, data.getData().get(currentPosition));
                            isSimpleClick = false;
                        } else {
                            if (isRefresh){
                                if (data.getData().size() < 5) {
                                    expertStoryAdapter.loadMoreEnd();
                                }
                                expertStoryAdapter.setNewData(data.getData());
                                expertStoryAdapter.notifyDataSetChanged();
                            }else {
                                expertStoryAdapter.addData(data.getData());
//                                expertStoryAdapter.notifyDataSetChanged();
                                expertStoryAdapter.notifyItemRangeChanged(expertStoryAdapter.getData().size() - 6,expertStoryAdapter.getData().size());
                            }
                        }
                }
            }

        }
    }

    @Override
    public void setFollowerData(BaseResponse data) {
        if (data != null) {
            if (data.getCode() == 0) {
//                ToastUtils.show("关注成功");
//                homePresenter.loadSquareInfo("tmp_user");
                SquareInfo.DataBean item = (SquareInfo.DataBean) expertStoryAdapter.getItem(currentPosition);
                if (item.isIs_subscribe()) {
                    item.setIs_subscribe(false);
                    item.setMaster_rank(item.getMaster_rank() - 1);
                } else {
                    item.setIs_subscribe(true);
                    item.setMaster_rank(item.getMaster_rank() + 1);
                }
                expertStoryAdapter.setData(currentPosition, item);
                expertStoryAdapter.notifyItemChanged(currentPosition);
            }
        }
    }

    @Override
    public void netWorkError(String result) {
        ToastUtils.show(result);
        homeSwrl.finishRefresh();
    }

    private void requestFilePermission() {
        XXPermissions.with(getSupportActivity()).permission(Permission.CALL_PHONE).request(this);

    }

    @Override
    public void hasPermission(List<String> granted, boolean isAll) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "4008004438"));
        getSupportActivity().startActivity(intent);
    }

    @Override
    public void noPermission(List<String> denied, boolean quick) {
        if (quick) {
            ToastUtils.show("没有权限访问文件，请手动授予权限");
            XXPermissions.gotoPermissionSettings(getContext(), true);
        } else {
            ToastUtils.show("请先授予打电话权限");
            getSupportActivity().getWindow().getDecorView().postDelayed(new Runnable() {
                @Override
                public void run() {
                    requestFilePermission();
                }
            }, 2000);
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetMessage(Event message) {

    }


    @OnClick({R.id.search_tv, R.id.search_bgiv, R.id.hot_recipe_ll, R.id.brand_area_ll, R.id.integral_punching_ll, R.id.message_ll, R.id.more_wonderful_tv, R.id.expert_story_tv, R.id.activity_more_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.hot_recipe_ll:
                Intent intent = new Intent(getContext(), WebActivity.class);
                String userId =  (String)SPUtils.get(AppConstant.USER_ID, "");
                intent.putExtra("url", "http://wechat.53iq.com/tmp/kitchen/food/square?code=123&ctg=cookbook&openid=" + userId);
                startActivity(intent);
                break;
            case R.id.brand_area_ll:
                Intent intent1 = new Intent(getContext(), WebActivity.class);
                intent1.putExtra("url", "http://wechat.53iq.com/tmp/static/html/areaChoice/areaChoice.html");
                startActivity(intent1);
                break;
            case R.id.integral_punching_ll:
                Intent intent2 = new Intent(getContext(), WebActivity.class);
                intent2.putExtra("url", "http://wechat.53iq.com/tmp/kitchen/point_goods?code=123");
                startActivity(intent2);
                break;
            case R.id.message_ll:
                SPUtils.put("msg_num", 0);
                ((HomeActivity) getActivity()).setMessageNumTv();
                Intent intent3 = new Intent(getContext(), WebActivity.class);
                intent3.putExtra("url", "http://wechat.53iq.com/tmp/kitchen/relate/me?code=123");
                startActivity(intent3);
                break;
            case R.id.more_wonderful_tv:
                moreWonderfulRv.setVisibility(View.VISIBLE);
                break;
            case R.id.expert_story_tv:
                Intent intent4 = new Intent(getContext(), WebActivity.class);
                intent4.putExtra("url", "http://wechat.53iq.com/tmp/kitchen/rec_articles?code=123&types=user_story");
                startActivity(intent4);

                break;
            case R.id.activity_more_tv:
                Intent intent5 = new Intent(getContext(), WebActivity.class);
                intent5.putExtra("url", "http://wechat.53iq.com/tmp/kitchen/activity_list?code=123");
                startActivity(intent5);

                break;
            case R.id.search_tv:
                String search = searchEt.getText().toString().trim();
                if (!TextUtils.isEmpty(search)) {
                    Intent intent6 = new Intent(getContext(), WebActivity.class);
                    intent6.putExtra("url", "http://wechat.53iq.com/tmp/kitchen/cookbook/" + search);
                    startActivity(intent6);
                }

                break;
            case R.id.search_bgiv:
                searchLl.setVisibility(View.VISIBLE);
                searchBgiv.setVisibility(View.GONE);
                break;
        }
    }


    public void scroolTopRefresh() {
//        ToastUtils.show("首页刷新");
        if (homeSrv.getScaleY() != 0){
            homeSrv.fullScroll(ScrollView.FOCUS_UP);
        }
        initData();
    }

}