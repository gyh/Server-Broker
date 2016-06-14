package com.customer.model;

import java.util.Date;

public class Customer {

    private Long id;

    private Long userId;

    private String name;

    private String mobile;

    private Date createTime;

    private Integer customerType;

    private Integer isdelete;

    private String buyDemand;

    private Integer buyPower;

    private String houseArea;

    private Date appointTime;

    public String getHouseArea() {
        return houseArea;
    }

    public void setHouseArea(String houseArea) {
        this.houseArea = houseArea;
    }

    public Date getAppointTime() {
        return appointTime;
    }

    public void setAppointTime(Date appointTime) {
        this.appointTime = appointTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getCustomerType() {
        return customerType;
    }

    public void setCustomerType(Integer customerType) {
        this.customerType = customerType;
    }

    public Integer getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(Integer isdelete) {
        this.isdelete = isdelete;
    }

    public String getBuyDemand() {
        return buyDemand;
    }

    public void setBuyDemand(String buyDemand) {
        this.buyDemand = buyDemand == null ? null : buyDemand.trim();
    }

    public Integer getBuyPower() {
        return buyPower;
    }

    public void setBuyPower(Integer buyPower) {
        this.buyPower = buyPower;
    }
}