package com.fly.viewlibrary.sortlist;

import java.util.Comparator;

/**
 * 包    名 : com.fly.viewlibrary.sortlist
 * 作    者 : FLY
 * 创建时间 : 2019/9/18
 * 描述: 根据拼音来排列ListView里面的数据类
 */
public class PinyinComparator implements Comparator<SortModel> {

    public int compare(SortModel o1, SortModel o2) {
        //这里主要是用来对ListView里面的数据根据ABCDEFG...来排序
        if (o1.getSortLetters().equals("@") || o2.getSortLetters().equals("#")) {
            return -1;
        } else if (o1.getSortLetters().equals("#") || o2.getSortLetters().equals("@")) {
            return 1;
        } else {
            return o1.getSortLetters().compareTo(o2.getSortLetters());
        }
    }
}
