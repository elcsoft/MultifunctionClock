package com.elclcd.multifunctionclock;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import com.elclcd.multifunctionclock.MainActivity;
import com.elclcd.multifunctionclock.R;
import com.elclcd.multifunctionclock.utils.Constant;

public class WarningTimeDialogActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setFinishOnTouchOutside(false);
        setContentView(R.layout.activity_warning_time_dialog);

    }

    public void doClick(View v){
        int id=v.getId();
        if(id==R.id.ikonw){
            finish();
        }
        else{
            Intent intent=new Intent(this, MainActivity.class);
            intent.setAction(Constant.CancelClose);
            startActivity(intent);
            finish();
        }
    }



}
