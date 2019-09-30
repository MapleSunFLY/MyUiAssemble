package com.fly.viewlibrary.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 包    名 : com.fly.viewlibrary.utils
 * 作    者 : FLY
 * 创建时间 : 2019/8/15
 * 描述: 时间处理
 */
public class TimeUtils {

    public TimeUtils() {
    }

    public static String format(long date, String formatStr) {
        if (date == 0L) {
            return "";
        } else {
            SimpleDateFormat format = new SimpleDateFormat(formatStr);
            return format.format(new Date(date));
        }
    }
}
