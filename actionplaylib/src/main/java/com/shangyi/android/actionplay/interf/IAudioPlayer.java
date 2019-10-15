package com.shangyi.android.actionplay.interf;

import com.shangyi.android.actionplay.entity.AudioResModel;

/**
 * packageName: design.demo.shangyi.com.myapplication.exo.controller
 * version:
 * author: liubi
 * created on: 2017/11/9 11:52
 * description:音频播放器接口
 */

public interface IAudioPlayer {
    /**
     * 播放音频
     *
     * @param audioResModel 音频资源
     * @return 是否播放音频
     */
    boolean play(AudioResModel audioResModel);

    /**
     * 暂停播放
     */
    void stop();

    /**
     * 中断播放
     */
    void interrupt();

    /**
     * 重置资源
     */
    void reset();

    /**
     * 释放播放器以及资源,释放之后播放器将不可用
     */
    void release();
}
