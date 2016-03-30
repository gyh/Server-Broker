package com.ant.common;

/**
 * Created by wangpeng-ds3 on 2016/3/29.
 */

public final class StringUtils {

    public static boolean isNullorBlank(String value){
        return (null == value) || ("".equals(value.trim()));
    }
    public static boolean isBlank(String astr) {
        return (null == astr) || (astr.length() == 0);
    }


}
