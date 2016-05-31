package com.roommate.android.broker.common.view.SmoothListView;

import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.roommate.android.broker.common.core.BaseActivity;
import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * Created by GYH on 2016/5/23.
 */
public abstract class NextpageLoader implements SmoothListView.ISmoothListViewListener{

    private static final String TAG ="NextpageLoader";

    private SmoothListView adapterView;

    private BaseAdapter baseAdapter;

    protected BaseActivity baseActivity;

    //参数
    protected HashMap<String,Object> params;
    //baseUrl
    private String baseurl;

    //页码key
    protected String pageNumParamKey = "page";
    protected String pageSizeParamKey = "pagesize";
    //当前页码
    protected Integer pageNum = 1;
    protected Integer pageSize = 10;
    //开始页码
    private  Integer startPageNum = 0;

    //可显示商品的最大数量
    private Integer maxNum = Integer.MAX_VALUE;
    // 网络出错是否通知用户
    protected boolean httpNotifyUser = true;
    // 标志位，保证滚动到屏幕底部的时候，不会多次触发加载
    private boolean loading = false;
    // 是否分页加载
    private boolean isPaging = true;
    //判断是否最后一页
    private boolean loadedLastPage = false;
    // 是否预加载
    private boolean isPreloading = true;
    // 是否静止状态
    private boolean isHolding = false;
    // 是否滑行状态
    private boolean isFling = false;
    // 标志位，如果第二页还在下载的过程中就滚动到页尾部，可以通过该标志位申请下载后马上显示。
    private boolean loadedShow = false;
    // 这个实例对象是否从来没从网络加载过数据，这是头次加载
    protected boolean firstLoad = true;
    // 是否需要遮罩
    protected boolean isEffect = true;
    // 是否存在数据改变通知
    private boolean hasNotify;
    // 直接引用当前显示的数据
    protected ArrayList<Object> showItemList = new ArrayList<Object>();
    // 用于预存下一页的数据
    protected ArrayList<?> nextItemList = null;

    public NextpageLoader(SmoothListView adapterView, BaseActivity baseActivity,HashMap<String,Object> params,String baseurl){
        this.adapterView = adapterView;
        this.baseActivity = baseActivity;
        this.params = params;
        this.baseurl = baseurl;
    }


    @Override
    public void onRefresh() {
        baseActivity.post(new Runnable() {
            @Override
            public void run() {
                showItemList.clear();
                baseAdapter.notifyDataSetChanged();
                pageNum = startPageNum;
                showPageOne();
            }
        });
    }

    @Override
    public void onLoadMore() {
        baseActivity.post(new Runnable() {
            @Override
            public void run() {
                if(!loadedLastPage){
                    tryShowNextPage();
                }else {
                    adapterView.setLoadMoreEnable(false);
                }
            }
        });
    }

    /**
     * 设置开始页码
     *
     * @param pageNum
     */
    public void setStartNum(Integer pageNum){
        this.startPageNum = pageNum;
        this.pageNum = pageNum;
    }

    public void setPageNumParamKey(String pageNumParamKey) {
        this.pageNumParamKey = pageNumParamKey;
    }

    public void setPageSizeParamKey(String pageSizeParamKey) {
        this.pageSizeParamKey = pageSizeParamKey;
    }

    /**
     * 获取当前条目数量
     * @return
     */
    public int getItemCount(){
        return showItemList.size();
    }

    public Object getItem(int postion){
        if(postion<getItemCount()){
            return showItemList.get(postion);
        }
        return null;
    }

    /**
     * 设置是否启用分页
     *
     * @param isPaging
     */
    public void setPaging(boolean isPaging) {
        this.isPaging = isPaging;
    }

    /**
     * 是否预加载
     * @param isPreloading
     */
    public void setPreloading(boolean isPreloading) {
        this.isPreloading = isPreloading;
    }

    /***
     * 请求数据
     */
    private void loading(){
        //判断是否需要遮罩  只有在第一次加载的时候才有遮罩
        if(isEffect && firstLoad){
            baseActivity.svProgressHUD.showWithProgress("正在加载", SVProgressHUD.SVProgressHUDMaskType.None);
        }
        RequestParams requestParams = new RequestParams(baseurl);
        Iterator iter = this.params.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            Object key = entry.getKey();
            Object val = entry.getValue();
            requestParams.addQueryStringParameter(key.toString(),val.toString());
        }
        requestParams.addQueryStringParameter(pageNumParamKey,pageNum+"");
        requestParams.addQueryStringParameter(pageSizeParamKey,pageSize+"");
        LogUtil.d(TAG+"  ----Input---   " + requestParams.toString());
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                LogUtil.d(TAG+"  ----output---   " + result);

                final ArrayList<?> itemList = toList(result);

                baseActivity.post(new Runnable() {
                    @Override
                    public void run() {

                        if(isEffect && firstLoad){
                            baseActivity.svProgressHUD.showSuccessWithStatus("恭喜，加载成功");
                            firstLoad = false;
                        }

                        if(null == itemList){
                            showError();
                        }else {
                            nextItemList = itemList;

                            if(loadedShow()){
                                if(x.isDebug()){
                                    LogUtil.d( "show now -->> ");
                                }
                                // 这个方法里面会自动又继续加载下一页
                                showNextPage(itemList);
                            }
                        }

                    }
                });
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                firstLoad = false;
                baseActivity.svProgressHUD.dismiss();
                showError();
            }

            @Override
            public void onCancelled(CancelledException cex) {
                baseActivity.svProgressHUD.dismiss();
                firstLoad = false;
            }

            @Override
            public void onFinished() {
                baseActivity.svProgressHUD.dismiss();
                firstLoad = false;
            }
        });
    }

    //加载第一页
    public void showPageOne() {

        // 申请要求下载后马上显示
        applyLoadedShow();
        //加载下一页
        tryShowNextPage();
    }

    /**
     * @author lijingzuo
     *
     *         Time: 2011-1-23 上午11:59:10
     *
     *         Name:
     *
     *         Description: 被通知数据发生改变
     *
     *
     */
    public void notifyDataSetChanged() {
        if (!isHolding) {
            if (null != baseAdapter) {
                baseAdapter.notifyDataSetChanged();
            }
        } else {
            hasNotify = true;
        }
    }

    /**
     * @author guoyuehua
     *
     *         Time: 2010-12-25 下午04:39:38
     *
     *         Name:
     *
     *         Description: 尝试显示下一页。每次滚动到最后一行会触发这个
     *
     *
     */
    private void tryShowNextPage() {

        if (loadedLastPage) {// 如果已经是最后一页，不作尝试
            if (x.isDebug()) {
                LogUtil.d("loadedLast Page " + loadedLastPage);
            }
            return;
        }

        if (null == nextItemList) {// 预加载数据为空才尝试加载
            if (x.isDebug()) {
                LogUtil.d("nextItemList == null isPreloading " + isPreloading);
            }
            applyLoadedShow();// 申请要求下载后马上显示

            if (!loadingLock()) {// 如果不能加锁代表已经有在下载中
                return;
            }

            if(x.isDebug()){
                LogUtil.d("isPreloading = "+isPreloading);
            }
            if (isPreloading)
                loading();
        } else {// 预加载数据不为空，就直接显示
            if (x.isDebug()) {
                LogUtil.d("show do -->> ");
                System.err.println("showNextPage(nextItemList)");
            }
            showNextPage(nextItemList);
        }

    }

    //初始化列表
    private void initAdapterView(){
        if (x.isDebug()) {
            LogUtil.v("setAdapter adpter size = " + baseAdapter.getCount());
        }
        this.adapterView.setAdapter(baseAdapter);
        this.adapterView.setSmoothListViewListener(this);

        final OnScrollLastListener onScrollLastListener = new OnScrollLastListener() {
            @Override
            public void onScrollLast() {
                // XXX 下拉到低的时候触发，一般默认，预留扩展
            }

            @Override
            public void onScrollFling() {
                // 滚动滑动时
                isFling = true;
            }

            @Override
            public void onScrollIdle() {
                // 滚动停止时
                isHolding = false;
                isFling = false;
                baseActivity.post(new Runnable() {
                    @Override
                    public void run() {
                        if (hasNotify) {// 是否需要更新
                            hasNotify = false;
                            if (baseAdapter != null) {
                                baseAdapter.notifyDataSetChanged();
                            }
                        }
                        checkLast();
                    }
                });
            }
        };

        adapterView.setOnScrollListener(onScrollLastListener);

        adapterView.setOnTouchListener(new ListView.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        isHolding = true;
                        break;
                    case MotionEvent.ACTION_UP:
                        if (!isFling) {
                            onScrollLastListener.onScrollIdle();
                        }

                        break;
                }
                return false;
            }
        });
    }


    /***
     * 加载下一页数据
     */
    private void showNextPage(ArrayList<?> itemList){
        // 下页已经投入使用
        nextItemList = null;

        showItemList.addAll(itemList);
        if (x.isDebug()) {
            LogUtil.d("showItemList size = " + showItemList.size());
        }
        if(itemList.size() < pageSize){
            adapterView.setLoadMoreEnable(false);
        }else{
            adapterView.setLoadMoreEnable(true);
        }

        //如果当前显示的列表为空的话，则显示默认视图
        if(showItemList.size() == 0){
            showError();
        }
        //如果当前页面小于页数 或者 不分页 或者 加载页数已经到最大
        if (itemList.size() < pageSize || !isPaging || showItemList.size() >= maxNum) {
            // 最后一页
            loadedLastPage = true;
            if (adapterView instanceof ListView)
                ((ListView) adapterView).setOnScrollListener(null);
        } else {
            if (x.isDebug()) {
                LogUtil.d("showNextPage() isPreloading " + isPreloading);
            }
            pageNum++;
            loading();// 如果要显示这页就马上继续加载下一页
        }

        if (null != baseAdapter) {

            baseAdapter.notifyDataSetChanged();
        }else {
            baseAdapter = createAdapter();
            //初始化
            initAdapterView();
        }
        loadingUnLock();

        if(loadedLastPage){
            adapterView.setLoadMoreEnable(false);
        }
    }

    /**
     * @author 郭跃华
     *
     *         Time: 2010-12-25 下午04:27:26
     *
     *         Name:
     *
     *         Description: 当需要下载下一页数据时，加锁。目前没有正在下载的，那么返回true，可以加锁，否则返回false。
     *
     * @return
     *
     */
    private synchronized boolean loadingLock() {
        if (!loading) {// 如果没有加载中就改为加载中
            loading = true;
            return loading;
        }
        return false;
    }

    /**
     * @author 郭跃华
     *
     *         Time: 2010-12-25 下午04:27:29
     *
     *         Name:
     *
     *         Description: 下载完毕时，解锁
     *
     *
     */
    private synchronized void loadingUnLock() {
        loading = false;
    }

    /**
     * @author guoyuehua
     *
     *         Time: 2010-12-25 下午04:06:08
     *
     *         Name:
     *
     *         Description: 下载后判断是否需要马上显示
     *
     * @return
     *
     */
    private boolean loadedShow() {
        if (loadedShow) {
            loadedShow = false;
            return true;
        } else {
            return false;
        }
    }

    /***
     *
     * @return
     */
    private BaseAdapter createAdapter(){
        return new MyBaseAdapter(showItemList);
    }
    /**
     * @author guoyuehua
     *
     *         Time: 2010-12-25 下午04:33:55
     *
     *         Name:
     *
     *         Description: 申请要求下载后马上显示
     *
     * @return
     *
     */
    private void applyLoadedShow() {
        loadedShow = true;
    }

    /**
     * @author guoyuehua
     *
     *         Time: 2010-12-25 下午04:00:55
     *
     *         Name:
     *
     *         Description: 错误呈现逻辑
     *
     *
     */
    protected abstract void showError();

    /**
     * @author guoyuehua
     *
     *         Time: 2010-12-25 下午03:59:12
     *
     *         Name:
     *
     *         Description: 根据网络返回的result内容转换为列表需要的beanList。如果转换过程发生异常，无法满足基本显示要求 ，请返回空，此类会自动调用错误呈现。
     *
     * @param result
     * @return
     *
     */
    protected abstract ArrayList<?> toList(String result);

    protected abstract View getItemView(int position, View convertView, ViewGroup parent);


    class MyBaseAdapter extends BaseAdapter{

        List<Object> datas ;

        MyBaseAdapter( List<Object> datas){
            this.datas = datas;
        }

        @Override
        public int getCount() {
            return datas.size();
        }

        @Override
        public Object getItem(int position) {
            return datas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getItemView(position,convertView,parent);
        }
    }

    /**
     * Copyright 2010 Jingdong Android Mobile Application
     *
     * @author guoyuehua
     *
     *         Time: 2010-12-25 下午04:07:04
     *
     *         Name:
     *
     *         Description: 滚动到最后一行的监听器逻辑
     */
    private abstract class OnScrollLastListener implements AbsListView.OnScrollListener {

        private int firstVisibleItem;
        private int visibleItemCount;
        private int totalItemCount;

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            this.firstVisibleItem = firstVisibleItem;
            this.visibleItemCount = visibleItemCount;
            this.totalItemCount = totalItemCount;
        }

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            switch (scrollState) {
                case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:// 滚动开始时
                    break;
                case AbsListView.OnScrollListener.SCROLL_STATE_FLING:// 滚动滑行时
                    onScrollFling();
                    break;
                case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:// 滚动停止时
                    onScrollIdle();
                    break;
            }
        }

        public void checkLast() {
            if (firstVisibleItem + visibleItemCount == totalItemCount) {
                onScrollLast();
            }
        }

        public abstract void onScrollFling();// 滚动滑行时

        public abstract void onScrollIdle();// 滚动停止时

        public abstract void onScrollLast();// 滚动到最后一项时

    }
}
