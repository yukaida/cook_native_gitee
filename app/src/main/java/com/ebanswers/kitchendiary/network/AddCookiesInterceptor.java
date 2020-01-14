package com.ebanswers.kitchendiary.network;

import android.content.Context;

import com.ebanswers.kitchendiary.common.CommonApplication;
import com.ebanswers.kitchendiary.utils.SPUtils;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AddCookiesInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request.Builder builder = chain.request().newBuilder();
        SPUtils.get("cookie", null);
        HashSet<String> perferences = (HashSet) CommonApplication.getInstance().getSharedPreferences("cookieData", Context.MODE_PRIVATE).getStringSet("cookie", null);



        if (perferences != null) {
            for (String cookie : perferences) {
                builder.addHeader("Cookie", cookie);
            }
        }
        return chain.proceed(builder.build());
    }
}