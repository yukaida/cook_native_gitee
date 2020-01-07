package com.ebanswers.kitchendiary.mvp.openjs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.ebanswers.kitchendiary.common.CommonApplication;
import com.ebanswers.kitchendiary.config.ShareContentConfig;
import com.ebanswers.kitchendiary.constant.AppConstant;
import com.ebanswers.kitchendiary.mvp.view.base.ImageLookActivity;
import com.ebanswers.kitchendiary.mvp.view.base.LoginActivity;
import com.ebanswers.kitchendiary.mvp.view.base.SendDiaryActivity;
import com.ebanswers.kitchendiary.mvp.view.base.WebActivity;
import com.ebanswers.kitchendiary.mvp.view.base.WelActivity;
import com.ebanswers.kitchendiary.utils.SPUtils;
import com.hjq.toast.ToastUtils;
import com.hjq.toast.style.ToastQQStyle;
import com.previewlibrary.GPreviewBuilder;
import com.previewlibrary.enitity.ThumbViewInfo;
import com.trycatch.mysnackbar.TSnackbar;

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

//    /**
//     * 打开发布日记界面
//     */
//    @JavascriptInterface
//    public void openPictures() {
//        Log.d("openPictures", "openPictures");
//        if (webView != null && webView.get() != null) {
//            Context context = webView.get().getContext();
//            if (!KitchenDiaryApplication.getInstance().isLogin()) {
//                showLogin();
//            } else if (context instanceof FragmentActivity) {
//                FragmentActivity activity = (FragmentActivity) context;
//                Intent intent = new Intent(activity, PublishActvity.class);
//                intent.putExtra(Constans.Publish_Flag_Key, Constans.Publish_Diary_Flag);
//                activity.startActivity(intent);
//                UmengAnalyze.onEvent(webView.get().getContext(), UmengAnalyze.PUBLISH_OPEN);
//            }
//        }
//    }
//
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
            }
            else  {
                WebActivity webActivity = (WebActivity) context;
                Intent intent = new Intent(webActivity, SendDiaryActivity.class);
                intent.putExtra("topic", topic);

                webActivity.startActivity(intent);
            }
        }
//        Context context = webView.get().getContext();
//        Activity activity = (Activity) webView.get().getContext();


    }





    @JavascriptInterface
    public void preImage(String json) {//网页图片大图预览
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
//                            .setType(GPreviewBuilder.IndicatorType.Number)
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

}
