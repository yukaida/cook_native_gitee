package com.ebanswers.kitchendiary.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ebanswers.kitchendiary.R;
import com.ebanswers.kitchendiary.utils.GlideApp;
import com.ebanswers.kitchendiary.utils.ImageCompressUtils;
import com.makeramen.roundedimageview.RoundedImageView;

/**
 * Create by dongli
 * Create date 2019-10-30
 * desc：
 */
public class SharePictureAdapter extends BaseQuickAdapter<String, BaseViewHolder> {


    public SharePictureAdapter() {
        super(R.layout.item_picture_share);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        RoundedImageView view = helper.getView(R.id.pic_iv);
        if (!TextUtils.isEmpty(item)){

            Bitmap bitmap = BitmapFactory.decodeFile(item);
            if (bitmap != null){
                ImageCompressUtils.compressImage(bitmap);
                GlideApp.with(mContext)
                        .load(ImageCompressUtils.compressImage(bitmap))
                        .dontAnimate()
                        .centerCrop()
                        .override(140,140)
                        .skipMemoryCache(true)
                        .into(view);
            }else {
                GlideApp.with(mContext)
                        .load(item)
                        .dontAnimate()
                        .centerCrop()
                        .override(140,140)
                        .skipMemoryCache(true)
                        .into(view);
            }



        }
    }
}
