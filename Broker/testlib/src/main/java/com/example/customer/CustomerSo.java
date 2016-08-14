package com.example.customer;

import com.google.gson.Gson;

import java.util.Date;

/**
 * Created by 郭跃华 on 2016/8/14.
 */

public class CustomerSo {
    private String id;

    private Long userId;

    private String name;
    /**
     * 电话
     */
    private String mobile;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 客户类型：0买房子 1卖房子
     */
    private Integer customerType;

    /**
     * 是否删除 0没删除，1：逻辑删除
     */
    private Integer isdelete;

    /**
     * 购买需求
     */
    private String buyDemand;

    /**
     * 购买欲望强度，1-12个月 越高欲望越大
     */
    private Integer buyPower;

    /**
     * 房屋面积
     */
    private String houseArea;

    /**
     * 预约时间
     */
    private String appointTime;

    /**
     * 用户意愿
     */
    private String remark;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setBuyDemand(String buyDemand) {
        this.buyDemand = buyDemand;
    }

    public void setIsdelete(Integer isdelete) {
        this.isdelete = isdelete;
    }

    public void setCustomerType(Integer customerType) {
        this.customerType = customerType;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setAppointTime(String appointTime) {
        this.appointTime = appointTime;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setBuyPower(Integer buyPower) {
        this.buyPower = buyPower;
    }

    public void setHouseArea(String houseArea) {
        this.houseArea = houseArea;
    }

    public String getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getMobile() {
        return mobile;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Integer getCustomerType() {
        return customerType;
    }

    public Integer getIsdelete() {
        return isdelete;
    }

    public String getBuyDemand() {
        return buyDemand;
    }

    public Integer getBuyPower() {
        return buyPower;
    }

    public String getHouseArea() {
        return houseArea;
    }

    public String getAppointTime() {
        return appointTime;
    }

    @Override
    public String toString() {
        String str;
        CustomerSo customerSo = new CustomerSo();
        customerSo.setRemark(remark);
        customerSo.setUserId(userId);
        customerSo.setId(id);
        customerSo.setAppointTime(appointTime);
        customerSo.setBuyDemand(buyDemand);
        customerSo.setBuyPower(buyPower);
        customerSo.setCreateTime(createTime);
        customerSo.setCustomerType(customerType);
        customerSo.setHouseArea(houseArea);
        customerSo.setMobile(mobile);
        customerSo.setName(name);
        str = new Gson().toJson(customerSo);
        return str;
    }
}
