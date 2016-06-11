package com.roommate.android.broker.common;

/**
 * Created by GYH on 2016/3/26.
 */
public class ApiContant {

    public static boolean isDebug = true;

    public static final String BASE_URL = "http://broker.applinzi.com";


    public static final String DATA = "Data";

    public static String LOGIN_URL="/user/login";

    public static String REGISTER_URL = "/user/register";

    public static String CUSTOMER_SYNDATA_URL="/cutomer/synDada";

    public static String CUSTOMER_GETDATA_URL="/cutomer/getData";

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
