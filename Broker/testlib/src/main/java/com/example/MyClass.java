package com.example;

import com.example.customer.CustomerSo;
import com.example.customer.RemoteOp;
import com.example.http.HttpRequester;
import com.example.http.HttpRespons;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyClass {

    public static final String BASE_URL = "http://120.27.7.127:8080";

    public static final String TEST_BASE_URL = "http://192.168.3.103:8080";

    public static void main(String args[]) {

        try {

            List<RemoteOp> remoteOps = new ArrayList<>();
            CustomerSo customerSo = new CustomerSo();
            customerSo.setMobile("13753648500");
            customerSo.setName("郭跃华001");
            customerSo.setId("1471168814824");
            customerSo.setHouseArea("70");
            customerSo.setAppointTime("2016-08-14 17:59:00");
            customerSo.setBuyDemand("7");
            customerSo.setUserId(31L);
            customerSo.setRemark("wo shi guoyuehua");
            RemoteOp remoteOp = new RemoteOp("1471168815314","1","1",customerSo);
            remoteOps.add(remoteOp);

            String gons = new Gson().toJson(remoteOps);

            HttpRequester request = new HttpRequester();
            request.setDefaultContentEncoding("utf-8");
            Map<String,String> stringMap = new HashMap<>();
            stringMap.put("Data",gons);
            HttpRespons hr = request.sendPost(TEST_BASE_URL+"/customer/customer/synDada",stringMap);
            System.out.println(hr.getUrlString());
            System.out.println(hr.getProtocol());
            System.out.println(hr.getHost());
            System.out.println(hr.getPort());
            System.out.println(hr.getContentEncoding());
            System.out.println(hr.getMethod());
            System.out.println(hr.getContent());
            String sr = hr.getContent();
            if(JsonUtils.isResultSuccess(sr)){
            System.out.println("成功 -- " +sr);
        }else {
            System.out.println("失败 -- " +sr);
        }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
