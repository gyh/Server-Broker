package com.roommate.android.broker;

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

import com.bigkoo.pickerview.TimePickerView;
import com.roommate.android.broker.common.ActivityUtils;
import com.roommate.android.broker.common.DateUtils;
import com.roommate.android.broker.common.core.BaseActivity;
import com.roommate.android.broker.customer.SearchActivity;
import com.roommate.android.broker.customer.data.source.CustomerRepository;
import com.roommate.android.broker.customer.data.source.local.CustomerLocalDataScource;
import com.roommate.android.broker.customer.data.source.remote.CustomerRemoteDataSource;
import com.roommate.android.broker.customer.list.CustomerListFragment;
import com.roommate.android.broker.customer.list.CustomersPresenter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


/**
 * Created by GYH on 2016/5/28.
 */
public class BrokerActivity extends BaseActivity{

    //时间选择器
    private TimePickerView pvTime;

    private CustomersPresenter mCustomersPresenter;

    private DrawerLayout mDrawerLayout;

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
                customerListFragment);


    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_broker;
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
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_date:
                pvTime.show();
                break;
            case R.id.action_search:
                startActivity(new Intent(BrokerActivity.this, SearchActivity.class));
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
//                tvOrderDate.setText(DateUtils.getTime(date));
            }
        });
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.list_navigation_menu_item:
                                // Do nothing, we're already on that screen

                                mDrawerLayout.closeDrawers();

                                break;
                            case R.id.statistics_navigation_menu_item:
                                //
                                mDrawerLayout.closeDrawers();

                                postDelay(new Runnable() {
                                    @Override
                                    public void run() {
                                        startActivity(new Intent(BrokerActivity.this,AboutActivity.class));
                                    }
                                }, 1000);

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
