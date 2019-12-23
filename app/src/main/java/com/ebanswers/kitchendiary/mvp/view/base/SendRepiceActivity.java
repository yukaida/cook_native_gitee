package com.ebanswers.kitchendiary.mvp.view.base;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.animation.SlideInLeftAnimation;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemDragListener;
import com.ebanswers.kitchendiary.R;
import com.ebanswers.kitchendiary.adapter.FoodStepAdapter;
import com.ebanswers.kitchendiary.bean.AllMsgFound;
import com.ebanswers.kitchendiary.bean.FoodMaterialinfo;
import com.ebanswers.kitchendiary.bean.FoodStepinfo;
import com.ebanswers.kitchendiary.bean.LikedInfo;
import com.ebanswers.kitchendiary.bean.Stepinfo;
import com.ebanswers.kitchendiary.bean.draftsDetail.DraftsDetail;
import com.ebanswers.kitchendiary.bean.draftsDetail.Material;
import com.ebanswers.kitchendiary.bean.draftsDetail.Steps;
import com.ebanswers.kitchendiary.common.CommonActivity;
import com.ebanswers.kitchendiary.constant.AppConstant;
import com.ebanswers.kitchendiary.eventbus.Event;
import com.ebanswers.kitchendiary.eventbus.EventBusUtil;
import com.ebanswers.kitchendiary.mvp.contract.BaseView;
import com.ebanswers.kitchendiary.mvp.presenter.SendRepicePresenter;
import com.ebanswers.kitchendiary.network.api.DrufNumResponse;
import com.ebanswers.kitchendiary.network.progress.DialogCircleProgress;
import com.ebanswers.kitchendiary.network.response.BaseResponse;
import com.ebanswers.kitchendiary.network.response.ImageResponse;
import com.ebanswers.kitchendiary.service.CreateRepiceDraftService;
import com.ebanswers.kitchendiary.service.CreateRepiceService;
import com.ebanswers.kitchendiary.utils.GlideApp;
import com.ebanswers.kitchendiary.utils.LogUtils;
import com.ebanswers.kitchendiary.utils.NetworkUtils;
import com.ebanswers.kitchendiary.utils.SPUtils;
import com.ebanswers.kitchendiary.widget.dialog.DialogBackTip;
import com.ebanswers.kitchendiary.widget.popupwindow.CustomPopWindow;
import com.google.gson.Gson;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.hjq.permissions.OnPermission;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.hjq.toast.ToastUtils;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Create by dongli
 * Create date 2019-11-05
 * desc：
 */
public class SendRepiceActivity extends CommonActivity implements BaseView.SendRepiceView, OnPermission {

    @BindView(R.id.repice_title)
    TitleBar repiceTitle;
    @BindView(R.id.repice_cover_iv)
    ImageView repiceCoverIv;
    @BindView(R.id.add_ll)
    LinearLayout addLl;
    @BindView(R.id.add_desc_tv)
    TextView addDescTv;
    @BindView(R.id.repice_name_et)
    EditText repiceNameEt;
    @BindView(R.id.repice_desc_et)
    EditText repiceDescEt;
    @BindView(R.id.food_material_ll)
    LinearLayout foodMaterialLl;
    @BindView(R.id.repice_steaming_roast_rb)
    RadioButton repiceSteamingRoastRb;
    @BindView(R.id.repice_normal_rb)
    RadioButton repiceNormalRb;
    @BindView(R.id.repice_type_rg)
    RadioGroup repiceTypeRg;
    @BindView(R.id.add_food_material_ll)
    LinearLayout addFoodMaterialLl;
    @BindView(R.id.adjust_food_material_tv)
    TextView adjustFoodMaterialTv;
    @BindView(R.id.more_pic_add_tv)
    TextView morePicAddTv;
    @BindView(R.id.food_step_rv)
    RecyclerView foodStepRv;
    @BindView(R.id.add_food_step_ll)
    LinearLayout addFoodStepLl;
    @BindView(R.id.adjust_food_step_tv)
    TextView adjustFoodStepTv;
    @BindView(R.id.repice_tip_et)
    EditText repiceTipEt;
    @BindView(R.id.release_bt)
    Button releaseBt;
    @BindView(R.id.save_draft_tv)
    TextView saveDraftTv;
    @BindView(R.id.repice_cover_rl)
    RelativeLayout repiceCoverRl;
    @BindView(R.id.scroll)
    NestedScrollView scroll;
    private CustomPopWindow customPopWindow;

    private boolean adjustFoodMaterial = false;
    private boolean adjustFoodStep = false;
    private boolean isNormal = true;
    private FoodStepAdapter foodStepAdapter;
    List<Stepinfo> stepinfos = new ArrayList<>();
    List<FoodMaterialinfo> foodMaterialinfos = new ArrayList<>();

    private int currentPosition;
    private String type;
    private String submitType;
    private String cookbookType;
    private OnItemDragListener itemDragListener;
    private SendRepicePresenter sendRepicePresenter;

    private String topUrl;
    private DialogBackTip.Builder builder;
    private List<String> thumbnail_url;
    private List<String> img_url;
    private String titlePath;
    private String titleThumbPath;
    private String sdCardDir = Environment.getExternalStorageDirectory() + "/tempcook/";
    private long DISPLAY_LENGHT = 1000;
    private int currentLocalMedia = 0;
    List<FoodStepinfo> foodStepinfos = new ArrayList<>();
    private ProgressBar progressBar;
    private DialogCircleProgress.Builder builder3;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_send_repice;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.repice_title;
    }

    @Override
    protected void initView() {

        sendRepicePresenter = new SendRepicePresenter(this, this);
        EventBusUtil.register(this);
        repiceTitle.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(View v) {
                if (checkEditStatus()) {
                    finish();
                } else {
                    popupBackTip();
                }
            }

            @Override
            public void onTitleClick(View v) {

            }

            @Override
            public void onRightClick(View v) {
                //跳转到草稿箱界面

                if (checkEditStatus()) {
                    Intent intent = new Intent(SendRepiceActivity.this, DraftsActivity.class);
                    startActivityForResult(intent, 56);
                } else {
                    popupCheckCover();
                }


            }
        });

        repiceTypeRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.repice_normal_rb) {
                    repiceSteamingRoastRb.setChecked(false);
                    repiceNormalRb.setChecked(true);
                    isNormal = true;
                    cookbookType = "普通菜谱";
                } else {
                    repiceSteamingRoastRb.setChecked(true);
                    repiceNormalRb.setChecked(false);
                    isNormal = false;
                    cookbookType = "蒸烤菜谱";
                }
            }
        });

        itemDragListener = new OnItemDragListener() {
            @Override
            public void onItemDragStart(RecyclerView.ViewHolder viewHolder, int i) {

            }

            @Override
            public void onItemDragMoving(RecyclerView.ViewHolder viewHolder, int i, RecyclerView.ViewHolder viewHolder1, int i1) {

            }

            @Override
            public void onItemDragEnd(RecyclerView.ViewHolder viewHolder, int i) {

            }
        };

        foodStepRv.setLayoutManager(new LinearLayoutManager(this));
        foodStepRv.setHasFixedSize(false);
        foodStepRv.setNestedScrollingEnabled(true);
        foodStepAdapter = new FoodStepAdapter(stepinfos);
        foodStepAdapter.openLoadAnimation(new SlideInLeftAnimation());

        foodStepRv.setAdapter(foodStepAdapter);

        foodStepAdapter.notifyDataSetChanged();
        foodStepAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                type = "recycler";
                currentPosition = position;
                if (view.getId() == R.id.add_pic_step_rl) {
                    requestFilePermission();
                    openCamera(1, PictureConfig.SINGLE);
                } else if (view.getId() == R.id.delete_iv) {
                    List<Stepinfo> data = foodStepAdapter.getData();
                    data.remove(position);
                    foodStepAdapter.setNewData(data);
                    foodStepAdapter.notifyDataSetChanged();
                }
            }
        });

        requestFilePermission();
        addViewItem();
        addItemRecycler();

        scroll.setNestedScrollingEnabled(true);
        scroll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
            }
        });
    }

    @Override
    protected void initData() {
        String userId = (String) SPUtils.get(AppConstant.USER_ID, "");
        sendRepicePresenter.loadDraftnum(userId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBusUtil.unregister(this);
    }

    @Override
    public void onBackPressed() {
        if (checkEditStatus()) {
            finish();
        } else {
            popupBackTip();
        }

    }

    private boolean checkEditStatus() {
        boolean back = true;
        String repiceName = repiceNameEt.getText().toString().trim();
        String repiceDesc = repiceDescEt.getText().toString().trim();
        String repiceTip = repiceTipEt.getText().toString().trim();
        List<Stepinfo> data = foodStepAdapter.getData();
        loadViewItem();

        if (repiceCoverIv.getBackground() == null) {
            if (TextUtils.isEmpty(repiceName)) {
                if (TextUtils.isEmpty(repiceDesc)) {
                    if (TextUtils.isEmpty(repiceTip)) {
                        for (int i = 0; i < foodMaterialinfos.size(); i++) {
                            if (TextUtils.isEmpty(foodMaterialinfos.get(i).getName()) && TextUtils.isEmpty(foodMaterialinfos.get(i).getAmount())) {

                            } else {
                                back = false;
                            }
                        }

                        for (int i = 0; i < data.size(); i++) {
                            if (TextUtils.isEmpty(data.get(i).getImg()) && TextUtils.isEmpty(data.get(i).getDesc())) {

                            } else {
                                back = false;
                            }
                        }

                    } else {
                        back = false;
                    }
                } else {
                    back = false;
                }
            } else {
                back = false;
            }
        } else {
            back = false;
        }
        return back;

    }


    @OnClick({R.id.repice_cover_rl, R.id.add_food_material_ll, R.id.adjust_food_material_tv, R.id.more_pic_add_tv, R.id.add_food_step_ll, R.id.adjust_food_step_tv, R.id.release_bt, R.id.save_draft_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.repice_cover_rl:
                type = "top";
                requestFilePermission();
                openCamera(1, PictureConfig.SINGLE);
                break;
            case R.id.add_food_material_ll:
                addViewItem();
                break;
            case R.id.adjust_food_material_tv:
                if (adjustFoodMaterial) {
                    adjustFoodMaterial = false;
                    addFoodMaterialLl.setClickable(true);
                    adjustFoodMaterialTv.setText("调整用料");
                    sortViewItem();
                } else {
                    adjustFoodMaterial = true;
                    addFoodMaterialLl.setClickable(false);
                    adjustFoodMaterialTv.setText("调整完成");
                    sortViewItem();
                }
                break;
            case R.id.more_pic_add_tv:
                type = "more";
                requestFilePermission();
                openCamera(999, PictureConfig.MULTIPLE);
                break;
            case R.id.add_food_step_ll:
                addItemRecycler();
                break;
            case R.id.adjust_food_step_tv:
                if (adjustFoodStep) {
                    adjustFoodStep = false;
                    adjustFoodStepTv.setText("调整步骤");
                    morePicAddTv.setClickable(true);
                    addFoodStepLl.setClickable(true);
                    foodStepAdapter.enableDragItem(null);
                    foodStepAdapter.setOnItemDragListener(null);
                    adjustFoodRv();
                } else {
                    adjustFoodStep = true;
                    adjustFoodStepTv.setText("调整完成");
                    addFoodStepLl.setClickable(false);
                    morePicAddTv.setClickable(false);
                    ItemDragAndSwipeCallback itemDragAndSwipeCallback = new ItemDragAndSwipeCallback(foodStepAdapter);
                    ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemDragAndSwipeCallback);
                    itemTouchHelper.attachToRecyclerView(foodStepRv);
                    // 开启拖拽
                    foodStepAdapter.enableDragItem(itemTouchHelper, R.id.add_pic_step_rl, true);
                    foodStepAdapter.setOnItemDragListener(itemDragListener);
                    adjustFoodRv();
                }

                break;
            case R.id.release_bt:
                submitType = "release";
                SPUtils.put("type", "release");
                /**
                 * 发布和保存都是需要获取所有的item的值，保存在info中，
                 */
                String repiceName1 = repiceNameEt.getText().toString().trim();
                if (!TextUtils.isEmpty(repiceName1) && !TextUtils.isEmpty(titlePath)) {
                    if (foodStepAdapter.getData().size() > 0) {
                        if (!TextUtils.isEmpty(foodStepAdapter.getData().get(0).getDesc()) || !TextUtils.isEmpty(foodStepAdapter.getData().get(0).getImg())) {
                            SPUtils.put("success", false);
                            repiceCreate();
                            startService(new Intent(SendRepiceActivity.this, CreateRepiceService.class));
                            EventBusUtil.sendEvent(new Event(Event.EVENT_UPDATE_FOUBND, "发现页"));
                            finish();
                        } else {
                            ToastUtils.show("发布菜谱必须包含:封面图 菜谱名 有一条步骤或步骤图");
                        }
                    }
                } else {
                    ToastUtils.show("发布菜谱必须包含:封面图 菜谱名 有一条步骤或步骤图");
                }

                break;
            case R.id.save_draft_tv:
                submitType = "draft";
                type = "draft";
                SPUtils.put("type", "draft");
                String repiceName = repiceNameEt.getText().toString().trim();
                if (!TextUtils.isEmpty(repiceName) && !TextUtils.isEmpty(titlePath)) {
                /*    File file = new File(titleThumbPath);
                    RequestBody image = RequestBody.create(MediaType.parse("*"), file);
                    RequestBody watermark = RequestBody.create(MediaType.parse("text/plain"), "yes");
                    MultipartBody.Part part = MultipartBody.Part.createFormData("image", file.getName(), image);
                    sendRepicePresenter.uploadImg(part, watermark);*/

                    SPUtils.put("draftsuccess", false);
                    repiceCreate();
                    initProgressDialog(this);

                    startService(new Intent(SendRepiceActivity.this, CreateRepiceDraftService.class));

                } else {
                    ToastUtils.show("保存草稿必须包含封面图和菜谱名");
                }

                break;
            default:

        }
    }

    /**
     * 将数据收集在对象中返回
     *
     * @return
     */
    private AllMsgFound repiceCreate() {
        String repiceName = repiceNameEt.getText().toString().trim();
        String repiceDesc = repiceDescEt.getText().toString().trim();
        String repiceTip = repiceTipEt.getText().toString().trim();
        List<Stepinfo> data = foodStepAdapter.getData();
        loadViewItem();
        if (img_url == null) {
            img_url = new ArrayList<String>();
        }
        if (thumbnail_url == null) {
            thumbnail_url = new ArrayList<String>();
        }

        AllMsgFound allMsgFound = new AllMsgFound();
        allMsgFound.setHead_url((String) SPUtils.get(AppConstant.USER_ICON, ""));
        allMsgFound.setNickname((String) SPUtils.get(AppConstant.USER_NAME, ""));
        allMsgFound.setCreate_user((String) SPUtils.get(AppConstant.USER_ID, ""));
        allMsgFound.setLike_count(0);
        allMsgFound.setIs_liked(false);
        allMsgFound.setLiked(new ArrayList<LikedInfo>());
        allMsgFound.setIs_collected(false);
        allMsgFound.setIs_recommend("");
        allMsgFound.setMaster_rank(0);
        allMsgFound.setArticle_img("");
        allMsgFound.setArticle_link("");
        allMsgFound.setArticle_title("");
        allMsgFound.setComment(new ArrayList<>());
        allMsgFound.setComment_count(0);
        allMsgFound.setCookbook_type(cookbookType);
        allMsgFound.setDiary_type("cookbook");
        allMsgFound.setIs_top(0);
        allMsgFound.setIs_show("1");
        allMsgFound.setIs_master(false);
        allMsgFound.setTitle(repiceName);
        allMsgFound.setMsg_content(repiceDesc);
        allMsgFound.setMaterial(foodMaterialinfos);
        allMsgFound.setDesc(repiceTip);
        allMsgFound.setCreate_date("刚刚");

        Gson gson = new Gson();

        SPUtils.put(AppConstant.USER_IMAGE, titleThumbPath);
        SPUtils.put(AppConstant.pic, gson.toJson(data));
        SPUtils.put(AppConstant.repice, gson.toJson(allMsgFound));

        SPUtils.put(AppConstant.USER_IMAGE2, titleThumbPath);
        SPUtils.put(AppConstant.pic2, gson.toJson(data));
        SPUtils.put(AppConstant.repice2, gson.toJson(allMsgFound));

        LogUtils.d("步骤信息：" + gson.toJson(data));
        LogUtils.d("菜谱信息：" + gson.toJson(allMsgFound));

        return allMsgFound;
    }

    private void addItemRecycler() {
        Stepinfo foodStepinfo = new Stepinfo();
        foodStepinfo.setEdit(adjustFoodStep);
        foodStepinfo.setThumbnail("");
        foodStepinfo.setImg("");
        foodStepinfo.setDesc("");
        foodStepAdapter.addData(foodStepinfo);
        foodStepAdapter.notifyDataSetChanged();
    }

    private void adjustFoodRv() {
        List<Stepinfo> data = foodStepAdapter.getData();
        for (int i = 0; i < data.size(); i++) {
            Stepinfo foodStepinfo = data.get(i);
            foodStepinfo.setEdit(adjustFoodStep);
            data.set(i, foodStepinfo);
        }
        foodStepAdapter.setNewData(data);
        foodStepAdapter.notifyDataSetChanged();

    }

    private void addViewItem() {
        View inflate = View.inflate(this, R.layout.item_view_food, null);
        foodMaterialLl.addView(inflate);

        sortViewItem();
    }

    private void loadAndAddMaterialView(List<Material> list) {//从草稿箱获取到的数据中的材料List
        for (int i = 0; i < list.size(); i++) {

            View inflate = View.inflate(this, R.layout.item_view_food, null);

            EditText editText_name = inflate.findViewById(R.id.food_name_et);//材料名称
            editText_name.setText(list.get(i).getName());

            EditText editText_amount = inflate.findViewById(R.id.food_use_num_et);//材料数量
            editText_amount.setText(list.get(i).getAmount());

            foodMaterialLl.addView(inflate);
            sortViewItem();
        }

    }

    private void sortViewItem() {
        for (int i = 0; i < foodMaterialLl.getChildCount(); i++) {
            View childAt = foodMaterialLl.getChildAt(i);
            ImageView deleteIv = childAt.findViewById(R.id.delete_iv);
            EditText foodNameEt = childAt.findViewById(R.id.food_name_et);
            EditText foodUseNumEt = childAt.findViewById(R.id.food_use_num_et);
            foodNameEt.setFocusableInTouchMode(false);
            foodUseNumEt.setFocusableInTouchMode(false);
            if (adjustFoodMaterial) {
                deleteIv.setVisibility(View.VISIBLE);
                foodNameEt.setFocusableInTouchMode(false);
                foodNameEt.setFocusable(false);
                foodUseNumEt.setFocusable(false);
                foodUseNumEt.setFocusableInTouchMode(false);
            } else {
                deleteIv.setVisibility(View.GONE);
                foodNameEt.setFocusableInTouchMode(true);
                foodUseNumEt.setFocusableInTouchMode(true);
            }

            deleteIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    foodMaterialLl.removeView(childAt);
                }
            });
        }
    }

    private void loadViewItem() {
        foodMaterialinfos.clear();
        for (int i = 0; i < foodMaterialLl.getChildCount(); i++) {
            View childAt = foodMaterialLl.getChildAt(i);
            EditText foodNameEt = childAt.findViewById(R.id.food_name_et);
            EditText foodUseNumEt = childAt.findViewById(R.id.food_use_num_et);
            FoodMaterialinfo foodMaterialinfo = new FoodMaterialinfo();
            foodMaterialinfo.setName(foodNameEt.getText().toString());
            foodMaterialinfo.setAmount(foodUseNumEt.getText().toString());
            foodMaterialinfos.add(foodMaterialinfo);
        }
    }

    public void openCamera(int max, int type) {
        // 进入相册 以下是例子：用不到的api可以不写
        PictureSelector.create(SendRepiceActivity.this).openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
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
//                .setOutputCameraPath("/CustomPath")// 自定义拍照保存路径,可不填
                .enableCrop(true)// 是否裁剪 true or false
                .compress(true)// 是否压缩 true or false
//                .glideOverride()// int glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                .withAspectRatio(16, 9)// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
//                .hideBottomControls(false)// 是否显示uCrop工具栏，默认不显示 true or false
                .isGif(false)// 是否显示gif图片 true or false
//                .compressSavePath(getPath())//压缩图片保存地址
                .freeStyleCropEnabled(false)// 裁剪框是否可拖拽 true or false
//                .circleDimmedLayer()// 是否圆形裁剪 true or false
                .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                .showCropGrid(true)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
//                .openClickSound()// 是否开启点击声音 true or false
//                .selectionMedia()// 是否传入已选图片 List<LocalMedia> list
                .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                .cropCompressQuality(90)// 裁剪压缩质量 默认90 int
                .minimumCompressSize(300)// 小于300kb的图片不压缩
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                .cropWH(2000, 2000)// 裁剪宽高比，设置如果大于图片本身宽高则无效 int
                .rotateEnabled(true) // 裁剪是否可旋转图片 true or false
//                .scaleEnabled()// 裁剪是否可放大缩小图片 true or false
//                .videoQuality()// 视频录制质量 0 or 1 int
//                .videoMaxSecond(15)// 显示多少秒以内的视频or音频也可适用 int
//                .videoMinSecond(10)// 显示多少秒以内的视频or音频也可适用 int
//                .recordVideoSecond()//视频秒数录制 默认60s int
//                .isDragFrame(false)// 是否可拖动裁剪框(固定)
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片、视频、音频选择结果回调
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    if (selectList != null && selectList.size() > 0) {

                        if (type.equals("more")) {
                            stepinfos.clear();
                        }

                        for (int i = 0; i < selectList.size(); i++) {
                            if (selectList.get(i).isCompressed()) {
                                if (type.equals("top")) {
                                    addLl.setVisibility(View.GONE);
                                    addDescTv.setVisibility(View.GONE);
                                    titlePath = selectList.get(0).getPath();
                                    titleThumbPath = selectList.get(0).getCompressPath();
                                    SPUtils.put(AppConstant.pic, titleThumbPath);
                                    GlideApp.with(SendRepiceActivity.this).load(selectList.get(0).getCompressPath()).placeholder(R.mipmap.icon_empty).diskCacheStrategy(DiskCacheStrategy.NONE).dontAnimate().skipMemoryCache(true).into(repiceCoverIv);

                                } else if (type.equals("recycler")) {
                                    Stepinfo item = foodStepAdapter.getItem(currentPosition);
                                    item.setImg(selectList.get(i).getPath());
                                    item.setThumbnail(selectList.get(i).getCompressPath());
                                    foodStepAdapter.setData(currentPosition, item);
                                    foodStepAdapter.notifyDataSetChanged();
                                } else if (type.equals("more")) {
                                    Stepinfo stepinfo = new Stepinfo();
                                    stepinfo.setEdit(adjustFoodStep);
                                    stepinfo.setImg(selectList.get(i).getPath());
                                    stepinfo.setThumbnail(selectList.get(i).getCompressPath());
                                    stepinfo.setDesc("");
                                    foodStepAdapter.addData(stepinfo);
                                    foodStepAdapter.notifyDataSetChanged();
                                }
                            }
                        }


                    } else {
                        ToastUtils.show("无图片选中");
                    }

                    break;

                case 56://草稿箱返回数据

                        String json_draftsDetail = data.getStringExtra("json_draftsDetail");
                        Log.d("testneed", "onActivityResult: " + json_draftsDetail);
                        DraftsDetail draftsDetail = new Gson().fromJson(json_draftsDetail, DraftsDetail.class);
                        List<String> img_url = draftsDetail.getData().getImg_url();//封面
                        titlePath = img_url.get(0);
//                    repiceCoverIv
                        if (img_url.size() > 0) {
                            GlideApp.with(this).load(img_url.get(0))
//                    .skipMemoryCache(true)
                                    .dontAnimate().diskCacheStrategy(DiskCacheStrategy.ALL).into(repiceCoverIv);

                            addLl.setVisibility(View.GONE);
                            addDescTv.setVisibility(View.GONE);
                        }

                        String title = draftsDetail.getData().getTitle();//菜谱名称
                        repiceNameEt.setText(title);

                        String explain = draftsDetail.getData().getMsg_content();//菜谱说明
                        repiceDescEt.setText(explain);

                        String cookbook_type = draftsDetail.getData().getCookbook_type();//菜谱类型（蒸烤/普通）
                        if ("蒸烤菜谱".equals(cookbook_type)) {
                            repiceSteamingRoastRb.setChecked(true);
                        } else {
                            repiceNormalRb.setChecked(true);
                        }

                        List<Material> material_list = draftsDetail.getData().getMaterial();//材料list
                        loadAndAddMaterialView(material_list);

                        List<Steps> steps_lsit = draftsDetail.getData().getSteps();//步骤list
                        List<Stepinfo> data_temp = new ArrayList<>();//添加进rv的list
                        for (int i = 0; i < steps_lsit.size(); i++) {//将获取到的数据添加到界面原来的数组结构中，Edit属性默认全置为flase
                            Stepinfo stepinfo = new Stepinfo();
                            stepinfo.setDesc(steps_lsit.get(i).getDesc());
                            stepinfo.setEdit(false);
                            stepinfo.setImg(steps_lsit.get(i).getImg());
                            stepinfo.setThumbnail(steps_lsit.get(i).getThumbnail());
                            data_temp.add(stepinfo);
                        }
//                    foodStepAdapter.setNewData(stepinfos);
//                    List<Stepinfo> data = foodStepAdapter.getData();
//                    data.remove(position);
                        foodStepAdapter.setNewData(data_temp);
                        foodStepAdapter.notifyDataSetChanged();
                        String tips = draftsDetail.getData().getDesc();//小贴士
                        repiceTipEt.setText(tips);
                        break;
                    }
            }
        }


    @Override
    public void setData(DrufNumResponse data) {
        if (data != null) {
            if (data.getDraft_num() > 0) {
                repiceTitle.setRightTitle("草稿箱（" + data.getDraft_num() + ")");
                popupOpenRepice();
            }
        }
    }

    @Override
    public void saveOrSendData(BaseResponse data) {
        if (!TextUtils.isEmpty(data.getMsg())) {
            ToastUtils.show(data.getMsg());
        }
        if (submitType.equals("draft")) {
            if (data.getCode() == 0) {
                finish();
            }
        }

    }

    @Override
    public void savePic(ImageResponse data) {
        if (data != null) {
            AllMsgFound allMsgFound = repiceCreate();
            if (data.getData() != null) {
                if (type.equals("draft")) {
                    if (!TextUtils.isEmpty(data.getData().getImg_url())) {
                        img_url.clear();
                        thumbnail_url.clear();
                        img_url.add(data.getData().getImg_url());
                        thumbnail_url.add(data.getData().getThumbnail_url());
                    }
                    allMsgFound.setImg_url(img_url);
                    allMsgFound.setThumbnail_url(thumbnail_url);

                    type = "step";
                    List<Stepinfo> data1 = foodStepAdapter.getData();
                    if (currentLocalMedia < data1.size()) {
                        if (!TextUtils.isEmpty(data1.get(currentLocalMedia).getThumbnail())) {
                            File file = new File(data1.get(currentLocalMedia).getThumbnail());
                            RequestBody image = RequestBody.create(MediaType.parse("*"), file);
                            RequestBody watermark = RequestBody.create(MediaType.parse("text/plain"), "yes");
                            MultipartBody.Part part = MultipartBody.Part.createFormData("image", file.getName(), image);
                            sendRepicePresenter.uploadImg(part, watermark);
                        }
                    } else {
                        allMsgFound.setSteps(new ArrayList<>());
                        Gson gson = new Gson();
                        sendRepicePresenter.loadCookbook("draft", gson.toJson(allMsgFound));
                    }
                } else if (type.equals("step")) {

                    List<Stepinfo> data1 = foodStepAdapter.getData();
                    FoodStepinfo foodStepinfo = new FoodStepinfo();
                    foodStepinfo.setImg(data.getData().getImg_url());
                    foodStepinfo.setThumbnail(data.getData().getThumbnail_url());
                    foodStepinfo.setDesc(data1.get(currentLocalMedia).getDesc());
                    foodStepinfos.add(foodStepinfo);

                    currentLocalMedia++;
                    if (currentLocalMedia < data1.size()) {
                        if (!TextUtils.isEmpty(data1.get(currentLocalMedia).getThumbnail())) {
                            File file = new File(data1.get(currentLocalMedia).getThumbnail());
                            RequestBody image = RequestBody.create(MediaType.parse("*"), file);
                            RequestBody watermark = RequestBody.create(MediaType.parse("text/plain"), "yes");
                            MultipartBody.Part part = MultipartBody.Part.createFormData("image", file.getName(), image);
                            sendRepicePresenter.uploadImg(part, watermark);
                        }
                    } else {
                        allMsgFound.setSteps(foodStepinfos);
                        Gson gson = new Gson();
                        sendRepicePresenter.loadCookbook("draft", gson.toJson(allMsgFound));
                    }
                }
            }
        }

    }

    @Override
    public void netWorkError(String result) {
        if (NetworkUtils.isNetworkAvailable(this)) {
            ToastUtils.show(result);
        } else {
            ToastUtils.show("无可用网络！");
        }
    }

    public void popupBackTip() {
        submitType = "draft";
        if (builder == null) {
            builder = new DialogBackTip.Builder(this);
        }

        builder.setTitle("是否将菜谱保存为草稿？").setLeftText("不保存草稿").setRightText("保存草稿").setRightClickListener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AllMsgFound allMsgFound1 = repiceCreate();
                if (!TextUtils.isEmpty(allMsgFound1.getTitle()) && !TextUtils.isEmpty(titlePath)) {
                    submitType = "draft";
                    type = "draft";
                    String repiceName = repiceNameEt.getText().toString().trim();
                    if (!TextUtils.isEmpty(repiceName) && !TextUtils.isEmpty(titlePath)) {
                        File file = new File(titleThumbPath);
                        RequestBody image = RequestBody.create(MediaType.parse("*"), file);
                        RequestBody watermark = RequestBody.create(MediaType.parse("text/plain"), "yes");
                        MultipartBody.Part part = MultipartBody.Part.createFormData("image", file.getName(), image);
                        sendRepicePresenter.uploadImg(part, watermark);
                    } else {
                        ToastUtils.show("保存草稿必须包含封面图和菜谱名");
                    }
                } else {
                    ToastUtils.show("保存草稿必须包含封面图和菜谱名");
                }
            }
        }).setLeftClickListener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                SPUtils.put("draft", "");
            }
        }).create().show();

    }

    public void  popupCheckCover() {//从草稿箱添加回来的数据确认覆盖弹窗

        if (builder == null) {
            builder = new DialogBackTip.Builder(this);
        }

        builder.setTitle("进入草稿箱当前已编辑菜谱会丢失\n" +
                "是否保留？").setLeftText("不保留").setRightText("保留").setRightClickListener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                scroll.fullScroll(ScrollView.FOCUS_DOWN);
                Toast.makeText(SendRepiceActivity.this, "请点击最下方的“存为草稿”", Toast.LENGTH_SHORT).show();
            }
        }).setLeftClickListener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //todo 点击确认之后清空当前编辑的数据
                foodMaterialLl.removeAllViews();

                foodStepAdapter.removeAllFooterView();


                Intent intent = new Intent(SendRepiceActivity.this, DraftsActivity.class);
                startActivityForResult(intent, 56);
            }
        }).create().show();



    }

    public void popupOpenRepice() {
        if (builder == null) {
            builder = new DialogBackTip.Builder(this);
        }

        builder.setTitle("是否启用上次未完成菜谱").setLeftText("不启用").setRightText("启用").setRightClickListener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                /**
                 * 请求草稿接口，抓去上次草稿内容。
                 */
                Gson gson = new Gson();
                AllMsgFound draft = gson.fromJson(String.valueOf(SPUtils.get("draft", "")), AllMsgFound.class);
                initAllMsgFound(draft);
            }
        }).setLeftClickListener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        }).create().show();

    }

    private void initAllMsgFound(AllMsgFound draft) {

        int childCount = foodMaterialLl.getChildCount();
        for (int i = 0; i < childCount; i++) {
            foodMaterialLl.removeViewAt(i);
        }

        stepinfos.clear();
        foodStepAdapter.setNewData(stepinfos);

        if (!TextUtils.isEmpty(draft.getTitle())) {
            repiceNameEt.setText(draft.getTitle());
        }

        if (!TextUtils.isEmpty(draft.getMsg_content())) {
            repiceDescEt.setText(draft.getMsg_content());
        }

        if (!TextUtils.isEmpty(draft.getDesc())) {
            repiceTipEt.setText(draft.getDesc());
        }
        if (!TextUtils.isEmpty(draft.getCookbook_type())) {
            if (draft.getCookbook_type().equals("普通菜谱")) {
                repiceNormalRb.setChecked(true);
                repiceSteamingRoastRb.setChecked(false);
            } else {
                repiceNormalRb.setChecked(false);
                repiceSteamingRoastRb.setChecked(true);
            }
        }


        if (draft.getImg_url() != null && draft.getImg_url().size() > 0) {
            titlePath = draft.getImg_url().get(0);
            titleThumbPath = draft.getThumbnail_url().get(0);

            GlideApp.with(SendRepiceActivity.this).load(draft.getImg_url().get(0)).placeholder(R.mipmap.icon_empty).diskCacheStrategy(DiskCacheStrategy.NONE).dontAnimate().skipMemoryCache(true).into(repiceCoverIv);
        }

        if (draft.getSteps() != null && draft.getSteps().size() > 0) {
            for (int i = 0; i < draft.getSteps().size(); i++) {
                Stepinfo stepinfo = new Stepinfo();
                stepinfo.setEdit(false);
                stepinfo.setDesc(draft.getSteps().get(i).getDesc());
                stepinfo.setImg(draft.getSteps().get(i).getImg());
                stepinfo.setThumbnail(draft.getSteps().get(i).getThumbnail());
                foodStepAdapter.addData(stepinfo);
            }
            foodStepAdapter.notifyDataSetChanged();
        }

        if (draft.getMaterial() != null && draft.getMaterial().size() > 0) {

            for (int i = 0; i < draft.getMaterial().size(); i++) {
                View inflate = View.inflate(this, R.layout.item_view_food, null);
                foodMaterialLl.addView(inflate);
                View childAt = foodMaterialLl.getChildAt(i);
                EditText foodNameEt = childAt.findViewById(R.id.food_name_et);
                EditText foodUseNumEt = childAt.findViewById(R.id.food_use_num_et);
                foodNameEt.setText(draft.getMaterial().get(i).getName());
                foodUseNumEt.setText(draft.getMaterial().get(i).getAmount() + "");

            }
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (XXPermissions.isHasPermission(SendRepiceActivity.this, Permission.Group.STORAGE)) {
            hasPermission(null, true);
        } else {
            requestFilePermission();
        }
    }

    private void requestFilePermission() {
        XXPermissions.with(this).permission(Permission.Group.STORAGE).request(this);
    }


    @Override
    public void hasPermission(List<String> granted, boolean isAll) {

    }

    @Override
    public void noPermission(List<String> denied, boolean quick) {
        if (quick) {
            ToastUtils.show("没有权限访问文件，请手动授予权限");
            XXPermissions.gotoPermissionSettings(SendRepiceActivity.this, true);
        } else {
            ToastUtils.show("请先授予文件读写权限");
            getWindow().getDecorView().postDelayed(new Runnable() {
                @Override
                public void run() {
                    requestFilePermission();
                }
            }, 2000);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void GetEvent(Event event) {
        if (event.getType() == Event.EVENT_SAVE_SUCCESS) {
            if (builder3 != null) {
                builder3.dismiss();
            }
            finish();
        }
    }

    private void initProgressDialog(Context context) {
        if (builder3 == null) {
            builder3 = new DialogCircleProgress.Builder(context);
//            pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);// 设置进度条的形式为圆形转动的进度条
            builder3.setCancelable(true);
            builder3.setDialogSize(120, context);
            if (true) {
                builder3.setListener(new DialogCircleProgress.Builder.CircleProgressListener() {
                    @Override
                    public void onCancel() {

                    }
                });
            }
            builder3.create().show();
            if (!builder3.isShowing()) {
                builder3.dismiss();
            }
        }
    }


}
