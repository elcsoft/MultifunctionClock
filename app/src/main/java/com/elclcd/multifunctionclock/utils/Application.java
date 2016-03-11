package com.elclcd.multifunctionclock.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.elclcd.multifunctionclock.R;

/**
 * Created by elc-06 on 2016/3/11.
 */


public class Application {

    /**
     2  * 获取版本号
     3  * @return 当前应用的版本号
     4  */
    public static int getVersion(Context context) {

        PackageInfo info=getPackageInfo(context);
        return info.versionCode;
         }

    /**
     * 获取本地软件版本名称
     */
    public static   String getLocalVersionName(Context context) {
        PackageInfo info=getPackageInfo(context);
        return info.versionName;
    }


    private static PackageInfo getPackageInfo(Context ctx){
        try {
            return ctx.getApplicationContext().getPackageManager().getPackageInfo(ctx.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
