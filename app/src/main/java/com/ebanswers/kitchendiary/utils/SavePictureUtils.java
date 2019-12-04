package com.ebanswers.kitchendiary.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author Created by lishihui on 2017/1/23.
 */

public class SavePictureUtils {
    public static final int TYPE_EXTERNAL_DCIM = 474;
    public static final int TYPE_CACHE = 35;

    public static void savePicture(Context context, Bitmap bmp) {
        savePicture(context, TYPE_EXTERNAL_DCIM, bmp, "");
    }

    public static void savePicture(Context context, int type, Bitmap bmp, String fileName) {
        // 首先保存图片
        File appDir;
        if (type == TYPE_EXTERNAL_DCIM) {
            appDir = new File(Environment.getExternalStorageDirectory(), "DCIM");
        } else {
            appDir = new File(context.getCacheDir().toString());
        }
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        if (TextUtils.isEmpty(fileName)) {
            fileName = System.currentTimeMillis() + ".jpg";
        }
        File file = new File(appDir, fileName);
        Log.d("SavePictureUtils", "savePicture: " + fileName + "," + file.exists() + "," + file.getAbsolutePath());
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file.getAbsolutePath())));
    }
}
