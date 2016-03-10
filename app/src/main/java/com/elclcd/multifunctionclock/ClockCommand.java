package com.elclcd.multifunctionclock;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by 123 on 2016/3/10.
 * 时间命令相关工具类
 */
public class ClockCommand {
    private Process ps1;
    private Process p;

    //得到命令需要输入的时间
    public String getCommond(String timeOn, String timeOff) {
        StringBuffer sb = new StringBuffer("");
        sb.append("/system/xbin/test ");
        sb.append(timeOn);
        sb.append(" ");
        sb.append(timeOff);
        sb.append(" ");
        sb.append("enable");
        return sb.toString();
    }

    //设置开机和关机时间
    public void SendCommand(String Command)  {
        DataOutputStream dos = null;
// String shell = "su";
        try {

//            Command="reboot";
//            ps1 = Runtime.getRuntime().exec(new String[]{"su", Command});
            p = Runtime.getRuntime().exec("su");
            dos = new DataOutputStream(p.getOutputStream());
            dos.writeBytes(Command + "\n");
            dos.flush();
            dos.writeBytes("exit\n");
            dos.flush();
            String line = null;

            new Thread() {
                public BufferedReader Reader1;

                @Override
                public void run() {
                    try {
                         Reader1 = new BufferedReader(new InputStreamReader(p.getInputStream()));
                        String line = null;

                        while ((line = Reader1.readLine()) != null) {
                            Log.e("MainActivity", "---result :" + line);
                        }
                    } catch (IOException e) {
                        Log.e("MainActivity", "--error :", e);
                        e.printStackTrace();

                    }
                    finally {
                        if(Reader1!=null){
                            try {
                                Reader1.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }

            }.start();
            ///system/xbin/test 201603101333 201603101332 enable
            new Thread() {
                public BufferedReader Reader1;

                @Override
                public void run() {
                    try {
                        Reader1 = new BufferedReader(new InputStreamReader(p.getErrorStream()));
                        String line = null;

                        while ((line = Reader1.readLine()) != null) {
                            Log.e("MainActivity", "---error :" + line);
                        }
                    } catch (IOException e) {
                        Log.e("MainActivity", "--error :", e);
                        e.printStackTrace();

                    }
                    finally {
                        if(Reader1!=null){
                            try {
                                Reader1.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }

            }.start();

        } catch (IOException e) {
            Log.e("MainActivity", "---error :", e);
            e.printStackTrace();


        } finally {
            if (dos != null) {
                try {
                    dos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }



}
