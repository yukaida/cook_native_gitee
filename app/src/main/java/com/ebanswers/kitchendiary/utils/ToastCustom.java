package com.ebanswers.kitchendiary.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ebanswers.kitchendiary.R;
import com.ebanswers.kitchendiary.common.CommonApplication;


/**
 * Created by air on 2017/9/2.
 */

public class ToastCustom {
    public static final int LENGTH_SHORT = Toast.LENGTH_SHORT;
    public static final int LENGTH_LONG = Toast.LENGTH_LONG;

    Toast toast;
    Context mContext;
    TextView toastTextField;
    private static ToastCustom toastCustom;

    public ToastCustom(Context context) {
        mContext = context;
        toast = new Toast(mContext);
        toast.setGravity(Gravity.CENTER,0,0);// 位置会比原来的Toast偏上一些
        View toastRoot = View.inflate(context, R.layout.toast_view, null);
        toastTextField = (TextView) toastRoot.findViewById(R.id.toast_text);
        toast.setView(toastRoot);
    }

    public void setDuration(int d) {
        toast.setDuration(d);
    }

    public void setText(String t) {
        toastTextField.setText(t);
    }

    public static ToastCustom makeText(Context context, String text, int duration) {
//        if(toastCustom == null ) {
//        }
        toastCustom = new ToastCustom(context);
        toastCustom.setText(text);
        toastCustom.setDuration(duration);
        return toastCustom;
    }


    public static ToastCustom makeText(Context context, int textid, int duration) {
//        if(toastCustom == null ) {
//        }
        toastCustom = new ToastCustom(context);
        toastCustom.setText(context.getResources().getString(textid));
        toastCustom.setDuration(duration);
        return toastCustom;
    }

    public static ToastCustom makeText(String text) {
        return makeText(CommonApplication.getInstance(), text, ToastCustom.LENGTH_SHORT);
    }

    public static ToastCustom makeText(int text) {
        return makeText(CommonApplication.getInstance(), text, ToastCustom.LENGTH_SHORT);
    }

    public void show() {
        toast.show();
    }

}
