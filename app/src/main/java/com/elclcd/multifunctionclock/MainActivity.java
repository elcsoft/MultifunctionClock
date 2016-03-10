package com.elclcd.multifunctionclock;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;


public class MainActivity extends Activity {
    private final static String Week = "week";
    private final static String DefaultTime = "201501010000";//偏好设置的默认时间
    private String timeOn;//开机时间
    private String timeOff;//关机时间
    private int Currentweekday;//今天的星期的下标
    int count = 0;//计数器，保证只能增加7天
    UTIL UTIL = new UTIL();//设置时间的工具类
    ClockCommand ClockCommand = new ClockCommand();//时钟命令类
    ArrayList CheckBoxlist = new ArrayList<CheckBox>();

    private TimePicker timePickerlift;
    private TimePicker timePickerright;
    private CheckBox checkbox;//判断是否启用定时开关机
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
        getCurrentWeekDay();//得到当天信息的下标
        setListener();
        getUserSet();


       String Command = ClockCommand.getCommond(timeOn, timeOff);
        Log.i("test",Command);
        AppcationIsRun(Command);

    }

    private void getCurrentWeekDay() {
        Calendar c = Calendar.getInstance();
        Currentweekday = c.get(Calendar.DAY_OF_WEEK);
        Currentweekday-=1;
    }

    //    初始化
    public void init() {
        //开关
        checkbox = (CheckBox) findViewById(R.id.checkbox);

        //timepicker
        timePickerlift = (TimePicker) findViewById(R.id.timePicker);
        timePickerright = (TimePicker) findViewById(R.id.timePicker2);
        timePickerlift.setIs24HourView(true);
        timePickerright.setIs24HourView(true);

        sunday = (CheckBox) findViewById(R.id.checkboxtian);
        saturday = (CheckBox) findViewById(R.id.checkboxliu);
        friday = (CheckBox) findViewById(R.id.checkboxwu);
        thursday = (CheckBox) findViewById(R.id.checkboxsi);
        wednesday = (CheckBox) findViewById(R.id.checkboxsan);
        tuesday = (CheckBox) findViewById(R.id.checkboxer);
        monday = (CheckBox) findViewById(R.id.checkboxyi);

        setCkeckboxToArrayList();


    }

    private void setCkeckboxToArrayList() {
        CheckBoxlist.add(sunday);
        CheckBoxlist.add(monday);
        CheckBoxlist.add(tuesday);
        CheckBoxlist.add(wednesday);
        CheckBoxlist.add(thursday);
        CheckBoxlist.add(friday);
        CheckBoxlist.add(saturday);

    }

    private void setListener() {
        //左边timepicker监听
        //在偏好设置时会变动
        timePickerlift.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                timeOn = UTIL.getnewTime(timePickerlift);
            }
        });
        timePickerright.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                timeOff = UTIL.getnewTime(timePickerright);
            }
        });
        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (checkbox.isChecked()) {
                    Toast.makeText(MainActivity.this, "定时开关机功能打开", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(MainActivity.this, "定时开关机功能关闭", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    /**
     * //     * 存储数据
     * //
     */
    public void sheardperfences(String timeNo, String timeOff) {
        //没有触发timepicker时获取timepicker当前显示的时间
        if (timeNo == null || timeNo.equals("")) {
            timeNo = UTIL.getnewTime(timePickerlift);
        }
        if (timeOff == null || timeOff.equals("")) {
            timeOff = UTIL.getnewTime(timePickerright);
        }


        SharedPreferences sharePre = getSharedPreferences("times", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharePre.edit();

        for (int i = 0; i < CheckBoxlist.size(); i++) {
            CheckBox CheckBox = (android.widget.CheckBox) CheckBoxlist.get(i);
            String week = CheckBox.getText().toString();
            StringBuilder sb = new StringBuilder(Week);
            sb.append(i);
            if (CheckBox.isChecked()) {
//             getSendCommandDay();

                editor.putString(sb.toString(), "yes");
            } else {
                editor.putString(sb.toString(), "no");
            }
        }


        //保存开关机时间
        editor.putString("timeNo", timeNo);
        editor.putString("timeOff", timeOff);
        editor.commit();
    }


    //读取用户设置
    public void getUserSet() {
        for (int i = 0; i < CheckBoxlist.size(); i++) {
            CheckBox checkBox = (CheckBox) CheckBoxlist.get(i);
            checkBox.setChecked(false);
        }

        SharedPreferences sp = getSharedPreferences("times", Context.MODE_PRIVATE);
        timeOn = sp.getString("timeNo", DefaultTime);
        timeOff = sp.getString("timeOff", DefaultTime);
        for (int i = 0; i < CheckBoxlist.size(); i++) {
            StringBuilder sb = new StringBuilder(Week);
            sb.append(i);
            String week = sp.getString(sb.toString(), "");
            if (week.equals("yes")) {
                setCkeckBoxIsCheck(i);
            }

        }
        setPicker();


    }

    //设置CheckBox
    private void setCkeckBoxIsCheck(int index) {

        CheckBox checkBox = (CheckBox) CheckBoxlist.get(index);
        checkBox.setChecked(true);
    }


    //设置picker
    private void setPicker() {
      int  Openhour = Integer.parseInt(timeOn.substring(8, 10));
        int Openminute = Integer.parseInt(timeOn.substring(10, 12));
        int Closehour = Integer.parseInt(timeOff.substring(8, 10));
       int Closeminute = Integer.parseInt(timeOff.substring(10, 12));
        timePickerlift.setCurrentHour(Openhour);
        timePickerlift.setCurrentMinute(Openminute);
        timePickerright.setCurrentHour(Closehour);
        timePickerright.setCurrentMinute(Closeminute);
    }


    //提交
    public void doClick(View v) {
        Log.i("test", "111");
        sheardperfences(timeOn, timeOff);
        String Command = ClockCommand.getCommond(timeOn, timeOff);
        Log.i("test", Command);
        AppcationIsRun(Command);
    }

    //判断功能是不是开启
    public void AppcationIsRun(String Command){
        if(checkbox.isChecked()){
            ClockCommand.SendCommand(Command);
        }
        else{
            Toast.makeText(this,"定时开关机功能是关闭的",Toast.LENGTH_SHORT).show();
        }

    }


//    //得到发送命令的日子
//    public void getSendCommandDay() {
//        timeOn = getTheData(UTIL.getnewTime(timePickerlift));
//        timeOff = getTheData(UTIL.getnewTime(timePickerright));
//
//    }
////
//    private String getTheData(String time) {
//        String time1 = time.substring(0, 6);
//        int Openday = Integer.parseInt(time.substring(6, 8));
//        String time2 = time.substring(8);
//        Openday += count;
//        StringBuilder sb = new StringBuilder(time1);
//        sb.append(UTIL.chick(Openday));
//        sb.append(time2);
//        return sb.toString();
//    }
//
//    //得到相差天数
//    private void getdifference(String time) {
//        getCurrentWeekDay();//得到当天信息的下标
//        for (count = 0; count < CheckBoxlist.size(); count++) {
//
//                CheckBox checkbox = (CheckBox) CheckBoxlist.get(Currentweekday);
//            Log.i("test",Currentweekday+""+"    1");
//                if (checkbox.isChecked()&&count!=0) {
//                        break;
//                }
////                else if(checkbox.isChecked()&&count==0){
////                    Boolean b= JudgmentMinuteAndHour(time);
////                    if(b==false){
////                        break;
////                    }
////                }
//                Currentweekday++;
//                if (Currentweekday >= 7) {
//                    Currentweekday = 0;
//                }
//
//            }
//
//
//    }

//    //判断时间和小时
//    private boolean JudgmentMinuteAndHour(String time) {
//        int  Openhour = Integer.parseInt(time.substring(8, 10));
//        Log.i("test",Openhour+"");
//        int Openminute = Integer.parseInt(time.substring(10, 12));
//        Log.i("test",Openminute+"");
//        Calendar c =Calendar.getInstance();
//        int CurrentHour =c.get(Calendar.HOUR_OF_DAY);
//        int CurrentMinture=c.get(Calendar.MINUTE);
//        if(Openhour<CurrentHour){
//           return true;
//        }
//        else if(Openhour>CurrentHour){
//            return  false;
//        }
//        else{
//            if(Openminute<=CurrentMinture){
//                return  true;
//            }
//            else{
//                return  false;
//            }
//        }
//
//    }


}
