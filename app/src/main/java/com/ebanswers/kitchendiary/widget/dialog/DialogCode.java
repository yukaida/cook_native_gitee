package com.ebanswers.kitchendiary.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ebanswers.kitchendiary.R;
import com.ebanswers.kitchendiary.utils.GlideApp;
import com.ebanswers.kitchendiary.utils.Utils;
import com.ebanswers.kitchendiary.utils.ZXingUtils;


public class DialogCode extends Dialog {

    public DialogCode(Context context, int theme) {
        super(context, theme);
    }

    public DialogCode(Context context) {
        super(context);
    }

    public static class Builder {
        private Context context;
        private String leftStr, rightStr;
        private String path;

        private TextView left_btn, right_btn;
        private ImageView  codeIv;
        private LinearLayout payCodeLl;
        private OnClickListener  rightClickListener;
        private LeftButtonListener leftClickListener;


        public Builder(Context context,String path) {
            this.context = context;
            this.path = path;

        }

        public Builder setTitle(String path) {
            this.path = path;
            return this;
        }


        public Builder setLeftBtnStr(String leftStr) {
            this.leftStr = leftStr;
            return this;
        }

        public Builder setRightBtnStr(String rightStr) {
            this.rightStr = rightStr;
            return this;
        }

        public Builder setLeftClickListener(LeftButtonListener listener) {
            this.leftClickListener = listener;
            return this;
        }

        public Builder setRightClickListener(OnClickListener listener) {
            this.rightClickListener = listener;
            return this;
        }


        public DialogCode create() {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final DialogCode dialog = new DialogCode(context, R.style.dialog);
            dialog.getWindow().setBackgroundDrawable(null);
            View layout = inflater.inflate(R.layout.popup_code_screen, null);
            dialog.addContentView(layout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));

            codeIv = ((ImageView) layout.findViewById(R.id.qr_code_iv));
            left_btn = ((TextView) layout.findViewById(R.id.dialog_base_left_tv));
            right_btn = ((TextView) layout.findViewById(R.id.dialog_base_right_tv));
            payCodeLl = ((LinearLayout) layout.findViewById(R.id.pay_code_ll));

            if (!TextUtils.isEmpty(path)) {

                Bitmap bitmap = ZXingUtils.createQRImage(path, 400,  400);
                GlideApp.with(context)
                        .load(bitmap)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .dontAnimate()
                        .placeholder(R.drawable.empty)
                        .into(codeIv);
            }


            if (leftClickListener != null) {
                left_btn.setVisibility(View.VISIBLE);
                left_btn.setText(leftStr);
                left_btn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        boolean isDown = getBitmap(payCodeLl);
                        leftClickListener.downloadStatus(dialog,isDown);
                    }
                });
            }
            if (rightClickListener != null) {
                right_btn.setVisibility(View.VISIBLE);
                right_btn.setText(rightStr);
                right_btn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        rightClickListener.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
                        dialog.dismiss();
                    }
                });
            }

            dialog.setContentView(layout);
            return dialog;
        }

        /*
         * 方法二
         * */
        private boolean getBitmap(View view) {
            //先进行测量，这样不会获取为null   //变形的厉害
          /*  view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
            view.buildDrawingCache();*/
//            Bitmap bitmap = view.getDrawingCache();
            //高保真图片
            Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),
                    Bitmap.Config.ARGB_8888); //创建一个和View大小一样的Bitmap
            Canvas canvas = new Canvas(bitmap);  //使用上面的Bitmap创建canvas
            view.draw(canvas);  //把View画到Bitmap上
            //假如图片不符合要求，可以使用Bitmap.createBitmap( )方法处理图片
            return Utils.savePictrue(context, bitmap);

        }

        public interface LeftButtonListener{
            void downloadStatus(DialogInterface dialogInterface,boolean b);
        }
    }




}

