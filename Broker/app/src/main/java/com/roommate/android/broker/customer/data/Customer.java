package com.roommate.android.broker.customer.data;

import android.support.annotation.Nullable;

import com.google.common.base.Objects;

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

    public boolean isEmpty() {
        return (mId == null || "".equals(name)) &&
                ("".equals(phoneNumber))&&
                ("".equals(houseArea) || "".equals(desire))&&
                ( "".equals(describe) || "".equals(inputDate));
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
        return "Customer with name " + name;
    }
}
