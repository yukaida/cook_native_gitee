package com.ebanswers.kitchendiary.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageCompressUtils {

    /**
     * 一、质量压缩法
     * @param bitmap
     * @return
     */
    public static Bitmap compressImage(Bitmap bitmap) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while ( baos.toByteArray().length / 1024>100) { //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap1 = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap1;
    }

    /**
     *  二、图片按比例大小压缩方法（根据路径获取图片并压缩）
     */
    public static  Bitmap getimage(String srcPath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath,newOpts);//此时返回bm为空

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 800f;//这里设置高度为800f
        float ww = 480f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        return compressImage(bitmap);//压缩好比例大小后再进行质量压缩
    }

    /**
     * 三、图片按比例大小压缩方法（根据Bitmap图片压缩）
     */
    public static  Bitmap comp(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        if( baos.toByteArray().length / 1024>1024) {//判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
            baos.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, 50, baos);//这里压缩50%，把压缩后的数据存放到baos中
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 800f;//这里设置高度为800f
        float ww = 480f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        isBm = new ByteArrayInputStream(baos.toByteArray());
        bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        return compressImage(bitmap);//压缩好比例大小后再进行质量压缩
    }
    /** 质量压缩,
     * @param bitmap 要压缩的图片
     * @param file //压缩的图片保存地址
     *  Hint to the compressor, 0-100. 0 meaning compress for small size, 100 meaning compress for max quality. Some
     * formats, like PNG which is lossless, will ignore the quality setting
     * quality  (0-100)  100是不压缩，值越小，压缩得越厉害
     */
    public static boolean qualityCompressBitmap(Bitmap bitmap, File file){
        //字节数组输出流
        ByteArrayOutputStream stream =new ByteArrayOutputStream();
        int quality=80;
        //图片压缩后把数据放在stream中
        boolean compress = bitmap.compress(Bitmap.CompressFormat.JPEG, quality, stream);
        try {
            FileOutputStream fileOutputStream=new FileOutputStream(file);
            //不断把stream的数据写文件输出流中去
            fileOutputStream.write(stream.toByteArray());
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return compress;
    }

    /**尺寸压缩
     * @param bitmap 要压缩的图片
     * @param ratio 压缩比例，值越大，图片的尺寸就越小
     * @param file 压缩的图片保存地址
     */
    public static void sizeCompressBitmap(Bitmap bitmap,int ratio,File file){
        if (ratio<=0){
            return;
        }
        Bitmap result=Bitmap.createBitmap(bitmap.getWidth()/ratio,bitmap.getHeight()/ratio, Bitmap.Config.ARGB_8888);
        Canvas canvas =new Canvas();
        Rect rect=new Rect(0,0,bitmap.getWidth()/ratio,bitmap.getHeight()/ratio);
        canvas.drawBitmap(bitmap,null,rect,null);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // 把压缩后的数据存放到baos中
        result.compress(Bitmap.CompressFormat.JPEG, 100 ,baos);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(baos.toByteArray());
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** 采样率压缩
     * @param filePath 压缩图
     * @param file 压缩的图片保存地址
     */
    public  static void pixeCompressBitmap(String filePath, File file){
        //采样率，数值越高，图片像素越低
        int inSampleSize=8;
        BitmapFactory.Options osts=new BitmapFactory.Options();
        osts.inSampleSize=inSampleSize;
        //inJustDecodeBounds设为True时，不会真正加载图片，而是得到图片的宽高信息。
        osts.inJustDecodeBounds=false;
        Bitmap bitmap= BitmapFactory.decodeFile(filePath,osts);
        ByteArrayOutputStream stream =new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
        try {
            if (file.exists()){
                file.delete();
            }else{
                file.createNewFile();
            }
            FileOutputStream fileOutputStream=new FileOutputStream(file);
            fileOutputStream.write(stream.toByteArray());
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void createBitmapThumbnail(String filePath, File file, boolean needRecycler) {
        //采样率，数值越高，图片像素越低
        int inSampleSize=1;
        BitmapFactory.Options osts=new BitmapFactory.Options();
        osts.inSampleSize=inSampleSize;
        //inJustDecodeBounds设为True时，不会真正加载图片，而是得到图片的宽高信息。
        osts.inJustDecodeBounds=false;
        Bitmap bitmap= BitmapFactory.decodeFile(filePath);
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int newWidth = width / 3 * 2;
        int newHeight = height / 3 * 2;
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap newBitMap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);

        ByteArrayOutputStream stream =new ByteArrayOutputStream();
        newBitMap.compress(Bitmap.CompressFormat.JPEG,100,stream);
        try {
            if (file.exists()){
                file.delete();
            }else{
                file.createNewFile();
            }
            FileOutputStream fileOutputStream=new FileOutputStream(file);
            fileOutputStream.write(stream.toByteArray());
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (needRecycler) {
                bitmap.recycle();
                newBitMap.recycle();
            }
        }


    }

    /**
     * 删除文件夹和文件夹里面的文件
     */
    public static void deleteDir(final String pPath) {
        File dir = new File(pPath);
        deleteDirWihtFile(dir);
    }

    public static void deleteDirWihtFile(File dir) {
        if (dir == null || !dir.exists() || !dir.isDirectory())
            return;
        for (File file : dir.listFiles()) {
            if (file.isFile())
                file.delete(); // 删除所有文件
            else if (file.isDirectory())
                deleteDirWihtFile(file); // 递规的方式删除文件夹
        }
        dir.delete();// 删除目录本身
    }


    public static File saveBitmapFile(Bitmap bitmap){
        File file=new File("/mnt/sdcard/pic/01.jpg");//将要保存图片的路径
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }



}
