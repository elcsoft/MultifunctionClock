package com.elclcd.multifunctionclock;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TimePicker;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.sql.Time;
import java.util.Calendar;

public class MainActivity extends Activity {
    private String OpenTime;
    private String ClosedTime;
    private TimePicker timeNo;
    private TimePicker timeOff;
    private String CurrenTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timeNo =(TimePicker)findViewById(R.id.timeNo);
        timeOff= (TimePicker) findViewById(R.id.timeOff);
        //saveUserSet();
        getUserSet();
        UserSet();
        String time =getTodayTime();
        getCommond(time);
        setTime();



    }

    //用户设置时间
    private void UserSet() {
        timeNo.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                StringBuffer sb=new StringBuffer(TimeStyleChange(hourOfDay));
                sb.append(TimeStyleChange(minute));
                OpenTime=sb.toString();
            }
        });
        timeOff.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                StringBuffer sb=new StringBuffer(TimeStyleChange(hourOfDay));
                sb.append(TimeStyleChange(minute));
                ClosedTime=sb.toString();
            }
        });
    }

    //设置开机和关机时间
    public void setTime() {
//&&
        String shell = "su";
        try {
            Process ps1 = Runtime.getRuntime().exec("su");

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ps1.getOutputStream()));
            writer.write( "/system/xbin/test 201603091147 201603091145 enable");
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



    //读取用户的偏好设置
    public void getUserSet() {
        File userset = new File("");
        if (userset.exists()) {
            SharedPreferences sp = getSharedPreferences("", Context.MODE_PRIVATE);
            String OpenTime = sp.getString("opentime", "201501010000");
            String ClosedTime = sp.getString("closedtime", "2015010100");
        }

    }

    //得到命令需要输入的时间
    public  String getCommond(String time){
        StringBuffer sb=new StringBuffer("");
        sb.append("/system/xbin/test ");
        sb.append(time);
        sb.append(OpenTime);
        sb.append(" ");
        sb.append(time);
        sb.append(ClosedTime);
        sb.append(" ");
        sb.append("enable");
        Log.i("test",sb.toString());
        return sb.toString();
    }

    //得到今天的年，月，日
    public String getTodayTime() {
        Calendar c = Calendar.getInstance();
        String year = TimeStyleChange(c.get(Calendar.YEAR));
        String month = TimeStyleChange(c.get(Calendar.MONTH) + 1);
        String day = TimeStyleChange(c.get(Calendar.DAY_OF_MONTH));
        StringBuffer sb = new StringBuffer(year);
        sb.append(month);
        sb.append(day);
        return sb.toString();
    }

    //改变时间格式，如果时间小于10，则变换成0X格式
    public String TimeStyleChange(int text) {
        if (text < 10) {
            StringBuffer sb = new StringBuffer("0");
            sb.append(String.valueOf(text));
            return sb.toString();
        }
        return String.valueOf(text);
    }
}

