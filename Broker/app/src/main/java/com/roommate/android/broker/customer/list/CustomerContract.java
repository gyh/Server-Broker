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

import android.support.annotation.NonNull;


import com.roommate.android.broker.common.core.BasePresenter;
import com.roommate.android.broker.common.core.BaseView;
import com.roommate.android.broker.customer.data.Customer;

import java.util.List;

/**
 * This specifies the contract between the view and the presenter.
 */
public interface CustomerContract {

    interface View extends BaseView<Presenter> {

        void setLoadingIndicator(boolean active);

        void showCustomers(List<Customer> customers);

        void showAddCustomer();

        void showCustomerDetailsUi(String CustomerId);

        void showLoadingCustomersError();

        void showNoCustomers();

        void showSuccessfullyDeletedMessage();

        void showErrorfullyDeletedMessage();

        boolean isActive();

        void showEditCustomer(String customerId);

    }

    interface Presenter extends BasePresenter {

        void result(int requestCode, int resultCode);

        void loadCustomers(boolean forceUpdate);

        void addNewCustomer();

        void openCustomerDetails(@NonNull Customer requestedCustomer);

        void synchronization();

        void editCustomer(String customerId);

        void delCustomer(String customerId);

        void refershData();
    }
}
