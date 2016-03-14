package com.elclcd.multifunctionclock;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
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

import java.util.ArrayList;
import java.util.Calendar;
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

    private Thread thread = null;
    private  static int newMinute;
    private static  int newHour;


    private List<CheckBox> list = null;

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
        if(thread==null){
            chickTime();
        }


        AlarmsConfig config = Alarms.getConfig(this);
        if (config != null) {
            Alarms.saveConfig(this, config);
            updateView(config);
        }


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
        timePickerlift.setIs24HourView(true);
        timePickerright.setIs24HourView(true);

        timePickerlift.setSaveFromParentEnabled(false);
        timePickerlift.setSaveEnabled(true);

        timePickerright.setSaveFromParentEnabled(false);
        timePickerright.setSaveEnabled(true);

        timeSize.resizePikcer(timePickerlift);
        timeSize.resizePikcer(timePickerright);

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

        icon_dialog = (Button) findViewById(R.id.icon_dialog);
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
        if (config != null) {
            newMinute = config.getPowerOffTime().getMinute();
            newHour=config.getPowerOffTime().getHour();
        }

        for (int i = 0; i < list.size(); i++) {
            list.get(i).setChecked(config.getDayWeek()[i]);
        }
        //设置控件当前显示的时间

        timePickerlift.setCurrentHour(config.getPowerOnTime().getHour());
        timePickerlift.setCurrentMinute(config.getPowerOnTime().getMinute());
        timePickerright.setCurrentHour(config.getPowerOffTime().getHour());
        timePickerright.setCurrentMinute(config.getPowerOffTime().getMinute());


    }

    /**
     * 从视图控件搜集数据生成AlarmsConfig对象返回
     *
     * @return
     */
    private AlarmsConfig createAlarmsConfig() {
        AlarmsConfig config = new AlarmsConfig();
        boolean[] weeks = new boolean[7];
        if (checkbox.isChecked()) {
            config.setEnablen(true);
        } else {
            config.setEnablen(false);
        }

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).isChecked()) {
                weeks[i] = true;
            } else {
                weeks[i] = false;
            }
        }
        config.setDayWeek(weeks);
        //获取timepick的时间
        AlarmsConfig.TimePoint timePoint = new AlarmsConfig.TimePoint();
        timePoint.setHour(timePickerlift.getCurrentHour());
        timePoint.setMinute(timePickerlift.getCurrentMinute());
        config.setPowerOnTime(timePoint);
        AlarmsConfig.TimePoint timePoint2 = new AlarmsConfig.TimePoint();
        timePoint2.setHour(timePickerright.getCurrentHour());
        timePoint2.setMinute(timePickerright.getCurrentMinute());
        config.setPowerOffTime(timePoint2);

        return config;
    }


    //控件的监听事件
    class MyLinsister implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.checkbox) {
                isOrNotCheck();
            }
            AlarmsConfig config = createAlarmsConfig();
            Alarms.saveConfig(MainActivity.this, config);
        }
    }

    /**
     *
     */
    class iconLinstener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            dialogs();
        }
    }

    /**
     * dialog
     */
    private void dialogs() {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        mAlertDialog = builder.create();

        LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
        View view = inflater.inflate(R.layout.dialog_layout, null);

        verView = (TextView) view.findViewById(R.id.dialog_text_ver);
        codeView = (TextView) view.findViewById(R.id.dialog_text_code);
        dialog_button = (TextView) view.findViewById(R.id.dialog_text_button);
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

        String ver = Application.getLocalVersionName(MainActivity.this);
        int code = Application.getVersion(MainActivity.this);
        verView.setText(ver);
        codeView.setText(code + "");

    }

    /**
     * timepicker监听
     */
    class timeChangedLinsister implements TimePicker.OnTimeChangedListener {
        @Override
        public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {

            AlarmsConfig config=createAlarmsConfig();
            Alarms.saveConfig(MainActivity.this, config);

            newMinute = config.getPowerOffTime().getMinute();
            newHour=config.getPowerOffTime().getHour();
            Log.i("save", config.getPowerOffTime().getMinute() + "----" + newMinute);
        }
    }
    /**
     * 当开关开启才能操作，否者不能
     */
    private void isOrNotCheck() {
        if (checkbox.isChecked()) {

            for (CheckBox box : list) {
                box.setEnabled(true);
            }
            timePickerlift.setEnabled(true);
            timePickerright.setEnabled(true);
        } else {
            for (CheckBox box : list) {
                box.setEnabled(false);
            }
            timePickerlift.setEnabled(false);
            timePickerright.setEnabled(false);
        }
    }

    /**
     * 开启一个线程，判断时间是否为设定的时间
     */

    public void chickTime() {


        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {

                showRemindDialog();
                super.handleMessage(msg);
            }
        };
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                try {
                    Calendar calendar = Calendar.getInstance();
                    //判断当前时间是否为设定时间前5分钟
                    if( calendar.getTime().getMinutes()==newMinute-1&&calendar.getTime().getHours()==newHour){
                    //弹出提示对话框
                            Message message=handler.obtainMessage();
                            handler.sendMessage(message);
                    }
                    Log.i("-----", "---------"+newMinute);
                    Thread.sleep(1000*60);//休眠5秒
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                }
            }
        });
        thread.start();

    }

    //提醒对话框
    public void showRemindDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
        dialog.create().setCanceledOnTouchOutside(false);
        dialog.setTitle("提示");
        dialog.setMessage("还有1分钟关机，是否继续执行此操作？");
        dialog.setPositiveButton("确定", new Dialog.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        dialog.show();

    }


}
