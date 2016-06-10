package com.roommate.android.broker.user.data;

import java.io.Serializable;

/**
 * Created by GYH on 2016/5/23.
 */
public class UserInfo implements Serializable{
    private final String userId;
    private final String userName;
    private final String password;
    private final String mobile;


    public UserInfo(String userId, String userName, String password, String mobile) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.mobile = mobile;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getMobile() {
        return mobile;
    }

    public String getPassword() {
        return password;
    }
}
