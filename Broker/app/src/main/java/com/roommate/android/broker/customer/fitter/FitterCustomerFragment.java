package com.roommate.android.broker.customer.fitter;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.roommate.android.broker.R;
import com.roommate.android.broker.common.core.BaseActivity;
import com.roommate.android.broker.customer.CustomersAdapter;
import com.roommate.android.broker.customer.data.Customer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GYH on 2016/6/8.
 */
public class FitterCustomerFragment extends Fragment implements FitterCustomerContract.View{

    public static final String FITTER_DATE_KEY = "fitterDateKey";

    private ListView listViewCompat;

    private CustomersAdapter myAdatper;

    private FitterCustomerContract.Presenter mPresenter;

    private Handler mHandler;


    public static FitterCustomerFragment newInstance(String dateStr) {
        FitterCustomerFragment fitterCustomerFragment = new FitterCustomerFragment();

        Bundle bundle = new Bundle();
        bundle.putString(FitterCustomerFragment.FITTER_DATE_KEY, dateStr);
        fitterCustomerFragment.setArguments(bundle);

        return fitterCustomerFragment;
    }

    public FitterCustomerFragment() {
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

        String dateStr;

        if (getArguments() != null && getArguments().containsKey(FITTER_DATE_KEY)) {
            dateStr = getArguments().getString(FITTER_DATE_KEY);
            mPresenter.fitterCustomer(dateStr);
        }
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
    public void setPresenter(FitterCustomerContract.Presenter presenter) {
        this.mPresenter = presenter;
    }
}
