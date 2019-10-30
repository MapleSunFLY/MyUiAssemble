package com.fly.viewlibrary.time;

import android.app.Activity;
import android.os.CountDownTimer;
import android.support.annotation.ColorRes;
import android.widget.TextView;

import com.fly.viewlibrary.R;

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
 * 包    名 : com.shangyi.android.commonlibrary.widget
 * 作    者 : FLY
 * 创建时间 : 2018/10/29
 * 描述: 自定义倒计时
 */
public class CustomCountDownTimer extends CountDownTimer {


    private TextView mTv;
    private Activity activity;
    private int mResFinishId;
    private int mResTickId;
    private boolean isEnabled;
    private int startColor;
    private int endColor;
    private OnFinishListener onFinishListener;

    public CustomCountDownTimer(Activity act, TextView tt, int resFinishId, int resTickId, long millisInFuture, long countDownInterval) {
        this(act, tt, resFinishId, resTickId, millisInFuture, countDownInterval, false, R.color.common_666666_color, R.color.common_999999_color);
    }

    public CustomCountDownTimer(Activity act, TextView tt, int resFinishId, int resTickId, long millisInFuture,
                                long countDownInterval, boolean isEnabled, @ColorRes int startColor, @ColorRes int endColor) {
        super(millisInFuture, countDownInterval);
        this.mTv = tt;
        this.activity = act;
        this.mResFinishId = resFinishId;
        this.mResTickId = resTickId;
        this.isEnabled = isEnabled;
        this.startColor = startColor;
        this.endColor = endColor;
    }

    public void setOnFinishListener(OnFinishListener onFinishListener) {
        this.onFinishListener = onFinishListener;
    }

    @Override
    public void onFinish() {
        mTv.setEnabled(true);
        mTv.setText(mResFinishId);
        mTv.setTextColor(mTv.getResources().getColor(startColor));
        if (onFinishListener != null) onFinishListener.onFinish();
    }


    @Override
    public void onTick(long millisUntilFinished) {
        mTv.setEnabled(isEnabled);
        mTv.setTextColor(mTv.getResources().getColor(endColor));
        mTv.setText(activity.getString(mResTickId, millisUntilFinished / 1000 + ""));
    }


    public interface OnFinishListener {
        void onFinish();
    }
}
