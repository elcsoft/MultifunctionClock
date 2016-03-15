package com.elclcd.multifunctionclock;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.elclcd.multifunctionclock.utils.Constant;

/**
 * Created by elc-06 on 2016/3/14.
 */
public class DialogActivity extends Activity {
    private Button button_yes;
    private Button button_no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.remind_dialog);
        this.setFinishOnTouchOutside(false);
        setTitle("提示：");
        super.onCreate(savedInstanceState);
        init();

    }

    private void init() {

        button_no= (Button) findViewById(R.id.button_no);
        button_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogActivity.this.finish();

            }
        });
        button_yes= (Button) findViewById(R.id.button_yes);
        button_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DialogActivity.this,MainActivity.class);
                intent.setAction(Constant.CancelClose);
                startActivity(intent);
                DialogActivity.this.finish();
            }
        });
    }



}
