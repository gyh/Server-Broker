package com.roommate.android.broker.customer.searchCustomer;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.ListViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.roommate.android.broker.R;
import com.roommate.android.broker.common.DialogUtils;
import com.roommate.android.broker.common.core.BaseActivity;
import com.roommate.android.broker.customer.CustomersAdapter;
import com.roommate.android.broker.customer.data.Customer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GYH on 2016/6/6.
 */
public class SearchCustomerFragment extends Fragment implements SearchCustomerContract.View{

    private ListView listViewCompat;

    private CustomersAdapter myAdatper;

    private SearchCustomerContract.Presenter mPresenter;

    private Handler mHandler;

    public static SearchCustomerFragment newInstance() {
        return new SearchCustomerFragment();
    }

    public SearchCustomerFragment() {
        // Required empty public constructor
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_search_resultlist,container,false);

        myAdatper = new CustomersAdapter(new ArrayList<Customer>(), getContext());

        mHandler = new Handler();

        listViewCompat = (ListView)rootView.findViewById(R.id.listviewCompat);

        listViewCompat.setAdapter(myAdatper);

        mPresenter.start();

        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }

    @Override
    public void showCustomerDetailsUi(String CustomerId) {

    }

    @Override
    public void showEditCustomer(String customerId) {

    }

    @Override
    public void showCustomers(final List<Customer> customers) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                listViewCompat.setVisibility(View.VISIBLE);
                myAdatper.replaceData(customers);
                ((BaseActivity) getActivity()).svProgressHUD.showSuccessWithStatus("加载成功", SVProgressHUD.SVProgressHUDMaskType.Clear);
            }
        });
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
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void hideListView() {
        listViewCompat.setVisibility(View.GONE);
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
    public void setPresenter(SearchCustomerContract.Presenter presenter) {
        this.mPresenter = presenter;
    }
}
