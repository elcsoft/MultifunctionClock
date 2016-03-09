package com.elclcd.multifunctionclock;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TimePicker;
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
import java.sql.Time;
import java.util.Calendar;
import java.util.Calendar;

public class MainActivity extends Activity{
    private String OpenTime;
    private String ClosedTime;
    private TimePicker timePickerlift;
    private TimePicker timePickerright;
    private CheckBox checkbox;
    private Button submit;
    private String timeOn;//开机时间
    private String timeOff;//关机时间
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
        setTitle("定时开机功能");
        init();



    }
    //初始化
    public void init(){
        //开关
        checkbox= (CheckBox) findViewById(R.id.checkbox);

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
        //timepicker
        timePickerlift= (TimePicker) findViewById(R.id.timePicker);
        timePickerright= (TimePicker) findViewById(R.id.timePicker2);
        timePickerlift.setIs24HourView(true);
        timePickerright.setIs24HourView(true);
        timePickerlift.setOnTimeChangedListener(new LiftTimeChangedLinsister());
        timePickerright.setOnTimeChangedListener(new rigtTimeChangeLinsiser());

        submit= (Button) findViewById(R.id.buttonSubmit);
        submit.setOnClickListener(new SubmitLinsister());
    }

//提交按钮监听
     class SubmitLinsister implements View.OnClickListener{
    @Override
        public void onClick(View v) {
        //如果开关按钮选中了就存储数据
            if(checkbox.isChecked()){
                sheardperfences(timeOn,timeOff);
//     Toast.makeText(MainActivity.this,"",Toast.LENGTH_LONG).show();
            }else{

            }
    }
}
   //左边timepicker监听
   class LiftTimeChangedLinsister implements TimePicker.OnTimeChangedListener{
         @Override
         public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {

             timeOn=getnewTime(timePickerlift);

         }
     }


    //右边timePicker监听
    class rigtTimeChangeLinsiser implements TimePicker.OnTimeChangedListener{
        @Override
        public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
            timeOff=getnewTime(timePickerright);
        }
    }
    //获取当前时间
    public String getnewTime(TimePicker timePicker){
        //获取日历中的年月
        Calendar calendar=Calendar.getInstance();
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar .MONTH)+1;
        int  date=calendar.get(Calendar.DATE);
        //获取timepick的时间
        int hour=timePicker.getCurrentHour();
        int minute=timePicker.getCurrentMinute();

        String  time=year+chick(month)+chick(date)+chick(hour)+chick(minute);
        return  time;
    }
//    将当前获取的timePicker的时间存储起来
     public void sheardperfences(String timeNo ,String timeOff){
         //没有触发timepicker时获取timepicker当前显示的时间
       if(timeNo==null||timeNo.equals("")){
             timeNo=getnewTime(timePickerlift);
         }
         if(timeOff==null||timeOff.equals("")){
             timeOff=getnewTime(timePickerright);
         }
         //存储
         SharedPreferences sharePre=getSharedPreferences("times",Context.MODE_PRIVATE);
         SharedPreferences.Editor editor =sharePre.edit();
         editor.putString("timeNo",timeNo);
         editor.putString("timeOff",timeOff);
         editor.commit();

         Toast.makeText(MainActivity.this,sharePre.getString("timeNo","0")+"---"+sharePre.getString("timeOff","0"),Toast.LENGTH_LONG).show();

     }

    //判断int是单数还是双数
    public String chick(int intchick){

        if(intchick<10){
            return "0"+intchick;
        }else{
            return intchick+"";
        }

    }

    //读取用户设置
    public void getUserSet(){
        File userset =new File("");
        if(userset.exists()){
            SharedPreferences sp= getSharedPreferences("", Context.MODE_PRIVATE);
            String OpenTime=sp.getString("opentime", "00:00");
            String ClosedTime=sp.getString("closedtime", "00:00");
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

