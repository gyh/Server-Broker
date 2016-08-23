package com.roommate.android.broker.customer.data;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.google.common.base.Objects;
import com.google.gson.Gson;
import com.roommate.android.broker.customer.CustomerSo;
import com.roommate.android.broker.user.UserInfoCase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by GYH on 2016/5/27.
 */
public final class Customer {

    private final String mId;

    private final String name;

    private final String phoneNumber;

    //购买意愿
    private final String desire;
    
    private final String houseArea;
    
    //描述
    private final String describe;

    //录入时间
    private final String inputDate;


    public Customer(@Nullable String mId,@Nullable String name,@Nullable String phoneNumber,@Nullable String desire,@Nullable String houseArea,@Nullable String describe,@Nullable String inputDate) {
        this.mId = mId;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.desire = desire;
        this.houseArea = houseArea;
        this.describe = describe;


        this.inputDate = inputDate;
    }

    public String getmId() {
        checkNotNull(mId,"customer'id is not null");
        return mId;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        checkNotNull(mId,"customer'phonenumber is not null");
        return phoneNumber;
    }

    public String getDesire() {
        return desire;
    }

    public String getHouseArea() {
        return houseArea;
    }

    public String getDescribe() {
        return describe;
    }

    public String getInputDate() {
        return inputDate;
    }

    /**
     * 判空  id不能为空
     * 名字不能为空
     * 手机号不能为空
     * 面积或者购房时间不能为空
     * 描述或者预定日期可以为空
     * @return
     */
    public boolean isEmpty() {
        return (TextUtils.isEmpty(mId)) &&
                ("".equals(name))&&
                ("".equals(phoneNumber))&&
                ("".equals(houseArea) || "".equals(desire));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer task = (Customer) o;
        return Objects.equal(mId, task.mId) &&
                Objects.equal(name, task.name) &&
                Objects.equal(houseArea, task.houseArea)&&
                Objects.equal(desire, task.desire)&&
                Objects.equal(describe, task.describe)&&
                Objects.equal(phoneNumber, task.phoneNumber)&&
                Objects.equal(inputDate, task.inputDate);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mId, name, houseArea,phoneNumber,desire,describe,inputDate);
    }

    @Override
    public String toString() {
        //todo 需要将数据转换为可以用的数据
        CustomerSo customer = new CustomerSo();
        customer.setId(mId);
        customer.setMobile(phoneNumber);
        customer.setAppointTime(inputDate);
        customer.setBuyPower(Integer.valueOf(desire));
        customer.setHouseArea(houseArea);
        customer.setName(name);
        customer.setUserId(Long.valueOf(UserInfoCase.getUserId()));
        customer.setRemark(describe);
        return new Gson().toJson(customer);
    }

    public CustomerSo toCustomerSo(){
        CustomerSo customer = new CustomerSo();
        customer.setId(mId);
        customer.setMobile(phoneNumber);
        customer.setAppointTime(inputDate);
        customer.setBuyPower(Integer.valueOf(desire));
        customer.setHouseArea(houseArea);
        customer.setName(name);
        customer.setUserId(Long.valueOf(UserInfoCase.getUserId()));
        return customer;
    }

}
