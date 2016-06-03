package com.roommate.android.broker.customer.AddEditCustomerDetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.roommate.android.broker.R;
import com.roommate.android.broker.common.ActivityUtils;
import com.roommate.android.broker.common.core.BaseActivity;
import com.roommate.android.broker.customer.data.source.CustomerRepository;
import com.roommate.android.broker.customer.data.source.local.CustomerLocalDataScource;
import com.roommate.android.broker.customer.data.source.remote.CustomerRemoteDataSource;

/**
 * Created by GYH on 2016/5/19.
 */
public class AddEditCustomerActivity extends BaseActivity {


    private AddEditCustomerPresenter presenter;

    public static void startAddCustomer(Fragment context, int requestCode){
        Intent intent = new Intent();
        intent.setClass(context.getActivity(),AddEditCustomerActivity.class);
        context.startActivityForResult(intent,requestCode);
    }

    public static void startForResult(Fragment context, int requestCode, String customerId){
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
                addEditCustomerFragment);

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_customer;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_edit,menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_insertphone:
                presenter.importPhone();
                break;
        }
        return true;
    }
}
