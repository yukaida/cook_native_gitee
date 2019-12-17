package com.ebanswers.kitchendiary.adapter;

import android.content.Intent;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ebanswers.kitchendiary.R;
import com.ebanswers.kitchendiary.bean.CommentInfo;
import com.ebanswers.kitchendiary.mvp.view.base.HomeActivity;
import com.ebanswers.kitchendiary.mvp.view.base.WebActivity;
import com.ebanswers.kitchendiary.utils.SpannableStringUtils;

/**
 * Create by dongli----------评论适配器------------
 * Create date 2019-10-30
 * desc：
 */
public class CommentsAdapter extends BaseQuickAdapter<CommentInfo, BaseViewHolder> {

    public CommentsAdapter() {
        super(R.layout.item_comments);
    }

    @Override
    protected void convert(BaseViewHolder helper, CommentInfo item) {

        if (TextUtils.isEmpty(item.getFrom_user())){
            SpannableStringUtils.Builder builder = SpannableStringUtils.getBuilder("");
            builder.append(item.getNickname()).setForegroundColor(mContext.getResources().getColor(R.color.btn_blue_normal)).setClickSpan(new ClickableSpan() {
                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setColor(mContext.getResources().getColor(R.color.btn_blue_normal));       //设置文件颜色
                    ds.setUnderlineText(false);      //设置下划线
                }

                @Override
                public void onClick(@NonNull View widget) {
                    Intent intent = new Intent(mContext, WebActivity.class);
                    intent.putExtra("url", "https://wechat.53iq.com/tmp/kitchen/food/diary?openid=" + item.getOpenid() + "&my_openid=" + HomeActivity.isLoginMethod());
                    mContext.startActivity(intent);
                }
            });
            builder.append("：" + item.getComment()).setClickSpan(new ClickableSpan() {
                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setColor(mContext.getResources().getColor(R.color.text_gray_normal));       //设置文件颜色
                    ds.setUnderlineText(false);      //设置下划线
                }

                @Override
                public void onClick(@NonNull View widget) {
                    if (onCommentClickListener != null){
                        onCommentClickListener.click(helper.getPosition());
                    }
                }
            });

//            addTagToTextView(helper.getView(R.id.liked_username_tv),builder.create().toString(),"等" + item.getLiked().size() + "人觉得很赞");
            helper.setText(R.id.reader_name_tv,builder.create());
            ((TextView)helper.getView(R.id.reader_name_tv)).setMovementMethod(LinkMovementMethod.getInstance());
        }else {

            SpannableStringUtils.Builder builder = SpannableStringUtils.getBuilder("");
            builder.append(item.getNickname()).setForegroundColor(mContext.getResources().getColor(R.color.btn_blue_normal)).setClickSpan(new ClickableSpan() {
                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setColor(mContext.getResources().getColor(R.color.btn_blue_normal));       //设置文件颜色
                    ds.setUnderlineText(false);      //设置下划线
                }

                @Override
                public void onClick(@NonNull View widget) {
                    Intent intent = new Intent(mContext, WebActivity.class);
                    intent.putExtra("url", "https://wechat.53iq.com/tmp/kitchen/food/diary?openid=" + item.getOpenid()+ "&my_openid=" + HomeActivity.isLoginMethod());
                    mContext.startActivity(intent);
                }
            });
            builder.append("回复").setClickSpan(new ClickableSpan() {
                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setColor(mContext.getResources().getColor(R.color.text_gray_normal));       //设置文件颜色
                    ds.setUnderlineText(false);      //设置下划线
                }

                @Override
                public void onClick(@NonNull View widget) {
                    if (onCommentClickListener != null){
                        onCommentClickListener.click(helper.getPosition());
                    }
                }
            });

            builder.append(item.getFrom_user()).setForegroundColor(mContext.getResources().getColor(R.color.btn_blue_normal)).setClickSpan(new ClickableSpan() {
                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setColor(mContext.getResources().getColor(R.color.btn_blue_normal));       //设置文件颜色
                    ds.setUnderlineText(false);      //设置下划线
                }

                @Override
                public void onClick(@NonNull View widget) {
                    Intent intent = new Intent(mContext, WebActivity.class);
                    intent.putExtra("url", "https://wechat.53iq.com/tmp/kitchen/food/diary?openid=" + item.getFrom_openid()+ "&my_openid=" + HomeActivity.isLoginMethod());
                    mContext.startActivity(intent);
                }
            });
            builder.append("：" + item.getComment()).setClickSpan(new ClickableSpan() {
                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setColor(mContext.getResources().getColor(R.color.text_gray_normal));       //设置文件颜色
                    ds.setUnderlineText(false);      //设置下划线
                }

                @Override
                public void onClick(@NonNull View widget) {
                    if (onCommentClickListener != null){
                        onCommentClickListener.click(helper.getPosition());
                    }
                }
            });

//            addTagToTextView(helper.getView(R.id.liked_username_tv),builder.create().toString(),"等" + item.getLiked().size() + "人觉得很赞");
            helper.setText(R.id.reader_name_tv,builder.create());
            ((TextView)helper.getView(R.id.reader_name_tv)).setMovementMethod(LinkMovementMethod.getInstance());
        }


    }

    interface onCommentClickListener{
        void click(int position);
    }

    onCommentClickListener onCommentClickListener;

    public void setOnCommentClickListener(CommentsAdapter.onCommentClickListener onCommentClickListener) {
        this.onCommentClickListener = onCommentClickListener;
    }
}
