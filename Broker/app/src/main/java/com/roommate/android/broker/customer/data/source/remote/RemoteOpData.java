package com.roommate.android.broker.customer.data.source.remote;

/**
 * Created by GYH on 2016/5/28.
 */
public class RemoteOpData {

    public static final String CUSTOMERDATA = "2";

    public static final String ADDOPT = "1";
    public static final String DELOPT = "2";
    public static final String UPDOPT = "3";

    //用户id
    private final String userId;
    //操作对象  1 客户
    private final String optObject;
    //操作类型  1 增加  2 删除 3 修改
    private final String optType;
    //数据
    private final String optData;


    public RemoteOpData(String userId, String optObject, String optType, String optData) {
        this.userId = userId;
        this.optObject = optObject;
        this.optType = optType;
        this.optData = optData;
    }

    public String getOptData() {
        return optData;
    }

    public String getUserId() {
        return userId;
    }

    public String getOptObject() {
        return optObject;
    }

    public String getOptType() {
        return optType;
    }
}
