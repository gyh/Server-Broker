package com.roommate.android.broker.user;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.roommate.android.broker.AboutActivity;
import com.roommate.android.broker.R;
import com.roommate.android.broker.common.AppUtils;
import com.roommate.android.broker.common.DialogUtils;
import com.roommate.android.broker.common.core.BaseActivity;
import com.roommate.android.broker.customer.data.source.CustomerDataSource;
import com.roommate.android.broker.customer.data.source.RemoteOpDataSource;
import com.roommate.android.broker.customer.data.source.local.CustomerLocalDataScource;
import com.roommate.android.broker.customer.data.source.local.RemoteOpLocalDataScource;

/**
 * Created by GYH on 2016/7/4.
 */
public class SettingActivity extends BaseActivity{


    private final String FUNCATION_URL = "http://120.27.7.127:8080/introduction/functionIntroduction.html";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        TextView versionName = (TextView)findViewById(R.id.tvVersionName);
        versionName.setText(AppUtils.getAppVersionName());

        findViewById(R.id.viewByFunction).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AboutActivity.startAbout(SettingActivity.this,FUNCATION_URL);
            }
        });
        findViewById(R.id.viewByAbout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        findViewById(R.id.viewbyHelp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        findViewById(R.id.viewByLoginOut).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtils.twoButtonDialog(SettingActivity.this, "警告", "是否真的退出",
                        "是的，退出", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                UserInfoCase.clearUserInfo();
                                RemoteOpDataSource remoteOpDataSource =  RemoteOpLocalDataScource.getInstance(SettingActivity.this);
                                remoteOpDataSource.deleteRemoteOps(new RemoteOpDataSource.OpInfoCallback() {
                                    @Override
                                    public void onSuccess() {

                                    }

                                    @Override
                                    public void onFail() {

                                    }
                                });
                                CustomerDataSource customerDataSource = CustomerLocalDataScource.getInstance(SettingActivity.this);
                                customerDataSource.deleteAllCustomers();
                                setResult(RESULT_OK);
                                finish();
                            }
                        }, "哎呀，点错了", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
            }
        });
        findViewById(R.id.viewByCheck).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_setting;
    }
}
