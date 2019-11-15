package com.ebanswers.kitchendiary.adapter;

import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ebanswers.kitchendiary.R;
import com.ebanswers.kitchendiary.bean.SquareInfo;
import com.ebanswers.kitchendiary.utils.GlideApp;
import com.ebanswers.kitchendiary.widget.CircleImageView;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.Random;

/**
 * Create by dongli
 * Create date 2019-10-29
 * desc：更多精彩适配器
 */
public class MoreWonderfulAdapter extends BaseQuickAdapter<SquareInfo.DataBean, BaseViewHolder> {


    public MoreWonderfulAdapter() {
        super(R.layout.item_more_wonderful);
    }

    @Override
    protected void convert(BaseViewHolder helper, SquareInfo.DataBean item) {

        CircleImageView cookingUserIv = helper.getView(R.id.goods_builder_iv);
        if (!TextUtils.isEmpty(item.getHead_url())){

            GlideApp.with(mContext)
                    .load(item.getHead_url())
                    .skipMemoryCache(true)
                    .dontAnimate()
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(cookingUserIv);

        }

        RoundedImageView goodsShowIv = helper.getView(R.id.goods_show_iv);

        ViewGroup.LayoutParams layoutParams = goodsShowIv.getLayoutParams();
        layoutParams.width = layoutParams.width;
        Random rand = new Random();
        layoutParams.height = rand.nextInt(100)+600;
        goodsShowIv.setLayoutParams(layoutParams);

        if (item.getDiary()!= null && !TextUtils.isEmpty(item.getDiary().get(0).getImg_url().get(0))){

            GlideApp.with(mContext)
                    .load(item.getDiary().get(0).getImg_url().get(0))
                    .skipMemoryCache(true)
                    .dontAnimate()
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .placeholder(R.drawable.empty)
                    .into(goodsShowIv);

        }


        ImageView focusStatusIv = helper.getView(R.id.focus_status_iv);
        helper.addOnClickListener(R.id.focus_status_iv);
        helper.addOnClickListener(R.id.goods_builder_iv);
        helper.addOnClickListener(R.id.goods_builder_tv);
        helper.addOnClickListener(R.id.goods_show_iv);

        if (item.isIs_subscribe()){
            focusStatusIv.setBackgroundResource(R.mipmap.icon_focu);
        }else {
            focusStatusIv.setBackgroundResource(R.mipmap.icon_focus_off);
        }

        if (!TextUtils.isEmpty(item.getNickname())){
            helper.setText(R.id.goods_builder_tv,item.getNickname());
        }

        if (!TextUtils.isEmpty(item.getMaster_rank() + "")){
            helper.setText(R.id.goods_focu_num_tv,item.getMaster_rank() + "");
        }

      /* if (!TextUtils.isEmpty(item.getNickname())){
            helper.setText(R.id.goods_name_tv,item.getNickname());
        }
*/

    }

}
