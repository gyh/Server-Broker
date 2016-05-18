package com.customer.common;

/**
 * 操作符号枚举：账户(-):0  账户(+):1
 */
public enum HouseStructEnum {

    one(0,"一居室"),
    two(1,"二室一厅"),
    third(2,"三室一厅"),
    four(3,"三室两厅");


    private int key;
    private String value;

    HouseStructEnum(int key, String value) {
        this.key = key;
        this.value = value;
    }

    public static String getvalueByKey(int key) {
        for (HouseStructEnum obj : HouseStructEnum.values()) {
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
