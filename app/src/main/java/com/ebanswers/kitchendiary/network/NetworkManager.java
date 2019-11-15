// (c)2016 Flipboard Inc, All Rights Reserved.
package com.ebanswers.kitchendiary.network;

import com.google.gson.Gson;
import com.ebanswers.kitchendiary.network.api.ImageApi;
import com.ebanswers.kitchendiary.network.api.LoginApi;
import com.ebanswers.kitchendiary.network.api.WorkingLogApi;

import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkManager {

//    private static final String baseUrl = Deployment.BASE_URL;
    private static final String baseUrlLogin = Deployment.BASE_URL_LOGIN;
    private static final String baseUrlWork = Deployment.BASE_URL_WORK;


    public static final int CONNECT_TIME_OUT = 30;//连接超时时长x秒
    public static final int READ_TIME_OUT = 30;//读数据超时时长x秒
    public static final int WRITE_TIME_OUT = 30;//写数据接超时时长x秒

    private static LoginApi loginApi;
    private static ImageApi imageApi;
    private static WorkingLogApi workingLogApi;

    public static final MediaType FORM_CONTENT_TYPE
            = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");

    /**
     * 设置okHttp
     *
     * @author ZhongDaFeng
     */
    private static OkHttpClient okHttpClient() {
        //开启Log
        HttpLoggingInterceptor logging =new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
                .addInterceptor(logging)
                .build();
        return client;
    }


    private static Converter.Factory gsonConverterFactory = GsonConverterFactory.create(new Gson());
//    private static Converter.Factory xmlConverterFactory = SimpleXmlConverterFactory.create();
    private static CallAdapter.Factory rxJavaCallAdapterFactory = RxJava2CallAdapterFactory.create();

    public static LoginApi getLoginApi(){
        if (loginApi == null){
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient())
                    .baseUrl(baseUrlLogin)
                    .addConverterFactory(gsonConverterFactory)
//                    .addConverterFactory(xmlConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            loginApi = retrofit.create(LoginApi.class);
        }
        return loginApi;
    }

    public static WorkingLogApi getWorkingLogApi(){
        if (workingLogApi == null){
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient())
                    .baseUrl(baseUrlWork)
                    .addConverterFactory(gsonConverterFactory)
//                    .addConverterFactory(xmlConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            workingLogApi = retrofit.create(WorkingLogApi.class);
        }
        return workingLogApi;
    }


    public static ImageApi getImageApi(){
        if (imageApi == null){
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient())
                    .baseUrl(baseUrlLogin + "acl/")
                    .addConverterFactory(gsonConverterFactory)
//                    .addConverterFactory(xmlConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            imageApi = retrofit.create(ImageApi.class);
        }
        return imageApi;
    }


}
