package com.ebanswers.kitchendiary.adapter;

import android.text.TextUtils;

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

            GlideApp.with(mContext)
                    .load(item)
                    .placeholder(R.drawable.empty)
                    .dontAnimate()
                    .centerCrop()
                    .thumbnail(0.5f)
//                    .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
//                    .optionalTransform(new TransformationUtils())
                    .into(picIv);
        }
    }
}
