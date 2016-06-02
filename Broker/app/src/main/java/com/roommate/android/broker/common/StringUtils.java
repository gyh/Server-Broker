package com.roommate.android.broker.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by GYH on 2016/6/2.
 */
public class StringUtils {

    private static final String regEx = "[`~@#$%^&*()+=|{}''\\[\\].<>/~@#￥%……&*——+|{}【】_]";

    //特殊字符集
    private  static  Pattern p = null;

    /**
     * 是否包含特殊字符
     * @return
     */
    public static boolean IshasSpecialChar(String string){
        //查找用户和记录有没有特殊字符
        if(p == null){
            p = Pattern.compile(regEx);
        }
        Matcher usernamem = p.matcher(string);

        if(usernamem.find()){
           return true;
        }else {
            return false;
        }
    }
}
