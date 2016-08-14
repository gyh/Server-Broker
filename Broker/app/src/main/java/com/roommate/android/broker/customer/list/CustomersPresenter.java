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
import android.os.Handler;
import android.support.annotation.NonNull;


import com.roommate.android.broker.common.ApiContant;
import com.roommate.android.broker.common.DateUtils;
import com.roommate.android.broker.common.PreferencesUtil;
import com.roommate.android.broker.customer.CustomerSo;
import com.roommate.android.broker.customer.data.Customer;
import com.roommate.android.broker.customer.data.RemoteOp;
import com.roommate.android.broker.customer.data.source.CustomerDataSource;
import com.roommate.android.broker.customer.data.source.CustomerRepository;
import com.roommate.android.broker.customer.data.source.RemoteOpDataSource;
import com.roommate.android.broker.customer.data.source.RemoteOpRepository;
import com.roommate.android.broker.user.UserInfoCase;

import org.xutils.common.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Listens to user actions from the UI ({@link }), retrieves the data and updates the
 * UI as required.
 */
public class CustomersPresenter implements CustomerContract.Presenter {

    private final RemoteOpRepository mRemoteOpRepository;

    private final CustomerDataSource mCustomerRepository;

    private final CustomerContract.View mCustomersView;


    public CustomersPresenter(@NonNull CustomerRepository mCustomerRepository, @NonNull CustomerContract.View mCustomersView, @NonNull RemoteOpRepository mRemoteOpRepository) {
        this.mRemoteOpRepository = checkNotNull(mRemoteOpRepository,"mRemoteOpRepository is not null");
        this.mCustomerRepository = checkNotNull(mCustomerRepository,"mCustomerRepository  is not null");
        this.mCustomersView = checkNotNull(mCustomersView,"mCustomersView is not null");

        mCustomersView.setPresenter(this);


        LogUtil.d("----- 初始化客户导演 ------");
    }



    @Override
    public void result(int requestCode, int resultCode) {
        LogUtil.d("客户导演  requestCode = "+ requestCode +" resultCode "+ resultCode);
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

        Boolean isFisrt = PreferencesUtil.getAttrBoolean(ApiContant.KEYVAULE.IS_FIRST,true);

        LogUtil.d("----- 客户导演 ------  加载客户列表  forceUpdate = "+ forceUpdate + " isFisrt = "+ isFisrt);

        loadCustomers(forceUpdate||isFisrt,true);

        PreferencesUtil.setAttr(ApiContant.KEYVAULE.IS_FIRST,false);
    }

    @Override
    public void addNewCustomer() {
        LogUtil.d("----- 客户导演 ------  添加客户操作  ");
        mCustomersView.showAddCustomer();
    }

    @Override
    public void openCustomerDetails(@NonNull Customer requestedCustomer) {
        checkNotNull(requestedCustomer, "requestedCustomer cannot be null!");

        LogUtil.d("----- 客户导演 ------  客户详情操作  requestedCustomer = "+ requestedCustomer.toString());

        mCustomersView.showCustomerDetailsUi(requestedCustomer.getmId());
    }

    @Override
    public void synchronization() {

        LogUtil.d("----- 客户导演 ------  同步客户 ");

        mCustomersView.setLoadingIndicator(true);

        mRemoteOpRepository.synCustomer(null, new RemoteOpDataSource.OpInfoCallback() {
            @Override
            public void onSuccess() {

                LogUtil.d("----- 客户导演 ------  同步客户 成功 ");

                mCustomersView.showSynSuccess();
            }

            @Override
            public void onFail() {

                LogUtil.d("----- 客户导演 ------  同步客户 失败 ");

                mCustomersView.showLoadingCustomersError();
            }
        });
    }

    @Override
    public void refershData() {

        LogUtil.d("----- 客户导演 ------  刷新客户  ");

        mCustomersView.setLoadingIndicator(true);


        mRemoteOpRepository.synCustomer(null, new RemoteOpDataSource.OpInfoCallback() {
            @Override
            public void onSuccess() {

                LogUtil.d("----- 客户导演 ------  操作提交成功  ");

                loadCustomers(true);
            }
            @Override
            public void onFail() {

                LogUtil.d("----- 客户导演 ------  操作提交失败  ");

                mCustomersView.showLoadingCustomersError();
            }
        });
    }

    @Override
    public void fitterDate(String dateStr) {

        LogUtil.d("----- 客户导演 ------  客户日期筛选  dateStr = "+ dateStr);

        mCustomersView.showFitterCustomers(dateStr);
    }

    @Override
    public void editCustomer(String customerId) {

        checkNotNull(customerId,"edit customerId is not null");

        LogUtil.d("----- 客户导演 ------  编辑客户操作  customerId = "+ customerId);

        mCustomersView.showEditCustomer(customerId);
    }

    @Override
    public void delCustomer(String customerId) {

        checkNotNull(customerId, "del customerId is not null");

        LogUtil.d("----- 客户导演 ------  删除客户操作  customerId = "+ customerId);

        //删除数据层的数据
        mCustomerRepository.deleteCustomer(customerId);


        //更新删除视图层的数据
        mCustomerRepository.getCustomers(new CustomerDataSource.LoadCustomersCallback() {
            @Override
            public void onCustomersLoader(List<Customer> customers) {
                List<Customer> customerList = new ArrayList<>();
                customerList.addAll(customers);
                processCustomers(customerList);

                LogUtil.d("----- 客户导演 ------ 删除 成功  --  刷新视图列表");

                mCustomersView.showSuccessfullyDeletedMessage();
            }

            @Override
            public void onDataNotAvailable() {

                LogUtil.d("----- 客户导演 ------ 删除 成功  --  刷新视图失败");

                List<Customer> customerList = new ArrayList<>();
                processCustomers(customerList);

                mCustomersView.showErrorfullyDeletedMessage();
            }
        });

        //添加操作
        String mId = System.currentTimeMillis()+"";
        CustomerSo customerSo = new CustomerSo();
        customerSo.setId(customerId);
        customerSo.setUserId(Long.valueOf(UserInfoCase.getUserId()));
        RemoteOp remoteOp = new RemoteOp(mId,RemoteOp.CUSTOMERDATA,RemoteOp.DELOPT,customerSo);
        mRemoteOpRepository.saveRemoteOp(remoteOp, new RemoteOpDataSource.OpInfoCallback() {
            @Override
            public void onSuccess() {
                LogUtil.d("----- 客户导演 ------ 删除  --  添加操作成功");
            }

            @Override
            public void onFail() {
                LogUtil.d("----- 客户导演 ------ 删除  --  添加操作失败");
            }
        });
    }

    @Override
    public void start() {

        LogUtil.d("----- 客户导演 ------ 判断用户是否登录  -- ---------");

        if(UserInfoCase.isLogin()){

            LogUtil.d("----- 客户导演 ------登录 加载开始  -- ---------");

            loadCustomers(false);
        }else {
            LogUtil.d("----- 客户导演 ------未登录 加载开始  -- ---------");
        }

    }

    /**
     * @param forceUpdate   Pass in true to refresh the data in the {@link CustomerDataSource}
     * @param showLoadingUI Pass in true to display a loading icon in the UI
     */
    private void loadCustomers(boolean forceUpdate, final boolean showLoadingUI) {

        LogUtil.d("----- 客户导演 ------ 加载数据  -- ---------");

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

                List<Customer> customerList = new ArrayList<>();

                processCustomers(customerList);

                mCustomersView.showLoadingCustomersError();
            }
        });
    }

    /**
     * 显示列表流程
     * @param customerList
     */
    private void processCustomers(List<Customer> customerList) {

        LogUtil.d("----- 客户导演 ------ 同步视图列表数据  -- ---------customerList.size = "+customerList.size());

        if (customerList.isEmpty()) {
            // Show a message indicating there are no tasks for that filter type.
            mCustomersView.showNoCustomers();
            mCustomersView.showCustomers(customerList);
        } else {
            // Show the list of tasks
            mCustomersView.showCustomers(customerList);
        }
    }
}
