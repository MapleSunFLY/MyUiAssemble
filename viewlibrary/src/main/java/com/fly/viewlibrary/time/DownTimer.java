package com.fly.viewlibrary.time;

import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;

/**
 * 包    名 : com.fly.viewlibrary.time
 * 作    者 : FLY
 * 创建时间 : 2019/10/30
 * 描述: 定时器
 */
public class DownTimer {

    private static final int MSG = 1;

    /**
     * Millis since epoch when alarm should stop.
     */
    private final long mMillisInFuture;

    /**
     * The interval in millis that the user receives callbacks
     */
    private final long mCountdownInterval;

    private long mStopTimeInFuture;

    private long mThisTime;

    /**
     * boolean representing if the timer was cancelled
     */
    private boolean mCancelled = false;

    private OnDownTimerhListener listener;

    /**
     * @param millisInFuture    The number of millis in the future from the call
     *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
     *                          is called.
     * @param countDownInterval The interval along the way to receive
     *                          {@link #onTick(long)} callbacks.
     */
    public DownTimer(long millisInFuture, long countDownInterval) {
        mMillisInFuture = millisInFuture;
        mCountdownInterval = countDownInterval;
    }

    /**
     * Completed the countdown.
     */
    public synchronized final void completed() {
        mCancelled = true;
        mHandler.removeMessages(MSG);
        onFinish();
    }

    /**
     * Pause the countdown.
     */
    public synchronized final void pause() {
        mCancelled = true;
        mHandler.removeMessages(MSG);
    }

    /**
     * Start the countdown.
     */
    public synchronized final DownTimer start() {
        mCancelled = false;
        if (mMillisInFuture <= 0) {
            onFinish();
            return this;
        }
        mStopTimeInFuture = SystemClock.elapsedRealtime() + mMillisInFuture;
        mHandler.sendMessage(mHandler.obtainMessage(MSG));
        return this;
    }

    /**
     * Resume the countdown.
     */
    public synchronized final DownTimer resume() {
        mCancelled = false;
        if (mMillisInFuture <= 0) {
            onFinish();
            return this;
        }
        mStopTimeInFuture = SystemClock.elapsedRealtime() + mThisTime;
        mHandler.sendMessage(mHandler.obtainMessage(MSG));
        return this;
    }

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            synchronized (DownTimer.this) {
                if (mCancelled) {
                    return;
                }

                final long millisLeft = mStopTimeInFuture - SystemClock.elapsedRealtime();

                if (millisLeft <= 0) {
                    onFinish();
                } else {
                    long lastTickStart = SystemClock.elapsedRealtime();
                    onTick(millisLeft);
                    long lastTickDuration = SystemClock.elapsedRealtime() - lastTickStart;
                    long delay;
                    if (millisLeft < mCountdownInterval) {
                        delay = millisLeft - lastTickDuration;
                        if (delay < 0) delay = 0;
                    } else {
                        delay = mCountdownInterval - lastTickDuration;
                        while (delay < 0) delay += mCountdownInterval;
                    }

                    sendMessageDelayed(obtainMessage(MSG), delay);
                }
            }
        }
    };


    public void onTick(long millisUntilFinished) {
        mThisTime = millisUntilFinished;
        if (listener != null) listener.onTick(millisUntilFinished, mMillisInFuture);
    }

    public void onFinish() {
        if (listener != null) listener.onFinish();
    }

    public void setDownTimerhListener(OnDownTimerhListener listener) {
        this.listener = listener;
    }

    public interface OnDownTimerhListener {

        void onTick(long millisUntilFinished, long totalTime);

        void onFinish();
    }
}
