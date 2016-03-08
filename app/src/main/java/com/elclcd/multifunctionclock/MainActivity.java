package com.elclcd.multifunctionclock;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.io.File;

public class MainActivity extends Activity {
    private String OpenTime;
    private String ClosedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getUserSet();


    }

    //
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
