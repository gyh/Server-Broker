package com.roommate.android.broker.customer.fitter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.roommate.android.broker.R;
import com.roommate.android.broker.common.ActivityUtils;
import com.roommate.android.broker.common.core.BaseActivity;
import com.roommate.android.broker.customer.data.source.CustomerRepository;
import com.roommate.android.broker.customer.data.source.local.CustomerLocalDataScource;
import com.roommate.android.broker.customer.data.source.remote.CustomerRemoteDataSource;

/**
 * Created by GYH on 2016/6/8.
 */
public class FitterCustomersActivity extends BaseActivity{

    public static final String FITTER_DATE_KEY = "fitterDateKey";

    private FitterCustomerContract.Presenter mPresenter;

    /**
     * 打开日期筛选页面
     * @param context
     * @param fitterDate
     */
    public static void startFitter(Context context,String fitterDate){
        Intent intent = new Intent();
        intent.setClass(context,FitterCustomersActivity.class);
        intent.putExtra(FITTER_DATE_KEY,fitterDate);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        FitterCustomerFragment fitterCustomerFragment =
                (FitterCustomerFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        String dateStr = null;
        if (fitterCustomerFragment == null) {
            dateStr = getIntent().getStringExtra(FitterCustomerFragment.FITTER_DATE_KEY);
            fitterCustomerFragment = FitterCustomerFragment.newInstance(dateStr);
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    fitterCustomerFragment, R.id.contentFrame);
        }
        // Create the presenter
        mPresenter = new FitterCustomerPresenter(CustomerRepository.getInstance(CustomerRemoteDataSource.getInstance(),
                CustomerLocalDataScource.getInstance(this)),
                fitterCustomerFragment);

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_fitter;
    }
}
