package com.ebanswers.kitchendiary.bean;

import android.content.Context;
import android.graphics.Bitmap;

import com.umeng.socialize.media.UMImage;

import java.io.File;

/**
 * @author
 * Created by lishihui on 2017/3/13.
 */

public class ShareImage extends UMImage {
    private File imgFile;
    private String imgUrl;
    private Bitmap imgBitmap;

    public ShareImage(Context context, File file) {
        super(context, file);
        imgFile = file;
    }

    public ShareImage(Context context,String s){
        super(context,s);
        imgUrl = s;
    }

    public ShareImage(Context context,Bitmap bitmap){
        super(context,bitmap);
        imgBitmap = bitmap;
    }

    public File getImgFile() {
        return imgFile;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public Bitmap getImgBitmap() {
        return imgBitmap;
    }
}
