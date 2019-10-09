package com.fly.viewlibrary.progress;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.Keep;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.fly.viewlibrary.R;
import com.fly.viewlibrary.utils.ViewUtils;

/**
 * 包    名 : com.fly.viewlibrary.progress
 * 作    者 : FLY
 * 创建时间 : 2019/10/8
 * 描述: 圆形进度条（可设置 线性渐变-背景色-进度条颜色-圆弧宽度）
 */
public class CirclePercentBar extends View {

    public static final int WIDTH_RADIUS_RATIO = 4;
    public static final int MAX = 100;
    private Paint mPaint;
    private int max;
    private float progressPercent;
    private float radius;//圆弧宽度
    private RectF rectF;
    private int bgColor, progressColor;
    private int startColor, endColor;
    private LinearGradient gradient;
    private boolean isGradient;

    public CirclePercentBar(Context context) {
        this(context, null);
    }

    public CirclePercentBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CirclePercentBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CirclePercentView);
        bgColor = typedArray.getColor(R.styleable.CirclePercentView_circleBgColor, 0xffcfcfcf);
        progressColor = typedArray.getColor(R.styleable.CirclePercentView_circleProgressColor, 0xffffc032);
        radius = typedArray.getDimension(R.styleable.CirclePercentView_circleRadius, ViewUtils.dip2px(context, WIDTH_RADIUS_RATIO));
        isGradient = typedArray.getBoolean(R.styleable.CirclePercentView_circleIsGradient, true);
        startColor = typedArray.getColor(R.styleable.CirclePercentView_circleStartColor, 0xff3A3D4E);
        endColor = typedArray.getColor(R.styleable.CirclePercentView_circleEndColor, 0xff475B80);
        max = typedArray.getInt(R.styleable.CirclePercentView_circleMax, MAX);
        int progress = typedArray.getInt(R.styleable.CirclePercentView_circleProgress, 0);
        if (progress > max) {
            progressPercent = 1;
        } else progressPercent = progress / max;
        typedArray.recycle();
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (getMeasuredWidth() > getMeasuredHeight()){
            setMeasuredDimension(getMeasuredHeight(), getMeasuredHeight());
        }else {
            setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth());//自定义的View能够使用wrap_content或者是match_parent的属性
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        gradient = new LinearGradient(getWidth(), 0, getWidth(), getHeight(), startColor, endColor, Shader.TileMode.MIRROR);
    }

    @SuppressWarnings("Duplicates")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 1、绘制背景灰色圆环
        int centerX = getWidth() / 2;
        mPaint.setShader(null);//必须设置为null，否则背景也会加上渐变色
        mPaint.setStrokeWidth(radius); //设置画笔的大小
        mPaint.setColor(bgColor);
        canvas.drawCircle(centerX, centerX, centerX - radius / 2, mPaint);
        // 2、绘制比例弧
        if (rectF == null) {//外切正方形
            rectF = new RectF(radius / 2, radius / 2, 2 * centerX - radius / 2, 2 * centerX - radius / 2);
        }
        //3、是否绘制渐变色
        if (isGradient) {
            mPaint.setShader(gradient);//设置线性渐变
        } else {
            mPaint.setColor(progressColor);
        }
        canvas.drawArc(rectF, -90, 360f * progressPercent, false, mPaint);   //画比例圆弧
    }

    private void init() {
        mPaint = new Paint();
        //画笔样式
        mPaint.setStyle(Paint.Style.STROKE);
        //设置笔刷的样式:圆形
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        //设置抗锯齿
        mPaint.setAntiAlias(true);
    }

    @Keep
    public void setPercentage(float percentage) {
        if (percentage > max) {
            this.progressPercent = 1;
        } else this.progressPercent = percentage / max;
        invalidate();
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
    }

    public void setProgressColor(int progressColor) {
        this.progressColor = progressColor;
    }

    public void setStartColor(int startColor) {
        this.startColor = startColor;
    }

    public void setEndColor(int endColor) {
        this.endColor = endColor;
    }

    public void setGradient(boolean gradient) {
        isGradient = gradient;
    }
}