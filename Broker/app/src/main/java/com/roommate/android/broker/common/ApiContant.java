package com.roommate.android.broker.common;

/**
 * Created by GYH on 2016/3/26.
 */
public class ApiContant {

    public static boolean isDebug = true;

    public static final String BASE_URL = "http://192.168.1.109:8080";


    public static final String REQ_STR = "squer";

    public static String LOGIN_URL="/user/addUser";

    public static String REGISTER_URL = "";

    static {
        if(isDebug){
            LOGIN_URL=BASE_URL+LOGIN_URL;
            REGISTER_URL = BASE_URL+REGISTER_URL;
        }
    }

}
