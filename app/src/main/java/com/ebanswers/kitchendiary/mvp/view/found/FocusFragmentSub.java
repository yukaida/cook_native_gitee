package com.ebanswers.kitchendiary.mvp.view.found;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
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
import com.ebanswers.kitchendiary.bean.AllMsgFound;
import com.ebanswers.kitchendiary.bean.CommentInfo;
import com.ebanswers.kitchendiary.bean.FoundLoadMoreInfo;
import com.ebanswers.kitchendiary.bean.FoundTopInfo;
import com.ebanswers.kitchendiary.common.CommonLazyFragment;
import com.ebanswers.kitchendiary.constant.AppConstant;
import com.ebanswers.kitchendiary.eventbus.Event;
import com.ebanswers.kitchendiary.eventbus.EventBusUtil;
import com.ebanswers.kitchendiary.mvp.contract.BaseView;
import com.ebanswers.kitchendiary.mvp.presenter.FocusPresenter;
import com.ebanswers.kitchendiary.mvp.view.base.HomeActivity;
import com.ebanswers.kitchendiary.mvp.view.base.WebActivity;
import com.ebanswers.kitchendiary.mvp.view.base.WelActivity;
import com.ebanswers.kitchendiary.network.response.BaseResponse;
import com.ebanswers.kitchendiary.network.response.FocusResponse;
import com.ebanswers.kitchendiary.network.response.FoundTopResponse;
import com.ebanswers.kitchendiary.utils.LogUtils;
import com.ebanswers.kitchendiary.utils.SPUtils;
import com.ebanswers.kitchendiary.utils.Utils;
import com.ebanswers.kitchendiary.widget.decorator.HorizontaltemDecoration;
import com.ebanswers.kitchendiary.widget.decorator.VerticalltemDecoration;
import com.ebanswers.kitchendiary.widget.popupwindow.CustomPopWindow;
import com.hjq.toast.ToastUtils;
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
public class FocusFragmentSub extends CommonLazyFragment implements BaseView.FocusView {

    @BindView(R.id.pic_show_rv)
    RecyclerView picShowRv;
    @BindView(R.id.new_msg_num_tv)
    TextView newMsgNumTv;
    @BindView(R.id.new_msg_show_ll)
    LinearLayout newMsgShowLl;
    @BindView(R.id.focus_rv)
    RecyclerView focusRv;
    @BindView(R.id.focus_srl)
    SmartRefreshLayout focusSrl;
    @BindView(R.id.search_et)
    ClearEditText searchEt;
    @BindView(R.id.search_tv)
    TextView searchTv;
    @BindView(R.id.search_ll)
    LinearLayout searchLl;
    @BindView(R.id.search_bgiv)
    ImageView searchBgiv;
    @BindView(R.id.focus_srv)
    NestedScrollView focusSrv;
    private FoundTabAdapter foundTabAdapter;
    private FocusPresenter focusPresenter;

    private boolean isSimpleClick = false;
    private int currentPosition = 0;
    private String type = "";
    private FoundAdapter foundAdapter;
    private CustomPopWindow customPopWindow;
    private Bundle bundle;
    String userId =  (String)SPUtils.get(AppConstant.USER_ID, "");
    private CommentInfo commentInfo;
    private CommentInfo commentInfo1;
    private int deletePosition;

    public static FocusFragmentSub newInstance() {
        return new FocusFragmentSub();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_focus_sub;
    }

    @Override
    protected int getTitleBarId() {
        return 0;
    }

    @Override
    protected void initView() {
        focusPresenter = new FocusPresenter(this, this);
        UMShareListener umShareListener = new UMShareListener() {
            /**
             * @descrption 分享开始的回调
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

        picShowRv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        picShowRv.addItemDecoration(new HorizontaltemDecoration(10));
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

        focusRv.setLayoutManager(new LinearLayoutManager(getContext()));
        focusRv.addItemDecoration(new VerticalltemDecoration(10));

        foundAdapter = new FoundAdapter();
        focusRv.setAdapter(foundAdapter);
        foundAdapter.notifyDataSetChanged();
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
                        final UMWeb web = new UMWeb("https://wechat.53iq.com/tmp/kitchen/diary/" + item.getDiary_id() + "/detail?code=123&openid=" + openid); //切记切记 这里分享的链接必须是http开头
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
                                focusPresenter.follower("cancel ", userId, item.getCreate_user());
                            } else {
                                focusPresenter.follower("", userId, item.getCreate_user());
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
                                focusPresenter.uncollected("cancel_collect", item.getDiary_id());
                            } else {
                                focusPresenter.collected("collect", item.getDiary_id(), item.getCreate_user());
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
                                focusPresenter.islike("unlike", item.getDiary_id(), userId);
                            } else {
                                focusPresenter.islike("like", item.getDiary_id(), userId);
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
                        intent1.putExtra("url", "https://wechat.53iq.com/tmp/kitchen/diary/" + item.getDiary_id() + "/detail?code=123&openid=" + openid1);
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
                    popupCommentDeleteWindow(diary_id, commentInfo.getFrom_openid(), commentInfo.getNickname(), commentInfo.getComment());
                }
            }

            @Override
            public void replyComment(CommentInfo commentInfo, String diary_id) {
                type = "ReplyComment";
                popupReplyCommentWindow(diary_id, commentInfo.getFrom_openid(), commentInfo.getNickname());
            }
        });

        focusSrl.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (foundAdapter != null){
                    List<AllMsgFound> data = foundAdapter.getData();
                    if (data.size() > 0){
                        focusPresenter.foundinfoLoadMore("more", String.valueOf(data.size()), userId, "square-related", data.get(data.size() - 1).getDiary_id(), "", "", "zh", true);
                    }else {
                        focusSrl.finishLoadMore();
                    }
                }else {
                    focusSrl.finishLoadMore();
                }

            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                focusPresenter.loadInfo(userId, true);
            }
        });

    }

    @Override
    protected void initData() {
        if (focusPresenter != null){
            focusPresenter.loadInfo(userId, false);
            focusPresenter.topic();
        }

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
                ((HomeActivity) getActivity()).setMessageNumTv();
                Intent intent3 = new Intent(getContext(), WebActivity.class);
                intent3.putExtra("url", "http://wechat.53iq.com/tmp/kitchen/relate/me?code=123&openid="+userId);
                startActivity(intent3);
                newMsgShowLl.setVisibility(View.GONE);
                break;
        }
    }

    private void popupCommentWindow(String diary_id, String tmp_user, String name) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.popup_comment, null);
        customPopWindow = new CustomPopWindow.PopupWindowBuilder(getContext()).setView(contentView).enableBackgroundDark(true) //弹出popWindow时，背景是否变暗
                .setBgDarkAlpha(0.7f) // 控制亮度
                .setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED)
                .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
                .size(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)//显示大小
                .create().showAtLocation(getView(), Gravity.BOTTOM, 0, 0);
        Utils.showSoftInput(getContext(),getView());
        EditText commentEt = contentView.findViewById(R.id.comment_et);
        TextView sendCommmentTv = contentView.findViewById(R.id.send_commment_tv);
        sendCommmentTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comment = commentEt.getText().toString().trim();
                if (!TextUtils.isEmpty(comment)) {
                    focusPresenter.comment("comment", diary_id, comment, tmp_user, name);
                    customPopWindow.dissmiss();
                }
            }
        });
    }

    private void popupReplyCommentWindow(String diary_id, String tmp_user, String name) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.popup_comment_reply, null);
        customPopWindow = new CustomPopWindow.PopupWindowBuilder(getContext()).setView(contentView).enableBackgroundDark(true) //弹出popWindow时，背景是否变暗
                .setBgDarkAlpha(0.7f) // 控制亮度
                .setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED).setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE).size(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)//显示大小
                .create().showAtLocation(getView(), Gravity.BOTTOM, 0, 0);
        EditText commentEt = contentView.findViewById(R.id.reply_comment_et);
        commentEt.setHint("回复" + name);
        commentEt.setFocusable(true);
        Utils.showSoftInput(getContext(), commentEt);
        TextView replyCommmentTv = contentView.findViewById(R.id.reply_comment_tv);
        replyCommmentTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comment = commentEt.getText().toString().trim();
                if (!TextUtils.isEmpty(comment)) {
                    if (commentInfo1 == null){
                        commentInfo1 = new CommentInfo();
                    }
                    commentInfo1.setComment(comment);
                    commentInfo1.setNickname((String) SPUtils.get(AppConstant.USER_NAME,""));
                    commentInfo1.setOpenid(userId);
                    commentInfo1.setFrom_openid(tmp_user);
                    commentInfo1.setFrom_user(name);
                    focusPresenter.commentReply("comment", diary_id, comment, userId, (String) SPUtils.get(AppConstant.USER_NAME,""),tmp_user, name);
                    customPopWindow.dissmiss();
                }
            }
        });
    }

    private void popupCommentDeleteWindow(String diary_id, String tmp_user, String name, String comment) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.popup_comment_delete, null);
        customPopWindow = new CustomPopWindow.PopupWindowBuilder(getContext()).setView(contentView).enableBackgroundDark(true) //弹出popWindow时，背景是否变暗
                .setBgDarkAlpha(0.7f) // 控制亮度
                .size(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)//显示大小
                .create().showAtLocation(focusRv.getRootView(), Gravity.CENTER, 0, 0);


        TextView cannelTv = contentView.findViewById(R.id.cannel_tv);
        cannelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customPopWindow.dissmiss();
            }
        });

        TextView deleteTv = contentView.findViewById(R.id.delete_tv);
        deleteTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                focusPresenter.commentDelete("delete_comment", diary_id, comment, tmp_user, name);
                customPopWindow.dissmiss();
            }
        });

    }

    @Override
    public void setData(FocusResponse data) {

        focusSrl.finishRefresh();
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
        }
    }

    @Override
    public void setMoreData(FoundLoadMoreInfo data) {
        focusSrl.finishLoadMore();
        if (data != null) {
            if (data.getData().size() > 0) {
//                allMsgFounds.addAll(data.getData());
                if (data.getData().size() < 5) {
                    foundAdapter.loadMoreEnd();
                }
                foundAdapter.addData(data.getData());
//                foundAdapter.setNewData(allMsgFounds);
                foundAdapter.notifyItemRangeInserted(foundAdapter.getData().size() - data.getData().size(),
                        data.getData().size());
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
//                        item.setMaster_rank(item.getMaster_rank() - 1);
                    } else {
                        item.setIs_subscribe(true);
//                        item.setMaster_rank(item.getMaster_rank() + 1);
                    }
                } else if (type.equals("collection")) {
                    if (item.isIs_collected()) {
                        item.setIs_collected(false);
//                        item.setMaster_rank(item.getMaster_rank() - 1);
                    } else {
                        item.setIs_collected(true);
//                        item.setMaster_rank(item.getMaster_rank() + 1);
                    }
                } else if (type.equals("like")){
                    if (item.isIs_liked()) {
                        item.setIs_liked(false);
                        item.setLike_count(item.getLike_count() - 1);
                    } else {
                        item.setIs_liked(true);
                        item.setLike_count(item.getLike_count() + 1);
                    }
                }else if (type.equals("Comment")){
                    List<CommentInfo> comment = item.getComment();
                    if (comment == null){
                        comment = new ArrayList<>();
                    }
                    comment.add(commentInfo);
                    item.setComment(comment);

                } else if (type.equals("ReplyComment")){
                    List<CommentInfo> comment = item.getComment();
                    if (comment == null){
                        comment = new ArrayList<>();
                    }
                    comment.add(commentInfo1);
                    item.setComment(comment);
                } else if (type.equals("CommentDelete")){
                    List<CommentInfo> comment = item.getComment();
                    if (comment == null){
                        comment = new ArrayList<>();
                    }
                    comment.remove(deletePosition);
                    item.setComment(comment);
                }
                foundAdapter.setData(currentPosition, item);
                foundAdapter.notifyDataSetChanged();
//                focusPresenter.loadInfo(userId,true);
            } else {
                if (!TextUtils.isEmpty(data.getMsg())) {
                    ToastUtils.show(data.getMsg());
                }
            }
        }
    }

    @Override
    public void topData(FoundTopResponse baseResponse) {
        if (baseResponse!=null){
            if (baseResponse.getData() != null && baseResponse.getData().size() > 0){
                foundTabAdapter.setNewData(baseResponse.getData());
                foundTabAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void netWorkError(String result) {
        focusSrl.finishLoadMore();
        focusSrl.finishRefresh();
        ToastUtils.show(result);
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
        if (focusSrv.getScaleY()!=0) {
            focusSrv.fullScroll(ScrollView.FOCUS_UP);
        }
        initData();
    }
}
