package com.roommate.android.broker.customer.searchCustomer;

import com.roommate.android.broker.common.core.BasePresenter;
import com.roommate.android.broker.common.core.BaseView;
import com.roommate.android.broker.customer.data.Customer;

import java.util.List;

/**
 * Created by GYH on 2016/6/6.
 */
public interface SearchCustomerContract {

    interface View extends BaseView<Presenter>{

        void showCustomerDetailsUi(String CustomerId);

        void showEditCustomer(String customerId);

        void showCustomers(List<Customer> customers);

        void setLoadingIndicator(boolean active);

        boolean isActive();

        void hideListView();

        void showNoCustomers();
    }

    interface Presenter extends BasePresenter {

        void searchCustomer(String msg);

        void editCustomer(String customerId);

        void delCustomer(String customerId);
    }
}
