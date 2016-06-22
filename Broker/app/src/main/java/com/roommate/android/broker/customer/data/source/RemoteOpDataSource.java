package com.roommate.android.broker.customer.data.source;

import com.roommate.android.broker.customer.data.RemoteOp;

import java.util.List;

/**
 * Created by GYH on 2016/6/18.
 */
public interface RemoteOpDataSource {


    interface LoadLocalCallback{

        void onRemoteOpsLoader(List<RemoteOp> remoteOps);

        void onDataNotAvailable();
    }

    interface OpInfoCallback{

        void onSuccess();

        void onFail();
    }

    void getRemoteOps(LoadLocalCallback callback);

    void saveRemoteOp(RemoteOp remoteOp,OpInfoCallback opInfoCallback);

    void deleteRemoteOps(OpInfoCallback opInfoCallback);

    void synCustomer(List<RemoteOp> remoteOps , OpInfoCallback opInfoCallback);
}
