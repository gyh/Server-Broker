package com.roommate.android.broker.customer.data.source.remote;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.roommate.android.broker.common.ApiContant;
import com.roommate.android.broker.common.JsonUtils;
import com.roommate.android.broker.customer.CustomerSo;
import com.roommate.android.broker.customer.data.RemoteOp;
import com.roommate.android.broker.user.UserInfoCase;
import com.roommate.android.broker.customer.data.Customer;
import com.roommate.android.broker.customer.data.source.CustomerDataSource;

import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
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


    // Prevent direct instantiation.
    private CustomerRemoteDataSource() {}

    public static CustomerRemoteDataSource getInstance() {

        LogUtil.d("创建线上客户数据 初始化线上数据操作");

        if (INSTANCE == null) {
            INSTANCE = new CustomerRemoteDataSource();
        }
        return INSTANCE;
    }


    @Override
    public void getCustomers(@NonNull final LoadCustomersCallback callback) {

        checkNotNull(callback);

        LogUtil.d("线上客户数据 获取线上全部数据");

        RequestParams params = new RequestParams(ApiContant.CUSTOMER_GETDATA_URL);

        Map<String,String> stringMap = new HashMap<>();
        stringMap.put("userId",UserInfoCase.getUserId());
        String gson =  new Gson().toJson(stringMap);

        params.addBodyParameter(ApiContant.DATA,gson);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(final String result) {
                LogUtil.d("线上客户数据 获取线上全部数据  result = "+ result);
                if(TextUtils.isEmpty(result)){
                    callback.onDataNotAvailable();
                }else {
                    if(JsonUtils.isResultSuccess(result)){
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                ArrayList<Customer> strings = new ArrayList<>();
                                JsonParser jsonParser = new JsonParser();
                                JsonElement jsonElement = jsonParser.parse(result);
                                JsonObject jsonObject = jsonElement.getAsJsonObject();
                                JsonArray rows = jsonObject.get("data").getAsJsonArray();
                                for(int i=0;i<rows.size();i++){
                                    JsonObject row = rows.get(i).getAsJsonObject();
                                    CustomerSo customerSo = new Gson().fromJson(row,CustomerSo.class);
                                    Customer house = new Customer(
                                            customerSo.getId(),
                                            customerSo.getName(),
                                            customerSo.getMobile(),
                                            customerSo.getBuyPower()+"",
                                            customerSo.getHouseArea(),
                                            customerSo.getRemark(),
                                            customerSo.getAppointTime());
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
                    }else {
                        callback.onDataNotAvailable();
                    }
                }
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

    }

    @Override
    public void saveCustomer(@NonNull Customer customer) {
    }

    @Override
    public void updataCustomer(@NonNull Customer customer) {

    }

    @Override
    public void refreshCustomers() {

    }

    @Override
    public void deleteAllCustomers() {

    }

    @Override
    public void deleteCustomer(@NonNull String customerId) {

    }

    @Override
    public void searchPhoneNumber(@NonNull String phoneNumber,@NonNull LoadCustomersCallback callback) {

    }

    @Override
    public void searchName(@NonNull String name,@NonNull LoadCustomersCallback callback) {

    }

    @Override
    public void searchInputDate(@NonNull String dateStr, @NonNull LoadCustomersCallback callback) {

    }
}
