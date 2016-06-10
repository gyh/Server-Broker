package com.roommate.android.broker.user;

import android.text.TextUtils;

import com.google.gson.JsonObject;
import com.roommate.android.broker.common.PreferencesUtil;
import com.roommate.android.broker.user.data.UserInfo;

import java.io.IOException;

/**
 * Created by GYH on 2016/5/23.
 */
public class UserInfoCase {

    //
    public static final String USER_KEY = "userKey";

    /**
     * 判断是否登录
     * @return
     */
    public static boolean isLogin(){
        if(getUserInfo() == null){
            return false;
        }
        return true;
    }

    /**
     * 获取用户id
     * @return
     */
    public static String getUserId(){
        UserInfo userInfo = getUserInfo();
        if(userInfo == null){
            return "-1";
        }
        return userInfo.getUserId();
    }

    /**
     * 获取用户名称
     * @return
     */
    public static String getUserName(){
        UserInfo userInfo = getUserInfo();
        if(userInfo == null){
            return "-1";
        }
        if(TextUtils.isEmpty(userInfo.getUserName())){
            return userInfo.getMobile();
        }
        return userInfo.getUserName();
    }

    /**
     * 保持本地用户数据
     * @param jsonObject
     */
    public static void saveUserInfo(JsonObject jsonObject){
        String userId = jsonObject.get("id").getAsString();
        String mobile = jsonObject.get("mobile").getAsString();
        String password = jsonObject.get("passwd").getAsString();
        String userName = "";
        if(jsonObject.get("username").isJsonNull()){
            userName = "";
        }else {
            userName = jsonObject.get("username").getAsString();
        }

        UserInfo userInfo = new UserInfo(userId,userName,password,userName);
        try {
            PreferencesUtil.setAttr(USER_KEY,PreferencesUtil.serialize(userInfo));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取用户信息
     * @return
     */
    private static UserInfo getUserInfo(){
        UserInfo userInfo = null;
        try {
             userInfo = (UserInfo) PreferencesUtil.deSerialization(PreferencesUtil.getAttrString(USER_KEY));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return userInfo;
    }
}
