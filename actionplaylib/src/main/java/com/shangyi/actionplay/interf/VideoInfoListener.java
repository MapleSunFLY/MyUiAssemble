package com.shangyi.actionplay.interf;

import android.support.annotation.Nullable;

import com.google.android.exoplayer2.ExoPlaybackException;

/**
 * packageName: design.demo.shangyi.com.myapplication.exo.listener
 * version:
 * author: liubi
 * created on: 2017/11/1 15:49
 * description：视频视频信息回调
 */

public interface VideoInfoListener {

    /***
     * 开始播放
     * **/
    void onPlayStart();

    /***
     * 播放是否加载中
     * **/
    void onLoadingChanged();

    /***
     * 播放失败
     * @param e  异常
     * **/
    void onPlayerError(@Nullable ExoPlaybackException e);

    /***
     * 播放结束
     * **/
    void onPlayEnd();

    /***
     *模式发生改变
     * @param  repeatMode  { int REPEAT_MODE_OFF = 0; int REPEAT_MODE_ONE = 1,REPEAT_MODE_ALL = 2}
     *  Normal playback without repetition REPEAT_MODE_OFF
     * "Repeat One" mode to repeat the currently playing window infinitely. REPEAT_MODE_ONE
     * "Repeat All" mode to repeat the entire timeline infinitely.REPEAT_MODE_ALL
     * ***/
    void onRepeatModeChanged(int repeatMode);

    /***
     *暂停还是播放
     * @param  playWhenReady  暂停还是播放
     * */
    void  isPlaying(boolean playWhenReady);
}