package com.elclcd.multifunctionclock;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 定时开关机的闹钟
 */
public class MainActivity extends Activity{
    private String OpenTime;
    private String ClosedTime;
    private TimePicker timePickerlift;
    private TimePicker timePickerright;
    private CheckBox checkbox;
    private Button submit;
    private String timeOn;//开机时间
    private String timeOff;//关机时间

    private CheckBox sunday;
    private CheckBox monday;
    private CheckBox tuesday;
    private CheckBox wednesday;
    private CheckBox thursday;
    private CheckBox friday;
    private CheckBox saturday;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getUserSet();
        setTitle("定时开机功能");
        init();


    }
    /**
     * 初始化控件
     */

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


        sunday= (CheckBox) findViewById(R.id.checkboxtian);
        saturday= (CheckBox) findViewById(R.id.checkboxliu);
        friday= (CheckBox) findViewById(R.id.checkboxwu);
        thursday= (CheckBox) findViewById(R.id.checkboxsi);
        wednesday= (CheckBox) findViewById(R.id.checkboxsan);
        tuesday= (CheckBox) findViewById(R.id.checkboxer);
        monday= (CheckBox) findViewById(R.id.checkboxyi);

        getSheardData();

    }

    /**
     * 初始化控件
     * 将保存的数据从sharedPreferences中取出并设置在控件上
     */
    public void getSheardData(){
        SharedPreferences shareData=getSharedPreferences("times", Context.MODE_PRIVATE);
        //复选框
        String checkboxCheck=shareData.getString("checkbox", "");
        if(checkboxCheck.equals("yes")){
            checkbox.setChecked(true);
        }

        String sundayCheck=shareData.getString("sunday", "");
        if(sundayCheck.equals("yes")){
            sunday.setChecked(true);
        }

        String saturdayCheck=shareData.getString("saturday","");
        if(saturdayCheck.equals("yes")){
            saturday.setChecked(true);
        }

        String fridayCheck=shareData.getString("friday","");
        if(fridayCheck.equals("yes")){
            friday.setChecked(true);
        }


        String thursdayCheck=shareData.getString("thursday","");
        if(thursdayCheck.equals("yes")){
            thursday.setChecked(true);
        }

        String wednesdayCheck=shareData.getString("wednesday","");
        if(wednesdayCheck.equals("yes")){
            wednesday.setChecked(true);
        }

        String tuesdayCheck=shareData.getString("tuesday","");
        if(tuesdayCheck.equals("yes")){
            tuesday.setChecked(true);
        }

        String mondayCheck=shareData.getString("monday","");
        if(mondayCheck.equals("yes")){
            monday.setChecked(true);
        }
        //初始化时间控件
        String timeNo=shareData.getString("timeNo","");
        if(!timeNo.equals("")||timeNo!=null){
            try{
               int  hour=Integer.parseInt(timeNo.substring(8,10));
                int minute=Integer.parseInt(timeNo.substring(10));

                timePickerlift.setCurrentHour(hour);
                timePickerlift.setCurrentMinute(minute);
            }catch (Exception e){
            }
        }
        String timeOff=shareData.getString("timeOff","");
        if(!timeOff.equals("")||timeOff!=null){
            try{
                int  hour=Integer.parseInt(timeOff.substring(8,10));
               int  minute=Integer.parseInt(timeOff.substring(10));

                timePickerright.setCurrentHour(hour);
                timePickerright.setCurrentMinute(minute);
            }catch (Exception e){

            }
        }
    }

    /**
     * 提交按钮监听
     */
     class SubmitLinsister implements View.OnClickListener{
    @Override
        public void onClick(View v) {
                sheardperfences(timeOn,timeOff);
    }
}

    /**
     * 左边timepicker监听
     */
   class LiftTimeChangedLinsister implements TimePicker.OnTimeChangedListener{
         @Override
         public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {

             timeOn=getnewTime(timePickerlift);

         }
     }


    /**
     * 右边timePicker监听
     */
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

    /**
     * 存储数据
     */
     public void sheardperfences(String timeNo ,String timeOff){
         //没有触发timepicker时获取timepicker当前显示的时间
       if(timeNo==null||timeNo.equals("")){
             timeNo=getnewTime(timePickerlift);
         }
         if(timeOff==null||timeOff.equals("")){
             timeOff=getnewTime(timePickerright);
         }

         SharedPreferences sharePre=getSharedPreferences("times",Context.MODE_PRIVATE);
         SharedPreferences.Editor editor =sharePre.edit();
         if(checkbox.isChecked()){
             editor.putString("checkbox", "yes");
         }else{
             editor.putString("checkbox","no");
         }

         if(sunday.isChecked()){
             editor.putString("sunday","yes");
         }else {
             editor.putString("sunday","no");
         }
         if(saturday.isChecked()){
             editor.putString("saturday","yes");
         }else {
             editor.putString("saturday","no");
         }

         if(friday.isChecked()){
             editor.putString("friday","yes");
         }else {
             editor.putString("friday","no");
         }

         if(thursday.isChecked()){
             editor.putString("thursday","yes");
         }else {
             editor.putString("thursday","no");
         }

         if(wednesday.isChecked()){
             editor.putString("wednesday","yes");
         }else {
             editor.putString("wednesday","no");
         }

         if(tuesday.isChecked()){
             editor.putString("tuesday","yes");
         }else {
             editor.putString("tuesday","no");
         }
         if(monday.isChecked()){
             editor.putString("monday","yes");
         }else {
             editor.putString("monday","no");
         }
    //保存开关机时间
         editor.putString("timeNo",timeNo);
         editor.putString("timeOff", timeOff);
         editor.commit();

         Toast.makeText(this,"设置成功！",Toast.LENGTH_LONG).show();

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
