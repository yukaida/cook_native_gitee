package com.ebanswers.kitchendiary.adapter;

import android.text.TextUtils;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ebanswers.kitchendiary.R;
import com.ebanswers.kitchendiary.network.response.FocusResponse;
import com.ebanswers.kitchendiary.widget.CircleImageView;
import com.ebanswers.kitchendiary.widget.decorator.HorizontaltemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by dongli
 * Create date 2019-10-30
 * desc：
 */
public class FocusAdapter extends BaseQuickAdapter<FocusResponse.MasterBean, BaseViewHolder> {


    public FocusAdapter() {
        super(R.layout.item_focus_show);
    }

    @Override
    protected void convert(BaseViewHolder helper, FocusResponse.MasterBean item) {

        CircleImageView userHeadIv = helper.getView(R.id.user_head_iv);
        ImageView focuStatusIv = helper.getView(R.id.focu_status_iv);
        if (!TextUtils.isEmpty(item.getNickname())){
            helper.setText(R.id.user_name_tv,item.getNickname());
        }

//        helper.setText(R.id.user_descrbe_tv,"用户描述");
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add("121212");
        }

        RecyclerView sharePicRv = helper.getView(R.id.share_pic_rv);
        RecyclerView  commentsRv = helper.getView(R.id.comments_rv);
        sharePicRv.setLayoutManager(new LinearLayoutManager(mContext,RecyclerView.HORIZONTAL,false));
        sharePicRv.addItemDecoration(new HorizontaltemDecoration(10));
        SharePictureAdapter sharePictureAdapter = new SharePictureAdapter();
        sharePicRv.setAdapter(sharePictureAdapter);
        sharePictureAdapter.setNewData(list);
        sharePictureAdapter.notifyDataSetChanged();

        commentsRv.setLayoutManager(new LinearLayoutManager(mContext));
        CommentsAdapter commentsAdapter = new CommentsAdapter();
        commentsRv.setAdapter(commentsAdapter);
//        commentsAdapter.setNewData(commentsInfos);
        commentsAdapter.notifyDataSetChanged();

        helper.setText(R.id.sned_time_tv,"8:00");

        helper.setText(R.id.collection_tv,"8");
        ImageView collectionStatusIv = helper.getView(R.id.collection_status_iv);


        ImageView focusStatusIv = helper.getView(R.id.focus_status_iv);


        helper.setText(R.id.message_tv,"5");
        helper.setText(R.id.focus_tv,"8");


    }
}
