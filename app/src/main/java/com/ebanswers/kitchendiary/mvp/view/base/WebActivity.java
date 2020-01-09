package com.ebanswers.kitchendiary.mvp.view.base;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ebanswers.baselibrary.utils.WebViewLifecycleUtils;
import com.ebanswers.kitchendiary.R;
import com.ebanswers.kitchendiary.common.CommonActivity;
import com.ebanswers.kitchendiary.constant.AppConstant;
import com.ebanswers.kitchendiary.mvp.openjs.JsApi;
import com.ebanswers.kitchendiary.mvp.openjs.OnJsOpen;
import com.ebanswers.kitchendiary.utils.ImageLoader;
import com.ebanswers.kitchendiary.utils.LogUtils;
import com.ebanswers.kitchendiary.utils.SPUtils;
import com.previewlibrary.ZoomMediaLoader;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.io.File;
import java.lang.ref.WeakReference;

import butterknife.BindView;

import static android.webkit.WebView.setWebContentsDebuggingEnabled;

/*
 *    desc   : 浏览器界面
 */
public class WebActivity extends CommonActivity {
    private OnJsOpen onJsOpen;
    @BindView(R.id.pb_web_progress)
    ProgressBar mProgressBar;
    @BindView(R.id.wv_web_view)
    WebView mWebView;
    private JsApi mJsApi;
    private String currentUrl;
    private boolean isAppendId;///  是否拼接id

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

        cookieManager.setCookie("https://wechat.53iq.com/", "openid"+ SPUtils.get(AppConstant.USER_ID, ""));//用户标识
        cookieManager.setCookie("http://wechat.53iq.com/", "openid"+ SPUtils.get(AppConstant.USER_ID, ""));//用户标识

        cookieManager.setCookie("https://wechat.53iq.com/", "partner_id"+"ddb4c038579a11e59e8800a0d1eb6068");
        cookieManager.setCookie("http://wechat.53iq.com/", "partner_id"+"ddb4c038579a11e59e8800a0d1eb6068");//厂商标识

        cookieManager.setCookie("https://wechat.53iq.com/", "X-source"+"diary");
        cookieManager.setCookie("http://wechat.53iq.com/", "X-source"+"diary");//从哪个app打开

        if (Build.VERSION.SDK_INT >= 19) {
            setWebContentsDebuggingEnabled(true);
        }
        // 不显示滚动条
        mWebView.setVerticalScrollBarEnabled(false);
        mWebView.setHorizontalScrollBarEnabled(false);

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

    @Override
    protected void initData() {


        CookieManager cookieManager = CookieManager.getInstance();

        cookieManager.setCookie("https://wechat.53iq.com/", "openid"+ SPUtils.get(AppConstant.USER_ID, ""));//用户标识
        cookieManager.setCookie("http://wechat.53iq.com/", "openid"+ SPUtils.get(AppConstant.USER_ID, ""));//用户标识

        cookieManager.setCookie("https://wechat.53iq.com/", "partner_id"+"ddb4c038579a11e59e8800a0d1eb6068");
        cookieManager.setCookie("http://wechat.53iq.com/", "partner_id"+"ddb4c038579a11e59e8800a0d1eb6068");//厂商标识

        cookieManager.setCookie("https://wechat.53iq.com/", "X-source"+"diary");
        cookieManager.setCookie("http://wechat.53iq.com/", "X-source"+"diary");//从哪个app打开

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
                    mWebView.loadUrl(url);

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