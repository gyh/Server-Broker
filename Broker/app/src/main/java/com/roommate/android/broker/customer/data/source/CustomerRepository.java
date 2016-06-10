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

package com.roommate.android.broker.customer.data.source;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.roommate.android.broker.customer.data.Customer;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;


/**
 * Concrete implementation to load Customers from the data sources into a cache.
 * <p>
 * For simplicity, this implements a dumb synchronisation between locally persisted data and data
 * obtained from the server, by using the remote data source only if the local database doesn't
 * exist or is empty.
 */
public class CustomerRepository implements CustomerDataSource {

    private volatile static  CustomerRepository INSTANCE = null;

    private final CustomerDataSource mCustomersRemoteDataSource;

    private final CustomerDataSource mCustomersLocalDataSource;

    /**
     * This variable has package local visibility so it can be accessed from tests.
     */
    Map<String, Customer> mCachedCustomers;

    /**
     * Marks the cache as invalid, to force an update the next time data is requested. This variable
     * has package local visibility so it can be accessed from tests.
     */
    boolean mCacheIsDirty = false;

    // Prevent direct instantiation.
    private CustomerRepository(@NonNull CustomerDataSource customersRemoteDataSource,
                               @NonNull CustomerDataSource customersLocalDataSource) {
        mCustomersRemoteDataSource = checkNotNull(customersRemoteDataSource);
        mCustomersLocalDataSource = checkNotNull(customersLocalDataSource);
    }

    /**
     * Returns the single instance of this class, creating it if necessary.
     *
     * @param customersRemoteDataSource the backend data source
     * @param customersLocalDataSource  the device storage data source
     * @return the {@link CustomerDataSource} instance
     */
    public synchronized static CustomerRepository getInstance(CustomerDataSource customersRemoteDataSource,
                                                 CustomerDataSource customersLocalDataSource) {
        if (INSTANCE == null) {
            synchronized (CustomerRepository.class) {
                if(INSTANCE==null)
                    INSTANCE = new CustomerRepository(customersRemoteDataSource, customersLocalDataSource);
            }
        }
        return INSTANCE;
    }

    /**
     * Used to force {@link #getInstance(CustomerDataSource, CustomerDataSource)} to create a new instance
     * next time it's called.
     */
    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public void synCustomer(@NonNull SynCustomerCallback callback) {
        checkNotNull(callback);
        mCustomersRemoteDataSource.synCustomer(callback);
    }

    @Override
    public void getCustomers(@NonNull final LoadCustomersCallback callback) {
        checkNotNull(callback);

        //如果缓存不为空 并且 不是刷新数据
        if(mCachedCustomers!=null && !mCacheIsDirty){
            callback.onCustomersLoader(new ArrayList<>(mCachedCustomers.values()));
            return;
        }

        //如果缓存为空或者刷新数据 则执行下面的代码

        if(mCacheIsDirty){
            //刷新数据，获取远程数据
            getCustomerFromRemoteDataSource(callback);
        }else {
            //如果缓存为空
            //从本地数据总获取数据
            mCustomersLocalDataSource.getCustomers(new LoadCustomersCallback() {
                @Override
                public void onCustomersLoader(List<Customer> customerList) {
                    refreshCache(customerList);
                    callback.onCustomersLoader(new ArrayList<>(mCachedCustomers.values()));
                }

                @Override
                public void onDataNotAvailable() {
                    getCustomerFromRemoteDataSource(callback);
                }
            });
        }

    }

    @Override
    public void getCustomer(@NonNull final String customerId, @NonNull final GetCustomerCallback callback) {

        checkNotNull(customerId);
        checkNotNull(callback);

        //从缓存中获取
        Customer cachedCustomer = getCustomerWithId(customerId);

        if(cachedCustomer!=null){
            callback.onCustomerLoader(cachedCustomer);
            return;
        }

        mCustomersLocalDataSource.getCustomer(customerId, new GetCustomerCallback() {
            @Override
            public void onCustomerLoader(Customer customer) {
                callback.onCustomerLoader(customer);
            }

            @Override
            public void onDataNotAvailable() {
                mCustomersRemoteDataSource.getCustomer(customerId, new GetCustomerCallback() {
                    @Override
                    public void onCustomerLoader(Customer customer) {

                        mCachedCustomers.put(customer.getmId(), customer);

                        mCustomersRemoteDataSource.saveCustomer(customer);

                        callback.onCustomerLoader(customer);
                    }

                    @Override
                    public void onDataNotAvailable() {
                        callback.onDataNotAvailable();
                    }
                });
            }
        });

    }

    @Override
    public void saveCustomer(@NonNull Customer customer) {
        checkNotNull(customer);
        //先加入要提交服务端的客户信息
        mCustomersRemoteDataSource.saveCustomer(customer);
        //然后本地考虑是否要存储
        mCustomersLocalDataSource.saveCustomer(customer);
        if(mCachedCustomers == null){
            mCachedCustomers = new LinkedHashMap<>();
        }
        //缓存存储
        mCachedCustomers.put(customer.getmId(),customer);
    }

    @Override
    public void updataCustomer(@NonNull Customer customer){
        checkNotNull(customer);
        //先加入要提交服务端的客户信息
        mCustomersRemoteDataSource.updataCustomer(customer);
        //然后要更新本地库
        mCustomersLocalDataSource.updataCustomer(customer);
        if(mCachedCustomers == null){
            mCachedCustomers = new LinkedHashMap<>();
        }
        //缓存存储
        mCachedCustomers.put(customer.getmId(),customer);
    }

    @Override
    public void refreshCustomers() {
        mCacheIsDirty = true;
    }

    @Override
    public void deleteAllCustomers() {
        mCustomersLocalDataSource.deleteAllCustomers();
        if (mCachedCustomers == null) {
            mCachedCustomers = new LinkedHashMap<>();
        }
        mCachedCustomers.clear();
    }

    @Override
    public void deleteCustomer(@NonNull String customerId) {
        mCustomersRemoteDataSource.deleteCustomer(checkNotNull(customerId));
        mCustomersLocalDataSource.deleteCustomer(checkNotNull(customerId));
        mCachedCustomers.remove(customerId);
    }

    @Override
    public void searchPhoneNumber(@NonNull String phoneNumber,@NonNull LoadCustomersCallback callback) {
        mCustomersLocalDataSource.searchPhoneNumber(phoneNumber,callback);
    }

    @Override
    public void searchName(@NonNull String name,@NonNull LoadCustomersCallback callback) {
        mCustomersLocalDataSource.searchName(name,callback);
    }

    @Override
    public void searchInputDate(@NonNull String dateStr, @NonNull LoadCustomersCallback callback) {
        mCustomersLocalDataSource.searchInputDate(dateStr,callback);
    }

    /**
     * 从远程获取数据
     * @param callback
     */
    private void getCustomerFromRemoteDataSource(@NonNull final LoadCustomersCallback callback) {
        mCustomersRemoteDataSource.getCustomers(new LoadCustomersCallback() {
            @Override
            public void onCustomersLoader(List<Customer> customers) {
                refreshCache(customers);
                refreshLocalDataSource(customers);
                callback.onCustomersLoader(new ArrayList<>(mCachedCustomers.values()));
            }

            @Override
            public void onDataNotAvailable() {
                //如果请求失败
                //从本地数据总获取数据
                mCustomersLocalDataSource.getCustomers(new LoadCustomersCallback() {
                    @Override
                    public void onCustomersLoader(List<Customer> customerList) {
                        refreshCache(customerList);
                        callback.onCustomersLoader(new ArrayList<>(mCachedCustomers.values()));
                    }

                    @Override
                    public void onDataNotAvailable() {
                        callback.onDataNotAvailable();
                    }
                });
            }
        });
    }

    /**
     * 刷新缓存数据
     * @param customerList
     */
    private void refreshCache(List<Customer> customerList) {
        if (mCachedCustomers == null) {
            mCachedCustomers = new LinkedHashMap<>();
        }
        mCachedCustomers.clear();
        for (Customer customer : customerList) {
            mCachedCustomers.put(customer.getmId(), customer);
        }
        mCacheIsDirty = false;
    }

    /**
     * 刷新本地数据
     * @param customerList
     */
    private void refreshLocalDataSource(List<Customer> customerList) {
        mCustomersLocalDataSource.deleteAllCustomers();
        for (Customer customer : customerList) {
            mCustomersLocalDataSource.saveCustomer(customer);
        }
    }

    @Nullable
    private Customer getCustomerWithId(@NonNull String id) {
        checkNotNull(id);
        if (mCachedCustomers == null || mCachedCustomers.isEmpty()) {
            return null;
        } else {
            return mCachedCustomers.get(id);
        }
    }
}
