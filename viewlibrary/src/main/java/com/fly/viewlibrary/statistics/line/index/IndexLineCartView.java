package com.fly.viewlibrary.statistics.line.index;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.fly.viewlibrary.R;
import com.fly.viewlibrary.utils.StringUtils;
import com.fly.viewlibrary.utils.TimeUtils;
import com.fly.viewlibrary.utils.ViewUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

/**
 * 包    名 : com.fly.viewlibrary.statistics.line
 * 作    者 : FLY
 * 创建时间 : 2019/10/16
 * 描述: 指标曲线
 */
public class IndexLineCartView extends View {

    /**
     * 指标数据列表
     */
    private List<? extends IndexLineMode> mData;

    /**
     * 指标区间
     */
    private HashSet<Integer> ectionsData;

    /**
     * 实线画笔
     */
    private Paint mPaint;

    /**
     * 虚线画笔
     */
    private Paint mDottedPaint;

    /**
     * 宽
     */
    private float width;

    /**
     * 高
     */
    private float height;

    /**
     * 虚线颜色
     */
    private int mDottedLineColor = 0xff999999;

    /**
     * 虚线的高度
     */
    private float mDottedLineHeight = 1;

    /**
     * 虚线里左边的间距
     */
    private float mDottedSpacing = 20;

    /**
     * 实线颜色
     */
    private int mSolidLineColor = 0xffff6770;

    /**
     * 实线的高度
     */
    private float mSolidLineHeight = 1;

    /**
     * 点的颜色
     */
    private int mPointColor = 0xffff6770;

    /**
     * 点的半径
     */
    private float mPointRadius = 3;

    /**
     * 值的大小
     */
    private float mValueSize = 10;

    /**
     * 底部文本颜色
     */
    private int mBottomTextColor = 0xff333333;

    /**
     * 底部文本大小
     */
    private float mBottomTextSize = 12;

    /**
     * 左边文本颜色
     */
    private int mLeftTextColor = 0xff333333;

    /**
     * 左边文本大小
     */
    private float mLeftTextSize = 12;

    /**
     * x点之间的间距
     */
    private float xInterval = 60;

    /**
     * 在移动时，第一个点允许最小的x坐标
     */
    private float minXinit;
    /**
     * 在移动时，第一个点允许允许最大的x坐标
     */
    private float maxXinit;

    /**
     * 滑动启动点X
     */
    private float startX;

    /**
     * 滑动启动点Y
     */
    private float startY;

    /**
     * 第一个点位置
     */
    private float xInit;

    /**
     * 初始化数据
     */
    private boolean isFirst = true;

    /**
     * 最大值
     */
    private double max;

    /**
     * 最小值
     */
    private double min;

    /**
     * 日期格式
     */
    private String dateFormat = "MM-dd";

    /**
     * 时间
     */
    private String timeFormat = "HH:mm";

    /**
     * 背景颜色
     */
    private int mBackgroundColor = 0xffffffff;

    /**
     * 背景透明色
     */
    private int mTransparentColor = 0x17BCA573;

    public IndexLineCartView(Context context) {
        this(context, null);
    }

    public IndexLineCartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IndexLineCartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initConfig(context, attrs, defStyleAttr);
        initPainters();
    }

    /**
     * 初始化LineCart属性
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    private void initConfig(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.IndexLineCartView, defStyleAttr, 0);

        //虚线参数
        mDottedLineColor = array.getColor(R.styleable.IndexLineCartView_iLCDottedLineColor, mDottedLineColor);
        mDottedLineHeight = array.getDimension(R.styleable.IndexLineCartView_iLCDottedLineHeight, ViewUtils.dip2px(context, mDottedLineHeight));
        mDottedSpacing = array.getDimension(R.styleable.IndexLineCartView_iLCDottedSpacing, ViewUtils.dip2px(context, mDottedSpacing));

        //实线参数
        mSolidLineColor = array.getColor(R.styleable.IndexLineCartView_iLCSolidLineColor, mSolidLineColor);
        mSolidLineHeight = array.getDimension(R.styleable.IndexLineCartView_iLCSolidLineHeight, ViewUtils.dip2px(context, mSolidLineHeight));

        //点与值的参数
        mPointColor = array.getColor(R.styleable.IndexLineCartView_iLCPointColor, mPointColor);
        mPointRadius = array.getDimension(R.styleable.IndexLineCartView_iLCPointRadius, ViewUtils.dip2px(context, mPointRadius));
        mValueSize = array.getDimension(R.styleable.IndexLineCartView_iLCValueSize, ViewUtils.dip2px(context, mValueSize));

        //底部文本参数
        mBottomTextColor = array.getColor(R.styleable.IndexLineCartView_iLCBottomTextColor, mBottomTextColor);
        mBottomTextSize = array.getDimension(R.styleable.IndexLineCartView_iLCBottomTextSize, ViewUtils.dip2px(context, mBottomTextSize));

        //左边文本参数
        mLeftTextColor = array.getColor(R.styleable.IndexLineCartView_iLCLeftTextColor, mLeftTextColor);
        mLeftTextSize = array.getDimension(R.styleable.IndexLineCartView_iLCLeftTextSize, ViewUtils.dip2px(context, mLeftTextSize));

        //x点之间的间距
        xInterval = array.getDimension(R.styleable.IndexLineCartView_iLCXInterval, ViewUtils.dip2px(context, xInterval));

        //背景色
        //控件背景有透明度时需要设置背景底色和透明色
        mBackgroundColor = array.getColor(R.styleable.IndexLineCartView_iLCBackgroundColor, mBackgroundColor);
        mTransparentColor = array.getColor(R.styleable.IndexLineCartView_iLCTransparentColor, mTransparentColor);

        array.recycle();
    }

    private void initPainters() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);//抗锯齿
        mPaint.setAntiAlias(true);//防抖动
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(mSolidLineHeight);

        mDottedPaint = new Paint(Paint.ANTI_ALIAS_FLAG);//抗锯齿
        mDottedPaint.setAntiAlias(true);//防抖动
        mDottedPaint.setStyle(Paint.Style.STROKE);
        mDottedPaint.setStrokeCap(Paint.Cap.ROUND);
        mDottedPaint.setColor(mDottedLineColor);
        mDottedPaint.setStrokeWidth(mDottedLineHeight);
        mDottedPaint.setPathEffect(new DashPathEffect(new float[]{ViewUtils.dip2px(getContext(), 2f), ViewUtils.dip2px(getContext(), 2f)}, 0));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (isFirst) {
            width = getWidth() - getPaddingRight() - getPaddingLeft();
            height = getHeight() - getPaddingTop() - getPaddingBottom();
            int size = 0;
            if (listIsNotNull(mData)) {
                size = mData.size() - 1;
            }
            float length = xInterval * size;

            mPaint.setTextSize(mBottomTextSize);
            float v = mPaint.measureText("12-23");
            float realLength = width - mDottedSpacing - v;
            if (length <= realLength) {
                minXinit = maxXinit = xInit = length + mDottedSpacing + v;
            } else {
                minXinit = xInit = realLength + mDottedSpacing;
                maxXinit = length + mDottedSpacing + v;
            }
            isFirst = false;
        }
        double numDifference = max - min;
        numDifference = numDifference <= 0 ? 1 : numDifference;

        // 折线高度
        float lY = height - mValueSize - mPointRadius - mBottomTextSize - mBottomTextSize * 0.8f - 2 * mLeftTextSize - ViewUtils.dip2px(getContext(), 2);
        //折线高度比例
        float proportion = (float) (lY / numDifference);

        //折线最小值高度
        float minValueHeight = height + getPaddingBottom() - mBottomTextSize - mBottomTextSize * 0.8f - mLeftTextSize;

        drawX(minValueHeight, proportion, canvas);

        drawVerticalRectangle(canvas);

        drawDottedLine(minValueHeight, proportion, canvas);
    }


    /**
     * 绘制虚线
     *
     * @param minValueHeight 折线最小值高度
     * @param proportion     折线高度比例
     * @param canvas
     */
    private void drawDottedLine(float minValueHeight, float proportion, Canvas canvas) {
        if (ectionsData == null || ectionsData.size() <= 0) return;
        Path path = new Path();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(mSolidLineHeight);
        mPaint.setColor(mLeftTextColor);
        mPaint.setTextSize(mLeftTextSize);
        for (Integer ectionsDatum : ectionsData) {
            float coordinateY = (float) (minValueHeight + (min - ectionsDatum) * proportion);
            path.moveTo(getPaddingLeft() + mDottedSpacing, coordinateY);
            path.lineTo(width, coordinateY);
            canvas.drawPath(path, mDottedPaint);

            canvas.drawText(String.valueOf(ectionsDatum), getPaddingLeft() + ViewUtils.dip2px(getContext(), 2), coordinateY + mLeftTextSize / 2, mPaint);
        }
    }

    /**
     * 画点 线
     *
     * @param minValueHeight 折线最小值高度
     * @param proportion     折线高度比例
     * @param canvas
     */
    private void drawX(float minValueHeight, float proportion, Canvas canvas) {
        if (listIsNotNull(mData)) {
            for (int j = 0; j < mData.get(0).getValues().size(); j++) {
                Path path = new Path();
                for (int i = 0; i < mData.size(); i++) {
                    IndexLineMode indexLineMode = mData.get(i);
                    Double d = indexLineMode.getValues().get(j);
                    float x = xInit - i * xInterval;
                    float coordinateY = (float) (minValueHeight + (min - d) * proportion);
                    if (i == 0) {
                        path.moveTo(x, coordinateY);
                    } else {
                        path.lineTo(x, coordinateY);
                    }


                    //画小圆点
                    mPaint.setStyle(Paint.Style.FILL);
                    mPaint.setColor(mPointColor);
                    canvas.drawCircle(x, coordinateY, mPointRadius, mPaint);

                    //画数据对应值得文本
                    mPaint.setTextSize(mValueSize);
                    float valueT = mPaint.measureText(String.valueOf(d));
                    float textY = coordinateY - mPointRadius - ViewUtils.dip2px(getContext(), 2);
                    canvas.drawText(String.valueOf(d), x - valueT / 2, textY, mPaint);

                    //画底部文本
                    if (j == 0) {
                        float timeY = height + getPaddingBottom() - mLeftTextSize / 2;

                        mPaint.setColor(mBottomTextColor);
                        mPaint.setTextSize(mBottomTextSize);
                        float dateT = mPaint.measureText(String.valueOf(d));
                        String date = TimeUtils.format(indexLineMode.getRecordTime(), dateFormat);
                        float dateY = timeY - mBottomTextSize * 0.8f;
                        canvas.drawText(date, x - dateT / 2, dateY, mPaint);

                        mPaint.setTextSize(mBottomTextSize * 0.8f);
                        float timeT = mPaint.measureText(String.valueOf(d));
                        String time = TimeUtils.format(indexLineMode.getRecordTime(), timeFormat);
                        canvas.drawText(time, x - timeT / 2, timeY, mPaint);
                    }
                }

                mPaint.setColor(mSolidLineColor);
                mPaint.setStyle(Paint.Style.STROKE);
                canvas.drawPath(path, mPaint);
            }
        }
    }

    /**
     * 垂直矩形
     *
     * @param canvas
     */
    private void drawVerticalRectangle(Canvas canvas) {
        float minValueHeight = height + getPaddingBottom() - mLeftTextSize / 2;
        int maxValueHeight = (int) (getPaddingTop() + mLeftTextSize);
        int leftX2 = (int) (getPaddingLeft() + mDottedSpacing);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mBackgroundColor);
        canvas.drawRect(0, maxValueHeight, leftX2, minValueHeight, mPaint);// 长方形
        canvas.drawRect(width, maxValueHeight, getWidth(), minValueHeight, mPaint);// 长方形
        if (mTransparentColor > 0) {
            mPaint.setColor(mTransparentColor);
            canvas.drawRect(0, maxValueHeight, leftX2, minValueHeight, mPaint);// 长方形
            canvas.drawRect(width, maxValueHeight, getWidth(), minValueHeight, mPaint);// 长方形
        }
    }

    private boolean listIsNotNull(List list) {
        return list != null && list.size() > 0;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                this.getParent().requestDisallowInterceptTouchEvent(true);
                startX = event.getX();
                startY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float disY = event.getY() - startY;
                float dis = event.getX() - startX;
                if (Math.abs(disY) > Math.abs(dis)) {
                    this.getParent().requestDisallowInterceptTouchEvent(false);
                    return true;
                }
                if (Math.abs(dis) < ViewUtils.dip2px(getContext(), 8)) {
                    this.getParent().requestDisallowInterceptTouchEvent(false);
                    return true;
                }
                if (xInit + dis >= maxXinit) {
                    xInit = maxXinit;
                } else if (xInit + dis <= minXinit) {
                    xInit = minXinit;
                } else {
                    xInit = (int) (xInit + dis);
                }
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return true;
    }

    /**
     * 设置左边显示区间
     *
     * @param ectionsData
     */
    public void setLeftData(HashSet<Integer> ectionsData) {
        if (ectionsData != null && ectionsData.size() > 0) {
            this.ectionsData = ectionsData;
            List<Double> list = new ArrayList<>();
            for (Integer ectionsDatum : ectionsData) {
                list.add(Double.valueOf(ectionsDatum));
            }
            if (listIsNotNull(mData)) {
                for (IndexLineMode mDatum : mData) {
                    if (listIsNotNull(mDatum.getValues())) {
                        list.addAll(mDatum.getValues());
                    }
                }
            }
            max = Collections.max(list);
            min = Collections.min(list);
            isFirst = true;
            invalidate();
        }
    }

    /**
     * 设置点数据 数组每条值数组长度必须相同，且不为空，否则不绘制
     *
     * @param mData 值数组
     */
    public void setData(List<? extends IndexLineMode> mData) {
        setData(mData, "0.0");
    }

    /**
     * 设置点数据 数组每条值数组长度必须相同，且不为空，否则不绘制
     *
     * @param mData   值数组
     * @param pattern 保留小数模型
     */
    public void setData(List<? extends IndexLineMode> mData, String pattern) {
        if (listIsNotNull(mData)) {
            List<Double> list = new ArrayList<>();
            for (IndexLineMode mDatum : mData) {
                if (listIsNotNull(mDatum.getValues())
                        && mDatum.getValues().size() == mData.get(0).getValues().size()) {
                    for (Double aDouble : mDatum.getValues()) {
                        if (aDouble != null) {
                            String s = StringUtils.doubleString(aDouble, pattern);
                            list.add(Double.parseDouble(s));
                        } else {
                            Log.e("setData", "折线数据值不能为空且必须位数相同");
                            return;
                        }
                    }
                } else {
                    Log.e("setData", "折线数据值不能为空且必须位数相同");
                    return;
                }
            }
            if (ectionsData != null && ectionsData.size() > 0) {
                for (Integer ectionsDatum : ectionsData) {
                    list.add(Double.valueOf(ectionsDatum));
                }
            }
            this.mData = mData;
            max = Collections.max(list);
            min = Collections.min(list);
            isFirst = true;
            invalidate();
        }
    }
}
