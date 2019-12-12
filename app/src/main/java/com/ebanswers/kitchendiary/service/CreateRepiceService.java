package com.ebanswers.kitchendiary.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Nullable;

import com.ebanswers.kitchendiary.bean.AllMsgFound;
import com.ebanswers.kitchendiary.bean.FoodStepinfo;
import com.ebanswers.kitchendiary.bean.Stepinfo;
import com.ebanswers.kitchendiary.constant.AppConstant;
import com.ebanswers.kitchendiary.eventbus.Event;
import com.ebanswers.kitchendiary.eventbus.EventBusUtil;
import com.ebanswers.kitchendiary.network.api.ApiMethods;
import com.ebanswers.kitchendiary.network.observer.MyObserver;
import com.ebanswers.kitchendiary.network.observer.ObserverOnNextListener;
import com.ebanswers.kitchendiary.network.response.BaseResponse;
import com.ebanswers.kitchendiary.network.response.ImageResponse;
import com.ebanswers.kitchendiary.receiver.AlarmReceiver;
import com.ebanswers.kitchendiary.utils.LogUtils;
import com.ebanswers.kitchendiary.utils.NetworkUtils;
import com.ebanswers.kitchendiary.utils.PollingUtil;
import com.ebanswers.kitchendiary.utils.SPUtils;
import com.ebanswers.kitchendiary.widget.dialog.DialogBackTip;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hjq.toast.ToastUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
    private boolean uploadHeadImage = false;
    private boolean uploadStepImage = false;
    private boolean uploadStep = false;
    private PollingUtil pollingUtil;
    private Runnable runnable;
    private Runnable runnable1;
    private DialogBackTip.Builder builder;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Log.e(TAG, "onCreate: ");
        super.onCreate();
        EventBusUtil.register(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand: ");


        success = (boolean) SPUtils.get("success",false);

        if (success){
            stopSelf();
        }else {

            send();
        }


        return super.onStartCommand(intent, flags, startId);
    }

    private void send() {
        String repice = (String) SPUtils.get(AppConstant.repice, "");
        String pic = (String) SPUtils.get(AppConstant.pic, "");
        Gson gson = new Gson();
        allMsgFound = gson.fromJson(repice, AllMsgFound.class);
        stepinfos = gson.fromJson(pic,new TypeToken<List<Stepinfo>>(){}.getType());
        String userImage = (String) SPUtils.get(AppConstant.USER_IMAGE, "");
        if (!TextUtils.isEmpty(userImage)){
            File file = new File(userImage);
            RequestBody image = RequestBody.create(MediaType.parse("*"), file);
            RequestBody watermark = RequestBody.create(MediaType.parse("text/plain"), "yes");
            MultipartBody.Part part = MultipartBody.Part.createFormData("image", file.getName(), image);
            uploadHeadImg(part, watermark);
        }

        //每3秒打印一次日志
        pollingUtil = new PollingUtil(new Handler(getMainLooper()));
        runnable = new Runnable() {
            @Override
            public void run() {
                if (uploadHeadImage){
                    if (stepinfos != null){
                        if (!uploadStep){
                            uploadStep = true;
                            if (currentcount < stepinfos.size()) {
                                File file = new File(stepinfos.get(currentcount).getThumbnail());
                                RequestBody image = RequestBody.create(MediaType.parse("*"), file);
                                RequestBody watermark = RequestBody.create(MediaType.parse("text/plain"), "yes");
                                MultipartBody.Part part = MultipartBody.Part.createFormData("image", file.getName(), image);
                                uploadImg(part, watermark);
                            }else {
                                uploadStepImage = true;
                                allMsgFound.setSteps(foodStepinfos);
                                loadCookbook("cookbook",new Gson().toJson(allMsgFound));
                            }
                        }
                    }
                }
            }
        };

        pollingUtil.startPolling(runnable, 2000, true);

        runnable1 = new Runnable() {
            @Override
            public void run() {

            }
        };

        pollingUtil.startPolling(runnable1, 10000, true);

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
                    SPUtils.put(AppConstant.repice, new Gson().toJson(allMsgFound));
              /*      Gson gson = new Gson();
                    SPUtils.put(AppConstant.repice, gson.toJson(allMsgFound));
                    SPUtils.put(AppConstant.USER_IMAGE, "");*/
                    uploadHeadImage = true;
                }
            }

            @Override
            public void onError(Throwable throwable) {
                if (NetworkUtils.isNetworkAvailable(getApplicationContext())){
                    EventBusUtil.sendEvent(new Event(Event.EVENT_SEND_FAIL,"发布失败"));
                }else {
                    EventBusUtil.sendEvent(new Event(Event.EVENT_SEND_FAIL,"发布失败"));
                }
            }
        };

        ApiMethods.uploadImg(new MyObserver<ImageResponse>(getApplicationContext(),listener),part,body);
    }

    public void loadCookbook(String action, String allMsgFound){

        ObserverOnNextListener<BaseResponse,Throwable> listener = new ObserverOnNextListener<BaseResponse, Throwable>() {
            @Override
            public void onNext(BaseResponse baseResponse) {
                if (baseResponse.getCode() == 0){
                    SPUtils.put("success",true);
                    SPUtils.put(AppConstant.repice,"");
                    SPUtils.put(AppConstant.pic,"");
                    SPUtils.put(AppConstant.USER_IMAGE,"");
                    ToastUtils.show("菜谱创建成功");
                    if (uploadStepImage){
                        pollingUtil.endPolling(runnable);
                        pollingUtil.endPolling(runnable1);
                    }
                    EventBusUtil.sendEvent(new Event(Event.EVENT_SEND_SUCCESS,"发布成功"));
                    stopSelf();
                }else {
                    count ++;
                    if (count == 6){
//                    ToastUtils.show("菜谱创建失败，重新提交中");
                        EventBusUtil.sendEvent(new Event(Event.EVENT_SEND_FAIL,"发布失败"));
                    }else {
                        manager = (AlarmManager) getSystemService(ALARM_SERVICE);
                        int anHour = 60 * 1000; // 这是8小时的毫秒数
                        long triggerAtTime = SystemClock.elapsedRealtime() + anHour;
                        Intent i = new Intent(getApplicationContext(), AlarmReceiver.class);
                        mPi = PendingIntent.getBroadcast(getApplicationContext(), 0, i, 0);
                        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, mPi);

                    }
                }
            }

            @Override
            public void onError(Throwable throwable) {
                count ++;
                if (NetworkUtils.isNetworkAvailable(getApplicationContext())){
                    if (count == 6){
//                    ToastUtils.show("菜谱创建失败，重新提交中");
                        EventBusUtil.sendEvent(new Event(Event.EVENT_SEND_FAIL,"发布失败"));
                    }else {
                        manager = (AlarmManager) getSystemService(ALARM_SERVICE);
                        int anHour = 60 * 1000; // 这是8小时的毫秒数
                        long triggerAtTime = SystemClock.elapsedRealtime() + anHour;
                        Intent i = new Intent(getApplicationContext(), AlarmReceiver.class);
                        mPi = PendingIntent.getBroadcast(getApplicationContext(), 0, i, 0);
                        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, mPi);

                    }
                }else {
                    EventBusUtil.sendEvent(new Event(Event.EVENT_SEND_FAIL,"发布失败"));
                }


            }
        };

        ApiMethods.sendcookbook(new MyObserver<BaseResponse>(this,listener),allMsgFound,action);
    }

    public void uploadImg(MultipartBody.Part part, RequestBody body){
        ObserverOnNextListener<ImageResponse,Throwable> listener = new ObserverOnNextListener<ImageResponse, Throwable>() {
            @Override
            public void onNext(ImageResponse imageResponse) {
                if (imageResponse != null && imageResponse.getData() != null){
                    FoodStepinfo foodStepinfo = new FoodStepinfo();
                    foodStepinfo.setImg(imageResponse.getData().getImg_url());
                    foodStepinfo.setThumbnail(imageResponse.getData().getThumbnail_url());
                    foodStepinfo.setDesc(stepinfos.get(currentcount).getDesc());
                    foodStepinfos.add(foodStepinfo);
                    allMsgFound.setSteps(foodStepinfos);
                    SPUtils.put(AppConstant.repice, new Gson().toJson(allMsgFound));
                    currentcount ++ ;
                    uploadStep = false;
                }
            }

            @Override
            public void onError(Throwable throwable) {
                if (NetworkUtils.isNetworkAvailable(getApplicationContext())){
                    EventBusUtil.sendEvent(new Event(Event.EVENT_SEND_FAIL,"发布失败"));
                }else {
                    EventBusUtil.sendEvent(new Event(Event.EVENT_SEND_FAIL,"发布失败"));
                }
            }
        };

        ApiMethods.uploadImg(new MyObserver<ImageResponse>(this,listener),part,body);


    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy: 关闭广播注册者");
        if (manager != null) {
            manager.cancel(mPi);//关闭的服务的时候同时关闭广播注册者
        }
        EventBusUtil.unregister(this);
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetMessage(Event message) {
        if (message.getType() == Event.EVENT_NET) {
            if (message.equals("false")){
                EventBusUtil.sendEvent(new Event(Event.EVENT_SEND_FAIL,"发布失败"));
                stopSelf();
                LogUtils.d("网络状态==="+  message.getParam());

            }

        }
    }

}
