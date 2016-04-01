package com.elclcd.multifunctionclock.handler;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by 123 on 2016/4/1.
 */
public class BootCompletedReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action =intent.getAction();
        if(action.equals("android.intent.action.BOOT_COMPLETED")){
            Alarms.resetDo(context.getApplicationContext());
        }
    }
}
