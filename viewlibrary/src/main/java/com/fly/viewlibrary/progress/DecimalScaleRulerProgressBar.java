package com.fly.viewlibrary.progress;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Scroller;

import com.fly.viewlibrary.R;
import com.fly.viewlibrary.utils.ViewUtils;

/**
 * 包    名 : com.fly.viewlibrary.progress
 * 作    者 : FLY
 * 创建时间 : 2019/11/26
 * 描述: 小数点横向滚动刻度尺
 */
public class DecimalScaleRulerProgressBar extends View {

    /**
     * 滑动
     */
    private Scroller mScroller;

    /**
     * 速度追踪器：计算出当前手势的初始速度
     */
    private VelocityTracker mVelocityTracker;

    /**
     * 手势动作的最小速度值
     */
    private int mMinVelocity;

    /**
     * 宽度
     */
    private int mWidth;

    /**
     * 高度
     */
    private int mHeight;

    /**
     * 进率
     */
    private int mAdvanceRate;

    /**
     * 默认值
     */
    private int mDefaultValue;

    /**
     * 当前小格子
     */
    private float mItemNum;
    /**
     * 小各种起始值
     */
    private float mMinItemNum;

    /**
     * 最小值
     */
    private float mMinValue;

    /**
     * 最大值
     */
    private float mMaxValue;

    /**
     * 大格子间隔值
     */
    private int mPerSpanValue;

    /**
     * 小格子间隔
     */
    private int mItemSpacing;

    /**
     * 文本大小
     */
    private int mTextSize;

    /**
     * 大刻度高度
     */
    private int mMaxLineHeight;

    /**
     * 中间刻度高度
     */
    private int mMiddleLineHeight;

    /**
     * 小刻度高度
     */
    private int mMinLineHeight;

    /**
     * 刻度宽
     */
    private int mLineWidth;

    /**
     * 文本与刻度距离
     */
    private int mTextMarginTop;

    /**
     * 文本高度
     */
    private float mTextHeight;

    /**
     * 选中刻度宽度
     */
    private int mSelectWidth;

    /**
     * 选中刻度颜色
     */
    private int mSelectColor;

    /**
     * 线颜色
     */
    private int dsrLineColor;

    /**
     * 小格子数
     */
    private int mTotalLine;

    /**
     * 绘制文本的画笔
     */
    private Paint mTextPaint;

    /**
     * 线画笔
     */
    private Paint mLinePaint;

    /**
     * 默认尺起始点在屏幕中心, offset是指尺起始点的偏移值
     */
    private float mOffset;

    /**
     * 最大偏移
     */
    private int mMaxOffset;

    private int mLastX, mMove;

    /**
     * 选中刻度画笔
     */
    private Paint mSelectPaint = new Paint();

    /**
     * 滑动监听
     */
    private OnValueChangeListener mListener;

    public DecimalScaleRulerProgressBar(Context context) {
        this(context, null);
    }

    public DecimalScaleRulerProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public DecimalScaleRulerProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    protected void init(Context context, AttributeSet attrs) {
        mScroller = new Scroller(context);
        //获得允许执行一个fling手势动作的最小速度值
        mMinVelocity = ViewConfiguration.get(getContext()).getScaledMinimumFlingVelocity();

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.DecimalScaleRulerProgressBar);
        mAdvanceRate = (int) ta.getDimension(R.styleable.DecimalScaleRulerProgressBar_dsrAdvanceRate, 12);
        mDefaultValue = ta.getInteger(R.styleable.DecimalScaleRulerProgressBar_dsrDefaultValue, 50);
        mMinValue = ta.getInteger(R.styleable.DecimalScaleRulerProgressBar_dsrMinValue, 0);
        mMaxValue = ta.getInteger(R.styleable.DecimalScaleRulerProgressBar_dsrMaxValue, 300);
        mPerSpanValue = ta.getInteger(R.styleable.DecimalScaleRulerProgressBar_dsrPerSpanValue, 1);
        mItemSpacing = (int) ta.getDimension(R.styleable.DecimalScaleRulerProgressBar_dsrItemSpacing, ViewUtils.dip2px(getContext(), 7));
        mTextSize = (int) ta.getDimension(R.styleable.DecimalScaleRulerProgressBar_dsrTextSize, ViewUtils.dip2px(getContext(), 12));
        mMaxLineHeight = (int) ta.getDimension(R.styleable.DecimalScaleRulerProgressBar_dsrMaxLineHeight, ViewUtils.dip2px(getContext(), 32));
        mMiddleLineHeight = (int) ta.getDimension(R.styleable.DecimalScaleRulerProgressBar_dsrMiddleLineHeight, ViewUtils.dip2px(getContext(), 32));
        mMinLineHeight = (int) ta.getDimension(R.styleable.DecimalScaleRulerProgressBar_dsrMinLineHeight, ViewUtils.dip2px(getContext(), 24));
        mLineWidth = (int) ta.getDimension(R.styleable.DecimalScaleRulerProgressBar_dsrLineWidth, ViewUtils.dip2px(getContext(), 1));
        mTextMarginTop = (int) ta.getDimension(R.styleable.DecimalScaleRulerProgressBar_dsrTextMarginTop, ViewUtils.dip2px(getContext(), 14));
        mSelectWidth = (int) ta.getDimension(R.styleable.DecimalScaleRulerProgressBar_dsrSelectWidth, ViewUtils.dip2px(getContext(), 2));
        dsrLineColor = ta.getColor(R.styleable.DecimalScaleRulerProgressBar_dsrLineColor, 0X80222222);
        mSelectColor = ta.getColor(R.styleable.DecimalScaleRulerProgressBar_dsrSelectColor, 0XffF7577F);
        mMinItemNum = mMinValue / mPerSpanValue * mAdvanceRate;
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(dsrLineColor);
        mTextHeight = getFontHeight(mTextPaint);

        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setStrokeWidth(mLineWidth);
        mLinePaint.setColor(dsrLineColor);

        initViewParam();
    }

    private void initViewParam() {
        mTotalLine = (int) (mMaxValue - mMinValue) * mAdvanceRate / mPerSpanValue + 1;
        mMaxOffset = -(mTotalLine - 1) * mItemSpacing;
        mOffset = (mMinValue - mDefaultValue) / mPerSpanValue * mItemSpacing * mAdvanceRate;
        invalidate();
        setVisibility(VISIBLE);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w > 0 && h > 0) {
            mWidth = w;
            mHeight = h;
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawScaleLine(canvas);
        drawMiddleLine(canvas);
    }

    /**
     * 画中间的红色指示线
     *
     * @param canvas
     */
    private void drawMiddleLine(Canvas canvas) {
        canvas.save();
        mSelectPaint.setStrokeWidth(mSelectWidth);
        mSelectPaint.setColor(mSelectColor);
        canvas.drawLine(mWidth / 2, 0, mWidth / 2, mHeight * 2 / 3
                , mSelectPaint);
        canvas.restore();
    }

    private void drawScaleLine(Canvas canvas) {
        canvas.save();
        float left, height;
        String value;
        int alpha;
        float scale;
        int srcPointX = mWidth / 2; // 默认表尺起始点在屏幕中心
        for (int i = 0; i < mTotalLine; i++) {
            left = srcPointX + mOffset + i * mItemSpacing;

            if (left < 0 || left > mWidth) {
                continue;
            }

            if (i % mAdvanceRate == 0) {
                height = mMaxLineHeight;
            } else if (i % getMiddleValue() == 0) {
                height = mMiddleLineHeight;
            } else {
                height = mMinLineHeight;
            }
            scale = 1 - Math.abs(left - srcPointX) / srcPointX;//透明位置
            alpha = (int) (255 * scale * scale);//透明度
            mLinePaint.setAlpha(alpha);
            canvas.drawLine(left, 0, left, height, mLinePaint);

            if (i % mAdvanceRate == 0) { // 大指标,要标注文字
                value = String.valueOf((int) (mMinValue + i * mPerSpanValue / mAdvanceRate));
                mTextPaint.setAlpha(alpha);
                canvas.drawText(value, left - mTextPaint.measureText(value) / 2,
                        height + mTextMarginTop + mTextHeight - ViewUtils.dip2px(getContext(), 3), mTextPaint);
            }

        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        int xPosition = (int) event.getX();

        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mScroller.forceFinished(true);
                mLastX = xPosition;
                mMove = 0;
                break;
            case MotionEvent.ACTION_MOVE:
                mMove = (mLastX - xPosition);
                changeMoveAndValue();
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                countMoveEnd();
                countVelocityTracker();
                return false;
            default:
                break;
        }

        mLastX = xPosition;
        return true;
    }

    private void countVelocityTracker() {
        mVelocityTracker.computeCurrentVelocity(1000);
        float xVelocity = mVelocityTracker.getXVelocity();
        if (Math.abs(xVelocity) > mMinVelocity) {
            mScroller.fling(0, 0, (int) xVelocity, 0, Integer.MIN_VALUE, Integer.MAX_VALUE, 0, 0);
        }
    }

    private void countMoveEnd() {
        mOffset -= mMove;
        if (mOffset <= mMaxOffset) {
            mOffset = mMaxOffset;
        } else if (mOffset >= 0) {
            mOffset = 0;
        }

        mLastX = 0;
        mMove = 0;

        int math = Math.round(Math.abs(mOffset) / mItemSpacing);
        mItemNum = mMinItemNum + math;
        mOffset = (mMinItemNum - mItemNum) * mItemSpacing; // 矫正位置,保证不会停留在两个相邻刻度之间
        notifyValueChange();
        postInvalidate();
    }

    private void changeMoveAndValue() {
        mOffset -= mMove;
        if (mOffset <= mMaxOffset) {
            mOffset = mMaxOffset;
            mMove = 0;
            mScroller.forceFinished(true);
        } else if (mOffset >= 0) {
            mOffset = 0;
            mMove = 0;
            mScroller.forceFinished(true);
        }
        mItemNum = mMinItemNum + Math.round(Math.abs(mOffset) / mItemSpacing);
        notifyValueChange();
        postInvalidate();
    }

    private void notifyValueChange() {
        if (null != mListener) {
            int i = (int) (mItemNum / mAdvanceRate);
            int d = (int) (mItemNum % mAdvanceRate);
            mListener.onValueChange(i, d);
        }
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            if (mScroller.getCurrX() == mScroller.getFinalX()) { // over
                countMoveEnd();
            } else {
                int xPosition = mScroller.getCurrX();
                mMove = (mLastX - xPosition);
                changeMoveAndValue();
                mLastX = xPosition;
            }
        }
    }

    /**
     * @return 返回指定笔的文字高度
     */
    private float getFontHeight(Paint paint) {
        Paint.FontMetrics fm = paint.getFontMetrics();
        return fm.descent - fm.ascent;
    }

    /**
     * 获取中间值
     *
     * @return
     */
    private int getMiddleValue() {
        return mAdvanceRate / 2;
    }

    /**
     * 设置用于接收结果的监听器
     *
     * @param listener
     */
    public void setValueChangeListener(OnValueChangeListener listener) {
        mListener = listener;
    }

    /**
     * 设置间隔值
     *
     * @param mPerSpanValue
     */
    public void setPerSpanValue(int mPerSpanValue) {
        this.mPerSpanValue = mPerSpanValue;
        initViewParam();
    }

    /**
     * 设置默认值
     *
     * @param mDefaultValue
     */
    public void setDefaultValue(int mDefaultValue) {
        this.mDefaultValue = mDefaultValue;
        initViewParam();
    }

    /**
     * 设置最小值
     *
     * @param mMinValue
     */
    public void setMinValue(float mMinValue) {
        this.mMinValue = mMinValue;
        mMinItemNum = mMinValue / mPerSpanValue * mAdvanceRate;
        initViewParam();
    }

    /**
     * 设置最大值
     *
     * @param mMaxValue
     */
    public void setMaxValue(float mMaxValue) {
        this.mMaxValue = mMaxValue;
        initViewParam();
    }


    /**
     * 滑动监听
     */
    public interface OnValueChangeListener {
        void onValueChange(int valueInt, int valueDecimal);
    }
}
