package com.elclcd.multifunctionclock;

import android.test.AndroidTestCase;

import com.elclcd.multifunctionclock.handler.Alarms;
import com.elclcd.multifunctionclock.utils.CmdExecuter;
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
//    public void testResetConfig() {
//
//        AlarmsConfig config = new AlarmsConfig();
//
//        config.getPowerOffTime().setHour(7);
//        config.getPowerOffTime().setMinute(10);
//        config.getPowerOnTime().setHour(7);
//        config.getPowerOnTime().setMinute(9);
//        config.setDayWeek(new boolean[]{true, true, true,true,false,false,false});
//
//
//       String result= Alarms.resetConfig(config);
//        assertEquals("/system/xbin/test 201603112000 201603112000 enable",result);
//
//    }

//    public void testCmd(){
//        CmdExecuter exec =   new CmdExecuter();
//        exec.exec("/system/xbin/test 201603111848 201603111850 enable");


        //        String command="/system/xbin/test 201603111655 201603111658 enable";
//        try {
//            Process p=Runtime.getRuntime().exec("su");
//            DataOutputStream dos = new DataOutputStream(p.getOutputStream());
//            dos.writeBytes(command + "\n");
//            dos.flush();
//            dos.writeBytes("exit\n");
//            dos.flush();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }
}
