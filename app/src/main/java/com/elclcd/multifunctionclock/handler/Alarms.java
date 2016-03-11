package com.elclcd.multifunctionclock.handler;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.TimePicker;

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
     */
    public static void resetConfig(AlarmsConfig config) {
        //TODO 计算时间
        //TODO 生成命令
        //开机关机
        AlarmsConfig.TimePoint timeOn = config.getPowerOnTime();
        String onCommand=getDate(timeOn);
        AlarmsConfig.TimePoint timeOff =config.getPowerOnTime();
        String offCommand=getDate(timeOff);
        getCommond(onCommand,offCommand);
        CmdExecuter executer =new CmdExecuter();



    }

    private static String getDate(AlarmsConfig.TimePoint time) {
        //获取日历中的年月
        Calendar c = Calendar.getInstance();
        String year = chick(c.get(Calendar.YEAR));
        String month = chick(c.get(Calendar.MONTH) + 1);
        String day = chick(c.get(Calendar.DAY_OF_MONTH));
        String hour =chick(time.getHour());
        String minute=chick(time.getMinute());
        StringBuffer sb = new StringBuffer(year);
        sb.append(month);
        sb.append(day);
        sb.append(hour);
        sb.append(minute);
        return sb.toString();
    }

    //设置格式变成OX
    private static String chick(int intchick) {

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


}