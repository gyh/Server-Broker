package com.roommate.android.broker.house;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.roommate.android.broker.R;
import com.roommate.android.broker.common.view.SmoothListView.SmoothListView;

/**
 * Created by GYH on 2015/8/11.
 */
public class SecondFragment extends Fragment implements SmoothListView.ISmoothListViewListener{

    private SmoothListView smoothListView;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    //实例化碎片
    public static SecondFragment instance() {
        SecondFragment view = new SecondFragment();
        return view;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.second_fragment, null);
        smoothListView = (SmoothListView)view.findViewById(R.id.listview);
        smoothListView.setAdapter(new MyAdatper());

        smoothListView.setRefreshEnable(false);
        smoothListView.setLoadMoreEnable(true);
        smoothListView.setSmoothListViewListener(this);
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

    class MyAdatper extends BaseAdapter {

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
                convertView = inflater.inflate(R.layout.item_house_res,null);
            }
            return convertView;
        }
    }
}