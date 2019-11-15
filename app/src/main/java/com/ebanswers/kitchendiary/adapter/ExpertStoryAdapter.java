package com.ebanswers.kitchendiary.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ebanswers.kitchendiary.R;
import com.ebanswers.kitchendiary.bean.SquareInfo;
import com.ebanswers.kitchendiary.utils.GlideApp;
import com.ebanswers.kitchendiary.widget.CircleImageView;

/**
 * Create by dongli
 * Create date 2019-10-29
 * desc：更多精彩适配
 */
public class ExpertStoryAdapter extends BaseQuickAdapter<SquareInfo.DataBean, BaseViewHolder> {

    public ExpertStoryAdapter() {
        super(R.layout.item_expert_story);
    }

    @Override
    protected void convert(BaseViewHolder helper, SquareInfo.DataBean item) {

        CircleImageView cookingUserIv = helper.getView(R.id.cooking_user_iv);

        if (!TextUtils.isEmpty(item.getHead_url())){

            GlideApp.with(mContext)
                    .load(item.getHead_url())
                    .skipMemoryCache(true)
                    .dontAnimate()
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(cookingUserIv);

        }

        if (item.isIs_master()){
            helper.getView(R.id.is_master_iv).setVisibility(View.VISIBLE);
        }else {
            helper.getView(R.id.is_master_iv).setVisibility(View.GONE);
        }

        ImageView isMasterRankIv = helper.getView(R.id.is_master_rank_iv);
        TextView masterRankTv = helper.getView(R.id.master_rank_tv);

        if (item.getMaster_rank() > 0){
            if (item.getMaster_rank() == 1){
                isMasterRankIv.setVisibility(View.VISIBLE);
                isMasterRankIv.setBackgroundResource(R.mipmap.icon_first);
                masterRankTv.setVisibility(View.GONE);
            }else if (item.getMaster_rank() == 2){
                isMasterRankIv.setVisibility(View.VISIBLE);
                isMasterRankIv.setBackgroundResource(R.mipmap.icon_second);
                masterRankTv.setVisibility(View.GONE);
            }else if (item.getMaster_rank() == 3){
                isMasterRankIv.setVisibility(View.VISIBLE);
                isMasterRankIv.setBackgroundResource(R.mipmap.icon_third);
                masterRankTv.setVisibility(View.GONE);
            }else if (item.getMaster_rank() <= 500){
                isMasterRankIv.setVisibility(View.VISIBLE);
                isMasterRankIv.setBackgroundResource(R.mipmap.icon_sort);
                masterRankTv.setVisibility(View.VISIBLE);
                masterRankTv.setText(item.getMaster_rank()+"");
            }else {
                isMasterRankIv.setVisibility(View.GONE);
                masterRankTv.setVisibility(View.GONE);
            }
        }else {
            isMasterRankIv.setVisibility(View.GONE);
            masterRankTv.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(item.getNickname())){
            helper.setText(R.id.cooking_username_tv,item.getNickname());
        }

     /*   if (item.isIs_master()){
            helper.getView(R.id.auth_cooking_iv).setVisibility(View.VISIBLE);
        }else {
            helper.getView(R.id.auth_cooking_iv).setVisibility(View.GONE);
        }*/

        if (item.isIs_subscribe()){
            helper.getView(R.id.focu_iv).setBackgroundResource(R.mipmap.icon_focuon_bg);
        }else {
            helper.getView(R.id.focu_iv).setBackgroundResource(R.mipmap.icon_focus_found);
        }

        helper.addOnClickListener(R.id.focu_iv);
        helper.addOnClickListener(R.id.cooking_username_tv);
        helper.addOnClickListener(R.id.cooking_user_iv);

        RecyclerView cookingPicRv = helper.getView(R.id.cooking_pic_rv);
        cookingPicRv.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false));
        PictureAdapter pictureAdapter = new PictureAdapter();
        cookingPicRv.setAdapter(pictureAdapter);
        if (item.getDiary().size()>0){
            pictureAdapter.setNewData(item.getDiary());
        }

    }


}
