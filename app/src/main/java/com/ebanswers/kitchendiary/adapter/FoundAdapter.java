package com.ebanswers.kitchendiary.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ebanswers.kitchendiary.R;
import com.ebanswers.kitchendiary.bean.AllMsgFound;
import com.ebanswers.kitchendiary.bean.CommentInfo;
import com.ebanswers.kitchendiary.bean.CommentInfoMore;
import com.ebanswers.kitchendiary.constant.AppConstant;
import com.ebanswers.kitchendiary.mvp.view.base.ImageLookActivity;
import com.ebanswers.kitchendiary.mvp.view.base.WebActivity;
import com.ebanswers.kitchendiary.network.api.ApiMethods;
import com.ebanswers.kitchendiary.network.observer.MyObserver;
import com.ebanswers.kitchendiary.network.observer.ObserverOnNextListener;
import com.ebanswers.kitchendiary.utils.GlideApp;
import com.ebanswers.kitchendiary.utils.SPUtils;
import com.ebanswers.kitchendiary.utils.SpannableStringUtils;
import com.ebanswers.kitchendiary.widget.CircleImageView;
import com.ebanswers.kitchendiary.widget.VerticalImageSpan;
import com.luck.picture.lib.tools.ScreenUtils;
import com.previewlibrary.GPreviewBuilder;
import com.previewlibrary.enitity.ThumbViewInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by dongli
 * Create date 2019-10-30
 * desc：
 */
public class FoundAdapter extends BaseQuickAdapter<AllMsgFound, BaseViewHolder> {



    public FoundAdapter() {
        super(R.layout.item_found_data);
    }

    CommentDeleteLisenter commentDeleteLisenter;

    @Override
    protected void convert(BaseViewHolder helper, AllMsgFound item) {

        CircleImageView userHeadIv = helper.getView(R.id.user_head_iv);
        ImageView isTopShowIv = helper.getView(R.id.is_top_show_iv);
        String userid = (String) SPUtils.get(AppConstant.USER_ID, "");

        if (userid.equals(item.getCreate_user())){
            helper.getView(R.id.focu_status_iv).setVisibility(View.GONE);
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

        if (item.getIs_top() != 0){
            isTopShowIv.setVisibility(View.VISIBLE);
        }else {
            isTopShowIv.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(item.getHead_url())){
            GlideApp.with(mContext)
                    .load(item.getHead_url())
                    .skipMemoryCache(true)
                    .dontAnimate()
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(userHeadIv);
        }else {
            GlideApp.with(mContext)
                    .load(R.mipmap.icon_user_head)
                    .skipMemoryCache(true)
                    .dontAnimate()
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(userHeadIv);
        }

        if (item.isIs_subscribe()){
            helper.getView(R.id.focu_status_iv).setBackgroundResource(R.mipmap.icon_focuon_bg);
        }else {
            helper.getView(R.id.focu_status_iv).setBackgroundResource(R.mipmap.icon_focus_found);
        }

        helper.addOnClickListener(R.id.focu_status_iv);

        if (!TextUtils.isEmpty(item.getNickname())){
            helper.setText(R.id.user_name_tv,item.getNickname());
        }else {
            helper.setText(R.id.user_name_tv,"");
        }

        if (!TextUtils.isEmpty(item.getMsg_content())){
            if (!TextUtils.isEmpty(item.getDiary_type()) && item.getDiary_type().equals("cookbook")){

                SpannableString spannableString = new SpannableString(" " + item.getMsg_content());
                Drawable drawable = mContext.getResources().getDrawable(R.mipmap.icon_repice);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                spannableString.setSpan(new VerticalImageSpan(drawable), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                helper.setText(R.id.user_descrbe_tv,spannableString);

            }else {
                helper.setText(R.id.user_descrbe_tv,item.getMsg_content());
            }

        }else {
            if (!TextUtils.isEmpty(item.getDiary_type()) && item.getDiary_type().equals("cookbook")){

                SpannableString spannableString = new SpannableString(" " + item.getMsg_content());
                Drawable drawable = mContext.getResources().getDrawable(R.mipmap.icon_repice);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                spannableString.setSpan(new VerticalImageSpan(drawable), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                helper.setText(R.id.user_descrbe_tv,spannableString);
            }
        }

        RecyclerView sharePicRv = helper.getView(R.id.share_pic_rv);
        sharePicRv.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false));

        if (item.getThumbnail_url() != null && item.getThumbnail_url().size() > 0){
            if (item.getThumbnail_url().size() > 1){
                SharePictureAdapter sharePictureAdapter = new SharePictureAdapter();
                sharePicRv.setAdapter(sharePictureAdapter);
                sharePictureAdapter.setNewData(item.getThumbnail_url());
                sharePictureAdapter.notifyDataSetChanged();
                sharePictureAdapter.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                        //组织数据
                        ArrayList<ThumbViewInfo> mThumbViewInfoList = new ArrayList<>(); // 这个最好定义成成员变量
                        ThumbViewInfo iteminfo;
                        mThumbViewInfoList.clear();
                        for (int i = 0;i < item.getImg_url().size(); i++) {
                            Rect bounds = new Rect();
                            //new ThumbViewInfo(图片地址);
                            iteminfo=new ThumbViewInfo(item.getThumbnail_url().get(i));
                            iteminfo.setBounds(bounds);
                            mThumbViewInfoList.add(iteminfo);
                        }

                        //打开预览界面
                        GPreviewBuilder.from((Activity) mContext)
                                //是否使用自定义预览界面，当然8.0之后因为配置问题，必须要使用
                                .to(ImageLookActivity.class)
                                .setData(mThumbViewInfoList)
                                .setCurrentIndex(position)
                                .setSingleFling(true)
                                .setType(GPreviewBuilder.IndicatorType.Number)
                                // 小圆点
                                //  .setType(GPreviewBuilder.IndicatorType.Dot)
                                .start();//启动
                    }
                });
            }else {
                SinglePictureAdapter singlePictureAdapter = new SinglePictureAdapter();
                sharePicRv.setAdapter(singlePictureAdapter);
                singlePictureAdapter.setNewData(item.getImg_url());
                singlePictureAdapter.notifyDataSetChanged();
                singlePictureAdapter.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                        //组织数据
                        ArrayList<ThumbViewInfo> mThumbViewInfoList = new ArrayList<>(); // 这个最好定义成成员变量
                        mThumbViewInfoList.clear();
                        for (int i = 0;i < item.getImg_url().size(); i++) {
                            Rect bounds = new Rect();
                            //new ThumbViewInfo(图片地址);
                            ThumbViewInfo iteminfo = new ThumbViewInfo(item.getImg_url().get(i));
                            iteminfo.setBounds(bounds);
                            mThumbViewInfoList.add(iteminfo);
                        }

                        //打开预览界面
                        GPreviewBuilder.from((Activity) mContext)
                                //是否使用自定义预览界面，当然8.0之后因为配置问题，必须要使用
                                .to(ImageLookActivity.class)
                                .setData(mThumbViewInfoList)
                                .setCurrentIndex(position)
                                .setSingleFling(true)
                                .setType(GPreviewBuilder.IndicatorType.Number)
                                // 小圆点
                                //  .setType(GPreviewBuilder.IndicatorType.Dot)
                                .start();//启动
                    }
                });
            }
        }
        RecyclerView commentsRv = helper.getView(R.id.comments_rv);
        commentsRv.setHasFixedSize(true);
        commentsRv.setLayoutManager(new LinearLayoutManager(mContext));
        CommentsAdapter commentsAdapter = new CommentsAdapter();

        //-------------------查看更多--------------------
        TextView lookMoreTv = helper.getView(R.id.look_more_tv);
        commentsRv.setAdapter(commentsAdapter);

        if (item.getComment() != null && item.getComment().size() > 0){
            commentsRv.setAdapter(commentsAdapter);

            if (item.getComment_count()>3){
                lookMoreTv.setVisibility(View.VISIBLE);
                for (int i = 0; i < 3; i++) {
                    commentsAdapter.addData(item.getComment().get(i));
                }
            }

            else {
                lookMoreTv.setVisibility(View.GONE);
                commentsAdapter.setNewData(item.getComment());
            }

            commentsAdapter.notifyDataSetChanged();
         /*   commentsAdapter.setOnCommentClickListener(new () {
                @Override
                public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                    if (commentDeleteLisenter != null){
                        commentDeleteLisenter.deleteComment((CommentInfo) adapter.getItem(position),item.getDiary_id(),position);
                    }

                    return true;
                }
            });*/

            commentsAdapter.setOnCommentClickListener(new CommentsAdapter.onCommentClickListener() {
                @Override
                public void click(int position) {
                    String userId = (String) SPUtils.get(AppConstant.USER_ID, "");
                    if (userId.equals(((CommentInfo) commentsAdapter.getItem(position)).getOpenid())){
                        if (commentDeleteLisenter != null){
                            commentDeleteLisenter.deleteComment((CommentInfo) commentsAdapter.getItem(position),item.getDiary_id(),position,helper.getAdapterPosition());
                        }
                    }else {
                        if (commentDeleteLisenter != null) {
                            commentDeleteLisenter.replyComment((CommentInfo) commentsAdapter.getItem(position), item.getDiary_id(),helper.getAdapterPosition());
                        }
                    }
                }
            });

            commentsAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {


                }
            });


            lookMoreTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int size = item.getComment_count();//评论数
                    List<CommentInfo> data = commentsAdapter.getData();//在展示的评论数
                    if (data.size() < size){
//                        if (size - data.size() > 0 && size - data.size() < 5){
//                            for (int i = 0; i < commentInfoMore_get.getData().size(); i++) {
                                //todo 在这添加获取剩余评论的方法 并添加进去

                        Log.d(TAG, "onClick: yukaida__"+item.getDiary_id());
                                getMoreComment(commentsAdapter,item.getDiary_id(),lookMoreTv);
//                                lookMoreTv.setVisibility(View.GONE);
//                            }

                        }else {
//                            for (int i = data.size(); i < data.size() +5; i++) {
//                                commentsAdapter.addData(item.getComment().get(i));
//                            }
//                            lookMoreTv.setVisibility(View.VISIBLE);
//                        }
                    }
                }
            });
        }else {
            lookMoreTv.setVisibility(View.GONE);
            commentsAdapter.setNewData(new ArrayList<CommentInfo>());
            commentsAdapter.notifyDataSetChanged();
        }

        if (!TextUtils.isEmpty(item.getCreate_date())){
            helper.setText(R.id.sned_time_tv,item.getCreate_date());
        }

        if (item.isIs_liked()){
            helper.setBackgroundRes(R.id.like_status_iv,R.mipmap.icon_focu);
        }else {
            helper.setBackgroundRes(R.id.like_status_iv,R.mipmap.icon_focus_off);
        }

        if (!TextUtils.isEmpty(item.getLike_count() + "")){
            if (item.getLike_count() < 999){
                helper.setText(R.id.like_tv,item.getLike_count() + "");
            }else if (item.getLike_count() < 9999){
                helper.setText(R.id.like_tv,item.getLike_count()/1000 + "k");
            }else {
                helper.setText(R.id.like_tv,item.getLike_count()/10000 + "w");
            }
        }

        if (item.isIs_collected()){
            helper.setBackgroundRes(R.id.collection_status_iv,R.mipmap.icon_collection);
        }else {
            helper.setBackgroundRes(R.id.collection_status_iv,R.mipmap.icon_collection_off);
        }

       /* if (!TextUtils.isEmpty(item.getMaster_rank() + "")){
            if (item.getMaster_rank() < 999){
                helper.setText(R.id.collection_tv,item.getMaster_rank() + "");
            }else if (item.getLike_count() < 9999){
                helper.setText(R.id.collection_tv,item.getMaster_rank()/1000 + "k");
            }else {
                helper.setText(R.id.collection_tv,item.getMaster_rank()/10000 + "w");
            }
        }*/



        if (!TextUtils.isEmpty(item.getComment_count() + "")){

            if (item.getComment_count() < 999){
                helper.setText(R.id.message_tv,item.getComment_count() + "");
                Log.d(TAG, "convert: yukaida catch"+item.getComment_count());
                if (item.getComment_count() > 0) {
                    Log.d(TAG, "convert: yukaida catch-----"+item.getComment().size());
                }

            }else if (item.getLike_count() < 9999){
                helper.setText(R.id.message_tv,item.getComment_count()/1000 + "k");
            }else {
                helper.setText(R.id.message_tv,item.getComment_count()/10000 + "w");
            }


        }

        if (item.getLiked() != null && item.getLiked().size() > 0 ){
            helper.getView(R.id.liked_rl).setVisibility(View.VISIBLE);
            SpannableStringUtils.Builder builder = SpannableStringUtils.getBuilder("");
            for (int i = 0; i < item.getLiked().size(); i++) {
                int currentPosition = i;
                if (i == item.getLiked().size()-1){
                    builder.append(item.getLiked().get(i).getNickname()).setForegroundColor(mContext.getResources().getColor(R.color.btn_blue_normal)).setClickSpan(new ClickableSpan() {
                        @Override
                        public void updateDrawState(TextPaint ds) {
                            super.updateDrawState(ds);
                            ds.setColor(mContext.getResources().getColor(R.color.btn_blue_normal));       //设置文件颜色
                            ds.setUnderlineText(false);      //设置下划线
                        }

                        @Override
                        public void onClick(@NonNull View widget) {
                            Intent intent = new Intent(mContext, WebActivity.class);
                            intent.putExtra("url", "https://wechat.53iq.com/tmp/kitchen/food/diary?openid=" + item.getLiked().get(currentPosition).getOpenid());
                            mContext.startActivity(intent);
                        }
                    });
                    builder.append("等" + item.getLiked().size() + "人觉得很赞");
                }else {
                    builder.append(item.getLiked().get(i).getNickname() + ",").setForegroundColor(mContext.getResources().getColor(R.color.btn_blue_normal)).setClickSpan(new ClickableSpan() {
                        @Override
                        public void updateDrawState(TextPaint ds) {
                            super.updateDrawState(ds);
                            ds.setColor(mContext.getResources().getColor(R.color.btn_blue_normal));       //设置文件颜色
                            ds.setUnderlineText(false);      //设置下划线
                        }

                        @Override
                        public void onClick(@NonNull View widget) {
                            Intent intent = new Intent(mContext, WebActivity.class);
                            intent.putExtra("url", "https://wechat.53iq.com/tmp/kitchen/food/diary?openid=" + item.getLiked().get(currentPosition).getOpenid());
                            mContext.startActivity(intent);
                        }
                    });
                }
            }
//            addTagToTextView(helper.getView(R.id.liked_username_tv),builder.create().toString(),"等" + item.getLiked().size() + "人觉得很赞");
            helper.setText(R.id.liked_username_tv,builder.create());
            ((TextView)helper.getView(R.id.liked_username_tv)).setMovementMethod(LinkMovementMethod.getInstance());
            //在ListView/RecyclerView中的应用
//            etv.updateForRecyclerView(text, etvWidth, state);//etvWidth为控件的真实宽度，state是控件所处的状态，“收缩”/“伸展”状态
        } else {
            helper.getView(R.id.liked_rl).setVisibility(View.GONE);
        }

        helper.addOnClickListener(R.id.focu_status_iv);
        helper.addOnClickListener(R.id.collection_status_iv);
        helper.addOnClickListener(R.id.like_status_iv);
        helper.addOnClickListener(R.id.share_iv);
        helper.addOnClickListener(R.id.message_iv);
        helper.addOnClickListener(R.id.user_head_iv);
        helper.addOnClickListener(R.id.user_name_tv);
        helper.addOnClickListener(R.id.user_descrbe_tv);
        helper.addOnClickListener(R.id.share_pic_rv);
//        helper.addOnClickListener(R.id.liked_username_tv);
//        helper.addOnClickListener(R.id.liked_user_all_tv);

    }

    public interface CommentDeleteLisenter{
        void deleteComment(CommentInfo commentInfo, String diary_id, int position,int foundPosition);
        void replyComment(CommentInfo commentInfo, String diary_id,int foundPosition);
    }

    public void setCommentDeleteLisenter(CommentDeleteLisenter commentDeleteLisenter) {
        this.commentDeleteLisenter = commentDeleteLisenter;
    }

    private void addTagToTextView(TextView target, String title, String tag) {
        if (TextUtils.isEmpty(title)) {
            title = "";
        }

        String content = title + tag;

        /**
         * 创建TextView对象，设置drawable背景，设置字体样式，设置间距，设置文本等
         * 这里我们为了给TextView设置margin，给其添加了一个父容器LinearLayout。不过他俩都只是new出来的，不会添加进任何布局
         */
        LinearLayout layout = new LinearLayout(mContext);
        TextView textView = new TextView(mContext);
        textView.setText(tag);
//        textView.setBackground(mContext.getResources().getDrawable(R.drawable.icon_search));
        textView.setTextSize(12);
        textView.setTextColor(Color.parseColor("#FDA413"));
        textView.setIncludeFontPadding(false);
        textView.setPadding(ScreenUtils.dip2px(mContext, 6), 0,
                ScreenUtils.dip2px(mContext, 6), 0);
        textView.setHeight(ScreenUtils.dip2px(mContext, 17));
        textView.setGravity(Gravity.CENTER_VERTICAL);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置左间距
        layoutParams.leftMargin = ScreenUtils.dip2px(mContext, 6);
        // 设置下间距，简单解决ImageSpan和文本竖直方向对齐的问题
        layoutParams.bottomMargin = ScreenUtils.dip2px(mContext, 3);
        layout.addView(textView, layoutParams);

        /**
         * 第二步，测量，绘制layout，生成对应的bitmap对象
         */
        layout.setDrawingCacheEnabled(true);
        layout.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        // 给上方设置的margin留出空间
        layout.layout(0, 0, textView.getMeasuredWidth() + ScreenUtils.dip2px(mContext, (6 + 3)), textView.getMeasuredHeight());
        // 获取bitmap对象
        Bitmap bitmap = Bitmap.createBitmap(layout.getDrawingCache());
        //千万别忘最后一步
        layout.destroyDrawingCache();

        /**
         * 第三步，通过bitmap生成我们需要的ImageSpan对象
         */
        ImageSpan imageSpan = new ImageSpan(mContext, bitmap);


        /**
         * 第四步将ImageSpan对象设置到SpannableStringBuilder的对应位置
         */
        SpannableStringBuilder ssb = new SpannableStringBuilder(content);
        //将尾部tag字符用ImageSpan替换
        ssb.setSpan(imageSpan, title.length(), content.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        target.setText(ssb);

        notifyDataSetChanged();
    }

    public void getMoreComment(CommentsAdapter commentsAdapter,String draft_id,TextView lookMoreTv) {//获取更多评论信息-----------------------
        ObserverOnNextListener<CommentInfoMore, Throwable> listener = new ObserverOnNextListener<CommentInfoMore, Throwable>() {
            @Override
            public void onNext(CommentInfoMore commentInfoMore) {//从网络上获取的草稿箱信息
                if (commentInfoMore.getCode() == 0) {


                    List<CommentInfo> data = commentsAdapter.getData();

                    for (int i = 0; i < commentInfoMore.getData().size(); i++) {
                        data.add(commentInfoMore.getData().get(i));

                    }
                    commentsAdapter.setNewData(data);
                    lookMoreTv.setVisibility(View.GONE);
                } else {
                    Toast.makeText(mContext, "未获取到正确数据，请重试", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable throwable) {
                Toast.makeText(mContext, "后台网络异常", Toast.LENGTH_SHORT).show();
            }
        };
        ApiMethods.getMoreComment(new MyObserver<CommentInfoMore>(mContext, listener), "allcomment", draft_id, "3");
    }

}
