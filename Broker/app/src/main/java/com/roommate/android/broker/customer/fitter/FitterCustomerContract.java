package com.roommate.android.broker.customer.fitter;

import com.roommate.android.broker.common.core.BasePresenter;
import com.roommate.android.broker.common.core.BaseView;
import com.roommate.android.broker.customer.data.Customer;

import java.util.List;

/**
 * Created by GYH on 2016/6/6.
 */
public interface FitterCustomerContract {

    interface View extends BaseView<Presenter>{

        void showCustomerDetailsUi(String CustomerId);

        void showCustomers(List<Customer> customers);

        void setLoadingIndicator(boolean active);

        boolean isActive();

        void hideListView();

        void showNoCustomers();
    }

    interface Presenter extends BasePresenter {

        void fitterCustomer(String dateStr);

    }
}
