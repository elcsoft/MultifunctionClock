package com.elclcd.multifunctionclock.handler;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.elclcd.multifunctionclock.utils.Constant;

/**
 * Created by 123 on 2016/3/14.
 */
    public class AlarmReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Constant.AlarmReceiverSend)) {
                StringBuilder sb = new StringBuilder("离关机时间还有");
                sb.append(Alarms.WarningTime);
                sb.append("分钟，请即使保存你的工作  ");
                Log.i("test",sb.toString());
                Toast.makeText(context, sb.toString(), Toast.LENGTH_SHORT).show();

            }

        }
}
