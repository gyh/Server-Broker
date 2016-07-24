package com.roommate.android.broker.user;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.roommate.android.broker.R;
import com.roommate.android.broker.common.core.BaseActivity;

/**
 * Created by GYH on 2016/7/10.
 */
public class ModPwdActivity extends BaseActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_modpwd;
    }
}
