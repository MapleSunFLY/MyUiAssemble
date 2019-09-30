package com.fly.myuiassemble.addressbook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fly.myuiassemble.R;
import com.fly.viewlibrary.sortlist.SortAdapter;
import com.fly.viewlibrary.text.ImageTextView;

import java.util.List;

/**
 * 包    名 : com.fly.myuiassemble.adapter
 * 作    者 : FLY
 * 创建时间 : 2019/9/26
 * 描述:字母排序Adapter
 */

public class MySortAdapter extends SortAdapter {

    public MySortAdapter(Context mContext) {
        super(mContext);
    }

    public MySortAdapter(Context mContext, List<SortEntity> list) {
        super(mContext, list);
    }

    public View getView(final int position, View view, ViewGroup arg2) {
        ViewHolder viewHolder = null;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.activity_wave_side_phone_constacts_item, null);
            viewHolder.tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            viewHolder.tvLetter = (TextView) view.findViewById(R.id.tvCatalog);
            viewHolder.icon = (ImageTextView) view.findViewById(R.id.icon);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        //根据position获取分类的首字母的Char ascii值
        int section = getSectionForPosition(position);

        SortEntity sortEntity = (SortEntity) list.get(position);
        //如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
        if (position == getPositionForSection(section)) {
            viewHolder.tvLetter.setVisibility(View.VISIBLE);
            viewHolder.tvLetter.setText(sortEntity.getSortLetters());
        } else {
            viewHolder.tvLetter.setVisibility(View.GONE);
        }

        viewHolder.tvTitle.setText(sortEntity.getName());
        viewHolder.icon.setText(sortEntity.getName());
        viewHolder.icon.setIconText(mContext, sortEntity.getName());
        return view;
    }

    final static class ViewHolder {
        TextView tvLetter;
        TextView tvTitle;
        ImageTextView icon;
    }
}
