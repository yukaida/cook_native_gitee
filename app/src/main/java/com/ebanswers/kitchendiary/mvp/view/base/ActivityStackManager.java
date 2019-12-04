package com.ebanswers.kitchendiary.mvp.view.base;

import androidx.fragment.app.FragmentActivity;

import java.util.ArrayList;

/**
 * @author caixd
 * @date 2019/2/21
 * PS:
 */
public class ActivityStackManager {

    private static ArrayList<FragmentActivity> mActivityList = new ArrayList<>();

    public static void add(FragmentActivity activity) {
        if (!mActivityList.contains(activity)) {
            mActivityList.add(activity);
        }
    }

    public static void remove(FragmentActivity activity) {
        if (mActivityList.contains(activity)) {
            mActivityList.remove(activity);
        }
    }

    public static FragmentActivity getLastActivity() {
        if (mActivityList.size() > 0) {
            return mActivityList.get(mActivityList.size() - 1);
        }
        return null;
    }

    public static void exit() {
        for (FragmentActivity activity : mActivityList) {
            if (activity != null) {
                activity.finish();
            }
        }
        mActivityList.clear();
        System.exit(0);
    }

}
