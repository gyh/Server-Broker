package com.roommate.android.broker.customer.AddEditCustomerDetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.roommate.android.broker.R;
import com.roommate.android.broker.common.ActivityUtils;
import com.roommate.android.broker.common.core.BaseActivity;
import com.roommate.android.broker.customer.data.source.CustomerRepository;
import com.roommate.android.broker.customer.data.source.RemoteOpRepository;
import com.roommate.android.broker.customer.data.source.local.CustomerLocalDataScource;
import com.roommate.android.broker.customer.data.source.local.RemoteOpLocalDataScource;
import com.roommate.android.broker.customer.data.source.remote.CustomerRemoteDataSource;
import com.roommate.android.broker.customer.data.source.remote.RemoteOpRemoteDataScource;

/**
 * Created by GYH on 2016/5/19.
 */
public class AddEditCustomerActivity extends BaseActivity {


    private AddEditCustomerPresenter presenter;

    /**
     * 打开添加
     * @param context
     * @param requestCode
     */
    public static void startAddCustomer(Fragment context, int requestCode){
        Intent intent = new Intent();
        intent.setClass(context.getActivity(),AddEditCustomerActivity.class);
        context.startActivityForResult(intent,requestCode);
    }

    /**
     * 打开编辑并且返回
     * @param context
     * @param requestCode
     * @param customerId
     */
    public static void startEditForResult(Fragment context, int requestCode, String customerId){
        Intent intent = new Intent();
        intent.setClass(context.getActivity(),AddEditCustomerActivity.class);
        intent.putExtra(AddEditCustomerFragment.ARGUMENT_EDIT_CUSTOMER_ID,customerId);
        context.startActivityForResult(intent,requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        AddEditCustomerFragment addEditCustomerFragment =
                (AddEditCustomerFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);

        String customerId = null;
        if (addEditCustomerFragment == null) {
            addEditCustomerFragment = AddEditCustomerFragment.newInstance();

            if (getIntent().hasExtra(AddEditCustomerFragment.ARGUMENT_EDIT_CUSTOMER_ID)) {
                customerId = getIntent().getStringExtra(AddEditCustomerFragment.ARGUMENT_EDIT_CUSTOMER_ID);
                actionBar.setTitle(R.string.activity_editcustomer);
                Bundle bundle = new Bundle();
                bundle.putString(AddEditCustomerFragment.ARGUMENT_EDIT_CUSTOMER_ID, customerId);
                addEditCustomerFragment.setArguments(bundle);
            } else {
                actionBar.setTitle(R.string.activity_addcustomer);
            }

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    addEditCustomerFragment, R.id.contentFrame);
        }

        // Create the presenter
        presenter = new AddEditCustomerPresenter(CustomerRepository.getInstance(CustomerRemoteDataSource.getInstance(), CustomerLocalDataScource.getInstance(this)),
                customerId,
                addEditCustomerFragment, RemoteOpRepository.getInstance(RemoteOpRemoteDataScource.getInstance(), RemoteOpLocalDataScource.getInstance(this)));

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_customer;
    }

}
