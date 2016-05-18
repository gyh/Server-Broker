package com.customer.common;

/**
 * 操作符号枚举：账户(-):0  账户(+):1
 */
public enum HouseTypeEnum {

    NEW(0,"一手房"),
    OLD(1,"二手房");


    private int key;
    private String value;

    HouseTypeEnum(int key, String value) {
        this.key = key;
        this.value = value;
    }

    public static String getvalueByKey(int key) {
        for (HouseTypeEnum obj : HouseTypeEnum.values()) {
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
