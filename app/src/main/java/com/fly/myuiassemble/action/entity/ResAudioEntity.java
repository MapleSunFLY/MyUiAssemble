package com.fly.myuiassemble.action.entity;

import com.shangyi.android.actionplay.entity.AudioResModel;

/**
 * 包    名 : com.fly.myuiassemble.action.entity
 * 作    者 : FLY
 * 创建时间 : 2019/10/15
 * 描述:
 */
public class ResAudioEntity implements AudioResModel {

    public String id;
    public String audioPath;
    //优先级
    public int priority;

    @Override
    public String getAudioId() {
        return id;
    }

    @Override
    public int getAudioPriority() {
        return priority;
    }

    @Override
    public String getAudioPath() {
        return audioPath;
    }

}
