package com.elclcd.multifunctionclock.utils;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.elclcd.multifunctionclock.vo.AlarmsConfig;

import java.util.Calendar;

/**
 * Created by elc-06 on 2016/3/14.
 */
public class RemindThread {

    public static AlarmsConfig config = new AlarmsConfig();

    public static boolean start = true;

    public static void remind(final Handler handler) {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i("--start---", "---------" + start);
                while (start) {
                    try {
                        Calendar calendar = Calendar.getInstance();
                        //判断当前时间是否为设定时间前5分钟
//                        calendar.get(Calendar.DAY_OF_WEEK);
                        Log.i("week",Calendar.MONDAY+"-----------"+calendar.get(Calendar.DAY_OF_WEEK));
                        if (calendar.getTime().getMinutes() == config.getPowerOffTime().getMinute()  && calendar.getTime().getHours() == config.getPowerOffTime().getHour() && config.isEnabled()&&config.getDayWeek()[calendar.get(Calendar.DAY_OF_WEEK)-1]) {

                            //弹出提示对话框
                            Message message = handler.obtainMessage();
                            handler.sendMessage(message);
                            Thread.sleep(1000 * 60);//休眠5秒
                        } else {
                            Thread.sleep(1000 * 5);//休眠5秒
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
        thread.start();
    }


}
