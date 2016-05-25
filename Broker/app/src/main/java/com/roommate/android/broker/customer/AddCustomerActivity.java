package com.roommate.android.broker.customer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.roommate.android.broker.R;
import com.roommate.android.broker.common.core.BaseActivity;

/**
 * Created by GYH on 2016/5/19.
 */
public class AddCustomerActivity extends BaseActivity {

    public static void startAddCustomer(Fragment context, int key){
        Intent intent = new Intent();
        intent.setClass(context.getActivity(),AddCustomerActivity.class);
        context.startActivityForResult(intent,key);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_customer;
    }
}
