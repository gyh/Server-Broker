package com.roommate.android.broker.common;

/**
 * Created by GYH on 2016/3/26.
 */
public class ApiContant {

    public static boolean isDebug = true;

    //基础地址218.29.188.22   172.16.5.82
    public static final String BASE_URL = "http://218.29.188.22:8080";

    //登录地址
    public static String LOGIN_URL = "";

    //注册
    public static String REGISTER_URL = "";

    public static String FARM_URL = "";

    public static final String REQ_STR = "req_str";

    public static final String KEY_LISTITEM = "keyListItem";

    static {
        LOGIN_URL = BASE_URL + "/farm/MainServlet";
        REGISTER_URL = BASE_URL + "/farm/MainServlet";
        FARM_URL = BASE_URL + "/farm/MainServlet";
    }


}
