package com.ebanswers.kitchendiary.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.ebanswers.kitchendiary.R;


/**
 * @author Created by lishihui on 2017/1/9.
 */

public class DialogUtils {

    private static AlertDialog timeoutDialog;
    private static AlertDialog alarmDialog;
    private static TextView textView;
    private static TextView noticeView;

    public static Dialog createNoInteraction(Activity activity, int id) {
        AlertDialog dialog = new AlertDialog.Builder(activity).create();
        dialog.show();
        Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setContentView(id);
        window.setGravity(Gravity.CENTER);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    public static Dialog createNoInteraction(Activity activity, String content) {
        AlertDialog dialog = new AlertDialog.Builder(activity).create();
        dialog.show();
        Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setContentView(R.layout.dialog_wait_loading);
        View view1 = window.getDecorView();
        if (view1 != null && view1.findViewById(R.id.id_tv_wait_content) != null) {
            TextView textView = (TextView) view1.findViewById(R.id.id_tv_wait_content);
            textView.setText(content);
        }
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    public static Dialog createInteraction(Activity activity, View view) {
        AlertDialog dialog = new AlertDialog.Builder(activity).create();
        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_PANEL);
        dialog.show();
        Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setContentView(view);
        window.setGravity(Gravity.CENTER);
        window.setLayout(ScreenSizeUtils.getScreenWidth(activity), ScreenSizeUtils.getScreenHeight(activity));
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    public static Dialog createInteractionDialog(Activity activity, View view) {
        Dialog dialog = new Dialog(activity, R.style.TransparentDialog);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout((int) (ScreenSizeUtils.getScreenWidth(activity) * 0.8), WindowManager.LayoutParams.WRAP_CONTENT);
        return dialog;
    }



    public interface OnSaveCallBack {
        void onSave(boolean isSave);
    }


    public interface OnBindCallBack {
        void onBind(int position);
    }

    private static int dp2px(Context context, int size) {
        return (int) (size * context.getResources().getDisplayMetrics().density + 0.5);
    }

}
