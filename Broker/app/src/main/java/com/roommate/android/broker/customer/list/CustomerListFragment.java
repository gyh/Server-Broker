package com.roommate.android.broker.customer.list;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.roommate.android.broker.R;
import com.roommate.android.broker.common.DateUtils;
import com.roommate.android.broker.common.DialogUtils;
import com.roommate.android.broker.common.core.BaseActivity;
import com.roommate.android.broker.common.view.SmoothListView.SmoothListView;
import com.roommate.android.broker.customer.AddEditCustomerDetail.AddEditCustomerActivity;
import com.roommate.android.broker.customer.CustomersAdapter;
import com.roommate.android.broker.customer.data.Customer;
import com.roommate.android.broker.customer.fitter.FitterCustomersActivity;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;


/**
 * Created by GYH on 2015/8/11.
 *
 */
public class CustomerListFragment extends Fragment implements CustomerContract.View {

    public static final int ADD_CUSTOMER = 100;
    public static final int EDIT_CUSTOMER = 101;

    private CustomerContract.Presenter mPresenter;


    private SmoothListView smoothListView;
    private CustomersAdapter myAdatper;


    public CustomerListFragment() {
        // Requires empty public constructor
    }

    private Handler mHandler;

    //实例化碎片
    public static CustomerListFragment instance() {
        CustomerListFragment view = new CustomerListFragment();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customerlist, null);

        smoothListView = (SmoothListView) view.findViewById(R.id.listview);
        smoothListView.setRefreshEnable(true);
        smoothListView.setLoadMoreEnable(false);

        smoothListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                DialogUtils.twoButtonDialog(getActivity(), "提示信息", "选择操作", "编辑", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Customer customer = (Customer) myAdatper.getItem(position - 1);
                        mPresenter.editCustomer(customer.getmId());
                    }
                }, "删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Customer customer = (Customer) myAdatper.getItem(position - 1);
                        delCustomer(customer.getmId());
                    }
                });
                return true;
            }
        });

        smoothListView.setSmoothListViewListener(new SmoothListView.ISmoothListViewListener() {
            @Override
            public void onRefresh() {
                smoothListView.setRefreshTime(DateUtils.getNewDate());
                smoothListView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        smoothListView.stopRefresh();
                    }
                }, 1000);
                mPresenter.refershData();
            }

            @Override
            public void onLoadMore() {

            }
        });

        myAdatper = new CustomersAdapter(new ArrayList<Customer>(), getContext());

        mHandler = new Handler();

        smoothListView.setAdapter(myAdatper);

        FloatingActionButton fab =
                (FloatingActionButton) view.findViewById(R.id.fab_add_customer);

        fab.setImageResource(R.drawable.ic_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.addNewCustomer();
            }
        });

        mPresenter.start();

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.sync, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.result(requestCode, resultCode);
    }

    @Override
    public void setLoadingIndicator(final boolean active) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (active) {
                    ((BaseActivity) getActivity()).svProgressHUD.showWithProgress("正在加载", SVProgressHUD.SVProgressHUDMaskType.Clear);
                } else {
                    ((BaseActivity) getActivity()).svProgressHUD.dismiss();
                }
            }
        });
    }

    @Override
    public void showCustomers(final List<Customer> customers) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                myAdatper.replaceData(customers);
                ((BaseActivity) getActivity()).svProgressHUD.showSuccessWithStatus("加载成功", SVProgressHUD.SVProgressHUDMaskType.Clear);
            }
        });
    }

    @Override
    public void showAddCustomer() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                AddEditCustomerActivity.startAddCustomer(CustomerListFragment.this, ADD_CUSTOMER);
            }
        });

    }

    @Override
    public void showCustomerDetailsUi(final String customerId) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                AddEditCustomerActivity.startEditForResult(CustomerListFragment.this, ADD_CUSTOMER, customerId);
            }
        });
    }

    @Override
    public void showLoadingCustomersError() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                ((BaseActivity) getActivity()).svProgressHUD.showErrorWithStatus("哎呀，失败", SVProgressHUD.SVProgressHUDMaskType.Clear);
            }
        });
    }

    @Override
    public void showSynSuccess() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                ((BaseActivity) getActivity()).svProgressHUD.showSuccessWithStatus("同步成功", SVProgressHUD.SVProgressHUDMaskType.Clear);
            }
        });
    }

    @Override
    public void showNoCustomers() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                ((BaseActivity) getActivity()).svProgressHUD.showErrorWithStatus("哎呀，没有数据", SVProgressHUD.SVProgressHUDMaskType.Clear);
            }
        });
    }

    @Override
    public void showSuccessfullyDeletedMessage() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                ((BaseActivity) getActivity()).svProgressHUD.showSuccessWithStatus("删除成功", SVProgressHUD.SVProgressHUDMaskType.Clear);
            }
        });
    }

    @Override
    public void showErrorfullyDeletedMessage() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                ((BaseActivity) getActivity()).svProgressHUD.showErrorWithStatus("删除失败", SVProgressHUD.SVProgressHUDMaskType.Clear);
            }
        });

    }

    @Override
    public boolean isActive() {
        return isAdded();
    }


    @Override
    public void showEditCustomer(final String customerId) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                AddEditCustomerActivity.startEditForResult(CustomerListFragment.this, EDIT_CUSTOMER, customerId);
            }
        });
    }

    @Override
    public void showFitterCustomers(String fitterDate) {
        FitterCustomersActivity.startFitter(getActivity(),fitterDate);
    }

    @Override
    public void setPresenter(CustomerContract.Presenter presenter) {
        this.mPresenter = checkNotNull(presenter);
    }

    /**
     * 删除 提示框
     */
    private void delCustomer(final String customerId) {
        DialogUtils.twoButtonDialog(getActivity(), "提示", "确定是否删除", "是，删除", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mPresenter.delCustomer(customerId);
            }
        }, "不，我再想想", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
    }

}

