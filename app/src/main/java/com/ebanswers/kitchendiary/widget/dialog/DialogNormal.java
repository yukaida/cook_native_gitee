package com.ebanswers.kitchendiary.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ebanswers.kitchendiary.R;


public class DialogNormal extends Dialog {

    public DialogNormal(Context context, int theme) {
        super(context, theme);
    }

    public DialogNormal(Context context) {
        super(context);
    }

    public static class Builder {
        private Context context;
        private String title,content, leftStr, rightStr;

        private TextView left_btn, right_btn;
        private TextView  subtitle_tv,titleTv;
        private LinearLayout btnll;
        private OnClickListener leftClickListener, rightClickListener;


        public Builder(Context context) {
            this.context = context;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setContent(String content) {
            this.content = content;
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

        public Builder setLeftClickListener(OnClickListener listener) {
            this.leftClickListener = listener;
            return this;
        }

        public Builder setRightClickListener(OnClickListener listener) {
            this.rightClickListener = listener;
            return this;
        }


        public DialogNormal create() {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final DialogNormal dialog = new DialogNormal(context, R.style.dialog);
            dialog.getWindow().setBackgroundDrawable(null);
            View layout = inflater.inflate(R.layout.view_dialog_normal, null);
            dialog.addContentView(layout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

            titleTv = ((TextView) layout.findViewById(R.id.title_tv));
            subtitle_tv = ((TextView) layout.findViewById(R.id.dialog_content_tv));
            left_btn = ((TextView) layout.findViewById(R.id.dialog_base_left_tv));
            right_btn = ((TextView) layout.findViewById(R.id.dialog_base_right_tv));
            btnll = ((LinearLayout) layout.findViewById(R.id.dialog_base_btnll));

            if (!TextUtils.isEmpty(title)) {
                titleTv.setText(title);
                titleTv.setVisibility(View.VISIBLE);
            }


            if (!TextUtils.isEmpty(content)) {
                subtitle_tv.setText(content);
                subtitle_tv.setVisibility(View.VISIBLE);
            }

            if (leftClickListener != null || rightClickListener != null) {
                btnll.setVisibility(View.VISIBLE);
            }

            if (leftClickListener != null) {
                left_btn.setVisibility(View.VISIBLE);
                left_btn.setText(leftStr);
                left_btn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        leftClickListener.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
                        dialog.dismiss();
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

    }

}

