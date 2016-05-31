package com.roommate.android.broker;

import android.graphics.Bitmap;
import android.os.Bundle;
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        WebView webView = (WebView)findViewById(R.id.webView);
        //设置点击在本地打开
        WebViewClient client = new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

        };
        webView.setWebViewClient(client);
        String str = "returnUrl=/MemberShop/Default.aspx&token=39C5D60E0C1D9C607772EAE65B680746102C15650A9112B32B659B471EF900C3B8BB45920005B146A416FD7A1F40D19A598E6F88E9369FFF121652D84693217BF04C380094A12D9B0BB250F81FAF63B5B5B73D65C0EB7426ED298FB2E578A9D785E45BA98D79C40F2DC842A717CA40868BB1E3766A5B4CD0CFCA822EECCD9E8AF0DB339F01EE3B79F03BBD60326F996B0593550A321BB440D221F439D2497AD1779B48AF5141B019CDE72C6D10816B484EC4C7885D3200782B6CF5140F8A81D5B782075445BD79F87C759DAA2698935CDDFFD2261320CE1E";
        byte b[] = str.getBytes();
        webView.postUrl("http://m.lj3w.com/MemberShop/Login.aspx",b);
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
