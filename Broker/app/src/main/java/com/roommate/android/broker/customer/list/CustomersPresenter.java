/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.roommate.android.broker.customer.list;

import android.app.Activity;
import android.support.annotation.NonNull;


import com.roommate.android.broker.customer.data.Customer;
import com.roommate.android.broker.customer.data.source.CustomerDataSource;
import com.roommate.android.broker.customer.data.source.CustomerRepository;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Listens to user actions from the UI ({@link FirstFragment}), retrieves the data and updates the
 * UI as required.
 */
public class CustomersPresenter implements CustomerContract.Presenter {


    private final CustomerRepository mCustomerRepository;

    private final CustomerContract.View mCustomersView;

    private boolean mFirstLoad = true;

    public CustomersPresenter(@NonNull CustomerRepository mCustomerRepository,@NonNull CustomerContract.View mCustomersView) {
        this.mCustomerRepository = checkNotNull(mCustomerRepository,"mCustomerRepository  is not null");
        this.mCustomersView = checkNotNull(mCustomersView,"mCustomersView is not null");

        mCustomersView.setPresenter(this);
    }



    @Override
    public void result(int requestCode, int resultCode) {
        if(requestCode==CustomerListFragment.ADD_CUSTOMER){
            if(resultCode == Activity.RESULT_OK){
                loadCustomers(false);
            }
        }else {
            if(requestCode == CustomerListFragment.EDIT_CUSTOMER){
                if(resultCode == Activity.RESULT_OK){
                    loadCustomers(false);
                }
            }
        }
    }

    @Override
    public void loadCustomers(boolean forceUpdate) {
        loadCustomers(forceUpdate||mFirstLoad,true);
        mFirstLoad = false;
    }

    @Override
    public void addNewCustomer() {
        mCustomersView.showAddCustomer();
    }

    @Override
    public void openCustomerDetails(@NonNull Customer requestedCustomer) {
        checkNotNull(requestedCustomer, "requestedCustomer cannot be null!");
        mCustomersView.showCustomerDetailsUi(requestedCustomer.getmId());
    }

    @Override
    public void synchronization() {
        loadCustomers(true);
    }

    @Override
    public void refershData() {
        loadCustomers(false,false);
    }

    @Override
    public void editCustomer(String customerId) {
        mCustomersView.showEditCustomer(customerId);
    }

    @Override
    public void delCustomer(String customerId) {
        //删除数据层的数据
        mCustomerRepository.deleteCustomer(customerId);
        //更新删除视图层的数据
        mCustomerRepository.getCustomers(new CustomerDataSource.LoadCustomersCallback() {
            @Override
            public void onCustomersLoader(List<Customer> customers) {
                List<Customer> customerList = new ArrayList<>();
                customerList.addAll(customers);
                processCustomers(customerList);
                mCustomersView.showSuccessfullyDeletedMessage();
            }

            @Override
            public void onDataNotAvailable() {
                mCustomersView.showErrorfullyDeletedMessage();
            }
        });
    }

    @Override
    public void start() {
        loadCustomers(false);
    }

    /**
     * @param forceUpdate   Pass in true to refresh the data in the {@link CustomerDataSource}
     * @param showLoadingUI Pass in true to display a loading icon in the UI
     */
    private void loadCustomers(boolean forceUpdate, final boolean showLoadingUI) {
        //判断是否显示ui加载
        if (showLoadingUI) {
            mCustomersView.setLoadingIndicator(true);
        }
        //判断是否强制刷新
        if (forceUpdate) {
            mCustomerRepository.refreshCustomers();
        }
        //获取数据
        mCustomerRepository.getCustomers(new CustomerDataSource.LoadCustomersCallback(){
            @Override
            public void onCustomersLoader(List<Customer> customers) {
                List<Customer> customerList = new ArrayList<>();
                customerList.addAll(customers);
                if (!mCustomersView.isActive()) {
                    return;
                }
                if (showLoadingUI) {
                    mCustomersView.setLoadingIndicator(false);
                }
                processCustomers(customerList);
            }

            @Override
            public void onDataNotAvailable() {
                // The view may not be able to handle UI updates anymore
                if (!mCustomersView.isActive()) {
                    return;
                }
                mCustomersView.showLoadingCustomersError();
            }
        });
    }

    /**
     * 显示列表流程
     * @param customerList
     */
    private void processCustomers(List<Customer> customerList) {
        if (customerList.isEmpty()) {
            // Show a message indicating there are no tasks for that filter type.
            mCustomersView.showNoCustomers();
        } else {
            // Show the list of tasks
            mCustomersView.showCustomers(customerList);
        }
    }
}
