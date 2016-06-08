package com.roommate.android.broker.customer;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.roommate.android.broker.R;
import com.roommate.android.broker.common.DialogUtils;
import com.roommate.android.broker.common.PhoneNumberUtils;
import com.roommate.android.broker.customer.data.Customer;

import java.lang.ref.WeakReference;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by GYH on 2016/6/6.
 */
public class CustomersAdapter extends BaseAdapter {

    private List<Customer> customers;

    private LayoutInflater inflater ;

    private WeakReference<Context> contextWeakReference;

    public CustomersAdapter(List<Customer> customers, Context context){
        contextWeakReference = new WeakReference<Context>(context);
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
        if(inflater ==null){
            Context context = contextWeakReference.get();
            if(context!=null){
                inflater =  LayoutInflater.from(context);
            }
        }
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

        holder.tvPhoneNumber.setText(PhoneNumberUtils.hidePhoneNumber(customer.getPhoneNumber()));

        stringBuffer = new StringBuffer();
        stringBuffer.append("预约时间：");
        stringBuffer.append(customer.getInputDate());
        holder.tvDate.setText(stringBuffer);

        holder.tvPhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Context context = contextWeakReference.get();
                if(context!=null){
                    DialogUtils.twoButtonDialog(context, "拨号", "拨打电话"+customer.getPhoneNumber(), "确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            PhoneNumberUtils.dial(customer.getPhoneNumber(),context);
                        }
                    }, "取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                }

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

    class Holder{
        TextView tvPhoneNumber;
        TextView tvName;
        TextView tvDescribe;
        TextView tvDate;
        TextView tvArea;
        TextView tvDesire;
    }
}
