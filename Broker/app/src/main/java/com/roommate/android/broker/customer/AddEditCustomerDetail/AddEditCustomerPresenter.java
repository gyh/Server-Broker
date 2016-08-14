package com.roommate.android.broker.customer.AddEditCustomerDetail;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.roommate.android.broker.customer.CustomerSo;
import com.roommate.android.broker.customer.data.Customer;
import com.roommate.android.broker.customer.data.RemoteOp;
import com.roommate.android.broker.customer.data.source.CustomerDataSource;
import com.roommate.android.broker.customer.data.source.CustomerRepository;
import com.roommate.android.broker.customer.data.source.RemoteOpDataSource;
import com.roommate.android.broker.customer.data.source.RemoteOpRepository;
import com.roommate.android.broker.customer.list.CustomerContract;

import org.xutils.common.util.LogUtil;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by GYH on 2016/6/1.
 */
public class AddEditCustomerPresenter implements AddEditCustomerContract.Presenter ,CustomerDataSource.GetCustomerCallback{


    private final RemoteOpDataSource mRemoteOpRepository;

    @NonNull
    private final CustomerDataSource mCustomerRepository;

    @NonNull
    private final AddEditCustomerContract.View mAddEditCustomerView;

    @Nullable
    private final String mCustomerId;

    public AddEditCustomerPresenter(@NonNull CustomerDataSource mCustomerRepository, @NonNull String mCustomerId, @NonNull AddEditCustomerContract.View mAddEditCustomerView, RemoteOpRepository mRemoteOpRepository) {
        this.mRemoteOpRepository = checkNotNull(mRemoteOpRepository);
        this.mAddEditCustomerView = checkNotNull(mAddEditCustomerView);
        this.mCustomerRepository = checkNotNull(mCustomerRepository);
        this.mCustomerId = mCustomerId;
        this.mAddEditCustomerView.setPresenter(this);
    }


    @Override
    public void createCustomer(String name, String phoneNumber, String desire, String houseArea, String describe, String inputDate) {
        String mId = System.currentTimeMillis()+"";
        Customer customer = new Customer(mId,name,phoneNumber,desire,houseArea,describe,inputDate);

        LogUtil.d("createCustomer" + customer.toString());

        if(customer.isEmpty()){
            mAddEditCustomerView.showEmptyCustomerError();
        }else {
            mCustomerRepository.saveCustomer(customer);
            mAddEditCustomerView.showCustomersList();

            //添加操作
            String mopId = System.currentTimeMillis()+"";
            RemoteOp remoteOp = new RemoteOp(mopId,RemoteOp.CUSTOMERDATA,RemoteOp.ADDOPT,customer.toCustomerSo());
            mRemoteOpRepository.saveRemoteOp(remoteOp, new RemoteOpDataSource.OpInfoCallback() {
                @Override
                public void onSuccess() {
                    LogUtil.d("新增  --  添加操作成功");
                }

                @Override
                public void onFail() {
                    LogUtil.d("新增  --  添加操作失败");
                }
            });

        }
    }

    @Override
    public void updateCustomer(String name, String phoneNumber, String desire, String houseArea, String describe, String inputDate) {

        checkNotNull(mCustomerId,"updateCustomer() was called but customer is new.");

        Customer customer = new Customer(mCustomerId,name,phoneNumber,desire,houseArea,describe,inputDate);

        LogUtil.d("updateCustomer" + customer.toString());

        if(customer.isEmpty()){
            mAddEditCustomerView.showEmptyCustomerError();
        }else {
            mCustomerRepository.updataCustomer(customer);
            mAddEditCustomerView.showCustomersList();

            //添加操作
            String mopId = System.currentTimeMillis()+"";

            RemoteOp remoteOp = new RemoteOp(mopId,RemoteOp.CUSTOMERDATA,RemoteOp.UPDOPT,customer.toCustomerSo());
            mRemoteOpRepository.saveRemoteOp(remoteOp, new RemoteOpDataSource.OpInfoCallback() {
                @Override
                public void onSuccess() {
                    LogUtil.d("更新  --  添加操作成功");
                }

                @Override
                public void onFail() {
                    LogUtil.d("更新  --  添加操作失败");
                }
            });
        }
    }

    @Override
    public void populateCustomer() {

        checkNotNull(mCustomerId,"updateCustomer() was called but customer is new.");

        mCustomerRepository.getCustomer(mCustomerId, this);

    }

    @Override
    public void importPhone() {
        mAddEditCustomerView.showDialogPhoneList();
    }

    @Override
    public void start() {
        if(mCustomerId!=null){
            populateCustomer();
        }
    }

    @Override
    public void onCustomerLoader(Customer customer) {
        if(mAddEditCustomerView.isActive()){
            mAddEditCustomerView.setName(customer.getName());
            mAddEditCustomerView.setPhoneNumber(customer.getPhoneNumber());
            mAddEditCustomerView.setDesire(customer.getDesire());
            mAddEditCustomerView.setHouseArea(customer.getHouseArea());
            mAddEditCustomerView.setDescries(customer.getDescribe());
            mAddEditCustomerView.setOrderDate(customer.getInputDate());
        }
    }

    @Override
    public void onDataNotAvailable() {
        if(mAddEditCustomerView.isActive()){
            mAddEditCustomerView.showEmptyCustomerError();
        }
    }
}
