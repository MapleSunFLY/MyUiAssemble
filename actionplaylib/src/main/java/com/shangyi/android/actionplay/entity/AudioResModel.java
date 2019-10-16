package com.shangyi.android.actionplay.entity;

/**
 * 包    名 : com.shangyi.android.actionplay.entity
 * 作    者 : FLY
 * 创建时间 : 2019/8/20
 * 描述: 音频资源
 */
public interface AudioResModel {

    /**
     * 返回音频ID
     *
     * @return String
     */
    String getAudioId();

    /**
     * 返回音频优先级 数值越大优先级越高
     *
     * @return int
     */
    int getAudioPriority();

    /**
     * 返回音频路径
     *
     * @return String
     */
    String getAudioPath();
}
