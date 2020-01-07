package com.ebanswers.kitchendiary.mvp.openjs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.ebanswers.kitchendiary.R;
import com.ebanswers.kitchendiary.common.CommonApplication;
import com.ebanswers.kitchendiary.config.ShareContentConfig;
import com.ebanswers.kitchendiary.constant.AppConstant;
import com.ebanswers.kitchendiary.mvp.view.base.ImageLookActivity;
import com.ebanswers.kitchendiary.mvp.view.base.LoginActivity;
import com.ebanswers.kitchendiary.mvp.view.base.SendDiaryActivity;
import com.ebanswers.kitchendiary.mvp.view.base.WebActivity;
import com.ebanswers.kitchendiary.mvp.view.base.WelActivity;
import com.ebanswers.kitchendiary.utils.DialogUtils;
import com.ebanswers.kitchendiary.utils.LogUtils;
import com.ebanswers.kitchendiary.utils.SPUtils;
import com.hjq.toast.ToastUtils;
import com.hjq.toast.style.ToastQQStyle;
import com.previewlibrary.GPreviewBuilder;
import com.previewlibrary.enitity.ThumbViewInfo;
import com.trycatch.mysnackbar.TSnackbar;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class JsApi {
    private WeakReference<WebView> webView;
    private ShareListener umShareListener;
    private static final String ERROR = "error";
    private String quickImgState, qucickImgUrl;
    private TSnackbar snackBar;

    public JsApi(final WeakReference<WebView> weakReference) {
        this.webView = weakReference;
        umShareListener = new ShareListener(new WeakReference<View>(webView.get()));
    }

    private void clearShareData() {
        ShareContentConfig.title = "";
        ShareContentConfig.content = "";
        ShareContentConfig.targetUrl = "";
        ShareContentConfig.imgUrl = "";
        quickImgState = "";
        qucickImgUrl = "";
    }

    private int isExist = 0;
    private String title ="";
    private String content ="";
    private String targetUrl ="";
    private String imgUrl ="";

    /**
     * 打开发布日记界面，带话题
     *
     * @param topic
     */
    @JavascriptInterface
    public void openPictures(String topic) {
        Context context = webView.get().getContext();
        Log.d("openPictures", "openPictures topic");
        if (webView != null && webView.get() != null) {

            if (getOpenId().equals("tmp_user")) {
                showLogin();
            } else {
                WebActivity webActivity = (WebActivity) context;
                Intent intent = new Intent(webActivity, SendDiaryActivity.class);
                intent.putExtra("topic", topic);

                webActivity.startActivity(intent);
            }
        }
    }


    @JavascriptInterface
    public void preImage(String json) {
        Context context = webView.get().getContext();
        WebActivity webActivity = (WebActivity) context;
        if (!TextUtils.isEmpty(json)) {
            Log.d("JsApi", json);
            try {
                JSONObject jsonObject = new JSONObject(json);
                String current = jsonObject.getString("current");
//                final ArrayList<String> list_str = new ArrayList<>();
//                JSONArray jsonArray = jsonObject.getJSONArray("urls");
//                int count = jsonArray.length();
//                for (int i = 0; i < count; i++) {
//                    list_str.add(jsonArray.getString(i));
//                }
                if (webView != null && webView.get() != null && (webView.get().getContext() instanceof Activity)) {


                    //组织数据
                    ArrayList<ThumbViewInfo> mThumbViewInfoList = new ArrayList<>(); // 这个最好定义成成员变量
                    ThumbViewInfo iteminfo;
                    mThumbViewInfoList.clear();
//                    for (int i = 0; i < list_str.size(); i++) {
                    Rect bounds = new Rect();
                    //new ThumbViewInfo(图片地址);
                    iteminfo = new ThumbViewInfo(current);
                    iteminfo.setBounds(bounds);
                    mThumbViewInfoList.add(iteminfo);
//                    }
                    Log.d("catch13", "preImage: " + current);
                    //打开预览界面

                    GPreviewBuilder.from( webActivity)
                            //是否使用自定义预览界面，当然8.0之后因为配置问题，必须要使用
                            .to(ImageLookActivity.class)
                            .setData(mThumbViewInfoList)
                            .setCurrentIndex(0)
                            .setSingleFling(true)
                            .setType(GPreviewBuilder.IndicatorType.Number)
                            // 小圆点
                            //  .setType(GPreviewBuilder.IndicatorType.Dot)
                            .start();//启动
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @JavascriptInterface
    public void showLogin() {
        if (webView != null && webView.get() != null) {
            Context context = webView.get().getContext();
            ToastUtils.show("请先登录");
//        com.luck.picture.lib.tools.ToastUtils.s(context,"请先登录");
//        Toast.makeText(context, "请先登录", Toast.LENGTH_SHORT).show();
            WebActivity webActivity = (WebActivity) context;
            Intent intent = new Intent(webActivity, WelActivity.class);
            webActivity.startActivity(intent);
        }
    }


    public static String getOpenId() {
        String userId = (String) SPUtils.get(AppConstant.USER_ID, "");
        if (TextUtils.isEmpty(userId)) {
            return "tmp_user";
        }
        return userId;
    }

    @JavascriptInterface
    public void shareQuickImg(String state, String img_url) {
        Log.d("jsApi", "shareQuickImg: ");
        quickImgState = state;
        qucickImgUrl = img_url;
    }

    @JavascriptInterface
    public void quickOpenShare() {
        Log.d("JsApi", "quickOpenShare");
        if (webView != null && webView.get() != null) {
            Context context = webView.get().getContext();
            WebActivity webActivity = (WebActivity) context;
            if (SPUtils.getIsLogin()) {
                UMImage image2;
                Log.d("catch13", "quickOpenShare: "+imgUrl);
                if (imgUrl != null&&imgUrl.length()>1) {
                    image2 = new UMImage(webActivity, imgUrl);//分享图标

                } else {
//                    Log.d("catch13", "quickOpenShare:2 "+imgUrl);
//                    image = new UMImage(webActivity, R.drawable.icon_logo);
                    image2 = new UMImage(webActivity, imgUrl);//分享图标
                }

                image2.compressStyle = UMImage.CompressStyle.SCALE;//大小压缩，默认为大小压缩，适合普通很大的图
//                image.compressStyle = UMImage.CompressStyle.QUALITY;//质量压缩，适合长图的分享
//                        压缩格式设置
//                 image.compressFormat = Bitmap.CompressFormat.PNG;//用户分享透明背景的图片可以设置这种方式，但是qq好友，微信朋友圈，不支持透明背景图片，会变成黑色
                final UMWeb web = new UMWeb(targetUrl);
                //切记切记 这里分享的链接必须是http开头
//                final UMWeb web = new UMWeb("https://wechat.53iq.com/tmp/kitchen/diary/" + item.getDiary_id() + "/detail?code=123"); //切记切记 这里分享的链接必须是http开头
                web.setTitle(title);//标题
                web.setThumb(image2);  //缩略图
                web.setDescription(content);//描述
                new ShareAction(webActivity).withMedia(web).setDisplayList(SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE,
                        SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE).setShareboardclickCallback(new ShareBoardlistener() {
                    @Override
                    public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
                        if (share_media == SHARE_MEDIA.QQ) {
                            LogUtils.e("点击QQ");
                            new ShareAction(webActivity).setPlatform(SHARE_MEDIA.QQ).withMedia(web).setCallback(umShareListener).share();
                        } else if (share_media == SHARE_MEDIA.WEIXIN) {
                            LogUtils.e("点击微信");
                            new ShareAction(webActivity).setPlatform(SHARE_MEDIA.WEIXIN).withMedia(web).setCallback(umShareListener).share();
                        } else if (share_media == SHARE_MEDIA.QZONE) {
                            new ShareAction(webActivity).setPlatform(SHARE_MEDIA.QZONE).withMedia(web).setCallback(umShareListener).share();
                        } else if (share_media == SHARE_MEDIA.WEIXIN_CIRCLE) {
                            new ShareAction(webActivity).setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE).withMedia(web).setCallback(umShareListener).share();
                        }
                    }
                }).open();
            } else {
                webActivity.startActivity(new Intent(webActivity, WelActivity.class));
            }
        }
    }


    /**
     * 分享数据来源
     */
    @JavascriptInterface
    public void shareMsg(String title, String content, String targetUrl, String imgUrl) {
        Log.d("JsApi", "shareMsg: " + title + "," + content);
        clearShareData();
        quickImgState = "";
        qucickImgUrl = "";
        isExist++;
        if (isExist == 1) {
            this.title = title;
            this.content = content;
            this.targetUrl = targetUrl;
            this.imgUrl = imgUrl;
        }

//        ShareContentConfig.title = title;
//        ShareContentConfig.content = content;
//        ShareContentConfig.targetUrl = targetUrl;
//        ShareContentConfig.imgUrl = imgUrl;
    }
}
