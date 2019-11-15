package com.ebanswers.kitchendiary.adapter;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.ebanswers.kitchendiary.R;
import com.ebanswers.kitchendiary.mvp.model.NoticeItem;
import com.ebanswers.kitchendiary.utils.SpannableStringUtils;

import java.util.List;


/**
 * SimpleBulletinAdapter 简单的垂直公告栏轮播适配器
 * Created by bakumon on 2017/3/31 17:22.
 */
public class SimpleBulletinAdapter extends BulletinAdapter<NoticeItem> {

    private int mImageDrawableID = R.mipmap.icon_notice;
    private Context context;

    public SimpleBulletinAdapter(Context context, List<NoticeItem> dataList) {
        this(context, dataList, R.mipmap.icon_notice);
        this.context = context;
    }

    public SimpleBulletinAdapter(Context context, List<NoticeItem> dataList, int imageDrawableID) {
        super(context, dataList);
        mImageDrawableID = imageDrawableID;
    }

    @Override
    public View getView(int position) {

        View view = getRootView(R.layout.simple_item);

//        ImageView imageView = (ImageView) view.findViewById(R.id.iv_image);
        TextView textView = (TextView) view.findViewById(R.id.tv_content);

        NoticeItem data = mData.get(position);
//        imageView.setImageResource(mImageDrawableID);
        if (!TextUtils.isEmpty(data.getNotice())){
            SpannableStringBuilder spannableStringBuilder = SpannableStringUtils.getBuilder(data.getNotice()).append(data.getNotice()).create();
            textView.setText(spannableStringBuilder);
        }

        return view;
    }

}
