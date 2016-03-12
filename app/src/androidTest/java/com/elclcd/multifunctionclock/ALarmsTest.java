package com.elclcd.multifunctionclock;

import android.test.AndroidTestCase;

import com.elclcd.multifunctionclock.handler.Alarms;
import com.elclcd.multifunctionclock.vo.AlarmsConfig;

/**
 * Created by 123 on 2016/3/11.
 */
public class ALarmsTest extends AndroidTestCase {

    public void testResetConfig() {

        AlarmsConfig config = new AlarmsConfig();

        config.getPowerOffTime().setHour(7);
        config.getPowerOffTime().setMinute(10);
        config.getPowerOnTime().setHour(7);
        config.getPowerOnTime().setMinute(11);
        config.setDayWeek(new boolean[]{false, true, false,false,false,true,false});


       String result= Alarms.resetConfig(getContext(),config);
        assertEquals("/system/xbin/test 201603112000 201603112000 enable",result);

    }
}
