package com.ebanswers.kitchendiary.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.ebanswers.kitchendiary.service.CreateRepiceService;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, CreateRepiceService.class);
        context.startService(i);
    }
}
