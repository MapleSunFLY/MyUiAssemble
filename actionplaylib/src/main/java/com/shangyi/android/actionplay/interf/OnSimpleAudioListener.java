package com.shangyi.android.actionplay.interf;


import android.media.MediaPlayer;

import com.shangyi.android.actionplay.entity.AudioResModel;


/**
 * <pre>
 *           .----.
 *        _.'__    `.
 *    .--(Q)(OK)---/$\
 *  .' @          /$$$\
 *  :         ,   $$$$$
 *   `-..__.-' _.-\$$/
 *         `;_:    `"'
 *       .'"""""`.
 *      /,  FLY  ,\
 *     //         \\
 *     `-._______.-'
 *     ___`. | .'___
 *    (______|______)
 * </pre>
 * 包    名 : com.shangyi.android.actionplay.interf
 * 作    者 : FLY
 * 创建时间 : 2018/9/6
 * 描述: 音频播放接口
 */

public interface OnSimpleAudioListener {

    /**
     * 播放完成
     *
     * @param mp
     * @param audioId
     */
    void onCompletion(MediaPlayer mp, String audioId);

    /**
     * 音频中断
     *
     * @param audio
     */
    void onInterrupt(AudioResModel audio);

    /**
     * 播放异常
     *
     * @param mp
     * @param what
     * @param extra
     * @param audioId
     * @return
     */
    boolean onError(MediaPlayer mp, int what, int extra, String audioId);

    /**
     * 准备好
     * @param mp
     * @param audio
     */
    void onPrepared(MediaPlayer mp, AudioResModel audio);
}
