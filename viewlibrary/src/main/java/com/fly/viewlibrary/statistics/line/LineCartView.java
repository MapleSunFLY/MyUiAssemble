package com.fly.viewlibrary.statistics.line;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.fly.viewlibrary.R;
import com.fly.viewlibrary.utils.StringUtils;
import com.fly.viewlibrary.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *           .----.
 *        _.'__    `.
 *    .--(Q)(OK)---/$\
 *  .' @          /$$$\
 *  :         ,   $$$$$
 *   `-..__.-' _.-\$$/
 *         `;_:    `"'
 *       .'"""""`.
 *      /,  FLY  ,\
 *     //         \\
 *     `-._______.-'
 *     ___`. | .'___
 *    (______|______)
 * </pre>
 * 包    名 : com.fly.viewlibrary.statistics.line
 * 作    者 : FLY
 * 创建时间 : 2019/8/15
 * 描述:
 */
public class LineCartView extends View {


    //折线数据
    private List<String> mData = new ArrayList<>();

    //虚线里左边的间距
    private float mDottedCoordinate = 20;
    //虚线颜色
    private int mDottedLineColor = 0xffebebeb;
    //虚线的高度
    private float mDottedLineHeight = 1;
    //虚线 Paint
    private Paint mDottedPaint;

    //垂直的数据
    private List<String> mVerticalData = new ArrayList<>();
    //垂直对应文字颜色
    private int mVerticalTextColor = 0xff333333;
    //垂直对应文字大小
    private float mVerticalTextSize = 9;
    //垂直对应背景颜色
    private int mVerticalBackgroundColor = Color.WHITE;
    //垂直文字Paint
    private Paint mVerticalTextPaint;
    //垂直背景方块
    private Paint mVerticalBackgroundPaint;

    //底部对应文字的数据(日期)
    private List<String> mBottoData = new ArrayList<>();
    //底部对应文字的颜色(日期)
    private int mBottoTextColor = 0xff333333;
    //底部对应文字大小(日期)
    private float mBottoTextSize = 10;
    //底部对应文字Paint(日期)
    private Paint mBottoTextPaint;

    //底部对应说明文字的数据(时间)
    private List<String> mBottoHintData = new ArrayList<>();
    //底部对应说明文字的颜色(时间)
    private int mBottoHintColor = 0xff999999;
    //底部对应说明文字大小(时间)
    private float mBottoHintSize = 8;
    //底部说明文字Paint(时间)
    private Paint mBottoHintTextPaint;

    //折线颜色
    private int mLineColor = 0xff495fab;
    //折线高度
    private float mLineHeight = 1f;
    //折线 Paint
    private Paint mBrokenPaint;
    //折线 path
    private Path mBrokenPath;

    //分数对应得圆点颜色
    private int mPointColor = 0xff495fab;
    //分数对应得圆点大小
    private float mPointSize = 3;
    //分数对应点Paint
    private Paint mPointPaint;

    //分数对应得圆点颜色
    private int mScoreTextColor = 0xff495fab;
    //分数文字大小
    private float mScoreTextSize = 7;
    //分数文本
    private Paint mScorePaint;

    //view得宽
    private float viewWidth;
    //view得高
    private float viewHeight;
    //显示数据的个数
    private float xInterval = 22;    //x点之间的间距
    private float yInterval = 10;    //y点之间的间距
    private float minXinit;//在移动时，第一个点允许最小的x坐标
    private float maxXinit;//在移动时，第一个点允许允许最大的x坐标
    private float xInit;
    private boolean isFirst;


    public LineCartView(Context context) {
        this(context, null);
    }

    public LineCartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineCartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initConfig(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 初始化LineCart属性
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    private void initConfig(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.LineCartView, defStyleAttr, 0);
        //虚线属性
        mDottedLineColor = array.getColor(R.styleable.LineCartView_lineCartDottedLineColor, mDottedLineColor);
        mDottedLineHeight = array.getDimension(R.styleable.LineCartView_lineCartDottedLineHeight, mDottedLineHeight);
        mDottedCoordinate = array.getDimension(R.styleable.LineCartView_lineCartDottedCoordinate, mDottedCoordinate);
        //垂直属性
        mVerticalTextColor = array.getColor(R.styleable.LineCartView_lineCartVerticalTextColor, mVerticalTextColor);
        mVerticalTextSize = array.getDimension(R.styleable.LineCartView_lineCartVerticalTextSize, mVerticalTextSize);
        mVerticalBackgroundColor = array.getColor(R.styleable.LineCartView_lineCartVerticalBackgroundColor, mVerticalBackgroundColor);
        //底部属性
        mBottoTextColor = array.getColor(R.styleable.LineCartView_lineCartBottoTextColor, mBottoTextColor);
        mBottoTextSize = array.getDimension(R.styleable.LineCartView_lineCartBottoTextSize, mBottoTextSize);
        mBottoHintColor = array.getColor(R.styleable.LineCartView_lineCartBottoHintColor, mBottoHintColor);
        mBottoHintSize = array.getDimension(R.styleable.LineCartView_lineCartBottoHintSize, mBottoHintSize);
        //折线
        mLineColor = array.getColor(R.styleable.LineCartView_lineCartPointColor, mLineColor);
        mLineHeight = array.getDimension(R.styleable.LineCartView_lineCartLineHeight, mLineHeight);
        //圆点
        mPointColor = array.getColor(R.styleable.LineCartView_lineCartPointColor, mPointColor);
        mPointSize = array.getDimension(R.styleable.LineCartView_lineCartPointSize, mPointSize);
        //分数对应属性
        mScoreTextColor = array.getColor(R.styleable.LineCartView_lineCartScoreTextColor, mScoreTextColor);
        mScoreTextSize = array.getDimension(R.styleable.LineCartView_lineCartScoreTextSize, mScoreTextSize);
        //x点之间的间距
        xInterval = array.getDimension(R.styleable.LineCartView_lineCartXInterval, xInterval);
        //y点之间的间距
        yInterval = array.getDimension(R.styleable.LineCartView_lineCartYInterval, yInterval);

        array.recycle();
    }

    /**
     * 初始化lineCart画布
     */
    private void init() {
        //虚线
        mDottedPaint = new Paint();
        mDottedPaint.setColor(mDottedLineColor);
        mDottedPaint.setAntiAlias(true);
        mDottedPaint.setTextAlign(Paint.Align.CENTER);
        mDottedPaint.setStyle(Paint.Style.STROKE);
        mDottedPaint.setStrokeCap(Paint.Cap.ROUND);
        mDottedPaint.setStrokeWidth(dipToPx(mDottedLineHeight));
        //折线
        mBrokenPaint = new Paint();
        mBrokenPaint.setStrokeWidth(dipToPx(mLineHeight));
        mBrokenPaint.setStrokeCap(Paint.Cap.SQUARE);
        mBrokenPaint.setAntiAlias(true);
        mBrokenPaint.setStyle(Paint.Style.STROKE);

        //分数对应得原点
        mPointPaint = new Paint();
        mPointPaint.setAntiAlias(true);
        mPointPaint.setStyle(Paint.Style.FILL);
        mPointPaint.setStrokeWidth(mPointSize);
        //左侧覆盖方块
        mVerticalBackgroundPaint = new Paint();
        mVerticalBackgroundPaint.setAntiAlias(true);
        mVerticalBackgroundPaint.setStyle(Paint.Style.FILL);
        //折线
        mBrokenPath = new Path();
        //垂直文字
        mVerticalTextPaint = initTextPaint(mVerticalTextColor, mVerticalTextSize);
        mVerticalTextPaint.setTextAlign(Paint.Align.LEFT);
        //底部文字
        mBottoTextPaint = initTextPaint(mBottoTextColor, mBottoTextSize);
        //分数
        mScorePaint = initTextPaint(mScoreTextColor, mScoreTextSize);
        //底部时间
        mBottoHintTextPaint = new Paint();
        mBottoHintTextPaint.setTextSize(dipToPx(mBottoHintSize));
        mBottoHintTextPaint.setAntiAlias(true);
        mBottoHintTextPaint.setStyle(Paint.Style.FILL);
        mBottoHintTextPaint.setTextAlign(Paint.Align.CENTER);

    }

    /**
     * 初始化文本
     *
     * @param color    颜色
     * @param textSize 字体大小
     */
    private Paint initTextPaint(int color, float textSize) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(color);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(dipToPx(textSize));
        paint.setStyle(Paint.Style.FILL);
        return paint;
    }


    @SuppressLint("DrawAllLocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isFirst) {
            viewWidth = getWidth();
            viewHeight = getHeight();
            int size = mData.size() - 1;
            float length = dipToPx(xInterval) * size;
            float v = mBottoTextPaint.measureText("12/23");
            float realLength = viewWidth - dipToPx(mDottedCoordinate) - v;
            if (length <= realLength) {
                minXinit = maxXinit = xInit = length + dipToPx(mDottedCoordinate) + v;
            } else {
                minXinit = xInit = realLength + dipToPx(mDottedCoordinate);
                maxXinit = length + dipToPx(mDottedCoordinate) + v;
            }
            setThemePaint();
            isFirst = false;
        }
        //画虚线
        drawLine(canvas);
        //画折线
        drawX(canvas);
        //画垂直文本
        drawVerticalText(canvas);
    }


    private float startX;
    private float startY;

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
                if (Math.abs(dis) < dipToPx(8)) {
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
     * 画底部文本，画折线，画文字
     *
     * @param canvas
     */
    @SuppressLint("ResourceAsColor")
    private void drawX(Canvas canvas) {
        mBrokenPath.reset();
        for (int i = 0; i < mData.size(); i++) {
            float x = xInit - i * dipToPx(xInterval);
            if (i == 0) {
                mBrokenPath.moveTo(x, getYValue(mData.get(i)));
            } else {
                mBrokenPath.lineTo(x, getYValue(mData.get(i)));
            }

            //画原点
            canvas.drawCircle(x, getYValue(mData.get(i)), dipToPx(mPointSize), mPointPaint);
            //画数据对应得文本
            canvas.drawText(mData.get(i), x, getYValue(mData.get(i)) - dipToPx(mScoreTextSize - 2), mScorePaint);

            //画底部文本
            if (mBottoData.size() > i) {
                canvas.drawText(mBottoData.get(i), x, viewHeight - dipToPx(11), mBottoTextPaint);
            }

            if (mBottoHintData.size() > 1) {
                canvas.drawText(mBottoHintData.get(i), x, viewHeight - dipToPx(2), mBottoHintTextPaint);
            }

        }
        canvas.drawPath(mBrokenPath, mBrokenPaint);
    }

    /**
     * 根据主题的不同设置画笔的颜色
     */
    private void setThemePaint() {
        mVerticalTextPaint.setColor(mVerticalTextColor);
        mVerticalBackgroundPaint.setColor(mVerticalBackgroundColor);
        mBottoTextPaint.setColor(mBottoTextColor);
        mBottoHintTextPaint.setColor(mBottoHintColor);
        mBrokenPaint.setColor(mLineColor);
        mPointPaint.setColor(mPointColor);
        mScorePaint.setColor(mScoreTextColor);
        mDottedPaint.setColor(mDottedLineColor);
    }


    private void intercept(Canvas canvas) {
        mVerticalBackgroundPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
        RectF rect = new RectF(0, 0, dipToPx(mDottedCoordinate), (viewHeight));
        canvas.drawRect(rect, mVerticalBackgroundPaint);
    }

    /**
     * 绘制垂直的文本
     *
     * @param canvas
     */
    private void drawVerticalText(Canvas canvas) {
        //将超出的部分截取出来
        intercept(canvas);
        if (mVerticalData == null || mVerticalData.isEmpty()) return;
        float coordinateY;
        float newHeight = viewHeight * 0.2f;
        for (int i = 0; i < mVerticalData.size(); i++) {
            coordinateY = newHeight + viewHeight * i * 0.3f;
            canvas.drawText(mVerticalData.get(i) + "", 0, coordinateY, mVerticalTextPaint);
        }
    }

    private void drawLine(Canvas canvas) {
        if (mVerticalData == null || mVerticalData.isEmpty()) return;
        float coordinateY;
        float newHeight = viewHeight * 0.2f;
        for (int i = 0; i < mVerticalData.size(); i++) {
            coordinateY = newHeight + viewHeight * i * 0.3f;
            drawDottedLine(canvas, dipToPx(mDottedCoordinate), coordinateY - dipToPx(mVerticalTextSize) / 2, viewWidth, coordinateY - dipToPx(mVerticalTextSize) / 2);
        }
    }


    /**
     * 添加分数对应得点
     */
    private float getYValue(String strValue) {
        int size = mVerticalData.size();
        float max = Float.parseFloat(mVerticalData.get(0));
        float min = Float.parseFloat(mVerticalData.get(size - 1));
        float center = Float.parseFloat(mVerticalData.get(1));

        float value = strValue.contains(".") ? Float.parseFloat(strValue) : Integer.parseInt(strValue);
        if (value > max) {
            value = max;
        } else if (value < min) {
            value = min;
        }
        if (center < value && value < max) {
            return viewHeight * 0.5f - dipToPx(mVerticalTextSize) / 2 - ((value - center) / (max - center)) * 0.3f * viewHeight;
        } else {
            return viewHeight * 0.8f - dipToPx(mVerticalTextSize) / 2 - ((value - min) / (center - min)) * 0.3f * viewHeight;
        }
    }


    /**
     * 画虚线
     *
     * @param canvas 画布
     * @param startX 起点X坐标
     * @param startY 起点Y坐标
     * @param stopX  终点X坐标
     * @param stopY  终点Y坐标
     */
    private void drawDottedLine(Canvas canvas, float startX, float startY, float stopX, float stopY) {
        mDottedPaint.setPathEffect(new DashPathEffect(new float[]{5, 8}, 4));
        mBrokenPath.reset();
        //定义路径起点
        mBrokenPath.moveTo(startX, startY);
        mBrokenPath.lineTo(stopX, stopY);
        canvas.drawPath(mBrokenPath, mDottedPaint);
    }

    /**
     * dip 转换成px
     *
     * @param dip
     * @return
     */
    private int dipToPx(float dip) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (dip * density + 0.5f * (dip >= 0 ? 1 : -1));
    }

    /**
     * 设置数据并且刷新界面
     *
     * @param verticalData 垂直的文本数据
     * @param scoreData    浮动的数据
     * @param bottomData   底部水平的日期
     */
    public void setData(List<String> verticalData, List<String> scoreData, List<Long> bottomData) {
        if (scoreData.size() != bottomData.size()) {
            throw new IllegalArgumentException("浮点数据个数必须等于底部数据个数");
        }
        if (!this.mVerticalData.isEmpty()) this.mVerticalData.clear();
        for (int i = verticalData.size() - 1; i >= 0; i--) {
            this.mVerticalData.add(verticalData.get(i));
        }
        mBottoHintData.clear();
        mBottoData.clear();
        mData.clear();
        for (Long l : bottomData) {
            String time = TimeUtils.format(l, "MM-dd HH:mm");
            String[] split = time.split(" ");
            mBottoData.add(split[0]);
            mBottoHintData.add(split[1]);
        }
        for (String score : scoreData) {
            if (score.contains(".")) {
                mData.add(StringUtils.twoPointNum((float) Double.parseDouble(score)));
            } else {
                mData.add(score);
            }
        }
        isFirst = true;
        invalidate();
    }

    /**
     * 设置数据并且刷新界面
     *
     * @param verticalData     垂直的文本数据
     * @param scoreData        浮动的数据
     * @param bottomData       底部水平的文本上
     * @param bottomSecondData 底部水平的文本下
     */
    public void setData(List<String> verticalData, List<String> scoreData, List<String> bottomData, List<String> bottomSecondData) {
        if (scoreData.size() != bottomData.size()) {
            throw new IllegalArgumentException("浮点数据个数必须等于底部数据个数");
        }
        if (!this.mVerticalData.isEmpty()) this.mVerticalData.clear();
        for (int i = verticalData.size() - 1; i >= 0; i--) {
            this.mVerticalData.add(verticalData.get(i));
        }
        mBottoHintData.clear();
        mBottoData.clear();
        mData.clear();
        mBottoData.addAll(bottomData);
        mBottoHintData.addAll(bottomSecondData);
        for (String score : scoreData) {
            if (score.contains(".")) {
                mData.add(StringUtils.twoPointNum((float) Double.parseDouble(score)));
            } else {
                mData.add(score);
            }
        }
        isFirst = true;
        invalidate();
    }
}
