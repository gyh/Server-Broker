package com.roommate.android.broker.customer.searchCustomer;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.roommate.android.broker.R;
import com.roommate.android.broker.common.core.BaseActivity;

/**
 * Created by GYH on 2016/5/18.
 */
public class SearchActivity extends BaseActivity {

    private EditText mEtSearchText;

    //用于控制软键盘
    private InputMethodManager mInputManger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mInputManger= (InputMethodManager) getSystemService(SearchActivity.INPUT_METHOD_SERVICE);

        mEtSearchText = (EditText)findViewById(R.id.edit_customer);

        mEtSearchText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && KeyEvent.ACTION_DOWN == event.getAction()) {
                    if (mEtSearchText.getText().toString().isEmpty()) {
                        svProgressHUD.showErrorWithStatus("请输入搜索内容");
                    } else {
                        //或者执行搜索
                        doSearch(mEtSearchText.getText().toString());
                    }

                }
                return false;
            }
        });

        //取消返回
        findViewById(R.id.tv_cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_search;
    }

    /**
     * 搜索内容
     * @param searchStr
     */
    private void doSearch(String searchStr){

    }
}
