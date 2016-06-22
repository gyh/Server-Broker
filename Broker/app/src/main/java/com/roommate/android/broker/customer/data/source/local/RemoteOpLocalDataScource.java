package com.roommate.android.broker.customer.data.source.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.roommate.android.broker.common.data.BrokerDbHelper;
import com.roommate.android.broker.common.data.DatabaseManager;
import com.roommate.android.broker.customer.data.RemoteOp;
import com.roommate.android.broker.customer.data.source.RemoteOpDataSource;
import com.roommate.android.broker.common.data.BrokerPersistenceContract.RemoteOpEntry;

import org.xutils.common.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by GYH on 2016/6/18.
 */
public class RemoteOpLocalDataScource implements RemoteOpDataSource {

    private static RemoteOpLocalDataScource INSTANCE;

//    private BrokerDbHelper mDbHelper;

    // Prevent direct instantiation.
    private RemoteOpLocalDataScource(@NonNull Context context) {
        checkNotNull(context);
//        mDbHelper = BrokerDbHelper.initNintce(context);
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

//        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                RemoteOpEntry.COLUMN_NAME_OP_ID,
                RemoteOpEntry.COLUMN_NAME_OP_DATA_TYPE,
                RemoteOpEntry.COLUMN_NAME_OP_TYPE,
                RemoteOpEntry.COLUMN_NAME_OP_DETAILS
        };

        Cursor c = liteDatabase.query(
                RemoteOpEntry.TABLE_NAME, projection, null, null, null, null, null);

        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                String opId = c.getString(c.getColumnIndexOrThrow(RemoteOpEntry.COLUMN_NAME_OP_ID));
                String opDataType =c.getString(c.getColumnIndexOrThrow(RemoteOpEntry.COLUMN_NAME_OP_DATA_TYPE));
                String opType =c.getString(c.getColumnIndexOrThrow(RemoteOpEntry.COLUMN_NAME_OP_TYPE));
                String details =c.getString(c.getColumnIndexOrThrow(RemoteOpEntry.COLUMN_NAME_OP_DETAILS));
                RemoteOp remoteOp = new RemoteOp(opId,opDataType,opType,details);

                LogUtil.d("本地数据操作 获取所有操作列表---" + remoteOp.toString());

                remoteOps.add(remoteOp);
            }
        }
        if (c != null) {
            c.close();
        }

        DatabaseManager.getInstance().closeDatabase();
//        db.close();

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

//        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(RemoteOpEntry.COLUMN_NAME_OP_ID, remoteOp.getOpId());
        values.put(RemoteOpEntry.COLUMN_NAME_OP_DATA_TYPE, remoteOp.getOptObject());
        values.put(RemoteOpEntry.COLUMN_NAME_OP_TYPE, remoteOp.getOptType());
        values.put(RemoteOpEntry.COLUMN_NAME_OP_DETAILS, remoteOp.getOptData());

        liteDatabase.insert(RemoteOpEntry.TABLE_NAME, null, values);

        LogUtil.d("本地数据操作 保存操作数据成功 ---" + remoteOp.toString());

        DatabaseManager.getInstance().closeDatabase();

//        db.close();
    }

    @Override
    public void deleteRemoteOps(OpInfoCallback opInfoCallback) {

        LogUtil.d("本地数据操作 删除操作数据");

//        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        SQLiteDatabase liteDatabase = DatabaseManager.getInstance().openWritableDatabase();

        liteDatabase.delete(RemoteOpEntry.TABLE_NAME, null, null);

        LogUtil.d("本地数据操作 删除操作数据成功");

        DatabaseManager.getInstance().closeDatabase();

//        db.close();
    }

    @Override
    public void synCustomer(List<RemoteOp> remoteOps, OpInfoCallback opInfoCallback) {

    }
}
