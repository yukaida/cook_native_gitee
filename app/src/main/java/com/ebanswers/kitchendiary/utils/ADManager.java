package com.ebanswers.kitchendiary.utils;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;

import com.ebanswers.kitchendiary.common.CommonApplication;
import com.ebanswers.kitchendiary.config.Constans;
import com.ebanswers.kitchendiary.database.bean.AD;
import com.ebanswers.kitchendiary.database.bean.DBManager;
import com.ebanswers.kitchendiary.retrofit.RetrofitTask;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @author caixd
 * @date 2019/9/29
 * PS:
 */
public class ADManager {

    private MyTarget target;
    /**
     * 有效广告列表
     */
    private List<AD> validAds;
    private long curTime;

    private ADManager() {
        validAds = new ArrayList<>();
    }

    private static class Holder {
        private static ADManager INSTANCE = new ADManager();
    }

    public static ADManager getInstance() {
        return Holder.INSTANCE;
    }

    public void downloadAds() {
        RetrofitTask.getAllAds(Constans.URL_AD_SERVER, new RetrofitTask.CallBack<List<AD>>() {
            @Override
            public void result(List<AD> data) {
                Log.d("ADManager", "result: " + data);
                if (data != null && data.size() > 0) {
                    curTime = System.currentTimeMillis();
                    for (AD ad : data) {
                        if (curTime >= date2ms(ad.getStart_data()) && curTime <= date2ms(ad.getEnd_data())) {
                            DBManager.getInstance().addAds(ad);
                            downImage(ad.getImage());
                        }
                    }
                }
            }

            @Override
            public void onError() {
                Log.d("ADManager", "onError: ");
            }
        });
    }


    private void downImage(String imageUrl) {
        final String imageName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
        File file = getLocalFileByName(imageName);
        if (file != null && file.exists()) {
            return;
        }
        target = new MyTarget(imageName);
        Picasso.with(CommonApplication.getInstance()).load(imageUrl).into(target);
    }

    public AD getLocalValidAd() {
        List<AD> ads = DBManager.getInstance().getAllAds();
        if (ads == null || ads.size() == 0) {
            return null;
        }
        String url, name;
        curTime = System.currentTimeMillis();
        for (AD ad : ads) {
            if (curTime >= date2ms(ad.getStart_data()) && curTime <= date2ms(ad.getEnd_data())) {
                url = ad.getImage();
                name = url.substring(url.lastIndexOf("/") + 1);
                File file = new File(CommonApplication.getInstance().getCacheDir(), name);
                if (file.exists()) {
                    validAds.add(ad);
                }
            }
        }
        Log.d("ADManager", "getLocalValidAd: "+validAds);
        if(validAds.size()>0){
            return validAds.get(new Random().nextInt(validAds.size()));
        }
        return null;
    }

    public File getLocalAdImage(AD ad) {
        String name;
        if (ad != null) {
            name = ad.getImage().substring(ad.getImage().lastIndexOf("/") + 1);
            File file = new File(CommonApplication.getInstance().getCacheDir(), name);
            if (file.exists()) {
                return file;
            }
        }
        return null;
    }

    public File getLocalFileByName(String name) {
        if (!TextUtils.isEmpty(name)) {
            File file = new File(CommonApplication.getInstance().getCacheDir(), name);
            if (file.exists()) {
                return file;
            }
        }
        return null;
    }



    public static long date2ms(String data) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = format.parse(data);
            return date.getTime();
        } catch (Exception e) {
            return 0;
        }
    }

    //使用匿名内部类不执行回调
    private class MyTarget implements Target {
        private String imageName;

        public MyTarget(String imageName) {
            this.imageName = imageName;
        }

        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom loadedFrom) {
            Log.d("ADManager", "onBitmapLoaded: startdown" + Thread.currentThread().getName());
            SavePictureUtils.savePicture(CommonApplication.getInstance(), SavePictureUtils.TYPE_CACHE, bitmap, imageName);
        }

        @Override
        public void onBitmapFailed(Drawable drawable) {

        }

        @Override
        public void onPrepareLoad(Drawable drawable) {

        }
    }

}
