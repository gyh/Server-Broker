/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.roommate.android.broker.common.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BrokerDbHelper extends SQLiteOpenHelper {

    private static BrokerDbHelper brokerDbHelper;

    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "Broker.db";

    private static final String TEXT_TYPE = " TEXT";

    private static final String BOOLEAN_TYPE = " INTEGER";

    private static final String INTEGER_TYPE = " INTEGER";

    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_CUSTOMER_ENTRIES =
            "CREATE TABLE " + BrokerPersistenceContract.CustomerEntry.TABLE_NAME + " (" +
                    BrokerPersistenceContract.CustomerEntry.COLUMN_NAME_CUSTOMER_ID + TEXT_TYPE + " PRIMARY KEY," +
                    BrokerPersistenceContract.CustomerEntry.COLUMN_NAME_CUSTOMER_NAME + TEXT_TYPE + COMMA_SEP +
                    BrokerPersistenceContract.CustomerEntry.COLUMN_NAME_CUSTOMER_PHONE_NUMBER + TEXT_TYPE + COMMA_SEP +
                    BrokerPersistenceContract.CustomerEntry.COLUMN_NAME_DESCRIBE + TEXT_TYPE + COMMA_SEP +
                    BrokerPersistenceContract.CustomerEntry.COLUMN_NAME_DESIRE + TEXT_TYPE + COMMA_SEP +
                    BrokerPersistenceContract.CustomerEntry.COLUMN_NAME_HOUSE_AREA + TEXT_TYPE + COMMA_SEP +
                    BrokerPersistenceContract.CustomerEntry.COLUMN_NAME_INPUTDATE + TEXT_TYPE +
            " )";

    private static final String SQL_CREATE_REMOTE_ENTRIES =
            "CREATE TABLE " + BrokerPersistenceContract.RemoteOpEntry.TABLE_NAME + " (" +
                    BrokerPersistenceContract.RemoteOpEntry.COLUMN_NAME_OP_ID + INTEGER_TYPE + " PRIMARY KEY autoincrement," +
                    BrokerPersistenceContract.RemoteOpEntry.COLUMN_NAME_OP_TYPE + TEXT_TYPE + COMMA_SEP +
                    BrokerPersistenceContract.RemoteOpEntry.COLUMN_NAME_OP_DATA_TYPE + TEXT_TYPE + COMMA_SEP +
                    BrokerPersistenceContract.RemoteOpEntry.COLUMN_NAME_OP_DETAILS + TEXT_TYPE +
                    " )";

    private BrokerDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static BrokerDbHelper initNintce(Context context){
        if(brokerDbHelper == null){
            brokerDbHelper = new BrokerDbHelper(context);
        }
        return brokerDbHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        /**
         * 这个方法
         * 1、在第一次打开数据库的时候才会走
         * 2、在清除数据之后再次运行-->打开数据库，这个方法会走
         * 3、没有清除数据，不会走这个方法
         * 4、数据库升级的时候这个方法不会走
         */

        //创建
        db.execSQL(SQL_CREATE_CUSTOMER_ENTRIES);
        db.execSQL(SQL_CREATE_REMOTE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Not required as at version 1

        /**
         * 1、第一次创建数据库的时候，这个方法不会走
         * 2、清除数据后再次运行(相当于第一次创建)这个方法不会走
         * 3、数据库已经存在，而且版本升高的时候，这个方法才会调用
         */

        db.execSQL( "DROP TABLE IF EXISTS " + SQL_CREATE_CUSTOMER_ENTRIES );
        db.execSQL( "DROP TABLE IF EXISTS " + SQL_CREATE_REMOTE_ENTRIES );
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Not required as at version 1
        /**
         * 执行数据库的降级操作
         * 1、只有新版本比旧版本低的时候才会执行
         * 2、如果不执行降级操作，会抛出异常
         */

        super.onDowngrade(db, oldVersion, newVersion);
    }
}
