package com.fly.myuiassemble.viewholder;

import android.os.SystemClock;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fly.myuiassemble.R;
import com.fly.viewlibrary.time.CustomCountDownTimer;

import java.util.concurrent.TimeUnit;

/**
 * 包    名 : com.shangyi.assess.holder
 * 作    者 : FLY
 * 创建时间 : 2018/1/11
 * 描述: 过渡页界面
 */

public class TransitionPageViewViewHolder extends BaseViewHolder {

    private TextView tvTitle;
    private TextView tvTime;
    private CustomCountDownTimer mCountDownTimer;
    private OnCompleteListener mCompleteListener;

    private TransitionPageViewViewHolder(View view) {
        super(view);
    }

    /**
     * 创建对应界面
     *
     * @param parent
     * @return
     */
    public static TransitionPageViewViewHolder createView(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.common_transition_page_1_layout, parent, false);
        parent.addView(view);
        TransitionPageViewViewHolder holder = new TransitionPageViewViewHolder(view);
        holder.tvTime = view.findViewById(R.id.tvTime);
        return holder;
    }

    public void  start(long time,String text) {
        setVisibility(true);
        mCountDownTimer = new CustomCountDownTimer(mItemView.getContext(), tvTime,
                R.string.common_start, R.string.common_count_down, time-1000, 1000,
                true, R.color.common_white_color, R.color.common_white_color);
        mCountDownTimer.setOnFinishListener(() -> {
            end();
        });
        mCountDownTimer.start();
    }

    public void complete() {
        mCountDownTimer.cancel();
        end();
    }

    private void end() {
        setVisibility(false);
        if (mCompleteListener != null) mCompleteListener.onComplete();
    }

    public void setCompleteListener(OnCompleteListener mCompleteListener) {
        this.mCompleteListener = mCompleteListener;
    }

    public interface OnCompleteListener {
        void onComplete();
    }
}
