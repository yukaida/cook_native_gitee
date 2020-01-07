package com.ebanswers.kitchendiary.adapter;

import android.text.TextUtils;

import com.bumptech.glide.request.target.Target;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ebanswers.kitchendiary.R;
import com.ebanswers.kitchendiary.utils.GlideApp;
import com.makeramen.roundedimageview.RoundedImageView;

/**
 * Create by dongli
 * Create date 2019-10-30
 * desc：
 */
public class SinglePictureAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public SinglePictureAdapter() {
        super(R.layout.item_picture_single);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        RoundedImageView picIv = helper.getView(R.id.pic_iv);

        if (!TextUtils.isEmpty(item)){
            //1、通过自己构造 target 可以获取到图片实例
      /*      SimpleTarget<Bitmap> simpleTarget = new SimpleTarget<Bitmap>() {

                @Override
                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                    picIv.setImageBitmap(resource);
                }
            };*/
            GlideApp.with(mContext)
                    .load(item)
                    .placeholder(R.drawable.empty)
                    .dontAnimate()
                    .centerCrop ()
                    .override(Target.SIZE_ORIGINAL)
//                    .submit(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);
                    .into(picIv);
        }
    }
}
