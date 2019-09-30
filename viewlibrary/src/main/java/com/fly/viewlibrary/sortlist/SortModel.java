package com.fly.viewlibrary.sortlist;

import android.text.TextUtils;

/**
 * <pre>
 *           .----.
 *        _.'__    `.
 *    .--(Q)(OK)---/$\
 *  .' @          /$$$\
 *  :         ,   $$$$$
 *   `-..__.-' _.-\$$/
 *         `;_:    `"'
 *       .'"""""`.
 *      /,  FLY  ,\
 *     //         \\
 *     `-._______.-'
 *     ___`. | .'___
 *    (______|______)
 * </pre>
 * 包    名 : com.fly.myview.progressbar
 * 作    者 : FLY
 * 创建时间 : 2018/9/6
 * 描述:右侧的sideBar,显示的是二十六个字母以及*，和#号，点击字母，自动导航到相应拼音的汉字上
 */
public abstract class SortModel {

    /**
     * 排序展示字段
     *
     * @return
     */
    public abstract String getExhibition();

    /**
     * 排序字段
     *
     * @return
     */
    public abstract String getSort();

    public String getSortLetters() {
        if (!TextUtils.isEmpty(getSort())) {
            return getSort();
        }
        if (!TextUtils.isEmpty(getExhibition())) {
            // 汉字转换成拼音
            String pinyin = CharacterParser.getInstance().getSelling(getExhibition());

            //提取英文的首字母，非英文字母用#代替。
            String sortString = pinyin.substring(0, 1).toUpperCase();
            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                return sortString;
            }
        }
        return "#";
    }
}
