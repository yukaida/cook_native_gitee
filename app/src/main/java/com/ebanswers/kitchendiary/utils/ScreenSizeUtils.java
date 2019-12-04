package com.ebanswers.kitchendiary.utils;

import android.content.Context;

/**
 * @author
 * Created by lishihui on 2017/1/9.
 */

public class ScreenSizeUtils {

    public static int getScreenWidth(Context context){
        return context.getResources().getDisplayMetrics().widthPixels;
    }
    public static int getScreenHeight(Context context){
        return context.getResources().getDisplayMetrics().heightPixels;
    }
}
