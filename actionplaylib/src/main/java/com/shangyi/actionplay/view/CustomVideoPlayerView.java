package com.shangyi.actionplay.view;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.android.exoplayer2.SimpleExoPlayer;

import com.shangyi.android.actionplay.R;
import com.shangyi.actionplay.exo.CustomExoPlayer;
import com.shangyi.actionplay.interf.ExoPlayerListener;
import com.shangyi.actionplay.interf.ExoPlayerViewListener;


/**
 * packageName: design.demo.shangyi.com.myapplication.widget
 * version:
 * author: liubi
 * created on: 2017/11/10 11:30
 * description:
 */

public class CustomVideoPlayerView extends FrameLayout implements CustomPlaybackControlView.VisibilityListener {
    public static final String TAG = CustomVideoPlayerView.class.getName();

    /***活动窗口***/
    private Activity activity;

    /***水印,封面图占位,显示音频和亮度布图***/
    private ImageView exoPlayWatermark, exoPreviewImage;

    /***全屏按钮***/
    private ImageButton exoFullscreen;

    /***视频加载页,错误页,进度控件返回按钮***/
    private View exoControlsBack, exoPlayErrorLayout;

    /***播放view***/
    protected CustomSimpleExoPlayerView playerView;

    /***占位图是否在上面***/
    private boolean isPreViewTop;

    /***回调接口实现类***/
    private ComponentListener componentListener = new ComponentListener();
    private ExoPlayerListener mExoPlayerListener;

    public CustomVideoPlayerView(Context context) {
        super(context, null);
        activity = (Activity) context;
        initView();
    }

    public CustomVideoPlayerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomVideoPlayerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        activity = (Activity) context;
        int userWatermark = 0;
        int defaultArtworkId = 0;
        int back = R.layout.common_simple_exo_back_view;
        int errorId = 0;
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        playerView = new CustomSimpleExoPlayerView(getContext(), attrs);
        addView(playerView, params);
        if (attrs != null) {
            TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.VideoPlayerView, 0, 0);
            try {
                userWatermark = a.getResourceId(R.styleable.VideoPlayerView_user_watermark, 0);
                errorId = a.getResourceId(R.styleable.VideoPlayerView_player_error_layout_id, errorId);
                defaultArtworkId = a.getResourceId(R.styleable.VideoPlayerView_default_artwork, defaultArtworkId);
                if (errorId == 0) {
                    errorId = R.layout.common_simple_exo_play_error;
                }
            } finally {
                a.recycle();
            }
        }
        exoPlayErrorLayout = LayoutInflater.from(context).inflate(errorId, null);
        exoControlsBack = LayoutInflater.from(context).inflate(back, null);
        initView();
        if (userWatermark != 0) {
            exoPlayWatermark.setImageResource(userWatermark);
        }
        if (defaultArtworkId != 0) {
            setPreviewImage(BitmapFactory.decodeResource(getResources(), defaultArtworkId));
        } else {
            if (!isPreViewTop) {
                setPreviewImage(BitmapFactory.decodeResource(getResources(), R.mipmap.common_ic_video_back_normal));
            }
        }
    }

    private void initView() {
        FrameLayout frameLayout = playerView.getContentFrameLayout();
        frameLayout.setBackgroundColor(ContextCompat.getColor(activity, android.R.color.black));
        exoPlayErrorLayout.setVisibility(GONE);
        frameLayout.addView(exoPlayErrorLayout, frameLayout.getChildCount());
        exoPlayWatermark = (ImageView) playerView.findViewById(R.id.exo_player_watermark);
        exoFullscreen = (ImageButton) findViewById(R.id.exo_video_fullscreen);
        if (playerView.findViewById(R.id.exo_player_error_btn_id) != null) {
            playerView.findViewById(R.id.exo_player_error_btn_id).setOnClickListener(componentListener);
        }
        if (playerView.findViewById(R.id.exo_preview_image) != null) {
            isPreViewTop = true;
            exoPreviewImage = (ImageView) playerView.findViewById(R.id.exo_preview_image);
        } else {
            isPreViewTop = true;
            exoPreviewImage = (ImageView) playerView.findViewById(R.id.exo_preview_image_bottom);
        }
        exoControlsBack.setOnClickListener(componentListener);
        playerView.findViewById(R.id.exo_video_fullscreen).setOnClickListener(componentListener);
        if (!isLand(activity)) {
            exoControlsBack.setVisibility(GONE);
        }
        exoControlsBack.setVisibility(GONE);
        playerView.setControllerVisibilityListener(this);
    }

    /****
     * 获取控制类
     *
     * @return PlaybackControlView
     ***/
    @Nullable
    public CustomPlaybackControlView getPlaybackControlView() {
        if (playerView != null) {
            return playerView.getUseControllerView();
        }
        return null;
    }

    /**
     * 设置占位预览图
     *
     * @param previewImage 预览图
     **/
    public void setPreviewImage(Bitmap previewImage) {
        this.exoPreviewImage.setImageBitmap(previewImage);
    }

    /***
     * 显示隐藏返回键
     *
     * @param visibility 状态
     ***/
    private void showBackView(int visibility) {
        if (exoControlsBack != null) {
            if (!isLand(activity)) {
                exoControlsBack.setVisibility(GONE);
            } else {
                exoControlsBack.setVisibility(visibility);
            }
        }
    }

    /***
     * 显示隐藏全屏按钮
     *
     * @param visibility 状态
     ***/
    private void showFullscreenView(int visibility) {
        Log.d(TAG, "+showFullscreenView:" + visibility + "_:" + isPreViewTop);
        if (exoFullscreen == null) {
            return;
        }
        if (isPreViewTop) {
            if (getPreviewImage().getVisibility() != VISIBLE) {
                exoFullscreen.setVisibility(visibility);
            } else {
                exoFullscreen.setVisibility(INVISIBLE);
            }
        } else {
            exoFullscreen.setVisibility(visibility);
        }

    }

    /***
     * 获取预览图
     *
     * @return ImageView
     ***/
    @NonNull
    public ImageView getPreviewImage() {
        return exoPreviewImage;
    }

    /***
     * 显示隐藏错误页
     *
     * @param visibility 状态
     ***/
    private void showErrorState(int visibility) {
        if (visibility == View.VISIBLE) {
            playerView.hideController();
            showBackView(VISIBLE);
            playerView.setOnTouchListener(null);
        }
        if (exoPlayErrorLayout != null) {
            exoPlayErrorLayout.setVisibility(visibility);
        }
    }

    /**
     * 获取g播放控制类
     *
     * @return ExoUserPlayer
     **/
    @Nullable
    public CustomExoPlayer getPlay() {
        if (mExoPlayerListener != null) {
            return mExoPlayerListener.getPlay();
        }
        return null;
    }

    /***
     * 设置播放的状态回调
     *
     * @param mExoPlayerListener 回调
     ***/
    public void setExoPlayerListener(ExoPlayerListener mExoPlayerListener) {
        this.mExoPlayerListener = mExoPlayerListener;
    }

    /***
     * 获取监听事件
     *
     * @return ComponentListener
     ***/
    public ComponentListener getComponentListener() {
        return componentListener;
    }

    /**
     * 监听类
     **/
    private class ComponentListener implements ExoPlayerViewListener, OnClickListener {

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.exo_video_fullscreen) {
                //横屏
                if (getOrientation(activity) == Configuration.ORIENTATION_LANDSCAPE) {
                    activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    //竖屏
                } else if (getOrientation(activity) == Configuration.ORIENTATION_PORTRAIT) {
                    activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                }
            } else if (v.getId() == R.id.exo_controls_back) {
                mExoPlayerListener.onBack();
            }
        }

        @Override
        public void setWatermarkImage(int res) {
            if (exoPlayWatermark != null) {
                exoPlayWatermark.setImageResource(res);
            }
        }

        @Override
        public void showErrorStateView(int visibility) {
        }

        @Override
        public void setTitle(@NonNull String title) {
        }

        @Override
        public void onConfigurationChanged(int newConfig) {
        }

        @Override
        public void setVolumePosition(int mMaxVolume, int currIndex) {
        }

        @Override
        public void setBrightnessPosition(int mMaxVolume, int currIndex) {
        }

        @Override
        public void next() {
            if (playerView.getUseControllerView() != null) {
                playerView.getUseControllerView().next();
            }
        }

        @Override
        public void previous() {
            if (playerView.getUseControllerView() != null) {
                playerView.getUseControllerView().previous();
            }
        }

        @Override
        public void hideController() {
            if (playerView != null) {
                playerView.getUseControllerView().setVisibility(GONE);
                setControllerHideOnTouch(false);
            }
        }

        @Override
        public void setUserController(boolean isControl) {
            playerView.setUseController(isControl);
        }

        @Override
        public void showControllerView() {
            if (playerView != null) {
                playerView.showController();
                setControllerHideOnTouch(true);
            }
        }

        @Override
        public void setControllerHideOnTouch(boolean onTouch) {
            if (playerView != null) {
                playerView.setControllerHideOnTouch(onTouch);
            }
        }

        @Override
        public void showPreview(int visibility) {
            getPreviewImage().setVisibility(visibility);
            showFullscreenView(visibility);
        }

        @Override
        public void setPreviewImage(Bitmap bitmap) {
            CustomVideoPlayerView.this.setPreviewImage(bitmap);
        }

        @Override
        public void reset() {
            ((SurfaceView) playerView.getVideoSurfaceView()).setVisibility(GONE);
            ((SurfaceView) playerView.getVideoSurfaceView()).setVisibility(VISIBLE);
        }

        @Override
        public int getHeight() {
            return playerView.getHeight();
        }

        @Override
        public void setPlayer(@NonNull SimpleExoPlayer player) {
            if (null != playerView) {
                playerView.setPlayer(player);
            }
        }

        @Override
        public void exitFull() {
        }
    }

    /***
     * 获取当前手机状态
     *
     * @param activity 活动
     * @return int
     ***/
    public static int getOrientation(@NonNull Activity activity) {
        Resources resources = activity.getResources();
        if (resources == null || resources.getConfiguration() == null) {
            return Configuration.ORIENTATION_PORTRAIT;
        }
        return activity.getResources().getConfiguration().orientation;
    }


    /***
     * 获取当前手机横屏状态
     *
     * @param activity 活动
     * @return int
     ***/
    public static boolean isLand(@NonNull Context activity) {
        Resources resources = activity.getResources();
        return !(resources == null || resources.getConfiguration() == null) && resources.getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    /**
     * dp转px
     *
     * @param context 山下文
     * @param dpValue dp单位
     * @return int
     */
    public static int dip2px(@NonNull Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    @Override
    public void onVisibilityChange(int visibility) {
        if (activity == null) {
            return;
        }
        showBackView(View.GONE);
        showFullscreenView(visibility);
        if (visibility == View.GONE) {
            if (exoPreviewImage != null) {
                exoPreviewImage.setVisibility(GONE);
            }
        }
    }
}
