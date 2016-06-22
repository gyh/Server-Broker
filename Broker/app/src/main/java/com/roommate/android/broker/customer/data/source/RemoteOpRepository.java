package com.roommate.android.broker.customer.data.source;

import com.roommate.android.broker.customer.data.RemoteOp;

import org.xutils.common.util.LogUtil;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by GYH on 2016/6/18.
 */
public class RemoteOpRepository implements RemoteOpDataSource {

    private volatile static  RemoteOpRepository INSTANCE = null;

    private final RemoteOpDataSource mRemotesRemoteDataSource;

    private final RemoteOpDataSource mRemotesLocalDataSource;

    private RemoteOpRepository(RemoteOpDataSource mRemotesRemoteDataSource, RemoteOpDataSource mRemotesLocalDataSource) {
        this.mRemotesRemoteDataSource = mRemotesRemoteDataSource;
        this.mRemotesLocalDataSource = mRemotesLocalDataSource;
        LogUtil.d("初始化 操作数据知识库");
    }

    /**
     * java 设计模式 装饰模式 ，集成本地操作和线上操作，加锁并且采用原子原子性
     * @param mRemotesRemoteDataSource
     * @param mRemotesLocalDataSource
     * @return
     */
    public synchronized static RemoteOpRepository getInstance(RemoteOpDataSource mRemotesRemoteDataSource,
                                                              RemoteOpDataSource mRemotesLocalDataSource) {
        if (INSTANCE == null) {
            synchronized (RemoteOpRepository.class) {
                if(INSTANCE==null)
                    INSTANCE = new RemoteOpRepository(mRemotesRemoteDataSource, mRemotesLocalDataSource);
            }
        }

        LogUtil.d("操作数据知识库 创建 ");

        return INSTANCE;
    }


    /**
     * 获取所有数据操作
     * @param callback
     */
    @Override
    public void getRemoteOps(LoadLocalCallback callback) {
        checkNotNull(callback);
        LogUtil.d("操作数据知识库 获取所有数据操作 ");
        mRemotesLocalDataSource.getRemoteOps(callback);
    }

    @Override
    public void saveRemoteOp(RemoteOp remoteOp, OpInfoCallback opInfoCallback) {

        LogUtil.d("操作数据知识库 保存操作数据  remoteOp = " + remoteOp.toString());

        mRemotesLocalDataSource.saveRemoteOp(remoteOp,opInfoCallback);
    }

    @Override
    public void deleteRemoteOps(OpInfoCallback opInfoCallback) {

        LogUtil.d("操作数据知识库 删除所有操作数据 ");

        mRemotesLocalDataSource.deleteRemoteOps(opInfoCallback);

    }

    @Override
    public void synCustomer(List<RemoteOp> remoteOps, final OpInfoCallback opInfoCallback) {

        LogUtil.d("操作数据知识库 同步所有操作数据 ");

        mRemotesLocalDataSource.getRemoteOps(new LoadLocalCallback() {
            @Override
            public void onRemoteOpsLoader(List<RemoteOp> remoteOps) {
                mRemotesRemoteDataSource.synCustomer(remoteOps, new OpInfoCallback() {
                    @Override
                    public void onSuccess() {
                        mRemotesLocalDataSource.deleteRemoteOps(new OpInfoCallback() {
                            @Override
                            public void onSuccess() {
                                opInfoCallback.onSuccess();
                            }

                            @Override
                            public void onFail() {
                                opInfoCallback.onFail();
                            }
                        });

                    }

                    @Override
                    public void onFail() {
                        opInfoCallback.onFail();
                    }
                });
            }

            @Override
            public void onDataNotAvailable() {
                opInfoCallback.onSuccess();
            }
        });
    }
}
