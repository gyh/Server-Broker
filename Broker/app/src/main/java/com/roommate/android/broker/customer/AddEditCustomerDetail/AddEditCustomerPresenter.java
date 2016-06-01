package com.roommate.android.broker.customer.AddEditCustomerDetail;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.roommate.android.broker.customer.data.Customer;
import com.roommate.android.broker.customer.data.source.CustomerDataSource;
import com.roommate.android.broker.customer.data.source.CustomerRepository;
import com.roommate.android.broker.customer.list.CustomerContract;

import org.xutils.common.util.LogUtil;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by GYH on 2016/6/1.
 */
public class AddEditCustomerPresenter implements AddEditCustomerContract.Presenter ,CustomerDataSource.GetCustomerCallback{


    @NonNull
    private final CustomerDataSource mCustomerRepository;

    @NonNull
    private final AddEditCustomerContract.View mAddEditCustomerView;

    @Nullable
    private final String mCustomerId;

    public AddEditCustomerPresenter(@NonNull CustomerDataSource mCustomerRepository, @NonNull String mCustomerId, @NonNull AddEditCustomerContract.View mAddEditCustomerView) {
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
            mCustomerRepository.saveCustomer(customer);
            mAddEditCustomerView.showCustomersList();
        }
    }

    @Override
    public void populateCustomer() {

        checkNotNull(mCustomerId,"updateCustomer() was called but customer is new.");

        mCustomerRepository.getCustomer(mCustomerId, this);

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
