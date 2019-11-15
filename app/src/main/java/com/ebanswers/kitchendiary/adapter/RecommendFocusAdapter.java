package com.ebanswers.kitchendiary.adapter;

import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ebanswers.kitchendiary.R;
import com.ebanswers.kitchendiary.bean.MasterInfo;
import com.ebanswers.kitchendiary.utils.GlideApp;

/**
 * Create by dongli
 * Create date 2019-10-30
 * desc：发现推荐适配器
 */
public class RecommendFocusAdapter extends BaseQuickAdapter<MasterInfo, BaseViewHolder> {

    public RecommendFocusAdapter() {
        super(R.layout.item_recommend_focus);
    }

    @Override
    protected void convert(BaseViewHolder helper, MasterInfo item) {

        ImageView userHeadIv = helper.getView(R.id.user_head_iv);
        TextView userNameTv = helper.getView(R.id.user_name_tv);
        TextView userStatusTv = helper.getView(R.id.user_status_tv);
        ImageView focusUserIv = helper.getView(R.id.focus_user_iv);

        if (!TextUtils.isEmpty(item.getNickname())){
            helper.setText(R.id.user_name_tv,item.getNickname());
        }

        if (item.getSignature() != null && item.getSignature().size() > 0) {
            helper.setText(R.id.user_status_tv, item.getSignature().get(0));
        }
        if (!TextUtils.isEmpty(item.getHeadimgurl())) {

            GlideApp.with(mContext).load(item.getHeadimgurl()).placeholder(R.mipmap.icon_empty).diskCacheStrategy(DiskCacheStrategy.NONE).dontAnimate().skipMemoryCache(true).into(userHeadIv);
        }

        helper.addOnClickListener(R.id.focus_user_iv);

        if (item.isIs_subscribe()){
            focusUserIv.setBackgroundResource(R.mipmap.icon_focuon_bg);
        }else {
            focusUserIv.setBackgroundResource(R.mipmap.icon_focus_found);
        }
    }



}
