package com.roommate.android.broker.customer.fitter;

import android.support.annotation.NonNull;

import com.roommate.android.broker.customer.data.Customer;
import com.roommate.android.broker.customer.data.source.CustomerDataSource;
import com.roommate.android.broker.customer.data.source.CustomerRepository;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by GYH on 2016/6/8.
 */
public class FitterCustomerPresenter implements FitterCustomerContract.Presenter{


    private final CustomerRepository mCustomerRepository;

    private final FitterCustomerContract.View mCustomersView;

    public FitterCustomerPresenter(@NonNull CustomerRepository mCustomerRepository, @NonNull FitterCustomerContract.View mCustomersView) {
        this.mCustomerRepository = checkNotNull(mCustomerRepository,"mCustomerRepository  is not null");
        this.mCustomersView = checkNotNull(mCustomersView,"mCustomersView is not null");
        this.mCustomersView.setPresenter(this);
    }

    @Override
    public void fitterCustomer(String dateStr) {

        checkNotNull(dateStr,"fitter date is not null");

        mCustomersView.setLoadingIndicator(true);

        final List<Customer> customers = new ArrayList<>();

        mCustomerRepository.searchInputDate(dateStr, new CustomerDataSource.LoadCustomersCallback() {
            @Override
            public void onCustomersLoader(List<Customer> customerList) {
                customers.addAll(customerList);

                mCustomersView.setLoadingIndicator(false);

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

    @Override
    public void start() {
        if(mCustomersView.isActive()){
            mCustomersView.hideListView();
        }
    }
}
