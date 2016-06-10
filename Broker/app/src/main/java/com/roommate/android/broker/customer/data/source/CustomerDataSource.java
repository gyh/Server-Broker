package com.roommate.android.broker.customer.data.source;

import android.support.annotation.NonNull;

import com.roommate.android.broker.customer.data.Customer;

import java.util.List;

/**
 * Created by GYH on 2016/5/27.
 */
public interface CustomerDataSource {

    interface LoadCustomersCallback{

        void onCustomersLoader(List<Customer> customerList);

        void onDataNotAvailable();

    }

    interface GetCustomerCallback {

        void onCustomerLoader(Customer customer);

        void onDataNotAvailable();

    }

    interface SynCustomerCallback{

        void onSynSuccess();

        void onSynError();
    }

    void synCustomer(@NonNull SynCustomerCallback callback);

    void getCustomers(@NonNull LoadCustomersCallback callback);

    void getCustomer(@NonNull String customerId, @NonNull GetCustomerCallback callback);

    void saveCustomer(@NonNull Customer customer);

    void updataCustomer(@NonNull Customer customer);

    void refreshCustomers();

    void deleteAllCustomers();

    void deleteCustomer(@NonNull String customerId);

    void searchPhoneNumber(@NonNull String phoneNumbe,@NonNull LoadCustomersCallback callback);

    void searchName(@NonNull String name,@NonNull LoadCustomersCallback callback);

    void searchInputDate(@NonNull String dateStr,@NonNull LoadCustomersCallback callback);
}
