package com.roommate.android.broker.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


import com.roommate.android.broker.R;
import com.roommate.android.broker.customer.addcustomer.AddCustomerActivity;
import com.roommate.android.broker.app.SearchActivity;
import com.roommate.android.broker.common.view.SmoothListView.SmoothListView;



/**
 * Created by GYH on 2015/8/11.
 *
 * 产前环节
 */
public class FirstFragment extends Fragment implements SmoothListView.ISmoothListViewListener{

    private static final int ADD_CUSTOMER = 100;

    private SmoothListView smoothListView;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    //实例化碎片
    public static FirstFragment instance() {
        FirstFragment view = new FirstFragment();
		return view;
	}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.first_fragment, null);
        smoothListView = (SmoothListView)view.findViewById(R.id.listview);
        smoothListView.setAdapter(new MyAdatper());

        smoothListView.setRefreshEnable(false);
        smoothListView.setLoadMoreEnable(true);
        smoothListView.setSmoothListViewListener(this);

        FloatingActionButton fab =
                (FloatingActionButton) view.findViewById(R.id.fab_add_customer);

        fab.setImageResource(R.drawable.ic_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddCustomerActivity.startAddCustomer(FirstFragment.this,ADD_CUSTOMER);
            }
        });

        view.findViewById(R.id.img_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), SearchActivity.class));
            }
        });

        return view;
    }

    @Override
    public void onRefresh() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                smoothListView.stopRefresh();
                smoothListView.setRefreshTime("刚刚");
            }
        }, 2000);
    }

    @Override
    public void onLoadMore() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                smoothListView.stopLoadMore();
            }
        }, 2000);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ADD_CUSTOMER){
            if(resultCode == Activity.RESULT_OK){
                //刷新数据
            }
        }
    }

    class MyAdatper extends BaseAdapter{

        private LayoutInflater inflater ;

        MyAdatper(){
            inflater =  LayoutInflater.from(getContext());
        }

        @Override
        public int getCount() {
            return 10;
        }

        @Override
        public Object getItem(int position) {
            return null;
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
    }
}
