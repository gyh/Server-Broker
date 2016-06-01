package com.roommate.android.broker.customer.data.source.remote;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.support.annotation.NonNull;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.roommate.android.broker.common.ImagePathUtils;
import com.roommate.android.broker.customer.data.Customer;
import com.roommate.android.broker.customer.data.source.CustomerDataSource;
import com.roommate.android.broker.customer.data.source.local.CustomersPersistenceContract;
import com.roommate.android.broker.house.data.House;

import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by GYH on 2016/5/27.
 *
 * 远程数据库主要功能有：
 *
 * （1）存储要提交的数据数据请求
 * （2）获取远程数据
 */
public class CustomerRemoteDataSource implements CustomerDataSource{

    private static CustomerRemoteDataSource INSTANCE;

    private final static Map<String, RemoteOpData> REMOTE_OP_DATA_MAP;

    static {
        REMOTE_OP_DATA_MAP = new LinkedHashMap<>();
    }

    // Prevent direct instantiation.
    private CustomerRemoteDataSource() {}

    public static CustomerRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CustomerRemoteDataSource();
        }
        return INSTANCE;
    }


    @Override
    public void getCustomers(@NonNull final LoadCustomersCallback callback) {

        // TODO: 2016/6/1 传入操作数据

        RequestParams requestParams = new RequestParams("http://search.tootoo.cn/searchMB/goods");
        requestParams.addQueryStringParameter("channelType","2");
        requestParams.addQueryStringParameter("scope","11202");
        requestParams.addQueryStringParameter("geoId","1,2,3,0");
        requestParams.addQueryStringParameter("yz","39d16330300072bce2e7dab87a5d85f0");
        requestParams.addQueryStringParameter("key","水果");
        LogUtil.d("  ----Input---   " + requestParams.toString());
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(final String result) {

                LogUtil.d("  ----output---   " + result);

                REMOTE_OP_DATA_MAP.clear();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ArrayList<Customer> strings = new ArrayList<>();
                        JsonParser jsonParser = new JsonParser();
                        JsonObject resultObj = jsonParser.parse(result).getAsJsonObject().get("result").getAsJsonObject();
                        JsonArray rows = resultObj.get("rows").getAsJsonArray();
                        for(int i=0;i<3;i++){
//                        JsonObject row = rows.get(i).getAsJsonObject();
                            Customer house = new Customer(System.currentTimeMillis()+"",
                                    "郭跃华"+i,
                                    "13240123693",
                                    "3",
                                    "90",
                                    "客户描述经典款了房间里的康师傅几点睡了开发就考虑到",
                                    "2016-9-11");
                            strings.add(house);
                            try {
                                Thread.sleep(1);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        callback.onCustomersLoader(strings);
                    }
                }).start();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                callback.onDataNotAvailable();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }

    @Override
    public void getCustomer(@NonNull String customerId, @NonNull final GetCustomerCallback callback) {
        RequestParams requestParams = new RequestParams("");

        LogUtil.d("  ----Input---   " + requestParams.toString());
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                LogUtil.d("  ----output---   " + result);
                Handler handler = new Handler();
                final Customer house = new Customer(System.currentTimeMillis()+"",
                        "郭跃华",
                        "13240123693",
                        "3",
                        "90",
                        "123151651565465",
                        "2016-9-11");
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //todo 处理数据
                        callback.onCustomerLoader(house);
                    }
                });
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                callback.onDataNotAvailable();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    @Override
    public void saveCustomer(@NonNull Customer customer) {
        checkNotNull(customer);
        //// TODO: 2016/6/1   录入提交服务端操作记录 操作id 操作数据类型 操作类型  操作数据
        String mId = System.currentTimeMillis()+"";
        RemoteOpData remoteOpData = new RemoteOpData(mId,"1","1",customer.toString());
        REMOTE_OP_DATA_MAP.put(remoteOpData.getMid(),remoteOpData);
    }

    @Override
    public void updataCustomer(@NonNull Customer customer) {
        checkNotNull(customer);
        //// TODO: 2016/6/1   录入提交服务端操作记录 操作id 操作数据类型 操作类型  操作数据
        String mId = System.currentTimeMillis()+"";
        RemoteOpData remoteOpData = new RemoteOpData(mId,"1","2",customer.toString());
        REMOTE_OP_DATA_MAP.put(remoteOpData.getMid(),remoteOpData);
    }

    @Override
    public void refreshCustomers() {

    }

    @Override
    public void deleteAllCustomers() {

    }

    @Override
    public void deleteCustomer(@NonNull String customerId) {
        checkNotNull(customerId);
        //// TODO: 2016/6/1   录入提交服务端操作记录 操作id 操作数据类型 操作类型  操作数据
        String mId = System.currentTimeMillis()+"";
        RemoteOpData remoteOpData = new RemoteOpData(mId,"1","3",customerId);
        REMOTE_OP_DATA_MAP.put(remoteOpData.getMid(),remoteOpData);
    }
}
