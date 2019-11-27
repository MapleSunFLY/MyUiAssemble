package com.fly.myuiassemble;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.fly.myuiassemble.viewholder.TransitionPageViewViewHolder;
import com.fly.viewlibrary.time.CustomCountDownTimer;
import com.fly.viewlibrary.time.DownTimer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TimeActivity extends AppCompatActivity {

    private Button btnStart;
    private Button btnPause;
    private Button btnResume;
    private Button btnCompleted;

    private TextView tvDownTimer;
    private TextView tvCountDownTimer;

    private CustomCountDownTimer mCountDownTimer;

    private DownTimer mDownTimer;

    private TransitionPageViewViewHolder transitionPageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);
        mCountDownTimer = new CustomCountDownTimer(this, findViewById(R.id.btnTime),
                R.string.start_login_verification_num_again_get, R.string.start_login_verification_num_resend_count,
                60000, 1000);


        mDownTimer = new DownTimer(60000, 1000);
        initView();
        mDownTimer.setDownTimerhListener(new DownTimer.OnDownTimerhListener() {
            @Override
            public void onTick(long millis, long totalTime) {
                tvDownTimer.setText(String.valueOf((totalTime - millis) / 1000L));
                tvCountDownTimer.setText(String.valueOf(millis / 1000L));
            }

            @Override
            public void onFinish() {

            }
        });

    }

    private void initView() {
        findViewById(R.id.btnTime).setOnClickListener(v -> {
           // mCountDownTimer.start();
        });
        findViewById(R.id.btnStart).setOnClickListener(v -> {
            mDownTimer.start();
        });
        findViewById(R.id.btnPause).setOnClickListener(v -> {
            mDownTimer.pause();
        });
        findViewById(R.id.btnResume).setOnClickListener(v -> {
            mDownTimer.resume();
        });
        findViewById(R.id.btnCompleted).setOnClickListener(v -> {
            mDownTimer.completed();
        });
        tvDownTimer = findViewById(R.id.tvDownTimer);
        tvCountDownTimer = findViewById(R.id.tvCountDownTimer);
        FrameLayout fl = findViewById(R.id.fl);
        transitionPageView = TransitionPageViewViewHolder.createView(fl);
        transitionPageView.start(5000,"下一个动作：aa");
    }
}
