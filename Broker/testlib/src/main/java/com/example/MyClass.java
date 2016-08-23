package com.example;

import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;
import java.util.HashMap;
import java.util.Map;

public class MyClass {

    public static final String BASE_URL = "http://120.27.7.127:8080";

    public static final String TEST_BASE_URL = "http://192.168.3.103:8080";

    public static String LOGIN_URL=BASE_URL+"/customer/user/login";

    public static void main(String args[]) {
        Map<String,String> stringMap =new HashMap<>();
        stringMap.put("mobile","12345");
        stringMap.put("passwd","123456");
        String gson =  new Gson().toJson(stringMap);
        RequestParams params = new RequestParams(LOGIN_URL);
        params.addBodyParameter("Data",gson);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(final String result) {
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
}
