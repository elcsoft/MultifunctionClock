package com.elclcd.multifunctionclock.handler;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.elclcd.multifunctionclock.utils.Constant;
import com.elclcd.multifunctionclock.WarningTimeDialogActivity;

/**
 * Created by 123 on 2016/3/14.
 */
    public class AlarmReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Constant.AlarmReceiverSend)) {
                Log.i("test", "111");

                Intent intent1=new Intent(context, WarningTimeDialogActivity.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                context.startActivity(intent1);

            }

        }


}
