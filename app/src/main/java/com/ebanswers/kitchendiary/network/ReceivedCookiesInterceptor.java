package com.ebanswers.kitchendiary.network;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.ebanswers.kitchendiary.common.CommonApplication;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Response;

public class ReceivedCookiesInterceptor implements Interceptor {

    public ReceivedCookiesInterceptor() {
        super();
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());
        //这里获取请求返回的cookie
        if (!originalResponse.headers("Set-Cookie").isEmpty()) {

            HashSet<String> cookies = new HashSet<>();
            for (String header : originalResponse.headers("Set-Cookie")) {
                Log.i("qqqq", "拦截的cookie是：" + header);
                cookies.add(header);
            }
            //保存的sharepreference文件名为cookieData
            SharedPreferences sharedPreferences = CommonApplication.getInstance().getSharedPreferences("cookieData", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putStringSet("cookie", cookies);

            editor.commit();
        }

        return originalResponse;
    }
}