package com.customer.common;

/**
 * 操作符号枚举：账户(-):0  账户(+):1
 */
public enum CustomerTypeEnum {

    BUYER(0,"买房"),
    SALES(1,"卖房");


    private int key;
    private String value;

    CustomerTypeEnum(int key, String value) {
        this.key = key;
        this.value = value;
    }

    public static String getvalueByKey(int key) {
        for (CustomerTypeEnum obj : CustomerTypeEnum.values()) {
            if (obj.getKey() == key) {
                return obj.getValue();
            }
        }
        return null;
    }
    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
