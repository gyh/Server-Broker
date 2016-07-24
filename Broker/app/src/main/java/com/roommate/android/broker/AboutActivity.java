package com.roommate.android.broker;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.roommate.android.broker.common.core.BaseActivity;

import java.io.UnsupportedEncodingException;

/**
 * Created by GYH on 2016/5/30.
 */
public class AboutActivity extends BaseActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);


    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    protected boolean needLogin() {
        return false;
    }
}
