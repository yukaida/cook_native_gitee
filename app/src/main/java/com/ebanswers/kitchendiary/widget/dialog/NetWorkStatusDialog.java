package com.ebanswers.kitchendiary.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ebanswers.kitchendiary.R;


/**
 * 网络请求错误时弹框
 */
public class NetWorkStatusDialog extends Dialog {
    public NetWorkStatusDialog(@NonNull Context context) {
        super(context);
    }

    public NetWorkStatusDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected NetWorkStatusDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public static class Builder {

        private Context context;
        private boolean canceloutside = false;//调用这个方法时，按对话框以外的地方不起作用。按返回键还起作用
        private boolean cancelable = true;     //调用这个方法时，按对话框以外的地方不起作用。按返回键也不起作用
        private TextView titleTv;
        private TextView contentTv;
        private Button positiveButton;
        private String title = "", content = "", rightStr = "";
        private OnClickListener rightClicklistener;

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

        public Builder setRightStr(String rightStr) {
            this.rightStr = rightStr;
            return this;
        }

        public Builder setRightClicklistener(OnClickListener rightClicklistener) {
            this.rightClicklistener = rightClicklistener;
            return this;
        }

        public NetWorkStatusDialog create() {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            final NetWorkStatusDialog baseDialog = new NetWorkStatusDialog(context, R.style.dialog);
            baseDialog.getWindow().setBackgroundDrawable(null);
            View inflate = inflater.inflate(R.layout.dialog_network_status, null);
            baseDialog.addContentView(inflate, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            baseDialog.setCanceledOnTouchOutside(canceloutside);
            baseDialog.setCancelable(cancelable);

            titleTv = ((TextView) inflate.findViewById(R.id.dialog_base_title_tv));
            contentTv = ((TextView) inflate.findViewById(R.id.dialog_content_tv));
            positiveButton = ((Button) inflate.findViewById(R.id.positiveButton));

            if (title != null) {
                titleTv.setText(title);
            }

            if (content != null) {
                contentTv.setText(content);
            }

            if (rightStr != null) {
                positiveButton.setText(rightStr);
            }

            if (rightClicklistener != null) {
                positiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rightClicklistener.onClick(baseDialog, DialogInterface.BUTTON_POSITIVE);
                        baseDialog.dismiss();
                    }
                });
            }
            return baseDialog;
        }
    }
}
