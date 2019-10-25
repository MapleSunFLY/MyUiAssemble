package com.shangyi.actionplay.interf;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.google.android.exoplayer2.SimpleExoPlayer;

/**
 *
 * @author yangc
 * date 2017/7/21
 * E-Mail:yangchaojiang@outlook.com
 * Deprecated: 控制类回调view 接口
 */

public interface ExoPlayerViewListener {

    /***
     * 设置水印图片
     *
     * @param res 资源id
     */
    void setWatermarkImage(int res);

    /***
     * 显示隐藏错误布局
     *
     * @param visibility 显示类型
     */
    void showErrorStateView(int visibility);

    /**
     * 显示标题
     *
     * @param title 标题内容
     **/
    void setTitle(@NonNull String title);

    /***
     * 手机很横竖屏切换
     *
     * @param newConfig 切换类型
     ***/
    void onConfigurationChanged(int newConfig);

    /**
     * 改变声音
     *
     * @param mMaxVolume d最大音量
     * @param currIndex  当前音量
     **/
    void setVolumePosition(int mMaxVolume, int currIndex);

    /**
     * 改变亮度
     *
     * @param mMaxVolume d最大音量
     * @param currIndex  当前音量
     **/
    void setBrightnessPosition(int mMaxVolume, int currIndex);

    /**
     * 下一步
     **/
    void next();

    /**
     * 上一步
     **/
    void previous();

    /***
     * 隐藏控制布局操作不会显示
     * **/
    void hideController();

    /***
     * 不使用控制布局
     * **/
    void setUserController(boolean isControl);

    /***
     * 显示控制布局操作
     * **/
    void showControllerView();

    /***
     * 控制布局操作
     * @param  onTouch 启用控制布局点击事件 true 启用 反则  false;
     * **/
    void setControllerHideOnTouch(boolean onTouch);

    /***
     * 显示封面
     * @param  visibility  类型
     * **/
    void showPreview(int visibility);

    /***
     * 设置封面
     * @param  bitmap
     * **/
    void setPreviewImage(Bitmap bitmap);

    /***
     * 重置布局
     * **/
    void reset();

    /***
     * 获取view 高度
     * @return int
     * **/
    int getHeight();

    /***
     * 设置播放播放控制类
     * @param  player  实例
     * ***/
    void setPlayer(@NonNull SimpleExoPlayer player);

    void exitFull();

}