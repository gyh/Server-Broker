package com.roommate.android.broker.common;

import android.Manifest;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.widget.EditText;
import android.widget.Toast;

import com.roommate.android.broker.R;

import org.xutils.common.util.LogUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by GYH on 2016/6/2.
 */
public class PhoneNumberUtils {

    public static String hidePhoneNumber(String number){
        if(isPhoneNumberValid(number)){
            return number.substring(0,3) + "****" + number.substring(7, number.length());
        }
        return number;
    }

    /**
     * 拨打电话
     *
     * @param phoneNumber
     */
    public static void dial(String phoneNumber,Context context) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 判断手机号码是否输入正确
     * */
    public static boolean isPhoneNumberValid(String phoneNumber) {
        boolean isValid = false;
        String expression = "((^(13|15|18|14|17)[0-9]{9}$)|(^0[1,2]{1}\\d{1}-?\\d{8}$)|(^0[3-9] {1}\\d{2}-?\\d{7,8}$)|(^0[1,2]{1}\\d{1}-?\\d{8}-(\\d{1,4})$)|(^0[3-9]{1}\\d{2}-? \\d{7,8}-(\\d{1,4})$))";
        CharSequence inputStr = phoneNumber;
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    /**
     * 选择通话类型
     * */
    public static void alertPhoneType(final Context context, final EditText phoneedit) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.action_phone)
                .setItems(R.array.phonetypearr, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            getPhonenumber(context, 1, phoneedit);
                        } else if (which == 1) {
                            getPhonenumber(context, 2, phoneedit);
                        } else if (which == 2) {
                            getPhonenumber(context, 3, phoneedit);
                        }
                    }
                });
        Dialog dialog = builder.create();
        dialog.show();
    }

    /**
     * 选择通话类型
     * */
    public static void getPhonenumber(Context baseActivity, int Type, final EditText phoneedit) {
        int i = 0;
        ContentResolver cr = baseActivity.getContentResolver();
        if (ActivityCompat.checkSelfPermission(baseActivity, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Cursor cursor = cr.query(CallLog.Calls.CONTENT_URI,
                new String[]{CallLog.Calls.NUMBER, CallLog.Calls.CACHED_NAME, CallLog.Calls.TYPE, CallLog.Calls.DATE, CallLog.Calls.DURATION},
                null, null, CallLog.Calls.DEFAULT_SORT_ORDER);
        String strtemp="";
        while (cursor.moveToNext()){
            if(Type == cursor.getInt(2)){
                strtemp+=cursor.getString(0)+"_";
                i++;
            }
        }
        final String[] strphone=strtemp.split("_");
        cursor.close();
        if(strphone.length>0){
            AlertDialog.Builder builder = new AlertDialog.Builder(baseActivity);
            builder.setTitle(R.string.action_phone)
                    .setItems(strphone, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            phoneedit.setText(strphone[which]);
                        }
                    });
            Dialog dialog = builder.create();
            dialog.show();
        }else {
            Toast.makeText(baseActivity,"没有记录",Toast.LENGTH_SHORT).show();
        }
    }
}
