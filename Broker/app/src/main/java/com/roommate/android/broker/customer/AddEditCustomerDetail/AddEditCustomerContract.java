package com.roommate.android.broker.customer.AddEditCustomerDetail;

import com.roommate.android.broker.common.core.BasePresenter;
import com.roommate.android.broker.common.core.BaseView;

/**
 * Created by GYH on 2016/5/31.
 */
public interface AddEditCustomerContract {

    interface View extends BaseView<Presenter> {

        void showEmptyCustomerError();

        void showCustomersList();

        boolean isActive();

        void setName(String name);

        void setPhoneNumber(String phoneNumber);

        void setDesire(String desire);

        void setHouseArea(String houseArea);

        void setDescries(String describe);

        void setOrderDate(String inputDate);

    }

    interface Presenter extends BasePresenter{

        void createCustomer(String name, String phoneNumber,String desire,String houseArea,String describe,String inputDate);

        void updateCustomer(String name, String phoneNumber,String desire,String houseArea,String describe,String inputDate);

        void populateCustomer();
    }
}
