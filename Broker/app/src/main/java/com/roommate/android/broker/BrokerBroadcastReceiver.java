package com.roommate.android.broker;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.roommate.android.broker.common.AppUtils;
import com.roommate.android.broker.customer.push.PushCustomerService;

import org.xutils.common.util.LogUtil;

import java.util.List;

/**
 * Created by GYH on 2016/6/12.
 */
public class BrokerBroadcastReceiver extends BroadcastReceiver{

    private String action = null;

    @Override
    public void onReceive(Context context, Intent intent) {
        action = intent.getAction();
        if (Intent.ACTION_SCREEN_ON.equals(action)) { // 开屏
            LogUtil.d("开屏------");
        } else if (Intent.ACTION_SCREEN_OFF.equals(action)) { // 锁屏
            LogUtil.d("锁屏------");
        } else if (Intent.ACTION_USER_PRESENT.equals(action)) { // 解锁
            LogUtil.d("解锁------");
            if(!AppUtils.isServiceRunning(context,"com.roommate.android.broker.customer.push.PushCustomerService")){
                Intent intent1 = new Intent();
                intent1.setClass(context,PushCustomerService.class);
                context.startService(intent1);
            }
        }
    }


}
