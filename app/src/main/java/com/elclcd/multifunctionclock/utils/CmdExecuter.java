package com.elclcd.multifunctionclock.utils;

import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by 123 on 2016/3/10.
 * 执行命令
 */
public class CmdExecuter {
    private Process p;
    //
    public void exec(String command){
        DataOutputStream dos = null;
// String shell = "su";
        try {

//            Command="reboot";
//            ps1 = Runtime.getRuntime().exec(new String[]{"su", Command});
            p = Runtime.getRuntime().exec("su");//这句要用
            dos = new DataOutputStream(p.getOutputStream());
            dos.writeBytes(command + "\n");//这句要用
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
