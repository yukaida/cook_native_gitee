package com.ebanswers.kitchendiary.mvp.view.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ebanswers.kitchendiary.R;
import com.ebanswers.kitchendiary.adapter.SendDiaryPicAdapter;
import com.ebanswers.kitchendiary.adapter.TopicListAdapter;
import com.ebanswers.kitchendiary.bean.DiaryPicinfo;
import com.ebanswers.kitchendiary.bean.DiaryServiceNeed.ContentBean;
import com.ebanswers.kitchendiary.bean.DiaryServiceNeed.PiclistBean;
import com.ebanswers.kitchendiary.bean.Topics.NormalTopics;
import com.ebanswers.kitchendiary.bean.Topics.Topics;
import com.ebanswers.kitchendiary.common.CommonActivity;
import com.ebanswers.kitchendiary.config.Constans;
import com.ebanswers.kitchendiary.constant.AppConstant;
import com.ebanswers.kitchendiary.eventbus.Event;
import com.ebanswers.kitchendiary.eventbus.EventBusUtil;
import com.ebanswers.kitchendiary.mvp.view.mine.TagActivity;
import com.ebanswers.kitchendiary.mvp.view.mine.TestCodeinActivity;
import com.ebanswers.kitchendiary.network.api.ApiMethods;
import com.ebanswers.kitchendiary.network.observer.MyObserver;
import com.ebanswers.kitchendiary.network.observer.ObserverOnNextListener;
import com.ebanswers.kitchendiary.retrofit.RetrofitTask;
import com.ebanswers.kitchendiary.service.CreateDiaryService;
import com.ebanswers.kitchendiary.utils.NetworkUtils;
import com.ebanswers.kitchendiary.utils.SPUtils;
import com.ebanswers.kitchendiary.utils.ToastCustom;
import com.ebanswers.kitchendiary.wxapi.WXEntryActivity;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.umeng.socialize.utils.DeviceConfigInternal.context;

public class SendDiaryActivity extends CommonActivity implements OnPermission {
    private static final String TAG = "SendDiaryActivity";
    @BindView(R.id.diary_title)
    TitleBar diaryTitle;
    @BindView(R.id.diary_editText)
    EditText diaryEditText;
    @BindView(R.id.diary_imageView_topic)
    ImageView diaryImageViewTopic;
    @BindView(R.id.diary_imageView_link)
    ImageView diaryImageViewLink;
    @BindView(R.id.diary_textView_topic)
    TextView diaryTextViewTopic;
    @BindView(R.id.diary_textView_inputjiontopic)
    TextView diaryTextViewInputjiontopic;
    @BindView(R.id.diary_textView_link)
    EditText diaryTextViewLink;
    @BindView(R.id.diary_rv_add)
    RecyclerView diaryRvAdd;
    @BindView(R.id.diary_imageview_add)
    ImageView diaryImageviewAdd;
    @BindView(R.id.diary_textView_fabu)
    Button diaryTextViewFabu;
    @BindView(R.id.diary_textView_recommand)
    TextView diaryTextViewRecommand;
    @BindView(R.id.diary_recyclerView_topiclist)//话题列表
     RecyclerView diaryRecyclerViewTopiclist;

    @BindView(R.id.diary_imageView_clock)
    ImageView diaryImageViewClock;
    boolean imageviewClock = false;

    @BindView(R.id.diary_textView_clock)
    TextView diaryTextViewClock;
    boolean textviewClock = false;

    @BindView(R.id.diary_textView_rule)
    TextView diaryTextViewRule;

    private List<DiaryPicinfo> piclist = new ArrayList<>();
    private SendDiaryPicAdapter sendDiaryPicAdapter;
    private String topic = "";
    private String days = "1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);
        Intent intent = getIntent();
        if (intent != null) {
            topic = intent.getStringExtra("topic");
            diaryEditText.setText(topic);
        }

        String openid = (String) SPUtils.get(AppConstant.USER_ID, "");
        if (openid != null && openid.length() > 3) {
            RetrofitTask.getClockDay(Constans.USER_CLOCKDAYS, openid,new RetrofitTask.CallBack<String>() {
                @Override
                public void result(String s) {
                    if (!TextUtils.isEmpty(s)) {
                        try {
                            JSONObject json = new JSONObject(s);
                             days = json.getString("this_pub_day");
                            diaryTextViewClock.setText("打卡第"+days+"天");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onError() {

                }
            });
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_send_diary;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.diary_title;
    }

    @Override
    protected void initView() {


        diaryTitle.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(View v) {
                finish();
            }

            @Override
            public void onTitleClick(View v) {
            }

            @Override
            public void onRightClick(View v) {
            }
        });

        sendDiaryPicAdapter = new SendDiaryPicAdapter(this, R.layout.item_diary_pic, piclist);

        diaryRvAdd.setAdapter(sendDiaryPicAdapter);
        diaryRvAdd.setLayoutManager(new GridLayoutManager(this, 3));

        sendDiaryPicAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

                switch (view.getId()) {
                    case R.id.item_diary_image:
                        Log.d(TAG, "onItemChildClick: image");

                        LayoutInflater inflater = LayoutInflater.from(SendDiaryActivity.this);
                        View imgEntryView = inflater.inflate(R.layout.dialog_photo, null);
                        // 加载自定义的布局文件
                        final AlertDialog dialog = new AlertDialog.Builder(SendDiaryActivity.this).create();
                        ImageView img = imgEntryView.findViewById(R.id.large_image);
                        Glide.with(SendDiaryActivity.this).load(piclist.get(position).getImagePath()).into(img);

//                        GlideApp.with(SendDiaryActivity.this).load(selectList.get(0).getPath()).dontAnimate().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(userIcon);
                        dialog.setView(imgEntryView); // 自定义dialog
                        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                        dialog.show();

                        // 点击大图关闭dialog
                        imgEntryView.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View paramView) {
                                dialog.cancel();
                            }
                        });

                        break;
                    case R.id.item_diary_delete:
                        Log.d(TAG, "onItemChildClick: delete");
                        piclist.remove(position);
                        sendDiaryPicAdapter.notifyDataSetChanged();
                        diaryImageviewAdd.setVisibility(View.VISIBLE);
                        break;
                }

            }
        });

        diaryTextViewFabu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    public void hasPermission(List<String> granted, boolean isAll) {

    }

    @Override
    public void noPermission(List<String> denied, boolean quick) {

    }

    //--------------------------------------------------输入法弹窗相关
    protected void closeInputMethod() {//关闭输入法
        if (checkInputMethodIsOpen()) {
            if (this.getCurrentFocus() != null && this.getCurrentFocus().getWindowToken() != null) {
                InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    protected boolean checkInputMethodIsOpen() {//检查“输入法”是否在工作
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        return imm.isActive();
    }

    //---------------------------------------------------点击事件
    @OnClick({R.id.diary_textView_recommand, R.id.diary_imageview_add, R.id.diary_textView_fabu, R.id.diary_textView_topic, R.id.diary_imageView_clock, R.id.diary_textView_clock, R.id.diary_textView_rule})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.diary_imageView_clock:
                if (imageviewClock == false) {
                    imageviewClock = true;
                    textviewClock = true;
                    diaryImageViewClock.setImageResource(R.drawable.radio_able);
                    String text = diaryEditText.getText().toString().trim();
                    diaryEditText.setText(text + "#打卡第" + days + "天#");

                } else {
                    imageviewClock = false;
                    textviewClock = false;
                    diaryImageViewClock.setImageResource(R.drawable.radio_disable);
                    String text_todelete = diaryEditText.getText().toString().trim();
                    String toreplace = "#打卡第" + days + "天#";
                    String text_toadd=text_todelete.replaceAll(toreplace, "");
                    diaryEditText.setText(text_toadd);
                }
                break;
            case R.id.diary_textView_clock:
                if (imageviewClock == false) {
                    imageviewClock = true;
                    textviewClock = true;
                    String text = diaryEditText.getText().toString().trim();
                    diaryEditText.setText(text + "#打卡第" + days + "天#");
                } else {
                    imageviewClock = false;
                    textviewClock = false;
                    diaryImageViewClock.setImageResource(R.drawable.radio_disable);
                    String text_todelete = diaryEditText.getText().toString().trim();
                    String toreplace = "#打卡第" + days + "天#";
                    String text_toadd=text_todelete.replaceAll(toreplace, "");
                    diaryEditText.setText(text_toadd);
                }
                break;
            case R.id.diary_textView_rule:
                Intent intent = new Intent(SendDiaryActivity.this, WebActivity.class);
                intent.putExtra("url", Constans.URL_RULE);
                startActivity(intent);
                break;
            case R.id.diary_textView_recommand://推荐一篇
                closeInputMethod();
                if (diaryTextViewLink.getVisibility() == View.GONE)
                    diaryTextViewLink.setVisibility(View.VISIBLE);
                else {
                    diaryTextViewLink.setVisibility(View.GONE);
                }
                break;
            case R.id.diary_imageview_add://照片选择
                Log.d("testneed", "onViewClicked: ");
                int choiceCount = 9 - sendDiaryPicAdapter.getData().size();
                openCamera(choiceCount, PictureConfig.MULTIPLE);
                break;
            case R.id.diary_textView_fabu://发布
                if (diaryTextViewLink.getVisibility() == View.VISIBLE) {
                    String url_link = diaryTextViewLink.getText().toString().trim();

                    if (url_link.length() > 1) {
                        if (Patterns.WEB_URL.matcher(url_link).matches() || URLUtil.isValidUrl(url_link)) {
                        } else {
                            Toast.makeText(this, "请输入合法的网址", Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }
                }
                String text_input = diaryEditText.getText().toString().trim();
                if (text_input != null && text_input.length() > 1) {
                } else {
                    Toast.makeText(this, "请输入有效信息", Toast.LENGTH_SHORT).show();
                    break;
                }

                if (isContainChinese(text_input) || isENChar(text_input) || HasDigit(text_input)) {//限定输入的信息中必须有中文,英文,阿拉伯数字
                } else {
                    Toast.makeText(this, "请输入有效信息", Toast.LENGTH_SHORT).show();
                    break;
                }

                if (NetworkUtils.checkNetwork(this)) {
                    if (diaryEditText.getText().length() > 1) {
                        HashMap<String, String> map = new HashMap<>();
                        map.put("topic_id", "");
                        map.put("msg", diaryEditText.getText().toString().trim());//主要文字内容
                        map.put("article_link", diaryTextViewLink.getText().toString().trim());//超链接
                        map.put("nickname", "");//昵称（空）
                        map.put("head_url", "");//（空）
                        map.put("openid", (String) SPUtils.get(AppConstant.USER_ID, ""));//用户id
                        map.put("msg_type", "diary");//发布类型（diary）

                        Log.d(TAG, "onViewClicked: ");

                        List<DiaryPicinfo> imagelist = sendDiaryPicAdapter.getData();

                        //构建intent 把数据传递过去
                        Intent intent_toDiaryService = new Intent(SendDiaryActivity.this, CreateDiaryService.class);

                        ContentBean contentBean = new ContentBean();
                        contentBean.setMap(map);

                        PiclistBean piclistBean = new PiclistBean();
                        piclistBean.setList(imagelist);

                        intent_toDiaryService.putExtra("ContentBean", new Gson().toJson(contentBean));//文本信息-发送

//                intent_toDiaryService.putExtra("map", map);//文本信息
                        intent_toDiaryService.putExtra("PiclistBean", new Gson().toJson(piclistBean));//图片列表-发送

                        SPUtils.put("ContentBean", new Gson().toJson(contentBean));//文本信息-展示
                        SPUtils.put("PiclistBean", new Gson().toJson(piclistBean));//图片信息-展示

                        Log.d(TAG, "yukaida1: " + SPUtils.get("PiclistBean", new String()));

                        startService(intent_toDiaryService);
                        EventBusUtil.sendEvent(new Event(Event.EVENT_UPDATE_FOUND, "发现页"));

                        finish();
                        break;
                    } else {
                        ToastUtils.show("请输入日记内容");
                        break;
                    }
                } else {
                    Toast.makeText(this, "请检查网络连接", Toast.LENGTH_SHORT).show();
                    break;
                }

            case R.id.diary_textView_topic:
                String openid = (String) SPUtils.get(AppConstant.USER_ID, "");
                getTopic(openid);//获取话题列表
                Log.d(TAG, "onViewClicked: " + openid);
                break;

        }
    }

    //-----------图片选择
    public void openCamera(int max, int type) {
        // 进入相册 以下是例子：用不到的api可以不写
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
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
//                .enableCrop(true)// 是否裁剪 true or false
                .compress(true)// 是否压缩 true or false
//                .glideOverride()// int glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
//                .withAspectRatio(1,1)// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
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


    public void openCamera2(int max, int type) {
        // 进入相册 以下是例子：用不到的api可以不写
        PictureSelector.create(SendDiaryActivity.this)
                .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .theme(R.style.picture_default_style)//主题样式(不设置为默认样式) 也可参考demo values/styles下 例如：R.style.picture.white.style
                .maxSelectNum(max)// 最大图片选择数量 int
                .minSelectNum(1)// 最小选择数量 int
                .imageSpanCount(4)// 每行显示个数 int
                .selectionMode(type)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .previewImage(false)// 是否可预览图片 true or false
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
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                .cropWH(360, 200)// 裁剪宽高比，设置如果大于图片本身宽高则无效 int
                .rotateEnabled(true) // 裁剪是否可旋转图片 true or false
//                .scaleEnabled()// 裁剪是否可放大缩小图片 true or false
//                .videoQuality()// 视频录制质量 0 or 1 int
//                .videoMaxSecond(15)// 显示多少秒以内的视频or音频也可适用 int
//                .videoMinSecond(10)// 显示多少秒以内的视频or音频也可适用 int
//                .recordVideoSecond()//视频秒数录制 默认60s int
//                .isDragFrame(false)// 是否可拖动裁剪框(固定)
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }


    private void requestFilePermission() {
        XXPermissions.with(this).permission(Permission.Group.STORAGE).request(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
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
                        if (piclist.size() >= 9) {
                            Toast.makeText(this, "最多只能选择9张图片哦", Toast.LENGTH_SHORT).show();
                        } else {
                            for (int i = 0; i < selectList.size(); i++) {
//                            String url = Base64Utils.imageToBase64(selectList.get(0).getPath());
//                                String url = selectList.get(i).getPath();
                                String url = selectList.get(i).getCompressPath();
                                Log.d("testneed", "onActivityResult: " + url);
                                piclist.add(new DiaryPicinfo(url));


                                if (sendDiaryPicAdapter.getData().size() == 9) {
                                    diaryImageviewAdd.setVisibility(View.GONE);
                                } else {
                                    diaryImageviewAdd.setVisibility(View.VISIBLE);
                                }

                            }
                            Log.d("testneed", "onActivityResult: " + piclist.size());
                            sendDiaryPicAdapter.notifyDataSetChanged();
                        }

//                        GlideApp.with(this).load(selectList.get(0).getPath()).dontAnimate().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(userIcon);
                    }
                } else {
                    ToastUtils.show("无图片选中");
                }

                break;
        }
    }

    public void getTopic(String openid) {//获取话题列表
        ObserverOnNextListener<Topics, Throwable> listener = new ObserverOnNextListener<Topics, Throwable>() {
            @Override
            public void onNext(Topics topics) {
                if (topics.getCode() == 0) {
                    Log.d(TAG, "onNext:topic " + topics.toString());
                    //todo 将获取到的话题列表载入recyclerView中

                    List<NormalTopics> data = topics.getData();
                    List<String> topic_list = new ArrayList<>();
                    for (int i = 0; i < data.size(); i++) {
                        StringBuilder builder = new StringBuilder();
                        builder.append("#");
                        builder.append(data.get(i).getTopic_content());
                        builder.append("#");
                        topic_list.add(builder.toString());
                    }

                    diaryRecyclerViewTopiclist.setLayoutManager(new LinearLayoutManager(SendDiaryActivity.this));
                    TopicListAdapter topicListAdapter = new TopicListAdapter(R.layout.item_topic, topic_list);
                    diaryRecyclerViewTopiclist.setAdapter(topicListAdapter);

                    topicListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                        @Override
                        public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

                            String topic_onClick = topic_list.get(position);

                            int index = diaryEditText.getSelectionStart();//获取光标所在位置
                            String text = topic_onClick;
                            Editable edit = diaryEditText.getEditableText();//获取EditText的文字
                            if (index < 0 || index >= edit.length()) {
                                edit.append(text);
                            } else {
                                edit.insert(index, text);//光标所在位置插入文字
                            }

                        }
                    });
                } else {
                    Toast.makeText(context, "未获取到正确数据，请重试", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable throwable) {
                Toast.makeText(context, "后台网络异常", Toast.LENGTH_SHORT).show();
            }
        };
        ApiMethods.getTopic(new MyObserver<Topics>(SendDiaryActivity.this, listener), openid);
    }


    /**
     * 判断字符串中是否包含中文
     *
     * @param str 待校验字符串
     * @return 是否为中文
     * @warn 不能校验是否为中文标点符号
     */
    public static boolean isContainChinese(String str) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }

    /*
     * 判断字符串中是否含有英文，包含返回true
     */
    public boolean isENChar(String string) {
        boolean flag = false;
        Pattern p = Pattern.compile("[a-zA-z]");
        if (p.matcher(string).find()) {
            flag = true;
        }
        return flag;
    }

    // 判断一个字符串是否含有数字
    public boolean HasDigit(String content) {
        boolean flag = false;
        Pattern p = Pattern.compile(".*\\d+.*");
        Matcher m = p.matcher(content);
        if (m.matches()) {
            flag = true;
        }
        return flag;
    }

}
