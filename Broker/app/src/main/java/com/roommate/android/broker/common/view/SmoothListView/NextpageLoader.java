package com.roommate.android.broker.common.view.SmoothListView;

import android.widget.BaseAdapter;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.google.gson.Gson;
import com.roommate.android.broker.common.ApiContant;
import com.roommate.android.broker.common.core.BaseActivity;


import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.http.request.HttpRequest;
import org.xutils.x;

import java.util.Map;


/**
 * Created by GYH on 2016/5/23.
 */
public class NextpageLoader implements SmoothListView.ISmoothListViewListener{


    private SmoothListView adapterView;

    private BaseAdapter baseAdapter;

    protected BaseActivity baseActivity;

    //参数
    protected RequestParams  params;
    //页码key
    protected String pageNumParamKey = "page";
    protected String pageSizeParamKey = "pagesize";
    //开始页码
    protected Integer pageNum = 1;
    protected Integer pageSize = 10;
    // 网络出错是否通知用户
    protected boolean httpNotifyUser = true;

    public NextpageLoader(SmoothListView adapterView, BaseAdapter baseAdapter, BaseActivity baseActivity,RequestParams params){
        this.adapterView = adapterView;
        this.baseAdapter = baseAdapter;
        this.baseActivity = baseActivity;
        this.params = params;
    }


    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }

    /**
     * 显示开始
     */
    public void showThispage(){
        //初始化
    }

    /***
     * 请求数据
     */
    private void loading(){
        RequestParams params = new RequestParams("https://www.baidu.com/s");
        params.addQueryStringParameter("wd", "xUtils");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                baseActivity.svProgressHUD.showInfoWithStatus(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {
            }
        });
    }

    /***
     * 显示下一页
     */
    private void showNextPager(){

    }

    private void refreshPager(){

    }

}
