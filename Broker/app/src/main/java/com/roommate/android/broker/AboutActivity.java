package com.roommate.android.broker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.roommate.android.broker.common.core.BaseActivity;

/**
 * Created by GYH on 2016/5/30.
 */
public class AboutActivity extends BaseActivity{


    private final static String URL_KEY = "urlkey";

    private String URL = "";

    public static void startAbout(Activity activity,String url){
        Intent intent = new Intent();
        intent.setClass(activity,AboutActivity.class);
        intent.putExtra(URL_KEY,url);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        URL = getIntent().getStringExtra(URL_KEY);

        final WebView webView = (WebView)findViewById(R.id.webView);

        WebChromeClient wvcc = new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                actionBar.setTitle(title);
            }
        };
        webView.setWebChromeClient(wvcc);

        // 创建WebViewClient对象
        WebViewClient wvc = new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 使用自己的WebView组件来响应Url加载事件，而不是使用默认浏览器器加载页面
                webView.loadUrl(url);
                // 消耗掉这个事件。Android中返回True的即到此为止吧,事件就会不会冒泡传递了，我们称之为消耗掉
                return true;
            }
        };

        webView.setWebViewClient(wvc);

        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);

        webView.loadUrl(URL);

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
