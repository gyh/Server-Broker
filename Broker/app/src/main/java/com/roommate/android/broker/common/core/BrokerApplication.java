package com.roommate.android.broker.common.core;

import android.app.Application;
import android.app.Service;
import android.content.res.Configuration;
import android.os.Vibrator;

import com.roommate.android.broker.common.PreferencesUtil;
import com.roommate.android.broker.common.data.BrokerDbHelper;
import com.roommate.android.broker.common.data.DatabaseManager;

import org.xutils.BuildConfig;
import org.xutils.HttpManager;
import org.xutils.x;


/**
 * Created by GYH on 2016/3/23.
 */
public class BrokerApplication extends Application {

    public Vibrator mVibrator;

    private static Application application;

    @Override
    public void onCreate() {
        super.onCreate();

        application = this;

        //xUtils初始化
        x.Ext.init(this);
        x.Ext.setDebug(true);

        //数据库初始化
        DatabaseManager.initializeInstance(BrokerDbHelper.initNintce(this));

        //初始化SharedPreference
        PreferencesUtil.initContext(this);

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

    public static Application getApplication() {
        return application;
    }
}
