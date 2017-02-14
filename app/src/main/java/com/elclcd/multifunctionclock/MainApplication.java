package com.elclcd.multifunctionclock;

import android.app.Application;
import android.os.Environment;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.io.File;

import de.mindpipe.android.logging.log4j.LogConfigurator;

/**
 * Created by 123 on 2017/2/13.
 */
public class MainApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        LogConfigurator logConfigurator=new LogConfigurator();
        logConfigurator.setFileName(Environment.getExternalStorageDirectory() + File.separator + "multifunctionclock" + File.separator + "logs.txt");
        logConfigurator.setRootLevel(Level.ALL);
        logConfigurator.setFilePattern("%d %-5p [%c{2}]-[%L] %m%n");
        logConfigurator.setMaxFileSize(1024 * 1024 * 100);
        logConfigurator.setImmediateFlush(true);
        logConfigurator.setMaxBackupSize(10);
        logConfigurator.configure();
        Logger log = Logger.getLogger(MainApplication.class);
        log.info("My Application Created");
    }


}
