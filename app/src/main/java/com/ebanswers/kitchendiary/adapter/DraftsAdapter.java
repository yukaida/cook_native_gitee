package com.ebanswers.kitchendiary.adapter;

import android.animation.Animator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ebanswers.kitchendiary.R;
import com.ebanswers.kitchendiary.bean.DraftItem;
import com.ebanswers.kitchendiary.utils.GlideApp;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

/**
* @author yukd
 * 草稿箱适配器
* */
public class DraftsAdapter extends BaseQuickAdapter<DraftItem, BaseViewHolder> {

    public DraftsAdapter(int layoutResId, @Nullable List<DraftItem> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, DraftItem item) {
        helper.setImageResource(R.id.drafts_item_imageView, R.drawable.def_qq);
        helper.setText(R.id.drafts_item_textView_name, item.getName());
        helper.addOnClickListener(R.id.drafts_item_imageView, R.id.drafts_item_textView_delete);//图片和“删除”添加点击事件
        helper.setText(R.id.drafts_item_textView_time, item.getTime());

        RoundedImageView roundedImageView = helper.getView(R.id.drafts_item_imageView);

            GlideApp.with(mContext)
                    .load(item.getImg())
//                    .skipMemoryCache(true)
                    .dontAnimate()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(roundedImageView);
    }


    @Override
    protected void startAnim(Animator anim, int index) {
        super.startAnim(anim, index);
        if (index < 10)
            anim.setStartDelay(index * 150);
    }
}
