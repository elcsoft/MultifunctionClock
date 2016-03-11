package com.elclcd.multifunctionclock.utils;

import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by 123 on 2016/3/10.
 * 设置格式的工具类
 */
public class UTIL {

    //设置格式变成OX
    public String chick(int intchick) {

        if (intchick < 10) {
            StringBuffer sb = new StringBuffer("0");
            sb.append(String.valueOf(intchick));
            return sb.toString();
        }
        return String.valueOf(intchick);
    }

    public String getnewTime(TimePicker timePicker) {
        //获取日历中的年月
        Calendar c = Calendar.getInstance();
        String year = chick(c.get(Calendar.YEAR));
        String month = chick(c.get(Calendar.MONTH) + 1);
        String day = chick(c.get(Calendar.DAY_OF_MONTH));
        String hour = chick(timePicker.getCurrentHour());
        String minute = chick(timePicker.getCurrentMinute());
        StringBuffer sb = new StringBuffer(year);
        sb.append(month);
        sb.append(day);
        sb.append(hour);
        sb.append(minute);
        return sb.toString();
    }
}
