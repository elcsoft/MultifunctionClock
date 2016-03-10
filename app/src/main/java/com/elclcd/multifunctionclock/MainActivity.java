package com.elclcd.multifunctionclock;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TimePicker;
import android.widget.Toast;

import com.elclcd.utils.TimePickerSize;

import java.util.Calendar;
/**
 * 定时开关机的闹钟
 */
public class MainActivity extends Activity{
    private TimePicker timePickerlift;//开机timepicker
    private TimePicker timePickerright;//关机timepicker
    private CheckBox checkbox;//开关
    private Button submit;//提交
    private String timeOn;//开机时间
    private String timeOff;//关机时间

    private CheckBox sunday;//星期天
    private CheckBox monday;
    private CheckBox tuesday;
    private CheckBox wednesday;
    private CheckBox thursday;
    private CheckBox friday;
    private CheckBox saturday;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去掉title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        init();
        getSheardData();

    }
    /**
     * 初始化控件
     */
    public void init(){
        //开关
        checkbox= (CheckBox) findViewById(R.id.checkbox);
        checkbox.setOnClickListener(new SwitchLinesister());

        //timepicker
        timePickerlift= (TimePicker) findViewById(R.id.timePicker);
        timePickerright= (TimePicker)findViewById(R.id.timePicker2);
        TimePickerSize timeSize=new TimePickerSize();
        timeSize.resizePikcer(timePickerlift);
        timeSize.resizePikcer(timePickerright);
        timePickerlift.setIs24HourView(true);
        timePickerright.setIs24HourView(true);
        timePickerlift.setOnTimeChangedListener(new LiftTimeChangedLinsister());
        timePickerright.setOnTimeChangedListener(new rigtTimeChangeLinsiser());

        submit= (Button) findViewById(R.id.buttonSubmit);
        submit.setOnClickListener(new SubmitLinsister());
        submit.getBackground().setAlpha(20);//设置背景为透明的

        sunday= (CheckBox) findViewById(R.id.checkboxtian);
        saturday= (CheckBox) findViewById(R.id.checkboxliu);
        friday= (CheckBox) findViewById(R.id.checkboxwu);
        thursday= (CheckBox) findViewById(R.id.checkboxsi);
        wednesday= (CheckBox) findViewById(R.id.checkboxsan);
        tuesday= (CheckBox) findViewById(R.id.checkboxer);
        monday= (CheckBox) findViewById(R.id.checkboxyi);
    }

    /**
     * 开关监听
     */
    class SwitchLinesister implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            isOrNotCheck();
        }
    }
    /**
     * 当开关开启才能操作，否者不能
     */
    public void isOrNotCheck(){
        if(checkbox.isChecked()){
            sunday.setEnabled(true);
            saturday.setEnabled(true);
            friday.setEnabled(true);
            thursday.setEnabled(true);
            wednesday.setEnabled(true);
            tuesday.setEnabled(true);
            monday.setEnabled(true);
            timePickerlift.setEnabled(true);
            timePickerright.setEnabled(true);
        }else{
            sunday.setEnabled(false);
            saturday.setEnabled(false);
            friday.setEnabled(false);
            thursday.setEnabled(false);
            wednesday.setEnabled(false);
            tuesday.setEnabled(false);
            monday.setEnabled(false);
            timePickerlift.setEnabled(false);
            timePickerright.setEnabled(false);
        }
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
        isOrNotCheck();
        //获取sunday存储状态的信息
        String sundayCheck=shareData.getString("sunday", "");
        if(sundayCheck.equals("yes")){
            sunday.setChecked(true);
        }
        //获取saturday存储状态的信息
        String saturdayCheck=shareData.getString("saturday","");
        if(saturdayCheck.equals("yes")){
            saturday.setChecked(true);
        }
        //获取friday存储状态的信息
        String fridayCheck=shareData.getString("friday","");
        if(fridayCheck.equals("yes")){
            friday.setChecked(true);
        }

        //获取thursday存储状态的信息
        String thursdayCheck=shareData.getString("thursday","");
        if(thursdayCheck.equals("yes")){
            thursday.setChecked(true);
        }
        //获取wednesday存储状态的信息
        String wednesdayCheck=shareData.getString("wednesday","");
        if(wednesdayCheck.equals("yes")){
            wednesday.setChecked(true);
        }
        //获取tuesday存储状态的信息
        String tuesdayCheck=shareData.getString("tuesday","");
        if(tuesdayCheck.equals("yes")){
            tuesday.setChecked(true);
        }
        //获取monday存储状态的信息
        String mondayCheck=shareData.getString("monday","");
        if(mondayCheck.equals("yes")){
            monday.setChecked(true);
        }
        //初始化时间控件（将获取的状态显示在界面）
        //获取存储的开机时间
        String timeNo=shareData.getString("timeNo","");
        if(!timeNo.equals("")||timeNo!=null){
            try{
               int  hour=Integer.parseInt(timeNo.substring(8,10));
                int minute=Integer.parseInt(timeNo.substring(10));
                //获取控件当前显示的时间
                timePickerlift.setCurrentHour(hour);
                timePickerlift.setCurrentMinute(minute);
            }catch (Exception e){
                            }
        }
        //获取存储的关机时间
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
         //存储
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






}
