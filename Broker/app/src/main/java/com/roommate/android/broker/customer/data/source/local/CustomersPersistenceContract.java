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

package com.roommate.android.broker.customer.data.source.local;

import android.provider.BaseColumns;

/**
 * The contract used for the db to save the tasks locally.
 */
public final class CustomersPersistenceContract {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public CustomersPersistenceContract() {}

    /* Inner class that defines the table contents */
    public static abstract class CustomerEntry implements BaseColumns {
        //表名
        public static final String TABLE_NAME = "customer";
        //id
        public static final String COLUMN_NAME_CUSTOMER_ID = "mid";
        //名字
        public static final String COLUMN_NAME_CUSTOMER_NAME = "name";
        //手机号
        public static final String COLUMN_NAME_CUSTOMER_PHONE_NUMBER = "phonenumber";
        //意愿
        public static final String COLUMN_NAME_DESIRE = "desire";
        //购房面积
        public static final String COLUMN_NAME_HOUSE_AREA = "housearea";
        //描述
        public static final String COLUMN_NAME_DESCRIBE = "describe";
        //输入录入时间
        public static final String COLUMN_NAME_INPUTDATE = "inputDate";
    }

    @Deprecated
    public static abstract class RemoteOpEntry implements BaseColumns{
        //表名
        public static final String TABLE_NAME = "remoteOp";
        //id
        public static final String COLUMN_NAME_OP_ID = "mid";
        //操的数据类型
        public static final String COLUMN_NAME_OP_DATA_TYPE = "opdatatype";
        //操作的类型
        public static final String COLUMN_NAME_OP_TYPE = "optype";
        //操作详情
        public static final String COLUMN_NAME_OP_DETAILS = "opdetails";
    }
}
