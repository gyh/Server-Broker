package com.roommate.android.broker.common.core;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.baidu.mobstat.StatService;
import com.bigkoo.svprogresshud.SVProgressHUD;
import com.roommate.android.broker.R;
import com.roommate.android.broker.user.UserInfoCase;
import com.roommate.android.broker.user.LoginActivity;

import java.lang.ref.WeakReference;

/**
 * Created by GYH on 2016/5/17.
 */
public abstract class BaseActivity extends AppCompatActivity{


    /**
     * 该activity持有的handler类
     */
    private FramHandler mHandler;

    private LinearLayout mContentContainer;

    protected static final int LOGIN_REQUEST = 1;

    public SVProgressHUD svProgressHUD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        svProgressHUD = new SVProgressHUD(this);

        //工具类
        ApplicationHelper.setCurrentActivity(this);
        //同步
        mHandler = new FramHandler(this);

        //初始化布局
        LayoutInflater inflater = LayoutInflater.from(this);
        setContentView(R.layout.activity_base);
        mContentContainer = (LinearLayout) findViewById(R.id.content_container);
        View contentView = inflater.inflate(getLayoutId(), null);
        mContentContainer.addView(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);


        //判断是否需求登录
        if(needLogin()&&!UserInfoCase.isLogin()){
            Intent intent = new Intent();
            intent.setClass(this, LoginActivity.class);
            startActivityForResult(intent,LOGIN_REQUEST);
            return;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(this);
    }

    /**
     * 获取hander对象<BR>
     *
     * @return 返回handler对象
     */
    protected final Handler getHandler() {
        return mHandler;
    }
    /**
     * 发送消息
     *
     * @param what 消息标识
     */
    protected final void sendEmptyMessage(int what) {
        if (mHandler != null) {
            mHandler.sendEmptyMessage(what);
        }
    }

    /**
     * 延迟发送空消息
     *
     * @param what        消息标识
     * @param delayMillis 延迟时间
     */
    protected final void sendEmptyMessageDelayed(int what, long delayMillis) {
        if (mHandler != null) {
            mHandler.sendEmptyMessageDelayed(what, delayMillis);
        }
    }


    /**
     * post一段操作到UI线程
     *
     * @param runnable Runnable
     */
    public final void post(final Runnable runnable) {
        if (mHandler != null) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (BaseActivity.this.isFinishing()) {
                        return;
                    }
                    runnable.run();
                }
            });
        }
    }

    /**
     * post一段操作到UI线程
     *
     * @param runnable Runnable
     */
    protected final void postDelay(final Runnable runnable, int delayMillis) {
        if (mHandler != null) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (BaseActivity.this.isFinishing()) {
                        return;
                    }
                    runnable.run();
                }
            }, delayMillis);
        }
    }

    /**
     * 发送消息
     *
     * @param msg 消息对象
     */
    protected final void sendMessage(Message msg) {
        if (mHandler != null) {
            mHandler.sendMessage(msg);
        }
    }

    /**
     * 延迟发送消息
     *
     * @param msg         消息对象
     * @param delayMillis 延迟时间
     */
    protected final void sendMessageDelayed(Message msg, long delayMillis) {
        if (mHandler != null) {
            mHandler.sendMessageDelayed(msg, delayMillis);
        }
    }

    /**
     * 返回一个boolean表示展示该页面是否需要登录成功
     *
     * @return boolean 是否是登录后的页面
     */
    protected boolean needLogin(){
        return true;
    }

    protected void loginResult(Intent data){

    }

    public abstract int getLayoutId();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == LOGIN_REQUEST){
            if(resultCode == RESULT_OK){
                loginResult(data);
            }else {
                finish();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        }else {
            return super.onOptionsItemSelected(item);
        }

    }


    /**
     * logic通过handler回调的方法<BR>
     * 通过子类重载可以实现各个logic的sendMessage到handler里的回调方法
     *
     * @param msg Message对象
     */
    protected void handleStateMessage(Message msg) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }

    private static class FramHandler extends Handler {

        private final WeakReference<BaseActivity> m_activity;

        FramHandler(BaseActivity ac) {
            m_activity = new WeakReference(ac);
        }

        @Override
        public void handleMessage(Message msg) {
            if (m_activity.get() != null) {
                m_activity.get().handleStateMessage(msg);
            }
        }

    }

}
