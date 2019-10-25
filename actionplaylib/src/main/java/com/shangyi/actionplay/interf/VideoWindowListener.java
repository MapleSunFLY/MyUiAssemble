package com.shangyi.actionplay.interf;

/**
 * packageName: design.demo.shangyi.com.myapplication.exo.listener
 * version:
 * author: liubi
 * created on: 2017/11/1 15:49
 * description:多个视频接口
 */

public interface VideoWindowListener {
    void onCurrentIndex(int currentWindowIndex, int windowCount);

    void onStart(int currentWindowIndex, int windowCount);

    void onOnlyHaveOneLoop();
}
