package com.roommate.android.broker.customer.AddEditCustomerDetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.roommate.android.broker.R;
import com.roommate.android.broker.common.core.BaseActivity;

/**
 * Created by GYH on 2016/5/19.
 */
public class AddEditCustomerActivity extends BaseActivity {

    private static final String CUSTOMERID_KEY  = "customerId_key";

    public static void startAddCustomer(Fragment context, int requestCode){
        Intent intent = new Intent();
        intent.setClass(context.getActivity(),AddEditCustomerActivity.class);
        context.startActivityForResult(intent,requestCode);
    }

    public static void startForResult(Fragment context, int requestCode, String customerId){
        Intent intent = new Intent();
        intent.setClass(context.getActivity(),AddEditCustomerActivity.class);
        intent.putExtra(CUSTOMERID_KEY,customerId);
        context.startActivityForResult(intent,requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_customer;
    }
}
