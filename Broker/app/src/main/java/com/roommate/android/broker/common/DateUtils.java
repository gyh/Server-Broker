package com.roommate.android.broker.common;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by GYH on 2016/6/2.
 */
public class DateUtils {

    /**
     * 获取时间数据
     * @param date
     * @return
     */
    public static String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(date);
    }
}
