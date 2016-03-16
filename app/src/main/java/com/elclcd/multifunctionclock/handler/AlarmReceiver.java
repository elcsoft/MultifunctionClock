package com.elclcd.multifunctionclock.handler;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.elclcd.multifunctionclock.utils.Constant;

/**
 * Created by 123 on 2016/3/14.
 */
    public class AlarmReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
           if(action.equals((Constant.ResetCommand))){
               Log.i("test","222");
                Alarms.resetDo();
            }



        }


}
