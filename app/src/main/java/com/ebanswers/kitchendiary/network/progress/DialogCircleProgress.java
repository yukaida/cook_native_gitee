package com.ebanswers.kitchendiary.network.progress;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.StyleRes;

import com.ebanswers.kitchendiary.R;


/**
 * Created by Administrator on 2018/1/26.
 */

public class DialogCircleProgress extends Dialog {

    public DialogCircleProgress(Context context) {
        super(context);
    }

    public DialogCircleProgress(Context context, @StyleRes int theme) {
        super(context, theme);
    }

    public static class Builder {

        private Context context;
        private boolean canceloutside = false;
        private boolean cancelable = true;

        //默认参数
        private int dialogSize = 120;
        private int progressColor = Color.GRAY;
        private int progressWidth = 6;
        private int shadowOffset = 1;
        private int textColor = Color.GRAY;
        private String text = "LOADING NOW";

        private ProgressBar progress;
        private boolean isShowing = false;

        private CircleProgressListener listener;
        private DialogCircleProgress mDialog;
        private AnimationDrawable animationDrawable;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setCancelable(boolean isCancelable) {
            if (mDialog != null) {
                mDialog.setCancelable(isCancelable);
            }
            return this;
        }

        public Builder dismiss() {
            if (mDialog != null) {
                SystemClock.sleep(200);
                try {
                    mDialog.dismiss();
                } catch (Exception e) {
                }
                if (listener != null) {
                    listener.onCancel();
                }
                isShowing = false;
            }
            return this;
        }

        public boolean isShowing() {
            return isShowing;
        }

        public Builder setDialogSize(int dialogSize, Context context) {
            if (context != null) {
                float scale = context.getResources().getDisplayMetrics().density;
                this.dialogSize = (int) (dialogSize * scale + 0.5f);
            }
            return this;
        }

        public Builder setProgressColor(int progressColor) {
            this.progressColor = progressColor;
            return this;
        }

        public Builder setProgressWidth(int progressWidth) {
            this.progressWidth = progressWidth;
            return this;

        }

        public Builder setText(String text) {
            this.text = text;
            return this;
        }

        public Builder setTextColor(int textColor) {
            this.textColor = textColor;
            return this;
        }

        public Builder setShadowPosition(int shadowPosition) {
            this.shadowOffset = shadowPosition;
            return this;
        }

        public interface CircleProgressListener {
            void onCancel();
        }

        public DialogCircleProgress create() {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            mDialog = new DialogCircleProgress(context, R.style.dialog);
            mDialog.getWindow().setBackgroundDrawable(null);
            View inflate = inflater.inflate(R.layout.dialog_circle_progress, null);
            mDialog.addContentView(inflate, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            mDialog.setCanceledOnTouchOutside(canceloutside);
            mDialog.setCancelable(cancelable);

            progress = (ProgressBar) mDialog.findViewById(R.id.loading_progress);
          /*  LinearLayout layout = (LinearLayout) mDialog.findViewById(R.id.llProgress);
            layout.setLayoutParams(new LinearLayout.LayoutParams(dialogSize, dialogSize));*/

            isShowing = true;
            return mDialog;
        }

        public CircleProgressListener getListener() {
            return listener;
        }

        public void setListener(CircleProgressListener listener) {
            this.listener = listener;
        }
    }

}
