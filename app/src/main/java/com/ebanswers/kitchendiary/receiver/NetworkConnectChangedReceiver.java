package com.ebanswers.kitchendiary.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.ebanswers.kitchendiary.eventbus.Event;
import com.ebanswers.kitchendiary.utils.NetworkUtils;

import org.greenrobot.eventbus.EventBus;

public class NetworkConnectChangedReceiver extends BroadcastReceiver {

    private static final String TAG = "NetworkConnectChanged";

    @Override
    public void onReceive(Context context, Intent intent) {
        //**判断当前的网络连接状态是否可用*/
        boolean isConnected = NetworkUtils.isNetworkAvailable(context);
        Log.d(TAG, "onReceive: 当前网络 " + isConnected);
        EventBus.getDefault().post(new Event(Event.EVENT_NET,"当前网络不可用！"));
    }

}
