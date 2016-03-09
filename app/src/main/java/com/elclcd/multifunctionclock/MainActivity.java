//package com.elclcd.multifunctionclock;
//
//import android.app.Activity;
//import android.content.Context;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.widget.TimePicker;
//import android.view.View;
//import android.widget.Button;
//import android.widget.CheckBox;
//import android.widget.TimePicker;
//import android.widget.Toast;
//
//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.OutputStreamWriter;
//import java.sql.Time;
//import java.util.Calendar;
//import java.util.Calendar;
//
//public class MainActivity extends Activity {
//    private TimePicker timePickerlift;//开机picker设置
//    private TimePicker timePickerright;//关机picker设置
//    private CheckBox checkbox;
//    private Button submit;
//    private String timeOn;//开机时间
//    private String timeOff;//关机时间
//    private String CurrenTime;
//
//
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        setTitle("定时开机功能");
//        String todaytime=getTodayTime();
//        init(todaytime);
//        getUserSet();
//        String Command =getCommond();
//        SendCommand(Command);
//
//    }
//    //初始化
//    public void init(String todaytime){
//        //开关
//        checkbox= (CheckBox) findViewById(R.id.checkbox);
//        //timepicker
//        timePickerlift= (TimePicker) findViewById(R.id.timePicker);
//        timePickerright= (TimePicker) findViewById(R.id.timePicker2);
//        //提交
//        submit= (Button) findViewById(R.id.buttonSubmit);
//        timePickerlift.setIs24HourView(true);
//        timePickerright.setIs24HourView(true);
//        UserSet(todaytime);
//
//    }
//
//
//    //用户设置,提交
//    private void UserSet(final String todaytime) {
//        timePickerlift.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
//            @Override
//            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
//                StringBuffer sb = new StringBuffer(todaytime);
//                sb.append(TimeStyleChange(hourOfDay));
//                sb.append(TimeStyleChange(minute));
//                timeOn = sb.toString();
//            }
//        });
//        timePickerright.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
//            @Override
//            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
//                StringBuffer sb = new StringBuffer(todaytime);
//                sb.append(TimeStyleChange(hourOfDay));
//                sb.append(TimeStyleChange(minute));
//                timeOff = sb.toString();
//            }
//        });
//
//    }
//
//    //设置开机和关机时间
//    public void SendCommand(String Command) {
////&&
//        String shell = "su";
//        try {
//            Process ps1 = Runtime.getRuntime().exec("su");
//
//            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ps1.getOutputStream()));
//            writer.write(Command);
//            writer.write("/n");
//            writer.flush();
//            BufferedReader reader = new BufferedReader(new InputStreamReader(ps1.getInputStream()));
//            String line = null;
//            while ((line = reader.readLine()) != null) {
//                Log.e("MainActivity", "---result :" + line);
//            }
//
//        } catch (IOException e) {
//            Log.e("MainActivity", "---error :", e);
//            e.printStackTrace();
//
//    }
//
//
//}
//
//
//
//
////    将当前获取的timePicker的时间存储起来
//     public void sheardperfences(){
//         //没有触发timepicker
//       if(timeOn==null||timeOn.equals("")){
//             timeOn=CurrenTime;
//           Log.i("test",timeOn);
//         }
//         if(timeOff==null||timeOff.equals("")){
//             timeOff=CurrenTime;
//             Log.i("test",timeOff);
//         }
//         //存储
//         SharedPreferences sharePre=getSharedPreferences("times",Context.MODE_PRIVATE);
//         SharedPreferences.Editor editor =sharePre.edit();
//         editor.putString("timeNo", timeOn);
//         editor.putString("timeOff", timeOff);
//         editor.commit();
//
//         Toast.makeText(MainActivity.this,sharePre.getString("timeNo","0")+"---"+sharePre.getString("timeOff","0"),Toast.LENGTH_LONG).show();
//
//     }
//
//
//
//    //读取用户的偏好设置
//    public void getUserSet() {
//            SharedPreferences sp = getSharedPreferences("times", Context.MODE_PRIVATE);
//            timeOn = sp.getString("timeNo", CurrenTime);
//            timeOff = sp.getString("timeOff", CurrenTime);
//
//    }
//
//    //得到命令需要输入的时间
//    public  String getCommond(){
//        StringBuffer sb=new StringBuffer("");
//        sb.append("/system/xbin/test ");
//        sb.append(timeOn);
//        sb.append(" ");
//        sb.append(timeOff);
//        sb.append(" ");
//        sb.append("enable");
//        Log.i("test", sb.toString());
//        return sb.toString();
//    }
//
//    //得到今天的年，月，日并以字符串形式存储，同时得到当前时间
//    public String getTodayTime() {
//        Calendar c = Calendar.getInstance();
//        String year = TimeStyleChange(c.get(Calendar.YEAR));
//        String month = TimeStyleChange(c.get(Calendar.MONTH) + 1);
//        String day = TimeStyleChange(c.get(Calendar.DAY_OF_MONTH));
//        String hour =TimeStyleChange(c.get(Calendar.HOUR_OF_DAY));
//        String minute=TimeStyleChange(c.get(Calendar.MINUTE));
//        StringBuffer sb = new StringBuffer(year);
//        sb.append(month);
//        sb.append(day);
//        StringBuffer sb1=new StringBuffer(sb);
//        sb1.append(hour);
//        sb1.append(minute);
//        CurrenTime=sb1.toString();
//        return sb.toString();
//    }
//
//
//
//
//
//
//    public void doClick(View v){
//        if (checkbox.isChecked()) {
//            sheardperfences();
//        } else {
//
//        }
//    }
//}

package com.elclcd.multifunctionclock;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TimePicker;
import android.widget.Toast;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Calendar;


public class MainActivity extends Activity {
    private String OpenTime;
    private String ClosedTime;
    private TimePicker timePickerlift;
    private TimePicker timePickerright;
    private CheckBox checkbox;
    private Button submit;
    private String timeOn;//开机时间
    private String timeOff;//关机时间


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("定时开机功能");
        init();
        getUserSet();
        String Command=getCommond();
        SendCommand(Command);


    }

    //初始化
    public void init() {
        //开关
        checkbox = (CheckBox) findViewById(R.id.checkbox);


        //timepicker
        timePickerlift = (TimePicker) findViewById(R.id.timePicker);
        timePickerright = (TimePicker) findViewById(R.id.timePicker2);
        timePickerlift.setIs24HourView(true);
        timePickerright.setIs24HourView(true);
        timePickerlift.setOnTimeChangedListener(new LiftTimeChangedLinsister());
        timePickerright.setOnTimeChangedListener(new rigtTimeChangeLinsiser());


        submit = (Button) findViewById(R.id.buttonSubmit);
        submit.setOnClickListener(new SubmitLinsister());
    }


    //提交按钮监听
    class SubmitLinsister implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            //如果开关按钮选中了就存储数据
            if (checkbox.isChecked()) {
                sheardperfences(timeOn, timeOff);

            } else {


            }
        }
    }

    //左边timepicker监听
    class LiftTimeChangedLinsister implements TimePicker.OnTimeChangedListener {
        @Override
        public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {


            timeOn = getnewTime(timePickerlift);


        }
    }


    //右边timePicker监听
    class rigtTimeChangeLinsiser implements TimePicker.OnTimeChangedListener {
        @Override
        public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
            timeOff = getnewTime(timePickerright);
        }
    }

    //获取当前时间
    public String getnewTime(TimePicker timePicker) {
        //获取日历中的年月
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int date = calendar.get(Calendar.DATE);
        //获取timepick的时间
        int hour = timePicker.getCurrentHour();
        int minute = timePicker.getCurrentMinute();


        String time = year + chick(month) + chick(date) + chick(hour) + chick(minute);
        return time;
    }

    //    将当前获取的timePicker的时间存储起来
    public void sheardperfences(String timeNo, String timeOff) {
        //没有触发timepicker时获取timepicker当前显示的时间
        if (timeNo == null || timeNo.equals("")) {
            timeNo = getnewTime(timePickerlift);
        }
        if (timeOff == null || timeOff.equals("")) {
            timeOff = getnewTime(timePickerright);
        }
        //存储
        SharedPreferences sharePre = getSharedPreferences("times", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharePre.edit();
        editor.putString("timeNo", timeNo);
        editor.putString("timeOff", timeOff);
        editor.commit();


        Toast.makeText(MainActivity.this, sharePre.getString("timeNo", "0") + "---" + sharePre.getString("timeOff", "0"), Toast.LENGTH_LONG).show();


    }


    //判断int是单数还是双数
    public String chick(int intchick) {


        StringBuffer sb = new StringBuffer("0");
        sb.append(String.valueOf(intchick));
        return sb.toString();


    }


    //读取用户设置
    public void getUserSet() {

        SharedPreferences sp = getSharedPreferences("times", Context.MODE_PRIVATE);
        timeOn = sp.getString("timeNo", getnewTime(timePickerlift));
        timeOff = sp.getString("timeOff", getnewTime(timePickerright));
        setPicker();

    }

    private void setPicker() {
        int Openhour = Integer.parseInt(timeOn.substring(8, 10));
        int Openminute = Integer.parseInt(timeOn.substring(10, 12));
        int Closehour = Integer.parseInt(timeOff.substring(8, 10));
        int Closeminute = Integer.parseInt(timeOff.substring(10, 12));
        timePickerlift.setCurrentHour(Openhour);
        timePickerlift.setCurrentMinute(Openminute);
        timePickerright.setCurrentHour(Closehour);
        timePickerright.setCurrentMinute(Closeminute);
    }


    //得到命令需要输入的时间
    public String getCommond() {
        StringBuffer sb = new StringBuffer("");
        sb.append("/system/xbin/test ");
        sb.append(timeOn);
        sb.append(" ");
        sb.append(timeOff);
        sb.append(" ");
        sb.append("enable");
        Log.i("test", sb.toString());
        return sb.toString();
    }

    //设置开机和关机时间
    public void SendCommand(String Command) {
//&&
        String shell = "su";
        try {
            Process ps1 = Runtime.getRuntime().exec("su");

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ps1.getOutputStream()));
            writer.write(Command);
            writer.write("/n");
            writer.flush();
            BufferedReader reader = new BufferedReader(new InputStreamReader(ps1.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                Log.e("MainActivity", "---result :" + line);
            }

        } catch (IOException e) {
            Log.e("MainActivity", "---error :", e);
            e.printStackTrace();


        }
    }
}
