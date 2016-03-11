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
    public static String getVersion(Context context) {
            try {
                     PackageManager manager = context.getPackageManager();
                   PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
                String version = info.versionName;
                    return  version;
                } catch (Exception e) {
                   e.printStackTrace();
//                     return context.getString(R.string.can_not_find_version_name);
                 }
        return null;
         }

    /**
     * 获取本地软件版本名称
     */
    public static   String getLocalVersionName(Context ctx) {
        String localVersion = "";
        try {
            PackageInfo packageInfo = ctx.getApplicationContext().getPackageManager().getPackageInfo(ctx.getPackageName(), 0);
            localVersion = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return localVersion;
    }
}
