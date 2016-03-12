package com.elclcd.multifunctionclock.handler;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.util.Log;
import android.widget.TimePicker;

import com.elclcd.multifunctionclock.MainActivity;
import com.elclcd.multifunctionclock.utils.CmdExecuter;
import com.elclcd.multifunctionclock.vo.AlarmsConfig;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import static com.elclcd.multifunctionclock.vo.AlarmsConfig.*;

/**
 * Created by 123 on 2016/3/10.
 */
public class Alarms {

    private static String onCommand;//开机时间
//    private static AlarmsConfig  config1;

    private static String[] weeks={"monday","tuesday","wednesday","thursday","friday","staturday","sunday"};

    /**
     * 保存配置
     *
     * @param context
     * @param config
     */
    public static void saveConfig(Context context, AlarmsConfig config) {
//resetConfig();、
        SharedPreferences sharePre=context.getSharedPreferences("times", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor =sharePre.edit();
        editor.putBoolean("checkbox", config.isEnabled());

        for (int i=0;i<weeks.length;i++){
            editor.putBoolean(weeks[i],config.getDayWeek()[i]);
        }

        editor.putInt("timeOnHour", config.getPowerOnTime().getHour());
        editor.putInt("timeOnMinute", config.getPowerOnTime().getMinute());
        editor.putInt("timeOffHour", config.getPowerOffTime().getHour());
        editor.putInt("timeOffMinute", config.getPowerOffTime().getMinute());

        editor.commit();

        resetConfig(context,config);



        //resetConfig();、
    }

    /**
     * 获取定时开关机配置
     *
     * @param context
     * @return
     */
    public static AlarmsConfig getConfig(Context context) {
        boolean[] week=new boolean[7];

        
        SharedPreferences sharePre=context.getSharedPreferences("times", Context.MODE_PRIVATE);

        AlarmsConfig alarmsConfig = new AlarmsConfig();
        alarmsConfig.setEnablen(sharePre.getBoolean("checkbox", false));
        for (int i=0;i<weeks.length;i++){
            week[i]=sharePre.getBoolean(weeks[i],false);
        }
        alarmsConfig.setDayWeek(week);
        AlarmsConfig.TimePoint timePoint=new AlarmsConfig.TimePoint();
        timePoint.setHour(sharePre.getInt("timeOnHour", 0));
        timePoint.setMinute(sharePre.getInt("timeOnMinute", 0));
        alarmsConfig.setPowerOnTime(timePoint);
        AlarmsConfig.TimePoint timePoint2=new AlarmsConfig.TimePoint();
        timePoint2.setHour(sharePre.getInt("timeOffHour", 0));
        timePoint2.setMinute(sharePre.getInt("timeOffMinute", 0));
        alarmsConfig.setPowerOffTime(timePoint2);

        return alarmsConfig;

    }

    /**
     * 重置配置，更新开关机服务
     *
     * @param context
     * @param config
     * @return 返回程序的状态，true 执行 ，false 不执行
     */
    public static String resetConfig(Context context, AlarmsConfig config) {
        //TODO 计算时间
        //TODO 生成命令
        //开机关机
//        Log.e("", "resetConfig " + config);
//        Log.e("", "result： " + config);
//        if(true)
//            return false;
//        config1=config;
        AlarmsConfig.TimePoint timeOn = config.getPowerOnTime();
        AlarmsConfig.TimePoint timeOff = config.getPowerOffTime();
//        boolean b = judgmentTimeStyle(timeOn, timeOff);
//        if (b == true) {//开始时间小于结束时间
////            OpenAlarm(context, timeOn, config);
//
//        }
        String onCommand = getTime(timeOn, config, 0);//正常无额外添加天数
        String offCommand = getTime(timeOff, config,0);
        boolean b=judgmentTimeStyle(onCommand,offCommand);
        if(b==true){
            int n=getDiffentDay(offCommand);
            Log.i("test","n:"+n);
            onCommand=getTime(timeOn, config, n);
            Log.i("test","onCommand1:"+onCommand);
        }
        Log.i("test","onCommand:"+onCommand);
        String command = getCommond(onCommand, offCommand);
        Log.i("test", command);
//        CmdExecuter executer = new CmdExecuter();
//        Boolean b1=config.isEnabled();
//        if(b1==true){
//            executer.exec(command);
////            return  true;
//        }
//        return  false;

        Log.i("-----------",command);

        return command;
    }

    private static int getDiffentDay(String offCommand){
        Calendar c =Calendar.getInstance();
        int currentday = c.get(Calendar.DAY_OF_MONTH);
        int  closeday =Integer.parseInt(offCommand.substring(6, 8));
        int diffent=closeday-currentday;
        return diffent;
    }

    //判断时间格式是否正确，保证先关机，后开机
    private static boolean judgmentTimeStyle(String onCommand, String offCommand) {
//        int openHour = timeOn.getHour();
//        int openMintue = timeOn.getMinute();
//        int closedHour = timeOff.getHour();
//        int closeMintue = timeOff.getMinute();
//        //如果开机时间小于关机时间，只有开 当 关 这种情况是正确的
//        if (openHour < closedHour) {
//            return true;
//        } else if (openHour == closedHour) {
//            if (openMintue < closeMintue) {
//                return true;
//            }
//        }
        Log.i("test",onCommand);
        Log.i("test",offCommand);
        int [] open=commandToTime(onCommand);//201603150102
        int [] close=commandToTime(offCommand);//201603151102
        boolean b=Compare(open,close);

        return b;
    }

    //判断开始命令的时间是不是小于关闭命令的时间
    private static boolean Compare(int[] open, int[] close) {
        if (open[0]<close[0]){
            return  true;
        }
        else if(open[0]==close[0]){
            if(open[1]<close[1]){
                return true;
            }
            else if(open[1]==close[1]){
                if(open[2]<close[2]){
                    return true;
                }
                else if(open[2]==close[2]){
                    if(open[3]<close[3]){
                        return  true;
                    }
                    else if(open[3]==close[3]){
                        if(open[4]<=close[4]){
                            return true;
                        }
                        else {
                            return false;
                        }
                    }
                    else {
                        return  false;
                    }
                }
                else {
                    return false;
                }
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
    }

    //把字符串拆成年月日等的整形
    private static int[] commandToTime(String command) {
        int[] time = new int[5];
        int year = Integer.parseInt(command.substring(0, 4));
        int moonth = Integer.parseInt(command.substring(4, 6));
        int day = Integer.parseInt(command.substring(6, 8));
        int hour = Integer.parseInt(command.substring(8, 10));
        int mintue = Integer.parseInt(command.substring(10));
        time[0] = year;
        time[1] = moonth;
        time[2] = day;
        time[3] = hour;
        time[4] = mintue;
        return time;
    }


    private static String getTime(AlarmsConfig.TimePoint time, AlarmsConfig config,int n) {
        Calendar c = Calendar.getInstance();
        int[] time1 = getDate(time, config, n);
        String year = Chick(time1[0]);
        String month = Chick(time1[1]);
        String day = Chick(time1[2]);
        String hour = Chick(time.getHour());
        String minute = Chick(time.getMinute());
        StringBuffer sb = new StringBuffer(year);
        sb.append(month);
        sb.append(day);
        sb.append(hour);
        sb.append(minute);
        return sb.toString();

    }


    //当开机时间大于关机时间是为0，当开机时间小于关机时间时为1；
    /*
    参数n是关机时间与今天相差的天数，当开始命令的时间小于关闭命令的时间的时候，需要从关闭后的第二天
    开始找到最新的开机时间
    */
    private static int[] getDate(AlarmsConfig.TimePoint time, AlarmsConfig config,int n) {
        Calendar c = Calendar.getInstance();
        int[] times = new int[3];
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DAY_OF_MONTH);
        int discrepancy = findRightDay(config,n);
        times[0] = year;
        times[1] = month;
        Log.i("test",discrepancy+" d");
        if (discrepancy == 0) {
            Boolean b = isNeedToTomorrow(time);
            if (b == true) {
                //如果今天设定的时间小于当前的时间，则用明天设定的时间来计算差异时间
//                times[2]=day+1;

                int discrepancy1 = findRightDay(config,1+n);
                Log.i("test",discrepancy1+" d1");
                times[2] = day+1+discrepancy1+n;

            } else {
                times[2] = day+n;
            }
        }
        else {
            times[2]=day+discrepancy+n;
        }
        return times;
    }

    //设置格式变成OX
    private static String Chick(int intchick) {

        if (intchick < 10) {
            StringBuffer sb = new StringBuffer("0");
            sb.append(String.valueOf(intchick));
            return sb.toString();
        }
        return String.valueOf(intchick);
    }

    private static String getCommond(String timeOn, String timeOff) {
        StringBuffer sb = new StringBuffer("");
        sb.append("/system/xbin/test ");
        sb.append(timeOff);
        sb.append(" ");
        sb.append(timeOn);
        sb.append(" ");
        sb.append("enable");
        return sb.toString();
    }

    //判断是否时间需要重置到明天
    private static Boolean isNeedToTomorrow(AlarmsConfig.TimePoint time) {
        Calendar c = Calendar.getInstance();
        int hour = time.getHour();
        int mintue = time.getMinute();
        int currentHour = c.get(Calendar.HOUR_OF_DAY);
        int currentMintue = c.get(Calendar.MINUTE);
        if (hour > currentHour) {
            return false;
        } else if (hour < currentHour) {
            return true;
        } else {
            if (mintue > currentMintue) {
                return false;
            } else {
                return true;
            }
        }
    }

    //判断最近要开关机的一天与今天的相差数
    private static int findRightDay(AlarmsConfig config,int  addtime) {
        Calendar c = Calendar.getInstance();
        int todayIndex = c.get(Calendar.DAY_OF_WEEK) - 2+addtime;//今天的星期在数组中的下标
        if (todayIndex >= 7) {
            todayIndex -= 7;
        }
        boolean[] b = config.getDayWeek();
        int index ;
        for (index = 0; index < 8; index++) {
            if (b[todayIndex] == true) {
                break;
            } else {
                todayIndex++;
                if (todayIndex >= 7) {
                    todayIndex -=7;
                }
            }
        }
        return index;
    }

//    //  定时发送广播激活服务
//    private static void OpenAlarm(Context context, AlarmsConfig.TimePoint timeOn, AlarmsConfig config) {
//        Calendar c = Calendar.getInstance();
//        long currenttime = c.getTimeInMillis();
//        String onCommand = getTime(timeOn, config);
//        SimpleDateFormat f = new SimpleDateFormat("yyyyMMddHHmm");
//        try {
//            Date d = f.parse(onCommand);
//            long longDate = d.getTime();
//            long cha = longDate - currenttime;//时间的差
//
//            AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//            Intent intent = new Intent();
//            int requestCode = 0;
//            PendingIntent pendIntent = PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//            // 30秒后发送广播，然后每个60秒重复发广播。广播都是直接发到AlarmReceiver的
//            int triggerAtTime = (int) (SystemClock.elapsedRealtime() + cha);
//            Log.v("321", "运行了。");
//            alarmMgr.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, 0, pendIntent);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//    }

//    //设置更改逻辑使程序保持关机 开机
//    private void setRightTimeStyle(){
//        String time1 = onCommand.substring(0, 6);
//        int openday = Integer.parseInt(onCommand.substring(6, 8));
//        String time2 = onCommand.substring(8);
//        int discrepancy1 = findRightDay(config1,1);
//        openday = openday+1 + discrepancy1;
//        StringBuilder sb =new StringBuilder(time1);
//        sb.append(Chick(openday));
//        sb.append(time2);
//        onCommand=sb.toString();
//
//    }
//
//
//
//    //开机广播
//    public class MyBroadcast extends BroadcastReceiver {
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            // TODO Auto-generated method stub
//            setRightTimeStyle();
//        }
//
//
//    }
//
//    public class  AlarmBroadcastReceiver extends  BroadcastReceiver{
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            setRightTimeStyle();
//        }
//    }

}
