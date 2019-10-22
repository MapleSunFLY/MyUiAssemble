package com.shangyi.actionplay.interf;

/**
 * packageName: design.demo.shangyi.com.myapplication.exo.listener
 * version:
 * author: liubi
 * created on: 2017/11/1 15:49
 * description:视频加载回调接口
 */

public interface LoadListener {
    /**
     * 进度
     * @param pro 进度值 0-100
     *
     * ***/
    void onProgress(long pro);
}
