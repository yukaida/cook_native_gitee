package com.ebanswers.kitchendiary.adapter;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ebanswers.kitchendiary.R;
import com.ebanswers.kitchendiary.bean.Stepinfo;
import com.ebanswers.kitchendiary.utils.GlideApp;

import java.util.List;

/**
 * Create by dongli
 * Create date 2019-11-06
 * desc：步骤适配
 */
public class FoodStepAdapter extends BaseItemDraggableAdapter<Stepinfo, BaseViewHolder> {

    public FoodStepAdapter(List<Stepinfo> data) {
        super(R.layout.item_food_step,data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, Stepinfo item) {
        helper.setIsRecyclable(false);
        helper.setText(R.id.step_num_tv,"步骤" + (helper.getPosition() +1));
        EditText stepDescEt = helper.getView(R.id.step_desc_et);
        ImageView deleteIv = helper.getView(R.id.delete_iv);
        LinearLayout touchLl = helper.getView(R.id.touch_ll);
        RelativeLayout add_pic_step_rl = helper.getView(R.id.add_pic_step_rl);
        helper.addOnClickListener(R.id.delete_iv);

        if (item.isEdit()){
            stepDescEt.setFocusable(false);
            add_pic_step_rl.setClickable(false);
        }else {
            stepDescEt.setFocusable(true);
            add_pic_step_rl.setClickable(true);
            helper.addOnClickListener(R.id.add_pic_step_rl);
        }

      /*  helper.setOnClickListener(R.id.delete_iv, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onDeleteItemListener != null){
                    onDeleteItemListener.delete(helper.getAdapterPosition());
                }
            }
        });*/

        if (!TextUtils.isEmpty(item.getDesc())){
            stepDescEt.setText(item.getDesc());
        }else {
            stepDescEt.setText("");
        }

        stepDescEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                item.setDesc(s.toString());
            }
        });

        if (!TextUtils.isEmpty(item.getImg())){
            GlideApp.with(mContext)
                    .load(item.getThumbnail())
                    .skipMemoryCache(true)
                    .placeholder(null)
                    .dontAnimate()
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into((ImageView) helper.getView(R.id.step_iv));

            helper.getView(R.id.add_ll).setVisibility(View.GONE);
        }else {
            ((ImageView) helper.getView(R.id.step_iv)).setBackgroundColor(mContext.getResources().getColor(R.color.gray01));
            helper.getView(R.id.add_ll).setVisibility(View.VISIBLE);
        }

        if (item.isEdit()){
            deleteIv.setVisibility(View.VISIBLE);
            touchLl.setVisibility(View.VISIBLE);

        }else {
            deleteIv.setVisibility(View.GONE);
            touchLl.setVisibility(View.GONE);
        }

    }

    interface OnDeleteItemListener{
        void delete(int position);
    }


    OnDeleteItemListener onDeleteItemListener;

    public void setOnDeleteItemListener(OnDeleteItemListener onDeleteItemListener) {
        this.onDeleteItemListener = onDeleteItemListener;
    }
}
