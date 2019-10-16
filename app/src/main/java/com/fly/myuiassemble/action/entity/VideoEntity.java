package com.fly.myuiassemble.action.entity;

import com.shangyi.android.actionplay.entity.AudioResModel;
import com.shangyi.android.actionplay.entity.VideoResModel;

/**
 * <pre>
 *           .----.
 *        _.'__    `.
 *    .--(Q)(OK)---/$\
 *  .' @          /$$$\
 *  :         ,   $$$$$
 *   `-..__.-' _.-\$/
 *         `;_:    `"'
 *       .'"""""`.
 *      /,  FLY  ,\
 *     //         \\
 *     `-._______.-'
 *     ___`. | .'___
 *    (______|______)
 * </pre>
 * 包    名 : com.shangyi.android.http.entity
 * 作    者 : FLY
 * 创建时间 : 2019/5/13
 * 描述: 视频音频实体类
 */
public class VideoEntity implements VideoResModel,AudioResModel{


    public String videoPath;
    public String audioPath;

    @Override
    public String getVideoUri() {
        return videoPath;
    }

    @Override
    public String getAudioId() {
        return "aa";
    }

    @Override
    public int getAudioPriority() {
        return 0;
    }

    @Override
    public String getAudioPath() {
        return audioPath;
    }
}
