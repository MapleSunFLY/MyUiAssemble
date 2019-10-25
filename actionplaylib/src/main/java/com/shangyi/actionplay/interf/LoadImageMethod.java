package com.shangyi.actionplay.interf;

import android.content.Context;
import android.widget.ImageView;

/**
 * 包    名 : com.shangyi.actionplay.interf
 * 作    者 : FLY
 * 创建时间 : 2019/8/21
 * 描述: 加载图片操作接口
 */
public interface LoadImageMethod {

    /**
     * 加载图片方法
     *
     * @param context   上下文
     * @param imageUrl  图片地址
     * @param imageView
     */
    void loadImage(Context context, String imageUrl, ImageView imageView);
}
