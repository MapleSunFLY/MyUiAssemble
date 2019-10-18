package com.fly.viewlibrary.utils;

import android.text.TextUtils;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 包    名 : com.fly.viewlibrary.utils
 * 作    者 : FLY
 * 创建时间 : 2019/8/15
 * 描述: 字符串处理
 */
public class StringUtils {

    /**
     * 数字保留两位小数
     *
     * @param money
     * @return
     */
    public static String twoPointNum(float money) {
        DecimalFormat format = new DecimalFormat("#0.00");
        return format.format(money);
    }

    public static String doubleString(double num, String pattern) {
        if(TextUtils.isEmpty(pattern)) {
            pattern = "0.00";
        }

        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        return decimalFormat.format(num);
    }

    /**
     * 判断字符串都为数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("-?[0-9]+.?[0-9]+");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /**
     * 判断一个字符串是否含有数字
     *
     * @param content
     * @return
     */

    public static boolean hasDigit(String content) {
        boolean flag = false;
        Pattern p = Pattern.compile(".*\\d+.*");
        Matcher m = p.matcher(content);
        if (m.matches())
            flag = true;
        return flag;
    }


    /**
     * 去掉字符串的空格
     *
     * @param input
     * @return
     */
    public static String trim(String input) {
        if (input == null) return "";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == ' ') {
                continue;
            } else {
                sb.append(input.charAt(i));
            }
        }
        return sb.toString();
    }

}
