package com.roommate.android.broker.customer.data.source.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.roommate.android.broker.customer.data.Customer;
import com.roommate.android.broker.customer.data.source.CustomerDataSource;

import java.util.ArrayList;
import java.util.List;
import com.roommate.android.broker.customer.data.source.local.CustomersPersistenceContract.CustomerEntry;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by GYH on 2016/5/27.
 *
 * 本地数据库存储主要功能是：
 *
 * （1）存储请求回来的数据
 * （2）获取本地存储数据
 * （3）更新请求回来的数据
 *
 */
public class CustomerLocalDataScource implements CustomerDataSource{


    private static CustomerLocalDataScource INSTANCE;

    private CustomersDbHelper mDbHelper;

    // Prevent direct instantiation.
    private CustomerLocalDataScource(@NonNull Context context) {
        checkNotNull(context);
        mDbHelper = new CustomersDbHelper(context);
    }

    public static CustomerLocalDataScource getInstance(@NonNull Context context) {
        if (INSTANCE == null) {
            INSTANCE = new CustomerLocalDataScource(context);
        }
        return INSTANCE;
    }

    @Override
    public void getCustomers(@NonNull LoadCustomersCallback callback) {
        List<Customer> customers = new ArrayList<Customer>();
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                CustomerEntry.COLUMN_NAME_CUSTOMER_ID,
                CustomerEntry.COLUMN_NAME_CUSTOMER_NAME,
                CustomerEntry.COLUMN_NAME_CUSTOMER_PHONE_NUMBER,
                CustomerEntry.COLUMN_NAME_DESIRE,
                CustomerEntry.COLUMN_NAME_HOUSE_AREA,
                CustomerEntry.COLUMN_NAME_DESCRIBE,
                CustomerEntry.COLUMN_NAME_INPUTDATE
        };

        Cursor c = db.query(
                CustomerEntry.TABLE_NAME, projection, null, null, null, null, null);

        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                String itemId = c.getString(c.getColumnIndexOrThrow(CustomerEntry.COLUMN_NAME_CUSTOMER_ID));
                String name =c.getString(c.getColumnIndexOrThrow(CustomerEntry.COLUMN_NAME_CUSTOMER_NAME));
                String phoneNumber =c.getString(c.getColumnIndexOrThrow(CustomerEntry.COLUMN_NAME_CUSTOMER_PHONE_NUMBER));
                String desire =c.getString(c.getColumnIndexOrThrow(CustomerEntry.COLUMN_NAME_DESIRE));
                String houseArea =c.getString(c.getColumnIndexOrThrow(CustomerEntry.COLUMN_NAME_HOUSE_AREA));
                String describe =c.getString(c.getColumnIndexOrThrow(CustomerEntry.COLUMN_NAME_DESCRIBE));
                String inputDate =c.getString(c.getColumnIndexOrThrow(CustomerEntry.COLUMN_NAME_INPUTDATE));
                Customer customer = new Customer(itemId, name, phoneNumber, desire,houseArea,describe,inputDate);
                customers.add(customer);
            }
        }
        if (c != null) {
            c.close();
        }

        db.close();

        if (customers.isEmpty()) {
            // This will be called if the table is new or just empty.
            callback.onDataNotAvailable();
        } else {
            callback.onCustomersLoader(customers);
        }
    }

    @Override
    public void getCustomer(@NonNull String customerId, @NonNull GetCustomerCallback callback) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                CustomerEntry.COLUMN_NAME_CUSTOMER_ID,
                CustomerEntry.COLUMN_NAME_CUSTOMER_NAME,
                CustomerEntry.COLUMN_NAME_CUSTOMER_PHONE_NUMBER,
                CustomerEntry.COLUMN_NAME_DESIRE,
                CustomerEntry.COLUMN_NAME_HOUSE_AREA,
                CustomerEntry.COLUMN_NAME_DESCRIBE,
                CustomerEntry.COLUMN_NAME_INPUTDATE
        };

        String selection = CustomerEntry.COLUMN_NAME_CUSTOMER_ID + " LIKE ?";
        String[] selectionArgs = { customerId };

        Cursor c = db.query(
                CustomerEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, null);

        Customer customer = null;

        if (c != null && c.getCount() > 0) {
            c.moveToFirst();
            String itemId = c.getString(c.getColumnIndexOrThrow(CustomerEntry.COLUMN_NAME_CUSTOMER_ID));
            String name =c.getString(c.getColumnIndexOrThrow(CustomerEntry.COLUMN_NAME_CUSTOMER_NAME));
            String phoneNumber =c.getString(c.getColumnIndexOrThrow(CustomerEntry.COLUMN_NAME_CUSTOMER_PHONE_NUMBER));
            String desire =c.getString(c.getColumnIndexOrThrow(CustomerEntry.COLUMN_NAME_DESIRE));
            String houseArea =c.getString(c.getColumnIndexOrThrow(CustomerEntry.COLUMN_NAME_HOUSE_AREA));
            String describe =c.getString(c.getColumnIndexOrThrow(CustomerEntry.COLUMN_NAME_DESCRIBE));
            String inputDate =c.getString(c.getColumnIndexOrThrow(CustomerEntry.COLUMN_NAME_INPUTDATE));
            customer = new Customer(itemId, name, phoneNumber, desire,houseArea,describe,inputDate);
        }
        if (c != null) {
            c.close();
        }

        db.close();

        if (customer != null) {
            callback.onCustomerLoader(customer);
        } else {
            callback.onDataNotAvailable();
        }
    }

    @Override
    public void saveCustomer(@NonNull Customer customer) {
        checkNotNull(customer);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CustomerEntry.COLUMN_NAME_CUSTOMER_ID, customer.getmId());
        values.put(CustomerEntry.COLUMN_NAME_CUSTOMER_NAME, customer.getName());
        values.put(CustomerEntry.COLUMN_NAME_CUSTOMER_PHONE_NUMBER, customer.getPhoneNumber());
        values.put(CustomerEntry.COLUMN_NAME_DESIRE, customer.getDesire());
        values.put(CustomerEntry.COLUMN_NAME_HOUSE_AREA, customer.getHouseArea());
        values.put(CustomerEntry.COLUMN_NAME_DESCRIBE, customer.getDescribe());
        values.put(CustomerEntry.COLUMN_NAME_INPUTDATE, customer.getInputDate());

        db.insert(CustomerEntry.TABLE_NAME, null, values);

        db.close();
    }

    @Override
    public void updataCustomer(@NonNull Customer customer) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CustomerEntry.COLUMN_NAME_CUSTOMER_NAME, customer.getName());
        values.put(CustomerEntry.COLUMN_NAME_CUSTOMER_PHONE_NUMBER, customer.getPhoneNumber());
        values.put(CustomerEntry.COLUMN_NAME_DESIRE, customer.getDesire());
        values.put(CustomerEntry.COLUMN_NAME_HOUSE_AREA, customer.getHouseArea());
        values.put(CustomerEntry.COLUMN_NAME_DESCRIBE, customer.getDescribe());
        values.put(CustomerEntry.COLUMN_NAME_INPUTDATE, customer.getInputDate());

        String selection = CustomerEntry.COLUMN_NAME_CUSTOMER_ID + " LIKE ?";
        String[] selectionArgs = { customer.getmId() };

        db.update(CustomerEntry.TABLE_NAME, values, selection, selectionArgs);

        db.close();
    }

    @Override
    public void refreshCustomers() {

    }

    @Override
    public void deleteAllCustomers() {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        db.delete(CustomerEntry.TABLE_NAME, null, null);

        db.close();
    }

    @Override
    public void deleteCustomer(@NonNull String customerId) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        String selection = CustomerEntry.COLUMN_NAME_CUSTOMER_ID + " LIKE ?";
        String[] selectionArgs = { customerId };

        db.delete(CustomerEntry.TABLE_NAME, selection, selectionArgs);

        db.close();
    }

    @Override
    public void searchPhoneNumber(@NonNull String phoneNumber,@NonNull LoadCustomersCallback callback) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                CustomerEntry.COLUMN_NAME_CUSTOMER_ID,
                CustomerEntry.COLUMN_NAME_CUSTOMER_NAME,
                CustomerEntry.COLUMN_NAME_CUSTOMER_PHONE_NUMBER,
                CustomerEntry.COLUMN_NAME_DESIRE,
                CustomerEntry.COLUMN_NAME_HOUSE_AREA,
                CustomerEntry.COLUMN_NAME_DESCRIBE,
                CustomerEntry.COLUMN_NAME_INPUTDATE
        };

        String selection = CustomerEntry.COLUMN_NAME_CUSTOMER_PHONE_NUMBER + " LIKE ?";
        String[] selectionArgs = { "%"+phoneNumber +"%"};

        Cursor c = db.query(
                CustomerEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, null);

        List<Customer> customers = new ArrayList<>();

        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()){
                String itemId = c.getString(c.getColumnIndexOrThrow(CustomerEntry.COLUMN_NAME_CUSTOMER_ID));
                String name =c.getString(c.getColumnIndexOrThrow(CustomerEntry.COLUMN_NAME_CUSTOMER_NAME));
                String phoneNumbera =c.getString(c.getColumnIndexOrThrow(CustomerEntry.COLUMN_NAME_CUSTOMER_PHONE_NUMBER));
                String desire =c.getString(c.getColumnIndexOrThrow(CustomerEntry.COLUMN_NAME_DESIRE));
                String houseArea =c.getString(c.getColumnIndexOrThrow(CustomerEntry.COLUMN_NAME_HOUSE_AREA));
                String describe =c.getString(c.getColumnIndexOrThrow(CustomerEntry.COLUMN_NAME_DESCRIBE));
                String inputDate =c.getString(c.getColumnIndexOrThrow(CustomerEntry.COLUMN_NAME_INPUTDATE));
                Customer customer = new Customer(itemId, name, phoneNumbera, desire,houseArea,describe,inputDate);
                customers.add(customer);
            }
        }
        if (c != null) {
            c.close();
        }

        db.close();

        if (customers.isEmpty()) {
            // This will be called if the table is new or just empty.
            callback.onDataNotAvailable();
        } else {
            callback.onCustomersLoader(customers);
        }
    }

    @Override
    public void searchName(@NonNull String name,@NonNull LoadCustomersCallback callback) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                CustomerEntry.COLUMN_NAME_CUSTOMER_ID,
                CustomerEntry.COLUMN_NAME_CUSTOMER_NAME,
                CustomerEntry.COLUMN_NAME_CUSTOMER_PHONE_NUMBER,
                CustomerEntry.COLUMN_NAME_DESIRE,
                CustomerEntry.COLUMN_NAME_HOUSE_AREA,
                CustomerEntry.COLUMN_NAME_DESCRIBE,
                CustomerEntry.COLUMN_NAME_INPUTDATE
        };

        String selection = CustomerEntry.COLUMN_NAME_CUSTOMER_NAME + " LIKE ?";
        String[] selectionArgs = {"%"+ name +"%" };

        Cursor c = db.query(
                CustomerEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, null);

        List<Customer> customers = new ArrayList<>();

        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()){
                String itemId = c.getString(c.getColumnIndexOrThrow(CustomerEntry.COLUMN_NAME_CUSTOMER_ID));
                String namea =c.getString(c.getColumnIndexOrThrow(CustomerEntry.COLUMN_NAME_CUSTOMER_NAME));
                String phoneNumbera =c.getString(c.getColumnIndexOrThrow(CustomerEntry.COLUMN_NAME_CUSTOMER_PHONE_NUMBER));
                String desire =c.getString(c.getColumnIndexOrThrow(CustomerEntry.COLUMN_NAME_DESIRE));
                String houseArea =c.getString(c.getColumnIndexOrThrow(CustomerEntry.COLUMN_NAME_HOUSE_AREA));
                String describe =c.getString(c.getColumnIndexOrThrow(CustomerEntry.COLUMN_NAME_DESCRIBE));
                String inputDate =c.getString(c.getColumnIndexOrThrow(CustomerEntry.COLUMN_NAME_INPUTDATE));
                Customer customer = new Customer(itemId, namea, phoneNumbera, desire,houseArea,describe,inputDate);
                customers.add(customer);
            }
        }
        if (c != null) {
            c.close();
        }

        db.close();

        if (customers.isEmpty()) {
            // This will be called if the table is new or just empty.
            callback.onDataNotAvailable();
        } else {
            callback.onCustomersLoader(customers);
        }
    }

    @Override
    public void searchInputDate(@NonNull String dateStr, @NonNull LoadCustomersCallback callback) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                CustomerEntry.COLUMN_NAME_CUSTOMER_ID,
                CustomerEntry.COLUMN_NAME_CUSTOMER_NAME,
                CustomerEntry.COLUMN_NAME_CUSTOMER_PHONE_NUMBER,
                CustomerEntry.COLUMN_NAME_DESIRE,
                CustomerEntry.COLUMN_NAME_HOUSE_AREA,
                CustomerEntry.COLUMN_NAME_DESCRIBE,
                CustomerEntry.COLUMN_NAME_INPUTDATE
        };

        String selection = CustomerEntry.COLUMN_NAME_INPUTDATE + " LIKE ?";
        String[] selectionArgs = {"%"+ dateStr +"%" };

        Cursor c = db.query(
                CustomerEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, null);

        List<Customer> customers = new ArrayList<>();

        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()){
                String itemId = c.getString(c.getColumnIndexOrThrow(CustomerEntry.COLUMN_NAME_CUSTOMER_ID));
                String namea =c.getString(c.getColumnIndexOrThrow(CustomerEntry.COLUMN_NAME_CUSTOMER_NAME));
                String phoneNumbera =c.getString(c.getColumnIndexOrThrow(CustomerEntry.COLUMN_NAME_CUSTOMER_PHONE_NUMBER));
                String desire =c.getString(c.getColumnIndexOrThrow(CustomerEntry.COLUMN_NAME_DESIRE));
                String houseArea =c.getString(c.getColumnIndexOrThrow(CustomerEntry.COLUMN_NAME_HOUSE_AREA));
                String describe =c.getString(c.getColumnIndexOrThrow(CustomerEntry.COLUMN_NAME_DESCRIBE));
                String inputDate =c.getString(c.getColumnIndexOrThrow(CustomerEntry.COLUMN_NAME_INPUTDATE));
                Customer customer = new Customer(itemId, namea, phoneNumbera, desire,houseArea,describe,inputDate);
                customers.add(customer);
            }
        }
        if (c != null) {
            c.close();
        }

        db.close();

        if (customers.isEmpty()) {
            // This will be called if the table is new or just empty.
            callback.onDataNotAvailable();
        } else {
            callback.onCustomersLoader(customers);
        }
    }
}
