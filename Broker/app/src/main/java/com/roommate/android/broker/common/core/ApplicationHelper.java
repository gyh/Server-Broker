/*
 * @author: jackem.mojx@gmail.com
 */
package com.roommate.android.broker.common.core;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Handler;
import android.text.TextUtils;


import com.google.gson.Gson;

import java.util.List;

/**
 * 整个App上下文相关工具方法
 */
public class ApplicationHelper {


    private static Activity currentActivity;

    private static float density = 180;

    private static Context mContext;

    private static Handler mHandler;

    private static long mainUiThreadId;

    private static int width;

    private static int height;

    public int contentTop;

    public static String versionName;

    private static int versionCode;

    private static String strUserAgent;

    //private static boolean isHighDisplay=true;
    private static ApplicationHelper s_ins = null;

    public static Context getContext() {
        return mContext;
    }

    public static Handler getMainHandler() {
        return mHandler;
    }

    public static long getMainUiThreadId() {
        return mainUiThreadId;
    }

    public static void setUserAgent(String userAgent) {
        strUserAgent = userAgent;
    }

    public static String getClientAgent() {
        if (TextUtils.isEmpty(strUserAgent)) {
            ClientAgent clientAgent = new ClientAgent();
            clientAgent.packageName = mContext.getPackageName();
            clientAgent.version_name = versionName;
            clientAgent.version_code = versionCode;
            clientAgent.platform = "9beikeAndroidClient";
            clientAgent.device_name = Build.DEVICE;
            clientAgent.android_brand = Build.BRAND;
            clientAgent.os_version = Build.VERSION.RELEASE;
            clientAgent.device_id = DeviceIDHelper.id();
            clientAgent.product_name = Build.PRODUCT;
            clientAgent.sdk_version = Build.VERSION.SDK_INT;
            clientAgent.manufacturer = Build.MANUFACTURER;
            strUserAgent = new Gson().toJson(clientAgent);
        }
        return strUserAgent;
    }


    public static Activity getCurrentActivity() {
        return currentActivity;
    }

    public static void setCurrentActivity(Activity currentActivity) {
        ApplicationHelper.currentActivity = currentActivity;
    }

    public static float getDensity() {
        return density;
    }

    public static int getScreenWidth() {
        return width;
    }

    public static int getScreenHeight() {
        return height;
    }

    public static int dip2px(float dipValue) {
        final float scale = getDensity();
        return (int) (dipValue * scale + 0.5f);
    }

    public static int px2dip(float pxValue) {
        final float scale = getDensity();
        return (int) (pxValue / scale + 0.5f);
    }

    //initEnv is called within Application.onCreate
    ApplicationHelper() {

    }

    public static ApplicationHelper getInstance() {
        if (null == s_ins) {
            s_ins = new ApplicationHelper();
        }
        return s_ins;
    }

    public static void initEnv(Context context) {
        mContext = context;
        mHandler = new Handler(mContext.getMainLooper());
        mainUiThreadId = Thread.currentThread().getId();
        //ZusNotificationCenter.defaultCenter().init(mContext); //use LocalBroadcastManager instead
        density = mContext.getResources().getDisplayMetrics().density;
        width = mContext.getResources().getDisplayMetrics().widthPixels;
        height = mContext.getResources().getDisplayMetrics().heightPixels;
        try {
            versionName = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0).versionName;
            versionCode = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0).versionCode;
        }
        catch (NameNotFoundException e) {
            versionName = "";
            e.printStackTrace();
        }

        strUserAgent = getClientAgent();
    }


    /**
     * Checks if the application is in the background (i.e behind another application's Activity).
     *
     * @return true if another application is above this one.
     */
    public static boolean isApplicationBroughtToBackground() {
        ActivityManager am = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(mContext.getPackageName())) {
                return true;
            }
        }

        return false;
    }

    public static String getTopActivityName() {
        ActivityManager am = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            return topActivity.getShortClassName();
        }
        return null;
    }

    public static String getVersionName() {
        return versionName;
    }

    public static void setVersionName(String versionName) {
        ApplicationHelper.versionName = versionName;
    }

    public static int getVersionCode() {
        return versionCode;
    }

    public static void setVersionCode(int versionCode) {
        ApplicationHelper.versionCode = versionCode;
    }

    public static String getStrUserAgent() {
        return strUserAgent;
    }

    public static void setStrUserAgent(String strUserAgent) {
        ApplicationHelper.strUserAgent = strUserAgent;
    }


    static class ClientAgent {

        public String packageName;

        public int version_code;//app 版本号

        public String version_name;//app 版本名称

        public String platform;// 平台

        public String device_id;// 设备唯一标示

        public String android_brand;//Xiaomi

        public String os_version;//android 手机系统版本

        public String device_name;//virgo

        public String product_name;//virgo

        public String manufacturer;//制造商

        public int sdk_version;//sdk = 21

    }
}
