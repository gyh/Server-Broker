package com.roommate.android.broker.common.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.roommate.android.broker.R;

/**
 * Created by 郭跃华 on 2016/7/30.
 */

public class UpdataDialogFragment extends DialogFragment{


    public static final String TAG = "UpdataDialogFragment";
    private static final String KEY_DATA = "datakey";

    private boolean isForce = false;//是否强制

    private TextView title;

    private TextView msg;

    public static UpdataDialogFragment newIntance(boolean isForce){
        UpdataDialogFragment updataDialogFragment = new UpdataDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean(KEY_DATA,isForce);
        updataDialogFragment.setArguments(bundle);
        return updataDialogFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rooView = inflater.inflate(R.layout.dialog_update,container,false);

        title = (TextView)rooView.findViewById(R.id.dialog_title);
        msg = (TextView)rooView.findViewById(R.id.dialog_msg);

        if(isForce){
            rooView.findViewById(R.id.viewDis).setVisibility(View.GONE);
            rooView.findViewById(R.id.line).setVisibility(View.GONE);
            rooView.findViewById(R.id.viewUpdata).setVisibility(View.VISIBLE);
            rooView.findViewById(R.id.viewUpdata).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(),"开始更新",Toast.LENGTH_SHORT).show();
                    dismiss();
                }
            });
        }else {
            rooView.findViewById(R.id.viewDis).setVisibility(View.VISIBLE);
            rooView.findViewById(R.id.line).setVisibility(View.VISIBLE);
            rooView.findViewById(R.id.viewUpdata).setVisibility(View.VISIBLE);

            rooView.findViewById(R.id.viewDis).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });

            rooView.findViewById(R.id.viewUpdata).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(),"开始更新",Toast.LENGTH_SHORT).show();
                    dismiss();
                }
            });
        }

        return rooView;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog=  super.onCreateDialog(savedInstanceState);
        if(isForce){
            dialog.setCanceledOnTouchOutside(false);
        }else {
            dialog.setCanceledOnTouchOutside(true);
        }
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_BACK){
                    getActivity().finish();
                    return true;
                }else {
                    //这里注意当不是返回键时需将事件扩散，否则无法处理其他点击事件
                    return false;
                }
            }
        });
        Window window = dialog.getWindow(); //得到对话框
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        window.setWindowAnimations(R.style.dialogWindowAnim); //设置窗口弹出动画
        window.setBackgroundDrawableResource(R.color.app_background); //设置对话框背景为透明
        WindowManager.LayoutParams wl = window.getAttributes();
        //根据x，y坐标设置窗口需要显示的位置
        wl.x = 0; //x小于0左移，大于0右移
        wl.y = 1; //y小于0上移，大于0下移
        wl.alpha = 0.6f; //设置透明度
        wl.gravity = Gravity.CENTER; //设置重力
        window.setAttributes(wl);
        return dialog;
    }

}
