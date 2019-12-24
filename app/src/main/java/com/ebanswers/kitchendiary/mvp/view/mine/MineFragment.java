package com.ebanswers.kitchendiary.mvp.view.mine;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ebanswers.baselibrary.widget.ClearEditText;
import com.ebanswers.kitchendiary.R;
import com.ebanswers.kitchendiary.adapter.CookBookAdapter;
import com.ebanswers.kitchendiary.adapter.KitchenDiaryAdapter;
import com.ebanswers.kitchendiary.bean.CookbookInfo;
import com.ebanswers.kitchendiary.bean.DiaryInfo;
import com.ebanswers.kitchendiary.bean.UserInfo;
import com.ebanswers.kitchendiary.common.CommonLazyFragment;
import com.ebanswers.kitchendiary.constant.AppConstant;
import com.ebanswers.kitchendiary.eventbus.Event;
import com.ebanswers.kitchendiary.eventbus.EventBusUtil;
import com.ebanswers.kitchendiary.eventbus.ShowSearchEvent;
import com.ebanswers.kitchendiary.mvp.contract.BaseView;
import com.ebanswers.kitchendiary.mvp.presenter.MinePresenter;
import com.ebanswers.kitchendiary.mvp.view.base.HomeActivity;
import com.ebanswers.kitchendiary.mvp.view.base.WebActivity;
import com.ebanswers.kitchendiary.mvp.view.base.WelActivity;
import com.ebanswers.kitchendiary.network.response.BaseResponse;
import com.ebanswers.kitchendiary.network.response.CookbookResponse;
import com.ebanswers.kitchendiary.network.response.DiaryResponse;
import com.ebanswers.kitchendiary.utils.Base64Utils;
import com.ebanswers.kitchendiary.utils.GlideApp;
import com.ebanswers.kitchendiary.utils.LogUtils;
import com.ebanswers.kitchendiary.utils.NetworkUtils;
import com.ebanswers.kitchendiary.utils.SPUtils;
import com.ebanswers.kitchendiary.widget.popupwindow.CustomPopWindow;
import com.hjq.bar.TitleBar;
import com.hjq.toast.ToastUtils;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * desc   : 我的
 */
public class MineFragment extends CommonLazyFragment implements BaseView.MineView {


    Unbinder unbinder;
    @BindView(R.id.me_title)
    TitleBar meTitle;
    @BindView(R.id.user_icon)
    CircleImageView userIcon;
    @BindView(R.id.username_tv)
    TextView usernameTv;
    @BindView(R.id.individuality_signature_tv)
    TextView individualitySignatureTv;
    @BindView(R.id.share_iv)
    ImageView shareIv;
    @BindView(R.id.setting_iv)
    ImageView settingIv;
    @BindView(R.id.invitation_card_iv)
    ImageView invitationCardIv;
    @BindView(R.id.focus_tv)
    TextView focusTv;
    @BindView(R.id.fans_tv)
    TextView fansTv;
    @BindView(R.id.integral_tv)
    TextView integralTv;
    @BindView(R.id.clock_tv)
    TextView clockTv;
    @BindView(R.id.me_diary_tv)
    TextView meDiaryTv;
    @BindView(R.id.me_recipe_tv)
    TextView meRecipeTv;
    @BindView(R.id.me_collection_tv)
    TextView meCollectionTv;
    @BindView(R.id.me_list_iv)
    ImageView meListIv;
    @BindView(R.id.me_grid_iv)
    ImageView meGridIv;
    @BindView(R.id.search_et)
    ClearEditText searchEt;
    @BindView(R.id.search_tv)
    TextView searchTv;
    @BindView(R.id.search_ll)
    LinearLayout searchLl;
    @BindView(R.id.search_bgiv)
    ImageView searchBgiv;
    @BindView(R.id.diary_rv)
    RecyclerView diaryRv;
    @BindView(R.id.repice_rv)
    RecyclerView repiceRv;
    @BindView(R.id.collection_diary_tv)
    TextView collectionDiaryTv;
    @BindView(R.id.collection_repice_tv)
    TextView collectionRepiceTv;
    @BindView(R.id.collection_diary_rv)
    RecyclerView collectionDiaryRv;
    @BindView(R.id.collection_repice_rv)
    RecyclerView collectionRepiceRv;
    @BindView(R.id.collection_ll)
    LinearLayout collectionLl;
    @BindView(R.id.mine_srl)
    SmartRefreshLayout mineSrl;
    @BindView(R.id.mine_srv)
    NestedScrollView mineSrv;
    @BindView(R.id.focus_ll)
    LinearLayout focusLl;
    @BindView(R.id.fans_ll)
    LinearLayout fansLl;
    @BindView(R.id.integral_ll)
    LinearLayout integralLl;
    @BindView(R.id.clock_ll)
    LinearLayout clockLl;
    private KitchenDiaryAdapter kitchenDiaryAdapter;
    private CookBookAdapter cookBookAdapter;
    private MinePresenter minePresenter;
    private boolean isMore = false;
    private boolean isRepice = false;

    private boolean isSimpleClick = false;
    private int currentPosition = 0;
    private View noDataView;
    private CustomPopWindow.PopupWindowBuilder popupWindowBuilder;
    private CustomPopWindow customPopWindow;
    String userId;
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
//            Toast.makeText(getContext(), "分享成功", Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享失败的回调
         */
        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
//            Toast.makeText(getContext(), "分享失败" + throwable.getMessage(), Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享取消的回调
         */
        @Override
        public void onCancel(SHARE_MEDIA share_media) {
//            Toast.makeText(getContext(), "分享取消了", Toast.LENGTH_LONG).show();
        }
    };

    public static MineFragment newInstance() {
        return new MineFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.me_title;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        userId = (String) SPUtils.get(AppConstant.USER_ID, "");

        minePresenter = new MinePresenter(this, this);
        mineSrl.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                isMore = true;
                String userId = (String) SPUtils.get(AppConstant.USER_ID, "");

                if (!TextUtils.isEmpty(userId)) {

                    if (isRepice) {
                        minePresenter.loadCookbookInfo("more", cookBookAdapter.getItemCount() + "", userId, "cookbook", "", true);

                    } else {
                        minePresenter.loadDiaryInfo("more", kitchenDiaryAdapter.getItemCount() + "", userId, "diary-only", "", true);
                    }
                }

            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {

                searchEt.setText("");
                searchLl.setVisibility(View.GONE);
                searchBgiv.setVisibility(View.VISIBLE);

                String userId = (String) SPUtils.get(AppConstant.USER_ID, "");
                if (!TextUtils.isEmpty(userId)) {
                    minePresenter.loadUserInfo("wer", userId);
                    if (isRepice) {
                        minePresenter.loadCookbookInfo("more", 0 + "", userId, "cookbook", "", true);

                    } else {
                        minePresenter.loadDiaryInfo("more", 0 + "", userId, "diary-only", "", true);
                    }
                }
            }
        });

        TextPaint tp10 = collectionDiaryTv.getPaint();
        tp10.setFakeBoldText(true);

        diaryRv.setHasFixedSize(true);
        diaryRv.setNestedScrollingEnabled(false);
        diaryRv.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        kitchenDiaryAdapter = new KitchenDiaryAdapter();
        diaryRv.setAdapter(kitchenDiaryAdapter);

        if (!SPUtils.getIsLogin()) {
            loadEmpty("请登录后查看日记记录", diaryRv);
        } else {
            loadEmpty("你还未发布日记记录", diaryRv);
        }
        kitchenDiaryAdapter.setEmptyView(noDataView);
        kitchenDiaryAdapter.notifyDataSetChanged();
        kitchenDiaryAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                DiaryInfo item = (DiaryInfo) adapter.getItem(position);
                Intent intent1 = new Intent(getContext(), WebActivity.class);
                String openid1 = (String) SPUtils.get(AppConstant.USER_ID, "");
                intent1.putExtra("url", "https://wechat.53iq.com/tmp/kitchen/diary/" + item.getDiary_id() + "/detail?code=123&openid=" + openid1);
                startActivity(intent1);
            }
        });


        if (SPUtils.getIsLogin()) {
            invitationCardIv.setVisibility(View.VISIBLE);
        } else {
            invitationCardIv.setVisibility(View.GONE);
        }


        cookBookAdapter = new CookBookAdapter();
        repiceRv.setHasFixedSize(true);
        repiceRv.setNestedScrollingEnabled(false);
        repiceRv.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        repiceRv.setAdapter(cookBookAdapter);
        cookBookAdapter.notifyDataSetChanged();
        cookBookAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                CookbookInfo item = (CookbookInfo) adapter.getItem(position);
                Intent intent1 = new Intent(getContext(), WebActivity.class);
                String openid1 = (String) SPUtils.get(AppConstant.USER_ID, "");
                intent1.putExtra("url", "https://wechat.53iq.com/tmp/kitchen/diary/" + item.getDiary_id() + "/detail?code=123&openid=" + openid1);
                startActivity(intent1);
            }
        });
        cookBookAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                CookbookInfo item = (CookbookInfo) adapter.getItem(position);
                if (view.getId() == R.id.focus_status_iv) {

                    if (SPUtils.getIsLogin()) {
                        isSimpleClick = true;
                        currentPosition = position;
                        String create_user = item.getCreate_user();
                        String userId = (String) SPUtils.get(AppConstant.USER_ID, "");
                        if (item.isIs_subscribe()) {
                            minePresenter.islike("like", userId, create_user);
                        } else {
                            minePresenter.islike("unlike", userId, create_user);
                        }
                    } else {
//                    LoginActivity.openActivity(getContext(),LoginActivity.TYPE_PHONE_CODE);
                        startActivity(new Intent(getContext(), WelActivity.class));
                    }
                }
            }
        });
    }

    @Override
    protected void initData() {
        userId = (String) SPUtils.get(AppConstant.USER_ID, "");
        if (!TextUtils.isEmpty(userId)) {
            minePresenter.loadDiaryInfo("more", "0", userId, "diary-only", "first", false);
            minePresenter.loadUserInfo("wer", userId);
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
        EventBus.getDefault().unregister(this);
        unbinder.unbind();
    }


    @OnClick({R.id.user_icon, R.id.username_tv, R.id.search_tv, R.id.search_bgiv, R.id.share_iv,
            R.id.setting_iv, R.id.invitation_card_iv, R.id.me_diary_tv, R.id.me_recipe_tv,
            R.id.me_collection_tv, R.id.me_list_iv, R.id.me_grid_iv, R.id.collection_diary_tv,
            R.id.collection_repice_tv, R.id.focus_ll, R.id.fans_ll, R.id.integral_ll, R.id.clock_ll})
    public void onViewClicked(View view) {
        showSearchEvent(null);
        switch (view.getId()) {
            case R.id.user_icon:
                if (!HomeActivity.isLoginMethod()) {
                    //todo  这里暂时屏蔽修改头像 待后台修复后继续完善
//                    openCamera(1, PictureConfig.SINGLE);
                } else {
                    startActivity(new Intent(getContext(), WelActivity.class));
                }
                break;
            case R.id.username_tv:
                if (!HomeActivity.isLoginMethod()) {
                    //todo  这里暂时屏蔽修改昵称 待后台修复后继续完善
//                    popupChangeNameWindow(usernameTv);
                } else {
                    startActivity(new Intent(getContext(), WelActivity.class));
                }
                break;
            case R.id.share_iv:
                if (SPUtils.getIsLogin()) {
                    UMImage image;
                    if ((((BitmapDrawable) userIcon.getDrawable()).getBitmap()) != null) {
                         image = new UMImage(getContext(), ((BitmapDrawable) userIcon.getDrawable()).getBitmap());//分享图标
                    } else {
                        image=new UMImage(getContext(), R.mipmap.icon_logo);//分享图标
                    }

                    image.compressStyle = UMImage.CompressStyle.SCALE;//大小压缩，默认为大小压缩，适合普通很大的图
                    image.compressStyle = UMImage.CompressStyle.QUALITY;//质量压缩，适合长图的分享
//                        压缩格式设置
//                    image.compressFormat = Bitmap.CompressFormat.PNG;//用户分享透明背景的图片可以设置这种方式，但是qq好友，微信朋友圈，不支持透明背景图片，会变成黑色
                    final UMWeb web = new UMWeb("http://wechat.53iq.com/tmp/kitchen/food/diary?openid="+userId);
                            //切记切记 这里分享的链接必须是http开头
//                final UMWeb web = new UMWeb("https://wechat.53iq.com/tmp/kitchen/diary/" + item.getDiary_id() + "/detail?code=123"); //切记切记 这里分享的链接必须是http开头
                    web.setTitle(getContext().getString(R.string.share_title_new));//标题
                    web.setThumb(image);  //缩略图
                    web.setDescription(getContext().getString(R.string.share_des_new));//描述
                    new ShareAction(getSupportActivity()).withMedia(web).setDisplayList(SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE,
                            SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE).setShareboardclickCallback(new ShareBoardlistener() {
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
                } else {
//                    LoginActivity.openActivity(getContext(),LoginActivity.TYPE_PHONE_CODE);
                    startActivity(new Intent(getContext(), WelActivity.class));
                }

                break;
            case R.id.setting_iv:
//                Intent intent4 = new Intent(getContext(), WebActivity.class);
//                intent4.putExtra("url", "https://wechat.53iq.com/tmp/kitchen/setting?code=123&openid=" + userId);
//                startActivity(intent4);
                Intent intent4 = new Intent(getContext(), SettingActivity.class);
                startActivity(intent4);
                break;
            case R.id.invitation_card_iv:
                Intent intent1 = new Intent(getContext(), WebActivity.class);
                intent1.putExtra("url", "https://wechat.53iq.com/tmp/kitchen/share/edit2?code=123&openid=" + userId);
                startActivity(intent1);
                break;
            case R.id.me_diary_tv:
                meDiaryTv.setTextColor(getResources().getColor(R.color.text_black_normal));
                meRecipeTv.setTextColor(getResources().getColor(R.color.text_gray_middle));
                meCollectionTv.setTextColor(getResources().getColor(R.color.text_gray_middle));
                meDiaryTv.setTextSize(18);
                TextPaint tp = meDiaryTv.getPaint();
                TextPaint tp1 = meRecipeTv.getPaint();
                TextPaint tp2 = meCollectionTv.getPaint();
                tp.setFakeBoldText(true);
                tp1.setFakeBoldText(false);
                tp2.setFakeBoldText(false);
                meRecipeTv.setTextSize(14);
                meCollectionTv.setTextSize(14);
                diaryRv.setVisibility(View.VISIBLE);
                repiceRv.setVisibility(View.GONE);
                collectionLl.setVisibility(View.GONE);
                meListIv.setBackgroundResource(R.mipmap.icon_list_select);
                meGridIv.setBackgroundResource(R.mipmap.icon_grid_unselect);
                isRepice = false;
                mineSrl.setEnableLoadMore(true);
                isMore = false;
                if (!TextUtils.isEmpty(userId))
                    minePresenter.loadDiaryInfo("more", "0", userId, "diary-only", "first", false);
                break;
            case R.id.me_recipe_tv:
                TextPaint tp4 = meDiaryTv.getPaint();
                TextPaint tp5 = meRecipeTv.getPaint();
                TextPaint tp6 = meCollectionTv.getPaint();
                tp4.setFakeBoldText(false);
                tp5.setFakeBoldText(true);
                tp6.setFakeBoldText(false);
                meDiaryTv.setTextSize(14);
                meRecipeTv.setTextSize(18);
                meCollectionTv.setTextSize(14);
                meDiaryTv.setTextColor(getResources().getColor(R.color.text_gray_middle));
                meRecipeTv.setTextColor(getResources().getColor(R.color.text_black_normal));
                meCollectionTv.setTextColor(getResources().getColor(R.color.text_gray_middle));
                diaryRv.setVisibility(View.GONE);
                repiceRv.setVisibility(View.VISIBLE);
                collectionLl.setVisibility(View.GONE);
                meListIv.setBackgroundResource(R.mipmap.icon_list_unselect);
                meGridIv.setBackgroundResource(R.mipmap.icon_grid_select);
                isRepice = true;
                isMore = false;
                if (!TextUtils.isEmpty(userId)) {
                    mineSrl.setEnableLoadMore(true);
                    minePresenter.loadCookbookInfo("more", "0", userId, "cookbook", "first", false);
                }
                break;
            case R.id.me_collection_tv:
               /* TextPaint tp7 = meDiaryTv.getPaint();
                TextPaint tp8 = meRecipeTv.getPaint();
                TextPaint tp9 = meCollectionTv.getPaint();
                tp7.setFakeBoldText(false);
                tp8.setFakeBoldText(false);
                tp9.setFakeBoldText(true);
                meDiaryTv.setTextSize(14);
                meRecipeTv.setTextSize(14);
                meCollectionTv.setTextSize(18);
                meDiaryTv.setTextColor(getResources().getColor(R.color.text_gray_middle));
                meRecipeTv.setTextColor(getResources().getColor(R.color.text_gray_middle));
                meCollectionTv.setTextColor(getResources().getColor(R.color.text_black_normal));

                diaryRv.setVisibility(View.GONE);
                repiceRv.setVisibility(View.GONE);
                collectionLl.setVisibility(View.VISIBLE);*/


                if (!HomeActivity.isLoginMethod()) {
                    userId = (String) SPUtils.get(AppConstant.USER_ID, "");
                    Intent intent = new Intent(getContext(), WebActivity.class);
                    intent.putExtra("url", "https://wechat.53iq.com/tmp/kitchen/food/diary?types=collections&code=123&openid=" + userId);
                    startActivity(intent);
                } else {
                    startActivity(new Intent(getContext(), WelActivity.class));
                }

                break;
            case R.id.me_list_iv:

                break;
            case R.id.me_grid_iv:

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
            case R.id.collection_diary_tv:
                collectionDiaryRv.setVisibility(View.VISIBLE);
                collectionRepiceRv.setVisibility(View.GONE);
                collectionDiaryTv.setBackground(getResources().getDrawable(R.mipmap.collection_select_bg));
                collectionRepiceTv.setBackground(getResources().getDrawable(R.mipmap.collection_noselect_bg));
                collectionDiaryTv.setTextColor(getResources().getColor(R.color.app_theme));
                collectionRepiceTv.setTextColor(getResources().getColor(R.color.text_gray_middle));
                TextPaint tp10 = collectionDiaryTv.getPaint();
                TextPaint tp11 = collectionRepiceTv.getPaint();
                tp10.setFakeBoldText(true);
                tp11.setFakeBoldText(false);

                break;
            case R.id.collection_repice_tv:
                collectionDiaryRv.setVisibility(View.GONE);
                collectionRepiceRv.setVisibility(View.VISIBLE);
                collectionDiaryTv.setBackground(getResources().getDrawable(R.mipmap.collection_noselect_bg));
                collectionRepiceTv.setBackground(getResources().getDrawable(R.mipmap.collection_select_bg));
                collectionDiaryTv.setTextColor(getResources().getColor(R.color.text_gray_middle));
                collectionRepiceTv.setTextColor(getResources().getColor(R.color.app_theme));
                TextPaint tp12 = collectionDiaryTv.getPaint();
                TextPaint tp13 = collectionRepiceTv.getPaint();
                tp13.setFakeBoldText(true);
                tp12.setFakeBoldText(false);


//                minePresenter.loadCookbookInfo("more", "0", "tmp_user", "cookbook", "first", false);
                break;

            case R.id.focus_ll:
                if (SPUtils.getIsLogin()) {
                    userId = (String) SPUtils.get(AppConstant.USER_ID, "");
                    Intent intent2 = new Intent(getContext(), WebActivity.class);
                    intent2.putExtra("url", "https://wechat.53iq.com/tmp/kitchen/" + userId + "/follower/list?types=follower");
                    startActivity(intent2);
                }

                break;
            case R.id.fans_ll:
                if (SPUtils.getIsLogin()) {
                    userId = (String) SPUtils.get(AppConstant.USER_ID, "");
                    Intent intent3 = new Intent(getContext(), WebActivity.class);
                    intent3.putExtra("url", "https://wechat.53iq.com/tmp/kitchen/" + userId + "/follower/list?types=following");
                    startActivity(intent3);
                }

                break;
            case R.id.integral_ll:
                if (SPUtils.getIsLogin()) {
                    userId = (String) SPUtils.get(AppConstant.USER_ID, "");
                    Intent intent8 = new Intent(getContext(), WebActivity.class);
                    intent8.putExtra("url", "https://wechat.53iq.com/tmp/kitchen/point_goods?code=123&openid=" + userId);
                    startActivity(intent8);
                }

                break;
            case R.id.clock_ll:
                if (SPUtils.getIsLogin()) {
                    userId = (String) SPUtils.get(AppConstant.USER_ID, "");
                    Intent intent5 = new Intent(getContext(), WebActivity.class);
                    intent5.putExtra("url", "https://mp.weixin.qq.com/s/0usP-wgrFdFoIseGsciJPQ");
                    startActivity(intent5);
                }

                break;
        }

    }

    @Override
    public void setFollowerData(BaseResponse data) {
        if (data != null) {
            if (data.getCode() == 0) {
                EventBusUtil.sendEvent(new Event(Event.EVENT_UPDATE_MINE, "刷新我的界面"));
                CookbookInfo item = (CookbookInfo) cookBookAdapter.getItem(currentPosition);
                if (item.isIs_liked()) {
                    item.setIs_liked(false);
                    item.setLike_count(item.getLike_count() - 1);
                } else {
                    item.setIs_liked(true);
                    item.setLike_count(item.getLike_count() + 1);
                }
                cookBookAdapter.setData(currentPosition, item);
                cookBookAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void changeName(BaseResponse data) {
        if (data.getCode() == 0) {
            userId = (String) SPUtils.get(AppConstant.USER_ID, "");
            minePresenter.loadUserInfo("wer", userId);
        } else {
            ToastUtils.show(data.getMsg());
        }

    }

    @Override
    public void changeHeadUrl(BaseResponse data) {
        if (data.getCode() == 0) {
            userId = (String) SPUtils.get(AppConstant.USER_ID, "");
            minePresenter.loadUserInfo("wer", userId);
        } else {
            ToastUtils.show(data.getMsg());
        }
    }

    @Override
    public void setData(UserInfo data) {
        if (mineSrl != null) {
            mineSrl.finishRefresh();
            mineSrl.finishLoadMore();
        }

        if (data != null) {
            if (!TextUtils.isEmpty(data.getMy_name())) {
                if (usernameTv != null) {
                    usernameTv.setText(data.getMy_name());
                }
                SPUtils.put(AppConstant.USER_NAME, data.getMy_name());
            }

            if (!TextUtils.isEmpty(data.getOpenid())) {
                SPUtils.put(AppConstant.USER_ID, data.getOpenid());
            }

            if (!TextUtils.isEmpty(data.getHead_url())) {
                SPUtils.put(AppConstant.USER_ICON, data.getHead_url());
                if (getContext() != null) {
                    GlideApp.with(getContext()).load(data.getHead_url()).dontAnimate().skipMemoryCache(true)
                            .diskCacheStrategy(DiskCacheStrategy.NONE).into(userIcon);
                }
            }


            if (!TextUtils.isEmpty(data.getFollower_number() + "")) {
                if (fansTv != null) {
                    fansTv.setText(data.getFollowing_number() + "");
                }

            }

            if (!TextUtils.isEmpty(data.getFollowing_number() + "")) {
                if (focusTv != null) {
                    focusTv.setText(data.getFollower_number() + "");
                }

            }

            if (!TextUtils.isEmpty(data.getDays() + "")) {
                if (clockTv != null) {
                    clockTv.setText(data.getDays() + "");
                }
            }

            if (!TextUtils.isEmpty(data.getPoints() + "")) {
                if (integralTv != null) {
                    integralTv.setText(data.getPoints() + "");
                }

            }

            if (!TextUtils.isEmpty(data.getSignature())) {
                if (individualitySignatureTv != null) {
                    individualitySignatureTv.setText(data.getSignature());
                }

            }

        }


    }

    @Override
    public void setDiaryData(DiaryResponse data) {
        if (mineSrl != null) {
            mineSrl.finishRefresh();
            mineSrl.finishLoadMore();
        }
        if (diaryRv != null) {
            diaryRv.setVisibility(View.VISIBLE);
        }
        if (repiceRv != null) {
            repiceRv.setVisibility(View.GONE);
        }

        if (data != null) {
            List<DiaryInfo> data1 = data.getData();
            if (data1 != null && data1.size() > 0) {
                if (isMore) {
                    kitchenDiaryAdapter.addData(data1);
                    kitchenDiaryAdapter.notifyItemRangeInserted(kitchenDiaryAdapter.getData().size() - data.getData().size(), data.getData().size());
                } else {
                    kitchenDiaryAdapter.setNewData(data1);
                    kitchenDiaryAdapter.notifyDataSetChanged();
                }
            } else {
                if (!isMore) {
                    if (HomeActivity.isLoginMethod()) {
                        loadEmpty("请登录后查看日记记录", diaryRv);
                        kitchenDiaryAdapter.setEmptyView(noDataView);
                    } else {
//                        kitchenDiaryAdapter.getEmptyView().setVisibility(View.GONE);
                        loadEmpty("你还未发布日记哦", diaryRv);
                        kitchenDiaryAdapter.setEmptyView(noDataView);

                    }
                    kitchenDiaryAdapter.notifyDataSetChanged();
                } else {
                    loadEmpty("你还未发布日记哦", repiceRv);
                    kitchenDiaryAdapter.setEmptyView(noDataView);
                    cookBookAdapter.notifyDataSetChanged();
                }
                isMore = false;
                mineSrl.setEnableLoadMore(false);
            }
        } else {
            loadEmpty("你还未发布日记哦", repiceRv);
            kitchenDiaryAdapter.setNewData(new ArrayList<>());
            kitchenDiaryAdapter.setEmptyView(noDataView);
            cookBookAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void setCookBookData(CookbookResponse data) {
        if (mineSrl != null) {
            mineSrl.finishRefresh();
            mineSrl.finishLoadMore();
        }
        if (diaryRv != null) {
            diaryRv.setVisibility(View.GONE);
        }
        if (repiceRv != null) {
            repiceRv.setVisibility(View.VISIBLE);
        }

        if (data != null) {
            List<CookbookInfo> data1 = data.getData();
            if (data1 != null && data1.size() > 0) {
                if (isMore) {
                    cookBookAdapter.addData(data1);
                    cookBookAdapter.notifyItemRangeInserted(cookBookAdapter.getData().size() - data.getData().size(), data.getData().size());
                } else {
                    cookBookAdapter.setNewData(data1);
                    cookBookAdapter.notifyDataSetChanged();
                }

            } else {
                if (!isMore) {
                    if (HomeActivity.isLoginMethod()) {
                        loadEmpty("请登录后查看菜谱记录", repiceRv);
                        cookBookAdapter.setEmptyView(noDataView);
                    } else {
//                        cookBookAdapter.getEmptyView().setVisibility(View.GONE);
                        loadEmpty("你还未发布菜谱记录", repiceRv);
                        cookBookAdapter.setEmptyView(noDataView);
                    }
                    cookBookAdapter.notifyDataSetChanged();
                } else {
                    loadEmpty("你还未发布菜谱记录", repiceRv);
                    cookBookAdapter.setEmptyView(noDataView);
                    cookBookAdapter.notifyDataSetChanged();
                }

                isMore = false;
                mineSrl.setEnableLoadMore(false);
//                cookBookAdapter.notifyDataSetChanged();
            }
        } else {
            loadEmpty("你还未发布菜谱记录", repiceRv);
            cookBookAdapter.setEmptyView(noDataView);
            cookBookAdapter.setNewData(new ArrayList<>());
            cookBookAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void netWorkError(String result) {
        if (NetworkUtils.isNetworkAvailable(getContext())) {
            ToastUtils.show(result);
        } else {
            ToastUtils.show("无可用网络！");
        }
        mineSrl.finishRefresh();
        mineSrl.finishLoadMore();
    }

    public void scroolTopRefresh() {
        if (mineSrv.getScaleY() != 0) {
            mineSrv.fullScroll(ScrollView.FOCUS_UP);
        }

        initData();
        if (isRepice) {
            minePresenter.loadCookbookInfo("more", "0", userId, "cookbook", "first", false);
        }
    }

    public void loadEmpty(String tip, View view) {
        noDataView = getLayoutInflater().inflate(R.layout.empty_view, (ViewGroup) view.getParent(), false);
        TextView emptyTv = noDataView.findViewById(R.id.empty_tv);
        ImageView emptyIv = noDataView.findViewById(R.id.empty_iv);
        ImageView loginIv = noDataView.findViewById(R.id.login_iv);
        emptyTv.setText(tip);
        if (tip.startsWith("你还未发布")) {
            loginIv.setVisibility(View.GONE);
        } else {
            loginIv.setVisibility(View.VISIBLE);
        }

        loginIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                LoginActivity.openActivity(getActivity(),LoginActivity.TYPE_PHONE_CODE);

                startActivity(new Intent(getActivity(), WelActivity.class));
            }
        });
    }

    @SuppressLint("NewApi")
    private void popupChangeNameWindow(View tabCenterLl) {
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View inflate = layoutInflater.inflate(R.layout.popup_change_name, null);
        EditText nameEt = inflate.findViewById(R.id.name_et);
        Button saveChangeBt = inflate.findViewById(R.id.save_change_bt);

        inflate.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);

        if (popupWindowBuilder == null) {
            popupWindowBuilder = new CustomPopWindow.PopupWindowBuilder(getContext());
        }
        popupWindowBuilder.setView(inflate).size(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT).setBgDarkAlpha(0.7f).enableBackgroundDark(true).setOutsideTouchable(true).setAnimationStyle(R.style.RtcPopupAnimation);
        customPopWindow = popupWindowBuilder.create();
        customPopWindow.showAtLocation(tabCenterLl.getRootView(), Gravity.CENTER, 0, 0);

        saveChangeBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String trim = nameEt.getText().toString().trim();
                if (!TextUtils.isEmpty(trim)) {
                    customPopWindow.dissmiss();
                    String userId = (String) SPUtils.get(AppConstant.USER_ID, "");
                    minePresenter.saveName(trim, userId);
                } else {
                    ToastUtils.show("请数入用户名");
                }
            }
        });

    }

    public void openCamera(int max, int type) {
        // 进入相册 以下是例子：用不到的api可以不写
        PictureSelector.create(getSupportActivity()).openGallery(PictureMimeType.ofAll())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .theme(R.style.picture_default_style)//主题样式(不设置为默认样式) 也可参考demo values/styles下 例如：R.style.picture.white.style
                .maxSelectNum(max)// 最大图片选择数量 int
                .minSelectNum(1)// 最小选择数量 int
                .imageSpanCount(4)// 每行显示个数 int
                .selectionMode(type)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .previewImage(true)// 是否可预览图片 true or false
//                .previewVideo(true)// 是否可预览视频 true or false
//                .enablePreviewAudio(true) // 是否可播放音频 true or false
                .isCamera(true)// 是否显示拍照按钮 true or false
                .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .setOutputCameraPath("/CustomPath")// 自定义拍照保存路径,可不填
//                .enableCrop()// 是否裁剪 true or false
                .compress(true)// 是否压缩 true or false
//                .glideOverride()// int glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
//                .withAspectRatio()// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
//                .hideBottomControls()// 是否显示uCrop工具栏，默认不显示 true or false
                .isGif(false)// 是否显示gif图片 true or false
//                .compressSavePath(getPath())//压缩图片保存地址
//                .freeStyleCropEnabled()// 裁剪框是否可拖拽 true or false
//                .circleDimmedLayer()// 是否圆形裁剪 true or false
//                .showCropFrame()// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
//                .showCropGrid()// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
//                .openClickSound()// 是否开启点击声音 true or false
//                .selectionMedia()// 是否传入已选图片 List<LocalMedia> list
                .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                .cropCompressQuality(90)// 裁剪压缩质量 默认90 int
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
//                .cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效 int
//                .rotateEnabled() // 裁剪是否可旋转图片 true or false
//                .scaleEnabled()// 裁剪是否可放大缩小图片 true or false
//                .videoQuality()// 视频录制质量 0 or 1 int
//                .videoMaxSecond(15)// 显示多少秒以内的视频or音频也可适用 int
//                .videoMinSecond(10)// 显示多少秒以内的视频or音频也可适用 int
//                .recordVideoSecond()//视频秒数录制 默认60s int
//                .isDragFrame(false)// 是否可拖动裁剪框(固定)
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case PictureConfig.CHOOSE_REQUEST:
                // 图片、视频、音频选择结果回调
                List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                // 例如 LocalMedia 里面返回三种path
                // 1.media.getPath(); 为原图path
                // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true  注意：音视频除外
                // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
                // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
//                    adapter.setList(selectList);
//                    adapter.notifyDataSetChanged();
                /*结果回调*/
                if (selectList != null && selectList.size() > 0) {


                    if (!TextUtils.isEmpty(selectList.get(0).getPath())) {
                        String url = "data:image/jpeg;base64," + Base64Utils.imageToBase64(selectList.get(0).getPath());

                        GlideApp.with(getContext()).load(selectList.get(0).getPath()).dontAnimate().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(userIcon);
                        /**
                         * 操作图片上传和发出修改头像请求
                         */
                        String userId = (String) SPUtils.get(AppConstant.USER_ID, "");
                        minePresenter.saveHeadUrl(url, userId);
                    }


                } else {
                    ToastUtils.show("无图片选中");
                }


                break;
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetMessage(Event message) {
        if (message.getType() == Event.EVENT_UPDATE_MINE) {
            String userId = (String) SPUtils.get(AppConstant.USER_ID, "");
            if (!TextUtils.isEmpty(userId)) {
//                minePresenter.loadDiaryInfo("more", "0", userId, "diary-only", "first", false);
                minePresenter.loadUserInfo("wer", userId);
            }
        } else if (message.getType() == Event.EVENT_UPDATE_TOMINE) {
            String userId = (String) SPUtils.get(AppConstant.USER_ID, "");
            if (!TextUtils.isEmpty(userId)) {
                minePresenter.loadUserInfo("wer", userId);
                if (isVisibleToUser()) {
                    if (isRepice) {
                        minePresenter.loadCookbookInfo("more", cookBookAdapter.getItemCount() + "", userId, "cookbook", "", true);
                    } else {
                        minePresenter.loadDiaryInfo("more", kitchenDiaryAdapter.getItemCount() + "", userId, "diary-only", "", true);
                    }
                }
            }
        }

    }


    /**
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showSearchEvent(ShowSearchEvent event) {
        if (searchLl != null && searchBgiv != null) {
            searchLl.setVisibility(View.GONE);
            searchBgiv.setVisibility(View.VISIBLE);
        }
    }

}