package com.elclcd.multifunctionclock.handler;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.elclcd.multifunctionclock.utils.Constant;

import org.apache.log4j.Logger;

/**
 * Created by 123 on 2016/3/14.
 */
public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Logger log = Logger.getLogger(AlarmReceiver.class);
        log.info("receive AlarmReceiver");
        String action = intent.getAction();
        if (action.equals((Constant.ResetCommand))) {
            Alarms.resetDo(context.getApplicationContext());
        }
    }
}
