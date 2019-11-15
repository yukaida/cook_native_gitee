package com.ebanswers.kitchendiary.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ebanswers.kitchendiary.R;


public class DialogBackTip extends Dialog {

    public DialogBackTip(Context context, int theme) {
        super(context, theme);
    }

    public DialogBackTip(Context context) {
        super(context);
    }

    public static class Builder {
        private Context context;

        private TextView left_btn, right_btn,textView;
        private OnClickListener leftClickListener, rightClickListener;
        private String leftText,rightText,title;


        public Builder(Context context) {
            this.context = context;
        }

        public Builder setTitle(String title){
            this.title = title;
            return this;
        }


        public Builder setLeftText(String leftText){
            this.leftText = leftText;
            return this;
        }


        public Builder setRightText(String rightText){
            this.rightText = rightText;
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


        public DialogBackTip create() {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final DialogBackTip dialog = new DialogBackTip(context, R.style.dialog);
            dialog.getWindow().setBackgroundDrawable(null);
            View layout = inflater.inflate(R.layout.popup_back, null);
            dialog.addContentView(layout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            dialog.setCanceledOnTouchOutside(true);

            textView = ((TextView) layout.findViewById(R.id.text_view));
            left_btn = ((TextView) layout.findViewById(R.id.no_save_tv));
            right_btn = ((TextView) layout.findViewById(R.id.save_tv));

            if (!TextUtils.isEmpty(title)){
                textView.setText(title);
            }


            if (!TextUtils.isEmpty(leftText)){
                left_btn.setText(leftText);
            }

            if (!TextUtils.isEmpty(rightText)){
                right_btn.setText(rightText);
            }

            if (leftClickListener != null) {
                left_btn.setVisibility(View.VISIBLE);
                left_btn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        leftClickListener.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
                        dialog.dismiss();
                    }
                });
            }
            if (rightClickListener != null) {
                right_btn.setVisibility(View.VISIBLE);
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

