package com.roommate.android.broker.customer.data.source.remote;

import com.google.gson.Gson;
import com.roommate.android.broker.common.ApiContant;
import com.roommate.android.broker.common.JsonUtils;
import com.roommate.android.broker.customer.data.RemoteOp;
import com.roommate.android.broker.customer.data.source.RemoteOpDataSource;

import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by GYH on 2016/6/18.
 */
public class RemoteOpRemoteDataScource implements RemoteOpDataSource {


    private static RemoteOpRemoteDataScource INSTANCE;


    // Prevent direct instantiation.
    private RemoteOpRemoteDataScource() {}

    public static RemoteOpRemoteDataScource getInstance() {

        LogUtil.d("创建线上操作数据  初始化线上数据操作");

        if (INSTANCE == null) {
            INSTANCE = new RemoteOpRemoteDataScource();
        }
        return INSTANCE;
    }


    @Override
    public void getRemoteOps(LoadLocalCallback callback) {

    }

    @Override
    public void saveRemoteOp(RemoteOp remoteOp, OpInfoCallback opInfoCallback) {

    }

    @Override
    public void deleteRemoteOps(OpInfoCallback opInfoCallback) {

    }

    /**
     * 发送请求  同步本地操作
     * @param remoteOps
     * @param opInfoCallback
     */
    @Override
    public void synCustomer(List<RemoteOp> remoteOps , final OpInfoCallback opInfoCallback) {

        checkNotNull(remoteOps);

        checkNotNull(opInfoCallback);

        LogUtil.d("线上操作数据  同步数据 remoteOps.size = " + remoteOps.size());

        RequestParams params = new RequestParams(ApiContant.CUSTOMER_SYNDATA_URL);

        String gson =  new Gson().toJson(remoteOps);

        LogUtil.d("线上操作数据  同步数据 gson = " + gson);

        params.addBodyParameter(ApiContant.DATA,gson);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(final String result) {

                LogUtil.d("线上操作数据  同步数据 result = " + result);

                LogUtil.d("synCustomer(result):  "+result);
                if(JsonUtils.isResultSuccess(result)){
                    opInfoCallback.onSuccess();
                }else {
                    opInfoCallback.onFail();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                opInfoCallback.onFail();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
}
