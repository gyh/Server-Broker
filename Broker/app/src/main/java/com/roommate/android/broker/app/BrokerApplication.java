package com.roommate.android.broker.app;

import android.app.Application;
import android.app.Service;
import android.content.res.Configuration;
import android.os.Vibrator;


/**
 * Created by GYH on 2016/3/23.
 */
public class BrokerApplication extends Application {

    public Vibrator mVibrator;

    @Override
    public void onCreate() {
        super.onCreate();


        //上下文相关工具类
        ApplicationHelper.initEnv(this);
        mVibrator =(Vibrator)getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);


    }
    @Override
    public void onTerminate() {
        // 程序终止的时候执行

        super.onTerminate();
    }
    @Override
    public void onLowMemory() {
        // 低内存的时候执行
        super.onLowMemory();
    }
    @Override
    public void onTrimMemory(int level) {
        // 程序在内存清理的时候执行
        super.onTrimMemory(level);
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
