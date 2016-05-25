package com.roommate.android.broker.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.roommate.android.broker.R;
import com.roommate.android.broker.common.LoginUtils;
import com.roommate.android.broker.common.core.BaseActivity;


/**
 * Created by GYH on 2016/3/23.
 */
public class LoginActivity extends BaseActivity {


    //注册标示
    private static final int REGISTER_REQUEST = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginUtils.loginss = true;
                setResult(RESULT_OK);
                finish();
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected boolean needLogin() {
        return false;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REGISTER_REQUEST) {
            if (resultCode == RESULT_OK) {
                setResult(RESULT_OK);
                finish();
            } else {

            }
        }
    }
}
