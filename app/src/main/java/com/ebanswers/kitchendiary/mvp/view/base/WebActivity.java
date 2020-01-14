package com.ebanswers.kitchendiary.mvp.view.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.CookieManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.TransitionOptions;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.ebanswers.baselibrary.utils.WebViewLifecycleUtils;
import com.ebanswers.kitchendiary.R;
import com.ebanswers.kitchendiary.common.CommonActivity;
import com.ebanswers.kitchendiary.common.CommonApplication;
import com.ebanswers.kitchendiary.constant.AppConstant;
import com.ebanswers.kitchendiary.mvp.openjs.JsApi;
import com.ebanswers.kitchendiary.mvp.openjs.OnJsOpen;
import com.ebanswers.kitchendiary.utils.GlideApp;
import com.ebanswers.kitchendiary.utils.ImageLoader;
import com.ebanswers.kitchendiary.utils.LogUtils;
import com.ebanswers.kitchendiary.utils.SPUtils;
import com.ebanswers.kitchendiary.utils.SavePictureUtils;
import com.ebanswers.kitchendiary.utils.ScreenSizeUtils;
import com.ebanswers.kitchendiary.utils.ToastCustom;
import com.hjq.permissions.OnPermission;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.hjq.toast.ToastUtils;
import com.previewlibrary.ZoomMediaLoader;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;

import static android.webkit.WebView.setWebContentsDebuggingEnabled;
import static com.umeng.commonsdk.stateless.UMSLEnvelopeBuild.mContext;

/*
 *    desc   : 浏览器界面
 */
public class WebActivity extends CommonActivity implements OnPermission {
    private OnJsOpen onJsOpen;
    @BindView(R.id.pb_web_progress)
    ProgressBar mProgressBar;
    @BindView(R.id.wv_web_view)
    WebView mWebView;
    private JsApi mJsApi;
    private String currentUrl;
    private boolean isAppendId;///  是否拼接id
    private WebView.HitTestResult hitTestResult;

    private  PopupWindow popupWindow;

    private String url;
    private int flag, tag;
    private boolean isHideTitle, isInputChanged = false;
    private String preLoadUrl;
    private String picUrl = "";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_web;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_web_title;
    }

    @Override
    protected void initView() {

            CookieManager cookieManager = CookieManager.getInstance();


        HashSet<String> perferences = (HashSet) CommonApplication.getInstance().getSharedPreferences("cookieData", Context.MODE_PRIVATE).getStringSet("cookie", null);

        for (String cookie:perferences) {
            cookieManager.setCookie("https://wechat.53iq.com/",cookie);
        }

        cookieManager.setCookie("https://wechat.53iq.com/","partner_id=ddb4c038579a11e59e8800a0d1eb6068; X-sourcediary; ");




//        cookieManager.setCookie("https://wechat.53iq.com/", "openid"+ SPUtils.get(AppConstant.USER_ID, ""));//用户标识
//        cookieManager.setCookie("http://wechat.53iq.com/", "openid"+ SPUtils.get(AppConstant.USER_ID, ""));//用户标识
//
//        cookieManager.setCookie("https://wechat.53iq.com/", "partner_id"+"ddb4c038579a11e59e8800a0d1eb6068");
//        cookieManager.setCookie("http://wechat.53iq.com/", "partner_id"+"ddb4c038579a11e59e8800a0d1eb6068");//厂商标识
//
//        cookieManager.setCookie("https://wechat.53iq.com/", "X-source"+"diary");
//        cookieManager.setCookie("http://wechat.53iq.com/", "X-source"+"diary");//从哪个app打开

        if (Build.VERSION.SDK_INT >= 19) {
            setWebContentsDebuggingEnabled(true);
        }
        // 不显示滚动条
        mWebView.setVerticalScrollBarEnabled(false);
        mWebView.setHorizontalScrollBarEnabled(false);




        mWebView.setOnLongClickListener(new View.OnLongClickListener() {//acp长按保存事件
            @Override
            public boolean onLongClick(View v) {
                hitTestResult = mWebView.getHitTestResult();
                // 如果是图片类型或者是带有图片链接的类型
                if (hitTestResult.getType() == com.tencent.smtt.sdk.WebView.HitTestResult.IMAGE_TYPE ||
                        hitTestResult.getType() == com.tencent.smtt.sdk.WebView.HitTestResult.SRC_ANCHOR_TYPE) {
                    // 弹出保存图片的对话框
//                    Log.d("yukaida", "onLongClick: "+hitTestResult.getExtra());
//                    String pic_url1=truncateHeadString(picUrl, 5);
//                    Log.d("yukaida", "onLongClick: "+pic_url1);
//                    showBottomDialog();

                    Bitmap bitmap = null;
                    picUrl = hitTestResult.getExtra();
                    url = picUrl;
                    //todo 这里只能保存静态图片 动图需要前端或者外包方面做出修改才能继续
                    Log.d("save", "onLongClick: "+picUrl);
//                    if (picUrl.length() > 100) {//说明是BASE64加密图片
//                        byte[] imageBytes = Base64.decode(picUrl.split(",")[1], Base64.DEFAULT);
//                        bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
//                        createSavePicDialog(WebActivity.this, bitmap);
//                    } else
                    if (picUrl.contains("https")){//说明是网络图片
//                            Log.d("测试3", "onLongClick: ");
//                            Glide.with(mContext).asBitmap().load(picUrl).into(new SimpleTarget<Bitmap>() {
//                                @Override
//                                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
//                                    createSavePicDialog((Activity) mContext, resource);
//                                }
//                            });

//                        GlideApp.with(WebActivity.this).asBitmap().load(picUrl).submit();

                            createSavePicDialog(WebActivity.this, url);

                    }else {
                        Toast.makeText(WebActivity.this, "暂不支持保存动图", Toast.LENGTH_SHORT).show();
                    }
//                    hitTestResult.getExtra();
//                    Log.d("yukaida", "onLongClick: "+hitTestResult.getExtra());
//                    DialogUtils.createSavePicDialog(getActivity(),);
                    return true;
                }
                return false;
            }
        });


        WebSettings settings = mWebView.getSettings();
        // 允许文件访问
        settings.setAllowFileAccess(true);
        // 支持javaScript
        settings.setJavaScriptEnabled(true);
        settings.setSaveFormData(false);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setAppCacheEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setAppCachePath(this.getFilesDir().getAbsolutePath() + File.separator + "web");
        settings.setDomStorageEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);


        WeakReference<WebView> weakReference = new WeakReference(mWebView);
        mJsApi = new JsApi(weakReference);
        //        js调用android原生方法
        mWebView.addJavascriptInterface(mJsApi,"jsapi");
        settings.setAllowFileAccess(true);
        settings.setAllowFileAccessFromFileURLs(true);
        settings.setAllowUniversalAccessFromFileURLs(true);
        settings.setSupportZoom(false);
        // 允许网页定位
        settings.setGeolocationEnabled(true);
        // 允许保存密码
        settings.setSavePassword(true);

        // 支持播放gif动画
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        // 解决Android 5.0上Webview默认不允许加载Http与Https混合内容
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //两者都可以
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        // 加快HTML网页加载完成的速度，等页面finish再加载图片
        if (Build.VERSION.SDK_INT >= 19) {
            settings.setLoadsImagesAutomatically(true);
        } else {
            settings.setLoadsImagesAutomatically(false);
        }
        ZoomMediaLoader.getInstance().init(new ImageLoader());//大图预览支持
    }


    private void createSavePicDialog(final Activity activity, String  url) {

        popupWindow = new PopupWindow(ScreenSizeUtils.getScreenWidth(activity) - 40, ScreenSizeUtils.getScreenHeight(activity) / 4);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        View view = View.inflate(activity, R.layout.dialog_savepicture_layout, null);
        popupWindow.setContentView(view);
        popupWindow.showAtLocation(activity.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
        TextView save = (TextView) view.findViewById(R.id.id_dialog_save_pic);
//        final TextView share = (TextView) view.findViewById(R.id.id_dialog_share_pic);
        TextView cancel = (TextView) view.findViewById(R.id.id_dialog_cancel);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                SavePictureUtils.savePicture(activity, url);

                Log.d("baochun", "onClick: "+url);


                Glide.with(activity)
                                       .asBitmap()
                                   .load(url)
                                .into(new SimpleTarget<Bitmap>() {
                                @Override
                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                    ToastCustom.makeText(activity, R.string.set_save_success, Toast.LENGTH_SHORT).show();
                                    SavePictureUtils.savePicture(activity,resource);
                                             }
                                     });


                if (popupWindow.isShowing()) {
                    popupWindow.dismiss();
                    popupWindow = null;
                }
            }
        });
//        share.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (popupWindow.isShowing()) {
//                    popupWindow.dismiss();
//                    popupWindow = null;
//                }
//            }
//        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow.isShowing()) {
                    popupWindow.dismiss();
                    popupWindow = null;
                }
            }
        });
    }


    public static String truncateHeadString(String origin, int count) {
        if (origin == null || origin.length() < count) {
            return null;
        }
        char[] arr = origin.toCharArray();
        char[] ret = new char[arr.length - count];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = arr[i + count];
        }
        return String.copyValueOf(ret);
    }

//    private void showBottomDialog() {
//        //1、使用Dialog、设置style
//        final Dialog dialog = new Dialog(getContext());
//        //2、设置布局
//        View view = View.inflate(getContext(), R.layout.dialog_custom_layout, null);
//        dialog.setContentView(view);
//
//        Window window = dialog.getWindow();
//        //设置弹出位置
//        window.setGravity(Gravity.BOTTOM);
//        //设置弹出动画
////        window.setWindowAnimations(R.style.main_menu_animStyle);
//        //设置对话框大小
//        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        dialog.show();
//
//        dialog.findViewById(R.id.tv_take_photo).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                picUrl = hitTestResult.getExtra();//获取图片链接
//                final String pic_url=truncateHeadString(picUrl, 5);
//                Log.d("yukaida", "onClick: "+picUrl);
////                            保存图片到相册
//                new Thread(new Runnable() {
//                    @RequiresApi(api = Build.VERSION_CODES.FROYO)
//                    @Override
//                    public void run() {
//                        urlToBitMap(picUrl);
//                    }
//                }).start();
//                dialog.dismiss();
//            }
//        });
//
//        dialog.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.dismiss();
//
//            }
//        });
//
//    }


    @Override
    public void hasPermission(List<String> granted, boolean isAll) {

    }

    @Override
    public void noPermission(List<String> denied, boolean quick) {
        if (quick) {
            ToastUtils.show("没有权限访问文件，请手动授予权限");
            XXPermissions.gotoPermissionSettings(WebActivity.this, true);
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

    private void requestFilePermission() {
        XXPermissions.with(WebActivity.this).permission(Permission.WRITE_EXTERNAL_STORAGE).request(this);

    }

    @Override
    protected void initData() {

//
//        CookieManager cookieManager = CookieManager.getInstance();
//
//        cookieManager.setCookie("https://wechat.53iq.com/", "openid"+ SPUtils.get(AppConstant.USER_ID, ""));//用户标识
//        cookieManager.setCookie("http://wechat.53iq.com/", "openid"+ SPUtils.get(AppConstant.USER_ID, ""));//用户标识
//
//        cookieManager.setCookie("https://wechat.53iq.com/", "partner_id"+"ddb4c038579a11e59e8800a0d1eb6068");
//        cookieManager.setCookie("http://wechat.53iq.com/", "partner_id"+"ddb4c038579a11e59e8800a0d1eb6068");//厂商标识
//
//        cookieManager.setCookie("https://wechat.53iq.com/", "X-source"+"diary");
//        cookieManager.setCookie("http://wechat.53iq.com/", "X-source"+"diary");//从哪个app打开

        mWebView.setWebViewClient(new MyWebViewClient());
        mWebView.setWebChromeClient(new MyWebChromeClient());
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        isAppendId = intent.getBooleanExtra("isAppendId",false);
        LogUtils.d("url====初始加载" + url);
        if (!TextUtils.isEmpty(url)) {
            mWebView.loadUrl(url);
        }

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
            // 后退网页并且拦截该事件
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        WebViewLifecycleUtils.onResume(mWebView);
        super.onResume();
    }

    @Override
    protected void onPause() {
        WebViewLifecycleUtils.onPause(mWebView);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        WebViewLifecycleUtils.onDestroy(mWebView);
        super.onDestroy();
    }

    private class MyWebViewClient extends WebViewClient {

        // 网页加载错误时回调，这个方法会在onPageFinished之前调用
        @Override
        public void onReceivedError(WebView view, int errorCode, String description, final String failingUrl) {

        }

        // 开始加载网页
        @Override
        public void onPageStarted(final WebView view, final String url, Bitmap favicon) {
            mProgressBar.setVisibility(View.VISIBLE);
        }

        // 完成加载网页
        @Override
        public void onPageFinished(WebView view, String url) {
            mProgressBar.setVisibility(View.GONE);
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            //super.onReceivedSslError(view, handler, error);注意一定要去除这行代码，否则设置无效。
            // handler.cancel();// Android默认的处理方式
            handler.proceed();// 接受所有网站的证书
            // handleMessage(Message msg);// 进行其他处理
        }

        // 跳转到其他链接
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, final String url) {
            LogUtils.d("url===跳转加载" + url);
            String scheme = Uri.parse(url).getScheme();
            if (scheme != null) {
                scheme = scheme.toLowerCase();
            }

            if ("http".equalsIgnoreCase(scheme) || "https".equalsIgnoreCase(scheme)) {
//                if (!url.contains("&openid=")) {
//                    if (!url.contains("?openid=")) {
//
//
//                        if (url.contains("following") || url.contains("follower")) {
//                            mWebView.loadUrl(url);
//                        }else {
//
//                            if (url.contains("point_goods")) {
//                                mWebView.loadUrl(url + "&openid=" + HomeActivity.getOpenId());
//                                Log.d("jump 兑换商品", "shouldOverrideUrlLoading: "+url + "&openid=" + HomeActivity.getOpenId());
//                            } else {
//
//                                if (url.contains("detail")) {
//                                    mWebView.loadUrl(url + "&openid=" + HomeActivity.getOpenId());
//                                    Log.d("jump 用户日记详情", "shouldOverrideUrlLoading: "+url + "?openid=" + HomeActivity.getOpenId());
//                                } else {
//                                    mWebView.loadUrl(url + "?openid=" + HomeActivity.getOpenId() + "&my_openid=" + HomeActivity.getOpenId());
//                                    Log.d("jump 1", "shouldOverrideUrlLoading: ");
//                                }
//                            }
//                        }
//
//
//                    } else {
//                        mWebView.loadUrl(url  + "&my_openid=" + HomeActivity.getOpenId());
//                        Log.d("jump2", "shouldOverrideUrlLoading: ");
//                    }
//                } else {

                CookieManager cookieManager = CookieManager.getInstance();

                cookieManager.setCookie("https://wechat.53iq.com/", "openid"+ SPUtils.get(AppConstant.USER_ID, ""));//用户标识
                cookieManager.setCookie("http://wechat.53iq.com/", "openid"+ SPUtils.get(AppConstant.USER_ID, ""));//用户标识

                cookieManager.setCookie("https://wechat.53iq.com/", "partner_id"+"ddb4c038579a11e59e8800a0d1eb6068");
                cookieManager.setCookie("http://wechat.53iq.com/", "partner_id"+"ddb4c038579a11e59e8800a0d1eb6068");//厂商标识

                cookieManager.setCookie("https://wechat.53iq.com/", "X-source"+"diary");
                cookieManager.setCookie("http://wechat.53iq.com/", "X-source"+"diary");//从哪个app打开


                Log.d("url====", "shouldOverrideUrlLoading: "+url);

                if(url.contains("kitchen/publish/cookbook")){
                    Intent intent = new Intent(WebActivity.this, SendRepiceActivity.class);
                    startActivity(intent);

                }else{
                    mWebView.loadUrl(url);}
//                }
            }
            // 已经处理该链接请求
            return true;
        }
    }

    private class MyWebChromeClient extends WebChromeClient {

        // 收到网页标题
        @Override
        public void onReceivedTitle(WebView view, String title) {
            if (title != null) {
                setTitle(title);
            }
        }

        // 收到加载进度变化
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            mProgressBar.setProgress(newProgress);
        }
    }

    @Override
    public boolean isStatusBarEnabled() {
        return super.isStatusBarEnabled();
    }

    @Override
    public boolean statusBarDarkFont() {
        return super.statusBarDarkFont();
    }

    public OnJsOpen getOnJsOpen() {
        return onJsOpen;
    }
    public void setOnJsOpen(OnJsOpen onJsOpen) {
        this.onJsOpen = onJsOpen;
    }
    public String getCurrentUrl() {
        return currentUrl;
    }

    public void setCurrentUrl(String currentUrl) {
        this.currentUrl = currentUrl;
    }
    public JsApi getJsApi() {
        return mJsApi;
    }


}