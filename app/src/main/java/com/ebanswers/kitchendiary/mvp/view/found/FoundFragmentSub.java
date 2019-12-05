package com.ebanswers.kitchendiary.mvp.view.found;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ebanswers.baselibrary.widget.ClearEditText;
import com.ebanswers.kitchendiary.R;
import com.ebanswers.kitchendiary.adapter.FoundAdapter;
import com.ebanswers.kitchendiary.adapter.FoundTabAdapter;
import com.ebanswers.kitchendiary.adapter.RecommendFocusAdapter;
import com.ebanswers.kitchendiary.bean.AllMsgFound;
import com.ebanswers.kitchendiary.bean.CommentInfo;
import com.ebanswers.kitchendiary.bean.FoodStepinfo;
import com.ebanswers.kitchendiary.bean.FoundHomeInfo;
import com.ebanswers.kitchendiary.bean.FoundLoadMoreInfo;
import com.ebanswers.kitchendiary.bean.FoundTopInfo;
import com.ebanswers.kitchendiary.bean.MasterInfo;
import com.ebanswers.kitchendiary.bean.Stepinfo;
import com.ebanswers.kitchendiary.common.CommonLazyFragment;
import com.ebanswers.kitchendiary.constant.AppConstant;
import com.ebanswers.kitchendiary.eventbus.Event;
import com.ebanswers.kitchendiary.eventbus.EventBusUtil;
import com.ebanswers.kitchendiary.mvp.contract.BaseView;
import com.ebanswers.kitchendiary.mvp.presenter.FoundPresenter;
import com.ebanswers.kitchendiary.mvp.view.base.HomeActivity;
import com.ebanswers.kitchendiary.mvp.view.base.WebActivity;
import com.ebanswers.kitchendiary.mvp.view.base.WelActivity;
import com.ebanswers.kitchendiary.network.response.BaseResponse;
import com.ebanswers.kitchendiary.network.response.FoundTopResponse;
import com.ebanswers.kitchendiary.utils.ImageLoader;
import com.ebanswers.kitchendiary.utils.LogUtils;
import com.ebanswers.kitchendiary.utils.SPUtils;
import com.ebanswers.kitchendiary.utils.Utils;
import com.ebanswers.kitchendiary.widget.decorator.VerticalltemDecoration;
import com.ebanswers.kitchendiary.widget.popupwindow.CustomPopWindow;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hjq.toast.ToastUtils;
import com.previewlibrary.ZoomMediaLoader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static android.app.Activity.RESULT_CANCELED;

/**
 * Create by dongli
 * Create date 2019-10-30
 * desc：
 */
public class FoundFragmentSub extends CommonLazyFragment implements BaseView.FoundView {


    @BindView(R.id.pic_show_rv)
    RecyclerView picShowRv;
    @BindView(R.id.recommend_rv)
    RecyclerView recommendRv;
    @BindView(R.id.found_rv)
    RecyclerView foundRv;
    @BindView(R.id.no_recommend_tv)
    TextView noRecommendTv;
    @BindView(R.id.found_swrl)
    SmartRefreshLayout foundSwrl;
    @BindView(R.id.recommend_ll)
    LinearLayout recommendLl;
    @BindView(R.id.new_msg_num_tv)
    TextView newMsgNumTv;
    @BindView(R.id.new_msg_show_ll)
    LinearLayout newMsgShowLl;
    @BindView(R.id.search_et)
    ClearEditText searchEt;
    @BindView(R.id.search_tv)
    TextView searchTv;
    @BindView(R.id.search_ll)
    LinearLayout searchLl;
    @BindView(R.id.search_bgiv)
    ImageView searchBgiv;
    @BindView(R.id.found_srv)
    NestedScrollView foundSrv;

    private FoundTabAdapter foundTabAdapter;
    private RecommendFocusAdapter recommendFocusAdapter;
    private FoundAdapter foundAdapter;
    private FoundPresenter foundPresenter;

    private boolean isSimpleClick = false;
    private int currentPosition = 0;
    private int recommendPosition = 0;
    private String type = "";
    private CustomPopWindow customPopWindow;
    private CustomPopWindow customPopWindow1;
    private CustomPopWindow customPopWindow2;
    private Bundle bundle;
    CommentInfo commentInfo;
    List<FoodStepinfo> foodStepinfos = new ArrayList<>();

    String userId = (String) SPUtils.get(AppConstant.USER_ID, "");
    private int deletePosition;
    private CommentInfo commentInfo1;

    public static FoundFragmentSub newInstance() {
        return new FoundFragmentSub();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_found_sub;
    }

    @Override
    protected int getTitleBarId() {
        return 0;
    }

    @Override
    protected void initView() {
        ZoomMediaLoader.getInstance().init(new ImageLoader());
        foundPresenter = new FoundPresenter(this, this);
        UMShareListener umShareListener = new UMShareListener() {
            /**
             * @descrption 分享开始的回调
             *
             */
            @Override
            public void onStart(SHARE_MEDIA share_media) {

            }

            /**
             * @descrption 分享成功的回调
             */
            @Override
            public void onResult(SHARE_MEDIA share_media) {

            }

            /**
             * @descrption 分享失败的回调
             */
            @Override
            public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                Toast.makeText(getContext(), "失败" + throwable.getMessage(), Toast.LENGTH_LONG).show();
            }

            /**
             * @descrption 分享取消的回调
             */
            @Override
            public void onCancel(SHARE_MEDIA share_media) {
                Toast.makeText(getContext(), "取消了", Toast.LENGTH_LONG).show();
            }
        };


        boolean recommend = (boolean) SPUtils.get("recommend", true);
        if (!recommend) {
            recommendLl.setVisibility(View.GONE);
            recommendRv.setVisibility(View.GONE);
        }


        picShowRv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        foundTabAdapter = new FoundTabAdapter();
        picShowRv.setAdapter(foundTabAdapter);
        foundTabAdapter.notifyDataSetChanged();
        foundTabAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                FoundTopInfo item = (FoundTopInfo) adapter.getItem(position);
                Intent intent = new Intent(getContext(), WebActivity.class);
                intent.putExtra("url", "https://wechat.53iq.com/tmp/topic/" + item.getTopic_id() + "?code=123&openid=" + userId);
                startActivity(intent);
            }
        });

        recommendRv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recommendFocusAdapter = new RecommendFocusAdapter();
        recommendRv.setAdapter(recommendFocusAdapter);
        recommendFocusAdapter.notifyDataSetChanged();
        recommendFocusAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });

        recommendFocusAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                MasterInfo item = (MasterInfo) adapter.getItem(position);
                if (view.getId() == R.id.focus_user_iv) {
                    type = "recommend";
                    recommendPosition = position;
                    if (item.isIs_subscribe()) {
                        foundPresenter.follower("cancel ", userId, item.getOpenid());
                    } else {
                        foundPresenter.follower("", userId, item.getOpenid());
                    }
                }
            }
        });


        foundRv.setHasFixedSize(true);
        foundRv.setNestedScrollingEnabled(false);
        foundRv.setLayoutManager(new LinearLayoutManager(getContext()));
        foundRv.addItemDecoration(new VerticalltemDecoration(10));

        foundAdapter = new FoundAdapter();
        foundRv.setAdapter(foundAdapter);
        foundAdapter.openLoadAnimation();
        foundAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
        foundAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                AllMsgFound item = (AllMsgFound) adapter.getItem(position);
                switch (view.getId()) {
                    case R.id.share_iv:
//                        showShare(item.getDesc(),item.getHead_url());
                        UMImage image = new UMImage(getContext(), item.getImg_url().get(0));//分享图标
                        image.compressStyle = UMImage.CompressStyle.SCALE;//大小压缩，默认为大小压缩，适合普通很大的图
                        image.compressStyle = UMImage.CompressStyle.QUALITY;//质量压缩，适合长图的分享
//                        压缩格式设置
                        image.compressFormat = Bitmap.CompressFormat.PNG;//用户分享透明背景的图片可以设置这种方式，但是qq好友，微信朋友圈，不支持透明背景图片，会变成黑色
                        String openid = (String) SPUtils.get(AppConstant.USER_ID, "");
                        final UMWeb web = new UMWeb("https://wechat.53iq.com/tmp/kitchen/diary/" + item.getDiary_id() + "/detail?code=123&openid=" + openid);//切记切记 这里分享的链接必须是http开头
                        web.setTitle(item.getTitle());//标题
                        web.setThumb(image);  //缩略图
                        web.setDescription(item.getDesc());//描述

                        new ShareAction(getSupportActivity()).withMedia(web).setDisplayList(SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE).setShareboardclickCallback(new ShareBoardlistener() {
                            @Override
                            public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
                                if (share_media == SHARE_MEDIA.QQ) {
                                    LogUtils.e("点击QQ");
                                    new ShareAction(getSupportActivity()).setPlatform(SHARE_MEDIA.QQ).withMedia(web).setCallback(umShareListener).share();
                                } else if (share_media == SHARE_MEDIA.WEIXIN) {
                                    LogUtils.e("点击微信");
                                    new ShareAction(getSupportActivity()).setPlatform(SHARE_MEDIA.WEIXIN).withMedia(web).setCallback(umShareListener).share();
                                } else if (share_media == SHARE_MEDIA.QZONE) {
                                    new ShareAction(getSupportActivity()).setPlatform(SHARE_MEDIA.QZONE).withMedia(web).setCallback(umShareListener).share();
                                } else if (share_media == SHARE_MEDIA.WEIXIN_CIRCLE) {
                                    new ShareAction(getSupportActivity()).setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE).withMedia(web).setCallback(umShareListener).share();

                                }
                            }
                        }).open();
                        break;
                    case R.id.focu_status_iv:

                        if (SPUtils.getIsLogin()){
                            isSimpleClick = true;
                            currentPosition = position;
                            type = "subscribe";
                            if (item.isIs_subscribe()) {
                                foundPresenter.follower("cancel ", userId, item.getCreate_user());
                            } else {
                                foundPresenter.follower("", userId, item.getCreate_user());
                            }
                        }else {
//                    LoginActivity.openActivity(getContext(),LoginActivity.TYPE_PHONE_CODE);
                            startActivity(new Intent(getContext(), WelActivity.class));
                        }

                        break;
                    case R.id.collection_status_iv:

                        if (SPUtils.getIsLogin()){
                            isSimpleClick = true;
                            currentPosition = position;
                            type = "collection";
                            if (item.isIs_collected()) {
                                foundPresenter.uncollected("cancel_collect", item.getDiary_id());
                            } else {
                                foundPresenter.collected("collect", item.getDiary_id(), item.getCreate_user());
                            }
                        }else {
//                    LoginActivity.openActivity(getContext(),LoginActivity.TYPE_PHONE_CODE);
                            startActivity(new Intent(getContext(), WelActivity.class));
                        }

                        break;
                    case R.id.message_iv:
                        if (SPUtils.getIsLogin()){
                            type = "Comment";
                            isSimpleClick = true;
                            currentPosition = position;
                            String username = (String) SPUtils.get(AppConstant.USER_NAME, "");
                            popupCommentWindow(item.getDiary_id(), userId, username);
                        }else {
//                    LoginActivity.openActivity(getContext(),LoginActivity.TYPE_PHONE_CODE);
                            startActivity(new Intent(getContext(), WelActivity.class));
                        }

                        break;
                    case R.id.like_status_iv:

                        if (SPUtils.getIsLogin()){
                            isSimpleClick = true;
                            currentPosition = position;
                            type = "like";
                            if (item.isIs_liked()) {
                                foundPresenter.islike("unlike", item.getDiary_id(), userId);
                            } else {
                                foundPresenter.islike("like", item.getDiary_id(), userId);
                            }
                        }else {
//                    LoginActivity.openActivity(getContext(),LoginActivity.TYPE_PHONE_CODE);
                            startActivity(new Intent(getContext(), WelActivity.class));
                        }

                        break;
                    case R.id.user_name_tv:
                    case R.id.user_head_iv:
                        Intent intent = new Intent(getContext(), WebActivity.class);
                        intent.putExtra("url", "https://wechat.53iq.com/tmp/kitchen/food/diary?openid=" + item.getCreate_user());
                        startActivity(intent);
                        break;
                    case R.id.user_descrbe_tv:
                    case R.id.share_pic_rv:
                        Intent intent1 = new Intent(getContext(), WebActivity.class);
                        String openid1 = (String) SPUtils.get(AppConstant.USER_ID, "");
                        intent1.putExtra("url", "https://wechat.53iq.com/tmp/kitchen/diary/" + item.getDiary_id() + "/detail?code=123&openid="+ openid1);
                        startActivity(intent1);
                        break;
                }

            }
        });

        foundAdapter.setCommentDeleteLisenter(new FoundAdapter.CommentDeleteLisenter() {
            @Override
            public void deleteComment(CommentInfo commentInfo, String diary_id, int position) {
                if (userId.equals(commentInfo.getFrom_openid())) {
                    type = "CommentDelete";
                    deletePosition = position;
                    popupCommentDeleteWindow(diary_id, commentInfo.getOpenid(), commentInfo.getNickname(), commentInfo.getComment());
                }

            }

            @Override
            public void replyComment(CommentInfo commentInfo, String diary_id) {
                type = "ReplyComment";
                popupReplyCommentWindow(diary_id, commentInfo.getOpenid(), commentInfo.getNickname());
            }
        });

        foundSwrl.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (foundAdapter != null) {
                    List<AllMsgFound> data = foundAdapter.getData();
                    if (data.size() > 0) {
                        foundPresenter.foundinfoLoadMore("more", String.valueOf(data.size()), userId, "square", data.get(data.size() - 1).getDiary_id(), "", "", "zh", true);
                    }
                }
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                foundPresenter.loadInfo(userId, true);
            }
        });


    }

    private void popupCommentWindow(String diary_id, String tmp_user, String name) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.popup_comment, null);
        customPopWindow = new CustomPopWindow.PopupWindowBuilder(getContext())
                .setView(contentView)
                .enableBackgroundDark(false) //弹出popWindow时，背景是否变暗
                .setBgDarkAlpha(0.7f) // 控制亮度
                .setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED)
                .setFocusable(true)
                .setOutsideTouchable(true)
                .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
                .size(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)//显示大小
                .create().showAtLocation(getView(), Gravity.BOTTOM, 0, 0);
        EditText commentEt = contentView.findViewById(R.id.comment_et);
        commentEt.requestFocus();
        Utils.showSoftInput(getContext(), commentEt);
        commentEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                //里面就是隐藏软键盘
                Utils.hideSoftInput(getContext(), commentEt);
                // 和自己要写的逻辑
                return false;
            }
        }) ;

        TextView sendCommmentTv = contentView.findViewById(R.id.send_commment_tv);
        sendCommmentTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comment = commentEt.getText().toString().trim();
                if (!TextUtils.isEmpty(comment)) {
                    commentInfo = new CommentInfo();
                    commentInfo.setComment(comment);
                    commentInfo.setNickname(name);
                    commentInfo.setOpenid(tmp_user);
                    foundPresenter.comment("comment", diary_id, comment, tmp_user, name);
                    customPopWindow.dissmiss();
                    Utils.hideSoftInput(getContext(), commentEt);
                }
            }
        });

        customPopWindow.getPopupWindow().setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (!customPopWindow.getPopupWindow().isShowing()){
                    Utils.hideSoftInput(getContext(), commentEt);
                }
            }
        });
    }

    private void popupReplyCommentWindow(String diary_id, String tmp_user, String name) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.popup_comment_reply, null);
        customPopWindow1 = new CustomPopWindow.PopupWindowBuilder(getContext())
                .setView(contentView)
                .enableBackgroundDark(false) //弹出popWindow时，背景是否变暗
                .setBgDarkAlpha(0.7f) // 控制亮度
                .setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED)
                .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
                .setFocusable(true)
                .setOutsideTouchable(true)
                .size(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)//显示大小
                .create().showAtLocation(getView(), Gravity.BOTTOM, 0, 0);
        EditText replyCommentEt = contentView.findViewById(R.id.reply_comment_et);
        replyCommentEt.setHint("回复" + name);
        replyCommentEt.requestFocus();
//        Utils.showSoftInput(getContext(), replyCommentEt);
        Utils.showSoftInput(getContext(), replyCommentEt);

        TextView replyCommmentTv = contentView.findViewById(R.id.reply_comment_tv);
        replyCommmentTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comment = replyCommentEt.getText().toString().trim();
                if (!TextUtils.isEmpty(comment)) {
                    if (commentInfo1 == null) {
                        commentInfo1 = new CommentInfo();
                    }
                    commentInfo1.setComment(comment);
                    commentInfo1.setNickname((String) SPUtils.get(AppConstant.USER_NAME, ""));
                    commentInfo1.setOpenid(userId);
                    commentInfo1.setFrom_openid(tmp_user);
                    commentInfo1.setFrom_user(name);
                    foundPresenter.commentReply("comment", diary_id, comment, userId, (String) SPUtils.get(AppConstant.USER_NAME, ""), tmp_user, name);
                    customPopWindow1.dissmiss();
                    Utils.hideSoftInput(getContext(), replyCommentEt);
                }
            }
        });

        customPopWindow1.getPopupWindow().setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (!customPopWindow1.getPopupWindow().isShowing()){
                    replyCommentEt.setFocusable(false);
                    Utils.hideSoftInput(getContext(), replyCommentEt);

                }
            }
        });


    }

    private void popupCommentDeleteWindow(String diary_id, String tmp_user, String name, String comment) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.popup_comment_delete, null);
        customPopWindow2 = new CustomPopWindow.PopupWindowBuilder(getContext()).setView(contentView).enableBackgroundDark(true) //弹出popWindow时，背景是否变暗
                .setBgDarkAlpha(0.7f) // 控制亮度
                .size(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)//显示大小
                .create().showAtLocation(foundRv.getRootView(), Gravity.CENTER, 0, 0);

        TextView cannelTv = contentView.findViewById(R.id.cannel_tv);
        cannelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customPopWindow2.dissmiss();
            }
        });

        TextView deleteTv = contentView.findViewById(R.id.delete_tv);
        deleteTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foundPresenter.commentDelete("delete_comment", diary_id, comment, tmp_user, name);
                customPopWindow2.dissmiss();
            }
        });

    }

    @Override
    protected void initData() {
//        String userId =  (String)SPUtils.get(AppConstant.USER_ID, "");
        foundPresenter.loadInfo(userId, false);
        foundPresenter.topic();
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

    @OnClick(R.id.no_recommend_tv)
    public void onViewClicked() {
        recommendLl.setVisibility(View.GONE);
        recommendRv.setVisibility(View.GONE);
        SPUtils.put("recommend", false);
    }


    @OnClick({R.id.search_tv, R.id.search_bgiv, R.id.new_msg_show_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.search_tv:
                String search = searchEt.getText().toString().trim();
                if (!TextUtils.isEmpty(search)) {
                    Intent intent6 = new Intent(getContext(), WebActivity.class);
                    intent6.putExtra("url", "https://wechat.53iq.com/tmp/search?action=search&content=" + search);
                    startActivity(intent6);
                }

                break;
            case R.id.search_bgiv:
                searchLl.setVisibility(View.VISIBLE);
                searchBgiv.setVisibility(View.GONE);
                break;

            case R.id.new_msg_show_ll:
                SPUtils.put("msg_num", 0);
                newMsgShowLl.setVisibility(View.GONE);
                ((HomeActivity) getActivity()).setMessageNumTv();
                Intent intent3 = new Intent(getContext(), WebActivity.class);
                intent3.putExtra("url", "http://wechat.53iq.com/tmp/kitchen/relate/me?code=123&openid="+userId);
                startActivity(intent3);
                break;
        }
    }

    @Override
    public void setData(FoundHomeInfo data) {

        foundSwrl.finishRefresh();
        if (data != null) {
            if (data.getAll_msg() != null && data.getAll_msg().size() > 0) {

//                foundAdapter.setNewData(data.getAll_msg());
                if (isSimpleClick) {
                    foundAdapter.setData(currentPosition, data.getAll_msg().get(currentPosition));
                    isSimpleClick = false;
                } else {
                    foundAdapter.setNewData(data.getAll_msg());
                }
                foundAdapter.notifyDataSetChanged();
            }

            if (data.getMaster() != null && data.getMaster().size() > 0) {
                recommendLl.setVisibility(View.VISIBLE);
                recommendRv.setVisibility(View.VISIBLE);
                recommendFocusAdapter.setNewData(data.getMaster());
                recommendFocusAdapter.notifyDataSetChanged();
            } else {
                recommendLl.setVisibility(View.GONE);
                recommendRv.setVisibility(View.GONE);
            }


        }
    }

    @Override
    public void setMoreData(FoundLoadMoreInfo data) {
        foundSwrl.finishLoadMore();
        if (data != null) {
            if (data.getData().size() > 0) {
                if (data.getData().size() < 5) {
                    foundAdapter.loadMoreEnd();
                }
                foundAdapter.addData(data.getData());
//                foundAdapter.setNewData(data.getData());
                foundAdapter.notifyItemRangeChanged(foundAdapter.getData().size() - 6,
                        foundAdapter.getData().size());

                foundAdapter.notifyDataSetChanged();

            }
        }


    }

    @Override
    public void setFollowerData(BaseResponse data) {

        if (data != null) {
            if (data.getCode() == 0) {
//                ToastUtils.show("关注成功");
                EventBusUtil.sendEvent(new Event(Event.EVENT_UPDATE_MINE,"刷新我的界面"));
                AllMsgFound item = (AllMsgFound) foundAdapter.getItem(currentPosition);
                if (type.equals("subscribe")) {
                    if (item.isIs_subscribe()) {
                        item.setIs_subscribe(false);
                    } else {
                        item.setIs_subscribe(true);
                    }
                    foundAdapter.setData(currentPosition, item);
                    foundAdapter.notifyDataSetChanged();
                } else if (type.equals("collection")) {
                    if (item.isIs_collected()) {
                        item.setIs_collected(false);
                    } else {
                        item.setIs_collected(true);
                    }
                    foundAdapter.setData(currentPosition, item);
                    foundAdapter.notifyDataSetChanged();
                } else if (type.equals("like")) {
                    if (item.isIs_liked()) {
                        item.setIs_liked(false);
                        item.setLike_count(item.getLike_count() - 1);
                    } else {
                        item.setIs_liked(true);
                        item.setLike_count(item.getLike_count() + 1);
                    }
                    foundAdapter.setData(currentPosition, item);
                    foundAdapter.notifyDataSetChanged();
                } else if (type.equals("Comment")) {
                    List<CommentInfo> comment = item.getComment();
                    if (comment == null) {
                        comment = new ArrayList<>();
                    }
                    comment.add(commentInfo);
                    item.setComment(comment);
                    item.setComment_count(item.getComment_count() +1);
                    foundAdapter.setData(currentPosition, item);
                    foundAdapter.notifyDataSetChanged();
                } else if (type.equals("ReplyComment")) {
                    List<CommentInfo> comment = item.getComment();
                    if (comment == null) {
                        comment = new ArrayList<>();
                    }
                    comment.add(commentInfo1);
                    item.setComment(comment);
                    item.setComment_count(item.getComment_count() +1);
                    foundAdapter.setData(currentPosition, item);
                    foundAdapter.notifyDataSetChanged();
                } else if (type.equals("CommentDelete")) {
                    List<CommentInfo> comment = item.getComment();
                    if (comment == null) {
                        comment = new ArrayList<>();
                    }
                    comment.remove(deletePosition);
                    item.setComment(comment);
                    item.setComment_count(item.getComment_count() - 1);
                    foundAdapter.setData(currentPosition, item);
                    foundAdapter.notifyDataSetChanged();
                } else if (type.equals("recommend")) {
                    MasterInfo item1 = recommendFocusAdapter.getItem(recommendPosition);
                    if (item.isIs_subscribe()) {
                        item.setIs_subscribe(false);
                    } else {
                        item.setIs_subscribe(true);
                    }
                    recommendFocusAdapter.setData(recommendPosition, item1);
                    recommendFocusAdapter.notifyDataSetChanged();
                }
//                foundPresenter.loadInfo(userId,false);
            } else {
                if (!TextUtils.isEmpty(data.getMsg())) {
                    ToastUtils.show(data.getMsg());
                }
            }
        }
    }

    @Override
    public void netWorkError(String result) {
        foundSwrl.finishLoadMore();
        foundSwrl.finishRefresh();
        ToastUtils.show(result);
    }

    @Override
    public void saveOrSendData(BaseResponse baseResponse) {
        if (baseResponse != null) {
            if (baseResponse.getCode() == 0) {
//               SPUtils.put("send",true);
            }
        }
    }

    @Override
    public void topData(FoundTopResponse baseResponse) {
        if (baseResponse != null) {
            if (baseResponse.getData() != null && baseResponse.getData().size() > 0) {
                foundTabAdapter.setNewData(baseResponse.getData());
                foundTabAdapter.notifyDataSetChanged();
            }
        }
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(getContext()).onActivityResult(requestCode, resultCode, data);
        if (requestCode == Activity.RESULT_FIRST_USER) {
            if (resultCode == RESULT_CANCELED) {
                bundle = data.getExtras();
            /*    Glide.with(getContext()).load(bundle.getString("iconurl")).into(mYicon);
                mNick.setText(bundle.getString("screenname"));*/

            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        int msg_num = (int) SPUtils.get("msg_num", 0);
        if (msg_num > 0) {
            newMsgShowLl.setVisibility(View.VISIBLE);
            if (msg_num < 100) {
                newMsgNumTv.setText(msg_num + "条新消息");
            } else {
                newMsgNumTv.setText("99+条新消息");
            }

        } else {
            newMsgShowLl.setVisibility(View.GONE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        int msg_num = (int) SPUtils.get("msg_num", 0);
        if (msg_num > 0) {
            newMsgShowLl.setVisibility(View.VISIBLE);
            if (msg_num < 100) {
                newMsgNumTv.setText(msg_num + "条新消息");
            } else {
                newMsgNumTv.setText("99+条新消息");
            }

        } else {
            newMsgShowLl.setVisibility(View.GONE);
        }
    }

    public void scroolTopRefresh() {
        if (foundSrv.getScaleY() != 0) {
            foundSrv.fullScroll(ScrollView.FOCUS_UP);
        }

        initData();
    }

    public void addData() {
        /*boolean success = (boolean) SPUtils.get("success", false);
        if (success) {
            initData();
//            foundSwrl.autoRefresh();
        } else {*/


            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    String repice = (String) SPUtils.get(AppConstant.repice2, "");
                    String pic = (String) SPUtils.get(AppConstant.pic2, "");
                    Gson gson = new Gson();
                    AllMsgFound allMsgFound = gson.fromJson(repice, AllMsgFound.class);
                    List<Stepinfo> stepinfos = gson.fromJson(pic, new TypeToken<List<Stepinfo>>() {
                    }.getType());
                    String userImage = (String) SPUtils.get(AppConstant.USER_IMAGE2, "");
                    ArrayList<String> img_url = new ArrayList<String>();
                    ArrayList<String> thumbnail_url = new ArrayList<String>();
                    img_url.add(userImage);
                    thumbnail_url.add(userImage);
                    allMsgFound.setImg_url(img_url);
                    allMsgFound.setThumbnail_url(thumbnail_url);
                    for (int i = 0; i < stepinfos.size(); i++) {
                        FoodStepinfo foodStepinfo = new FoodStepinfo();
                        foodStepinfo.setImg(stepinfos.get(i).getImg());
                        foodStepinfo.setThumbnail(stepinfos.get(i).getThumbnail());
                        foodStepinfo.setDesc(stepinfos.get(i).getDesc());
                        foodStepinfos.add(foodStepinfo);
                    }
                    allMsgFound.setSteps(foodStepinfos);

                    if (foundAdapter != null) {
                        foundAdapter.addData(0, allMsgFound);
                        foundAdapter.notifyDataSetChanged();
                    }
                }
            },500);


//        }
    }



}