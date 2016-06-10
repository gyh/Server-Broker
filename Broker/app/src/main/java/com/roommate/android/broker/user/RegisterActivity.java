package com.roommate.android.broker.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;


import com.bigkoo.svprogresshud.SVProgressHUD;
import com.google.gson.Gson;
import com.roommate.android.broker.R;
import com.roommate.android.broker.common.ApiContant;
import com.roommate.android.broker.common.JsonUtils;
import com.roommate.android.broker.common.core.BaseActivity;

import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by GYH on 2016/3/24.
 */
public class RegisterActivity extends BaseActivity {


    public static void startResultRegister(Activity activity,int requestCode){
        Intent intent  = new Intent();
        intent.setClass(activity,RegisterActivity.class);
        activity.startActivityForResult(intent,requestCode);
    }

    private EditText eduserName;
    private EditText edpassword;
    private EditText edpassword2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        eduserName = (EditText)findViewById(R.id.ed_username);
        edpassword = (EditText)findViewById(R.id.ed_password);
        edpassword2 = (EditText)findViewById(R.id.ed_password2);

        findViewById(R.id.btn_regiest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkEditText()){
                    registerService();
                }
            }
        });

        findViewById(R.id.back_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    private boolean checkEditText(){
        String username = eduserName.getText().toString().trim();
        String password = edpassword.getText().toString().trim();
        String password2 = edpassword2.getText().toString().trim();
        if(TextUtils.isEmpty(username)){
            svProgressHUD.showErrorWithStatus(getResources().getString(R.string.empty_username));
            return false;
        }else if(TextUtils.isEmpty(password)){
            svProgressHUD.showErrorWithStatus(getResources().getString(R.string.empty_password));
            return false;
        }else if(TextUtils.isEmpty(password2)){
            svProgressHUD.showErrorWithStatus(getResources().getString(R.string.empty_password2));
        }else if(!password.equals(password2)){
            svProgressHUD.showErrorWithStatus(getResources().getString(R.string.agree_password));
        }
        return true;
    }

    private void registerService(){
        svProgressHUD.showWithProgress("正在注册", SVProgressHUD.SVProgressHUDMaskType.None);
        RequestParams params = new RequestParams(ApiContant.REGISTER_URL);

        Map<String,String> stringMap =new HashMap<>();
        stringMap.put("mobile",eduserName.getText().toString());
        stringMap.put("passwd",edpassword.getText().toString());
        String gson =  new Gson().toJson(stringMap);
        params.addBodyParameter("Data",gson);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(final String result) {
                LogUtil.d(result);
                if(JsonUtils.isResultSuccess(result)){
                    UserInfoCase.saveUserInfo(JsonUtils.getData(result));
                    post(new Runnable() {
                        @Override
                        public void run() {
                            svProgressHUD.showSuccessWithStatus("注册成功");
                            setResult(RESULT_OK);
                            finish();
                        }
                    });
                }else {
                    post(new Runnable() {
                        @Override
                        public void run() {
                            svProgressHUD.showErrorWithStatus(JsonUtils.resultMsg(result));
                        }
                    });
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                svProgressHUD.showErrorWithStatus("哎呀，网络异常");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }


    @Override
    protected boolean needLogin() {
        return false;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
    }
}
