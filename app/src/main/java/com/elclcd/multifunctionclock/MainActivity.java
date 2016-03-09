package com.elclcd.multifunctionclock;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
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
        setTitle("定时开机功能");
        init();
        getUserSet();
        String Command=getCommond();
        SendCommand(Command);


    }
    //初始化
    public void init(){
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


        sunday= (CheckBox) findViewById(R.id.checkboxtian);
        saturday= (CheckBox) findViewById(R.id.checkboxliu);
        friday= (CheckBox) findViewById(R.id.checkboxwu);
        thursday= (CheckBox) findViewById(R.id.checkboxsi);
        wednesday= (CheckBox) findViewById(R.id.checkboxsan);
        tuesday= (CheckBox) findViewById(R.id.checkboxer);
        monday= (CheckBox) findViewById(R.id.checkboxyi);

        getSheardData();

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
}
   //左边timepicker监听
   class LiftTimeChangedLinsister implements TimePicker.OnTimeChangedListener{
         @Override
         public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {

    //左边timepicker监听
    class LiftTimeChangedLinsister implements TimePicker.OnTimeChangedListener {
        @Override
        public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {

         }
     }

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
