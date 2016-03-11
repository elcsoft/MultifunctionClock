package com.elclcd.multifunctionclock;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.TimePicker;

import com.elclcd.multifunctionclock.handler.Alarms;
import com.elclcd.multifunctionclock.utils.Application;
import com.elclcd.multifunctionclock.utils.TimePickerSize;
import com.elclcd.multifunctionclock.vo.AlarmsConfig;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {

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


    private List<CheckBox> list = null;

    private Alarms alarms=new Alarms();

    private Button icon_dialog;//点击弹出dialog按钮
    /**
     * dialog中控件
     */
    private AlertDialog mAlertDialog;
    private TextView verView;
    private TextView codeView;
    private TextView dialog_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main);
        init();

        updateView(Alarms.getConfig(this));//将获取的数据初始化控件状态


    }


    /**
     * 初始化
     */
    public void init() {
        //开关
        checkbox = (CheckBox) findViewById(R.id.checkbox);
        checkbox.setOnClickListener(new MyLinsister());

        //timepicker
        timePickerlift = (TimePicker) findViewById(R.id.timePicker);
        timePickerright = (TimePicker) findViewById(R.id.timePicker2);
        TimePickerSize timeSize = new TimePickerSize();
        timeSize.resizePikcer(timePickerlift);
        timeSize.resizePikcer(timePickerright);
        timePickerlift.setIs24HourView(true);
        timePickerright.setIs24HourView(true);
        timePickerlift.setOnTimeChangedListener(new timeChangedLinsister());
        timePickerright.setOnTimeChangedListener(new timeChangedLinsister());


        sunday = (CheckBox) findViewById(R.id.checkboxtian);
        sunday.setOnClickListener(new MyLinsister());
        saturday = (CheckBox) findViewById(R.id.checkboxliu);
        saturday.setOnClickListener(new MyLinsister());
        friday = (CheckBox) findViewById(R.id.checkboxwu);
        friday.setOnClickListener(new MyLinsister());
        thursday = (CheckBox) findViewById(R.id.checkboxsi);
        thursday.setOnClickListener(new MyLinsister());
        wednesday = (CheckBox) findViewById(R.id.checkboxsan);
        wednesday.setOnClickListener(new MyLinsister());
        tuesday = (CheckBox) findViewById(R.id.checkboxer);
        tuesday.setOnClickListener(new MyLinsister());
        monday = (CheckBox) findViewById(R.id.checkboxyi);
        monday.setOnClickListener(new MyLinsister());

        list = new ArrayList<CheckBox>();
        list.add(monday);
        list.add(tuesday);
        list.add(wednesday);
        list.add(thursday);
        list.add(friday);
        list.add(saturday);
        list.add(sunday);

        icon_dialog= (Button) findViewById(R.id.icon_dialog);
        icon_dialog.setOnClickListener(new iconLinstener());


    }

    /**
     * 将config数据映射到视图控件
     *
     * @param config
     */
    private void updateView(AlarmsConfig config) {
        if (config.isEnabled()) {
            checkbox.setChecked(true);
        }
        isOrNotCheck();//当开关开启才能操作其他控件

        for (int i = 0; i < list.size(); i++) {
            list.get(i).setChecked(config.getDayWeek()[i]);
        }
        //设置控件当前显示的时间

        timePickerlift.setCurrentHour(config.getPowerOnTime().getHour());
        timePickerlift.setCurrentMinute(config.getPowerOnTime().getMinute());
        timePickerright.setCurrentHour(config.getPowerOffTime().getHour());
        timePickerright.setCurrentMinute(config.getPowerOffTime().getMinute());


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

    /**
     * 从视图控件搜集数据生成AlarmsConfig对象返回
     *
     * @return
     */
    private AlarmsConfig createAlarmsConfig() {
        AlarmsConfig config=new AlarmsConfig();
        boolean[] weeks=new boolean[7];
        if(checkbox.isChecked()){
            config.setEnablen(true);
        }else{
            config.setEnablen(false);
        }

        for (int i=0;i<list.size();i++){
            if(list.get(i).isChecked()){
                weeks[i]=true;
            }
            else{
                weeks[i]=false;
            }
        }
        config.setDayWeek(weeks);
        //获取timepick的时间
        AlarmsConfig.TimePoint timePoint=new AlarmsConfig.TimePoint();
        timePoint.setHour(timePickerlift.getCurrentHour());
        timePoint.setMinute(timePickerlift.getCurrentMinute());
        config.setPowerOnTime(timePoint);
        AlarmsConfig.TimePoint timePoint2=new AlarmsConfig.TimePoint();
        timePoint2.setHour(timePickerright.getCurrentHour());
        timePoint2.setMinute(timePickerright.getCurrentMinute());
        config.setPowerOffTime(timePoint2);

        return config;
    }



    //控件的监听事件
    class MyLinsister implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            if(v.getId()==R.id.checkbox){
                isOrNotCheck();
            }
            alarms.saveConfig(MainActivity.this,createAlarmsConfig());
        }
    }

    /**
     *
     */
    class iconLinstener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            dialogs();
        }
    }

    /**
     * dialog
     */
    private void dialogs(){

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        mAlertDialog = builder.create();

        LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
        View view = inflater.inflate(R.layout.dialog_layout, null);

        verView= (TextView)view.findViewById(R.id.dialog_text_ver);
        codeView= (TextView) view.findViewById(R.id.dialog_text_code);
        dialog_button= (TextView) view.findViewById(R.id.dialog_text_button);
        mAlertDialog.show();
        mAlertDialog.getWindow().setContentView(view);
        mAlertDialog.getWindow().setLayout(320, 340);
        mAlertDialog.setCanceledOnTouchOutside(false);//dialog之外的地方不可点击

        dialog_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAlertDialog.dismiss();
            }
        });

        String ver=Application.getLocalVersionName(MainActivity.this);
        int code=Application.getVersion(MainActivity.this);
        verView.setText(ver);
        codeView.setText(code+"");

    }

    /**
     * timepicker监听
     */
    class timeChangedLinsister implements TimePicker.OnTimeChangedListener{
        @Override
        public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {

            alarms.saveConfig(MainActivity.this,createAlarmsConfig());
        }
    }


    /**
     * 当开关开启才能操作，否者不能
     */
    private void isOrNotCheck() {
        if (checkbox.isChecked()) {

            for (CheckBox box:list){
                box.setEnabled(true);
            }
            timePickerlift.setEnabled(true);
            timePickerright.setEnabled(true);
        } else {
            for (CheckBox box:list){
                box.setEnabled(false);
            }
            timePickerlift.setEnabled(false);
            timePickerright.setEnabled(false);
        }
    }

}
