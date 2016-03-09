package com.elclcd.multifunctionclock;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.File;
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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getUserSet();
        setTitle("定时开机功能");
        init();


    }
    //初始化
    public void init(){
        //开关
        checkbox= (CheckBox) findViewById(R.id.checkbox);

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
}
