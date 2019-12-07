package com.ebanswers.kitchendiary.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ebanswers.kitchendiary.R;

import java.util.List;

public class TopicListAdapter extends BaseQuickAdapter<String, BaseViewHolder> {


    public TopicListAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, String item) {
        helper.setText(R.id.item_topic_textView, item);
        helper.addOnClickListener(R.id.item_topic_textView);
    }
}
