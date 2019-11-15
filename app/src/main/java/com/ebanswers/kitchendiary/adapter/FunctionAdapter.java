package com.ebanswers.kitchendiary.adapter;

import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ebanswers.kitchendiary.R;
import com.ebanswers.kitchendiary.mvp.model.FunctionInfo;
import com.ebanswers.kitchendiary.utils.GlideApp;

/**
 * Create by dongli
 * Create date 2019-09-23
 * desc：首页功能
 */
public class FunctionAdapter extends BaseQuickAdapter<FunctionInfo,BaseViewHolder> {


    public FunctionAdapter() {
        super(R.layout.item_function);
    }

    @Override
    protected void convert(BaseViewHolder helper, FunctionInfo item) {

        if (!TextUtils.isEmpty(item.getFunctionName())){
            helper.setText(R.id.function_tv,item.getFunctionName());
        }

        if (item.getFunctionIv() > -1){
            GlideApp.with(mContext)
                    .load(item.getFunctionIv())
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .dontAnimate()
                    .into((ImageView) helper.getView(R.id.function_iv));
        }


    }
}
