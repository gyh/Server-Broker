package com.roommate.android.broker.common;

/**
 * Created by GYH on 2016/3/26.
 */
public class ApiContant {

    public static boolean isDebug = true;

    public static final String BASE_URL = "http://120.27.7.127:8080";

    public static final String DATA = "Data";

    public static String LOGIN_URL="/customer/user/login";

    public static String REGISTER_URL = "/customer/user/register";

    public static String CUSTOMER_SYNDATA_URL="/customer/cutomer/synDada";

    public static String CUSTOMER_GETDATA_URL="/customer/cutomer/getData";

    static {
        if(isDebug){
            LOGIN_URL=BASE_URL+LOGIN_URL;
            REGISTER_URL = BASE_URL+REGISTER_URL;
        }else {

        }
    }

    public class  KEYVAULE{
        public static final String IS_FIRST = "isfirst";
    }
}
