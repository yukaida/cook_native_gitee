package com.ebanswers.kitchendiary.adapter;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ebanswers.kitchendiary.R;
import com.ebanswers.kitchendiary.bean.SquareInfo;
import com.ebanswers.kitchendiary.constant.AppConstant;
import com.ebanswers.kitchendiary.mvp.view.base.WebActivity;
import com.ebanswers.kitchendiary.utils.GlideApp;
import com.ebanswers.kitchendiary.utils.SPUtils;
import com.makeramen.roundedimageview.RoundedImageView;

/**
 * Create by dongli
 * Create date 2019-10-29
 * desc：
 */
public class PictureAdapter extends BaseQuickAdapter<SquareInfo.DataBean.DiaryBean, BaseViewHolder> {


    public PictureAdapter() {
        super(R.layout.item_pictrue);
    }

    @Override
    protected void convert(BaseViewHolder helper, SquareInfo.DataBean.DiaryBean item) {

        RoundedImageView picIv = helper.getView(R.id.pic_iv);

        if (!TextUtils.isEmpty(item.getMsg_content())){

            helper.setText(R.id.name_tv,item.getMsg_content());
        }


        if (item.getImg_url() != null && !TextUtils.isEmpty(item.getThumbnail_url().get(0))){
            GlideApp.with(mContext)
                    .load(item.getThumbnail_url().get(0))
                    .dontAnimate()
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(picIv);
        }

        picIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, WebActivity.class);
                String openid = (String) SPUtils.get(AppConstant.USER_ID, "");
                String url = "http://wechat.53iq.com/tmp/kitchen/diary/" + item.getDiary_id() + "/detail?code=123&openid=" + openid;
                intent.putExtra("url",url);
                mContext.startActivity(intent);

            }
        });

    }
}
