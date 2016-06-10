package com.roommate.android.broker.user;

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
 * Created by GYH on 2016/3/23.
 */
public class LoginActivity extends BaseActivity {

    //注册标示
    private static final int REGISTER_REQUEST = 2;

    private EditText userName;

    private EditText password;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        userName = (EditText)findViewById(R.id.ed_username);
        password = (EditText)findViewById(R.id.ed_password);

        findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(ApiContant.isDebug){
//                    UserInfoCase.loginss = true;
//                    setResult(RESULT_OK);
//                    finish();
//                    return;
//                }
                if(checkVaule()){
                    loginService();
                }
            }
        });

        findViewById(R.id.go_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterActivity.startResultRegister(LoginActivity.this,REGISTER_REQUEST);
            }
        });
    }

    private boolean checkVaule(){
        if(TextUtils.isEmpty(userName.getText().toString())){
            svProgressHUD.showErrorWithStatus(getResources().getString(R.string.empty_username));
           return false;
        }else {
            if(TextUtils.isEmpty(password.getText().toString())){
                svProgressHUD.showErrorWithStatus(getResources().getString(R.string.empty_password));
                return false;
            }
        }
        return true;
    }

    private void loginService(){
        svProgressHUD.showWithProgress("正在登录", SVProgressHUD.SVProgressHUDMaskType.None);
        RequestParams params = new RequestParams(ApiContant.LOGIN_URL);

        Map<String,String> stringMap =new HashMap<>();
        stringMap.put("mobile",userName.getText().toString());
        stringMap.put("passwd",password.getText().toString());
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
                            svProgressHUD.showSuccessWithStatus("登录成功");
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
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected boolean needLogin() {
        return false;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REGISTER_REQUEST) {
            if (resultCode == RESULT_OK) {
                setResult(RESULT_OK);
                finish();
            } else {

            }
        }
    }
}
