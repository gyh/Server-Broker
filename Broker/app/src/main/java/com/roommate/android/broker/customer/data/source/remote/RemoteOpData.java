package com.roommate.android.broker.customer.data.source.remote;

/**
 * Created by GYH on 2016/5/28.
 */
public class RemoteOpData {

    private final String mid;
    private final String opdatatype;
    private final String optype;
    private final String opdetails;


    public RemoteOpData(String mid, String opdatatype, String optype, String opdetails) {
        this.mid = mid;
        this.opdatatype = opdatatype;
        this.optype = optype;
        this.opdetails = opdetails;
    }

    public String getMid() {
        return mid;
    }

    public String getOpdatatype() {
        return opdatatype;
    }

    public String getOptype() {
        return optype;
    }

    public String getOpdetails() {
        return opdetails;
    }
}
