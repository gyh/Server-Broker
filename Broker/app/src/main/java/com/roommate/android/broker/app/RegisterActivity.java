package com.roommate.android.broker.app;

import android.os.Bundle;
import android.text.TextUtils;


import com.roommate.android.broker.R;
import com.roommate.android.broker.common.BaseActivity;

import java.util.HashMap;

/**
 * Created by GYH on 2016/3/24.
 */
public class RegisterActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected boolean needLogin() {
        return false;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
    }
}
