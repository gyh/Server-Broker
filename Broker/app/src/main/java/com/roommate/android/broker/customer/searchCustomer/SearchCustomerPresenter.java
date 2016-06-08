package com.roommate.android.broker.customer.searchCustomer;

import android.support.annotation.NonNull;

import com.roommate.android.broker.common.StringUtils;
import com.roommate.android.broker.customer.data.Customer;
import com.roommate.android.broker.customer.data.source.CustomerDataSource;
import com.roommate.android.broker.customer.data.source.CustomerRepository;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by GYH on 2016/6/6.
 */
public class SearchCustomerPresenter implements SearchCustomerContract.Presenter{


    private final CustomerRepository mCustomerRepository;

    private final SearchCustomerContract.View mCustomersView;

    public SearchCustomerPresenter(@NonNull CustomerRepository mCustomerRepository,@NonNull SearchCustomerContract.View mCustomersView) {
        this.mCustomerRepository = checkNotNull(mCustomerRepository,"mCustomerRepository  is not null");
        this.mCustomersView = checkNotNull(mCustomersView,"mCustomersView is not null");
        this.mCustomersView.setPresenter(this);
    }


    @Override
    public void searchCustomer(final String msg) {

        mCustomersView.setLoadingIndicator(true);
        //判断输入的是否都为数字
        final List<Customer> customers = new ArrayList<>();
        if(StringUtils.isNumeric(msg)){
            //都为数字
            mCustomerRepository.searchPhoneNumber(msg, new CustomerDataSource.LoadCustomersCallback() {
                @Override
                public void onCustomersLoader(List<Customer> customerList) {

                    mCustomersView.setLoadingIndicator(false);

                    customers.addAll(customerList);
                    if(mCustomersView.isActive()){

                        mCustomersView.showCustomers(customers);
                    }
                }

                @Override
                public void onDataNotAvailable() {

                    mCustomersView.setLoadingIndicator(false);

                    if(mCustomersView.isActive()){
                        mCustomersView.showNoCustomers();
                    }
                }
            });
        }else {
            //数字和名字

            mCustomerRepository.searchName(msg, new CustomerDataSource.LoadCustomersCallback() {
                @Override
                public void onCustomersLoader(List<Customer> customerList) {

                    mCustomersView.setLoadingIndicator(false);

                    customers.addAll(customerList);
                    if(mCustomersView.isActive()){
                        mCustomersView.showCustomers(customers);
                    }
                }

                @Override
                public void onDataNotAvailable() {

                    mCustomersView.setLoadingIndicator(false);

                    if(mCustomersView.isActive()){
                        mCustomersView.showNoCustomers();
                    }
                }
            });
        }
    }

    @Override
    public void editCustomer(String customerId) {
        if(mCustomersView.isActive()){
            mCustomersView.showEditCustomer(customerId);
        }
    }

    @Override
    public void delCustomer(String customerId) {
        if(mCustomersView.isActive()){
            mCustomersView.showEditCustomer(customerId);
        }
    }

    @Override
    public void start() {
        if(mCustomersView.isActive()){
            mCustomersView.hideListView();
        }
    }
}
