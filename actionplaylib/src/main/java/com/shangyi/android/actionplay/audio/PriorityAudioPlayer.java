package com.shangyi.android.actionplay.audio;

import android.media.MediaPlayer;
import android.os.SystemClock;
import android.util.Log;

import com.shangyi.android.actionplay.entity.AudioResModel;
import com.shangyi.android.actionplay.interf.IAudioPlayer;
import com.shangyi.android.actionplay.interf.OnSimpleAudioListener;

import java.io.IOException;

/**
 * packageName: design.demo.shangyi.com.myapplication.widget
 * version:
 * author: liubi
 * created on: 2017/11/20 9:57
 * description: 优先级音频播放器
 */

public class PriorityAudioPlayer implements IAudioPlayer, MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener
        , MediaPlayer.OnPreparedListener {

    private static PriorityAudioPlayer instance;

    /**
     * 音频播放组件
     */
    private static MediaPlayer mediaPlayer;

    /**
     * 音频实体
     */
    private AudioResModel currentMainAudio;

    /**
     * 音频操作回调
     */
    private OnSimpleAudioListener listener;

    private PriorityAudioPlayer() {
        mediaPlayer = new MediaPlayer();
        //播放器播放完成监听
        mediaPlayer.setOnCompletionListener(this);
        //播放器准备完成监听
        mediaPlayer.setOnPreparedListener(this);
        //播放器错误监听
        mediaPlayer.setOnErrorListener(this);
    }

    public static PriorityAudioPlayer getInstance() {
        if (instance == null) {
            synchronized (PriorityAudioPlayer.class) {
                if (instance == null) {
                    instance = new PriorityAudioPlayer();
                }
            }
        }
        return instance;
    }

    /**
     * 开始播放 操作
     *
     * @param domain
     * @return
     */
    @Override
    public boolean play(AudioResModel domain) {
        Log.d("PriorityAudioPlayer", "playStart -- >" + SystemClock.uptimeMillis());
        if (domain == null) {
            if (listener != null) {
                listener.onError(null, 0, 0, "");
            }
            return false;
        }
        if (mediaPlayer.isPlaying() && currentMainAudio != null) {
            if (domain.getAudioPriority() > currentMainAudio.getAudioPriority()) {
                currentMainAudio = domain;
                try {
                    stop();
                    mediaPlayer.reset();
                    mediaPlayer.setDataSource(domain.getAudioPath());
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                    Log.d("MyCountDownTimer", "RealDuration -- >" + mediaPlayer.getDuration());
                } catch (IOException e) {
                    e.printStackTrace();
                    mediaPlayer.reset();
                }
            }
        } else {
            currentMainAudio = domain;
            try {
                stop();
                mediaPlayer.reset();
                mediaPlayer.setDataSource(domain.getAudioPath());
                mediaPlayer.prepare();
                mediaPlayer.start();
                Log.d("MyCountDownTimer", "RealDuration -- >" + mediaPlayer.getDuration());
            } catch (IOException e) {
                e.printStackTrace();
                mediaPlayer.reset();
            }
        }
        return true;
    }

    /**
     * 暂停播放 操作
     */
    @Override
    public void stop() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
    }

    /**
     * 中断播放 操作
     */
    @Override
    public void interrupt() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            if (listener != null) {
                listener.onInterrupt(currentMainAudio);
            }
        }
        mediaPlayer.reset();
        listener = null;
        currentMainAudio = null;
    }

    /**
     * 重置资源 操作
     */
    @Override
    public void reset() {
        stop();
        mediaPlayer.reset();
        currentMainAudio = null;
    }

    @Override
    public void release() {
        stop();
        mediaPlayer.release();
        listener = null;
        currentMainAudio = null;
    }

    /**
     * 音频监听 操作
     *
     * @param listener
     */
    public void setListener(OnSimpleAudioListener listener) {
        this.listener = listener;
    }

    /**
     * 播放器播放完成回调
     *
     * @param mp
     */

    @Override
    public void onCompletion(MediaPlayer mp) {
        Log.d("PriorityAudioPlayer", "playCompleted -- >" + SystemClock.uptimeMillis());
        String audioId = currentMainAudio.getAudioId();
        currentMainAudio = null;
        if (listener != null) {
            listener.onCompletion(mp, audioId);
        }
    }

    /**
     * 播放器准备完成回调
     *
     * @param mp
     */
    @Override
    public void onPrepared(MediaPlayer mp) {
        if (listener != null) {
            listener.onPrepared(mp, currentMainAudio);
        }
    }

    /**
     * 播放器错误回调
     *
     * @param mp
     * @param what
     * @param extra
     * @return
     */
    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        mediaPlayer.reset();
        String audioId = "";
        if (currentMainAudio != null) audioId = currentMainAudio.getAudioId();
        currentMainAudio = null;
        if (listener != null) {
            return listener.onError(mp, what, extra, audioId);
        }
        return false;
    }


    public static synchronized void dstroyInstance() {
        if (instance != null) instance = null;
    }
}
