package com.fly.myuiassemble;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.shangyi.android.actionplay.exo.CustomExoPlayer;
import com.shangyi.android.actionplay.view.CustomVideoPlayerView;

public class ActionActivity extends AppCompatActivity {

    CustomVideoPlayerView playerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action);
        playerView = findViewById(R.id.playerView);
        // 初始化播放器对象
        CustomExoPlayer player = new CustomExoPlayer(this, playerView);
        player.setUserController(false);
    }
}
