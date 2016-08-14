package com.roommate.android.broker.customer.data.source.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.roommate.android.broker.common.data.BrokerDbHelper;
import com.roommate.android.broker.common.data.DatabaseManager;
import com.roommate.android.broker.customer.CustomerSo;
import com.roommate.android.broker.customer.data.Customer;
import com.roommate.android.broker.customer.data.RemoteOp;
import com.roommate.android.broker.customer.data.source.RemoteOpDataSource;
import com.roommate.android.broker.common.data.BrokerPersistenceContract.RemoteOpEntry;

import org.xutils.common.util.LogUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by GYH on 2016/6/18.
 */
public class RemoteOpLocalDataScource implements RemoteOpDataSource {

    private static RemoteOpLocalDataScource INSTANCE;


    private RemoteOpLocalDataScource(@NonNull Context context) {
        checkNotNull(context);
    }

    public static RemoteOpLocalDataScource getInstance(@NonNull Context context) {

        LogUtil.d("创建本地数据操作 ------------ ");

        if (INSTANCE == null) {
            INSTANCE = new RemoteOpLocalDataScource(context);
        }
        return INSTANCE;
    }


    @Override
    public void getRemoteOps(LoadLocalCallback callback) {

        checkNotNull(callback);

        LogUtil.d("本地数据操作 获取所有操作列表---");

        List<RemoteOp> remoteOps = new ArrayList<>();

        SQLiteDatabase liteDatabase = DatabaseManager.getInstance().openReadDatabase();

        String[] projection = {
                RemoteOpEntry.COLUMN_NAME_OP_ID,
                RemoteOpEntry.COLUMN_NAME_OP_DATA_TYPE,
                RemoteOpEntry.COLUMN_NAME_OP_TYPE,
                RemoteOpEntry.COLUMN_NAME_OP_DETAILS
        };

        Cursor c = liteDatabase.query(
                RemoteOpEntry.TABLE_NAME, projection, null, null, null, null, null);

        if (c != null && c.getCount() > 0) {
            Gson gson = new Gson();
            while (c.moveToNext()) {
                String opId = c.getString(c.getColumnIndexOrThrow(RemoteOpEntry.COLUMN_NAME_OP_ID));
                String opDataType =c.getString(c.getColumnIndexOrThrow(RemoteOpEntry.COLUMN_NAME_OP_DATA_TYPE));
                String opType =c.getString(c.getColumnIndexOrThrow(RemoteOpEntry.COLUMN_NAME_OP_TYPE));
                String opdata =c.getString(c.getColumnIndexOrThrow(RemoteOpEntry.COLUMN_NAME_OP_DETAILS));
                CustomerSo customer = gson.fromJson(opdata,CustomerSo.class);
                String namestr = customer.getName();
                try {
                    namestr = URLEncoder.encode(namestr,"utf-8");
                    customer.setName(namestr);
                }catch (Exception e){

                }
                String datestr = customer.getAppointTime()+":00";
                customer.setAppointTime(datestr);
                RemoteOp remoteOp = new RemoteOp(opId,opDataType,opType,customer);

                LogUtil.d("本地数据操作 获取所有操作列表---" + remoteOp.toString());

                remoteOps.add(remoteOp);
            }
        }
        if (c != null) {
            c.close();
        }

        DatabaseManager.getInstance().closeDatabase();
        if (remoteOps.isEmpty()) {
            // This will be called if the table is new or just empty.
            callback.onDataNotAvailable();
        } else {
            callback.onRemoteOpsLoader(remoteOps);
        }
    }

    @Override
    public void saveRemoteOp(RemoteOp remoteOp, OpInfoCallback opInfoCallback) {

        checkNotNull(remoteOp);

        checkNotNull(opInfoCallback);

        LogUtil.d("本地数据操作 保存操作数据---" + remoteOp.toString());

        SQLiteDatabase liteDatabase = DatabaseManager.getInstance().openWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(RemoteOpEntry.COLUMN_NAME_OP_ID, remoteOp.getOpId());
        values.put(RemoteOpEntry.COLUMN_NAME_OP_DATA_TYPE, remoteOp.getOptObject());
        values.put(RemoteOpEntry.COLUMN_NAME_OP_TYPE, remoteOp.getOptType());
        values.put(RemoteOpEntry.COLUMN_NAME_OP_DETAILS, remoteOp.getOptData().toString());

        liteDatabase.insert(RemoteOpEntry.TABLE_NAME, null, values);

        LogUtil.d("本地数据操作 保存操作数据成功 ---" + remoteOp.toString());

        DatabaseManager.getInstance().closeDatabase();

    }

    @Override
    public void deleteRemoteOps(OpInfoCallback opInfoCallback) {

        LogUtil.d("本地数据操作 删除操作数据");

        SQLiteDatabase liteDatabase = DatabaseManager.getInstance().openWritableDatabase();

        liteDatabase.delete(RemoteOpEntry.TABLE_NAME, null, null);

        LogUtil.d("本地数据操作 删除操作数据成功");

        DatabaseManager.getInstance().closeDatabase();

        opInfoCallback.onSuccess();
    }

    @Override
    public void synCustomer(List<RemoteOp> remoteOps, OpInfoCallback opInfoCallback) {

    }
}
