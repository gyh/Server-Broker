package com.roommate.android.broker.customer.AddEditCustomerDetail;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.roommate.android.broker.R;
import com.roommate.android.broker.common.DateUtils;
import com.roommate.android.broker.common.PhoneNumberUtils;
import com.roommate.android.broker.common.StringUtils;
import com.roommate.android.broker.common.core.BaseActivity;

import java.util.ArrayList;
import java.util.Date;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by GYH on 2016/6/1.
 */
public class AddEditCustomerFragment extends Fragment implements AddEditCustomerContract.View{

    public static final String ARGUMENT_EDIT_CUSTOMER_ID = "EDIT_CUSTOMER_ID";

    private String mEditedCustomerId;

    private AddEditCustomerContract.Presenter presenter;

    private Handler mHandler;

    //时间选择器
    private TimePickerView pvTime;
    //月份
    private ArrayList<String> optionsDesireItems = new ArrayList<String>();
    //面积
    private ArrayList<String> optionsHouseAreaItems = new ArrayList<String>();
    //购买日期选择器
    private OptionsPickerView pvOptionsDesire;
    private OptionsPickerView pvOptionsHouseArea;

    private TextView tvOrderDate;
    private TextView tvDesires;
    private TextView tvHouseArea;
    private EditText editName;
    private EditText editphoneNumber;
    private EditText editDescribe;


    public static AddEditCustomerFragment newInstance() {
        return new AddEditCustomerFragment();
    }

    public AddEditCustomerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setCustomerIdIfAny();

        mHandler = new Handler();

        FloatingActionButton fab =
                (FloatingActionButton) getActivity().findViewById(R.id.fab_edit_customer_done);
        fab.setImageResource(R.drawable.ic_done);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNewCustomer()) {
                    if(checkInput()){
                        presenter.createCustomer(
                                editName.getText().toString(),
                                editphoneNumber.getText().toString(),
                                tvDesires.getText().toString(),
                                tvHouseArea.getText().toString(),
                                editDescribe.getText().toString(),
                                tvOrderDate.getText().toString());
                    }

                } else {
                    if(checkInput()){
                        presenter.updateCustomer(
                                editName.getText().toString(),
                                editphoneNumber.getText().toString(),
                                tvDesires.getText().toString(),
                                tvHouseArea.getText().toString(),
                                editDescribe.getText().toString(),
                                tvOrderDate.getText().toString());
                    }
                }

            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_add_edit_customer, container, false);

        //初始化View
        editDescribe = (EditText)root.findViewById(R.id.edit_describe);
        tvOrderDate = (TextView) root.findViewById(R.id.tv_orderDate);
        tvDesires = (TextView)root.findViewById(R.id.tv_desire);
        tvHouseArea = (TextView)root.findViewById(R.id.tv_housearea);
        editName = (EditText) root.findViewById(R.id.input_edit_name);
        editphoneNumber = (EditText) root.findViewById(R.id.input_edit_phonenumber);

        root.findViewById(R.id.img_input_phone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.importPhone();
            }
        });

        initDesireOption(root);
        initOrderDate(root);
        initHouseAreaOption(root);

        setHasOptionsMenu(true);
        setRetainInstance(true);

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }

    @Override
    public void showEmptyCustomerError() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                ((BaseActivity)getActivity()).svProgressHUD.showErrorWithStatus("哎呀，数据为空");
            }
        });

    }

    @Override
    public void showCustomersList() {
        getActivity().setResult(Activity.RESULT_OK);
        getActivity().finish();
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void setName(String name) {
        editName.setText(name);
        checkNotNull(name);
        editName.setSelection(name.length());
    }

    @Override
    public void setPhoneNumber(String phoneNumber) {
        editphoneNumber.setText(phoneNumber);
    }

    @Override
    public void setDesire(String desire) {
        tvDesires.setText(desire);
    }

    @Override
    public void setHouseArea(String houseArea) {
        tvHouseArea.setText(houseArea);
    }

    @Override
    public void setDescries(String describe) {
        editDescribe.setText(describe);
    }

    @Override
    public void setOrderDate(String inputDate) {
        tvOrderDate.setText(inputDate);
    }

    @Override
    public void showDialogPhoneList() {
        PhoneNumberUtils.alertPhoneType(getActivity(),editphoneNumber);
    }

    @Override
    public void setPresenter(@NonNull AddEditCustomerContract.Presenter presenter) {
        this.presenter = checkNotNull(presenter);
    }

    private void setCustomerIdIfAny() {
        if (getArguments() != null && getArguments().containsKey(ARGUMENT_EDIT_CUSTOMER_ID)) {
            mEditedCustomerId = getArguments().getString(ARGUMENT_EDIT_CUSTOMER_ID);
        }
    }

    private boolean checkInput(){
        boolean isOk = true;
        if(TextUtils.isEmpty(editName.getText().toString())){
            isOk = false;
            showToast("客户名称不能为空");
        }else if(TextUtils.isEmpty(editphoneNumber.getText().toString())){
            isOk = false;
            showToast("手机号不能为空");
        }else if(TextUtils.isEmpty(tvHouseArea.getText().toString())){
            isOk = false;
            showToast("购买面积不能为空");
        }else if(TextUtils.isEmpty(tvDesires.getText().toString())){
            isOk = false;
            showToast("购房日期不能为空");
        }else if(StringUtils.IshasSpecialChar(editName.getText().toString())){
            isOk = false;
            showToast("用户名名称输入有误");
        }else if(!PhoneNumberUtils.isPhoneNumberValid(editphoneNumber.getText().toString())){
            isOk = false;
            showToast("手机号输入有误");
        }else if(!TextUtils.isEmpty(tvDesires.getText().toString()) &&
                StringUtils.IshasSpecialChar(tvDesires.getText().toString())){
            isOk = false;
            showToast("客户描述输入有误");
        }
        return isOk;
    }

    private void showToast(String str){
        Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
    }

    /**
     * 判断是否新建
     * @return
     */
    private boolean isNewCustomer() {
        return mEditedCustomerId == null;
    }



    /**
     * 初始化预选日期
     * @param root
     */
    private void initOrderDate(View root){
        //时间选择器
        pvTime = new TimePickerView(this.getContext(), TimePickerView.Type.ALL);
        pvTime.setTime(new Date());
        pvTime.setCyclic(false);
        pvTime.setCancelable(true);
        //时间选择后回调
        pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {

            @Override
            public void onTimeSelect(Date date) {
                tvOrderDate.setText(DateUtils.getTime(date));
            }
        });

        root.findViewById(R.id.layout_orderdate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pvTime.show();
            }
        });
    }

    /**
     * 初始化最早购房时间
     * @param root
     */
    private void initDesireOption(View root){
        //选项选择器
        pvOptionsDesire = new OptionsPickerView(this.getContext());
        //选项1
        for (int i=1;i<13;i++){
            optionsDesireItems.add(i+"");
        }
        //三级联动效果
        pvOptionsDesire.setPicker(optionsDesireItems);
        //设置选择的三级单位
        pvOptionsDesire.setLabels("个月以内");
        pvOptionsDesire.setTitle("选择最早购房时间");
        pvOptionsDesire.setCyclic(true);
        //监听确定选择按钮
        pvOptionsDesire.setSelectOptions(1);
        pvOptionsDesire.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                //返回的分别是三个级别的选中位置
                tvDesires.setText(optionsDesireItems.get(options1));
            }
        });
        //点击弹出选项选择器
        root.findViewById(R.id.view_desire).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                pvOptionsDesire.show();
            }
        });
    }

    /**
     * 初始化最早购房时间
     * @param root
     */
    private void initHouseAreaOption(View root){
        //选项选择器
        pvOptionsHouseArea = new OptionsPickerView(this.getContext());
        //选项1
        for (int i=5;i<21;i++){
            String str = (i*10)+"";
            optionsHouseAreaItems.add(str);
        }
        //三级联动效果
        pvOptionsHouseArea.setPicker(optionsHouseAreaItems);
        //设置选择的三级单位
        pvOptionsHouseArea.setLabels("平米");
        pvOptionsHouseArea.setTitle("选择购房面积");
        pvOptionsHouseArea.setCyclic(true);
        //监听确定选择按钮
        pvOptionsHouseArea.setSelectOptions(1);
        pvOptionsHouseArea.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                //返回的分别是三个级别的选中位置
                tvHouseArea.setText(optionsHouseAreaItems.get(options1));
            }
        });
        //点击弹出选项选择器
        root.findViewById(R.id.view_housearea).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                pvOptionsHouseArea.show();
            }
        });
    }

}
