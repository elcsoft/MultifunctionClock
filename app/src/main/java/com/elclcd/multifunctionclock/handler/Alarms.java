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
import android.widget.Toast;

import com.elclcd.multifunctionclock.MainActivity;
import com.elclcd.multifunctionclock.utils.CmdExecuter;
import com.elclcd.multifunctionclock.utils.Constant;
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

//    private static AlarmsConfig  config1;
    private static String[] weeks={"monday","tuesday","wednesday","thursday","friday","staturday","sunday"};
    private static String offCommand;//关机命令
    public static  final int WarningTime=1;//警告时间
    private static String onCommand;//开机命令
    private static Context context1;
    private static AlarmsConfig config1;

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

        resetConfig(context, config);



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

    //发送定时广播 是警告时间
    private   static void setAlarmManger(Context context,boolean isneed){
        Intent intent1=new Intent(Constant.WarnTime);
        Intent intent2=new Intent(Constant.ResetCommand);
        PendingIntent sender1 = PendingIntent.getBroadcast(context, 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent sender2 = PendingIntent.getBroadcast(context, 0, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        SimpleDateFormat f = new SimpleDateFormat("yyyyMMddHHmm");
        try {
            if(isneed==true){
                String time=getTheWarningTime();
                Date date =f.parse(time);
                long d=date.getTime();
                Log.i("test", String.valueOf(d));
                Calendar calendar=Calendar.getInstance();
                calendar.setTime(date);
                manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender1);

            }
            else{
                Date date =f.parse(onCommand);
                Calendar calendar=Calendar.getInstance();
                calendar.setTime(date);
                manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender2);
            }
//            Calendar calendar=Calendar.getInstance();
////            calendar.setTimeInMillis(System.currentTimeMillis());//参数是毫秒值
////		    calendar.add(Calendar.SECOND,5);
//            long ct=System.currentTimeMillis();
//            Log.i("test",String.valueOf(ct));

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    private static String getTheWarningTime() {
        String time1 = offCommand.substring(0, 10);
        int mintue = Integer.parseInt(offCommand.substring(10));
        mintue-=WarningTime;
        StringBuilder sb=new StringBuilder(time1);
        sb.append(mintue);
        return  sb.toString();
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
        config1=config;
        context1=context;
        AlarmsConfig.TimePoint timeOn = config.getPowerOnTime();
        AlarmsConfig.TimePoint timeOff = config.getPowerOffTime();
        boolean weeks[] =config.getDayWeek();
        boolean b1=NoWeekChoosed(weeks);
        if(b1==true){
            Log.i("test","您还没有选星期，请记得选择");
            return "您还没有选择星期，请记得选择";
        }
        else {
            onCommand = getTime(timeOn, config);//正常无额外添加天数
            offCommand = getTime(timeOff, config);
            boolean b2 =judgmentTimeStyle(onCommand, offCommand);
            if(b2==true){
                setAlarmManger(context,false);
            }
            String command = getCommond(onCommand, offCommand);
            CmdExecuter executer = new CmdExecuter();
            Boolean b3=config.isEnabled();//开关机功能是否可用
            if(b3==true){
                setAlarmManger(context,true);
                command=command.replaceAll("disable","enable");

            }
            else{
                command=command.replaceAll("enable", "disable");

            }
            Log.i("test", command);
            executer.exec(command);

            return command;
        }

    }



    //是否有勾选星期
    private static boolean  NoWeekChoosed(boolean weeks[]){
        int i=0;
        for (i=0;i<7;i++){
            if (i<7){
                if (weeks[i]==true){
                    break;
                }
            }
        }
        if(i==7){
            return true;
        }
        else {
            return false;
        }
    }

    private static String getTime(AlarmsConfig.TimePoint time, AlarmsConfig config) {
        Calendar c = Calendar.getInstance();
        int[] time1 = getDate(time, config);
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

    private static int[] getDate(AlarmsConfig.TimePoint time,AlarmsConfig config) {
        Calendar c = Calendar.getInstance();
        int[] times = new int[3];
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DAY_OF_MONTH);
        int discrepancy = findRightDay(config, 0);
        times[0] = year;
        times[1] = month;
        if (discrepancy == 0) {
            Boolean b = isNeedToTomorrow(time);
            if (b == true) {
//                times[2]=day+1;

                    //如果今天设定的时间小于当前的时间，则用明天设定的时间来计算差异时间
                int discrepancy1 = findRightDay(config,1);
                times[2] = day+1+discrepancy1;


            } else {
                times[2] = day;
            }
        }
        else {
            times[2]=day+discrepancy;
        }
        return times;
    }

    //判断相差天数
    private static int findRightDay(AlarmsConfig config,int addtime) {
        Calendar c = Calendar.getInstance();
        int todayIndex = c.get(Calendar.DAY_OF_WEEK) - 2+addtime;//今天的星期在数组中的下标
        if (todayIndex >= 7) {
            todayIndex -= 7;
        }
        boolean[] b = config.getDayWeek();
        int index ;
        for (index = 0; index < 7; index++) {
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


    //设置格式变成OX
    private static String Chick(int intchick) {

        if (intchick < 10) {
            StringBuffer sb = new StringBuffer("0");
            sb.append(String.valueOf(intchick));
            return sb.toString();
        }
        return String.valueOf(intchick);
    }

    //通过开机时间和关机时间生成命令
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

    //判断时间格式是否正确，保证先关机，后开机
    private static boolean judgmentTimeStyle(String onCommand, String offCommand) {

        int [] open=commandToTime(onCommand);//201603150102
        int [] close=commandToTime(offCommand);//201603151102
        boolean b=Compare(open,close);

        return b;
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

    public  static  void resetDo(){
        resetConfig(context1,config1);
    }

}
