package com.ebanswers.kitchendiary.js;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;

import java.lang.ref.WeakReference;

public class JsApi extends Object {
    private WeakReference<WebView> webView;


    public JsApi(final WeakReference<WebView> weakReference) {
        this.webView = weakReference;
    }

    // 定义JS需要调用的方法
    // 被JS调用的方法必须加入@JavascriptInterface注解
    @JavascriptInterface
    public void showLogin() {

//            if (webView != null && webView.get() != null) {
//                Context context = webView.get().getContext();
//                Intent intent = new Intent(context, PublishActvity.class);
//                context.startActivity(intent);
//            }
//            Log.d("jsmethod", "showLogin: ");
    }
}
