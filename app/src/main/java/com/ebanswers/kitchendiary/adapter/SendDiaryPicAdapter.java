package com.ebanswers.kitchendiary.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ebanswers.kitchendiary.R;
import com.ebanswers.kitchendiary.bean.DiaryPicinfo;

import java.util.List;

public class SendDiaryPicAdapter extends BaseQuickAdapter<DiaryPicinfo, BaseViewHolder> {
    private Context context;

    public SendDiaryPicAdapter(Context context, int layoutResId, @Nullable List<DiaryPicinfo> data) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, DiaryPicinfo item) {

        Bitmap imageBitmap=orientation(item.getImagePath());//调整方向
        Bitmap  imageBitmapSmallSize =centerSquareScaleBitmap(imageBitmap, 400);//裁剪得到正方形展示图

        helper.setImageBitmap(R.id.item_diary_image, imageBitmapSmallSize);

        helper.addOnClickListener(R.id.item_diary_delete);
        helper.addOnClickListener(R.id.item_diary_image);//添加图片点击事件

    }

    /**
     * @param bitmap      原图
     * @param edgeLength  希望得到的正方形部分的边长
     * @return  缩放截取正中部分后的位图。
     */
    public static Bitmap centerSquareScaleBitmap(Bitmap bitmap, int edgeLength)
    {
        if(null == bitmap || edgeLength <= 0)
        {
            return  null;
        }
        Bitmap result = bitmap;
        int widthOrg = bitmap.getWidth();
        int heightOrg = bitmap.getHeight();
        if(widthOrg > edgeLength && heightOrg > edgeLength)
        {
            //压缩到一个最小长度是edgeLength的bitmap
            int longerEdge = (int)(edgeLength * Math.max(widthOrg, heightOrg) / Math.min(widthOrg, heightOrg));
            int scaledWidth = widthOrg > heightOrg ? longerEdge : edgeLength;
            int scaledHeight = widthOrg > heightOrg ? edgeLength : longerEdge;
            Bitmap scaledBitmap;
            try{
                scaledBitmap = Bitmap.createScaledBitmap(bitmap, scaledWidth, scaledHeight, true);
            }
            catch(Exception e){
                return null;
            }
            //从图中截取正中间的正方形部分。
            int xTopLeft = (scaledWidth - edgeLength) / 2;
            int yTopLeft = (scaledHeight - edgeLength) / 2;
            try{
                result = Bitmap.createBitmap(scaledBitmap, xTopLeft, yTopLeft, edgeLength, edgeLength);
                scaledBitmap.recycle();
            }
            catch(Exception e){
                return null;
            }
        }
        return result;
    }

    /**
     * @author yukaida
     * @param absolutePath 照片的绝对路劲
     * @return 重新调整方向之后的bitmap图片
     */
    public static Bitmap orientation(String absolutePath){
        Bitmap bitmap_or=BitmapFactory.decodeFile(absolutePath);
     try {

        ExifInterface exif = new ExifInterface(absolutePath);

        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);

        Log.d("EXIF", "Exif: " + orientation);

        Matrix matrix = new Matrix();

        if (orientation == 6) {

            matrix.postRotate(90);

        }

        else if (orientation == 3) {

            matrix.postRotate(180);

        }

        else if (orientation == 8) {

            matrix.postRotate(270);

        }

         bitmap_or= Bitmap.createBitmap(bitmap_or, 0, 0, bitmap_or.getWidth(), bitmap_or.getHeight(), matrix, true); // rotating bitmap
         return bitmap_or;
    }

            catch (Exception e) {

    }
        return null;
    }





}
