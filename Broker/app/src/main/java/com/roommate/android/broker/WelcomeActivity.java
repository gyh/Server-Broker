package com.roommate.android.broker;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.roommate.android.broker.customer.list.BrokerActivity;

/**
 * Created by GYH on 2016/5/23.
 */
public class WelcomeActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        startActivity(new Intent(this,BrokerActivity.class));
        finish();
    }
}
