package com.shangyi.actionplay.exo;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.CallSuper;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.Size;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.RenderersFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import com.shangyi.actionplay.entity.VideoResModel;
import com.shangyi.actionplay.interf.DataSourceListener;
import com.shangyi.actionplay.interf.ExoPlayerListener;
import com.shangyi.actionplay.interf.ExoPlayerViewListener;
import com.shangyi.actionplay.interf.LoadImageMethod;
import com.shangyi.actionplay.interf.LoadListener;
import com.shangyi.actionplay.interf.VideoInfoListener;
import com.shangyi.actionplay.interf.VideoWindowListener;
import com.shangyi.actionplay.view.CustomVideoPlayerView;

import java.util.List;


/**
 * packageName: design.demo.shangyi.com.myapplication
 * version:
 * author: liubi
 * created on: 2017/11/1 14:39
 * description:
 */

public class CustomExoPlayer {
    private static final String TAG = CustomExoPlayer.class.getName();

    /***当前活动**/
    private final Activity activity;

    /***数据源管理类**/
    private MediaSourceBuilder mediaSourceBuilder;

    /***内核播放器***/
    private SimpleExoPlayer player;

    /*** 多个视频接口***/
    private VideoWindowListener windowListener;

    /*** 视频回调信息接口 ***/
    private VideoInfoListener videoInfoListener;

    /***  视频状态回调接口 ***/
    protected ComponentListener componentListener;

    /*** 播放view实例***/
    private final CustomVideoPlayerView playerView;

    /*** view交互回调接口 ***/
    private PlayComponentListener playComponentListener;

    /*** 是否循环播放  0 不开启***/
    private int loopingCount = 0;

    /*** 设置播放参数***/
    private PlaybackParameters playbackParameters;

    /*** 获取当前进度 ***/
    private long resumePosition;

    /*** 获取当前视频窗口位置 ***/
    private int resumeWindow;

    /*** 屏蔽进度缩索引 ***/
    private int indexType = -1;

    /*** 是否手动暂停 ***/
    private Boolean handPause = false;

    /*** 播放view交互接口 ***/
    private ExoPlayerViewListener mPlayerViewListener;

    /**
     * 图片加载方法
     */
    private LoadImageMethod loadImageMethod;

    private boolean isFirst = true;

    /****
     * 初始化
     * @param activity   活动对象
     * @param playerView 播放控件
     **/
    public CustomExoPlayer(@NonNull Activity activity, @NonNull CustomVideoPlayerView playerView) {
        this(activity, playerView, null);
    }

    /****
     * @param activity 活动对象
     * @param reId     播放控件id
     **/
    public CustomExoPlayer(@NonNull Activity activity, @IdRes int reId) {
        this(activity, reId, null);
    }

    /****
     * 初始化
     * @param activity 活动对象
     * @param reId     播放控件id
     * @param listener 自定义数据源类
     **/
    public CustomExoPlayer(@NonNull Activity activity, @IdRes int reId, @Nullable DataSourceListener listener) {
        this(activity, (CustomVideoPlayerView) activity.findViewById(reId), listener);
    }

    /****
     * 初始化
     * @param activity   活动对象
     * @param playerView 播放控件
     * @param listener   自定义数据源类
     **/
    public CustomExoPlayer(@NonNull Activity activity, @NonNull CustomVideoPlayerView playerView, @Nullable DataSourceListener listener) {
        this.activity = activity;
        mediaSourceBuilder = new MediaSourceBuilder(listener);
        this.playerView = playerView;
        componentListener = new ComponentListener();
        playComponentListener = new PlayComponentListener();
        playerView.setExoPlayerListener(playComponentListener);
        getPlayerViewListener();
    }

    /***
     * 获取交互view接口实例
     * **/
    @NonNull
    protected ExoPlayerViewListener getPlayerViewListener() {
        if (mPlayerViewListener == null) {
            mPlayerViewListener = playerView.getComponentListener();
        }
        return mPlayerViewListener;
    }

    /***
     * 页面恢复处理
     **/
    @CallSuper
    public void onResume() {
        if ((Util.SDK_INT <= Build.VERSION_CODES.M || player == null)) {
            createPlayers();
        }
    }

    /***
     * 页面暂停处理
     **/
    @CallSuper
    public void onPause() {

    }

    /**
     * 页面销毁处理
     **/
    @CallSuper
    public void onDestroy() {
        releasePlayers();
    }

    /****
     * 创建
     **/
    void createPlayers() {
        if (player == null) {
            player = createFullPlayer();
        }
        playVideo();
    }

    /***
     * 播放视频
     **/
    private void playVideo() {
        onPlayNoAlertVideo();
    }

    /***
     * 播放视频
     **/

    public void onPlayNoAlertVideo() {

        if (player == null) {
            createPlayersPlay();
        }
        if (mPlayerViewListener != null) {
            mPlayerViewListener.showPreview(View.GONE);
        }
        boolean haveResumePosition = resumeWindow != C.INDEX_UNSET;
        if (haveResumePosition) {
            player.seekTo(resumeWindow, resumePosition);
        }
        if (handPause) {
            player.setPlayWhenReady(false);
        } else {
            player.setPlayWhenReady(true);
        }

        if (loopingCount == 0) {
            player.prepare(mediaSourceBuilder.getMediaSource(), !haveResumePosition, false);
        } else {
            player.prepare(mediaSourceBuilder.setLooping(loopingCount), !haveResumePosition, false);
        }
        player.addListener(componentListener);
    }

    /****
     * 创建
     **/
    void createPlayersPlay() {
        player = createFullPlayer();
    }

    private SimpleExoPlayer createFullPlayer() {
        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(new DefaultBandwidthMeter());
        TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
        LoadControl loadControl = new DefaultLoadControl();
        RenderersFactory renderersFactory = new DefaultRenderersFactory(activity);
        SimpleExoPlayer player = ExoPlayerFactory.newSimpleInstance(renderersFactory, trackSelector, loadControl);
        if (playbackParameters != null) {
            player.setPlaybackParameters(playbackParameters);
        }
        getPlayerViewListener().setPlayer(player);
        return player;
    }

    /***
     * 释放资源
     **/
    public void releasePlayers() {
        if (player != null) {
            updateResumePosition();
            player.stop();
            player.release();
            player.removeListener(componentListener);
            player = null;
        }
        if (mediaSourceBuilder != null) {
            mediaSourceBuilder.release();
        }
        if (activity.isFinishing()) {
            mediaSourceBuilder = null;
            componentListener = null;
            videoInfoListener = null;
        }
    }

    /**
     * 重置播放器
     */
    public void reset() {
        if (player != null) {
            clearResumePosition();
            player.stop();
            getPlayerViewListener().reset();
        }
        if (mediaSourceBuilder != null) {
            mediaSourceBuilder.release();
        }
        videoInfoListener = null;
        windowListener = null;
        isFirst = true;
    }

    /****
     * 重置进度
     **/
    void updateResumePosition() {
        if (player != null) {
            resumeWindow = player.getCurrentWindowIndex();
            resumePosition = player.isCurrentWindowSeekable() ? Math.max(0, player.getCurrentPosition())
                    : C.TIME_UNSET;
        }
    }

    /**
     * 清除进度
     ***/
    private void clearResumePosition() {
        resumeWindow = C.INDEX_UNSET;
        resumePosition = C.TIME_UNSET;
    }

    /***
     * 设置进度
     *
     * @param resumePosition 毫秒
     **/
    public void setPosition(long resumePosition) {
        this.resumePosition = resumePosition;
    }

    /***
     * 设置进度
     *@param  currWindowIndex  视频索引
     * @param currPosition 毫秒
     **/
    public void setPosition(int currWindowIndex, long currPosition) {
        this.resumeWindow = currWindowIndex;
        this.resumePosition = currPosition;
    }

    /***
     * 下跳转下一个视频
     **/
    public void next() {
        getPlayerViewListener().next();
    }

    /***
     * 隐藏控制布局
     * ***/
    public void hideControllerView() {
        getPlayerViewListener().hideController();
    }

    /***
     * 隐藏控制布局
     * ***/
    public CustomExoPlayer setUserController(boolean isControl) {
        getPlayerViewListener().setUserController(isControl);
        return this;
    }

    /***
     * 设置循环播放视频   Integer.MAX_VALUE 无线循环
     *
     * @param loopingCount  必须大于0
     **/
    public CustomExoPlayer setLooping(@Size(min = 1) int loopingCount) {
        this.loopingCount = loopingCount;
        return this;
    }

    public void showControllerView() {
        Assertions.checkArgument(getPlayerViewListener() != null);
        getPlayerViewListener().showControllerView();
    }

    public void setPreviewImage(Bitmap bitmap) {
        getPlayerViewListener().setPreviewImage(bitmap);
    }

    public void setPreviewImage(Context context, String url) {
        if (loadImageMethod != null) {
            loadImageMethod.loadImage(context, url, playerView.getPreviewImage());
        }
    }

    public CustomExoPlayer setLoadImageMethod(LoadImageMethod loadImageMethod) {
        this.loadImageMethod = loadImageMethod;
        return this;
    }

    public void showPreview(int visibility) {
        getPlayerViewListener().showPreview(visibility);
    }

    /***
     * 是否播放中
     * @return boolean
     * ***/
    public boolean isPlaying() {
        Assertions.checkArgument(player != null);
        return player.getPlayWhenReady();
    }

    /**
     * 设置播放路径
     *
     * @param uri 路径
     ***/
    public CustomExoPlayer setPlayUri(@NonNull String uri) {
        setPlayUri(Uri.parse(uri));
        return this;
    }

    /****
     * @param  indexType 设置当前索引视频屏蔽进度
     * @param firstVideoUri  预览的视频
     * @param secondVideoUri 第二个视频
     **/
    public CustomExoPlayer setPlayUri(@Size(min = 0) int indexType, @NonNull String firstVideoUri, @NonNull String secondVideoUri) {
        setPlayUri(indexType, Uri.parse(firstVideoUri), Uri.parse(secondVideoUri));
        return this;
    }

    /****
     * @param firstVideoUri  预览的视频
     * @param secondVideoUri 第二个视频
     **/
    public CustomExoPlayer setPlayUri(@NonNull Uri firstVideoUri, @NonNull Uri secondVideoUri) {
        setPlayUri(0, firstVideoUri, secondVideoUri);
        return this;
    }

    /****
     * @param  indexType  设置当前索引视频屏蔽进度
     * @param firstVideoUri  预览的视频
     * @param secondVideoUri 第二个视频

     **/
    public void setPlayUri(@Size(min = 0) int indexType, @NonNull Uri firstVideoUri, @NonNull Uri secondVideoUri) {
        if (mediaSourceBuilder != null) {
            mediaSourceBuilder.release();
        }
        this.indexType = indexType;
        mediaSourceBuilder.setMediaSourceUri(activity.getApplicationContext(), firstVideoUri, secondVideoUri);
        createPlayers();
    }

    /****
     * 设置视频列表播放
     * @param uris  视频列表集合
     **/
    public void setPlayUri(@NonNull Uri... uris) {
        if (mediaSourceBuilder != null) {
            mediaSourceBuilder.release();
        }
        mediaSourceBuilder.setMediaSourceUri(activity.getApplicationContext(), uris);
        createPlayers();
    }

    public <T extends VideoResModel> void setPlayUri(@NonNull List<T> uris) {
        if (mediaSourceBuilder != null) {
            mediaSourceBuilder.release();
        }
        mediaSourceBuilder.setMediaSourceUri(activity.getApplicationContext(), uris);
        createPlayers();
    }

    public <T extends VideoResModel> void setPlayDomain(@NonNull List<T> uris) {
        if (mediaSourceBuilder != null) {
            mediaSourceBuilder.release();
        }
        mediaSourceBuilder.setMediaSourceItem(activity.getApplicationContext(), uris);
        createPlayers();
    }

    public <T extends VideoResModel> void setPlayDomain(@NonNull T item) {
        if (mediaSourceBuilder != null) {
            mediaSourceBuilder.release();
        }
        mediaSourceBuilder.setMediaSourceItem(activity.getApplicationContext(), item);
        createPlayers();
    }

    /**
     * 设置播放路径
     *
     * @param uri 路径
     ***/
    public void setPlayUri(@NonNull Uri uri) {
        if (mediaSourceBuilder != null) {
            mediaSourceBuilder.release();
        }
        mediaSourceBuilder.setMediaSourceUri(activity.getApplicationContext(), uri);
        createPlayers();
    }

    /***
     * 设置倍数播放创建新的回放参数
     *
     * @param speed 播放速度加快   1f 是正常播放 小于1 慢放
     * @param pitch  音高被放大  1f 是正常播放 小于1 慢放
     */
    public void setPlaybackParameters(@Size(min = 0) float speed, @Size(min = 0) float pitch) {
        playbackParameters = new PlaybackParameters(speed, pitch);
    }

    /****
     * 横竖屏切换
     *
     * @param configuration 旋转
     ***/
    public void onConfigurationChanged(Configuration configuration) {
        getPlayerViewListener().onConfigurationChanged(configuration.orientation);
    }


    /***
     * 显示水印图
     *
     * @param res 资源
     ***/
    public void setExoPlayWatermarkImg(int res) {
        getPlayerViewListener().setWatermarkImage(res);
    }

    /***
     * 设置标题
     *
     * @param title 名字
     ***/
    public void setTitle(@NonNull String title) {
        getPlayerViewListener().setTitle(title);
    }


    /***
     * 设置播放或暂停
     * @param value  true 播放  false  暂停
     * **/
    public void setStartOrPause(boolean value) {
        if (player != null) {
            player.setPlayWhenReady(value);
        }
    }

    /***
     * 获取内核播放实例
     * @return SimpleExoPlayer
     * ****/
    public SimpleExoPlayer getPlayer() {
        return player;
    }

    /**
     * 返回视频总进度  以毫秒为单位
     *
     * @return long
     **/
    public long getDuration() {
        return player == null ? 0 : player.getDuration();
    }

    /**
     * 跳过到
     *
     * @return long
     **/
    public void seekTo(long mil) {
        player.seekTo(mil);
    }

    public void seekTo(int windowIndex, long positionMs) {
        player.seekTo(windowIndex, positionMs);
    }

    public int getCurrentWindowIndex() {
        return player.getCurrentWindowIndex();
    }

    public long getContentPosition() {
        return player.getContentPosition();
    }

    /**
     * 返回视频当前播放进度  以毫秒为单位
     *
     * @return long
     **/
    public long getCurrentPosition() {
        return player == null ? 0 : player.getCurrentPosition();
    }

    /**
     * 返回视频当前播放d缓冲进度  以毫秒为单位
     *
     * @return long
     **/
    public long getBufferedPosition() {
        return player == null ? 0 : player.getBufferedPosition();
    }


    /***
     * 设置视频信息回调
     * @param videoInfoListener 实例
     * ***/
    public CustomExoPlayer setVideoInfoListener(VideoInfoListener videoInfoListener) {
        this.videoInfoListener = videoInfoListener;
        return this;
    }

    /**
     * 设置多个视频状态回调
     *
     * @param windowListener 实例
     */
    public CustomExoPlayer setOnWindowListener(VideoWindowListener windowListener) {
        this.windowListener = windowListener;
        return this;
    }

    /***
     * 返回视频总数
     * @return int
     **/
    public int getWindowCount() {
        if (player == null) {
            return 0;
        }
        return player.getCurrentTimeline().isEmpty() ? 1 : player.getCurrentTimeline().getWindowCount();
    }

    /**
     * view 给控制类 回调类
     */
    private class ComponentListener implements Player.EventListener, LoadListener {

        @Override
        public void onTimelineChanged(Timeline timeline, Object o, int i) {

        }

        @Override
        public void onShuffleModeEnabledChanged(boolean b) {

        }

        @Override
        public void onPositionDiscontinuity(int i) {
            Log.d(TAG, "onPositionDiscontinuity:" + player.getCurrentWindowIndex() + "_:" + player.getCurrentTimeline().getWindowCount());
            if (getWindowCount() > 1) {
                boolean is = windowListener != null && newIndex != player.getCurrentWindowIndex();
                if (is) {
                    newIndex = player.getCurrentWindowIndex();
                    windowListener.onCurrentIndex(player.getCurrentWindowIndex(), getWindowCount());
                }
            }
        }

        @Override
        public void onSeekProcessed() {

        }

        /***
         * 视频播放播放
         **/
        @Override
        public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
            Log.d(TAG, "onTracksChanged:" + player.getCurrentWindowIndex() + "_:" + player.getCurrentTimeline().getWindowCount());
            if (getWindowCount() > 1) {
                if (windowListener != null && isFirst) {
                    isFirst = false;
                    windowListener.onCurrentIndex(player.getCurrentWindowIndex(), getWindowCount());
                }
            } else if (getWindowCount() == 1) {
                if (windowListener != null && isFirst) {
                    isFirst = false;
                    windowListener.onOnlyHaveOneLoop();
                }
            }
        }

        private int newIndex;

        /*****
         * 进度条控制 加载页
         *********/
        @Override
        public void onLoadingChanged(boolean isLoading) {

        }

        /**
         * 视频的播放状态
         * STATE_IDLE 播放器空闲，既不在准备也不在播放
         * STATE_PREPARING 播放器正在准备
         * STATE_BUFFERING 播放器已经准备完毕，但无法立即播放。此状态的原因有很多，但常见的是播放器需要缓冲更多数据才能开始播放
         * STATE_ENDED 播放已完毕
         */
        @Override
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
            if (playWhenReady) {
                //防锁屏
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            } else {
                //解锁屏
                activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            }
            if (videoInfoListener != null) {
                videoInfoListener.isPlaying(player.getPlayWhenReady());
            }
            Log.d(TAG, "onPlayerStateChanged:" + playbackState + "+playWhenReady:" + playWhenReady);
            switch (playbackState) {
                case Player.STATE_BUFFERING:
                    Log.d(TAG, "onPlayerStateChanged:加载中。。。");
                    if (videoInfoListener != null) {
                        videoInfoListener.onLoadingChanged();
                    }
                    break;
                case Player.STATE_ENDED:
                    Log.d(TAG, "onPlayerStateChanged:ended。。。");
                    newIndex = 0;
                    if (videoInfoListener != null) {
                        videoInfoListener.onPlayEnd();
                    }
                    break;
                case Player.STATE_IDLE:
                    Log.d(TAG, "onPlayerStateChanged::网络状态差，请检查网络。。。");
                    break;
                case Player.STATE_READY:
                    Log.d(TAG, "onPlayerStateChanged:ready。。。");
                    if (videoInfoListener != null) {
                        videoInfoListener.onPlayStart();
                    }
                    break;
                default:
                    break;
            }
        }

        @Override
        public void onRepeatModeChanged(int repeatMode) {
            if (videoInfoListener != null) {
                videoInfoListener.onRepeatModeChanged(repeatMode);
            }
        }

        @Override
        public void onPlayerError(ExoPlaybackException e) {
            Log.e(TAG, "onPlayerError:" + e.getMessage());
            if (videoInfoListener != null) {
                videoInfoListener.onPlayerError(e);
            }
        }

        @Override
        public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
        }

        @Override
        public void onProgress(long pro) {
        }
    }


    /****
     * 播放回调view事件处理
     * ***/
    private class PlayComponentListener implements ExoPlayerListener {
        @Override
        public void onCreatePlayers() {
            createPlayers();
        }

        @Override
        public void onClearPosition() {
            clearResumePosition();
        }

        @Override
        public void replayPlayers() {
            clearResumePosition();
            onPlayNoAlertVideo();
        }

        @Override
        public void playVideoUri() {
            onPlayNoAlertVideo();

        }

        @Override
        public CustomExoPlayer getPlay() {
            return CustomExoPlayer.this;
        }


        @Override
        public void onBack() {
            activity.onBackPressed();
        }
    }

}
