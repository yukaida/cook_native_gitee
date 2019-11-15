package com.ebanswers.kitchendiary.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Nullable;

import com.ebanswers.kitchendiary.bean.AllMsgFound;
import com.ebanswers.kitchendiary.bean.FoodStepinfo;
import com.ebanswers.kitchendiary.bean.Stepinfo;
import com.ebanswers.kitchendiary.constant.AppConstant;
import com.ebanswers.kitchendiary.network.api.ApiMethods;
import com.ebanswers.kitchendiary.network.observer.MyObserver;
import com.ebanswers.kitchendiary.network.observer.ObserverOnNextListener;
import com.ebanswers.kitchendiary.network.response.BaseResponse;
import com.ebanswers.kitchendiary.network.response.ImageResponse;
import com.ebanswers.kitchendiary.receiver.AlarmReceiver;
import com.ebanswers.kitchendiary.utils.SPUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hjq.toast.ToastUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class CreateRepiceService extends Service {


    public static final String TAG = "CreateRepiceService";
    private static boolean control = false;
//    private TokenBean mTokenBean;
    private AlarmManager manager;
    private PendingIntent mPi;
    private int count = 0;
    private int currentcount = 0;
    private boolean success;
    List<FoodStepinfo> foodStepinfos = new ArrayList<>();
    private AllMsgFound allMsgFound;
    private List<Stepinfo> stepinfos;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Log.e(TAG, "onCreate: ");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand: ");
        String repice = (String) SPUtils.get(AppConstant.repice, "");
        String pic = (String) SPUtils.get(AppConstant.pic, "");
        Gson gson = new Gson();
        allMsgFound = gson.fromJson(repice, AllMsgFound.class);
        stepinfos = gson.fromJson(pic,new TypeToken<List<Stepinfo>>(){}.getType());

        success = (boolean) SPUtils.get("success",false);

        String userImage = (String) SPUtils.get(AppConstant.USER_IMAGE, "");
        if (!TextUtils.isEmpty(userImage)){
            File file = new File(userImage);
            RequestBody image = RequestBody.create(MediaType.parse("*"), file);
            RequestBody watermark = RequestBody.create(MediaType.parse("text/plain"), "yes");
            MultipartBody.Part part = MultipartBody.Part.createFormData("image", file.getName(), image);
            uploadHeadImg(part, watermark);
        }else {

        }

        return super.onStartCommand(intent, flags, startId);
    }

    private void uploadHeadImg(MultipartBody.Part part, RequestBody body) {
        ObserverOnNextListener<ImageResponse,Throwable> listener = new ObserverOnNextListener<ImageResponse, Throwable>() {
            @Override
            public void onNext(ImageResponse imageResponse) {
                if (imageResponse != null && imageResponse.getData() != null){

                    ArrayList<String> img_url = new ArrayList<String>();
                    ArrayList<String> thumbnail_url = new ArrayList<String>();
                    if (!TextUtils.isEmpty(imageResponse.getData().getImg_url())){
                        img_url.add(imageResponse.getData().getImg_url());
                    }
                    if (!TextUtils.isEmpty(imageResponse.getData().getThumbnail_url())){
                        thumbnail_url.add(imageResponse.getData().getThumbnail_url());
                    }
                    allMsgFound.setImg_url(img_url);
                    allMsgFound.setThumbnail_url(thumbnail_url);
                    if (stepinfos != null){
                        File file = new File(stepinfos.get(currentcount).getThumbnail());
                        RequestBody image = RequestBody.create(MediaType.parse("*"), file);
                        RequestBody watermark = RequestBody.create(MediaType.parse("text/plain"), "yes");
                        MultipartBody.Part part = MultipartBody.Part.createFormData("image", file.getName(), image);
                        uploadImg(part,watermark);
                    }
                }
            }

            @Override
            public void onError(Throwable throwable) {
                ToastUtils.show("图片上传失败");
            }
        };

        ApiMethods.uploadImg(new MyObserver<ImageResponse>(getApplicationContext(),listener),part,body);
    }

    public void loadCookbook(String action, String allMsgFound){

        ObserverOnNextListener<BaseResponse,Throwable> listener = new ObserverOnNextListener<BaseResponse, Throwable>() {
            @Override
            public void onNext(BaseResponse baseResponse) {
                SPUtils.put("success",true);
                SPUtils.put(AppConstant.repice,"");
                SPUtils.put(AppConstant.pic,"");
                SPUtils.put(AppConstant.USER_IMAGE,"");
                ToastUtils.show("菜谱创建成功");
                stopSelf();
            }

            @Override
            public void onError(Throwable throwable) {
                ToastUtils.show("菜谱创建失败");

            }
        };

        ApiMethods.sendcookbook(new MyObserver<BaseResponse>(getApplicationContext(),listener),allMsgFound,action);
    }

    public void uploadImg(MultipartBody.Part allMsgFound, RequestBody body){
        ObserverOnNextListener<ImageResponse,Throwable> listener = new ObserverOnNextListener<ImageResponse, Throwable>() {
            @Override
            public void onNext(ImageResponse imageResponse) {
                if (imageResponse != null && imageResponse.getData() != null){
                    FoodStepinfo foodStepinfo = new FoodStepinfo();
                    foodStepinfo.setImg(imageResponse.getData().getImg_url());
                    foodStepinfo.setThumbnail(imageResponse.getData().getThumbnail_url());
                    foodStepinfo.setDesc(stepinfos.get(currentcount).getDesc());
                    foodStepinfos.add(foodStepinfo);
                    currentcount ++ ;
                   if (currentcount < stepinfos.size()){
                       File file = new File(stepinfos.get(currentcount).getThumbnail());
                       RequestBody image = RequestBody.create(MediaType.parse("*"), file);
                       RequestBody watermark = RequestBody.create(MediaType.parse("text/plain"), "yes");
                       MultipartBody.Part part = MultipartBody.Part.createFormData("image", file.getName(), image);
                       uploadImg(part,watermark);
                   }else {

                       count ++;
                       if(!success){
                           if (count < 5){
                               new Thread(new Runnable() {
                                   @Override
                                   public void run() {

                                       loadCookbook("cookbook",new Gson().toJson(allMsgFound));
                                   }
                               }).start();

                               manager = (AlarmManager) getSystemService(ALARM_SERVICE);
                               int anHour = 60 * 1000; // 这是8小时的毫秒数
                               long triggerAtTime = SystemClock.elapsedRealtime() + anHour;
                               Intent i = new Intent(getApplicationContext(), AlarmReceiver.class);
                               mPi = PendingIntent.getBroadcast(getApplicationContext(), 0, i, 0);
                               manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, mPi);
                           }else {
                               ToastUtils.show("菜谱创建失败");
                           }

                       }


                   }

                }
            }

            @Override
            public void onError(Throwable throwable) {
                ToastUtils.show("图片上传失败");
            }
        };

        ApiMethods.uploadImg(new MyObserver<ImageResponse>(getApplicationContext(),listener),allMsgFound,body);


    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy: 关闭广播注册者");
        manager.cancel(mPi);//关闭的服务的时候同时关闭广播注册者
        super.onDestroy();
    }

}
