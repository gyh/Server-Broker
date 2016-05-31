package com.roommate.android.broker.common;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.roommate.android.broker.common.core.ApplicationHelper;


/**
 * Created by GYH on 2016/4/29.
 */
public class DialogUtils {


    public static void oneButtonDialog(Context context, String title, String message, String btnName, DialogInterface.OnClickListener onClickListener){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(btnName, onClickListener);
        builder.create().show();
    }
    public static void twoButtonDialog(Context context, String title, String message, String onebtnName,
                                       DialogInterface.OnClickListener oneOnClickListener,
                                       String twoBtnName, DialogInterface.OnClickListener twoOnClickListener){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(onebtnName, oneOnClickListener);
        builder.setNegativeButton(twoBtnName, twoOnClickListener);
        builder.create().show();
    }
}
