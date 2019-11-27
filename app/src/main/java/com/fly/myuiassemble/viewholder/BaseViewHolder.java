package com.fly.myuiassemble.viewholder;

import android.support.annotation.ColorRes;
import android.support.annotation.IdRes;
import android.view.View;

/**
 * 包    名 : com.shangyi.commonlib.holder
 * 作    者 : FLY
 * 创建时间 : 2018/1/11
 * 描述: viewholder的基类
 */

public class BaseViewHolder {

    protected View mItemView;

    public BaseViewHolder(View view) {
        mItemView = view;
    }

    /**
     * 给item设置背景
     *
     * @param color
     */
    public void setBackgroundColor(@ColorRes int color) {
        mItemView.setBackgroundResource(color);
    }

    /**
     * 设置背景，以vie的id
     *
     * @param color
     * @param id
     */
    public void setBackgroundColor(int color, @IdRes int id) {
        View view = mItemView.findViewById(id);
        if (view != null) {
            view.setBackgroundColor(color);
        }
    }

    /**
     * 获取itemview
     *
     * @return
     */
    public View getItemView() {
        return mItemView;
    }

    /**
     * 设置view的可见性
     *
     * @param visibility
     */
    public void setVisibility(boolean visibility) {
        if (mItemView != null) {
            mItemView.setVisibility(visibility ? View.VISIBLE : View.GONE);
        }
    }
}
