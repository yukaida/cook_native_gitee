package com.ebanswers.kitchendiary.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hjq.toast.ToastUtils;
import com.ebanswers.kitchendiary.R;


public class DialogSelect extends Dialog {

    public DialogSelect(Context context, int theme) {
        super(context, theme);
    }

    public DialogSelect(Context context) {
        super(context);
    }

    public static class Builder {
        private Context context;
        private String title,content, leftStr, rightStr;

        private TextView left_btn, right_btn;
        private TextView  titleTv;
        private LinearLayout btnll;
        private OnClickListener leftClickListener, rightClickListener;
        private EditText urlContent;
//        private RadioGroup urlRg;


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


        public DialogSelect create() {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final DialogSelect dialog = new DialogSelect(context, R.style.dialog);
            dialog.getWindow().setBackgroundDrawable(null);
            View layout = inflater.inflate(R.layout.view_dialog_select, null);
            dialog.addContentView(layout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            titleTv = ((TextView) layout.findViewById(R.id.title_tv));
            urlContent = ((EditText) layout.findViewById(R.id.url_content));
//            urlRg = ((RadioGroup) layout.findViewById(R.id.url_rg));
            left_btn = ((TextView) layout.findViewById(R.id.dialog_base_left_tv));
            right_btn = ((TextView) layout.findViewById(R.id.dialog_base_right_tv));
            btnll = ((LinearLayout) layout.findViewById(R.id.dialog_base_btnll));

            if (!TextUtils.isEmpty(title)) {
                titleTv.setText(title);
                titleTv.setVisibility(View.VISIBLE);
            }

          /*  urlRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId){
                        case R.id.first:
                            content = "http://172.19.201.141/";
                            break;
                        case R.id.second:
                            content = "http://172.19.201.142/";
                            break;
                        case R.id.third:
                            content = "https://uniccs.cn/";
                            break;
                        case R.id.forth:
                            content = "http://uniccs.cn/";
                            break;
                        default:
                            content = "http://172.19.201.141/";
                            break;

                    }
                }
            });*/

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
                        if (lisenter != null){
                            content = urlContent.getText().toString().trim();
                            if (content != null && content.length() > 0){
                                if (content.startsWith("http")  || content.startsWith("https")){
                                    lisenter.selectString(content);
                                    dialog.dismiss();
                                }else {
                                    ToastUtils.show("'http' or 'https' but no colon was found");
                                }
                            }else {
                                ToastUtils.show("url is null");
                            }

                        }
                    }
                });
            }

            dialog.setContentView(layout);
            return dialog;
        }

        public interface Lisenter{
            void selectString(String content);
        }

        private Lisenter lisenter;

        public void setLisenter(Lisenter lisenter) {
            this.lisenter = lisenter;
        }

    }


}

