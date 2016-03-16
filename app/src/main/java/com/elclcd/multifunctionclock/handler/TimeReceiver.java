package com.elclcd.multifunctionclock.handler;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.elclcd.multifunctionclock.vo.AlarmsConfig;

/**
 * Created by 123 on 2016/3/16.
 */
public class TimeReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        AlarmsConfig config=Alarms.getConfig(context);
            Alarms.resetConfig(context,config);

    }
}
