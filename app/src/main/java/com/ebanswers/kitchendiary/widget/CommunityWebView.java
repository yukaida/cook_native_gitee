package com.ebanswers.kitchendiary.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.ebanswers.kitchendiary.R;
import com.ebanswers.kitchendiary.config.Constans;
import com.ebanswers.kitchendiary.mvp.openjs.OnJsCallBack;
import com.ebanswers.kitchendiary.mvp.openjs.OnJsOpen;
import com.ebanswers.kitchendiary.mvp.openjs.OnJsOpenNewPage;
import com.ebanswers.kitchendiary.network.PermissionUtil;
import com.ebanswers.kitchendiary.utils.LanguageUtil;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;

import org.simple.eventbus.EventBus;

import java.lang.ref.WeakReference;

/**
 * @author Created by lishihui on 2017/1/9.
 */

public class CommunityWebView extends WebView {
    private OnJsOpen onJsOpen;
    private OnJsOpenNewPage onJsOpenNewPage;
    private ProgressChanged progressChanged;
    private LoadState loadState;
    private String currentUrl, firstUrl;
    private SwipeRefreshLayout swipeRefreshLayout;
    private static final int FILE_SELECT_CODE = 0;
    private ValueCallback<Uri> mUploadMessage;//回调图片选择，4.4以下
    private ValueCallback<Uri[]> mUploadCallbackAboveL;//回调图片选择，5.0以上
    private OnJsCallBack onJsCallBack;
    private OnTitleShow onTitleShow;
    private boolean isShowProgressBar = true;
    private boolean isShow;

    public CommunityWebView(Context context) {
        super(context);
        initAttribute(context);
    }

    public CommunityWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttribute(context);
    }

    public CommunityWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttribute(context);
    }

    private void initAttribute(Context context) {
        //支持web端调试
        if (Build.VERSION.SDK_INT >= 19) {
            setWebContentsDebuggingEnabled(true);
        }
        WebSettings settings = getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setSaveFormData(false);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setAppCacheEnabled(true);
        settings.setAppCachePath(context.getCacheDir().getAbsolutePath());
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setLoadsImagesAutomatically(true);
        settings.setDomStorageEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        if (Build.VERSION.SDK_INT >= 11) {
            settings.setDisplayZoomControls(false);
        }
        //运行webview通过URI获取安卓文件
        settings.setAllowFileAccess(true);
        settings.setAllowFileAccessFromFileURLs(true);
        settings.setAllowUniversalAccessFromFileURLs(true);
        if (getX5WebViewExtension() != null)
            getX5WebViewExtension().setScrollBarFadingEnabled(false);
        //允许混合加载
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(0);
        }
        settings.setSupportZoom(false);
        WeakReference<CommunityWebView> weakReference = new WeakReference(this);
//        addJavascriptInterface(mJsApi, "jsapi");
//        setWebViewClient(new DsViewClient() {
//            @Override
//            public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                super.onPageStarted(view, url, favicon);
//                Log.d("CommunityWebView", "onPageStarted " + url);
//                setCurrentUrl(url);
//                if (loadState != null) {
//                    loadState.onStart((CommunityWebView) view);
//                }
//            }
//
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                super.onPageFinished(view, url);
//                Log.d("CommunityWebView", "onPageFinished 执行");
//                if (loadState != null) {
//                    loadState.onfinish((CommunityWebView) view);
//                }
//            }
//
//        });
        setOnJsOpen(new OnJsOpen() {
            @Override
            public void open(int index) {
                EventBus.getDefault().post(index, "changeFragment");
            }
        });
        setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (progressChanged != null) {
                    progressChanged.onProgressChanged((CommunityWebView) view, newProgress);
                }
            }

            //>Android4.1
            @Override
            public void openFileChooser(ValueCallback<Uri> valueCallback, String s, String s1) {
                Log.d("CommunityWebView", "openFileChooser: 3.0");
                if(PermissionUtil.checkStoragePermission()) {
                    if (getContext() instanceof Activity) {
                        setUploadMessage(valueCallback);
                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                        intent.addCategory(Intent.CATEGORY_OPENABLE);
                        intent.setType("image/*");
                        ((Activity) getContext()).startActivityForResult(Intent.createChooser(intent, LanguageUtil.getInstance().getStringById(R.string.web_file_chooser)), Constans.FILE_SELECT_CODE);
                    }
                }else{
//                    DialogUtils.createPermissionDialog(getContext(), Constans.TYPE_PERMISSION_STORAGE);
                }
            }

            //>Android5.0
            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> valueCallback, FileChooserParams fileChooserParams) {
                Log.d("CommunityWebView", "onShowFileChooser: "+PermissionUtil.checkStoragePermission());
                if(PermissionUtil.checkStoragePermission()) {
                    if (getContext() instanceof Activity) {
                        setUploadCallbackAboveL(valueCallback);
                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                        intent.addCategory(Intent.CATEGORY_OPENABLE);
                        intent.setType("image/*");
                        ((Activity) getContext()).startActivityForResult(Intent.createChooser(intent, LanguageUtil.getInstance().getStringById(R.string.web_file_chooser)), Constans.FILE_SELECT_CODE);
                    }
                }else{
//                    DialogUtils.createPermissionDialog(getContext(), Constans.TYPE_PERMISSION_STORAGE);
//                    if(valueCallback!=null){
//                        valueCallback.onReceiveValue(null);
//                    }
                }
                return true;
            }

            @Override
            public void onReceivedTitle(WebView webView, String s) {
                super.onReceivedTitle(webView, s);
                if (onTitleShow != null)
                    onTitleShow.onTitle(s);
            }

        });
    }


    public void setOnProgressChanged(ProgressChanged progressChanged) {
        this.progressChanged = progressChanged;
    }


    public void setIsShowProgressBar(boolean isShow) {
        this.isShowProgressBar = isShow;
    }

    public boolean getIsShowProgressBar() {
        return isShowProgressBar;
    }

    public ValueCallback<Uri> getUploadMessage() {
        return mUploadMessage;
    }

    public ValueCallback<Uri[]> getUploadCallbackAboveL() {
        return mUploadCallbackAboveL;
    }

    public void setUploadCallbackAboveL(ValueCallback<Uri[]> uploadCallbackAboveL) {
        mUploadCallbackAboveL = uploadCallbackAboveL;
    }

    public void setUploadMessage(ValueCallback<Uri> uploadMessage) {
        mUploadMessage = uploadMessage;
    }

    public void setLoadState(LoadState loadState) {
        this.loadState = loadState;
    }

    public OnJsOpen getOnJsOpen() {
        return onJsOpen;
    }

    public void setOnJsOpen(OnJsOpen onJsOpen) {
        this.onJsOpen = onJsOpen;
    }

    public OnJsOpenNewPage getOnJsOpenNewPage() {
        return onJsOpenNewPage;
    }

    public void setOnJsOpenNewPage(OnJsOpenNewPage onJsOpenNewPage) {
        this.onJsOpenNewPage = onJsOpenNewPage;
    }

    public void setOnJsCallBack(OnJsCallBack callBack) {
        this.onJsCallBack = callBack;
    }

    public OnJsCallBack getOnJsCallBack() {
        return onJsCallBack;
    }

    public String getCurrentUrl() {
        return currentUrl;
    }

    public void setCurrentUrl(String currentUrl) {
        this.currentUrl = currentUrl;
    }

    public void setFirstUrl(String firstUrl) {
        this.firstUrl = firstUrl;
    }

    public String getFirstUrl() {
        return firstUrl;
    }

    @Override
    protected void onDetachedFromWindow() {
        if (getHandler() != null) {
            getHandler().removeCallbacksAndMessages(null);
        }
        super.onDetachedFromWindow();
    }


    public void setRefreshEnable(boolean enable) {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setEnabled(enable);
        }
    }

    public boolean getIsShow() {
        return isShow;
    }

    public void setIsShow(boolean show) {
        isShow = show;
    }


    public void setSwipeRefreshLayout(SwipeRefreshLayout swipeRefreshLayout) {
        this.swipeRefreshLayout = swipeRefreshLayout;
    }

    public void setTitleShowListener(OnTitleShow onTitleShow) {
        this.onTitleShow = onTitleShow;
    }

    public interface ProgressChanged {
        void onProgressChanged(CommunityWebView view, int newProgress);
    }


    public interface OnTitleShow {
        void onTitle(String title);
    }

    public interface LoadState {
        void onStart(CommunityWebView view);

        void onfinish(CommunityWebView view);
    }

}
