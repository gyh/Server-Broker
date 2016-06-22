package com.roommate.android.broker.customer.data;

/**
 * Created by GYH on 2016/5/28.
 */
public class RemoteOp {

    public static final String CUSTOMERDATA = "1";

    public static final String ADDOPT = "1";
    public static final String DELOPT = "2";
    public static final String UPDOPT = "3";

    private final String opId;
    //用户id
    private  String userId;
    //操作对象  1 客户
    private final String optObject;
    //操作类型  1 增加  2 删除 3 修改
    private final String optType;
    //数据
    private final String optData;


    public RemoteOp(String opId, String optObject, String optType, String optData) {
        this.opId = opId;
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

    public String getOpId() {
        return opId;
    }

    @Override
    public String toString() {
        return opId+" "+ optType + " "+optObject+" "+ optData;
    }
}
