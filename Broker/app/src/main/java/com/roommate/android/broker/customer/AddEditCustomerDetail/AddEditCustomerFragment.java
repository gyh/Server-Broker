package com.roommate.android.broker.customer.AddEditCustomerDetail;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.roommate.android.broker.R;
import com.roommate.android.broker.common.core.BaseActivity;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by GYH on 2016/6/1.
 */
public class AddEditCustomerFragment extends Fragment implements AddEditCustomerContract.View{

    public static final String ARGUMENT_EDIT_CUSTOMER_ID = "EDIT_CUSTOMER_ID";

    private String mEditedCustomerId;

    private AddEditCustomerContract.Presenter presenter;

    private Handler mHandler;

    private EditText tvName;

    private EditText tvPhoneNumber;
    private EditText tvHouseAare;

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
                if (isNewTask()) {
//                    addEditCustomerPresenter.createCustomer(
//                            mTitle.getText().toString(),
//                            mDescription.getText().toString());
                } else {
//                    addEditCustomerPresenter.updateCustomer(
//                            mTitle.getText().toString(),
//                            mDescription.getText().toString());
                }

            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.add_edit_customer, container, false);
//        tvName = (EditText) root.findViewById(R.id.);
//        tvHouseAare = (EditText) root.findViewById(R.id.tv_area);
//        tvPhoneNumber = (EditText) root.findViewById(R.id.tv_phone);
        setHasOptionsMenu(true);
        setRetainInstance(true);

        TextInputLayout inputLayout = (TextInputLayout)root.findViewById(R.id.textInputLayout);
        inputLayout.setError("123456789");
        inputLayout.setErrorEnabled(false);

        return root;
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
        return false;
    }

    @Override
    public void setName(String name) {

    }

    @Override
    public void setPhoneNumber(String phoneNumber) {

    }

    @Override
    public void setDesire(String desire) {

    }

    @Override
    public void setHouseArea(String houseArea) {

    }

    @Override
    public void setDescries(String describe) {

    }

    @Override
    public void setOrderDate(String inputDate) {

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

    private boolean isNewTask() {
        return mEditedCustomerId == null;
    }

}
