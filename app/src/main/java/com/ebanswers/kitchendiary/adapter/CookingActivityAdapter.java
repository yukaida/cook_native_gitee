package com.ebanswers.kitchendiary.adapter;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ebanswers.kitchendiary.R;
import com.ebanswers.kitchendiary.bean.RecommendForYou;
import com.ebanswers.kitchendiary.common.CommonApplication;
import com.ebanswers.kitchendiary.utils.GlideApp;
import com.makeramen.roundedimageview.RoundedImageView;

/**
 * Create by dongli
 * Create date 2019-10-29
 * desc：美食活动适配器
 */
public class CookingActivityAdapter extends BaseQuickAdapter<RecommendForYou, BaseViewHolder> {

    public CookingActivityAdapter() {
        super(R.layout.item_cooking_activity);
    }

    @Override
    protected void convert(BaseViewHolder helper, RecommendForYou item) {

        RoundedImageView cookingActivityIv = helper.getView(R.id.cooking_activity_iv);

        if (!TextUtils.isEmpty(item.getImg())){
            Log.d(TAG, "convert: "+item.getImg());
            if ("activity".equals(item.getImg())) {
                GlideApp.with(CommonApplication.getInstance()).asGif().load(R.drawable.activity).into(cookingActivityIv);
            } else {
                GlideApp.with(mContext)
                        .load(item.getImg())
                        .skipMemoryCache(true)
                        .dontAnimate()
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .into(cookingActivityIv);
            }
        }


        if(!TextUtils.isEmpty(item.getTitle())){
            helper.setText(R.id.activity_name_tv,item.getTitle());
        }

        TextView activityStatusTv = helper.getView(R.id.activity_status_tv);

        if (!TextUtils.isEmpty(item.getStatus()+"")){
            if (item.getStatus() == 0){
                activityStatusTv.setVisibility(View.VISIBLE);
                if (!TextUtils.isEmpty(item.getTypes())){
                    if (item.getTypes().equals("activity")){
                        activityStatusTv.setBackground(mContext.getResources().getDrawable(R.mipmap.icon_recommend_bg));
                        activityStatusTv.setText("强力推荐");
                    }else if (item.getTypes().equals("meal_class_cover")){
                        activityStatusTv.setBackground(mContext.getResources().getDrawable(R.mipmap.icon_doing_bg));
                        activityStatusTv.setText("进行中");
                    }
                }
            }else {
                activityStatusTv.setVisibility(View.GONE);
            }
        }

        if (!TextUtils.isEmpty(item.getStart_time()) && !TextUtils.isEmpty(item.getEnd_time())){
            helper.setText(R.id.activity_time_tv,item.getStart_time() + "-" + item.getEnd_time());
        }else if (!TextUtils.isEmpty(item.getStart_time())){
            helper.setText(R.id.activity_time_tv,item.getStart_time());
        }else if (!TextUtils.isEmpty(item.getEnd_time())){
            helper.setText(R.id.activity_time_tv,item.getEnd_time());
        }


    }
}
