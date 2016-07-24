package com.roommate.android.broker.customer.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.roommate.android.broker.R;
import com.roommate.android.broker.common.ActivityUtils;
import com.roommate.android.broker.common.DateUtils;
import com.roommate.android.broker.common.core.BaseActivity;
import com.roommate.android.broker.customer.data.source.RemoteOpRepository;
import com.roommate.android.broker.customer.data.source.local.RemoteOpLocalDataScource;
import com.roommate.android.broker.customer.data.source.remote.RemoteOpRemoteDataScource;
import com.roommate.android.broker.customer.searchCustomer.SearchCustomerActivity;
import com.roommate.android.broker.customer.data.source.CustomerRepository;
import com.roommate.android.broker.customer.data.source.local.CustomerLocalDataScource;
import com.roommate.android.broker.customer.data.source.remote.CustomerRemoteDataSource;
import com.roommate.android.broker.user.LoginActivity;
import com.roommate.android.broker.user.ModPwdActivity;
import com.roommate.android.broker.user.SettingActivity;
import com.roommate.android.broker.user.UserInfoCase;

import java.util.Calendar;
import java.util.Date;


/**
 * Created by GYH on 2016/5/28.
 *
 * 自己实现更新
 *
 */
public class BrokerActivity extends BaseActivity {

    private final int SETTNG_REQUEST_CODE = 100;
    private final int MODPWD_REQUEST_CODE = 200;
    //时间选择器
    private TimePickerView pvTime;

    private CustomersPresenter mCustomersPresenter;

    private DrawerLayout mDrawerLayout;

    private TextView userName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);
        //初始化时间选择
        initOrderDate();
        // Set up the navigation drawer.
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setStatusBarBackground(R.color.colorPrimaryDark);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        CustomerListFragment customerListFragment =
                (CustomerListFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (customerListFragment == null) {
            // Create the fragment
            customerListFragment = CustomerListFragment.instance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), customerListFragment, R.id.contentFrame);
        }
        // Create the presenter
        mCustomersPresenter = new CustomersPresenter(CustomerRepository.getInstance(CustomerRemoteDataSource.getInstance(),
                CustomerLocalDataScource.getInstance(getApplicationContext())),
                customerListFragment, RemoteOpRepository.getInstance(RemoteOpRemoteDataScource.getInstance(),
                RemoteOpLocalDataScource.getInstance(getApplicationContext())));


    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_broker;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(userName!=null){
            userName.setText(UserInfoCase.getNickName());
        }
    }

    @Override
    protected boolean needLogin() {
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.broker,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SETTNG_REQUEST_CODE){
            if(resultCode == RESULT_OK){
                Intent intent = new Intent();
                intent.setClass(this, LoginActivity.class);
                startActivityForResult(intent,LOGIN_REQUEST);
            }
        }
    }

    @Override
    protected void loginResult(Intent data) {
        mCustomersPresenter.start();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_date:
                pvTime.show();
                break;
            case R.id.action_search:
                startActivity(new Intent(BrokerActivity.this, SearchCustomerActivity.class));
                break;
            case R.id.action_sync:
                mCustomersPresenter.synchronization();
                break;
            case android.R.id.home:
                // Open the navigation drawer when the home icon is selected from the toolbar.
                if(mDrawerLayout.isDrawerOpen(GravityCompat.START)){
                    mDrawerLayout.closeDrawers();
                }else {
                    mDrawerLayout.openDrawer(GravityCompat.START);
                }
                return true;
        }
        return true;
    }

    /**
     * 初始化预选日期
     */
    private void initOrderDate(){
        Calendar calendar = Calendar.getInstance();
        //时间选择器
        pvTime = new TimePickerView(this, TimePickerView.Type.YEAR_MONTH_DAY);
        pvTime.setRange(calendar.get(Calendar.YEAR), calendar.get(Calendar.YEAR)+2);
        pvTime.setTime(new Date());
        pvTime.setCyclic(false);
        pvTime.setCancelable(true);
        //时间选择后回调
        pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {

            @Override
            public void onTimeSelect(Date date) {
                mCustomersPresenter.fitterDate(DateUtils.getDate(date));
            }
        });
    }

    private void setupDrawerContent(NavigationView navigationView) {

        navigationView.inflateHeaderView(R.layout.nav_header);

        View headView = navigationView.getHeaderView(0);
        userName = (TextView) headView.findViewById(R.id.tv_userName);
        userName.setText(UserInfoCase.getNickName());

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.list_navigation_menu_item:
                                // Do nothing, we're already on that screen

                                mDrawerLayout.closeDrawers();

                                postDelay(new Runnable() {
                                    @Override
                                    public void run() {
                                        startActivityForResult(new Intent(BrokerActivity.this,ModPwdActivity.class),MODPWD_REQUEST_CODE);
                                    }
                                },500);

                                break;
                            case R.id.statistics_navigation_menu_item:
                                //
                                mDrawerLayout.closeDrawers();

                                postDelay(new Runnable() {
                                    @Override
                                    public void run() {
                                        startActivityForResult(new Intent(BrokerActivity.this,SettingActivity.class),SETTNG_REQUEST_CODE);
                                    }
                                }, 500);

                                break;
                            default:
                                break;
                        }
                        // Close the navigation drawer when an item is selected.
//                        menuItem.setChecked(true);

                        return true;
                    }
                });
    }

}
