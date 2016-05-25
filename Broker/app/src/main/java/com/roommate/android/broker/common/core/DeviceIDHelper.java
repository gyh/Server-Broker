/*
 * 文件名: DeviceIDHelper
 * 版    权：  Copyright Daman Tech. Co. Ltd. All Rights Reserved.
 * 描    述: [该类的简要描述]
 * 创建人: 郭跃华
 * 创建时间:15/9/9
 *
 * 修改人：
 * 修改时间:
 * 修改内容：[修改内容]
 */
package com.roommate.android.broker.common.core;

import android.text.TextUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.UUID;

/**
 * [一句话功能简述]<BR>
 * [功能详细描述]
 *
 * @author Jamin
 * @version [Daman Client V20150420, 15/9/9]
 */
public class DeviceIDHelper {


    private static String mID = null;
    private static final String DEVICEID = "DEVICEID";
    private static final String DEVICE_ID = "device_id";


    /**
     * @return
     */
    public synchronized static String id() {

        if (mID == null) {
            File idFile = new File(ApplicationHelper.getContext().getFilesDir(), DEVICEID);
            try {
                if (idFile.exists()) {
                    //文件如果存在，在文件里读
                    mID = readDeviceIDFile(idFile);
                } else {
                    //在系统设置里读
                    mID = android.provider.Settings.System.getString(
                            ApplicationHelper.getContext().getContentResolver(), DEVICE_ID);
                    if (TextUtils.isEmpty(mID)) {
                        writeDeviceIDFile(idFile);
                        mID = readDeviceIDFile(idFile);
                    }
                }
            } catch (Exception e) {

            }

            if (TextUtils.isEmpty(mID)) {
                mID = UUID.randomUUID().toString();
                android.provider.Settings.System.putString(ApplicationHelper.getContext().getContentResolver(), DEVICE_ID, mID);
            }
        }

        return mID;
    }

    private static String readDeviceIDFile(File idFile) throws IOException {
        RandomAccessFile f = new RandomAccessFile(idFile, "r");
        byte[] bytes = new byte[(int) f.length()];
        f.readFully(bytes);
        f.close();
        return new String(bytes);
    }

    /**
     * UUID写入文件和系统设置
     *
     * @param idFile
     * @throws IOException
     */
    private static void writeDeviceIDFile(File idFile) throws IOException {
        FileOutputStream out = new FileOutputStream(idFile);
        String id = UUID.randomUUID().toString();
        out.write(id.getBytes());
        out.close();
        android.provider.Settings.System.putString(ApplicationHelper.getContext().getContentResolver(), DEVICE_ID, id);

    }


    private static boolean isDeviceidExist() {
        if (!TextUtils.isEmpty(mID)) {
            return true;
        }

        String deviceUUID = android.provider.Settings.System.getString(ApplicationHelper.getContext().getContentResolver(), DEVICE_ID);
        if (!TextUtils.isEmpty(deviceUUID)) {
            return true;
        }
        File idFile = new File(ApplicationHelper.getContext().getFilesDir(), DEVICEID);
        try {
            if (idFile.exists()) {
                deviceUUID = readDeviceIDFile(idFile);
                if (!TextUtils.isEmpty(deviceUUID)) {
                    return true;
                }
            }
        } catch (Exception e) {
        }
        return false;
    }

    /**
     * @deprecated 清除device id
     */
    private static void clearDeviceID() {
        mID = null;
        File idFile = new File(ApplicationHelper.getContext().getFilesDir(), DEVICEID);
        if (idFile != null && idFile.exists()) {
            idFile.delete();
        }
        android.provider.Settings.System.putString(
                ApplicationHelper.getContext().getContentResolver(), DEVICE_ID, "");
    }

}
