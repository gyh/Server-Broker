package com.roommate.android.broker.customer.list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;


import com.bigkoo.svprogresshud.SVProgressHUD;
import com.roommate.android.broker.R;
import com.roommate.android.broker.common.core.BaseActivity;
import com.roommate.android.broker.customer.AddEditCustomerDetail.AddEditCustomerActivity;
import com.roommate.android.broker.customer.data.Customer;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;


/**
 * Created by GYH on 2015/8/11.
 *
 * 产前环节
 */
public class FirstFragment extends Fragment{

    private static final int ADD_CUSTOMER = 100;

    private CustomerContract.Presenter mPresenter;


    private ListView smoothListView;
    private MyAdatper myAdatper;


    public FirstFragment(){
        // Requires empty public constructor
    }

    private Handler mHandler;

    //实例化碎片
    public static FirstFragment instance() {
        FirstFragment view = new FirstFragment();
		return view;
	}

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.first_fragment, null);
        smoothListView = (ListView)view.findViewById(R.id.listview);

        myAdatper = new MyAdatper(new ArrayList<Customer>(),getContext());

        mHandler = new Handler();

        smoothListView.setAdapter(myAdatper);

//        FloatingActionButton fab =
//                (FloatingActionButton) view.findViewById(R.id.fab_add_customer);
//
//        fab.setImageResource(R.drawable.ic_add);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AddCustomerActivity.startAddCustomer(FirstFragment.this,ADD_CUSTOMER);
//            }
//        });
//
//        view.findViewById(R.id.img_search).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getActivity().startActivity(new Intent(getActivity(), SearchActivity.class));
//            }
//        });

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mPresenter.result(requestCode, resultCode);
    }


    private static class MyAdatper extends BaseAdapter{

        private List<Customer> customers;

        private LayoutInflater inflater ;

        public MyAdatper(List<Customer> customers, Context context){
            inflater =  LayoutInflater.from(context);
            setList(customers);
        }

        @Override
        public int getCount() {
            return customers.size();
        }

        @Override
        public Object getItem(int position) {
            return customers.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView ==null){
                convertView = inflater.inflate(R.layout.item_customer,null);
            }
            return convertView;
        }

        public void replaceData(List<Customer> customers) {
            setList(customers);
            notifyDataSetChanged();
        }

        private void setList(List<Customer> customers) {
            this.customers = checkNotNull(customers);
        }
    }
}
