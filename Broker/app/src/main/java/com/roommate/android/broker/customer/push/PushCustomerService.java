package com.roommate.android.broker.customer.push;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;

import com.roommate.android.broker.customer.data.source.RemoteOpDataSource;
import com.roommate.android.broker.customer.data.source.RemoteOpRepository;
import com.roommate.android.broker.customer.data.source.local.RemoteOpLocalDataScource;
import com.roommate.android.broker.customer.data.source.remote.RemoteOpRemoteDataScource;
import com.roommate.android.broker.customer.list.BrokerActivity;
import com.roommate.android.broker.R;
import com.roommate.android.broker.common.DateUtils;
import com.roommate.android.broker.customer.data.Customer;
import com.roommate.android.broker.customer.data.source.CustomerDataSource;
import com.roommate.android.broker.customer.data.source.CustomerRepository;
import com.roommate.android.broker.customer.data.source.local.CustomerLocalDataScource;
import com.roommate.android.broker.customer.data.source.remote.CustomerRemoteDataSource;
import com.roommate.android.broker.customer.fitter.FitterCustomersActivity;

import org.xutils.common.util.LogUtil;

import java.util.List;

/**
 * Created by GYH on 2016/6/13.
 */
public class PushCustomerService extends Service{

    private RemoteOpRepository remoteOpRepository;

    private static final int PENDING_INTENT_REQUESTCODE = 0;

    private static final int NOTIFICATION_FLAG = 1;

    private CustomerRepository mRepository;

    private boolean isRun = true;

    private PuhsThread puhsThread;

    //通知 管理
    private NotificationManager notificationManager;

    @Override
    public void onCreate() {
        super.onCreate();

        remoteOpRepository = RemoteOpRepository.getInstance(RemoteOpRemoteDataScource.getInstance(),
                RemoteOpLocalDataScource.getInstance(this));

        mRepository = CustomerRepository.getInstance(CustomerRemoteDataSource.getInstance(),
                CustomerLocalDataScource.getInstance(this));

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        puhsThread = new PuhsThread();

        puhsThread.start();
    }

    class PuhsThread extends Thread{
        @Override
        public void run() {
            while (isRun){

                LogUtil.d("后台 查询 - --------");

                mRepository.searchInputDate(DateUtils.getToday(), new CustomerDataSource.LoadCustomersCallback() {
                    @Override
                    public void onCustomersLoader(List<Customer> customerList) {
                        if(customerList == null ||customerList.size()== 0 ){
                            return;
                        }
                        PendingIntent pendingIntent = PendingIntent.getActivities(PushCustomerService.this,PENDING_INTENT_REQUESTCODE,makeIntentStack(PushCustomerService.this),PendingIntent.FLAG_UPDATE_CURRENT);

                        Bitmap btm = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);

                        NotificationCompat.Builder builder = (NotificationCompat.Builder) new NotificationCompat.Builder(PushCustomerService.this)
                                .setSmallIcon(R.mipmap.ic_launcher).setContentTitle(DateUtils.getToday())
                                .setContentText("您今天有 "+customerList.size()+" 预约");
                        builder.setTicker("您有新的提示");
                        builder.setLargeIcon(btm);
                        builder.setContentIntent(pendingIntent);
                        Notification notification = builder.build();
                        notification.flags= Notification.FLAG_AUTO_CANCEL;

                        notificationManager.notify(NOTIFICATION_FLAG,notification);
                    }



                    @Override
                    public void onDataNotAvailable() {

                    }
                });

                LogUtil.d("后台 同步 - --------");
                remoteOpRepository.synCustomer(null, new RemoteOpDataSource.OpInfoCallback() {
                    @Override
                    public void onSuccess() {
                        LogUtil.d("后台 同步 成功");
                    }

                    @Override
                    public void onFail() {
                        LogUtil.d("后台 同步 失败");
                    }
                });

                try {
                    sleep(1000*60*10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isRun = false;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private Intent[] makeIntentStack(Context context) {
        Intent[] intents = new Intent[2];
        intents[0] = Intent.makeRestartActivityTask(new ComponentName(context,BrokerActivity.class));
        intents[1] = new Intent();
        intents[1].setClass(PushCustomerService.this, FitterCustomersActivity.class);
        intents[1].putExtra(FitterCustomersActivity.FITTER_DATE_KEY,DateUtils.getToday());
        return intents;
    }
}
