package com.ebanswers.kitchendiary.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.ebanswers.kitchendiary.service.CreateRepiceDraftService;
import com.ebanswers.kitchendiary.service.CreateRepiceService;
import com.ebanswers.kitchendiary.utils.SPUtils;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (!TextUtils.isEmpty((String)SPUtils.get("type",""))&&
                ((String)SPUtils.get("type","")).equals("draft")){
            Intent i = new Intent(context, CreateRepiceDraftService.class);
            context.startService(i);
        }else {
            Intent i = new Intent(context, CreateRepiceService.class);
            context.startService(i);
        }

    }
}
