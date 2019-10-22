package com.fly.myuiassemble.action;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.fly.myuiassemble.R;
import com.fly.myuiassemble.action.entity.VideoEntity;
import com.fly.myuiassemble.util.PermissionUtils;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.shangyi.actionplay.audio.PriorityAudioPlayer;
import com.shangyi.actionplay.exo.CustomExoPlayer;
import com.shangyi.actionplay.interf.VideoInfoListener;
import com.shangyi.actionplay.interf.VideoWindowListener;
import com.shangyi.actionplay.view.CustomVideoPlayerView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ActionActivity extends AppCompatActivity {

    CustomVideoPlayerView playerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action);
        playerView = findViewById(R.id.playerView);


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (PermissionUtils.requestStorage(this)) {
            VideoEntity entity = new VideoEntity();
            entity.videoPath = copyAssetAndWrite("152.MP4");
            entity.audioPath = copyAssetAndWrite("153.MP3");
            // 初始化播放器对象
            CustomExoPlayer player = new CustomExoPlayer(this, playerView);
            player.setUserController(true);
            player.setLooping(Integer.MAX_VALUE);
            player.setPlayDomain(entity);
            player.setVideoInfoListener(videoInfoListener);
            player.setOnWindowListener(windowListener);

            //播放视频
            player.onPlayNoAlertVideo();

            //播放音频
            PriorityAudioPlayer.getInstance().play(entity);
        }
    }

    private VideoInfoListener videoInfoListener = new VideoInfoListener() {
        @Override
        public void onPlayStart() {

        }

        @Override
        public void onLoadingChanged() {
        }

        @Override
        public void onPlayerError(@Nullable ExoPlaybackException e) {
            if (e != null)
                e.printStackTrace();
        }

        @Override
        public void onPlayEnd() {

        }

        @Override
        public void onRepeatModeChanged(int repeatMode) {
        }

        @Override
        public void isPlaying(boolean playWhenReady) {
        }
    };

    private VideoWindowListener windowListener = new VideoWindowListener() {
        @Override
        public void onCurrentIndex(int currentWindowIndex, int windowCount) {

        }

        @Override
        public void onStart(int currentWindowIndex, int windowCount) {
        }

        @Override
        public void onOnlyHaveOneLoop() {
        }
    };


    private String copyAssetAndWrite(String fileName) {
        try {
            File cacheDir = getCacheDir();
            if (!cacheDir.exists()) {
                cacheDir.mkdirs();
            }
            File outFile = new File(cacheDir, fileName);
            if (!outFile.exists()) {
                boolean res = outFile.createNewFile();
                if (!res) {
                    return "";
                }
            } else {
                if (outFile.length() > 10) {//表示已经写入一次
                    return outFile.getAbsolutePath();
                }
            }
            InputStream is = getAssets().open(fileName);
            FileOutputStream fos = new FileOutputStream(outFile);
            byte[] buffer = new byte[1024];
            int byteCount;
            while ((byteCount = is.read(buffer)) != -1) {
                fos.write(buffer, 0, byteCount);
            }
            fos.flush();
            is.close();
            fos.close();
            return outFile.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }
}
