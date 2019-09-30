package com.fly.viewlibrary.sortlist;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SectionIndexer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 包    名 : com.fly.viewlibrary.sortlist
 * 作    者 : FLY
 * 创建时间 : 2019/9/26
 * 描述: 用来处理集合中数据的显示与排序
 */
public abstract class SortAdapter extends BaseAdapter implements SectionIndexer {

    protected List<? extends SortModel> list = new ArrayList<SortModel>();
    protected Context mContext;
    protected PinyinComparator pinyinComparator;

    public SortAdapter(Context mContext) {
        this(mContext, null);
    }

    public SortAdapter(Context mContext, List<? extends SortModel> list) {
        this.mContext = mContext;
        pinyinComparator = new PinyinComparator();
        if (list != null && list.size() > 0) {
            Collections.sort(list, pinyinComparator);
            this.list = list;
        }
    }

    /**
     * 当ListView数据发生变化时,调用此方法来更新ListView
     *
     * @param list
     */
    public void setListDate(List<? extends SortModel> list) {
        if (list != null) {
            Collections.sort(list, pinyinComparator);
            this.list = list;
            notifyDataSetChanged();
        }
    }

    public List<? extends SortModel> getListDate() {
        return list;
    }

    public int getCount() {
        return this.list.size();
    }

    public Object getItem(int position) {
        return list.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    /**
     * 根据ListView的当前位置获取分类的首字母的Char ascii值
     */
    public int getSectionForPosition(int position) {
        return list.get(position).getSortLetters().charAt(0);
    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    public int getPositionForSection(int section) {
        for (int i = 0; i < getCount(); i++) {
            String sortStr = list.get(i).getSortLetters();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public Object[] getSections() {
        return null;
    }

    public void setWaveSideBar(WaveSideBar sideBar, final ListView sortListView) {
        // 设置右侧触摸监听
        sideBar.setOnTouchingLetterChangedListener(new WaveSideBar.OnTouchingLetterChangedListener() {

            @SuppressLint("NewApi")
            @Override
            public void onTouchingLetterChanged(String s) {
                // 该字母首次出现的位置
                int position = getPositionForSection(s.charAt(0));
                if (position != -1) {
                    sortListView.setSelection(position);
                }
            }
        });
    }
}

