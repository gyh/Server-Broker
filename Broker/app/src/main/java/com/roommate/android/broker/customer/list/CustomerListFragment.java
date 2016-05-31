package com.roommate.android.broker.customer.list;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
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
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.roommate.android.broker.R;
import com.roommate.android.broker.common.DialogUtils;
import com.roommate.android.broker.common.core.BaseActivity;
import com.roommate.android.broker.common.view.SmoothListView.SmoothListView;
import com.roommate.android.broker.customer.AddEditCustomerDetail.AddEditCustomerActivity;
import com.roommate.android.broker.customer.data.Customer;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;


/**
 * Created by GYH on 2015/8/11.
 *
 */
public class CustomerListFragment extends Fragment implements CustomerContract.View{

    public static final int ADD_CUSTOMER = 100;
    public static final int EDIT_CUSTOMER = 101;

    private CustomerContract.Presenter mPresenter;


    private SmoothListView smoothListView;
    private MyAdatper myAdatper;


    public CustomerListFragment(){
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
        mPresenter.start();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.customerlist_fragment, null);

        smoothListView = (SmoothListView)view.findViewById(R.id.listview);
        smoothListView.setRefreshEnable(false);
        smoothListView.setLoadMoreEnable(false);

        smoothListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                DialogUtils.twoButtonDialog(getActivity(), "提示信息", "选择操作", "编辑", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Customer customer = (Customer)myAdatper.getItem(position);
                        mPresenter.editCustomer(customer.getmId());
                    }
                }, "删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Customer customer = (Customer)myAdatper.getItem(position);
                        delCustomer(customer.getmId());
                    }
                });
                return true;
            }
        });

        myAdatper = new MyAdatper(new ArrayList<Customer>(),getContext());

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
        mPresenter.result(requestCode, resultCode);
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        if(active){
            ((BaseActivity)getActivity()).svProgressHUD.showWithProgress("正在加载", SVProgressHUD.SVProgressHUDMaskType.None);
        }else {
            ((BaseActivity)getActivity()).svProgressHUD.dismiss();
        }
    }

    @Override
    public void showCustomers(List<Customer> customers) {
        myAdatper.replaceData(customers);
        ((BaseActivity)getActivity()).svProgressHUD.showSuccessWithStatus("正在加载", SVProgressHUD.SVProgressHUDMaskType.None);
    }

    @Override
    public void showAddCustomer() {
        Intent intent = new Intent(getContext(), AddEditCustomerActivity.class);
        startActivityForResult(intent, ADD_CUSTOMER);
    }

    @Override
    public void showCustomerDetailsUi(String CustomerId) {
        AddEditCustomerActivity.startAddCustomer(CustomerListFragment.this,ADD_CUSTOMER);
    }

    @Override
    public void showLoadingCustomersError() {
        ((BaseActivity)getActivity()).svProgressHUD.showErrorWithStatus("哎呀，失败", SVProgressHUD.SVProgressHUDMaskType.None);
    }

    @Override
    public void showNoCustomers() {
        ((BaseActivity)getActivity()).svProgressHUD.showErrorWithStatus("哎呀，没有数据", SVProgressHUD.SVProgressHUDMaskType.None);
    }

    @Override
    public void showSuccessfullyDeletedMessage() {
        ((BaseActivity)getActivity()).svProgressHUD.showErrorWithStatus("删除成功", SVProgressHUD.SVProgressHUDMaskType.None);
    }

    @Override
    public void showErrorfullyDeletedMessage() {
        ((BaseActivity)getActivity()).svProgressHUD.showErrorWithStatus("删除失败", SVProgressHUD.SVProgressHUDMaskType.None);
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }


    @Override
    public void showEditCustomer(String customerId) {
       AddEditCustomerActivity.startForResult(CustomerListFragment.this,EDIT_CUSTOMER,customerId);
    }

    @Override
    public void setPresenter(CustomerContract.Presenter presenter) {
       this.mPresenter =  checkNotNull(presenter);
    }

    /**
     * 删除 提示框
     */
    private void delCustomer(final String customerId){
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

    /**
     * 拨打电话
     * @param phoneNumber
     */
    private void dial(String phoneNumber){
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getActivity().startActivity(intent);
    }

    private class MyAdatper extends BaseAdapter{

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
            Holder holder = null;
            if(convertView ==null){
                convertView = inflater.inflate(R.layout.item_customer,null);
                holder = new Holder();
                holder.tvDate = (TextView)convertView.findViewById(R.id.tv_date);
                holder.tvArea = (TextView)convertView.findViewById(R.id.tv_area);
                holder.tvDescribe = (TextView)convertView.findViewById(R.id.tv_describe);
                holder.tvDesire = (TextView)convertView.findViewById(R.id.tv_desire);
                holder.tvName = (TextView)convertView.findViewById(R.id.tv_name);
                holder.tvPhoneNumber = (TextView)convertView.findViewById(R.id.tv_phone);
                convertView.setTag(holder);
            }else {
                holder = (Holder)convertView.getTag();
            }

            final Customer customer = (Customer)getItem(position);


            holder.tvName.setText(customer.getName());

            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(customer.getDesire());
            stringBuffer.append(" 个月内");

            holder.tvDesire.setText(stringBuffer);

            holder.tvDescribe.setText(customer.getDescribe());

            stringBuffer = new StringBuffer();
            stringBuffer.append(customer.getHouseArea());
            stringBuffer.append(" 平米");
            holder.tvArea.setText(stringBuffer);

            holder.tvPhoneNumber.setText(customer.getPhoneNumber());

            stringBuffer = new StringBuffer();
            stringBuffer.append("预约时间：");
            stringBuffer.append(customer.getInputDate());
            holder.tvDate.setText(stringBuffer);

            holder.tvPhoneNumber.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogUtils.twoButtonDialog(getActivity(), "拨号", "拨打电话"+customer.getPhoneNumber(), "确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dial(customer.getPhoneNumber());
                        }
                    }, "取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                }
            });
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

    class Holder{
        TextView tvPhoneNumber;
        TextView tvName;
        TextView tvDescribe;
        TextView tvDate;
        TextView tvArea;
        TextView tvDesire;
    }
}
