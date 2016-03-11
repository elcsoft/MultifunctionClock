package com.elclcd.multifunctionclock.handler;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.TimePicker;

import com.elclcd.multifunctionclock.MainActivity;
import com.elclcd.multifunctionclock.utils.CmdExecuter;
import com.elclcd.multifunctionclock.vo.AlarmsConfig;

import java.io.Serializable;
import java.util.Calendar;

import static com.elclcd.multifunctionclock.vo.AlarmsConfig.*;

/**
 * Created by 123 on 2016/3/10.
 */
public class Alarms {

    private static String[] weeks={"monday","tuesday","wednesday","thursday","friday","staturday","sunday"};

    /**
     * 保存配置
     * @param context
     * @param config
     */
    public static void saveConfig(Context context, AlarmsConfig config) {
//resetConfig();、
        SharedPreferences sharePre=context.getSharedPreferences("times", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor =sharePre.edit();
        editor.putBoolean("checkbox",config.isEnabled());

        for (int i=0;i<weeks.length;i++){
            editor.putBoolean(weeks[i],config.getDayWeek()[i]);
        }

        editor.putInt("timeOnHour",config.getPowerOnTime().getHour());
        editor.putInt("timeOnMinute",config.getPowerOnTime().getMinute());
        editor.putInt("timeOffHour",config.getPowerOffTime().getHour());
        editor.putInt("timeOffMinute",config.getPowerOffTime().getMinute());

        editor.commit();

    }

    /**
     * 获取定时开关机配置
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
        timePoint.setHour(sharePre.getInt("timeOnHour",0));
        timePoint.setMinute(sharePre.getInt("timeOnMinute", 0));
        alarmsConfig.setPowerOnTime(timePoint);
        AlarmsConfig.TimePoint timePoint2=new AlarmsConfig.TimePoint();
        timePoint2.setHour(sharePre.getInt("timeOffHour",0));
        timePoint2.setMinute(sharePre.getInt("timeOffMinute",0));
        alarmsConfig.setPowerOffTime(timePoint2);

        return alarmsConfig;

    }

    /**
     * 重置配置，更新开关机服务
     * @param config
     * @return 返回程序的状态，true 执行 ，false 不执行
     */
    public static String resetConfig(AlarmsConfig config) {
        //TODO 计算时间
        //TODO 生成命令
        //开机关机
//        Log.e("", "resetConfig " + config);
//        Log.e("", "result： " + config);
//        if(true)
//            return false;

        AlarmsConfig.TimePoint timeOn = config.getPowerOnTime();
        String onCommand=getTime(timeOn, config);
        AlarmsConfig.TimePoint timeOff =config.getPowerOffTime();
        String offCommand=getTime(timeOff, config);
        String command=getCommond(onCommand, offCommand);
        CmdExecuter executer =new CmdExecuter();
//        Boolean b=config.isEnabled();
//        if(b==true){
//            executer.exec(command);
//            return  true;
//        }
//        return  false;
          return  command;
    }

    private  static  String getTime(AlarmsConfig.TimePoint time,AlarmsConfig config){
        Calendar c = Calendar.getInstance();
        int []time1=getDate(time,config);
        String year =Chick(time1[0]);
        String month =Chick(time1[1]);
        String day =Chick(time1[2]);
        String hour =Chick(time.getHour());
        String minute=Chick(time.getMinute());
        StringBuffer sb = new StringBuffer(year);
        sb.append(month);
        sb.append(day);
        sb.append(hour);
        sb.append(minute);
        return sb.toString();

    }


    private static int[] getDate(AlarmsConfig.TimePoint time,AlarmsConfig config) {
        Calendar c = Calendar.getInstance();
        int [] times =new int[3];
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DAY_OF_MONTH);
        int discrepancy=findRightDay(config);
        times[0]=year;
        times[1]=month;
        if(discrepancy==0){
            Boolean b=isNeedToTomorrow(time);
            if(b==true){
                //如果今天设定的时间小于当前的时间，则用明天设定的时间来计算差异时间
//                times[2]=day+1;
                    int discrepancy1=findRightDay(config);
                    times[2]=day+1+discrepancy1;

            }
            else{
                times[2]=day;
            }
        }
        else {
            times[2]=day+discrepancy;
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
        sb.append(timeOn);
        sb.append(" ");
        sb.append(timeOff);
        sb.append(" ");
        sb.append("enable");
        return sb.toString();
    }

    //判断是否时间需要重置到明天
    private static Boolean isNeedToTomorrow(AlarmsConfig.TimePoint time) {
        Calendar c =Calendar.getInstance();
        int hour =time.getHour();
        int mintue=time.getMinute();
        int currentHour=c.get(Calendar.HOUR_OF_DAY);
        int currentMintue=c.get(Calendar.MINUTE);
        if(hour>currentHour){
            return  false;
        }
        else if(hour<currentHour){
            return true;
        }
        else{
            if (mintue>currentMintue){
                return false;
            }
            else {
                return true;
            }
        }
    }

    //判断最近要开关机的一天与今天的相差数
    private  static  int findRightDay(AlarmsConfig config){
        Calendar c =Calendar.getInstance();
        int todayIndex=c.get(Calendar.DAY_OF_WEEK)-2;//今天的星期在数组中的下标
        boolean [] b=config.getDayWeek();
        int index=0;
        for(index=0;index<8;index++){
            if(b[todayIndex]==true){
                break;
            }
            else {
                todayIndex++;
                if(todayIndex>=7){
                    todayIndex=0;
                }
            }
        }
        return index;
    }




}
