package com.ebanswers.kitchendiary.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.ebanswers.kitchendiary.bean.DiaryPicinfo;
import com.ebanswers.kitchendiary.bean.DiaryServiceNeed.ContentBean;
import com.ebanswers.kitchendiary.bean.DiaryServiceNeed.PiclistBean;
import com.ebanswers.kitchendiary.bean.PictureBean;
import com.ebanswers.kitchendiary.bean.PostDiaryBack;
import com.ebanswers.kitchendiary.eventbus.Event;
import com.ebanswers.kitchendiary.eventbus.EventBusUtil;
import com.ebanswers.kitchendiary.network.api.ApiMethods;
import com.ebanswers.kitchendiary.network.observer.MyObserver;
import com.ebanswers.kitchendiary.network.observer.ObserverOnNextListener;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

import static com.umeng.socialize.utils.DeviceConfigInternal.context;

/**
 * @author yukaida 2019.12.5
 *
 * */
public class CreateDiaryService extends Service {
    private List<DiaryPicinfo> image_list = new ArrayList<>();
    private HashMap<String, String> map = new HashMap<>();
    private CopyOnWriteArrayList<PictureBean> list_yun_pictures_path;
    private static final String TAG = "CreateDiaryService";
    public CreateDiaryService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        list_yun_pictures_path = new CopyOnWriteArrayList<>();
        String ContentBean_str = intent.getStringExtra("ContentBean");
        ContentBean contentBean = new Gson().fromJson(ContentBean_str, ContentBean.class);
        map = contentBean.getMap();
        String PiclistBean_str = intent.getStringExtra("PiclistBean");
        PiclistBean piclistBean = new Gson().fromJson(PiclistBean_str, PiclistBean.class);
        image_list = piclistBean.getList();
        send();
        return super.onStartCommand(intent, flags, startId);
    }

    private void send() {
        if (image_list.size() > 0) {

            Log.d(TAG, "onViewClicked:   发送有图");
            for (int i = 0; i < image_list.size(); i++) {
                String path = image_list.get(i).getImagePath();
                File file = new File(path);
                postDiaryWithPic(i, file, image_list.size(), map);
//                        postDiaryNoPic(map);
            }
        } else {
            postDiaryNoPic(map);
        }
    }
    public void postDiaryNoPic(HashMap<String, String> map) {//发布日记-----------------------
        ObserverOnNextListener<PostDiaryBack, Throwable> listener = new ObserverOnNextListener<PostDiaryBack, Throwable>() {
            @Override
            public void onNext(PostDiaryBack postDiaryBack) {
                Toast.makeText(CreateDiaryService.this, "发布成功", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onNext: 发布成功");
                EventBusUtil.sendEvent(new Event(Event.EVENT_SEND_SUCCESS,"发布成功"));
                stopSelf();
            }

            @Override
            public void onError(Throwable throwable) {
                Toast.makeText(context, "后台网络异常", Toast.LENGTH_SHORT).show();
            }
        };

        ApiMethods.postDiary(new MyObserver<PostDiaryBack>(CreateDiaryService.this, listener), map);
    }

    public void postDiaryNoPic2(HashMap<String, String> map) {//发布日记-----------------------
        ObserverOnNextListener<PostDiaryBack, Throwable> listener = new ObserverOnNextListener<PostDiaryBack, Throwable>() {
            @Override
            public void onNext(PostDiaryBack postDiaryBack) {
                Toast.makeText(CreateDiaryService.this, "发布成功", Toast.LENGTH_SHORT).show();
                list_yun_pictures_path.clear();

                EventBusUtil.sendEvent(new Event(Event.EVENT_SEND_SUCCESS,"发布成功"));
                stopSelf();
            }

            @Override
            public void onError(Throwable throwable) {
                Toast.makeText(context, "后台网络异常", Toast.LENGTH_SHORT).show();
            }
        };


        if (list_yun_pictures_path.size() > 0) {
            Gson gson = new Gson();
            PictureBean[] pictureBeans = new PictureBean[list_yun_pictures_path.size()];
            list_yun_pictures_path.toArray(pictureBeans);
            bubbleSort(pictureBeans);
            String[] pictures = new String[pictureBeans.length];
            for (int i = 0; i < pictures.length; i++) {
                pictures[i] = pictureBeans[i].getUrl();
            }
            String s = gson.toJson(pictures);
            map.put("imgs", s);
        }

        ApiMethods.postDiary(new MyObserver<PostDiaryBack>(CreateDiaryService.this, listener), map);
    }


    public static void bubbleSort(PictureBean[] numbers) {
        PictureBean temp; // 记录临时中间值
        int size = numbers.length; // 数组大小
        for (int i = 0; i < size - 1; i++) {
            for (int j = i + 1; j < size; j++) {
                if (numbers[i].getIndex() > numbers[j].getIndex()) { // 交换两数的位置
                    temp = numbers[i];
                    numbers[i] = numbers[j];
                    numbers[j] = temp;
                }
            }
        }
    }

    public void postDiaryWithPic(int index, File file, int size, HashMap<String, String> map) {//发送日记图片-----------------------
        ObserverOnNextListener<ResponseBody, Throwable> listener = new ObserverOnNextListener<ResponseBody, Throwable>() {
            @Override
            public void onNext(ResponseBody responseBody) {//从网络上获取的草稿箱信息
                //发布成功后跳转到发现页并刷新
                Log.d(TAG, "onNext:  发布成功 有图");

                try {
                    if (responseBody != null) {
                        String str = new String(responseBody.bytes());
                        JSONObject jsonObject = new JSONObject(str);
                        String pic_url = jsonObject.getJSONObject("data").getString("img_url");
                        PictureBean pictureBean = new PictureBean(index, pic_url);
                        list_yun_pictures_path.add(pictureBean);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (list_yun_pictures_path.size() == size) {
                    postDiaryNoPic2(map);
                }


            }

            @Override
            public void onError(Throwable throwable) {
                Toast.makeText(context, "后台网络异常", Toast.LENGTH_SHORT).show();
            }
        };

        RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart("access_token", "wl8f8rymKhkeUOiMiHJS1htxzFvIrrbyt6XovGqiuxFzYsquytbXDw8jA21NHgZP_36f2bc382452bf3c23cee4dd2874d41a9ca904cd").addFormDataPart("image", file.getName(), RequestBody.create(MediaType.parse("image/*"), file)).build();
        ApiMethods.uploadImg_diary(new MyObserver<ResponseBody>(CreateDiaryService.this, listener), requestBody);
    }


}
