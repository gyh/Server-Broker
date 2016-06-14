package com.customer.common.enums;

/**
 * Created by wangpeng on 2016/6/10.
 */
public enum OptTypsEnum {
        ADD("1","添加"),
        DEL("2","删除"),
        UPDATE("3","修改");

        private String key;
        private String value;

    private OptTypsEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public static OptTypsEnum get(String key){
        for(OptTypsEnum enumObj:OptTypsEnum.values()){
            if(key.equals(enumObj.key)){
                return enumObj;
            }
        }
        return null;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
