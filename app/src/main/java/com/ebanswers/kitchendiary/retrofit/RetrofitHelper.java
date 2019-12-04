package com.ebanswers.kitchendiary.retrofit;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * @author Created by lishihui on 2017/1/9.
 */

public class RetrofitHelper {
    private static final String BASE_URL = "http://wechat.53iq.com/tmp/";
    private Retrofit retrofit;

    public static RetrofitHelper getInstance() {
        return RetrofitSingleton.singleton;
    }

    private static class RetrofitSingleton {
        private static RetrofitHelper singleton = new RetrofitHelper();
    }

    private RetrofitHelper() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }

    public RetrofitService getService() {
        return retrofit.create(RetrofitService.class);
    }
}
